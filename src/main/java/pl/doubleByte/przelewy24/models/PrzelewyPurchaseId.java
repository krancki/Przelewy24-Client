package pl.doubleByte.przelewy24.models;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Value
public class PrzelewyPurchaseId {
    String value;

    public static PrzelewyPurchaseId of(String value) {
        return new PrzelewyPurchaseId(value);
    }
}
