package task6.exceptions;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) { super(message); }
    public ServiceException(String message, Throwable t) { super(message, t); }
}
