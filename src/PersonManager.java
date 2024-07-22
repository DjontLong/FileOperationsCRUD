import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonManager {
    private static final String FILE_NAME = "contacts.txt";

    public static void printMenu() {
        System.out.println("┌──────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                     CRUD >>>  File contacts.txt <<<                          |  ");
        System.out.println("├──────────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ 1 - Создать    2 - Удалить    3 - Редактировать    4 - Показать   0 - Выйти  │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────┘");
        System.out.print("Введите команду: ");
    }

    public void createPerson(Person person) {
        try(FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(person.getName() + " " + person.getSurname() + " " + person.getAge() + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<Person> getAllPersons() {
        try (FileReader fileReader = new FileReader(FILE_NAME)) {

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
