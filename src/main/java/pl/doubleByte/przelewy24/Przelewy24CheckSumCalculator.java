package pl.doubleByte.przelewy24;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import pl.doubleByte.przelewy24.models.MerchantId;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
class Przelewy24CheckSumCalculator {

    private final ObjectMapper objectMapper;

    private final MerchantId merchantId;
    private final String crcKey;

    @SneakyThrows
    public String calculateCheckSumForRegistryTransaction(String sessionId, int amount, String currency) {
        return calculateChecksum(objectMapper.writeValueAsString(new CheckSumForRegistryTransaction(sessionId, merchantId.getValue(), amount, currency, crcKey)
        ));
    }

    @SneakyThrows
    public String calculateCheckSumForVerifyTransaction(String sessionId, Long orderId, int amount, String currency) {
        return calculateChecksum(objectMapper.writeValueAsString(new CheckSumForVerifyTransaction(sessionId, orderId, amount, currency, crcKey)
        ));
    }


    record CheckSumForRegistryTransaction(
            String sessionId, int merchantId, int amount, String currency, String crc
    ) {
    }

    record CheckSumForVerifyTransaction(
            String sessionId, Long orderId, int amount, String currency, String crc
    ) {
    }

    private String calculateChecksum(String json) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-384").digest(json.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
