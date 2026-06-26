package se.lexicon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ContactRepositoryTest {
    ContactRepository contactRepository = new ContactRepository();
    Contact contact = new Contact("Anna", "Melvin", "1234567");

    @Test
    @DisplayName("Positive: Add one new contact to an empty Map")
    void addContact() {
        contactRepository.addContact(contact);
        Assertions.assertEquals(1, contactRepository.getAllContactsSortedByFullName().size(), "There should be one contact only (Anna Melvin, 1234567).");

        Contact actual = contactRepository.getAllContactsSortedByFullName().getFirst();
        Assertions.assertEquals(contact, actual, "The 2 contacts should be equal.");
    }

    @Test
    @DisplayName("Negative: Add the same contact two times - Map size should still be 1")
    void addDuplicateContact() {
        contactRepository.addContact(contact);
        contactRepository.addContact(contact);
        Assertions.assertEquals(1, contactRepository.getAllContactsSortedByFullName().size(), "There should be one contact only (Anna Melvin, 1234567).");
    }

    @Test
    @DisplayName("Positive: Find a contact that contains the search word")
    void findOneContact() {
        contactRepository.addContact(contact);
        Contact contact2 = new Contact("John", "Hoywer", "2345678");
        contactRepository.addContact(contact2);
        List<Contact> result = contactRepository.findOrContainsName("Anna Melvin");
        Assertions.assertEquals(1, result.size(), "There should be only one contact found.");
        Assertions.assertEquals(contact, result.getFirst(), "We should only get back \"Anna Melvin\".");
    }

    @Test
    @DisplayName("Negative: Trying to find a contact that does not exist")
    void findNonExistingContact() {
        List<Contact> result = contactRepository.findOrContainsName("No Person");
        Assertions.assertEquals(0, result.size(), "There should be zero contact found.");
        Assertions.assertTrue(result.isEmpty(), "The result list should be empty.");
    }

    @Test
    @DisplayName("Positive: Update one contact's last name and phone number")
    void updateOneContact() {
        contactRepository.addContact(contact);
        contact.setLastName("melvinupdated");
        contact.setPhone("3333333333");
        contactRepository.updateContact(contact);
        List<Contact> result = contactRepository.getAllContactsSortedByFullName();
        Assertions.assertEquals(1, result.size(), "There should be only one contact found.");
        Assertions.assertEquals("melvinupdated", result.getFirst().getLastName(), "We should see \"melvinupdated\".");
        Assertions.assertEquals("3333333333", result.getFirst().getPhone(), "We should see \"3333333333\".");
    }

    @Test
    @DisplayName("Negative: Trying to update a contact that does not exist")
    void updateNonExistingContact() {
        Contact newContact = new Contact("John", "Hoywer", "2345678");
        contactRepository.updateContact(newContact);
        List<Contact> result = contactRepository.getAllContactsSortedByFullName();
        Assertions.assertEquals(1, result.size(), "There should be one contact found. (we put the new contact into the Map)");
        Assertions.assertEquals(newContact, result.getFirst(), "We should only get back \"John Hoywer\".");
    }

    @Test
    @DisplayName("Positive: Delete one contact")
    void deleteOneContact() {
        contactRepository.addContact(contact);
        contactRepository.deleteContact(contact.getUuid());
        List<Contact> result = contactRepository.getAllContactsSortedByFullName();
        Assertions.assertEquals(0, result.size(), "There should be zero contact found.");
        Assertions.assertTrue(result.isEmpty(), "The result list should be empty.");
    }

    @Test
    @DisplayName("Negative: Trying to delete a contact that does not exist")
    void deleteNonExistingContact() {
        Contact newContact = new Contact("John", "Hoywer", "2345678");
        contactRepository.deleteContact(newContact.getUuid());
        List<Contact> result = contactRepository.getAllContactsSortedByFullName();
        Assertions.assertEquals(0, result.size(), "There should be zero contact found.");
        Assertions.assertTrue(result.isEmpty(), "The result list should be empty.");
    }

    @Test
    @DisplayName("Test alphabetical sorting")
    void sortAlphabetically() {
        Contact contact1 = new Contact("Zackery", "Zucker", "12345678");
        Contact contact2 = new Contact("Bennett", "Balbo", "23456789");
        Contact contact3 = new Contact("Diana", "Dobermann", "023456780");
        Contact contact4 = new Contact("Cecilia", "Talorman", "11112345678111");
        contactRepository.addContact(contact1);
        contactRepository.addContact(contact2);
        contactRepository.addContact(contact3);
        contactRepository.addContact(contact4);
        List<Contact> result = contactRepository.getAllContactsSortedByFullName();
        Assertions.assertEquals("Bennett Balbo", result.getFirst().getFullName(), "Bennet should be the first one");
        Assertions.assertEquals("Cecilia Talorman", result.get(1).getFullName(), "Cecilia should be the first one");
        Assertions.assertEquals("Diana Dobermann", result.get(2).getFullName(), "Diana should be the first one");
        Assertions.assertEquals("Zackery Zucker", result.get(3).getFullName(), "Zackery should be the first one");
    }
}
