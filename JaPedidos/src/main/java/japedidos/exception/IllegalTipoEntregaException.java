package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalTipoEntregaException extends ComparableException {
    public IllegalTipoEntregaException() {
        super("Tipo de entrega inválida");
    }
    
    public IllegalTipoEntregaException(String message) {
        super(message);
    }
    
    public IllegalTipoEntregaException(String message, Throwable cause) {
        super(message, cause);
    }
}
