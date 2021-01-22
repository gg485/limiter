package exceptions;

public class QuotaResourceException extends Exception{
    public QuotaResourceException() {
    }

    public QuotaResourceException(String message) {
        super(message);
    }
}
