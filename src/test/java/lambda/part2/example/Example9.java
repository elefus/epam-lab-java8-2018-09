package lambda.part2.example;

import lombok.Getter;

import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings("unused")
public class Example9 {

    @Getter
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

        private void foldFields(Metric m1, Metric m2) {
            // (Metric → String) → String
            Function<Function<Metric, String>, String> folder = getter -> fold(getter, m1, m2);

            tradeNumber = folder.apply(Metric::getTradeNumber);
            nettingLevel = folder.apply(Metric::getNettingLevel);
            counterparty = folder.apply(Metric::getCounterparty);
            book = folder.apply(Metric::getBook);
            bucketPillar = folder.apply(Metric::getBucketPillar);
        }

        private String fold(Function<Metric, String> getter, Metric m1, Metric m2) {
            String m1Value = getter.apply(m1);
            return Objects.equals(m1Value, getter.apply(m2)) ? m1Value : TOTAL;
        }
    }
}



