package exceptions;

public class InvalidAlgoException extends Exception{
    public InvalidAlgoException() {
    }

    public InvalidAlgoException(String message) {
        super(message);
    }
}
