package ru.ppxxd.deansoffice.exception;

public class WorkExperienceNotFoundException extends RuntimeException {
    public WorkExperienceNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}