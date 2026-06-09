package pl.zadanie.market.core;

import java.util.List;
import java.util.Objects;

public class ConsoleReporter {

    private static final int CHART_WIDTH = 40;
    private static final String SECTION_LINE = "=".repeat(60);
    private static final String SUBSECTION_LINE = "-".repeat(60);

    private final SimulationResult result;

    public ConsoleReporter(SimulationResult result) {
        this.result = Objects.requireNonNull(result, "Simulation result must not be null");
    }

    public void printReport() {
        printHeader();
        printSummary();
        printInflationHistory();
        printTurnStatistics();
        printFooter();
    }

    private void printHeader() {
        System.out.println();
        System.out.println(SECTION_LINE);
        System.out.println("           RAPORT SYMULACJI RYNKU");
        System.out.println(SECTION_LINE);
        System.out.println();
    }

    private void printSummary() {
        int turnCount = result.sellerProfitsPerTurn().size();
        double finalInflation = result.inflationHistory().get(result.inflationHistory().size() - 1);

        System.out.println("PODSUMOWANIE");
        System.out.println(SUBSECTION_LINE);
        System.out.printf("  Liczba tur:        %d%n", turnCount);
        System.out.printf("  Inflacja poczatkowa: %6.2f%%%n", result.inflationHistory().get(0) * 100);
        System.out.printf("  Inflacja koncowa:    %6.2f%%%n", finalInflation * 100);
        System.out.println();
    }

    private void printInflationHistory() {
        System.out.println("HISTORIA INFLACJI");
        System.out.println(SUBSECTION_LINE);

        List<Double> history = result.inflationHistory();
        double maxRate = history.stream().mapToDouble(Double::doubleValue).max().orElse(1.0);
        if (maxRate == 0.0) {
            maxRate = 1.0;
        }

        for (int i = 0; i < history.size(); i++) {
            double rate = history.get(i);
            int barLength = (int) Math.round((rate / maxRate) * CHART_WIDTH);
            String bar = "#".repeat(Math.max(barLength, 0));
            String label = i == 0 ? "Start" : String.format("Tura %2d", i);
            System.out.printf("  %s | %5.2f%% | %s%n", label, rate * 100, bar);
        }
        System.out.println();
    }

    private void printTurnStatistics() {
        System.out.println("STATYSTYKI TUR (SREDNIE WARTOSCI)");
        System.out.println(SUBSECTION_LINE);
        System.out.printf("  %4s | %18s | %18s%n", "Tura", "Sredni zysk sprzed.", "Srednie wydatki kup.");
        System.out.println("  " + "-".repeat(4) + "-+-" + "-".repeat(18) + "-+-" + "-".repeat(18));

        List<List<Double>> sellerProfits = result.sellerProfitsPerTurn();
        List<List<Double>> buyerExpenses = result.buyerExpensesPerTurn();

        for (int turn = 0; turn < sellerProfits.size(); turn++) {
            double avgSellerProfit = average(sellerProfits.get(turn));
            double avgBuyerExpense = average(buyerExpenses.get(turn));
            System.out.printf("  %4d | %18.2f | %18.2f%n", turn + 1, avgSellerProfit, avgBuyerExpense);
        }
        System.out.println();
    }

    private void printFooter() {
        System.out.println(SECTION_LINE);
        System.out.println();
    }

    private static double average(List<Double> values) {
        if (values.isEmpty()) {
            return 0.0;
        }
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }
}
