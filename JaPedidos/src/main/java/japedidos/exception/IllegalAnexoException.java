package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalAnexoException extends ComparableException {
    public IllegalAnexoException() {
        super("anexo inválido");
    }
    
    public IllegalAnexoException(String message) {
        super(message);
    }
    
    public IllegalAnexoException(String message, Throwable cause) {
        super(message, cause);
    }
}
