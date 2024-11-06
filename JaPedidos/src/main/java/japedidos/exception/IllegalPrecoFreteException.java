package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalPrecoFreteException extends ComparableException {
    public IllegalPrecoFreteException() {
        super("Preço de frete inválido");
    }
    
    public IllegalPrecoFreteException(String message) {
        super(message);
    }
    
    public IllegalPrecoFreteException(String message, Throwable cause) {
        super(message, cause);
    }
}
