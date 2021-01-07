package behavioral.observer.events;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class Event<TArgs> {

    private int count = 0;
    private Map<Integer, Consumer<TArgs>> handlers = new HashMap<>();

    public Subscription addHandler(Consumer<TArgs> handler) {
        int i = count;
        handlers.put(count++, handler);
        return new Subscription(this, i);
    }

    public void fire(TArgs args) {
        for (Consumer<TArgs> handler : handlers.values()) {
            handler.accept(args);
        }
    }

    public class Subscription implements AutoCloseable {

        private final Event<TArgs> event;
        private final int id;

        public Subscription(Event<TArgs> event, int id) {
            this.event = event;
            this.id = id;
        }

        @Override
        public void close() /*throws Exception*/ {
            event.handlers.remove(id);
        }
    }
}

class PropertyChangedEventArgs {

    public Object source;
    public String propertyName;
    public Object newValue;

    public PropertyChangedEventArgs(Object source, String propertyName, Object newValue) {
        this.source = source;
        this.propertyName = propertyName;
        this.newValue = newValue;
    }
}

class Person {

    public Event<PropertyChangedEventArgs> propertyChangedEvent = new Event<>();

    private int age;

    public void setAge(int age) {
        if (this.age == age) return;
        this.age = age;

        propertyChangedEvent.fire(new PropertyChangedEventArgs(this, "age", age));
    }
}

class HandmadeEventsDemo {

    public static void main(String[] args) {

        final Person person = new Person();
        final Event<PropertyChangedEventArgs>.Subscription subscription = person.propertyChangedEvent
                .addHandler(eventArgs ->
                        System.out.println("Person's " + eventArgs.propertyName
                                + " has been changed to " + eventArgs.newValue));

        person.setAge(17);
        person.setAge(18);
        subscription.close();
        person.setAge(19);
    }
}
