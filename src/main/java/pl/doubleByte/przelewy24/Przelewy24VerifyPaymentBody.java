package pl.doubleByte.przelewy24;

record Przelewy24VerifyPaymentBody(
        Integer merchantId,
        Integer posId,
        String sessionId,
        Integer amount,
        String currency,
        Long orderId,
        String sign
) {
}
