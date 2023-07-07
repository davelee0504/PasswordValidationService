package idv.dave.passwordvalidation.model;

public interface ValidationRule {
    ValidationResult isValid(String password);
}
