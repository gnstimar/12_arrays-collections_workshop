package se.lexicon;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ContactApp {
    public static Scanner scanner = new Scanner(System.in);
    static boolean shouldContinue = true;
    static String[] functionalities = {"Add New Contact", "Search By Name", "Modify Contact", "Remove Contact By Name", "Set Favourite By Name", "Show All Contact", "Exit"};
    static int option = 0;


    void main() {
        while (shouldContinue) {
            displayMenu();
            option = readMenuItemNumber("Choose an option: ");
            IO.println();
            switch (option) {
                case 1:
                    // TODO Add New Contact;
                    IO.println("Add New Contact");
                    break;
                case 2:
                    // TODO Search By Name;
                    IO.println("Search By Name");
                    break;
                case 3:
                    // TODO Modify Contact;
                    IO.println("Modify Contact");
                    break;
                case 4:
                    // TODO Remove Contact By Name;
                    IO.println("Remove Contact By Name");
                    break;
                case 5:
                    // TODO Set Favourite By Name;
                    IO.println("Set Favourite By Name");
                    break;
                case 6:
                    // TODO Show All Contact;
                    IO.println("Show All Contact");
                    break;
                case 7:
                    IO.println("Goodbye!");
                    return;
                default:
                    IO.println("Invalid option! Please try again.");
            }
            IO.println();
            shouldContinue = askToContinue("Continue? (yes/no): ");
            IO.println();
        }
    }

    public static void displayMenu() {
        IO.println("==============================");
        IO.println("        CONTACT APP");
        IO.println("==============================");
        for (int i = 0; i < functionalities.length; i++) {
            System.out.printf("%-1d. %-15s%n", i + 1, functionalities[i]);
        }
        IO.println("==============================");
    }

    public static int readMenuItemNumber(String message) {
        while (true) {
            try {
                IO.print(message);
                int number = scanner.nextInt();
                if (number >= 1 && number <= functionalities.length) {
                    return number;
                } else {
                    IO.println("Error: The number is out of range! It must be between 1 and " + functionalities.length + ". ");
                }
            } catch (InputMismatchException e) {
                IO.println("ERROR: Invalid input! Please enter number only.");
                scanner.nextLine();
            }
        }
    }

    public static boolean askToContinue(String message) {
        while (true) {
            IO.print(message);
            String answer = scanner.next();
            if (answer.equalsIgnoreCase("yes")) {
                return true;
            } else if (answer.equalsIgnoreCase("no")) {
                IO.println("Goodbye!");
                return false;
            } else {
                IO.println("Error: Invalid input! Please type 'yes' or 'no'.");
            }
        }
    }

}
