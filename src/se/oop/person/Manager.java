package se.oop.person;

public class Manager extends Person {
    private static final String password = "admin123";

    public Manager(String name) {
        super(name);
    }

    public static String getPassword() {
        return password;
    }


}
