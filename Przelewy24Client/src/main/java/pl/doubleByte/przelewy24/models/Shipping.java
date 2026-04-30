package pl.doubleByte.przelewy24.models;

import lombok.Value;

@Value
public class Shipping {
    ShippingType type;
    String address;
    String zip;
    String city;
    String country;

    // Constructor for backward compatibility
    private Shipping(ShippingType type, String address, String zip, String city, String country) {
        this.type = type;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.country = country;
    }

    public static TypeStep builder() {
        return new Builder();
    }

    // Builder interfaces to enforce required fields
    public interface TypeStep {
        AddressStep type(ShippingType type);
    }

    public interface AddressStep {
        ZipStep address(String address);
    }

    public interface ZipStep {
        CityStep zip(String zip);
    }

    public interface CityStep {
        CountryStep city(String city);
    }

    public interface CountryStep {
        BuildStep country(String country);
    }

    public interface BuildStep {
        Shipping build();
    }

    // Concrete builder implementation
    private static class Builder implements TypeStep, AddressStep, ZipStep, CityStep, CountryStep, BuildStep {
        private ShippingType type;
        private String address;
        private String zip;
        private String city;
        private String country;

        @Override
        public AddressStep type(ShippingType type) {
            this.type = type;
            return this;
        }

        @Override
        public ZipStep address(String address) {
            this.address = address;
            return this;
        }

        @Override
        public CityStep zip(String zip) {
            this.zip = zip;
            return this;
        }

        @Override
        public CountryStep city(String city) {
            this.city = city;
            return this;
        }

        @Override
        public BuildStep country(String country) {
            this.country = country;
            return this;
        }

        @Override
        public Shipping build() {
            return new Shipping(type, address, zip, city, country);
        }
    }
}
