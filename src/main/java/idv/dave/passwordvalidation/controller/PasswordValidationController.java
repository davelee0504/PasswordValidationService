package idv.dave.passwordvalidation.controller;

import idv.dave.passwordvalidation.model.Credential;
import idv.dave.passwordvalidation.model.ValidationResult;
import idv.dave.passwordvalidation.service.PasswordValidateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password/")
public class PasswordValidationController {

    private final PasswordValidateService passwordValidateService;

    public PasswordValidationController(PasswordValidateService passwordValidateService) {
        this.passwordValidateService = passwordValidateService;
    }

    @PostMapping(path = "validate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValidationResult> validatePassword(@RequestBody Credential credential) {
        ValidationResult result = passwordValidateService.validatePassword(credential.getPassword());
        if (result.isValid())
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
