package japedidos.exception;

/** Representa uma exceção que informa que uma descrição inválida foi fornecida 
 * como argumento a um método ou construtor.
 *
 * @author thiago
 */
public class IllegalDataVencimentoPagamentoException extends ComparableException {
    public IllegalDataVencimentoPagamentoException() {
        super("Data de vencimento de pagamento inválida");
    }
    
    public IllegalDataVencimentoPagamentoException(String message) {
        super(message);
    }
    
    public IllegalDataVencimentoPagamentoException(String message, Throwable cause) {
        super(message, cause);
    }
}
