package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalInfoAdicionalProdutoException extends ComparableException {
    public IllegalInfoAdicionalProdutoException() {
        super("Informação adicional de produto inválida");
    }
    
    public IllegalInfoAdicionalProdutoException(String message) {
        super(message);
    }
    
    public IllegalInfoAdicionalProdutoException(String message, Throwable cause) {
        super(message, cause);
    }
}
