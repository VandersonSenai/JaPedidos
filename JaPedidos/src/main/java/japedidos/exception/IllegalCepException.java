package japedidos.exception;

/**
 *
 * @author t.baiense
 */
public class IllegalCepException extends ComparableException {
    
//    public int order_index = 5;
    
    public IllegalCepException() {
        super("CEP inválido");
        super.order_index = 5;
    }
    
    public IllegalCepException(String message) {
        super(message);
        super.order_index = 5;
    }
    
    public IllegalCepException(String message, Throwable cause) {
        super(message, cause);
        super.order_index = 5;
    }
}
