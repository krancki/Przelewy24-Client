package pl.doubleByte.przelewy24.models;

import lombok.Builder;
import lombok.Value;

import java.util.Optional;

@Builder
@Value
public class Means {
    Card card;
    ReferenceNumber referenceNumber;
    SchemeToken schemeToken;
    XPayPayload xPayPayload;

    private Means(Card card, ReferenceNumber referenceNumber, SchemeToken schemeToken, XPayPayload xPayPayload) {
        this.card = card;
        this.referenceNumber = referenceNumber;
        this.schemeToken = schemeToken;
        this.xPayPayload = xPayPayload;
    }

    public Optional<Card> getCard() {
        return Optional.ofNullable(card);
    }

    public Optional<ReferenceNumber> getReferenceNumber() {
        return Optional.ofNullable(referenceNumber);
    }

    public Optional<SchemeToken> getSchemeToken() {
        return Optional.ofNullable(schemeToken);
    }

    public Optional<XPayPayload> getXPayPayload() {
        return Optional.ofNullable(xPayPayload);
    }

}
