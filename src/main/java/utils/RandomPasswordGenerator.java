package utils;

import org.passay.CharacterRule;
import org.passay.PasswordGenerator;

import java.util.Arrays;
import java.util.List;

public class RandomPasswordGenerator {
    public String generateRandomPassword() {

        List rules = Arrays.asList(new CharacterRule(PasswordCharactersData.UpperCase, 1),
                new CharacterRule(PasswordCharactersData.LowerCase, 1),
                new CharacterRule(PasswordCharactersData.Digit, 1), new CharacterRule(PasswordCharactersData.Special, 1));

        PasswordGenerator generator = new PasswordGenerator();
        return generator.generatePassword(16, rules);
    }
}
