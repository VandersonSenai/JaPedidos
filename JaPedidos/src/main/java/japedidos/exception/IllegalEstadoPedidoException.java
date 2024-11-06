package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalEstadoPedidoException extends ComparableException {
    public IllegalEstadoPedidoException() {
        super("Estado de pedido inválido");
    }
    
    public IllegalEstadoPedidoException(String message) {
        super(message);
    }
    
    public IllegalEstadoPedidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
