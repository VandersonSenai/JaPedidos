package japedidos.produto;

/**
 *
 * @author thiago
 */
public class Unidade {
    private int id = -1;
    private String nome, abreviacao;
    
    public Unidade(String nome, String abreviacao) {
        if (nome == null || abreviacao == null) {
            throw new NullPointerException();
        }
        nome = nome.trim();
        abreviacao = abreviacao.trim().toUpperCase();
        
        if (nome.isEmpty() || abreviacao.isEmpty() || nome.length() > 16 || abreviacao.length() > 5) {
            throw new IllegalArgumentException();
        }
        
        this.nome = nome;
        this.abreviacao = abreviacao;
    }
    
    public Unidade(int id, String nome, String abreviacao) {
        this(nome, abreviacao);
        this.setId(id);
    }
    
    public void setId(int id) {
        if (this.id != -1) {
            throw new IllegalStateException();
        }
        
        if (id < 0) {
            throw new IllegalArgumentException();
        }
        
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public String getAbreviacao() {
        return this.abreviacao;
    }

    public String toString() {
        return String.format("%d|%s|%s", getId(), getNome(), getAbreviacao());
    }
}
