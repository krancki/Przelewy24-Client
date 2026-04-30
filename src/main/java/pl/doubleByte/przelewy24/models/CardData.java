package pl.doubleByte.przelewy24.models;

import lombok.Value;

@Value
public class CardData {
    String traceID;
    Means means;
    TransactionType transactionType;

    // Constructor for backward compatibility
    private CardData(String traceID, Means means, TransactionType transactionType) {
        this.traceID = traceID;
        this.means = means;
        this.transactionType = transactionType;
    }

    public static TraceIDStep builder() {
        return new Builder();
    }

    // Builder interfaces to enforce required fields
    public interface TraceIDStep {
        MeansStep traceID(String traceID);
    }

    public interface MeansStep {
        TransactionTypeStep means(Means means);
    }

    public interface TransactionTypeStep {
        BuildStep transactionType(TransactionType transactionType);
    }

    public interface BuildStep {
        CardData build();
    }

    // Concrete builder implementation
    private static class Builder implements TraceIDStep, MeansStep, TransactionTypeStep, BuildStep {
        private String traceID;
        private Means means;
        private TransactionType transactionType;

        @Override
        public MeansStep traceID(String traceID) {
            this.traceID = traceID;
            return this;
        }

        @Override
        public TransactionTypeStep means(Means means) {
            this.means = means;
            return this;
        }

        @Override
        public BuildStep transactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        @Override
        public CardData build() {
            return new CardData(traceID, means, transactionType);
        }
    }

}
