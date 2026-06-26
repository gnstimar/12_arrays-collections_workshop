package se.lexicon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidatorTest {
    @Test
    @DisplayName("Positive: Validate non-empty name")
    void nonEmptyName() {
        String name = "Anna";
        Assertions.assertTrue(Validator.validateName(name));
    }

    @Test
    @DisplayName("Negative: Validate empty name")
    void emptyName() {
        String name = "";
        Assertions.assertFalse(Validator.validateName(name));
    }

    @Test
    @DisplayName("Positive: Validate good phone number structure")
    void phoneNumber() {
        String phone = "1234567";
        String phone1 = "+1234567";
        String phone2 = "123.4567";
        String phone3 = "123 4567";
        String phone4 = "123-4567";
        String phone5 = "(12)34567";
        String phone6 = "+36-20-123-4567";
        String phone7 = "(06) 1 234 5678";
        Assertions.assertTrue(Validator.validatePhone(phone));
        Assertions.assertTrue(Validator.validatePhone(phone1));
        Assertions.assertTrue(Validator.validatePhone(phone2));
        Assertions.assertTrue(Validator.validatePhone(phone3));
        Assertions.assertTrue(Validator.validatePhone(phone4));
        Assertions.assertTrue(Validator.validatePhone(phone5));
        Assertions.assertTrue(Validator.validatePhone(phone6));
        Assertions.assertTrue(Validator.validatePhone(phone7));
    }

    @Test
    @DisplayName("Negative: Validate bad phone number structure")
    void badPhoneNumber() {
        String phone = "123";
        String phone1 = "123456789012345678901";
        String phone2 = "123.abc";
        String phone3 = "Tel: 123 4567";
        String phone4 = "(12)34|56|7";
        String phone5 = "+36+20-123-4567";
        String phone6 = "+36/20/123/4567";
        String phone7 = "(06) 1 234?5678";
        Assertions.assertFalse(Validator.validatePhone(phone));
        Assertions.assertFalse(Validator.validatePhone(phone1));
        Assertions.assertFalse(Validator.validatePhone(phone2));
        Assertions.assertFalse(Validator.validatePhone(phone3));
        Assertions.assertFalse(Validator.validatePhone(phone4));
        Assertions.assertFalse(Validator.validatePhone(phone5));
        Assertions.assertFalse(Validator.validatePhone(phone6));
        Assertions.assertFalse(Validator.validatePhone(phone7));
    }
}
