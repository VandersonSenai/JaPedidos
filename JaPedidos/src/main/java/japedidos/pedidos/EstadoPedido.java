package japedidos.pedidos;

import japedidos.usuario.Usuario;
import java.time.LocalDateTime;

/**
 *
 * @author thiago
 */
public class EstadoPedido extends Estado {
    public final Usuario AUTOR;
    public final LocalDateTime CRIACAO;
    
    public EstadoPedido(Estado estado) {
        super(estado.ID, estado.NOME);
        this.AUTOR = Usuario.getAtual();
        this.CRIACAO = LocalDateTime.now();
    }
    
    public EstadoPedido(int id, String nome, Usuario autor, LocalDateTime criacao) {
        super(id, nome);
        
        if (autor == null || criacao == null) {
            throw new NullPointerException();
        }
        
        this.AUTOR = autor;
        this.CRIACAO = criacao;
    }
}
