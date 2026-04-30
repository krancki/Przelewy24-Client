package pl.doubleByte.przelewy24;

import lombok.extern.slf4j.Slf4j;
import pl.doubleByte.przelewy24.facade.*;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;
import java.util.stream.Collectors;

@Slf4j
class Przelewy24ApiClient implements Przelewy24Client {

    private static final String API_REGISTRY_ENDPOINT = "/api/v1/transaction/register";
    private static final String API_VERIFY_ENDPOINT = "/api/v1/transaction/verify";
    private static final String API_REFUND_ENDPOINT = "/api/v1/transaction/refund";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Przelewy24Properties przelewy24Properties;
    private final Przelewy24RequestBodyMapper przelewy24RequestBodyMapper;

    public Przelewy24ApiClient(
            HttpClient httpClient,
            Przelewy24Properties przelewy24Properties,
                               ObjectMapper objectMapper
    ) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.przelewy24Properties = przelewy24Properties;
        this.przelewy24RequestBodyMapper = new Przelewy24RequestBodyMapper(new Przelewy24CheckSumCalculator(
                objectMapper,
                przelewy24Properties.getMerchantDetails().getMerchantId(),
                przelewy24Properties.getCrc()
        ));
    }

    @Override
    public PaymentToken registryNewTransfer(RegistryTransferData registryTransferData) {
        try {
            Przelewy24ReqistryTransactionBody requestBody = przelewy24RequestBodyMapper.prepareRegistryTransaction(registryTransferData, przelewy24Properties);

            String fullUrl = przelewy24Properties.getPayment24Url() + API_REGISTRY_ENDPOINT;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " + prepareAuth())
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new RuntimeException("Failed to register transaction with Przelewy24: " + response.body());
            }

            Przelewy24RegistryTransactionResponse paymentToken = objectMapper.readValue(response.body(), Przelewy24RegistryTransactionResponse.class);

            return new PaymentToken(paymentToken.getTokenValue());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error while communicating with Przelewy24 API", e);
        }

    }

    @Override
    public PaymentVerificationStatus verifyPayment(VerificationData verificationData) {

        try {
            Przelewy24VerifyPaymentBody requestBody = przelewy24RequestBodyMapper.preparePaymentVerificationBody(verificationData, przelewy24Properties);

            String fullUrl = przelewy24Properties.getPayment24Url() + API_VERIFY_ENDPOINT;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " + prepareAuth())
                    .PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            PaymentVerificationResponse paymentToken = objectMapper.readValue(response.body(), PaymentVerificationResponse.class);


            if (paymentToken.isSuccess()) {
                return PaymentVerificationStatus.SUCCESS;
            }

            return PaymentVerificationStatus.FAILURE;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error while communicating with Przelewy24 API", e);
        }
    }

    @Override
    public PaymentRefundStatus refundPayment(RefundPaymentData verificationData) {

        try {
            Przelewy24RefundPaymentBody requestBody = przelewy24RequestBodyMapper.prepareRefundVerificationBody(verificationData, przelewy24Properties);

            String fullUrl = przelewy24Properties.getPayment24Url() + API_REFUND_ENDPOINT;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " + prepareAuth())
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());


            if (response.statusCode() == 201) {
                PaymentRefundResponse paymentRefundResponse = objectMapper.readValue(response.body(), PaymentRefundResponse.class);

                boolean isSuccess = paymentRefundResponse.data().stream()
                        .allMatch(PaymentRefundDataResponse::status);
                if (isSuccess) {
                    return PaymentRefundStatus.success();
                } else {

                    return PaymentRefundStatus.fail(paymentRefundResponse.data().stream()
                            .map(PaymentRefundDataResponse::message)
                            .collect(Collectors.joining(", ")));
                }

            }

            if (response.statusCode() == 409) {
                PaymentRefundErrorResponse paymentRefundErrorResponse = objectMapper.readValue(response.body(), PaymentRefundErrorResponse.class);
                log.atError().log("Refund failed. Reason: {}", response.body());

                return PaymentRefundStatus.fail(paymentRefundErrorResponse.error().stream()
                        .map(PaymentRefundDataResponse::message)
                        .collect(Collectors.joining(",")));
            }
            log.atError().log("Refund failed. Reason: {}", response.body());
            return PaymentRefundStatus.fail("Refund failed. Check logs");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error while communicating with Przelewy24 API", e);
        }
    }

    private String prepareAuth() {
        String auth = przelewy24Properties.getMerchantDetails().getMerchantId().getValue() + ":" + przelewy24Properties.getReportsKey();
        return Base64.getEncoder().encodeToString(auth.getBytes());
    }

    private record Przelewy24RegistryTransactionResponse(Przelewy24Token data, int responseCode) {

        public String getTokenValue() {
            return data.token();
        }
    }

    private record Przelewy24Token(String token) {
    }
}
