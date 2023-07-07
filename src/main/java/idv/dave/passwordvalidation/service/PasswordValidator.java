package idv.dave.passwordvalidation.service;

import idv.dave.passwordvalidation.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class PasswordValidator {
    List<ValidationRule> validationRules = new ArrayList<>();

    //three default rules apply if no rule provided
    public PasswordValidator() {
        this(new CharacterLimitRule(), new PasswordLengthRule(), new RepeatedCharacterSequenceRule());
    }

    public PasswordValidator(ValidationRule... rules) {
        Objects.requireNonNull(rules);
        validationRules.addAll(Arrays.asList(rules));
    }

    public ValidationResult validate(String password) {
        ValidationResult returnResult = new ValidationResult();

        for(ValidationRule rule : validationRules) {

            ValidationResult ruleValidationResult = rule.isValid(password);
            if(!ruleValidationResult.isValid()) {
                returnResult.addAllMessages(ruleValidationResult.getMessages());
            }
        }

        return returnResult;
    }

}
