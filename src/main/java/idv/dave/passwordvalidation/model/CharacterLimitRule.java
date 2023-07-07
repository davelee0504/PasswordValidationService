package idv.dave.passwordvalidation.model;

import java.util.List;

public class CharacterLimitRule implements ValidationRule {

    public static final String errorMessage = "Invalid password with the character types.";

    @Override
    public ValidationResult isValid(String password) {

        int lowercaseCount = 0, numericCount = 0;
        for (char c : password.toCharArray()){
            if(Character.isLowerCase(c)) {
                lowercaseCount++;
            } else if(Character.isDigit(c)) {
                numericCount++;
            } else {
                return new ValidationResult(false, List.of(errorMessage));
            }
        }

        if(lowercaseCount > 0 && numericCount > 0)
            return new ValidationResult(true);
        else
            return new ValidationResult(false, List.of(errorMessage));
    }
}
