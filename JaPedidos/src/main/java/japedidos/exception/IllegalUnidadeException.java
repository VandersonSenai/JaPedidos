package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalUnidadeException extends ComparableException {
    public IllegalUnidadeException() {
        super("Unidade inválida");
    }
    
    public IllegalUnidadeException(String message) {
        super(message);
    }
    
    public IllegalUnidadeException(String message, Throwable cause) {
        super(message, cause);
    }
}
