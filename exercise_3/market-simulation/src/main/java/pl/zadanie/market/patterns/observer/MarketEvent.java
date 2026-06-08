package pl.zadanie.market.patterns.observer;

public sealed interface MarketEvent permits InflationChangedEvent, NewOfferEvent, TransactionEvent {
}
