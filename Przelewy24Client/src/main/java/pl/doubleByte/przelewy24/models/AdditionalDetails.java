package pl.doubleByte.przelewy24.models;

import lombok.Value;


@Value
public class AdditionalDetails {
    Shipping shipping;
    PSU psu;

    @Value
    public static class PSU {
        String IP;
        String userAgent;

    }
}
