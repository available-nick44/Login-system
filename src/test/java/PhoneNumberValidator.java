import model.User;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.Assert.assertTrue;

public class PhoneNumberValidator {
    private static Validator validator;

    @BeforeClass
    public static void initAll() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testPhoneNumberValidator() {
        assertTrue(validator.validateValue(User.class, "phoneNumber", "123456789").size() == 0);
        assertTrue(validator.validateValue(User.class, "phoneNumber", "123-456-784").size() == 0);
        assertTrue(validator.validateValue(User.class, "phoneNumber", "123 456 784").size() == 0);
        assertTrue(validator.validateValue(User.class, "phoneNumber", "+48 123 456 784").size() == 0);
        assertTrue(validator.validateValue(User.class, "phoneNumber", "+48 12345678445").size() == 0);
        assertTrue(validator.validateValue(User.class, "phoneNumber", "no 456789").size() > 0);
        assertTrue(validator.validateValue(User.class, "phoneNumber", "789 789 789 789").size() > 0);
        assertTrue(validator.validateValue(User.class, "phoneNumber", "123456").size() > 0);
    }
}