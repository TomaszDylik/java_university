package pl.zadanie.market.patterns.observer;

import java.util.Objects;

import pl.zadanie.market.domain.Transaction;

public record TransactionEvent(Transaction transaction) implements MarketEvent {

    public TransactionEvent {
        Objects.requireNonNull(transaction, "Transaction must not be null");
    }
}
