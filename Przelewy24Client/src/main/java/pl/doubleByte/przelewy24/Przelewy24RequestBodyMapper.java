package pl.doubleByte.przelewy24;

import pl.doubleByte.przelewy24.facade.RefundPaymentData;
import pl.doubleByte.przelewy24.facade.RegistryTransferData;
import pl.doubleByte.przelewy24.facade.VerificationData;
import pl.doubleByte.przelewy24.models.*;

import java.math.BigDecimal;
import java.util.Optional;

class Przelewy24RequestBodyMapper {

    private final Przelewy24CheckSumCalculator checkSumCalculator;

    public Przelewy24RequestBodyMapper(Przelewy24CheckSumCalculator checkSumCalculator) {
        this.checkSumCalculator = checkSumCalculator;
    }

    public Przelewy24ReqistryTransactionBody prepareRegistryTransaction(RegistryTransferData data, Przelewy24Properties properties) {
        CustomerDetails customer = data.getCustomerDetails();
        PaymentDetails payment = data.getPaymentDetails();
        MerchantDetails merchant = properties.getMerchantDetails();

        Optional<AdditionalDetails> additionalDetails = data.getAdditionalDetails();

        String sessionId = data.getPurchaseId().getValue();
        int merchantId = merchant.getMerchantId().getValue();
        int posId = merchant.getPosId().getValue();
        int amount = payment.getPrice().multiply(new BigDecimal(100)).intValue();

        String sign = checkSumCalculator.calculateCheckSumForRegistryTransaction(
                sessionId,
                amount,
                payment.getCurrency()
        );

        return Przelewy24ReqistryTransactionBody.builder()
                .merchantId(String.valueOf(merchantId))
                .posId(String.valueOf(posId))
                .sessionId(sessionId)
                .amount(String.valueOf(amount))
                .currency(payment.getCurrency())
                .description(payment.getTransactionDescription())
                .email(customer.getEmail())

                .client(customer.getNameAndSurname().orElse(null))
                .address(customer.getAddress().orElse(null))
                .zip(customer.getZipCode().orElse(null))
                .city(customer.getCity().orElse(null))

                .country(customer.getCountry())
                .phone(customer.getPhone().orElse(null))
                .language(customer.getLang())
                .method(getPaymentMethod(payment.getMethod()))

                .urlReturn(appendSessionIdToUrl(properties.getRedirectUserToUrlAfterPayment(), sessionId))
                .urlStatus(properties.getUrlToUpdatePaymentStatus().orElse(null))

                .timeLimit(payment.getTransactionTimeLimit().orElse(null))
                .channel(payment.getPaymentType().orElse(null))
                .waitForResult(payment.isWaitForResult()) // waitForResult
                .regulationAccept(payment.getRegulationAccept().orElse(false))

                .shipping(payment.getShippingCost().map(BigDecimal::intValue).orElse(null)) // shipping
                .transferLabel(merchant.getTransferLabel().orElse(null))
                .mobileLib(merchant.getMobileLib().orElse(null))
                .sdkVersion(merchant.getSdkVersion().orElse(null))
                .sign(sign)
                .encoding(merchant.getEncoding().orElse(null))
                .methodRefId(merchant.getMethodRefId().orElse(null))
                .cart(data.getCartItems().isPresent() ? data.getCartItems().get().stream().map(this::mapCart).toList() : null)
                .additional(additionalDetails.map(this::prepareAdditional).orElse(null))
                .build();
    }

    private Przelewy24ReqistryTransactionBody.Additional prepareAdditional(AdditionalDetails additionalDetails) {
        Shipping shipping = additionalDetails.getShipping();
        AdditionalDetails.PSU psu = additionalDetails.getPsu();
        return Przelewy24ReqistryTransactionBody.Additional.builder()
                .shipping(Przelewy24ReqistryTransactionBody.Additional.Shipping.builder()
                        .type(mapShippingType(shipping.getType()))
                        .address(shipping.getAddress())
                        .zip(shipping.getZip())
                        .city(shipping.getCity())
                        .country(shipping.getCountry())
                        .build())
                .PSU(Przelewy24ReqistryTransactionBody.Additional.PSU.builder()
                        .IP(psu.getIP())
                        .userAgent(psu.getUserAgent())
                        .build())
                .build();
    }

    private static Integer mapShippingType(ShippingType shippingType) {
        return switch (shippingType) {
            case DELIVERY_MAN -> 0;
            case DELIVERY_TO_POINT -> 1;
            case PARCEL_LOCKER -> 2;
            case PACKAGE_IN_THE_STORE -> 3;
        };
    }

    private Przelewy24ReqistryTransactionBody.CartItem mapCart(CartItem it) {
        return Przelewy24ReqistryTransactionBody.CartItem.builder()
                .sellerId(it.getSellerId())
                .sellerCategory(it.getSellerCategory())
                .name(it.getName().orElse(null))
                .description(it.getDescription().orElse(null))
                .quantity(it.getQuantity().orElse(null))
                .price(it.getPrice().orElse(null))
                .number(it.getNumber().orElse(null))
                .build();
    }


    public int getPaymentMethod(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case PRZELEWY_24 -> 0;
            case BLIK -> 154;
            case PEKAO_S_A -> 65;
            case BANK_MILLENNIUM -> 85;
            case PKO_BP -> 31;
            case BZ_WBK -> 20;
            case ING_BANK_SLASKI -> 112;
            case ALIOR_BANK -> 88;
        };
    }

    private String appendSessionIdToUrl(String url, String sessionId) {
        if (url == null || sessionId == null) {
            return url;
        }

        String separator = url.contains("?") ? "&" : "?";
        return url + separator + "purchaseId=" + sessionId;
    }

    public Przelewy24VerifyPaymentBody preparePaymentVerificationBody(VerificationData verificationData, Przelewy24Properties przelewy24Properties) {

        MerchantDetails merchantDetails = przelewy24Properties.getMerchantDetails();

        String sessionId = verificationData.getPurchaseId();

        int merchantId = merchantDetails.getMerchantId().getValue();
        int posId = merchantDetails.getPosId().getValue();
        int amount = verificationData.getPrice().multiply(new BigDecimal(100)).intValue();
        String sign = checkSumCalculator.calculateCheckSumForVerifyTransaction(
                sessionId,
                verificationData.getOrderId(),
                amount,
                verificationData.getCurrency()
        );

        return new Przelewy24VerifyPaymentBody(
                merchantId,
                posId,
                sessionId,
                amount,
                verificationData.getCurrency(),
                verificationData.getOrderId(),
                sign
        );
    }

    public Przelewy24RefundPaymentBody prepareRefundVerificationBody(RefundPaymentData verificationData, Przelewy24Properties przelewy24Properties) {

        return new Przelewy24RefundPaymentBody(
                verificationData.getRequestId().getValue().toString(),
                verificationData.getRefunds().stream()
                        .map(it -> new Przelewy24RefundPaymentBody.RefundsEntry(
                                it.getOrderId().getValue().toString(),
                                it.getPaymentPurchaseId().getValue(),
                                it.getRefundAmount().multiply(new BigDecimal(100)).intValue(),
                                it.getDescription()))
                        .toList(),
                verificationData.getRefundId().getValue().toString(),
                przelewy24Properties.getUrlToUpdateRefundStatus()
        );
    }
}
