import model.User;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.Assert.assertTrue;

public class PasswordValidatorTest {
    private static Validator validator;

    @BeforeClass
    public static void initAll() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testPasswordValidator() {
        assertTrue(validator.validateValue(User.class, "password", "pass#1User2").size() == 0);
        assertTrue(validator.validateValue(User.class, "password", "'p.as/s#1~U,ser22Yd").size() == 0);
        assertTrue(validator.validateValue(User.class, "password", "Pas@1").size() > 0);
        assertTrue(validator.validateValue(User.class, "password", "password@1").size() > 0);
        assertTrue(validator.validateValue(User.class, "password", "passWord1").size() > 0);
        assertTrue(validator.validateValue(User.class, "password", "passWord#").size() > 0);
        assertTrue(validator.validateValue(User.class, "password", "passWordWord#23Password$21~24/:d3po").size() > 0);
    }
}
