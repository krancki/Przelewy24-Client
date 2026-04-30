package pl.doubleByte.przelewy24;

import java.util.List;

record PaymentRefundResponse(
        List<PaymentRefundDataResponse> data,
        int responseCode
) {


}
