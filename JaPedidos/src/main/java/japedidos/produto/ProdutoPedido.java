package japedidos.produto;

import japedidos.exception.IllegalArgumentsException;
import japedidos.exception.IllegalProdutoException;
import japedidos.exception.IllegalQuantidadeException;
/**
 *
 * @author thiago
 */
public class ProdutoPedido {
    private Produto produto;
    private int quantidade;
    
    public ProdutoPedido(Produto produto, int quantidade) {
        IllegalArgumentsException exs = new IllegalArgumentsException();
        
        if (produto == null) {
            exs.addCause(new IllegalProdutoException("Não pode ser null.")); 
        } else {
            this.produto = produto;
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
    
    public void setQuantidade(int quantidade) {
        if (quantidade < 1) {
            throw new IllegalQuantidadeException("Não pode ser inferior a 1.");
        } else {
            this.quantidade = quantidade;
        }
    }
    
    public int getQuantidade() {
        return this.quantidade;
    }
    
}
