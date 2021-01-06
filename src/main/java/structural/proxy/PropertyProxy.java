package structural.proxy;

import java.util.Objects;

class Property<T> {
    private T value;

    public Property(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property<?> property = (Property<?>) o;
        return Objects.equals(value, property.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

class Creature {
    private final Property<Integer> agility = new Property<>(10);

    public int getAgility() {
        return agility.getValue();
    }

    public void setAgility(int agility) {
        this.agility.setValue(agility);
    }
}

class PropertyProxyDemo {
    public static void main(String[] args) {
        Creature c = new Creature();
        System.out.println(c.getAgility());
        c.setAgility(12);
        System.out.println(c.getAgility());
    }
}