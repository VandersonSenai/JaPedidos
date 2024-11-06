package japedidos.exception;

/** Representa uma exceção que informa que uma descrição inválida foi fornecida 
 * como argumento a um método ou construtor.
 *
 * @author thiago
 */
public class IllegalDataPagoException extends ComparableException {
    public IllegalDataPagoException() {
        super("Data de pagamento inválida");
    }
    
    public IllegalDataPagoException(String message) {
        super(message);
    }
    
    public IllegalDataPagoException(String message, Throwable cause) {
        super(message, cause);
    }
}
