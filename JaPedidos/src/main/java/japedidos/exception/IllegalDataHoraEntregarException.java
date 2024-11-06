package japedidos.exception;

/**
 *
 * @author thiago
 */
public class IllegalDataHoraEntregarException extends ComparableException {
    public IllegalDataHoraEntregarException() {
        super("Data e hora de entregar inválida");
    }
    
    public IllegalDataHoraEntregarException(String message) {
        super(message);
    }
    
    public IllegalDataHoraEntregarException(String message, Throwable cause) {
        super(message, cause);
    }
}
