package se.oop.exceptions;

public class ReturnDateException extends RuntimeException {
    public ReturnDateException() {
        super("Return date invalid.");
    }

    public ReturnDateException(String message) throws RuntimeException {
        super(message);
    }
}
