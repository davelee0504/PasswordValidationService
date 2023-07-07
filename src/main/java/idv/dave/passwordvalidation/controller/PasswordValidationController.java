package idv.dave.passwordvalidation.controller;

import idv.dave.passwordvalidation.model.ValidationResult;
import idv.dave.passwordvalidation.service.PasswordValidateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password/")
public class PasswordValidationController {

    private final PasswordValidateService passwordValidateService;

    public PasswordValidationController(PasswordValidateService passwordValidateService) {
        this.passwordValidateService = passwordValidateService;
    }

    @RequestMapping(value = "validate")
    public ResponseEntity<ValidationResult> validatePassword(@RequestParam("s") String password) {
        ValidationResult result = passwordValidateService.validatePassword(password);
        if (result.isValid())
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
