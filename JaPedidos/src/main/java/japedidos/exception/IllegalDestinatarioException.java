package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalDestinatarioException extends ComparableException {
    public IllegalDestinatarioException() {
        super("Destinatario inválido");
    }
    
    public IllegalDestinatarioException(String message) {
        super(message);
    }
    
    public IllegalDestinatarioException(String message, Throwable cause) {
        super(message, cause);
    }
}
