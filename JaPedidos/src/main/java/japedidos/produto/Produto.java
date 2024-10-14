package japedidos.produto;

/**
 *
 * @author thiago
 */
public class Produto {
    public final String nome;
    public int id;
    public final String categoria;
    public final double precoCusto;
    public final double precoVenda;
    public final Unidade unidadeMedida;

    public Produto(String nome, String categoria, Unidade unidadeMedida, double precoCusto, double precoVenda) {
        if (nome == null || unidadeMedida == null) {
            throw new NullPointerException();
        }

        if (precoVenda < 0) {
            throw new IllegalArgumentException();
        }

        this.nome = nome;
        this.categoria = new Categoria(categoria).getNome();
        this.unidadeMedida = unidadeMedida;
        this.precoCusto  = precoCusto;
        this.precoVenda = precoVenda;
    }
    
    public Produto(int id, String nome, String categoria, Unidade unidadeMedida, double precoCusto, double precoVenda) {
        this(nome, categoria, unidadeMedida, precoCusto, precoVenda);
        
        if (id < 1) {
            throw new IllegalArgumentException();
        }
        
        this.id = id;
    }
    
    @Override
    public String toString() {
        return nome + " | " + " | " + precoCusto + " | " + precoVenda;
    }
}