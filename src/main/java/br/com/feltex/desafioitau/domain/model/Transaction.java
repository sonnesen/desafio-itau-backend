package br.com.feltex.desafioitau.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import br.com.feltex.desafioitau.domain.exception.InvalidTransactionDateException;

public record Transaction(BigDecimal value, OffsetDateTime createdAt) {

    public Transaction {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }
        if (value.doubleValue() < BigDecimal.ZERO.doubleValue()) {
            throw new IllegalArgumentException("value cannot be negative");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("createdAt cannot be null");
        }
        if (createdAt.isAfter(OffsetDateTime.now())) {
            throw new InvalidTransactionDateException();
        }
    }
}
