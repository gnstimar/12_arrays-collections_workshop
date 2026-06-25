package se.lexicon;

public class Validator {
    public static boolean validateName(String name) {
        return !name.trim().isBlank();
    }

    public static boolean validatePhone(String phone) {
        String regex = "^\\+?[0-9. ()-]{7,20}$"; // 7-20 digits, optional + in the beginning, it allows -, space, dots, parenthesis as separators
        return phone.matches(regex);
    }
}
