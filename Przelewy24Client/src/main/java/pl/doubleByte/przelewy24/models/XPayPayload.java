package pl.doubleByte.przelewy24.models;

import lombok.Value;

@Value
public class XPayPayload {
    String payload;
    String type;

    // Constructor for backward compatibility
    private XPayPayload(String payload, String type) {
        this.payload = payload;
        this.type = type;
    }

    public static PayloadStep builder() {
        return new Builder();
    }

    // Builder interfaces to enforce required fields
    public interface PayloadStep {
        TypeStep payload(String payload);
    }

    public interface TypeStep {
        BuildStep type(String type);
    }

    public interface BuildStep {
        XPayPayload build();
    }

    // Concrete builder implementation
    private static class Builder implements PayloadStep, TypeStep, BuildStep {
        private String payload;
        private String type;

        @Override
        public TypeStep payload(String payload) {
            this.payload = payload;
            return this;
        }

        @Override
        public BuildStep type(String type) {
            this.type = type;
            return this;
        }

        @Override
        public XPayPayload build() {
            return new XPayPayload(payload, type);
        }
    }
}
