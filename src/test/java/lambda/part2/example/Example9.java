package lambda.part2.example;

import java.util.Objects;

@SuppressWarnings("unused")
public class Example9 {

    private static class Metric {
        private static final String TOTAL = "< total";

        private String nettingLevel;
        private String tradeNumber;
        private String counterparty;
        private String book;
        private String portfolio;
        private String curve;
        private String bucket;
        private String bucketPillar;

        private double netRisk;
        private double grossRisk;
        private double boReserve;

        private void foldFields(Metric m1, Metric m2) {
            tradeNumber = Objects.equals(m1.tradeNumber, m2.tradeNumber) ? m1.tradeNumber : TOTAL;
            nettingLevel = Objects.equals(m1.nettingLevel, m2.nettingLevel) ? m1.nettingLevel : TOTAL;
            counterparty = Objects.equals(m1.counterparty, m2.counterparty) ? m1.counterparty : TOTAL;
            book = Objects.equals(m1.book, m2.book) ? m1.book: TOTAL;
            portfolio = Objects.equals(m1.portfolio, m2.portfolio) ? m1.portfolio : TOTAL;
            curve = Objects.equals(m1.curve, m2.curve) ? m1.portfolio : TOTAL;
            bucket = Objects.equals(m1.bucket, m2.curve) ? m1.bucket : TOTAL;
            bucketPillar = Objects.equals(m1.bucketPillar, m1.bucketPillar) ? m1.bucketPillar : TOTAL;
        }
    }
}



