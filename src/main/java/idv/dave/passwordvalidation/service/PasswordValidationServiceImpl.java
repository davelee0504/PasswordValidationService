package idv.dave.passwordvalidation.service;

import idv.dave.passwordvalidation.model.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordValidationServiceImpl implements PasswordValidateService{
    private final PasswordValidator validator;

    @Autowired
    public PasswordValidationServiceImpl(PasswordValidator validator) {
        this.validator = validator;
    }

    @Override
    public ValidationResult validatePassword(String password) {
        return validator.validate(password);
    }
}
