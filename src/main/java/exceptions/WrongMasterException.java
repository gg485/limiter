package exceptions;

public class WrongMasterException extends Exception{
    public WrongMasterException() {
    }

    public WrongMasterException(String message) {
        super(message);
    }
}
