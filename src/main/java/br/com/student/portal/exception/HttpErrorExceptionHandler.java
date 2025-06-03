package br.com.student.portal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.Instant.now;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class HttpErrorExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> badRequest(BadRequestException e) {
        return buildErrorResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ApiError> notFound(ObjectNotFoundException e) {
        return buildErrorResponse(NOT_FOUND, e.getMessage());
    }
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> forbidden(ForbiddenException e) {
        return buildErrorResponse(FORBIDDEN, e.getMessage());
    }

    private ResponseEntity<ApiError> buildErrorResponse(HttpStatus status, String message) {
        var error = new ApiError(status.value(), message, now().toEpochMilli());
        return ResponseEntity.status(status).body(error);
    }
}
