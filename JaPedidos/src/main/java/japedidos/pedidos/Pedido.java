package japedidos.pedidos;

import java.util.ArrayList;
import japedidos.clientes.Cliente;
import japedidos.produto.Categoria;
import japedidos.produto.Unidade;
import japedidos.produto.Produto;
import japedidos.usuario.Usuario;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 *
 * @author thiago
 */
public class Pedido {
    private int id = -1;
    public final ArrayList<EstadoPedido> ESTADOS = new ArrayList<EstadoPedido>();
    private TipoEntrega tipoEntrega;
    private Destino destino;
    private String destinatario;
    public final Usuario AUTOR;
    private LocalDateTime criacao;
    private Usuario usuarioAlteracao;
    private LocalDateTime dthrAlteracao;
    private LocalDateTime dthrAntregar;
    private LocalDate dtPago;
    private LocalDate dtVencimentoPagamento;
    private Cliente cliente;
    private boolean cancelado;
    private String infoCancelamento;
    private double precoFrete;
    private double precoFinal;
    private double precoCustoTotal;
    private double taxaDesconto;
    
    public Pedido() {
        this.AUTOR = Usuario.getAtual();
    }
}
