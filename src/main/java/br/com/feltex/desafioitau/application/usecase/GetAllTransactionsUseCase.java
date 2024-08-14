package br.com.feltex.desafioitau.application.usecase;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import br.com.feltex.desafioitau.domain.port.TransactionRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GetAllTransactionsUseCase {

    private final TransactionRepository transactionRepository;

    public GetAllTransactionsUseCase(final TransactionRepository transactionRepository) {
        this.transactionRepository = Objects.requireNonNull(transactionRepository,
                "TransactionRepository could not be null");
    }

    public List<Output> getAllTransactions() {
        final var transactions = this.transactionRepository.findAll();
        return transactions.stream().map(Output::from).toList();
    }

    public record Output(BigDecimal value, OffsetDateTime datetime) {

        public static Output from(br.com.feltex.desafioitau.domain.model.Transaction transaction) {
            return new Output(transaction.value(), transaction.createdAt());
        }
    }

}
