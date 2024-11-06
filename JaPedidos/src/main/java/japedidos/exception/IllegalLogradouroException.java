package japedidos.exception;

/**
 *
 * @author t.baiense
 */
public class IllegalLogradouroException extends ComparableException {
    
//    public int order_index = 7;
    
    public IllegalLogradouroException() {
        super("rua inválida");
        super.order_index = 7;
    }
    
    public IllegalLogradouroException(String message) {
        super(message);
        super.order_index = 7;
    }
    
    public IllegalLogradouroException(String message, Throwable cause) {
        super(message, cause);
        super.order_index = 7;
    }
}
