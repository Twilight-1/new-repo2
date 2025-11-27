package task6.exceptions;

public class StorageException extends RuntimeException {
    public StorageException(String message) { super(message); }
    public StorageException(String message, Throwable t) { super(message, t); }
}
