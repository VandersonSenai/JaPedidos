package japedidos.exception;

/**
 *
 * @author t.baiense
 */
public class IllegalEstadoException extends ComparableException {
    
    public IllegalEstadoException() {
        super("Estado inválido");
        super.order_index = 8;
    }
    
    public IllegalEstadoException(String message) {
        super(message);
        super.order_index = 8;
    }
    
    public IllegalEstadoException(String message, Throwable cause) {
        super(message, cause);
        super.order_index = 8;
    }
}
