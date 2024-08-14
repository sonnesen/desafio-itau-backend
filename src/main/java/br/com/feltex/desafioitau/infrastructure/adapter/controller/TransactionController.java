package br.com.feltex.desafioitau.infrastructure.adapter.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.feltex.desafioitau.api.TransacaoApi;
import br.com.feltex.desafioitau.application.usecase.CreateTransactionUseCase;
import br.com.feltex.desafioitau.application.usecase.CreateTransactionUseCase.Input;
import br.com.feltex.desafioitau.application.usecase.DeleteTransactionUseCase;
import br.com.feltex.desafioitau.application.usecase.GetAllTransactionsUseCase;
import br.com.feltex.desafioitau.application.usecase.GetAllTransactionsUseCase.Output;
import br.com.feltex.desafioitau.model.TransactionRequest;
import br.com.feltex.desafioitau.model.TransactionResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TransactionController implements TransacaoApi {

    private final CreateTransactionUseCase createTransactionUseCase;
    private final DeleteTransactionUseCase deleteTransactionUseCase;
    private final GetAllTransactionsUseCase getAllTransactionsUseCase;

    public TransactionController(final CreateTransactionUseCase createTransactionUseCase,
            final DeleteTransactionUseCase deleteTransactionUseCase,
            final GetAllTransactionsUseCase getAllTransactionsUseCase) {
        this.createTransactionUseCase = Objects.requireNonNull(createTransactionUseCase,
                "createTransactionUseCase cannot be null");
        this.deleteTransactionUseCase = Objects.requireNonNull(deleteTransactionUseCase,
                "deleteTransactionUseCase cannot be null");
        this.getAllTransactionsUseCase = Objects.requireNonNull(getAllTransactionsUseCase,
                "getAllTransactionsUseCase cannot be null");
    }

    @Override
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        log.info("Getting all transactions");
        final var transactions = this.getAllTransactionsUseCase.getAllTransactions();
        List<TransactionResponse> response = transactions.stream().map(TransactionController::from).toList();
        log.info("Transactions: {}", response);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> createTransaction(@Valid TransactionRequest body) {
        log.info("Creating transaction");
        createTransactionUseCase.create(new Input(body.getValor(), body.getDataHora()));
        log.info("Transaction created");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deleteTransactions() {
        log.info("Deleting all transactions");
        deleteTransactionUseCase.deleteAll();
        log.info("All transactions deleted");
        return ResponseEntity.noContent().build();
    }

    private static TransactionResponse from(Output output) {
        return new TransactionResponse().valor(output.value()).dataHora(output.datetime());
    }

}
