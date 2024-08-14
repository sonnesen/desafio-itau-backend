package br.com.feltex.desafioitau.domain.port;

import java.time.OffsetDateTime;
import java.util.List;

import br.com.feltex.desafioitau.domain.model.Transaction;

public interface TransactionRepository {

    void save(Transaction transaction);

    void deleteAll();

    List<Transaction> findAllWithCreatedAtAfter(final OffsetDateTime datetime);

    List<Transaction> findAll();
}
