package japedidos.exception;

import java.util.Arrays;

public class IllegalArgumentsException extends Exception {
    
    private Throwable[] causes;
    private int nextCause;
    
    public IllegalArgumentsException() {}
    
    public IllegalArgumentsException(String message) {
        super(message);
    }
    
    public IllegalArgumentsException(Throwable cause) {
        this.initCause(cause);
    }
    
    public IllegalArgumentsException(Throwable... causes) {
        this.initCauses(causes);
    }
    
    public IllegalArgumentsException(String message, Throwable cause) {
        super(message);
        this.initCause(cause);
    }
    
     public IllegalArgumentsException(String message, Throwable... causes) {
        super(message);
        this.initCauses(causes);
    }
    
    public Throwable initCause(Throwable cause) {
        causes = new Throwable[1];
        causes[0] = cause;
        
        return this;
    }
    
    public Throwable initCauses(Throwable... causes) {
        if (causes == null) {
            return this;
        }
        
        this.causes = causes;
        
        return this;
    }
    
    public boolean hasNextCause() {
        if (causes == null) {
            return false;
        } else if (nextCause < causes.length) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public Throwable getCause() {
        if (causes == null) {
            return null;
        }
        
        if (causes.length == 1) {
            return causes[nextCause++];
        } else if (this.nextCause < causes.length) {
            return causes[nextCause++];
        } else {
            return null;
        }
    }
    
    public Throwable nextCause() {
        return this.getCause();
    }
    
    public Throwable[] getCauses() {
        return this.causes;
    }
    
    public int getCauseCount() {
        if (this.causes == null) {
            return 0;
        } else { 
            return this.causes.length;
        }
    }
    
    public Throwable addCause(Throwable cause) {
        if (cause == null) {
            return this;
        }
        
        if (this.causes == null) {
            this.initCause(cause);
        }
        
        Throwable[] newCauses = Arrays.copyOf(getCauses(), getCauseCount()+1);
        newCauses[newCauses.length-1] = cause;
        
        this.causes = newCauses;
        return this;
    }
}
