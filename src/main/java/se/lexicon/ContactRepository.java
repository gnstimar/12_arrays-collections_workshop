package se.lexicon;

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
            if (contact.getFullName().toLowerCase().contains(searchName)) {
                results.add(contact);
            }
        }
        return results;
    }

    public void updateContact(Contact updatedContact) {
        contacts.put(updatedContact.getUuid(), updatedContact);
    }

    public void deleteContact(String Uuid) {
        contacts.remove(Uuid);
    }
}
