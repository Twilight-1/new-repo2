package task6.exceptions;

public class ConfigException extends RuntimeException {
    public ConfigException(String message) { super(message); }
    public ConfigException(String message, Throwable t) { super(message, t); }
}
