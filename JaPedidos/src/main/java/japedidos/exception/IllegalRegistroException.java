package japedidos.exception;

/** Representa uma exceção que informa que um valor de preço inválido foi fornecido
 * como argumento de um método ou construtor.
 *
 * @author thiago
 */

public class IllegalRegistroException extends ComparableException {
    public IllegalRegistroException() {
        super("Registro inválido");
    }
    
    public IllegalRegistroException(String message) {
        super(message);
    }
    
    public IllegalRegistroException(String message, Throwable cause) {
        super(message, cause);
    }
}
