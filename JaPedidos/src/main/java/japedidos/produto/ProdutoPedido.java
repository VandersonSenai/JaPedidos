package japedidos.produto;

import japedidos.bd.BD;
import japedidos.exception.IllegalArgumentsException;
import japedidos.exception.IllegalInfoAdicionalProdutoException;
import japedidos.exception.IllegalProdutoException;
import japedidos.exception.IllegalQuantidadeException;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author thiago
 */
public class ProdutoPedido implements Cloneable {
    private Produto produto;
    private int quantidade;
    private String infoAdicional;
    
    public static void main(String[] args) {
        ProdutoPedido[] recebidos = BD.ProdutoPedido.selectAllBy_id_pedido(20);
        ProdutoPedido[] clonados = ProdutoPedido.clone(recebidos);
        System.out.println(recebidos[0] == clonados[0]);
    }
    
    public ProdutoPedido(Produto produto, int quantidade) {
        IllegalArgumentsException exs = new IllegalArgumentsException();
        
        try {
            this.setProduto(produto);
        } catch (IllegalProdutoException ex) {
            exs.addCause(ex);
        }
        
        try {
            this.setQuantidade(quantidade);
        } catch (IllegalQuantidadeException ex) {
            exs.addCause(ex);
        }
        
        if (exs.size() > 0) {
            throw exs;
        }
    }
    
    public Produto getProduto() {
        return this.produto;
    }
    
    public void setProduto(Produto p) {
        if (p == null) {
            throw new IllegalProdutoException("Não pode ser null."); 
        } else {
            this.produto = p;
        }
    }
    
    public void setQuantidade(int quantidade) {
        if (quantidade < 1) {
            throw new IllegalQuantidadeException("Não pode ser inferior a 1.");
        } else {
            this.quantidade = quantidade;
        }
    }
    
    public void setInfoAdicional(String infoAdicional) {
        if (infoAdicional != null) {
            infoAdicional = infoAdicional.trim();
            if (infoAdicional.isEmpty()) {
                this.infoAdicional = null;
                return;
            } else if (infoAdicional.length() > 45) {
                throw new IllegalInfoAdicionalProdutoException("Informação adicional excede 45 caracteres.");
            }
            
            this.infoAdicional = infoAdicional;
        } else {
            this.infoAdicional = null;
        }
    }
    
    public String getInfoAdicional() {
        return this.infoAdicional;
    }
    
    public int getQuantidade() {
        return this.quantidade;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        ProdutoPedido p = (ProdutoPedido)super.clone();
//        p.setProduto((Produto)this.getProduto().clone());
        String info = this.getInfoAdicional();
        p.setInfoAdicional((info != null) ? new String(info) : null);
        return p;
    }
    
    public static ProdutoPedido[] clone(ProdutoPedido[] produtos) {
        if (produtos == null) {
            throw new NullPointerException("Produtos a serem clonados não podem ser nulos!");
        }
        
        ArrayList<ProdutoPedido> pList = new ArrayList<>();
        try {
            for (ProdutoPedido p : produtos) {
                if (p != null) {
                    pList.add((ProdutoPedido)p.clone());
                }
            }
        } catch (CloneNotSupportedException ex) {
            throw new IllegalArgumentException("Um ou mais produtos não são clonáveis", ex);
        }
        
        if (!pList.isEmpty()) {
            return pList.toArray(new ProdutoPedido[produtos.length]);
        } else {
            return null;
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s (%d %s)", this.getProduto().getNome(), this.getQuantidade(), this.getProduto().getUnidade().getAbreviacao());
    }
    
    public static boolean equals(ProdutoPedido[] p1, ProdutoPedido[] p2) {
        if (p1.length == p2.length) {
            for (int i = 0; i < p1.length; i++) {
                if (p1 != p2 && (p1 == null || p2 == null) || !p1[i].equals(p2[i])) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof ProdutoPedido) {
            ProdutoPedido p = (ProdutoPedido)o;
            String estaInfo, outraInfo;
            estaInfo = this.getInfoAdicional();
            outraInfo = p.getInfoAdicional();
            
            return this.getProduto().equals(p.getProduto()) &&
                    (estaInfo == outraInfo && estaInfo == null || estaInfo != null && outraInfo != null && this.getInfoAdicional().equals(p.getInfoAdicional()) ) &&
                    this.getQuantidade() == p.getQuantidade();
        } else {
            return false;
        }
    }
}
