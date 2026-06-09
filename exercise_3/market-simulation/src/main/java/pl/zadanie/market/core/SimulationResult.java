package pl.zadanie.market.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record SimulationResult(
        List<List<Double>> sellerProfitsPerTurn,
        List<List<Double>> buyerExpensesPerTurn,
        List<Double> inflationHistory) {

    public SimulationResult {
        Objects.requireNonNull(sellerProfitsPerTurn, "Seller profits per turn must not be null");
        Objects.requireNonNull(buyerExpensesPerTurn, "Buyer expenses per turn must not be null");
        Objects.requireNonNull(inflationHistory, "Inflation history must not be null");

        sellerProfitsPerTurn = copyTurnSnapshots(sellerProfitsPerTurn);
        buyerExpensesPerTurn = copyTurnSnapshots(buyerExpensesPerTurn);
        inflationHistory = List.copyOf(inflationHistory);
    }

    private static List<List<Double>> copyTurnSnapshots(List<List<Double>> snapshots) {
        List<List<Double>> copies = new ArrayList<>(snapshots.size());
        for (List<Double> snapshot : snapshots) {
            copies.add(List.copyOf(snapshot));
        }
        return Collections.unmodifiableList(copies);
    }
}
