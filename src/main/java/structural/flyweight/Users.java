package structural.flyweight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

class User {

    private String fullName;

    public User(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}

class User2 {

    static List<String> strings = new ArrayList<>();
    private int[] names;

    public User2(String fullName) {
        ToIntFunction<String> getOrAdd = (String s) -> {
            int idx = strings.indexOf(s);
            if (idx != -1)
                return idx;
            else {
                strings.add(s);
                return strings.size() - 1;
            }
        };

        names = Arrays.stream(fullName.split(" "))
                .mapToInt(getOrAdd)
                .toArray();
    }

    public String getFullName() {
        return Arrays.stream(names)
                .mapToObj(i -> strings.get(i))
                .collect(Collectors.joining(","));
    }

}

class UsersDemo {
    public static void main(String[] args) {
        User user = new User("John Smith");
        User user2 = new User("Jane Smith");
        System.out.println(user.getFullName());
        System.out.println(user2.getFullName());

        User2 newUser = new User2("John Smith");
        User2 newUser2 = new User2("Jane Smith");
        System.out.println(newUser2.getFullName());
        System.out.println(newUser.getFullName());
    }
}