package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalInfoEntregaException extends ComparableException {
    public IllegalInfoEntregaException() {
        super("Informação de entrega inválida");
    }
    
    public IllegalInfoEntregaException(String message) {
        super(message);
    }
    
    public IllegalInfoEntregaException(String message, Throwable cause) {
        super(message, cause);
    }
}
