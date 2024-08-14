package br.com.feltex.desafioitau.infrastructure.adapter.repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.feltex.desafioitau.domain.model.Transaction;
import br.com.feltex.desafioitau.domain.port.TransactionRepository;

@Component
public class InMemoryTransactionRepository implements TransactionRepository {

    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void save(Transaction transaction) {
        this.transactions.add(transaction);
    }

    @Override
    public void deleteAll() {
        this.transactions.clear();
    }

    @Override
    public List<Transaction> findAllWithCreatedAtAfter(final OffsetDateTime datetime) {
        return this.transactions.stream()
                .filter(transaction -> transaction.createdAt().isAfter(datetime)
                        || transaction.createdAt().isEqual(datetime))
                .toList();
    }

    @Override
    public List<Transaction> findAll() {
        return Collections.unmodifiableList(this.transactions);
    }

}
