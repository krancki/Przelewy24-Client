package pl.doubleByte.przelewy24.models;

import lombok.Value;

import java.util.Optional;

@Value
public class Card {
    String pan;
    Integer expYear;
    Integer expMonth;
    String clientName;
    String securityCode;

    // Constructor for backward compatibility
    private Card(String pan, Integer expYear, Integer expMonth, String clientName, String securityCode) {
        this.pan = pan;
        this.expYear = expYear;
        this.expMonth = expMonth;
        this.clientName = clientName;
        this.securityCode = securityCode;
    }

    public Optional<String> getSecurityCode() {
        return Optional.ofNullable(securityCode);
    }

    public static PanStep builder() {
        return new Builder();
    }

    // Builder interfaces to enforce required fields
    public interface PanStep {
        ExpYearStep pan(String pan);
    }

    public interface ExpYearStep {
        ExpMonthStep expYear(Integer expYear);
    }

    public interface ExpMonthStep {
        ClientNameStep expMonth(Integer expMonth);
    }

    public interface ClientNameStep {
        SecurityCodeStep clientName(String clientName);
    }

    public interface SecurityCodeStep extends BuildStep {
        BuildStep securityCode(String securityCode);
    }

    public interface BuildStep {
        Card build();
    }

    // Concrete builder implementation
    private static class Builder implements PanStep, ExpYearStep, ExpMonthStep, ClientNameStep, SecurityCodeStep, BuildStep {
        private String pan;
        private Integer expYear;
        private Integer expMonth;
        private String clientName;
        private String securityCode;

        @Override
        public ExpYearStep pan(String pan) {
            this.pan = pan;
            return this;
        }

        @Override
        public ExpMonthStep expYear(Integer expYear) {
            this.expYear = expYear;
            return this;
        }

        @Override
        public ClientNameStep expMonth(Integer expMonth) {
            this.expMonth = expMonth;
            return this;
        }

        @Override
        public SecurityCodeStep clientName(String clientName) {
            this.clientName = clientName;
            return this;
        }

        @Override
        public BuildStep securityCode(String securityCode) {
            this.securityCode = securityCode;
            return this;
        }

        @Override
        public Card build() {
            return new Card(pan, expYear, expMonth, clientName, securityCode);
        }
    }
}
