package pl.doubleByte.przelewy24;

record PaymentVerificationResponse(
        PaymentVerificationData data,
        int responseCode
) {

    public record PaymentVerificationData(
            String status
    ) {
    }


    boolean isSuccess()
    {
        return data != null && data.status().equals("success");
    }
}
