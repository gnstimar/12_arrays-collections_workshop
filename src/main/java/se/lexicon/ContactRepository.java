package se.lexicon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ContactRepository {
    private Map<String, Contact> contacts = new TreeMap<>();

    public Map<String, Contact> getAllContacts() {
        return contacts;
    }

    public void addContact(Contact contact) {
        contacts.put(contact.getUuid(), contact);
    }

    public List<Contact> findOrContainsName(String searchName) {
        List<Contact> results = new ArrayList<>();
        for (Contact contact : contacts.values()) {
            if (contact.getFullName().toLowerCase().contains(searchName)) {
                results.add(contact);
            }
        }
        return results;
    }

    public void updateContact(Contact updatedContact) {
        contacts.put(updatedContact.getUuid(), updatedContact);
    }
}
