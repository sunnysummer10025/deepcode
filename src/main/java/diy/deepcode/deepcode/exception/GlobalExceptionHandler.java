package diy.deepcode.deepcode.exception;

import java.time.Instant;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDupEmail(DuplicateEmailException ex) {
        return build(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler(DuplicatePhoneException.class)
    public ResponseEntity<ErrorResponse> handleDupPhone(DuplicatePhoneException ex) {
        return build(HttpStatus.CONFLICT, ex);
    }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    var message =
        ex.getBindingResult().getFieldErrors().stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .collect(Collectors.joining(", "));
    log.error("Validation failed: {}", message);
    var error =
        new ErrorResponse(
            Instant.now(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            "Bad Request",
            message);
    return ResponseEntity.badRequest().body(error);
  }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(Exception ex) {
        log.error(ex.getMessage());
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, Exception ex) {
        log.error(ex.getMessage());
        var error = new ErrorResponse(
                Instant.now(),
                status.getReasonPhrase(),
                ex.getMessage(),
                ex.getLocalizedMessage()
        );
        return ResponseEntity.status(status).body(error);
    }

    public record ErrorResponse(
            Instant timestamp,
            String status,
            String error,
            String message) {
    }
}

