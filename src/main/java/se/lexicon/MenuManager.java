package se.lexicon;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class MenuManager {
    private final ContactRepository REPOSITORY;
    private final Scanner SCANNER;
    private final String[] FUNCTIONALITIES = {"Add New Contact", "Show All Contacts", "Modify Contact", "Search By Name", "Remove Contact By Name", "Set Favourite By Name", "Exit"};


    public MenuManager(ContactRepository repository) {
        this.REPOSITORY = repository;
        this.SCANNER = new Scanner(System.in);
    }

    public void start() {
        boolean isRunning = true;

        while (isRunning) {
            displayMenu();
            int choice = readMenuItemNumber("Choose an option: ");

            switch (choice) {
                case 1 -> addNewContact();
                case 2 -> listAllContacts();
                case 3 -> IO.println("Modify");
                case 4 -> IO.println("Search");
                case 5 -> IO.println("Delete");
                case 6 -> IO.println("Favourite");
                case 7 -> {
                    IO.println("Goodbye!");
                    isRunning = false;
                }
                default -> IO.println("Invalid option! Please try again");
            }
        }
    }

    private void displayMenu() {
        IO.println("\n================================================================================");
        IO.println("                                CONTACT APP");
        IO.println("================================================================================");
        for (int i = 0; i < FUNCTIONALITIES.length; i++) {
            System.out.printf("%-1d. %-15s%n", i + 1, FUNCTIONALITIES[i]);
        }
        IO.println("================================================================================");
    }

    private int readMenuItemNumber(String message) {
        while (true) {
            try {
                IO.print(message);
                int number = SCANNER.nextInt();
                if (number >= 1 && number <= FUNCTIONALITIES.length) {
                    return number;
                } else {
                    IO.println("ERROR: The number is out of range! It must be between 1 and " + FUNCTIONALITIES.length + ". ");
                }
            } catch (InputMismatchException e) {
                IO.println("ERROR: Invalid input! Please enter number only.");
                SCANNER.nextLine();
            }
        }
    }

    private void addNewContact() {
        SCANNER.nextLine();
        IO.println("\n--------------------------------------------------------------------------------");
        IO.println("                        *** ADD NEW CONTACT ***");
        IO.println("--------------------------------------------------------------------------------");
        boolean isValid = true;
        String firstName = ""; // shall i write anything into that?????????????????????
        while (isValid) {
            IO.print("First name: ");
            firstName = SCANNER.nextLine().trim();
            if (Validator.validateName(firstName)) {
                isValid = false;
            } else {
                IO.println("ERROR: Name cannot be empty.");
            }
        }

        isValid=true;
        String lastName = "";
        while (isValid) {
            IO.print("Last name: ");
            lastName = SCANNER.nextLine().trim();
            if (Validator.validateName(lastName)) {
                isValid = false;
            } else {
                IO.println("ERROR: Name cannot be empty.");
            }
        }

        isValid=true;
        String phone = "";
        while (isValid) {
            IO.print("Phone number: ");
            phone = SCANNER.nextLine().trim();
            if (Validator.validatePhone(phone)) {
                isValid = false;
            } else {
                IO.println("ERROR: Phone must be between 7-20 digits. (It can contain hyphen, space, dots, parenthesis as separators.)");
            }
        }

        Contact newContact = new Contact(firstName, lastName, phone);
        REPOSITORY.addContact(newContact);
        IO.println("Contact added successfully.");
    }

    private void listAllContacts() {
        Map<String, Contact> allContacts = REPOSITORY.getAllContacts();

        IO.println("\n--------------------------------------------------------------------------------");
        IO.println("                       *** LIST ALL CONTACTS ***");
        IO.println("--------------------------------------------------------------------------------");

        if (allContacts.isEmpty()) {
            IO.println("No contacts found.");
            return;
        }

        System.out.printf("%-36s | %-20s | %-15s%n", "UUID", "Name", "Phone");
        IO.println("--------------------------------------------------------------------------------");
        for (Contact contact : allContacts.values()) {
            System.out.printf("%-36s | %-20s | %-15s%n",
                    contact.getUuid(),
                    contact.getFullName(),
                    contact.getPhone());
        }
        IO.println("--------------------------------------------------------------------------------");
    }
}
