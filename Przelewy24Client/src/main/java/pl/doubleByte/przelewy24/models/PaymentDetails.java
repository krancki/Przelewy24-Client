package pl.doubleByte.przelewy24.models;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Optional;

@Value
public class PaymentDetails {
    BigDecimal price;
    String currency;
    String transactionDescription;
    PaymentMethod method;

    BigDecimal shippingCost;
    Integer transactionTimeLimit;
    Integer paymentType;
    boolean regulationAccept;
    boolean waitForResult;


    public Optional<BigDecimal> getShippingCost() {
        return Optional.ofNullable(shippingCost);
    }

    public Optional<Integer> getTransactionTimeLimit() {
        return Optional.ofNullable(transactionTimeLimit);
    }

    public Optional<Integer> getPaymentType() {
        return Optional.ofNullable(paymentType);
    }

    public Optional<Boolean> getRegulationAccept() {
        return Optional.ofNullable(regulationAccept);
    }

    public static PriceStep builder() {
        return new Builder();
    }

    public interface PriceStep {
        CurrencyStep price(BigDecimal price);
    }

    public interface CurrencyStep {
        TransactionDescriptionStep currency(String currency);
    }

    public interface TransactionDescriptionStep {
        MethodStep transactionDescription(String transactionDescription);
    }

    public interface MethodStep {
        OptionalStep method(PaymentMethod method);
    }

    public interface OptionalStep {
        OptionalStep waitForResult(boolean waitForResult);

        OptionalStep shippingCost(BigDecimal shippingCost);

        OptionalStep transactionTimeLimit(Integer transactionTimeLimit);

        OptionalStep paymentType(Integer paymentType);

        OptionalStep regulationAccept(boolean regulationAccept);

        PaymentDetails build();
    }

    // Concrete builder implementation
    private static class Builder implements PriceStep, CurrencyStep, TransactionDescriptionStep, MethodStep, OptionalStep {
        private BigDecimal price;
        private String currency;
        private String transactionDescription;
        private PaymentMethod method;

        private Integer transactionTimeLimit;
        private Integer paymentType;
        private boolean regulationAccept;
        private boolean waitForResult;
        private BigDecimal shippingCost;

        @Override
        public CurrencyStep price(BigDecimal price) {
            this.price = price;
            return this;
        }

        @Override
        public TransactionDescriptionStep currency(String currency) {
            this.currency = currency;
            return this;
        }

        @Override
        public MethodStep transactionDescription(String transactionDescription) {
            this.transactionDescription = transactionDescription;
            return this;
        }

        @Override
        public OptionalStep method(PaymentMethod method) {
            this.method = method;
            return this;
        }

        @Override
        public OptionalStep waitForResult(boolean waitForResult) {
            this.waitForResult = waitForResult;
            return this;
        }

        @Override
        public OptionalStep shippingCost(BigDecimal shippingCost) {
            this.shippingCost = shippingCost;
            return this;
        }


        @Override
        public OptionalStep transactionTimeLimit(Integer transactionTimeLimit) {
            this.transactionTimeLimit = transactionTimeLimit;
            return this;
        }

        @Override
        public OptionalStep paymentType(Integer paymentType) {
            this.paymentType = paymentType;
            return this;
        }

        @Override
        public OptionalStep regulationAccept(boolean regulationAccept) {
            this.regulationAccept = regulationAccept;
            return this;
        }

        @Override
        public PaymentDetails build() {
            return new PaymentDetails(price, currency, transactionDescription, method,
                    shippingCost, transactionTimeLimit, paymentType, regulationAccept, waitForResult);
        }
    }

}
