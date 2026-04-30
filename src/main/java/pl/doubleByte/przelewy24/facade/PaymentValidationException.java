package pl.doubleByte.przelewy24.facade;

import pl.doubleByte.przelewy24.exception.TypedException;

public class PaymentValidationException extends TypedException {
    public PaymentValidationException(String message, PaymentErrorTypes errorType) {
        super(message, errorType);
    }

    public static PaymentValidationException missingRequiredField(String fieldName) {
        return new PaymentValidationException("Missing required field: " + fieldName, PaymentErrorTypes.MISSING_REQUIRED_FIELD);
    }

    public static PaymentValidationException invalidEmail(String email) {
        return new PaymentValidationException("Invalid email address: " + email, PaymentErrorTypes.INVALID_EMAIL);
    }

    public static PaymentValidationException invalidPrice(String price) {
        return new PaymentValidationException("Invalid price: " + price, PaymentErrorTypes.INVALID_PRICE);
    }

    public static PaymentValidationException invalidCurrency(String currency) {
        return new PaymentValidationException("Invalid currency: " + currency, PaymentErrorTypes.INVALID_CURRENCY);
    }

    public static PaymentValidationException invalidPaymentData(String message) {
        return new PaymentValidationException(message, PaymentErrorTypes.INVALID_PAYMENT_DATA);
    }
}
