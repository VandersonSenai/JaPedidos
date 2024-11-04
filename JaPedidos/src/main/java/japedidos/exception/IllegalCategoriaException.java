package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalCategoriaException extends ComparableException {
    public IllegalCategoriaException() {
        super("Categoria inválida");
    }
    
    public IllegalCategoriaException(String message) {
        super(message);
    }
    
    public IllegalCategoriaException(String message, Throwable cause) {
        super(message, cause);
    }
}
