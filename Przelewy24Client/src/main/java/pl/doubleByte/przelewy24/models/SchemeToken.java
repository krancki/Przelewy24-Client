package pl.doubleByte.przelewy24.models;

import lombok.Value;

import java.util.Optional;

@Value
public class SchemeToken {
    String pan;
    Integer expYear;
    Integer expMonth;
    String securityCode;
    String eci;
    String type;

    // Constructor for backward compatibility
    private SchemeToken(String pan, Integer expYear, Integer expMonth, String securityCode, String eci, String type) {
        this.pan = pan;
        this.expYear = expYear;
        this.expMonth = expMonth;
        this.securityCode = securityCode;
        this.eci = eci;
        this.type = type;
    }

    public Optional<String> getSecurityCode() {
        return Optional.ofNullable(securityCode);
    }

    public Optional<String> getEci() {
        return Optional.ofNullable(eci);
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
        TypeStep expMonth(Integer expMonth);
    }

    public interface TypeStep {
        OptionalStep type(String type);
    }

    public interface OptionalStep {
        OptionalStep securityCode(String securityCode);

        OptionalStep eci(String eci);

        SchemeToken build();
    }

    // Concrete builder implementation
    private static class Builder implements PanStep, ExpYearStep, ExpMonthStep, TypeStep, OptionalStep {
        private String pan;
        private Integer expYear;
        private Integer expMonth;
        private String securityCode;
        private String eci;
        private String type;

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
        public TypeStep expMonth(Integer expMonth) {
            this.expMonth = expMonth;
            return this;
        }

        @Override
        public OptionalStep type(String type) {
            this.type = type;
            return this;
        }

        @Override
        public OptionalStep securityCode(String securityCode) {
            this.securityCode = securityCode;
            return this;
        }

        @Override
        public OptionalStep eci(String eci) {
            this.eci = eci;
            return this;
        }

        @Override
        public SchemeToken build() {
            return new SchemeToken(pan, expYear, expMonth, securityCode, eci, type);
        }
    }
}
