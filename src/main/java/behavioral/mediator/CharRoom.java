package behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

class Person {
    public String name;
    public CharRoom charRoom;
    private List<String> chatLog = new ArrayList<>();

    public Person(String name) {
        this.name = name;
    }

    public void receive(String sender, String message) {
        String s = sender + ": '" + message + "'";
        System.out.println("[" + name + "'s chat session] " + s);
        chatLog.add(s);
    }

    public void say(String message) {
        charRoom.broadcast(name, message);
    }

    public void privateMessage(String destination, String message) {
        charRoom.message(name, destination, message);
    }
}

class CharRoom {

    private List<Person> people = new ArrayList<>();

    public void join(Person person) {
        String joinMessage = person.name + " joined the room.";
        broadcast("ROOM", joinMessage);

        person.charRoom = this;
        people.add(person);
    }

    public void broadcast(String source, String message) {
        for (Person person : people) {
            if (!source.equals(person.name)) {
                person.receive(source, message);
            }
        }
    }

    public void message(String source, String destination, String message) {
        people.stream()
                .filter(p -> p.name.equals(destination))
                .findFirst()
                .ifPresent(person -> person.receive(source, message));
    }
}

class ChatRoomDemo {

    public static void main(String[] args) {

        final CharRoom charRoom = new CharRoom();

        final Person john = new Person("John");
        final Person jane = new Person("Jane");

        charRoom.join(john);
        charRoom.join(jane);

        john.say("hi room");
        jane.say("oh, hey john");

        Person simon = new Person("Simon");
        charRoom.join(simon);
        simon.say("hi everyone!");

        jane.privateMessage("Simon", "glad you could join us!");
    }
}