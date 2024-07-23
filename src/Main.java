import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int command = -1;

        do {
            PersonManager.printMenu();
            if (scanner.hasNextInt()) {
                command = Integer.parseInt(scanner.nextLine());
            } else {
                scanner.nextLine();
            }
            PersonManager.handleCommand(command, scanner);
        } while (command != 0);
    }
}