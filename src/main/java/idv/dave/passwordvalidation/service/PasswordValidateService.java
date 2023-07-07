package idv.dave.passwordvalidation.service;

import idv.dave.passwordvalidation.model.ValidationResult;

public interface PasswordValidateService {
    ValidationResult validatePassword(String password);

}
