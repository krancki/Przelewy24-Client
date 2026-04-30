package pl.doubleByte.przelewy24.models;

import lombok.Value;

import java.util.Optional;

@Value
public class CustomerDetails {
    String email;
    String country;
    String lang;

    String nameAndSurname;
    String address;
    String zipCode;
    String city;
    String phone;

    // Constructor for backward compatibility
    private CustomerDetails(String email, String country, String lang, String nameAndSurname, String address, String zipCode, String city, String phone) {
        this.email = email;
        this.country = country;
        this.lang = lang;
        this.nameAndSurname = nameAndSurname;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.phone = phone;
    }

    public Optional<String> getNameAndSurname() {
        return Optional.ofNullable(nameAndSurname);
    }


    public Optional<String> getAddress() {
        return Optional.ofNullable(address);
    }


    public Optional<String> getZipCode() {
        return Optional.ofNullable(zipCode);
    }


    public Optional<String> getCity() {
        return Optional.ofNullable(city);
    }

    public Optional<String> getPhone() {
        return Optional.ofNullable(phone);
    }


    public static EmailStep builder() {
        return new Builder();
    }

    // Builder interfaces to enforce required fields
    public interface EmailStep {
        CountryStep email(String email);
    }

    public interface CountryStep {
        LangStep country(String country);
    }

    public interface LangStep {
        OptionalStep lang(String lang);
    }

    public interface OptionalStep {
        OptionalStep nameAndSurname(String nameAndSurname);
        OptionalStep address(String address);
        OptionalStep zipCode(String zipCode);
        OptionalStep city(String city);
        OptionalStep phone(String phone);
        CustomerDetails build();
    }

    // Concrete builder implementation
    private static class Builder implements EmailStep, CountryStep, LangStep, OptionalStep {
        private String email;
        private String country;
        private String lang;
        private String nameAndSurname;
        private String address;
        private String zipCode;
        private String city;
        private String phone;

        @Override
        public CountryStep email(String email) {
            this.email = email;
            return this;
        }

        @Override
        public LangStep country(String country) {
            this.country = country;
            return this;
        }

        @Override
        public OptionalStep lang(String lang) {
            this.lang = lang;
            return this;
        }

        @Override
        public OptionalStep nameAndSurname(String nameAndSurname) {
            this.nameAndSurname = nameAndSurname;
            return this;
        }

        @Override
        public OptionalStep address(String address) {
            this.address = address;
            return this;
        }

        @Override
        public OptionalStep zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        @Override
        public OptionalStep city(String city) {
            this.city = city;
            return this;
        }

        @Override
        public OptionalStep phone(String phone) {
            this.phone = phone;
            return this;
        }

        @Override
        public CustomerDetails build() {
            return new CustomerDetails(email, country, lang, nameAndSurname, address, zipCode, city, phone);
        }
    }
}
