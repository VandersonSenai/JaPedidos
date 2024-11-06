package japedidos.exception;

/** Representa uma exceção que informa que uma descrição inválida foi fornecida 
 * como argumento a um método ou construtor.
 *
 * @author thiago
 */
public class IllegalDestinoException extends ComparableException {
    public IllegalDestinoException() {
        super("Destino inválido");
    }
    
    public IllegalDestinoException(String message) {
        super(message);
    }
    
    public IllegalDestinoException(String message, Throwable cause) {
        super(message, cause);
    }
}
