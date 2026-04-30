package pl.doubleByte.przelewy24.facade;

import lombok.Value;
import pl.doubleByte.przelewy24.models.PurchaseId;
import pl.doubleByte.przelewy24.models.RefundsId;

import java.math.BigDecimal;

@Value
public class RefundPaymentRequest {

    OrderId orderId;
    PurchaseId purchaseId;
    RefundsId refundsId;
    BigDecimal refundAmount;
}


