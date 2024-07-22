import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private String surname;
    private int age;

    public Person() {}

    public Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + age;
    }

    // Create
    // Delete
    // Edit
    // Print

    // create ->>> contacts.txt
    // заполнить ->>> contacts.txt
}
