package validators;

import org.apache.commons.lang3.StringUtils;
import utils.PasswordCharactersData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password password) {
    }

    @Override
    public boolean isValid(String password,
                           ConstraintValidatorContext cxt) {
        return password != null
                && (password.length() > 8)
                && (password.length() < 33)
                && !password.equals(password.toLowerCase()) // contains at least one capital letter
                && password.matches(".*\\d+.*") // contains at least one digit
                && StringUtils.containsAny(password, PasswordCharactersData.Special.getCharacters()); // contains at least one special character

    }

}
