package idv.dave.passwordvalidation.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password/")
public class PasswordValidationController {

    @RequestMapping("validate")
    public ResponseEntity validatePassword(@RequestParam("s") String password) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
