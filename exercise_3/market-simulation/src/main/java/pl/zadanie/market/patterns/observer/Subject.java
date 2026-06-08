package pl.zadanie.market.patterns.observer;

public interface Subject {

    void attach(Observer observer);

    void detach(Observer observer);

    void notifyObservers(MarketEvent event);
}
