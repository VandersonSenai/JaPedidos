package japedidos.exception;

/**
 *
 * @author t.baiense
 */
public class IlegalProdutoPedidoArrayException extends ComparableException {
    
    public IlegalProdutoPedidoArrayException() {
        super("telefone inválido");
        super.order_index = 3;
    }
    
    public IlegalProdutoPedidoArrayException(String message) {
        super(message);
        super.order_index = 3;
    }
    
    public IlegalProdutoPedidoArrayException(String message, Throwable cause) {
        super(message, cause);
        super.order_index = 3;
    }
}
