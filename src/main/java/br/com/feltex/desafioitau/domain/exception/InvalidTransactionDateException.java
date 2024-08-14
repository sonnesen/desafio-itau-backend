package br.com.feltex.desafioitau.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InvalidTransactionDateException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        final var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Unprocessable Entity");
        pb.setDetail("The transaction cannot be in the future");

        return pb;
    }

}
