package japedidos.pedidos;

import java.util.ArrayList;
import japedidos.clientes.Cliente;
import japedidos.produto.Categoria;
import japedidos.produto.Unidade;
import japedidos.produto.Produto;
import japedidos.usuario.Usuario;
import java.time.LocalDateTime;

/**
 *
 * @author thiago
 */
public class Pedido {
    private int id = -1;
    public final ArrayList<EstadoPedido> ESTADOS = new ArrayList<EstadoPedido>();
    private Destino destino;
    private String destinatario;
    public final Usuario AUTOR;
    private LocalDateTime CRIACAO;
    private Cliente cliente;
    
    public Pedido() {
        this.AUTOR = Usuario.getAtual();
    }
}
