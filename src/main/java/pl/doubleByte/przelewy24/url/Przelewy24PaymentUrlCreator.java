package pl.doubleByte.przelewy24.url;

import lombok.RequiredArgsConstructor;
import pl.doubleByte.przelewy24.Przelewy24Properties;
import pl.doubleByte.przelewy24.facade.PaymentToken;

@RequiredArgsConstructor
public class Przelewy24PaymentUrlCreator implements PaymentUrlCreator {

    private final Przelewy24Properties przelewy24Properties;

    @Override
    public RedirectToPaymentUrl createPaymentUrl(PaymentToken paymentToken) {
        return new RedirectToPaymentUrl(przelewy24Properties.getPayment24Url() + "/trnRequest/" + paymentToken.getValue());
    }


}
