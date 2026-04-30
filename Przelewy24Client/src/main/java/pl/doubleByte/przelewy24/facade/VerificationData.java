package pl.doubleByte.przelewy24.facade;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class VerificationData {

    String purchaseId;
    BigDecimal price;
    String currency;
    Long orderId;

}
