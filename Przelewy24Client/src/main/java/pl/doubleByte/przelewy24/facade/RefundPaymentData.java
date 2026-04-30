package pl.doubleByte.przelewy24.facade;

import lombok.Value;
import pl.doubleByte.przelewy24.models.PaymentPurchaseId;
import pl.doubleByte.przelewy24.models.RefundsId;

import java.math.BigDecimal;
import java.util.List;

@Value
public class RefundPaymentData {
    RefundRequestId requestId;
    RefundsId refundId;
    List<RefundCartItem> refunds;


    @Value
    public static class RefundCartItem
    {
        OrderId orderId;
        PaymentPurchaseId paymentPurchaseId;
        BigDecimal refundAmount;
        String description;

    }

}

