package se.lexicon;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuManager {
    private final ContactRepository REPOSITORY;
    private final Scanner SCANNER;
    private final String[] FUNCTIONALITIES = {"Add New Contact", "Show All Contacts", "Modify Contact", "Search By Name", "Remove Contact By Name", "Set Favourite By Name", "Exit"};

    private static void lineSeparator(boolean isDoubleLine) {
        if (isDoubleLine) {
            for (int i = 0; i < 123; i++) {
                IO.print("=");
            }
            IO.println("=");
        } else {
            for (int i = 0; i < 123; i++) {
                IO.print("-");
            }
            IO.println("-");
        }
    }

    private static String centerText(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        int paddingLeft = (width - text.length()) / 2;

        return " ".repeat(paddingLeft) + text;
    }

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
                case 6 -> setFavourite();
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
        IO.println("\n");
        lineSeparator(true);
        IO.println(centerText("CONTACT APP", 123));
        lineSeparator(true);
        for (int i = 0; i < FUNCTIONALITIES.length; i++) {
            System.out.printf("%-1d. %-15s%n", i + 1, FUNCTIONALITIES[i]);
        }
        lineSeparator(true);
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
        lineSeparator(false);
        IO.println(centerText("ADD NEW CONTACT", 123));
        lineSeparator(false);

        String firstName = readInString("First name: ", false);
        String lastName = readInString("Last name: ", false);
        String phone = readInPhone("Phone: ", false);
        boolean isFavourite = readInYesOrNo("Favourite? (yes/no): ");
        String email = readInEmail("Email: ", false);
        LocalDate birthday = LocalDate.parse(readInBirthday("Birthday (YYYY-MM-DD): ", false));

        Contact newContact = new Contact(firstName, lastName, phone, isFavourite, email, birthday);
        REPOSITORY.addContact(newContact);
        IO.println("Contact added successfully.");
    }

    private void listAllContacts() {
        List<Contact> allContacts = REPOSITORY.getAllContactsSortedByFullName();

        lineSeparator(false);
        IO.println(centerText("LIST ALL CONTACTS", 123));
        lineSeparator(false);

        if (allContacts.isEmpty()) {
            IO.println("No contacts found.");
            return;
        }

        printContacts(allContacts);
    }

    private void modifyContact() {
        lineSeparator(false);
        IO.println(centerText("MODIFY CONTACT", 123));
        lineSeparator(false);

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
        System.out.printf("%-39s | %-20s | %-15s | %-3s | %-20s | %-10s%n", "UUID", "Name", "Phone", "Fav", "Email", "Birthday");
        lineSeparator(false);
        for (Contact contact : contactsToPrint) {
            System.out.printf("%-1d. %-36s | %-20s | %-15s | %-3s | %-20s | %-10s%n",
                    counter = counter + 1,
                    contact.getUuid(),
                    contact.getFullName(),
                    contact.getPhone(),
                    contact.isFavourite() ? " *" : "",
                    contact.getEmail(),
                    contact.getBirthday().toString());
        }
        lineSeparator(false);
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
        String firstName = readInString("First name: ", true);
        IO.print("Last Name (hit enter to keep \"" + contact.getLastName() + "\"): ");
        String lastName = readInString("Last name: ", true);
        IO.print("Phone (hit enter to keep \"" + contact.getPhone() + "\"): ");
        String phone = readInPhone("Phone: ", true);
        boolean isFavourite = readInYesOrNo("Favourite (yes/no): ");
        IO.print("Email (hit enter to keep \"" + contact.getEmail() + "\"): ");
        String email = readInEmail("Email: ", true);
        IO.print("Birthday (hit enter to keep \"" + contact.getBirthday() + "\"): ");
        String birthdayInput = readInBirthday("Birthday (YYYY-MM-DD): ", true);
        LocalDate birthday;
        if (!birthdayInput.isEmpty()) {
            birthday = LocalDate.parse(birthdayInput);
        } else {
            birthday = contact.getBirthday();
        }

        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setPhone(phone);
        contact.setFavourite(isFavourite);
        contact.setEmail(email);
        contact.setBirthday(birthday);
        REPOSITORY.updateContact(contact);
        IO.println("Contact updated successfully.");
    }

    private String readInString(String message, boolean allowedToBeEmpty) {
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
        lineSeparator(false);
        IO.println(centerText("DELETE CONTACT", 123));
        lineSeparator(false);

        IO.print("Type name: ");
        String searchName = SCANNER.nextLine().trim();
        List<Contact> foundContacts = REPOSITORY.findOrContainsName(searchName);
        if (foundContacts.isEmpty()) {
            IO.println("There is no name that would contain this word.");
        } else if (foundContacts.size() == 1) {
            IO.println("There is only one match found.");
            printContacts(foundContacts);
            IO.println("Are you sure you want to delete \"" + foundContacts.getFirst().getFullName() + "\"? (yes/no)");
            boolean shouldDelete = readInYesOrNo("Continue? (yes/no): ");
            if (shouldDelete) {
                Contact contactToDelete = foundContacts.getFirst();
                REPOSITORY.deleteContact(contactToDelete.getUuid());
            } else {
                IO.println("Contact was not deleted.");
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

    public boolean readInYesOrNo(String message) {
        while (true) {
            IO.print(message);
            String answer = SCANNER.next();
            if (answer.equalsIgnoreCase("yes")) {
                return true;
            } else if (answer.equalsIgnoreCase("no")) {
                return false;
            } else {
                IO.println("Error: Invalid input! Please type 'yes' or 'no'.");
            }
        }
    }

    private void searchContact() {
        lineSeparator(false);
        IO.println(centerText("SEARCH CONTACT", 123));
        lineSeparator(false);

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

    public void setFavourite() {
        lineSeparator(false);
        IO.println(centerText("SET FAVOURITE", 123));
        lineSeparator(false);

        IO.print("Type name: ");
        String searchName = SCANNER.nextLine().trim();
        List<Contact> foundContacts = REPOSITORY.findOrContainsName(searchName);
        if (foundContacts.isEmpty()) {
            IO.println("There is no name that would contain this word.");
        } else if (foundContacts.size() == 1) {
            IO.println("There is only one match found. You can set it as favourite.");
            Contact contactToSetFavourite = foundContacts.getFirst();
            REPOSITORY.setFavourite(contactToSetFavourite);
        } else {
            IO.println("There are " + foundContacts.size() + " contacts that contain this word in their names:");
            printContacts(foundContacts);
            int contactNumberToSetFavourite = readInt("Enter the number of the contact you want to modify: ", foundContacts.size());
            Contact contactToSetFavourite = foundContacts.get(contactNumberToSetFavourite - 1);
            REPOSITORY.setFavourite(contactToSetFavourite);
        }
        IO.println("Contact set as favourite successfully.");
    }

    private String readInEmail(String message, boolean allowedToBeEmpty) {
        SCANNER.nextLine();
        boolean isValid = true;
        String email = "";
        while (isValid) {
            IO.print(message);
            email = SCANNER.nextLine().trim();
            if (allowedToBeEmpty && email.isEmpty()) {
                break;
            } else if (Validator.validateEmail(email)) {
                isValid = false;
            } else {
                IO.println("ERROR: Email must be valid email address. ");
            }
        }
        return email;
    }

    private String readInBirthday(String message, boolean allowedToBeEmpty) {
        boolean isValid = true;
        String birthday = "";
        while (isValid) {
            IO.print(message);
            birthday = SCANNER.nextLine().trim();
            if (allowedToBeEmpty && birthday.isEmpty()) {
                break;
            } else if (Validator.validateBirthday(birthday)) {
                isValid = false;
            } else {
                IO.println("ERROR: Invalid format, or invalid number for month or day. ");
            }
        }
        return birthday;
    }
}
