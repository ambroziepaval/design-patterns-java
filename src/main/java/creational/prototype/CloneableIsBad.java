package creational.prototype;

import java.util.Arrays;

// Cloneable is a marker interface
class Address implements Cloneable {
    public String streetName;
    public int houseNumber;

    public Address(String streetName, int houseNumber) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetName='" + streetName + '\'' +
                ", houseNumber=" + houseNumber +
                '}';
    }

    // base class clone() is protected
    @Override
    public Object clone() {
        return new Address(streetName, houseNumber);
    }
}

class Person implements Cloneable {
    public String[] names;
    public Address address;

    public Person(String[] names, Address address) {
        this.names = names;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "names=" + Arrays.toString(names) +
                ", address=" + address +
                '}';
    }

    @Override
    public Object clone() {
        return new Person(
                names.clone(),
                (Address) address.clone()
        );
    }
}

class CloneableDemo {
    public static void main(String[] args) {
        Person john = new Person(new String[]{"John", "Smith"},
                new Address("London Road", 123));

        // shallow copy, not good:
        //Person jane = john;

        // jane is the girl next door
        Person jane = (Person) john.clone();
        jane.names[0] = "Jane"; // clone is (originally) shallow copy
        jane.address.houseNumber = 124;

        System.out.println(john);
        System.out.println(jane);
    }
}