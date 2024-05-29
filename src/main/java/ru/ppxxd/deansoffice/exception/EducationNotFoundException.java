package ru.ppxxd.deansoffice.exception;

public class EducationNotFoundException extends RuntimeException {
    public EducationNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}