package lt.javau12.TransferX.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // emailo dublikato exeption
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmail(DuplicateEmailException emailException){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(emailException.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException (ValidationException validationException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException.getMessage());
    }




}
