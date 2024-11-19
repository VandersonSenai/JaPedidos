package japedidos.pedidos;

import japedidos.exception.IllegalArgumentsException;
import japedidos.exception.IllegalDataHoraEntregarException;
import japedidos.exception.IllegalDestinatarioException;
import japedidos.exception.IllegalDestinoException;
import japedidos.exception.IllegalPrecoFreteException;
import japedidos.exception.IllegalTipoEntregaException;
import java.time.LocalDateTime;

/**
 *
 * @author t.baiense
 */
public class InfoEntrega implements Cloneable {
    private TipoEntrega tipoEntrega;
    private String destinatario;
    private LocalDateTime dthrEntregar;
    private double precoFrete;
    private Destino destino;
    
    public static void main(String[] args) throws Exception {
        InfoEntrega teste = new InfoEntrega(TipoEntrega.ENVIO, LocalDateTime.now(), 10);
        teste.setDestino(new Destino("a", "b", "c", "d", "e"));
        teste.setDestinatario("seu joao");
        System.out.println(((Destino)((InfoEntrega)teste.clone()).getDestino().clone()).equals(teste.getDestino()));
    }
    
    public InfoEntrega(TipoEntrega tipoEntrega, LocalDateTime dthrEntregar, double precoFrete) {
        IllegalArgumentsException exs = new IllegalArgumentsException();
        
        // Tipo da entrega
        try {
            setTipoEntrega(tipoEntrega);
        } catch (IllegalTipoEntregaException ex) {
            exs.addCause(ex);
        }
        
        // Data hora de entragar
        try {
            setDataHoraEntregar(dthrEntregar);
        } catch (IllegalDataHoraEntregarException ex) {
            exs.addCause(ex);
        }
        
        // Preco do frete
        try {
            setPrecoFrete(precoFrete);
        } catch (IllegalPrecoFreteException ex) {
            exs.addCause(ex);
        }
        
        if (exs.size() > 0) {
            throw exs;
        }
    }
    
    public void setTipoEntrega(TipoEntrega tipo) {
        if (tipo == null) {
            throw new IllegalTipoEntregaException("Tipo de entrega é nulo.");
        } else {
            this.tipoEntrega = tipo;
        }
    }
    
    public void setDestino(Destino destino) {
        if (destino == null && getTipoEntrega() == TipoEntrega.ENVIO) {
            throw new IllegalDestinoException("Destino é nulo para entrega de envio.");            
        } else {
            this.destino = destino;
        }
    }
    
    public void setDestinatario(String destinatario) {
        if (destinatario == null) {
//            throw new IllegalDestinatarioException("Destinatário é nulo.");
            this.destinatario = null;
        } else {
            destinatario = destinatario.trim();
            if (destinatario.isEmpty()) {
//                throw new IllegalDestinatarioException();
                this.destinatario = null;
            } else if (destinatario.length() > 120) {
                throw new IllegalDestinatarioException("Destino excede 120 caracteres.");
            } else {
                this.destinatario = destinatario;
            }
        }
    }
    
    public void setDataHoraEntregar(LocalDateTime dataHoraEntregar) {
        if (dataHoraEntregar == null) {
            throw new IllegalDataHoraEntregarException("Data e Hora de entregar é nulo.");
        } else {
            this.dthrEntregar = dataHoraEntregar;
        }
    }
    
    public void setPrecoFrete(double precoFrete) {
        if (precoFrete < 0) {
            throw new IllegalPrecoFreteException("Preço é inferior a 0.");
        } else {
            this.precoFrete = precoFrete;
        }
    }
    
    public TipoEntrega getTipoEntrega() {
        return this.tipoEntrega;
    }
    
    public Destino getDestino() {
        return this.destino;
    }
    
    public String getDestinatario() {
        return this.destinatario;
    }
    
    public LocalDateTime getDataHoraEntregar() {
        return this.dthrEntregar;
    }
    
    public double getPrecoFrete() {
        return this.precoFrete;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        InfoEntrega novo = (InfoEntrega)super.clone();
        if (this.getTipoEntrega() == TipoEntrega.ENVIO) {
            Destino destino = this.getDestino();
            if (destino != null) {
                novo.setDestino((Destino)destino.clone());
            }
        }
        String dest = this.getDestinatario();
        if (dest != null) {
            novo.setDestinatario(new String(this.getDestinatario()));
        }
        return novo;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof InfoEntrega) {
            InfoEntrega i = (InfoEntrega)o;
            String esteDestinatario = this.getDestinatario();
            String outroDestinatario = i.getDestinatario();
            return (
                    i.getDestino().equals(this.getDestino()) &&
                    ( esteDestinatario == outroDestinatario && esteDestinatario == null || outroDestinatario != null && esteDestinatario != null && i.getDestinatario().equals(this.getDestinatario()) ) &&
                    i.getTipoEntrega() == this.getTipoEntrega() &&
                    i.getDataHoraEntregar() == this.getDataHoraEntregar() &&
                    i.getPrecoFrete() == this.getPrecoFrete()
            ) ? true : false;
        } else {
            return false;
        }
    }
}
