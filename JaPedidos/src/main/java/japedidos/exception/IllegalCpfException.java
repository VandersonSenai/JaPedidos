package japedidos.exception;

/**
 *
 * @author t.baiense
 */
public class IllegalCpfException extends ComparableException {
    
    public IllegalCpfException() {
        super("CPF inválido");
        super.order_index = 2;
    }
    
    public IllegalCpfException(String message) {
        super(message);
        super.order_index = 2;
    }
    
    public IllegalCpfException(String message, Throwable cause) {
        super(message, cause);
        super.order_index = 2;
    }
}
