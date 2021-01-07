package behavioral.state;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;

enum State {
    OFF_HOOK, // starting
    ON_HOOK, // terminal
    CONNECTING,
    CONNECTED,
    ON_HOLD
}

enum Trigger {
    CALL_DIALED,
    HUNG_UP,
    CALL_CONNECTED,
    PLACED_ON_HOLD,
    TAKEN_OFF_HOLD,
    LEFT_MESSAGE,
    STOP_USING_PHONE
}

class HandmadeStateMachineDemo {
    private static final Map<State, List<Pair<Trigger, State>>> rules = new EnumMap<>(State.class);
    private static final State exitState = State.ON_HOOK;
    private static State currentState = State.OFF_HOOK;

    static {
        rules.put(State.OFF_HOOK, Arrays.asList(
                new Pair<>(Trigger.CALL_DIALED, State.CONNECTING),
                new Pair<>(Trigger.STOP_USING_PHONE, State.ON_HOOK)
        ));
        rules.put(State.CONNECTING, Arrays.asList(
                new Pair<>(Trigger.HUNG_UP, State.OFF_HOOK),
                new Pair<>(Trigger.CALL_CONNECTED, State.CONNECTED)
        ));
        rules.put(State.CONNECTED, Arrays.asList(
                new Pair<>(Trigger.LEFT_MESSAGE, State.OFF_HOOK),
                new Pair<>(Trigger.HUNG_UP, State.OFF_HOOK),
                new Pair<>(Trigger.PLACED_ON_HOLD, State.ON_HOLD)
        ));
        rules.put(State.ON_HOLD, Arrays.asList(
                new Pair<>(Trigger.TAKEN_OFF_HOLD, State.CONNECTED),
                new Pair<>(Trigger.HUNG_UP, State.OFF_HOOK)
        ));
    }

    public static void main(String[] args) {
        final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        do {
            System.out.println("The phone is currently " + currentState);
            System.out.println("Select a trigger: ");

            for (int i = 0; i < rules.get(currentState).size(); i++) {
                final Trigger trigger = rules.get(currentState).get(i).getValue0();
                System.out.println(i + ". " + trigger);
            }

            boolean parseOK;
            int choice = 0;
            do {
                try {
                    System.out.println("Please enter your choice:");

                    choice = Integer.parseInt(console.readLine());
                    parseOK = choice >= 0
                            && choice < rules.get(currentState).size();
                } catch (Exception e) {
                    parseOK = false;
                }
            } while (!parseOK);

            currentState = rules.get(currentState).get(choice).getValue1();

        } while (currentState != exitState);
        System.out.println("And we are done!");
    }
}