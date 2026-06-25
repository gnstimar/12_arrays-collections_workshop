package se.lexicon;

public class ContactManagerApp {
    void main () {
        ContactRepository repository = new ContactRepository();
        MenuManager menuManager = new MenuManager(repository);

        menuManager.start();
    }
}
