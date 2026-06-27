package se.lexicon;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuManager {
    private final ContactRepository REPOSITORY;
    private final Scanner SCANNER;
    private final String[] FUNCTIONALITIES = {"Add New Contact", "Show All Contacts", "Modify Contact", "Search By Name", "Remove Contact By Name", "Set Favourite By Name", "Exit"};


    public MenuManager(ContactRepository repository) {
        this.REPOSITORY = repository;
        this.SCANNER = new Scanner(System.in);
        this.REPOSITORY.importContactsFromFile();
    }

    public void start() {
        boolean isRunning = true;

        while (isRunning) {
            displayMenu();
            int choice = readMenuItemNumber("Choose an option: ");

            switch (choice) {
                case 1 -> addNewContact();
                case 2 -> listAllContacts();
                case 3 -> modifyContact();
                case 4 -> searchContact();
                case 5 -> deleteContact();
                case 6 -> IO.println("Favourite"); // TODO setFavourite()
                case 7 -> {
                    REPOSITORY.saveContacts();
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
                SCANNER.nextLine();
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
        IO.println("\n--------------------------------------------------------------------------------");
        IO.println("                        *** ADD NEW CONTACT ***");
        IO.println("--------------------------------------------------------------------------------");
        String firstName = readInName("First name: ", false);
        String lastName = readInName("Last name: ", false);
        String phone = readInPhone("Phone: ", false);

        Contact newContact = new Contact(firstName, lastName, phone);
        REPOSITORY.addContact(newContact);
        IO.println("Contact added successfully.");
    }

    private void listAllContacts() {
        List<Contact> allContacts = REPOSITORY.getAllContactsSortedByFullName();

        IO.println("\n--------------------------------------------------------------------------------");
        IO.println("                       *** LIST ALL CONTACTS ***");
        IO.println("--------------------------------------------------------------------------------");

        if (allContacts.isEmpty()) {
            IO.println("No contacts found.");
            return;
        }

        printContacts(allContacts);
    }

    private void modifyContact() {
        IO.println("\n--------------------------------------------------------------------------------");
        IO.println("                        *** MODIFY CONTACT ***");
        IO.println("--------------------------------------------------------------------------------");
        IO.print("Type name: ");
        String searchName = SCANNER.nextLine().trim();
        List<Contact> foundContacts = REPOSITORY.findOrContainsName(searchName);
        if (foundContacts.isEmpty()) {
            IO.println("There is no name that would contain this word.");
        } else if (foundContacts.size() == 1) {
            IO.println("There is only one match found. You can modify it.");
            Contact contactToModify = foundContacts.getFirst();
            executeModification(contactToModify);
        } else {
            IO.println("There are " + foundContacts.size() + " contacts that contain this word in their names:");
            printContacts(foundContacts);
            int contactNumberToModify = readInt("Enter the number of the contact you want to modify: ", foundContacts.size());
            Contact contactToModify = foundContacts.get(contactNumberToModify - 1);
            executeModification(contactToModify);
        }
    }

    private void printContacts(List<Contact> contactsToPrint) {
        int counter = 0;
        System.out.printf(" %-39s | %-20s | %-15s%n", "UUID", "Name", "Phone");
        IO.println("--------------------------------------------------------------------------------");
        for (Contact contact : contactsToPrint) {
            System.out.printf("%-1d. %-36s | %-20s | %-15s%n",
                    counter = counter + 1,
                    contact.getUuid(),
                    contact.getFullName(),
                    contact.getPhone());
        }
        IO.println("--------------------------------------------------------------------------------");
    }

    private int readInt(String message, int max) {
        while (true) {
            try {
                IO.print(message);
                int number = SCANNER.nextInt();
                SCANNER.nextLine();
                if (number >= 1 && number <= max) {
                    return number;
                } else {
                    IO.println("Error: The number is out of range! It must be between 1 and " + max + ".");
                }
            } catch (InputMismatchException e) {
                IO.println("ERROR: Invalid input! Please enter number only.");
                SCANNER.nextLine();
            }
        }
    }

    private void executeModification(Contact contact) {
        IO.print("First Name (hit enter to keep \"" + contact.getFirstName() + "\"): ");
        String firstName = readInName("First name: ", true);
        IO.print("Last Name (hit enter to keep \"" + contact.getLastName() + "\"): ");
        String lastName = readInName("Last name: ", true);
        IO.print("Phone (hit enter to keep \"" + contact.getPhone() + "\"): ");
        String phone = readInName("Phone: ", true);
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setPhone(phone);
        REPOSITORY.updateContact(contact);
        IO.println("Contact updated successfully.");
    }

    private String readInName(String message, boolean allowedToBeEmpty) {
        boolean isValid = true;
        String name = "";
        while (isValid) {
            IO.print(message);
            name = SCANNER.nextLine().trim();
            if (allowedToBeEmpty && name.isEmpty()) {
                break;
            } else if (Validator.validateName(name)) {
                isValid = false;
            } else {
                IO.println("ERROR: Name cannot be empty.");
            }
        }
        return name;
    }

    private String readInPhone(String message, boolean allowedToBeEmpty) {
        boolean isValid = true;
        String phone = "";
        while (isValid) {
            IO.print(message);
            phone = SCANNER.nextLine().trim();
            if (allowedToBeEmpty && phone.isEmpty()) {
                break;
            } else if (Validator.validatePhone(phone)) {
                isValid = false;
            } else {
                IO.println("ERROR: Phone must be between 7-20 digits. (It can contain hyphen, space, dots, parenthesis as separators.)");
            }
        }
        return phone;
    }

    private void deleteContact() {
        IO.println("\n--------------------------------------------------------------------------------");
        IO.println("                        *** DELETE CONTACT ***");
        IO.println("--------------------------------------------------------------------------------");
        IO.print("Type name: ");
        String searchName = SCANNER.nextLine().trim();
        List<Contact> foundContacts = REPOSITORY.findOrContainsName(searchName);
        if (foundContacts.isEmpty()) {
            IO.println("There is no name that would contain this word.");
        } else if (foundContacts.size() == 1) {
            IO.println("There is only one match found.");
            printContacts(foundContacts);
            IO.println("Are you sure you want to delete \"" + foundContacts.getFirst().getFullName() + "\"? (yes/no)");
            boolean shouldDelete = askToDelete("Continue? (yes/no): ");
            if (shouldDelete) {
                Contact contactToDelete = foundContacts.getFirst();
                REPOSITORY.deleteContact(contactToDelete.getUuid());
            } else {
                return;
            }
        } else {
            IO.println("There are " + foundContacts.size() + " contacts that contain this word in their names:");
            printContacts(foundContacts);
            int contactNumberToDelete = readInt("Enter the number of the contact you want to delete: ", foundContacts.size());
            Contact contactToDelete = foundContacts.get(contactNumberToDelete - 1);
            REPOSITORY.deleteContact(contactToDelete.getUuid());
        }
        IO.println("Contact deleted successfully.");
    }

    public boolean askToDelete(String message) {
        while (true) {
            IO.print(message);
            String answer = SCANNER.next();
            if (answer.equalsIgnoreCase("yes")) {
                return true;
            } else if (answer.equalsIgnoreCase("no")) {
                IO.println("Contact was not deleted.");
                return false;
            } else {
                IO.println("Error: Invalid input! Please type 'yes' or 'no'.");
            }
        }
    }

    private void searchContact() {
        IO.println("\n--------------------------------------------------------------------------------");
        IO.println("                        *** SEARCH CONTACT ***");
        IO.println("--------------------------------------------------------------------------------");
        IO.print("Type name: ");
        String searchName = SCANNER.nextLine().trim();
        List<Contact> foundContacts = REPOSITORY.findOrContainsName(searchName);
        if (foundContacts.isEmpty()) {
            IO.println("There is no name that would contain this word.");
        } else {
            IO.println("There are " + foundContacts.size() + " contacts that contain this word in their names:");
            printContacts(foundContacts);

        }
    }
}
