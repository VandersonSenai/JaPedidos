package japedidos.exception;

/** Representa uma exceção que informa que um valor de preço inválido foi fornecido
 * como argumento de um método ou construtor.
 *
 * @author thiago
 */

public class IllegalPrecoCustoException extends ComparableException {
    public IllegalPrecoCustoException() {
        super("Preço de custo inválido");
    }
    
    public IllegalPrecoCustoException(String message) {
        super(message);
    }
    
    public IllegalPrecoCustoException(String message, Throwable cause) {
        super(message, cause);
    }
}
