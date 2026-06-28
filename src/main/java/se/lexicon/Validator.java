package se.lexicon;

public class Validator {
    public static boolean validateName(String name) {
        return !name.trim().isBlank();
    }

    public static boolean validatePhone(String phone) {
        String regex = "^\\+?[0-9. ()-]{7,20}$"; // 7-20 digits, optional + in the beginning, it allows -, space, dots, parenthesis as separators
        return phone.matches(regex);
    }

    public static boolean validateEmail(String email) {
        String regex = "^(.+)@(\\S+)$";
        return email.matches(regex);
    }

    public static boolean validateBirthday(String birthday) {
        String regex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        return birthday.matches(regex);
    }

}
