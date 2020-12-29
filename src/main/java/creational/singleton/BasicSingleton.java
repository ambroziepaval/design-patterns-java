package creational.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;

class BasicSingleton implements Serializable {
    private static final BasicSingleton INSTANCE = new BasicSingleton();
    private int value = 0;

    // cannot new this class, however
    // * instance can be created deliberately (reflection)
    // * instance can be created accidentally (serialization)
    private BasicSingleton() {
        System.out.println("Singleton is initializing");
    }

    // generated getter
    public static BasicSingleton getInstance() {
        return INSTANCE;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    // required for correct serialization
    // readResolve is used for _replacing_ the object read from the stream
//    protected Object readResolve() {
//        return INSTANCE;
//    }
}

class BasicSingletonDemo {
    static void saveToFile(BasicSingleton singleton, String filename) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(singleton);
        }
    }

    static BasicSingleton readFromFile(String filename) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (BasicSingleton) in.readObject();
        }
    }

    public static void main(String[] args) throws Exception {
        BasicSingleton singleton = BasicSingleton.getInstance();
        singleton.setValue(111);
        
        // Serialization
        String filename = "singleton.bin";
        saveToFile(singleton, filename);

        singleton.setValue(222);

        BasicSingleton singleton2 = readFromFile(filename);

        System.out.println(singleton == singleton2);

        System.out.println(singleton.getValue());
        System.out.println(singleton2.getValue());

        // Reflection
        Constructor<BasicSingleton> constructor = BasicSingleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        BasicSingleton reflectionBS = constructor.newInstance();
        System.out.println(reflectionBS.getValue());
    }
}