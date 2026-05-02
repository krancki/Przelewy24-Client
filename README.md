## Simply an example of use:
### Create bean of Przelewy24ApiClient and provide Przelewy24Properties


    @Configuration
        class Przelewy24ClientConfiguration {
    
        @Profile(Profiles.REAL_PRZELEWY_CLIENT)
        @Bean
        Przelewy24Properties przelewy24Properties(
                @Value("${przelewy24.crc}") String crc,
                @Value("${przelewy24.reports-key}") String reportsKey,
                @Value("${przelewy24.api-url}") String apiAddress,
                @Value("${przelewy24.account-id}") int merchantId,
                @Value("${przelewy24.account-id}") int posId,
                @Value("${przelewy24.redirect-user-to-url-after-payment}") String redirectUserToUrlAfterPayment,
                @Value("${przelewy24.url-to-update-payment-status}") String urlToUpdatePaymentStatus,
                @Value("${przelewy24.url-to-update-refund-status}") String urlToUpdateRefundStatus
        ) {
            Przelewy24Properties przelewy24Properties = new Przelewy24Properties(
                    crc,
                    reportsKey,
                    apiAddress,
                    MerchantDetails.builder()
                            .merchantId(new MerchantId(merchantId))
                            .posId(new PosId(posId))
                            .build(),
                    redirectUserToUrlAfterPayment,
                    urlToUpdatePaymentStatus,
                    urlToUpdateRefundStatus
            );
            return przelewy24Properties;
        }
    
    
        @Bean
        Przelewy24ApiClient getPrzelewy24Client(Przelewy24Properties przelewy24Properties, ObjectMapper objectMapper, HttpClient httpClient) {
            return new DefaultPrzelewy24ApiApiClient(httpClient, przelewy24Properties, objectMapper);
        }
    
    
        @Profile(Profiles.FAKE_PRZELEWY_CLIENT)
        @Bean
        Przelewy24ApiClient getNotRealPrzelewy24Client() {
            log.warn("Not real implementation of Przelewy24Client has been activated");
            return new InMemoryPrzelewy24ApiClient();
        }
    }

### 2. An example how to use:
    public PaymentToken prepareNewPayment(PurchaseOwnerId purchaseOwnerId, PurchaseId purchaseId, NewTransactionRequest createPurchaseRequest) {

        Purchase purchase = purchaseFinder.getPurchaseById(purchaseOwnerId, purchaseId);  <-- Your domain object
        ClientDetails clientDetails = purchase.getClientDetails();  <-- Your domain object

        RegistryTransferData registryNewTransfer = RegistryTransferData.builder()
                .withPurchaseId(PaymentPurchaseId.of(purchase.getId().getValue().toString()))
                .customerDetails(CustomerDetails.builder()
                        .email(clientDetails.getEMail().getValue())
                        .country("PL")
                        .lang("PL")
                        .phone(clientDetails.getPhoneNumber().getFormattedNumber())
                        .build())
                .paymentDetails(
                        PaymentDetails.builder()
                                .price(purchase.getTotalPrice())
                                .currency("PLN")
                                .transactionDescription("Zakup: " + getDescriptionFromProduct(purchase.getItems()))
                                .method(createPurchaseRequest.paymentMethod())
                                .regulationAccept(createPurchaseRequest.regulationAccepted())
                                .build())
                .build();


        return przelewy24Client.registryNewTransfer(registryNewTransfer);
    }
### 3. An example of application.yml 
    przelewy24:
        crc: crcTest
        reports-key: raportKeyTest
        order-key: orderKey
        account-id: 10000
        api-url: /przelewy24
        redirect-user-to-url-after-payment: "http://localhost:5173"
        url-to-update-payment-status: "http://localhost:5173/public-api/purchase/update/status"
        url-to-update-refund-status: "http://localhost:5173/public-api/purchase/update/refund/status"

## How to run a fake implementation for local run:
    @Profile(Profiles.FAKE_PRZELEWY_CLIENT)
    @Bean
    Przelewy24ApiClient getNotRealPrzelewy24Client() {
        log.warn("Not real implementation of Przelewy24Client has been activated");
        return new InMemoryPrzelewy24ApiClient();
    }


## How to test End to End with WireMock:

    wireMockServer.stubFor(post(urlPathMatching("/przelewy24/api/v1/transaction/register"))
        .withHeader("Content-Type", equalTo("application/json"))
        .withHeader("Authorization", equalTo("Basic YourBasicAuth"))
        .willReturn(aResponse()
        .withStatus(201)
        .withHeader("Content-Type", "application/json")
        .withBody(objectMapper.writeValueAsString(new Przelewy24Response(new Przelewy24Token("UrlTokenPrzelewy"), 200))
        )));
    

    String YourBasicAuth = przelewy24Properties.getMerchantDetails().getMerchantId().getValue() + ":" + przelewy24Properties.getReportsKey();
        return Base64.getEncoder().encodeToString(auth.getBytes());


