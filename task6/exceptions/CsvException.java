package task6.exceptions;

public class CsvException extends RuntimeException {
    public CsvException(String message) { super(message); }
    public CsvException(String message, Throwable t) { super(message, t); }
}
