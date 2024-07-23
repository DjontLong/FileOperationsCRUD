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
        System.out.println("│                     CRUD >>>  File contacts.txt <<<                          │");
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
            case 0 -> System.out.println(Messages.EXIT_PROGRAM);
            default -> System.out.println(Messages.UNKNOWN_COMMAND);
        }
    }

    // 1 - Create
    public static void createPerson(Scanner scanner) {
        System.out.print(Messages.NAME_PERSON);
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println(Messages.EMPTY_NAME);
            return;
        }

        System.out.print(Messages.SURNAME_PERSON);
        String surName = scanner.nextLine().trim();

        if (surName.isEmpty()) {
            System.out.println(Messages.EMPTY_SURNAME);
            return;
        }

        System.out.print(Messages.AGE_PERSON);
        String ageStr = scanner.nextLine().trim();
        int age;

        try {
            age = Integer.parseInt(ageStr);
            if (age < 0) {
                System.out.println(Messages.NEGATIVE_AGE);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(Messages.INCORRECT_AGE);
            return;
        }

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            Person person = new Person();
            person.setName(name);
            person.setSurname(surName);
            person.setAge(age);

            writer.write(person.getName() + " " + person.getSurname() + " " + person.getAge() + System.lineSeparator());
            System.out.println(Messages.PERSON_SUCCESSFULLY_CREATED + person.getName() + " " + person.getSurname() + " " + person.getAge());
        } catch (IOException e) {
            System.out.println(Messages.FILE_WRITE_ERROR + e.getMessage());
        }
    }

    // 2 - Delete
    public static void deletePerson(Scanner scanner) {
        System.out.print(Messages.NAME_PERSON);
        String removeName = scanner.nextLine().trim();

        if (removeName.isEmpty()) {
            System.out.println(Messages.EMPTY_NAME);
            return;
        }

        System.out.print(Messages.SURNAME_PERSON);
        String removeSurname = scanner.nextLine().trim();

        if (removeSurname.isEmpty()) {
            System.out.println(Messages.EMPTY_SURNAME);
            return;
        }

        Path filePath = Path.of(FILE_NAME);

        try {
            List<String> lines = Files.readAllLines(filePath);
            List<String> newLines = new ArrayList<>();
            boolean found = false;
            for (String line : lines) {
                List<String> arrList = new ArrayList<>(Arrays.asList(line.split(" ")));
                if (arrList.size() != 3) continue;
                if (!arrList.get(0).equals(removeName) || !arrList.get(1).equals(removeSurname)) {
                    newLines.add(line);
                } else {
                    found = true;
                }
            }
            if (!found) {
                System.out.println(String.format(Messages.PERSON_NOT_FOUND, removeName, removeSurname));
            } else {
                Files.write(filePath, newLines);
                System.out.println(Messages.PERSON_SUCCESSFULLY_DELETED);
            }
        } catch (IOException e) {
            System.out.println(Messages.FILE_DELETE_ERROR + e.getMessage());
        }
    }

    // 3 - Edit
    public static void editPerson(Scanner scanner) {
        System.out.print("Введите имя человека: ");
        String editName = scanner.nextLine().trim();

        if (editName.isEmpty()) {
            System.out.println(Messages.EMPTY_NAME);
            return;
        }

        System.out.print("Введите фамилию человека: ");
        String editSurname = scanner.nextLine().trim();

        if (editSurname.isEmpty()) {
            System.out.println(Messages.EMPTY_SURNAME);
            return;
        }

        System.out.print("Введите новое имя: ");
        String newName = scanner.nextLine().trim();

        if (newName.isEmpty()) {
            System.out.println(Messages.EMPTY_NAME);
            return;
        }

        System.out.print("Введите новую фамилию: ");
        String newSurname = scanner.nextLine().trim();

        if (newSurname.isEmpty()) {
            System.out.println(Messages.EMPTY_SURNAME);
            return;
        }

        System.out.print("Введите новый возраст: ");
        String newAgeStr = scanner.nextLine().trim();
        int newAge;

        try {
            newAge = Integer.parseInt(newAgeStr);
            if (newAge < 0) {
                System.out.println(Messages.NEGATIVE_AGE);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(Messages.INCORRECT_AGE);
            return;
        }

        Path filePath = Path.of(FILE_NAME);
        try {
            List<String> lines = Files.readAllLines(filePath);
            List<String> newLines = new ArrayList<>();
            boolean found = false;
            for (String line : lines) {
                List<String> arrList = new ArrayList<>(Arrays.asList(line.split(" ")));
                if (arrList.size() != 3) continue;
                if (arrList.get(0).equals(editName) && arrList.get(1).equals(editSurname)) {
                    newLines.add(newName + " " + newSurname + " " + newAge);
                    found = true;
                } else {
                    newLines.add(line);
                }
            }
            if (!found) {
                System.out.println(String.format(Messages.PERSON_NOT_FOUND, editName, editSurname));
            } else {
                Files.write(filePath, newLines);
                System.out.println(Messages.PERSON_SUCCESSFULLY_EDITED);
            }
        } catch (IOException e) {
            System.out.println(Messages.FILE_EDIT_ERROR + e.getMessage());
        }
    }

    // 4 - Show
    public static void getAllPerson() {
        Path filePath = Path.of(FILE_NAME);

        try {
            List<String> lines = Files.readAllLines(filePath);
            if (lines.isEmpty()) {
                System.out.println(Messages.FILE_EMPTY);
            } else {
                for (String line : lines) {
                    List<String> arrList = new ArrayList<>(Arrays.asList(line.split(" ")));
                    if (arrList.size() != 3) continue;
                    String name = arrList.get(0);
                    String surname = arrList.get(1);
                    int age;
                    try {
                        age = Integer.parseInt(arrList.get(2));
                        System.out.println(name + " " + surname + " " + age);
                    } catch (NumberFormatException e) {
                        System.out.println(String.format(Messages.AGE_PARSE_ERROR, name, surname));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(Messages.FILE_READ_ERROR + e.getMessage());
        }
    }
}
