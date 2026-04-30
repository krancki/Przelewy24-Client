package pl.doubleByte.przelewy24.models;

import lombok.Value;

import java.util.Optional;

@Value
public class MerchantDetails {
    MerchantId merchantId;
    PosId posId;

    String encoding;
    String methodRefId;
    String transferLabel;
    Integer mobileLib;
    String sdkVersion;

    private MerchantDetails(MerchantId merchantId, PosId posId,
                            String encoding, String methodRefId, String transferLabel,
                            Integer mobileLib, String sdkVersion) {
        this.merchantId = merchantId;
        this.posId = posId;
        this.encoding = encoding;
        this.methodRefId = methodRefId;
        this.transferLabel = transferLabel;
        this.mobileLib = mobileLib;
        this.sdkVersion = sdkVersion;
    }

    public Optional<String> getEncoding() {
        return Optional.ofNullable(encoding);
    }

    public Optional<String> getMethodRefId() {
        return Optional.ofNullable(methodRefId);
    }

    public Optional<String> getTransferLabel() {
        return Optional.ofNullable(transferLabel);
    }

    public Optional<Integer> getMobileLib() {
        return Optional.ofNullable(mobileLib);
    }

    public Optional<String> getSdkVersion() {
        return Optional.ofNullable(sdkVersion);
    }

    public static MerchantIdStep builder() {
        return new Builder();
    }

    // Builder interfaces to enforce required fields
    public interface MerchantIdStep {
        PosIdStep merchantId(MerchantId merchantId);
    }

    public interface PosIdStep {
        OptionalStep posId(PosId posId);
    }

    public interface OptionalStep {
        OptionalStep encoding(String encoding);

        OptionalStep methodRefId(String methodRefId);

        OptionalStep transferLabel(String transferLabel);

        OptionalStep mobileLib(Integer mobileLib);

        OptionalStep sdkVersion(String sdkVersion);

        MerchantDetails build();
    }

    // Concrete builder implementation
    private static class Builder implements MerchantIdStep, PosIdStep,  OptionalStep {
        private MerchantId merchantId;
        private PosId posId;
        private String encoding;
        private String methodRefId;
        private String transferLabel;
        private Integer mobileLib;
        private String sdkVersion;

        @Override
        public PosIdStep merchantId(MerchantId merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        @Override
        public OptionalStep posId(PosId posId) {
            this.posId = posId;
            return this;
        }

        @Override
        public OptionalStep encoding(String encoding) {
            this.encoding = encoding;
            return this;
        }

        @Override
        public OptionalStep methodRefId(String methodRefId) {
            this.methodRefId = methodRefId;
            return this;
        }

        @Override
        public OptionalStep transferLabel(String transferLabel) {
            this.transferLabel = transferLabel;
            return this;
        }

        @Override
        public OptionalStep mobileLib(Integer mobileLib) {
            this.mobileLib = mobileLib;
            return this;
        }

        @Override
        public OptionalStep sdkVersion(String sdkVersion) {
            this.sdkVersion = sdkVersion;
            return this;
        }

        @Override
        public MerchantDetails build() {
            return new MerchantDetails(merchantId, posId,
                    encoding, methodRefId, transferLabel,
                    mobileLib, sdkVersion);
        }
    }

}
