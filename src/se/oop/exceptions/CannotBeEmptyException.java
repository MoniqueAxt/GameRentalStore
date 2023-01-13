package se.oop.exceptions;

public class CannotBeEmptyException extends RuntimeException {
    public CannotBeEmptyException() {
        super("This field cannot be empty");
    }

    public CannotBeEmptyException(String message) throws RuntimeException {
        super(message);
    }
}

