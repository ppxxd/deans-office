package ru.ppxxd.deansoffice.exception;

public class ChairNotFoundException extends RuntimeException {
    public ChairNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
