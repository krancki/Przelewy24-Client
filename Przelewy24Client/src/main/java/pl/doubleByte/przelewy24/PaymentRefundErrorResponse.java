package pl.doubleByte.przelewy24;

import java.util.List;

record PaymentRefundErrorResponse(
        List<PaymentRefundDataResponse> error,
        int code
) {
}
