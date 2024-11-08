package japedidos.exception;

/**
 *
 * @author t.baiense
 */
public class IllegalPaisException extends ComparableException {
    
//    public int order_index = 5;
    
    public IllegalPaisException() {
        super("Pais inválido");
        super.order_index = 5;
    }
    
    public IllegalPaisException(String message) {
        super(message);
        super.order_index = 5;
    }
    
    public IllegalPaisException(String message, Throwable cause) {
        super(message, cause);
        super.order_index = 5;
    }
}
