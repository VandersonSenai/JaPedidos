package japedidos.exception;

/** Representa uma exceção que informa que um valor de preço inválido foi fornecido
 * como argumento de um método ou construtor.
 *
 * @author thiago
 */

public class IllegalPrecoVendaException extends ComparableException {
    public IllegalPrecoVendaException() {
        super("Preço de venda inválido");
    }
    
    public IllegalPrecoVendaException(String message) {
        super(message);
    }
    
    public IllegalPrecoVendaException(String message, Throwable cause) {
        super(message, cause);
    }
}
