package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalInfoCancelamentoException extends ComparableException {
    public IllegalInfoCancelamentoException() {
        super("Informação de cancelamento inválida");
    }
    
    public IllegalInfoCancelamentoException(String message) {
        super(message);
    }
    
    public IllegalInfoCancelamentoException(String message, Throwable cause) {
        super(message, cause);
    }
}
