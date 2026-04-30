package pl.doubleByte.przelewy24.configuration;


import lombok.extern.slf4j.Slf4j;
import pl.doubleByte.przelewy24.*;
import pl.doubleByte.przelewy24.models.MerchantId;

@Slf4j
//@Configuration
class Przelewy24ClientConfiguration {

//    @Profile(Profiles.REAL_PRZELEWY_CLIENT)
//    @Bean
//    Przelewy24Properties przelewy24Properties(
//            @Value("${przelewy24.crc}") String crc,
//            @Value("${przelewy24.reports-key}") String reportsKey,
//            @Value("${przelewy24.api-url}") String apiAddress,
//            @Value("${przelewy24.account-id}") int merchantId,
//            @Value("${przelewy24.account-id}") int posId,
//            @Value("${przelewy24.redirect-user-to-url-after-payment}") String redirectUserToUrlAfterPayment,
//            @Value("${przelewy24.url-to-update-payment-status}") String urlToUpdatePaymentStatus,
//            @Value("${przelewy24.url-to-update-refund-status}") String urlToUpdateRefundStatus
//    ) {
//        return new Przelewy24Properties(
//                crc,
//                reportsKey,
//                apiAddress,
//                MerchantDetails.builder()
//                        .merchantId(new MerchantId(merchantId))
//                        .posId(new PosId(posId))
//                        .build(),
//                redirectUserToUrlAfterPayment,
//                urlToUpdatePaymentStatus,
//                urlToUpdateRefundStatus
//        );
//    }
//
//    @Bean
//    PaymentUrlCreator getPrzelewy24UrlCreator(Przelewy24Properties przelewy24Properties) {
//        return new Przelewy24PaymentUrlCreator(przelewy24Properties);
//    }
//
//
//    @Bean
//    Przelewy24Client getPrzelewy24Client(Przelewy24Properties przelewy24Properties, ObjectMapper objectMapper, Przelewy24RequestBodyMapper przelewy24RequestBodyMapper) {
//        return new Przelewy24ApiClient(przelewy24Properties, objectMapper, przelewy24RequestBodyMapper);
//    }
//
//    @Profile(Profiles.FAKE_PRZELEWY_CLIENT)
//    @Bean
//    PaymentUrlCreator getFakeUrlCreator() {
//        return new FakePrzelewy24PaymentUrlCreator();
//    }
//
//    @Profile(Profiles.FAKE_PRZELEWY_CLIENT)
//    @Bean
//    Przelewy24Client getNotRealPrzelewy24Client() {
//        log.warn("Not real implementation of Przelewy24Client has been activated");
//        return new InMemoryPrzelewy24Client();
//    }
//
//    @Bean
//    Przelewy24CheckSumCalculator getCheckSumCalculator(Przelewy24Properties przelewy24Properties, ObjectMapper objectMapper) {
//        return new Przelewy24CheckSumCalculator(
//                objectMapper,
//                przelewy24Properties.getMerchantDetails().getMerchantId(),
//                przelewy24Properties.getCrc()
//        );
//    }
//
//    @Bean
//    Przelewy24RequestBodyMapper getPrzelewy24RequestBodyMapper(
//
//            Przelewy24CheckSumCalculator przelewy24CheckSumCalculator
//    ) {
//        return new Przelewy24RequestBodyMapper(przelewy24CheckSumCalculator);
//    }
}
