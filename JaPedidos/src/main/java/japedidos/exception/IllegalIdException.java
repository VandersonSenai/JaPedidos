package japedidos.exception;

/** Representa uma exceção informa que um {@code id} inválido foi fornecido
 * como argumento a um método ou construtor.
 *
 * @author Thiago M. Baiense
 */
public class IllegalIdException extends ComparableException {
    public IllegalIdException() {
        super("Id inválido");
    }
    
    public IllegalIdException(String message) {
        super(message);
    }
    
    public IllegalIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
