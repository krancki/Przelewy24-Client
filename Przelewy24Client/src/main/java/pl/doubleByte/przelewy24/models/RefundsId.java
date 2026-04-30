package pl.doubleByte.przelewy24.models;

import lombok.Value;

import java.util.UUID;

@Value
public class RefundsId {
    UUID value;

    public static RefundsId of(UUID value) {
        return new RefundsId(value);
    }
    public static RefundsId ofNew() {
        return new RefundsId(UUID.randomUUID());
    }

}
