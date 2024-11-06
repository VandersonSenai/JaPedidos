package japedidos.exception;

/** Representa uma exceção que informa que um valor de preço inválido foi fornecido
 * como argumento de um método ou construtor.
 *
 * @author thiago
 */

public class IllegalTaxaDescontoException extends ComparableException {
    public IllegalTaxaDescontoException() {
        super("Preço de custo inválido");
    }
    
    public IllegalTaxaDescontoException(String message) {
        super(message);
    }
    
    public IllegalTaxaDescontoException(String message, Throwable cause) {
        super(message, cause);
    }
}
