package pl.doubleByte.przelewy24;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class Przelewy24PaymentUrlCreator implements PaymentUrlCreator {

    private final Przelewy24Properties przelewy24Properties;

    @Override
    public String createPaymentUrl(String token) {
        return przelewy24Properties.getPayment24Url() + "/trnRequest/" + token;
    }

}
