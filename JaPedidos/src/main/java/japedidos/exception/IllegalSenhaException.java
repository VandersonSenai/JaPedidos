package japedidos.exception;

/**
 *
 * @author t.baiense
 */
public class IllegalSenhaException extends ComparableException {
    public IllegalSenhaException() {
        super("senha inválida");
    }
    
    public IllegalSenhaException(String message) {
        super(message);
    }
    
    public IllegalSenhaException(String message, Throwable cause) {
        super(message, cause);
    }
}
