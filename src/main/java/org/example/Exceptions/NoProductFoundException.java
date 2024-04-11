package org.example.Exceptions;


public class NoProductFoundException extends RuntimeException {

    private final int statusCode;

    public NoProductFoundException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public static NoProductFoundException create(int statusCode, String message) {
        return new NoProductFoundException(statusCode, message);
    }

    public static NoProductFoundException create(String message) {
        return create(404, message);
    }
}
