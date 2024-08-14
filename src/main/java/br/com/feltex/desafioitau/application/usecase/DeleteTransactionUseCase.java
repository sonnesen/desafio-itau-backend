package br.com.feltex.desafioitau.application.usecase;

import java.util.Objects;

import org.springframework.stereotype.Component;

import br.com.feltex.desafioitau.domain.port.TransactionRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DeleteTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public DeleteTransactionUseCase(final TransactionRepository transactionRepository) {
        this.transactionRepository = Objects.requireNonNull(transactionRepository,
                "TransactionRepository could not be null");
    }

    public void deleteAll() {
        transactionRepository.deleteAll();
    }
}
