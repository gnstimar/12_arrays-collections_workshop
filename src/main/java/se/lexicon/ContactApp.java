package se.lexicon;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeMap;

public class ContactApp {
    public static Scanner scanner = new Scanner(System.in);
    public static boolean shouldContinue = true;
    public static String[] functionalities = {"Add New Contact", "Search By Name", "Modify Contact", "Remove Contact By Name", "Set Favourite By Name", "Show All Contacts", "Exit"};
    public static int option = 0;
    public static TreeMap<String, String> contacts = new TreeMap<>();


    void main() {
        while (shouldContinue) {
            displayMenu();
            option = readMenuItemNumber("Choose an option: ");
            IO.println();
            switch (option) {
                case 1:
                    addNewContact();
                    break;
                case 2:
                    searchByName();
                    break;
                case 3:
                    modifyByName();
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
                    showAllContacts();
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

    private static void showAllContacts() {
        if (contacts == null || contacts.isEmpty()) {
            IO.println("No contacts.");
        } else {
            int i = 1;
            for (String name : contacts.keySet()) {
                System.out.println(i + ". " + name + ": " + contacts.get(name));
                i++;
            }
        }
    }

    public static void addNewContact() {
        IO.println("--- Add New Contact ---");
        String name = IO.readln("Name: ");
        scanner.nextLine();
        String phoneNumber = readPhone("Phone number: ", false);
        contacts.put(name, phoneNumber);
        IO.println(name + " is added to the contacts.");
    }

    public static String readPhone(String message, boolean allowedToBeEmpty) {
        while (true) {
            try {
                IO.print(message);
                String phoneNumber = scanner.nextLine();
                if (phoneNumber.isBlank() && allowedToBeEmpty) {
                    return phoneNumber;
                }
                String regex = "^\\+?[0-9. ()-]{7,20}$"; // 7-15 digits, optional + in the beginning, it allows -, space, dots, parenthesis as separators
                if (phoneNumber.matches(regex)) {
                    return phoneNumber;
                } else {
                    IO.println("Error: The phone number is incorrect! Try again. ");
                }
            } catch (InputMismatchException e) {
                IO.println("ERROR: Invalid input! Please enter number only.");
            }
        }
    }

    public static void searchByName() {
        IO.println("--- Search Contact By Name ---");
        String name = IO.readln("Name: ");
        if (contacts == null || contacts.isEmpty()) {
            IO.println("You have no contacts to search from.");
        }
        for (String contactName : contacts.keySet()) {
            if (contactName.equalsIgnoreCase(name)) {
                IO.println(contacts.get(contactName));
            } else {
                IO.println("There is no contact with this name.");
            }
        }
    }

    public static void modifyByName() {
        IO.println("--- Modify Contact By Name ---");
        scanner.nextLine();
        String originalName = IO.readln("Name: ");

        if (!contacts.containsKey(originalName)) {
            IO.println("Error: Contact not found.");
            return;
        }

        String originalPhoneNumber = contacts.get(originalName);

        IO.print("Name (hit enter to keep \"" + originalName + "\" or type a new): ");
        String inputName = scanner.nextLine();
        String finalName;

        if (inputName.isEmpty()) {
            finalName = originalName;
            IO.println("Name has not been changed.");
        } else {
            finalName = inputName;
        }

        String inputPhone = readPhone("Phone number (hit enter to keep \"" + originalPhoneNumber + "\" or type a new): ", true);
        String finalPhone;

        if (inputPhone.isEmpty()) {
            finalPhone = originalPhoneNumber;
            IO.println("Phone has not been changed.");
        } else {
            finalPhone = inputPhone;
        }

        contacts.remove(originalName);
        contacts.put(finalName, finalPhone);
        IO.println("Contact updated successfully.");
    }

}
