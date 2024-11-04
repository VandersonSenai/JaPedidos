package japedidos.exception;

/**
 *
 * @author t.baiense
 */
public class IllegalNumeroException extends ComparableException {
    
    public IllegalNumeroException() {
        super("número inválido");
        super.order_index = 6;
    }
    
    public IllegalNumeroException(String message) {
        super(message);
        super.order_index = 6;
    }
    
    public IllegalNumeroException(String message, Throwable cause) {
        super(message, cause);
        super.order_index = 6;
    }
}
