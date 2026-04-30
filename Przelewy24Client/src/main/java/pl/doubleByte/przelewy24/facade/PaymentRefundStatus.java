package pl.doubleByte.przelewy24.facade;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Value
public class PaymentRefundStatus {

    PaymentRefundStatusType status;
    String message;



    public static PaymentRefundStatus success() {
        return new PaymentRefundStatus(PaymentRefundStatusType.SUCCESS, "");
    }

    public static PaymentRefundStatus fail(String message) {
        return new PaymentRefundStatus(PaymentRefundStatusType.FAILURE, message);
    }

    public boolean isFailed() {
        return status.equals(PaymentRefundStatusType.FAILURE);
    }
}
