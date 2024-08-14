package br.com.feltex.desafioitau.application.usecase;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

import org.springframework.stereotype.Component;

import br.com.feltex.desafioitau.domain.model.Transaction;
import br.com.feltex.desafioitau.domain.port.TransactionRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CreateTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public CreateTransactionUseCase(final TransactionRepository transactionRepository) {
        this.transactionRepository = Objects.requireNonNull(transactionRepository,
                "TransactionRepository could not be null");
    }

    public void create(final Input input) {
        transactionRepository.save(new Transaction(input.value(), input.timestamp()));
    }

    public record Input(BigDecimal value, OffsetDateTime timestamp) {

    }

}
