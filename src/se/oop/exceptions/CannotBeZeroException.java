package se.oop.exceptions;

public class CannotBeZeroException extends RuntimeException {
    public CannotBeZeroException() {
        super("This field cannot be zero");
    }

    public CannotBeZeroException(String message) throws RuntimeException {
        super(message);
    }
}

