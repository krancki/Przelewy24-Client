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

## 2. An example how to use:
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
