package pl.doubleByte.przelewy24;

import java.util.List;

 record Przelewy24RefundPaymentBody(
        String requestId,
        List<RefundsEntry> refunds,
        String refundsUuid,
        String urlStatus

) {

    public record RefundsEntry(
            String orderId,
            String sessionId,
            Integer amount,
            String description
    ) {
    }
}
