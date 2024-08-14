package br.com.feltex.desafioitau.infrastructure.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.feltex.desafioitau.domain.exception.InvalidTransactionDateException;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({ InvalidTransactionDateException.class })
    public ProblemDetail handleInvalidTransactionException(final InvalidTransactionDateException ex) {
        return ex.toProblemDetail();
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ProblemDetail handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final var invalidFields = e.getFieldErrors()
                .stream()
                .map(field -> new InvalidField(field.getField(), field.getDefaultMessage()))
                .toList();

        final var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("Bad Request");
        pb.setDetail("The request contains invalid fields");
        pb.setProperty("invalid-fields", invalidFields);

        return pb;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleConstraintViolationException(final ConstraintViolationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Constraint Violation");
        problemDetail.setDetail("One or more fields have validation errors.");

        final Map<String, String> violations = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        violation -> violation.getMessage()));

        problemDetail.setProperty("violations", violations);

        return problemDetail;
    }

    private record InvalidField(String name, String message) {
    }
}
