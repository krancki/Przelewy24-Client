package pl.doubleByte.przelewy24;

record PaymentRefundDataResponse(
        Long orderId,
        String sessionId,
        int amount,
        String description,
        boolean status,
        String message
) {

}
