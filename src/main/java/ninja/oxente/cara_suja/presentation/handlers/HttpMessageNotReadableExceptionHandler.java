package ninja.oxente.cara_suja.presentation.handlers;

import java.util.Optional;
import ninja.oxente.cara_suja.presentation.dto.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HttpMessageNotReadableExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException error) {
        ErrorResponse response;
        String errorMessage = error.getMessage();
        if (!errorMessage.contains("Required request body is missing")) {
            response = new ErrorResponse(errorMessage, Optional.empty());
        } else {
            response = new ErrorResponse("Required request body is missing", Optional.empty());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
