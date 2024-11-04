package japedidos.exception;

/** Representa uma exceção que informa que um valor de preço inválido foi fornecido
 * como argumento de um método ou construtor.
 *
 * @author thiago
 */

public class IllegalProdutoException extends ComparableException {
    public IllegalProdutoException() {
        super("Produto inválido");
    }
    
    public IllegalProdutoException(String message) {
        super(message);
    }
    
    public IllegalProdutoException(String message, Throwable cause) {
        super(message, cause);
    }
}
