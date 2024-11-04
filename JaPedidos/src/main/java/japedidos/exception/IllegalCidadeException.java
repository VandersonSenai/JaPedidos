package japedidos.exception;

/**
 *
 * @author t.baiense
 */
public class IllegalCidadeException extends ComparableException {
    
    public IllegalCidadeException() {
        super("cidade inválida");
        super.order_index = 9;
    }
    
    public IllegalCidadeException(String message) {
        super(message);
        super.order_index = 9;
    }
    
    public IllegalCidadeException(String message, Throwable cause) {
        super(message, cause);
    }
}
