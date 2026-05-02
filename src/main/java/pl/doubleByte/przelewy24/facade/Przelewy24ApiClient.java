package pl.doubleByte.przelewy24.facade;


public interface Przelewy24ApiClient {
    PaymentToken registryNewTransfer(RegistryTransferData registryTransferData);

    PaymentVerificationStatus verifyPayment(VerificationData verificationData);

    PaymentRefundStatus refundPayment(RefundPaymentData verificationData);

}
