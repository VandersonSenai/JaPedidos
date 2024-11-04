package japedidos.exception;

/**
 *
 * @author thiago
 */
public class ComparableException extends IllegalArgumentException implements Comparable {
    public int order_index;
    
    public void setOrderIndex(int orderIndex) {
        this.order_index = orderIndex;
    }
    
    @Override
    public int compareTo(Object c) {
        if (c == null) {
            throw new NullPointerException();
        } else if (c instanceof ComparableException) {
            ComparableException newC = (ComparableException)c;
            if (this.equals(newC)) {
                return 0;
            } else {
                return this.order_index - newC.order_index;
            }
        } else {
//            throw new ClassCastException();
            return -1;
        }
    }
    
    @Override
    public boolean equals(Object e) {
        if (e == null) {
            throw new NullPointerException();
        } else if (e instanceof ComparableException) {
            ComparableException cEx = (ComparableException)e;
            System.out.println(cEx.order_index);
            System.out.println(this.order_index);
            return cEx.order_index == this.order_index;
        } else {
            return false;
        }
    }
    
    public ComparableException() {
        super("argumento inválido");
    }
    
    public ComparableException(String message) {
        super(message);
    }
    
    public ComparableException(String message, Throwable cause) {
        super(message, cause);
    }
}
