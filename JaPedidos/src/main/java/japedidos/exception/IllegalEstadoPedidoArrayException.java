package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalEstadoPedidoArrayException extends ComparableException {
    public IllegalEstadoPedidoArrayException() {
        super("Estados de pedido inválido");
    }
    
    public IllegalEstadoPedidoArrayException(String message) {
        super(message);
    }
    
    public IllegalEstadoPedidoArrayException(String message, Throwable cause) {
        super(message, cause);
    }
}
