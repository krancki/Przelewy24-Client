package pl.doubleByte.przelewy24.url;

import pl.doubleByte.przelewy24.facade.PaymentToken;

public interface PaymentUrlCreator {

    RedirectToPaymentUrl createPaymentUrl(PaymentToken paymentToken);
}
