package idv.dave.passwordvalidation.model;

import java.util.Arrays;
import java.util.List;

public class PasswordLengthRule implements ValidationRule {

    private static final int MIN_LENGTH = 5;

    private static final int MAX_LENGTH = 12;

    public final static String errorMessage = "Invalid password length";

    @Override
    public ValidationResult isValid(String password) {
        boolean isValid = password.length() >= MIN_LENGTH && password.length() <= MAX_LENGTH;
        if (isValid)
            return new ValidationResult(true);
        else
            return new ValidationResult(false, List.of(errorMessage));

    }
}
