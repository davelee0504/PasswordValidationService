package idv.dave.passwordvalidation.model;

import java.util.List;
import java.util.regex.Pattern;

public class RepeatedCharacterSequenceRule implements ValidationRule {

    public final static String errorMessage = "Repeated character sequence found!";
    @Override
    public ValidationResult isValid(String password) {
        String regex = "(.{1,})\\1";
        Pattern pattern = Pattern.compile(regex);
        boolean isRepeatedSequence = pattern.matcher(password).find();

        // if the repeated patter is found in the password
        if (isRepeatedSequence)
            return new ValidationResult(false, List.of(errorMessage));
        else
            return new ValidationResult(true);
    }
}
