package japedidos.exception;

/**
 *
 * @author t.baiense
 */
public class IllegalRuaException extends ComparableException {
    
//    public int order_index = 7;
    
    public IllegalRuaException() {
        super("rua inválida");
        super.order_index = 7;
    }
    
    public IllegalRuaException(String message) {
        super(message);
        super.order_index = 7;
    }
    
    public IllegalRuaException(String message, Throwable cause) {
        super(message, cause);
        super.order_index = 7;
    }
}
