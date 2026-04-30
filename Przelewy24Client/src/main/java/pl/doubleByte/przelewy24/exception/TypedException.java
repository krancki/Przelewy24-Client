package pl.doubleByte.przelewy24.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class TypedException extends RuntimeException{
    private final Enum<?> errorType;

    public TypedException(String message, Enum<?> errorType) {
        super(message);
        this.errorType = errorType;
    }
}
