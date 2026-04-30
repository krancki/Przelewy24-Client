package pl.doubleByte.przelewy24.models;

import lombok.Value;

@Value
public class PaymentPurchaseId {
    String value;


    public static PaymentPurchaseId of(PurchaseId purchaseId) {
        return new PaymentPurchaseId(purchaseId.getValue().toString());
    }
}
