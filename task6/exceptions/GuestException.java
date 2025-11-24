package task6.exceptions;

public class GuestException extends RuntimeException {
    public GuestException(String message) { super(message); }
    public GuestException(String message, Throwable t) { super(message, t); }
}
