package japedidos.exception;

/** Representa uma exceção que informa que um valor de preço inválido foi fornecido
 * como argumento de um método ou construtor.
 *
 * @author thiago
 */

public class IllegalPrecoException extends ComparableException {
    public IllegalPrecoException() {
        super("preço inválido");
    }
    
    public IllegalPrecoException(String message) {
        super(message);
    }
    
    public IllegalPrecoException(String message, Throwable cause) {
        super(message, cause);
    }
}
