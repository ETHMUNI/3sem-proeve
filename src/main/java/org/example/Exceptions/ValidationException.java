package org.example.Exceptions;

public class ValidationException extends RuntimeException {

    private final int statusCode;

    public ValidationException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public static ValidationException create(int statusCode, String message) {
        return new ValidationException(statusCode, message);
    }

    public static ValidationException create(String message) {
        return create(400, message);
    }
}
