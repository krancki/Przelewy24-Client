package pl.doubleByte.przelewy24.models;

import lombok.Value;

@Value
public class PaymentPurchaseId {
    String value;


    public static PaymentPurchaseId of(String value) {
        return new PaymentPurchaseId(value);
    }
}
