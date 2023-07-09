package idv.dave.passwordvalidation;

import idv.dave.passwordvalidation.model.CharacterLimitRule;
import idv.dave.passwordvalidation.model.PasswordLengthRule;
import idv.dave.passwordvalidation.model.RepeatedCharacterSequenceRule;
import idv.dave.passwordvalidation.model.ValidationResult;
import idv.dave.passwordvalidation.service.PasswordValidationServiceImpl;
import idv.dave.passwordvalidation.service.PasswordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PasswordValidationServiceTest {

    PasswordValidator validator;

    PasswordValidationServiceImpl passwordValidationService;

    @BeforeEach
    public void before() {
        validator = new PasswordValidator();
        passwordValidationService = new PasswordValidationServiceImpl(validator);
    }

    @Test
    public void testValidationWithSpecifiedRules() {
        String breakRulesPassword = "dVdV";
        // default validator with 3 rules
        ValidationResult breakRulesResult = validator.validate(breakRulesPassword);
        assertFalse(breakRulesResult.isValid());
        assertEquals(3, breakRulesResult.getMessages().size());

        // starting with 1 rule only
        validator = new PasswordValidator(new PasswordLengthRule());
        breakRulesResult = validator.validate(breakRulesPassword);
        assertFalse(breakRulesResult.isValid());
        assertEquals(1, breakRulesResult.getMessages().size());

        // with 2 rules, break length and character type
        validator = new PasswordValidator(new PasswordLengthRule(), new CharacterLimitRule());
        breakRulesResult = validator.validate(breakRulesPassword);
        assertFalse(breakRulesResult.isValid());
        assertEquals(2, breakRulesResult.getMessages().size());
    }

    @Test
    public void testValidPasswords() {
        // Note: davelee0504 -> single 'e' character repeated is fine
        List<String> validPasswords = List.of("abc123abc", "321abc", "davelee0504", "0504davelee");

        for (String password : validPasswords) {
            ValidationResult testResult = passwordValidationService.validatePassword(password);
            assertTrue(testResult.isValid());
            assertTrue(testResult.getMessages().isEmpty());
        }
    }

    @Test
    public void testInvalidPasswords() {
        String lengthTooShortPassword = "abc1";
        ValidationResult testResult = passwordValidationService.validatePassword(lengthTooShortPassword);
        assertTestResult(testResult.isValid(), false, testResult.getMessages(), List.of(PasswordLengthRule.errorMessage));

        String breakCharacterTypeLimit = "ABC123";
        testResult = passwordValidationService.validatePassword(breakCharacterTypeLimit);
        assertTestResult(testResult.isValid(), false, testResult.getMessages(), List.of(CharacterLimitRule.errorMessage));

        // Note: daveleeee0504 -> 'ee' characters repeated should fail
        String repeatedSequencesPassword = "daveleeee54";
        testResult = passwordValidationService.validatePassword(repeatedSequencesPassword);
        assertTestResult(testResult.isValid(), false, testResult.getMessages(), List.of(RepeatedCharacterSequenceRule.errorMessage));
    }

    private void assertTestResult(boolean testValid, boolean expectedValid, List<String> testMessages, List<String> expectedMessages) {
        assertEquals(expectedValid, testValid);
        assertFalse(testMessages.isEmpty());
        assertEquals(testMessages.get(0), expectedMessages.get(0));
    }

}
