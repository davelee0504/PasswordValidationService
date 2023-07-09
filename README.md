# Requirement 
- Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each.
- Must be between 5 and 12 characters in length.
- Must not contain any sequence of characters immediately followed by the same sequence.

Note:
Repeated sequence of characters mean two or more characters repeat in the password. 

Please see the following examples.
- daveleeee54 -> fail ('ee' is repeated)
- davelee54 -> pass ('e' repeated is fine)

# Technology Stack
- Java 17 
- Spring Boot v3.1.1
- JUnit 
- Mockito
- Maven

# IDE
IntelliJ

# How to use
## RESTful API
```
POST /password/validate
Content-Type: application/json

{
    "password": "YOUR CREDENTIAL HERE"
}

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
mvn test
```
# Time taken
~12 hrs 
