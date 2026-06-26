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
}
