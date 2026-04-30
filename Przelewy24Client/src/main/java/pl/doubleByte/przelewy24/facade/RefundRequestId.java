package pl.doubleByte.przelewy24.facade;

import lombok.Value;

import java.util.UUID;

@Value
public class RefundRequestId {
    UUID value;

    public static RefundRequestId of(UUID value) {
        return new RefundRequestId(value);
    }

    public static RefundRequestId ofNew() {
        return new RefundRequestId(UUID.randomUUID());
    }
}
