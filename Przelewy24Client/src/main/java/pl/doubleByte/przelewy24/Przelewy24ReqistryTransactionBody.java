package pl.doubleByte.przelewy24;

import lombok.Builder;

import java.util.List;

@Builder
record Przelewy24ReqistryTransactionBody(
    String merchantId,   //Required
    String posId,   //Required
    String sessionId,   //Required
    String amount,   //Required
    String currency,   //Required
    String description,   //Required
    String email,   //Required


    String client,
    String address,
    String zip,
    String city,

    String country,  //Required

    String phone,

    String language,  //Required

    Integer method,

    String urlReturn,  //Required

    String urlStatus,
    Integer timeLimit,
    Integer channel,
    boolean waitForResult,
    boolean regulationAccept,

    Integer shipping,
    String transferLabel,
    Integer mobileLib,
    String sdkVersion,
    String sign,   //Required
    String encoding,
    String methodRefId,
    List<CartItem> cart,
    Additional additional
) {

    @Builder
    record CartItem(
        String sellerId,
        String sellerCategory,
        String name,
        String description,
        Integer quantity,
        Integer price,
        String number
    ) {}

    @Builder
    record Additional(
        Shipping shipping,
        PSU PSU
    ) {
        @Builder
        record Shipping(
            Integer type,
            String address,
            String zip,
            String city,
            String country
        ) {}
        @Builder
        record PSU(
            String IP,
            String userAgent
        ) {}
    }
}
