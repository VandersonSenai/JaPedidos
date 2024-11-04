package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalAlteracaoException extends ComparableException {
    public IllegalAlteracaoException() {
        super("Alteração inválida");
    }
    
    public IllegalAlteracaoException(String message) {
        super(message);
    }
    
    public IllegalAlteracaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
