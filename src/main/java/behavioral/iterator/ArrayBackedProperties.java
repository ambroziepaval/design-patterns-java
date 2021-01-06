package behavioral.iterator;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class SimpleCreature {

    private int strength, agility, intelligence;

    public int max() {
        return Math.max(strength, Math.max(agility, intelligence));
    }

    public int sum() {
        return strength + agility + intelligence;
    }

    public double average() {
        return sum() / 3.0;
    }
}

class Creature implements Iterable<Integer> {

    private int[] stats = new int[3];

    public int getStrength() {
        return stats[0];
    }

    public void setStrength(int value) {
        stats[0] = value;
    }

    public int getAgility() {
        return stats[1];
    }

    public void setAgility(int value) {
        stats[1] = value;
    }

    public int getIntelligence() {
        return stats[2];
    }

    public void setIntelligence(int value) {
        stats[2] = value;
    }

    public int sum() {
        return IntStream.of(stats).sum();
    }

    public int max() {
        return IntStream.of(stats).max().getAsInt();
    }

    public double average() {
        return IntStream.of(stats).average().getAsDouble();
    }

    @Override
    public Iterator<Integer> iterator() {
        return IntStream.of(stats).iterator();
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        for (int stat : stats) {
            action.accept(stat);
        }
    }

    @Override
    public Spliterator<Integer> spliterator() {
//        return Arrays.spliterator(stats);
        return IntStream.of(stats).spliterator();
    }
}

class ArrayBackedPropertiesDemo {

    public static void main(String[] args) {
        Creature creature = new Creature();
        creature.setAgility(12);
        creature.setIntelligence(13);
        creature.setStrength(17);
        System.out.println(
                "Creature has a max stat of " + creature.max()
                        + ", total stats of " + creature.sum()
                        + " and an average stat of " + creature.average()
        );
    }
}