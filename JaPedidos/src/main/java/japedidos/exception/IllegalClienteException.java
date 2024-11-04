package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalClienteException extends ComparableException {
    public IllegalClienteException() {
        super("cliente inválido");
    }
    
    public IllegalClienteException(String message) {
        super(message);
    }
    
    public IllegalClienteException(String message, Throwable cause) {
        super(message, cause);
    }
}
