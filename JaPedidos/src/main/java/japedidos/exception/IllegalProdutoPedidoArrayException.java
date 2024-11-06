package japedidos.exception;

/** Representa uma exceção que informa que um valor de preço inválido foi fornecido
 * como argumento de um método ou construtor.
 *
 * @author thiago
 */

public class IllegalProdutoPedidoArrayException extends ComparableException {
    public IllegalProdutoPedidoArrayException() {
        super("Produtos de pedido inválidos");
    }
    
    public IllegalProdutoPedidoArrayException(String message) {
        super(message);
    }
    
    public IllegalProdutoPedidoArrayException(String message, Throwable cause) {
        super(message, cause);
    }
}
