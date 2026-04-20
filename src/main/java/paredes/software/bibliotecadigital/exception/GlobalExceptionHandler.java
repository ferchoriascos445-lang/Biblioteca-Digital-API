package paredes.software.bibliotecadigital.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> buildErrorResponse(String message, HttpStatus status, String error, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);
        body.put("status", status.value());
        body.put("error", error);
        body.put("path", request.getDescription(false).replace("uri=", ""));
        return body;
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleRecursoNoEncontradoException(RecursoNoEncontradoException ex, WebRequest request) {
        return new ResponseEntity<>(
                buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, "Not Found", request),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>(
                buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request", request),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(
                buildErrorResponse(message, HttpStatus.BAD_REQUEST, "Validation Error", request),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoHandlerFound(NoHandlerFoundException ex, WebRequest request) {
        return new ResponseEntity<>(
                buildErrorResponse("Ruta no encontrada: " + ex.getRequestURL(), HttpStatus.NOT_FOUND, "Not Found", request),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(
                buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", request),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                buildErrorResponse("Ha ocurrido un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", request),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

