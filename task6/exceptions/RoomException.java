package task6.exceptions;

public class RoomException extends RuntimeException {
    public RoomException(String message) { super(message); }
    public RoomException(String message, Throwable t) { super(message, t); }
}
