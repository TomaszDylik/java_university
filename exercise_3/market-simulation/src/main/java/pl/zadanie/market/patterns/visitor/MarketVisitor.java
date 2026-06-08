package pl.zadanie.market.patterns.visitor;

import pl.zadanie.market.agents.Buyer;
import pl.zadanie.market.agents.Seller;

public interface MarketVisitor {

    void visit(Seller seller);

    void visit(Buyer buyer);
}
