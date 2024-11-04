package japedidos.exception;

/** Representa uma exceção que informa que uma observação inválida foi fornecida 
 * como argumento a um método ou construtor.
 *
 * @author thiago
 */
public class IllegalObservacaoException extends ComparableException {
    public IllegalObservacaoException() {
        super("observação inválida");
    }
    
    public IllegalObservacaoException(String message) {
        super(message);
    }
    
    public IllegalObservacaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
