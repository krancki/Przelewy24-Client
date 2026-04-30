package pl.doubleByte.przelewy24.models;

import lombok.Value;

import java.util.Optional;

@Value
public class ReferenceNumber {
    String id;
    String securityCode;

    // Constructor for backward compatibility
    private ReferenceNumber(String id, String securityCode) {
        this.id = id;
        this.securityCode = securityCode;
    }

    public Optional<String> getSecurityCode() {
        return Optional.ofNullable(securityCode);
    }

    public static IdStep builder() {
        return new Builder();
    }

    // Builder interfaces to enforce required fields
    public interface IdStep {
        BuildStep id(String id);
    }

    public interface BuildStep {
        BuildStep securityCode(String securityCode);

        ReferenceNumber build();
    }

    // Concrete builder implementation
    private static class Builder implements IdStep, BuildStep {
        private String id;
        private String securityCode;

        @Override
        public BuildStep id(String id) {
            this.id = id;
            return this;
        }

        @Override
        public BuildStep securityCode(String securityCode) {
            this.securityCode = securityCode;
            return this;
        }

        @Override
        public ReferenceNumber build() {
            return new ReferenceNumber(id, securityCode);
        }
    }
}
