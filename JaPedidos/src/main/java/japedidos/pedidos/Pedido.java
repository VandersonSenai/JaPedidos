package japedidos.pedidos;

import java.util.ArrayList;
import japedidos.clientes.Cliente;
import japedidos.exception.IllegalArgumentsException;
import japedidos.exception.IllegalClienteException;
import japedidos.exception.IllegalDataPagoException;
import japedidos.exception.IllegalDataVencimentoPagamentoException;
import japedidos.exception.IllegalEstadoPedidoArrayException;
import japedidos.exception.IllegalEstadoPedidoException;
import japedidos.exception.IllegalIdException;
import japedidos.exception.IllegalInfoCancelamentoException;
import japedidos.exception.IllegalInfoEntregaException;
import japedidos.exception.IllegalProdutoPedidoArrayException;
import japedidos.exception.IllegalRegistroException;
import japedidos.exception.IllegalTaxaDescontoException;
import japedidos.produto.*;
import japedidos.usuario.Registro;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 *
 * @author thiago
 */
public final class Pedido {
    
    public static final int NULL_ID = -1;
    
    private int id = -1;
    private Cliente cliente;
    private InfoEntrega infoEntrega;
    private Registro criacao;
    private Registro alteracao;
    private double precoFinal;
    private double precoCustoTotal;
    private int taxaDesconto;
    private EstadoPedido estadoAtual;
    private EstadoPedido[] estadosPedido;
    private LocalDate dtPago;
    private LocalDate dtVencimentoPagamento;
    private ProdutoPedido[] produtosPedido;
    private String infoCancelamento;
    
    public Pedido(int id, Cliente cliente, InfoEntrega infoEntrega, ProdutoPedido[] produtoPedido, int taxaDesconto) {
        IllegalArgumentsException exs = new IllegalArgumentsException();
        
        // Id
        try {
            setId(id);
        } catch (IllegalIdException ex) {
            exs.addCause(ex);
        }
        
        try {
            setCliente(cliente);
        } catch (IllegalClienteException ex) {
            exs.addCause(ex);
        }
        
        try {
            setInfoEntrega(infoEntrega);
        } catch (IllegalInfoEntregaException ex) {
            exs.addCause(ex);
        }
        
        try {
            setProdutosPedido(produtosPedido);
        } catch (IllegalProdutoPedidoArrayException ex) {
            exs.addCause(ex);
        }
        
        try {
            setTaxaDesconto(taxaDesconto);
        } catch (IllegalTaxaDescontoException ex) {
            exs.addCause(ex);
        }
        
        if (exs.size() > 0) {
            throw exs;
        }
    }
    
    public boolean isNew() {
        return this.getId() == NULL_ID;
    }
    
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalIdException("Id não pode ser menor que 0.");
        } else {
            this.id = id;
        }
    }
    
    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalClienteException("Cliente é nulo.");
        } else {
            this.cliente = cliente;
        }
    }
    
    public void setInfoEntrega(InfoEntrega infoEntrega) {
        if (infoEntrega == null) {
            throw new IllegalInfoEntregaException("Informação de entrega é nula.");
        } else {
            this.infoEntrega = infoEntrega;
        }
    }
    
    public void setProdutosPedido(ProdutoPedido[] produtosPedido) {
        if (produtosPedido == null) {
            throw new IllegalProdutoPedidoArrayException("Produtos são nulos.");
        } else {
            this.produtosPedido = produtosPedido;
        }
    }
    
    public void setTaxaDesconto(int taxaDesconto) {
        if (taxaDesconto < 0) {
            throw new IllegalTaxaDescontoException("Taxa de desconto negativa.");
        } else if (taxaDesconto > 100) {
            throw new IllegalTaxaDescontoException("Taxa de desconto superior a 100%%.");
        } else {
            this.taxaDesconto = taxaDesconto;
        }
    }
    
    public void setRegistroCriacao(Registro criacao) {
        if (criacao == null) {
            throw new IllegalRegistroException("Registro é nulo");
        } else {
            this.criacao = criacao;
        }
    }
    
    public void setRegistroAlteracao(Registro alteracao) {
        if (alteracao == null) {
            throw new IllegalRegistroException("Registro é nulo");
        } else {
            this.alteracao = alteracao;
        }
    }
    
    public void setEstadoAtual(EstadoPedido estadoAtual) {
        if (estadoAtual == null) {
            throw new IllegalEstadoPedidoException("Estado é nulo.");
        } else {
            this.estadoAtual = estadoAtual;
        }
    }
    
    public void setEstadosPedido(EstadoPedido[] estadosPedido) {
        if (estadosPedido == null) {
            throw new IllegalEstadoPedidoArrayException("Estados são nulos.");
        } else {
            this.estadosPedido = estadosPedido;
        }
    
    }
    
    public void setDataVencimentoPagamento(LocalDate dataVencimentoPagamento) {
        if (dataVencimentoPagamento == null) {
            throw new IllegalDataVencimentoPagamentoException("Data é nula.");
        } else {
            this.dtVencimentoPagamento = dataVencimentoPagamento;
        }
    }
    
    public void setDataPago (LocalDate dataPago) {
        if (dataPago == null) {
            throw new IllegalDataPagoException("Data é nula.");
        } else {
            this.dtPago = dataPago;
        }
    }
    
    public void setInfoCancelamento(String infoCancelamento) {
        if (infoCancelamento == null) {
//            throw new IllegalInfoCancelamentoException("Informação de cancelamento é nula.");
            this.infoCancelamento = null;
        } else {
            infoCancelamento = infoCancelamento.trim();
            if (infoCancelamento.length() > 120) {
                throw new IllegalInfoCancelamentoException("Informação de cancelamento excede 120 caracteres.");
            } else if (infoCancelamento.isEmpty()) {
//                throw new IllegalInfoCancelamentoException("Informação de cancelamento é vazia.");
                this.infoCancelamento = null;
            } else {
                this.infoCancelamento = infoCancelamento;
            }
        }
    }
    
    public int getId() {
        return this.id;
    }
}
