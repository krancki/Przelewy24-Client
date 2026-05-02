package pl.doubleByte.przelewy24;

import lombok.extern.slf4j.Slf4j;
import pl.doubleByte.przelewy24.facade.*;

import java.util.UUID;

@Slf4j
public class InMemoryPrzelewy24ApiClient implements Przelewy24ApiClient {

    @Override
    public PaymentToken registryNewTransfer(RegistryTransferData registryTransferData) {
        log.info("New transfer registered {} in stubbed repository.(Not real service)", registryTransferData);
        return new PaymentToken(UUID.randomUUID().toString(), "https://przelewy24.pl/token");
    }

    @Override
    public PaymentVerificationStatus verifyPayment(VerificationData verificationData) {
        log.info("Payment verification in stubbed repository.(Not real service)", verificationData);
        return PaymentVerificationStatus.SUCCESS;
    }

    @Override
    public PaymentRefundStatus refundPayment(RefundPaymentData verificationData) {

        log.info("Payment refund in stubbed repository.(Not real service)", verificationData);
        return PaymentRefundStatus.success();
    }
}
