package japedidos.pedidos;

import java.util.ArrayList;
import japedidos.clientes.Cliente;
import japedidos.exception.*;
import japedidos.produto.*;
import japedidos.usuario.Registro;
import japedidos.usuario.Usuario;
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
    
    public Pedido(Cliente cliente, InfoEntrega infoEntrega, ProdutoPedido[] produtoPedido, int taxaDesconto) {
        this(1, cliente, infoEntrega, produtoPedido, taxaDesconto);
        this.id = NULL_ID;
        setRegistroCriacao(new Registro());
    }
    
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
            setTaxaDesconto(taxaDesconto); // Deve ser definido antes de adicionar os produtos para o calculo do preço final estar correto.
        } catch (IllegalTaxaDescontoException ex) {
            exs.addCause(ex);
        }
        
        try {
            setProdutos(produtoPedido);
        } catch (IllegalProdutoPedidoArrayException ex) {
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
            calcularPrecos();
        }
    }
    // Adiciona produtos e atualiza precos
    public void setProdutos(ProdutoPedido[] produtosPedido) {
        if (produtosPedido == null) {
            throw new IllegalProdutoPedidoArrayException("Produtos são nulos.");
        } else {
            this.produtosPedido = produtosPedido;
            calcularPrecos();
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
            if (this.estadosPedido == null) {
                this.estadosPedido = new EstadoPedido[1];
                this.estadosPedido[0] = estadoAtual;
            } else { // Se já houverem estados, adicionar outro à array
                int qtdEst = getEstadosPedidoCount();
                EstadoPedido[] novosEstados = new EstadoPedido[qtdEst]; // Abre espaço para mais um estado
                EstadoPedido[] estadosAtuais = getEstadosPedido();
                for (int i = 0; i < qtdEst; i++) {
                    novosEstados[i] = estadosAtuais[i];
                }
                novosEstados[qtdEst] = estadosAtuais[qtdEst-1];
                setEstadosPedido(novosEstados); // Atualiza estados e define o atual como o último
            }
        }
    }
    
    public int getEstadosPedidoCount() {
        return this.estadosPedido.length;
    }
    
    public EstadoPedido[] getEstadosPedido() {
        return this.estadosPedido;
    }
    
    public EstadoPedido getEstadoAtualPedido() {
        return this.estadoAtual;
    }
    
    public void setEstadosPedido(EstadoPedido[] estadosPedido) {
        if (estadosPedido == null) {
            throw new IllegalEstadoPedidoArrayException("Estados são nulos.");
        } else {
            this.estadosPedido = estadosPedido;
            if (this.estadosPedido == null) {
                EstadoPedido ultimo = estadosPedido[estadosPedido.length - 1];
                if (ultimo != null) {
                    setEstadoAtual(ultimo);
                }
            }
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
    
    public void calcularPrecos() {
        if (produtosPedido != null) {
            double somaPrecoCusto = 0;
            double taxaDesconto = getTaxaDesconto();
            ProdutoPedido[] produtos = getProdutos();
            for (ProdutoPedido prodPedido : produtos) {
                if (prodPedido != null) {
                    int qtdProd = prodPedido.getQuantidade();
                    Produto p = prodPedido.getProduto();
                    somaPrecoCusto += p.getPrecoCusto() * qtdProd;
                }
            }
            
            InfoEntrega infoEntrega = this.getInfoEntrega(); 
            double frete = 0;
            if (infoEntrega != null) {
                frete = infoEntrega.getPrecoFrete();
            }
            this.precoFinal = precoFinalVenda(produtos, taxaDesconto, frete);
            this.precoCustoTotal = somaPrecoCusto;
        }
    }
    
    public static double precoFinalVenda(ProdutoPedido[] produtos, double desconto, double valorFrete) {
        double somaPrecoVenda = 0;
        double taxaDesconto = desconto;
        for (ProdutoPedido prodPedido : produtos) {
            if (prodPedido != null) {
                int qtdProd = prodPedido.getQuantidade();
                Produto p = prodPedido.getProduto();
                somaPrecoVenda += p.getPrecoVenda() * qtdProd;
            }
        }

        return somaPrecoVenda - (somaPrecoVenda * taxaDesconto) + valorFrete;
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
    
    public Cliente getCliente() {
        return this.cliente;
    }
    
    public int getProdutoCount() {
        return this.produtosPedido.length;
    }
    
    public ProdutoPedido[] getProdutos() {
        return this.produtosPedido;
    }
    
    public InfoEntrega getInfoEntrega() {
        return this.infoEntrega;
    }
    
    public double getTaxaDesconto() {
        return this.taxaDesconto / 100.0;
    }
    
    public Registro getRegistroCriacao() {
        return this.criacao;
    }
    
    public LocalDate getDataVencimentoPagamento() {
        return this.dtVencimentoPagamento;
    }
    
    public double getPrecoFinal() {
        return this.precoFinal;
    }
    
    public double getCustoTotal() {
        return this.precoCustoTotal;
    }
}
