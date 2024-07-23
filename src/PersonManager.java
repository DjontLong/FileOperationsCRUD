import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

    public static void handleCommand(int command, Scanner scanner) {
        switch (command) {
            case 1 -> createPerson(scanner);
            case 2 -> deletePerson(scanner);
            case 3 -> editPerson(scanner);
            case 4 -> getAllPerson();
//            case 5 -> displayDirectoryContent(scanner);
//            case 0 -> System.out.println(Messages.EXIT_PROGRAM);
//            default -> System.out.println(Messages.UNKNOWN_COMMAND);
        }
    }

    // 1 - Create
    public static void createPerson(Scanner scanner) {
        System.out.print(Messages.NAME_PERSON);
        String name = scanner.nextLine();

        System.out.print(Messages.SURNAME_PERSON);
        String surName = scanner.nextLine();

        System.out.print(Messages.AGE_PERSON);
        String age = scanner.nextLine();

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {

            Person person = new Person();
            person.setName(name);
            person.setSurname(surName);
            person.setAge(Integer.parseInt(age));

            writer.write(person.getName() + " " + person.getSurname() + " " + person.getAge() + "\n");
            System.out.println(Messages.PERSON_SUCCESSFULLY_CREATED + person.getName() + " " + person.getSurname() + " " + person.getAge());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // 2 - Delete
    public static void deletePerson(Scanner scanner) {
        System.out.print(Messages.NAME_PERSON);
        String removeName = scanner.nextLine();

        System.out.print(Messages.SURNAME_PERSON);
        String removeSurname = scanner.nextLine();

        Path filePath = Path.of(FILE_NAME);

        try {
            List<String> lines = Files.readAllLines(filePath);
            List<String> newLines = new ArrayList<>();
            for (String line : lines) {
                List<String> arrList = new ArrayList<>(Arrays.asList(line.split(" ")));
                if (!arrList.get(0).equals(removeName) || !arrList.get(1).equals(removeSurname)) {
                    newLines.add(line);
                }
            }
            Files.write(filePath, newLines);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // 3 - Edit
    public static void editPerson(Scanner scanner) {
        System.out.print("Введите имя человека: ");
        String editName = scanner.nextLine();

        System.out.print("Введите фамилию человека: ");
        String editSurname = scanner.nextLine();

        System.out.print("Введите новое имя: ");
        String newName = scanner.nextLine();
        System.out.print("Введите новую фамилию: ");
        String newSurname = scanner.nextLine();
        System.out.print("Введите новый возраст: ");
        String newAge = scanner.nextLine();

        Path filePath = Path.of(FILE_NAME);
        try {
            List<String> lines = Files.readAllLines(filePath);
            List<String> newLines = new ArrayList<>();
            boolean found = false;
            for (String line : lines) {
                List<String> arrList = new ArrayList<>(Arrays.asList(line.split(" ")));
                if (arrList.get(0).equals(editName) && arrList.get(1).equals(editSurname)) {
                    newLines.add(newName + " " + newSurname + " " + newAge);
                    found = true;
                } else {
                    newLines.add(line);
                }
            }
            if (!found) {
                System.out.println("Человек с именем " + editName + " и фамилией " + editSurname + " не найден.");
            } else {
                Files.write(filePath, newLines);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // 4 - Show
    public static void getAllPerson() {
        try {
            Path filePath = Path.of(FILE_NAME);

            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                List<String> arrList = new ArrayList<>(Arrays.asList(line.split(" ")));
                String name = arrList.get(0);
                String surname = arrList.get(1);
                int age = Integer.parseInt(arrList.get(2));
                System.out.println(name + " " + surname + " " + age);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
