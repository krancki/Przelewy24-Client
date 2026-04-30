package pl.doubleByte.przelewy24;
import lombok.Value;
import pl.doubleByte.przelewy24.models.MerchantDetails;

import java.util.Optional;

@Value
public class Przelewy24Properties {

    String crc;
    String reportsKey;
    String payment24Url;
    MerchantDetails merchantDetails;
    String redirectUserToUrlAfterPayment;
    String urlToUpdatePaymentStatus;
    String urlToUpdateRefundStatus;


    public Optional<String> getUrlToUpdatePaymentStatus() {
        return Optional.ofNullable(urlToUpdatePaymentStatus);
    }
}
