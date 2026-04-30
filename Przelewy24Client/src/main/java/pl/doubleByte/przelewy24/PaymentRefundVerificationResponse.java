package pl.doubleByte.przelewy24;

import java.util.List;

record PaymentRefundVerificationResponse(
        List<RefundVerificationData> data,
        String responseCode
) {


    record RefundVerificationData(
            Long orderId,
            String sessionId,
            Integer amount,
            String currency,
            List<Refunds> refunds
    ) {
    }

    record Refunds(
            Integer batchId,
            String requestId,
            String date,
            String login,
            String description,
            Integer status,
            Integer amount
    ) {

        RefundStatus refundStatus() {
            return switch (status)
            {
                case 1->  RefundStatus.REFUNDED;
                case 2->  RefundStatus.AWAITING_EXECUTION;
                case 3->  RefundStatus.AWAITING_APPROVAL;
                case 4->  RefundStatus.REFUNDED;
                default -> RefundStatus.REJECTED;
            };
        }

        enum RefundStatus {
            REFUNDED,
            AWAITING_EXECUTION,
            AWAITING_APPROVAL,
            REJECTED
        }

    }
}
