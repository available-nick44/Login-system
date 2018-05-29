package validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public void initialize(PhoneNumber phoneNumber) {
    }

    @Override
    public boolean isValid(String phoneNumber,
                           ConstraintValidatorContext cxt) {
        return phoneNumber != null
                && phoneNumber.matches("^(\\+\\d{1,3} )?((\\d{3})[- ]?(\\d{3})[- ]?(\\d{3})|(\\d{8,12}+))$");
    }

}