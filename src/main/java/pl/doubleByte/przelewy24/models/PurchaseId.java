package pl.doubleByte.przelewy24.models;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Value
public class PurchaseId {
    UUID value;

    public static PurchaseId ofNew() {
        return new PurchaseId(UUID.randomUUID());
    }

    public static PurchaseId of(UUID value) {
        return new PurchaseId(value);
    }
}
