package japedidos.pedidos;

import japedidos.exception.IllegalDataPagoException;
import japedidos.exception.IllegalDataVencimentoPagamentoException;
import japedidos.exception.IllegalInfoCancelamentoException;
import japedidos.usuario.Usuario;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author thiago
 */
public class EstadoPedido {
    public final Estado ESTADO;
    public final Usuario AUTOR;
    public final LocalDateTime CRIACAO;
    private LocalDate dtPago;
    private LocalDate dtVencimentoPagamento;
    private String infoCancelamento;
    
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
    
    public String getInfoCancelamento() {
        return this.infoCancelamento;
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
    
    public LocalDate getDataVencimentoPagamento() {
        return this.dtVencimentoPagamento;
    }
    
    public LocalDate getDataPago() {
        return this.dtPago;
    }
}
