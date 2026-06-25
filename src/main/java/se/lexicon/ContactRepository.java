package se.lexicon;

import java.util.Map;
import java.util.TreeMap;

public class ContactRepository {
    private Map<String, Contact> contacts = new TreeMap<>();

    public Map<String , Contact> getAllContacts() {
        return contacts;
    }

    public void addContact(Contact contact) {
        contacts.put(contact.getUuid(), contact);
    }
}
