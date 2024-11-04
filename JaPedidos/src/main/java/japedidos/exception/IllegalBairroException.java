package japedidos.exception;

/**
 *
 * @author t.baiense
 */
public class IllegalBairroException extends ComparableException {
    
    public IllegalBairroException() {
        super("bairro inválido");
        super.order_index = 8;
    }
    
    public IllegalBairroException(String message) {
        super(message);
        super.order_index = 8;
    }
    
    public IllegalBairroException(String message, Throwable cause) {
        super(message, cause);
        super.order_index = 8;
    }
}
