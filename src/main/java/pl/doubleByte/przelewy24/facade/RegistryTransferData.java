package pl.doubleByte.przelewy24.facade;

import lombok.Value;
import pl.doubleByte.przelewy24.models.*;

import java.util.List;
import java.util.Optional;

@Value
public class RegistryTransferData {

    PaymentPurchaseId purchaseId;
    CustomerDetails customerDetails;
    PaymentDetails paymentDetails;
    List<CartItem> cartItems;
    AdditionalDetails additionalDetails;

    private RegistryTransferData(
            PaymentPurchaseId purchaseId,
            CustomerDetails customerDetails,
            PaymentDetails paymentDetails,
            List<CartItem> cartItems,
            AdditionalDetails additionalDetails
    ) {
        this.purchaseId = purchaseId;
        this.customerDetails = customerDetails;
        this.paymentDetails = paymentDetails;
        this.cartItems = cartItems;
        this.additionalDetails = additionalDetails;
    }

    public Optional<List<CartItem>> getCartItems() {
        return Optional.ofNullable(cartItems);
    }

    public Optional<AdditionalDetails> getAdditionalDetails() {
        return Optional.ofNullable(additionalDetails);
    }

    public static PaymentStepId builder() {
        return new Builder();
    }

    public interface PaymentStepId {
        CustomerDetailsStep withPurchaseId(PaymentPurchaseId paymentPurchaseId);
    }

    public interface CustomerDetailsStep {
        PaymentDetailsStep customerDetails(CustomerDetails customerDetails);
    }

    public interface PaymentDetailsStep {
        OptionalStep paymentDetails(PaymentDetails paymentDetails);
    }

    public interface OptionalStep {
        OptionalStep cartItems(List<CartItem> cartItems);

        OptionalStep additionalDetails(AdditionalDetails additionalDetails);

        RegistryTransferData build();
    }

    private static class Builder implements PaymentStepId, CustomerDetailsStep, PaymentDetailsStep, OptionalStep {
        private PaymentPurchaseId purchaseId;
        private CustomerDetails customerDetails;
        private PaymentDetails paymentDetails;
        private List<CartItem> cartItems;
        private AdditionalDetails additionalDetails;

        @Override
        public CustomerDetailsStep withPurchaseId(PaymentPurchaseId paymentPurchaseId) {
            this.purchaseId = paymentPurchaseId;
            return this;
        }

        @Override
        public PaymentDetailsStep customerDetails(CustomerDetails customerDetails) {
            this.customerDetails = customerDetails;
            return this;
        }

        @Override
        public OptionalStep paymentDetails(PaymentDetails paymentDetails) {
            this.paymentDetails = paymentDetails;
            return this;
        }

        @Override
        public OptionalStep cartItems(List<CartItem> cartItems) {
            this.cartItems = cartItems;
            return this;
        }

        @Override
        public OptionalStep additionalDetails(AdditionalDetails additionalDetails) {
            this.additionalDetails = additionalDetails;
            return this;
        }

        @Override
        public RegistryTransferData build() {
            return new RegistryTransferData(
                    purchaseId,
                    customerDetails,
                    paymentDetails,
                    cartItems,
                    additionalDetails);
        }
    }
}
