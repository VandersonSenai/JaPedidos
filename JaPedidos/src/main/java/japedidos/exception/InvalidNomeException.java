package japedidos.exception;

public class InvalidNomeException extends Exception {
    public InvalidNomeException() {}
    
    public InvalidNomeException(String message) {
        super(message);
    }
    
    public InvalidNomeException(Throwable cause) {
        super(cause);
    }
    
    public InvalidNomeException(String message, Throwable cause) {
        super(message, cause);
    }
}
