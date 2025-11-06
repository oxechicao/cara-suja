package ninja.oxente.cara_suja.presentation.exceptions;

import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import ninja.oxente.cara_suja.presentation.dto.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JakartaValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handler(MethodArgumentNotValidException err) {
        Map<String, List<String>> errors = new HashMap<>();
        err.getBindingResult().getFieldErrors().forEach(error -> {
                if (errors.containsKey(error.getField())) {
                    List<String> errorsMessages = errors.get(error.getField());
                    errorsMessages.add(error.getDefaultMessage());
                    errors.put(error.getField(), errorsMessages);
                    return;
                }

                List<String> errorMessage = new ArrayList<>();
                errorMessage.add(error.getDefaultMessage());
                errors.put(error.getField(), errorMessage);
            }
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(new ErrorResponse("Invalid fields", Optional.of(errors)));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(
        ConstraintViolationException err) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err.getMessage());
    }
}
