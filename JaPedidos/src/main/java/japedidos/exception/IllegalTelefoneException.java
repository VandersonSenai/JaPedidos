package japedidos.exception;

/**
 *
 * @author t.baiense
 */
public class IllegalTelefoneException extends ComparableException {
    
    public IllegalTelefoneException() {
        super("telefone inválido");
        super.order_index = 3;
    }
    
    public IllegalTelefoneException(String message) {
        super(message);
        super.order_index = 3;
    }
    
    public IllegalTelefoneException(String message, Throwable cause) {
        super(message, cause);
        super.order_index = 3;
    }
}
