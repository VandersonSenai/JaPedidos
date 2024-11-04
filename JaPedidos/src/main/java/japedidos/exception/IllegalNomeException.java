package japedidos.exception;

/** Representa uma exceção que ocorre quando um {@code nome} inválido é fornecido
 * como argumento a um método ou construtor.
 *
 * @author thiago
 */
public class IllegalNomeException extends ComparableException {
    
    public IllegalNomeException() {
        super("nome inválido");
        super.order_index = 1;
    }
    
    public IllegalNomeException(String message) {
        super(message);
        super.order_index = 1;
    }
    
    public IllegalNomeException(String message, Throwable cause) {
        super(message, cause);
        super.order_index = 1;
    }
}
