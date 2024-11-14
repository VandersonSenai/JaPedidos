package japedidos.exception;

/**
 *
 * @author t.baiense
 */
public class IllegalCnpjException extends ComparableException {
    
    public IllegalCnpjException() {
        super("CNPJ inválido");
        super.order_index = 2;
    }
    
    public IllegalCnpjException(String message) {
        super(message);
        super.order_index = 2;
    }
    
    public IllegalCnpjException(String message, Throwable cause) {
        super(message, cause);
        super.order_index = 2;
    }
}
