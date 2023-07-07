# Technology Stack
- Java 17 
- Spring Boot v3.1.1
- JUnit 
- Mockito

# IDE
IntelliJ

# How to use
## RESTful API
```
GET /password/validate?s=YOUR_PASSWORD_HERE

#Example of response
{
    "isValid": false,
    "messages": [
        "Invalid password with Character limit.",
        "Repeated character sequence found!"
    ]
}
```

## Code Snippet
```
    // Dependency Injection
    PasswordValidator validator;

    @Autowired
    public PasswordValidationServiceImpl(PasswordValidator validator) {
        this.validator = validator;
    }
    
    // Validate by method
    ValidationResult ruleValidationResult = validator.validate(password);
```

## Customised your own validation rule
```
public class NewValidationRule implements ValidationRule {
    @Override
    public ValidationResult isValid(String password) {
       // implement your own validation logic
    }
}

```

# How to run tests
```
mvn -Dtest="/*Test" test
```
# Time taken
~12 hrs 
