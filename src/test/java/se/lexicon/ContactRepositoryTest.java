package se.lexicon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ContactRepositoryTest {
    ContactRepository contactRepository = new ContactRepository();
    Contact contact = new Contact("Anna", "Melvin", "1234567");

    @Test
    @DisplayName("Add one new contact to an empty Map")
    void addContact() {
        contactRepository.addContact(contact);
        Assertions.assertEquals(1, contactRepository.getAllContactsSortedByFullName().size(), "There should be one contact only (Anna Melvin, 1234567).");

        Contact actual = contactRepository.getAllContactsSortedByFullName().getFirst();
        Assertions.assertEquals(contact, actual, "The 2 contacts should be equal.");
    }

    @Test
    @DisplayName("Add the same contact two times - Map size should still be 1")
    void addDuplicateContact() {
        contactRepository.addContact(contact);
        contactRepository.addContact(contact);
        Assertions.assertEquals(1, contactRepository.getAllContactsSortedByFullName().size(), "There should be one contact only (Anna Melvin, 1234567).");
    }
}
