package behavioral.chainofresponsibility.broker;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

// CQS
class Event<TArgs> {

    private int index = 0;
    private Map<Integer, Consumer<TArgs>> handlers = new HashMap<>();

    public int subscribe(Consumer<TArgs> handler) {
        int i = index;
        handlers.put(index++, handler);
        return i;
    }

    public void unsubscribe(int key) {
        handlers.remove(key);
    }

    public void fire(TArgs args) {
        for (Consumer<TArgs> handler : handlers.values()) {
            handler.accept(args);
        }
    }
}

class Query {

    public String creatureName;
    public Argument argument;
    public int result;

    public Query(String creatureName, Argument argument, int result) {
        this.creatureName = creatureName;
        this.argument = argument;
        this.result = result;
    }

    enum Argument {
        ATTACK, DEFENSE
    }
}

class Game {
    public Event<Query> queries = new Event<>();
}

class Creature {

    public String name;

    private Game game;
    private int baseAttack, baseDefense;

    public Creature(Game game, String name, int baseAttack, int baseDefense) {
        this.game = game;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.name = name;
    }

    int getAttack() {
        Query query = new Query(name, Query.Argument.ATTACK, baseAttack);
        game.queries.fire(query);
        return query.result;
    }

    int getDefense() {
        Query query = new Query(name, Query.Argument.DEFENSE, baseDefense);
        game.queries.fire(query);
        return query.result;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "name='" + name + '\'' +
                ", attack=" + getAttack() +
                ", defense=" + getDefense() +
                '}';
    }
}

class CreatureModifier {

    protected Game game;
    protected Creature creature;

    public CreatureModifier(Game game, Creature creature) {
        this.game = game;
        this.creature = creature;
    }
}

class IncreaseDefenseModifier extends CreatureModifier implements AutoCloseable {

    private final int token;

    public IncreaseDefenseModifier(Game game, Creature creature) {
        super(game, creature);


        token = game.queries.subscribe(query -> {
            if (query.creatureName.equals(creature.name)
                    && query.argument.equals(Query.Argument.DEFENSE)) {
                query.result += 3;
            }
        });
    }

    @Override
    public void close() /*throws Exception*/ {
        game.queries.unsubscribe(token);
    }
}

class DoubleAttackModifier extends CreatureModifier implements AutoCloseable {

    private final int token;

    public DoubleAttackModifier(Game game, Creature creature) {
        super(game, creature);


        token = game.queries.subscribe(query -> {
            if (query.creatureName.equals(creature.name)
                    && query.argument.equals(Query.Argument.ATTACK)) {
                query.result *= 2;
            }
        });
    }

    @Override
    public void close() /*throws Exception*/ {
        game.queries.unsubscribe(token);
    }
}

class BrokerChainDemo {

    public static void main(String[] args) {
        Game game = new Game();
        Creature goblin = new Creature(game, "Goblin", 2, 2);
        System.out.println(goblin);

        IncreaseDefenseModifier increaseDefenseModifier = new IncreaseDefenseModifier(game, goblin);
        System.out.println(goblin);

        try (DoubleAttackModifier doubleAttackModifier = new DoubleAttackModifier(game, goblin)) {
            System.out.println(goblin);
        }

        System.out.println(goblin);
    }
}