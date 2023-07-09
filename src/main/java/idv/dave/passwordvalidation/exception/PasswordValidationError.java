package idv.dave.passwordvalidation.exception;

import com.fasterxml.jackson.core.JsonParseException;
import idv.dave.passwordvalidation.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PasswordValidationError {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> resolveInternalError(Exception ex,
                                                              HttpServletRequest request, HttpServletResponse response) {
        if(ex instanceof JsonParseException || ex instanceof HttpMessageNotReadableException)
            return new ResponseEntity<>(new ErrorResponse("Invalid request body."), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new ErrorResponse("Consult tech support team for more details."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
