package pl.zadanie.market.agents;

import pl.zadanie.market.patterns.visitor.MarketVisitor;

public interface MarketParticipant {

    void accept(MarketVisitor visitor);
}
