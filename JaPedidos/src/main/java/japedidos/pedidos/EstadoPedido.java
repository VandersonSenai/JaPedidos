package japedidos.pedidos;

import japedidos.usuario.Usuario;
import java.time.LocalDateTime;

/**
 *
 * @author thiago
 */
public class EstadoPedido {
    public final Estado ESTADO;
    public final Usuario AUTOR;
    public final LocalDateTime CRIACAO;
    
    public EstadoPedido(Estado estado) {
        if (estado == null) {
            throw new NullPointerException();
        }
        
        this.ESTADO = estado;
        this.AUTOR = Usuario.getAtual();
        this.CRIACAO = LocalDateTime.now();
    }
    
    public EstadoPedido(Estado estado, Usuario autor, LocalDateTime criacao) {
        if (estado == null || autor == null || criacao == null) {
            throw new NullPointerException();
        }
        this.ESTADO = estado;
        this.AUTOR = autor;
        this.CRIACAO = criacao;
    }
    
    
}
