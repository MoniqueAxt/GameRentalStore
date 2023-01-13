package se.oop.person;

import java.util.UUID;

abstract public class Person {
    private String id;
    private String name;

    // ID created
    public Person(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
    // No new ID created
    public Person()
    {}

    @Override
    public String toString() {
        return id + " : " + name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

