package japedidos.exception;

public class IllegalIdException extends Exception {
    public IllegalIdException() {}
    
    public IllegalIdException(String message) {
        super(message);
    }
    
    public IllegalIdException(Throwable cause) {
        super(cause);
    }
    
    public IllegalIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
