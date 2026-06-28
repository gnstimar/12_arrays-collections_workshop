package se.lexicon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class ContactRepository {
    private Map<String, Contact> contacts = new TreeMap<>();
    Comparator<Contact> compareByFullName = Comparator.comparing(Contact::getFullName, String.CASE_INSENSITIVE_ORDER);

    public List<Contact> getAllContactsSortedByFullName() {
        List<Contact> sortedList = new ArrayList<>(contacts.values());
        sortedList.sort(compareByFullName);
        return sortedList;
    }

    public void addContact(Contact contact) {
        contacts.put(contact.getUuid(), contact);
    }

    public List<Contact> findOrContainsName(String searchName) {
        List<Contact> results = new ArrayList<>();
        for (Contact contact : contacts.values()) {
            if (contact.getFullName().toLowerCase().contains(searchName.toLowerCase())) {
                results.add(contact);
            }
        }
        results.sort(compareByFullName);
        return results;
    }

    public void updateContact(Contact updatedContact) {
//          // It only updated those contacts that are already in the Map
//        if (!contacts.containsKey(updatedContact.getUuid())) {
//            return;
//        }
        contacts.put(updatedContact.getUuid(), updatedContact);
    }

    public void deleteContact(String Uuid) {
        contacts.remove(Uuid);
    }

    public void saveContacts() {
        Path path = Paths.get("contacts.csv");
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (Contact contact : contacts.values()) {
                String oneLine = createOneLine(contact);
                bw.write(oneLine);
                bw.newLine();
            }
            IO.println("Output file is successfully created.");
        } catch (IOException e) {
            IO.println("ERROR: could not save contacts to file. " + e.getMessage());
        }
    }

    private String createOneLine(Contact contact) {
        StringBuilder oneLine = new StringBuilder(contact.getUuid());
        oneLine.append(";");
        oneLine.append(contact.getFirstName());
        oneLine.append(";");
        oneLine.append(contact.getLastName());
        oneLine.append(";");
        oneLine.append(contact.getPhone());
        oneLine.append(";");
        oneLine.append(contact.isFavourite());
        oneLine.append(";");
        oneLine.append(contact.getEmail());
        oneLine.append(";");
        oneLine.append(contact.getBirthday().toString());
        return oneLine.toString();
    }

    public void importContactsFromFile() {
        Path path = Paths.get("contacts.csv");
        if (!Files.exists(path)) {
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                String uuid = values[0];
                String firstName = values[1];
                String lastName = values[2];
                String phone = values[3];
                boolean isFavourite = values[4].equalsIgnoreCase("true") ? true : false;
                String email = values[5];
                LocalDate birthday = LocalDate.parse(values[6]);
                Contact contact = new Contact(uuid, firstName, lastName, phone, isFavourite, email, birthday);
                contacts.put(contact.getUuid(), contact);
            }
            IO.println("Contacts are successfully loaded from file.");
        } catch (IOException e) {
            IO.println("ERROR: could not load contacts from the file. " + e.getMessage());
        }
    }

    public void setFavourite(Contact contact) {
        contact.setFavourite(true);
    }
}
