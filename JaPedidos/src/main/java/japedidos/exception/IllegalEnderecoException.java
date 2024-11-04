package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalEnderecoException extends ComparableException {
    
    public int order_index = 9;
    
    public IllegalEnderecoException() {
        super("endereço inválido");
        super.order_index = 9;
    }
    
    public IllegalEnderecoException(String message) {
        super(message);
        super.order_index = 9;
    }
    
    public IllegalEnderecoException(String message, Throwable cause) {
        super(message, cause);
        super.order_index = 9;
    }
}
