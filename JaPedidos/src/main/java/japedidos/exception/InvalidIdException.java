package japedidos.exception;

public class InvalidIdException extends Exception {
    public InvalidIdException() {}
    
    public InvalidIdException(String message) {
        super(message);
    }
    
    public InvalidIdException(Throwable cause) {
        super(cause);
    }
    
    public InvalidIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
