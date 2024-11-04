package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalQuantidadeException extends ComparableException {
    public IllegalQuantidadeException() {
        super("Quantidade inválida");
    }
    
    public IllegalQuantidadeException(String message) {
        super(message);
    }
    
    public IllegalQuantidadeException(String message, Throwable cause) {
        super(message, cause);
    }
}
