package pl.doubleByte.przelewy24;

interface PaymentUrlCreator {

    String createPaymentUrl(String token);
}
