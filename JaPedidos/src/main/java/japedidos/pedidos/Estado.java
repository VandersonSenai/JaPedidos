package japedidos.pedidos;

import java.util.ArrayList;

/** Representa o estado de andamento de um pedido em dado momento. <br>
 * O estado de andamento indica o progresso de um pedido em direção à sua 
 * conclusão. 
 *
 * @author thiago
 */
public class Estado {
    
    public static final Estado ABERTO               = new Estado(1, "Em aberto");
    public static final Estado AGUARDANDO_PAGAMENTO = new Estado(2, "Aguardando pagamento");
    public static final Estado PAGO                 = new Estado(3, "Pago");
    public static final Estado EM_PREPARO           = new Estado(4, "Em preparo/separação");
    public static final Estado AGUARDANDO_ENVIO     = new Estado(5, "Aguardando envio");
    public static final Estado AGUARDANDO_RETIRADA  = new Estado(6, "Aguardando retirada");
    public static final Estado SAIU_PARA_ENTREGA    = new Estado(7, "Saiu para entrega");
    public static final Estado CONCLUIDO            = new Estado(8, "Concluído");
    public static final Estado CANCELADO            = new Estado(9, "Cancelado");
    
    public final int ID;
    public final String NOME;
    public static final int QUANTIDADE = 9;
    
    private Estado(int id, String nome) {
        if (nome == null) {
            throw new NullPointerException();
        }
        
        if (id <= 0 || nome.length() > 16) {
            throw new IllegalArgumentException();
        }
        
        this.ID = id;
        this.NOME = nome;
    }
    
    public static Estado[] getAll() {
        Estado[] estados = new Estado[QUANTIDADE];
        for (int i=1; i <= QUANTIDADE; i++) {
            estados[i] = getEstado(i);
        }
        return estados;
    }
    
    public static Estado getEstado(int id) {
        return switch(id) {
            case 1 -> ABERTO;
            case 2 -> AGUARDANDO_PAGAMENTO;
            case 3 -> PAGO;
            case 4 -> EM_PREPARO;
            case 5 -> AGUARDANDO_ENVIO;
            case 6 -> AGUARDANDO_RETIRADA;
            case 7 -> SAIU_PARA_ENTREGA;
            case 8 -> CONCLUIDO;
            case 9 -> CANCELADO;
            default -> null;
        };
    }
    
    public String toString() {
        return this.NOME;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Estado && ((Estado)obj).ID == this.ID) {
            return true;
        }
        
        return false;
    }
}
