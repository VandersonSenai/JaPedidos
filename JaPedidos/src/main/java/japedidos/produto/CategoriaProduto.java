package japedidos.produto;

/**
 *
 * @author thiago
 */
public class CategoriaProduto {
    private int id = -1;
    private String nome;
    private String descricao;
    
    CategoriaProduto(int id, String nome, String descricao) {
        this(nome, descricao);
        
        if (id < 1) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }
    
    CategoriaProduto(int id, String nome) {
        this(nome);
        
        if (id < 1) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }
    
    CategoriaProduto(String nome, String descricao) {
        this(nome);
        if (descricao == null) {
            throw new NullPointerException();
        }
        
        if(descricao.length() > 120) {
            throw new IllegalArgumentException();
        }
        
        if (!descricao.isEmpty()) {
            this.descricao = descricao;
        }
    }
    
    CategoriaProduto(String nome) {
        if (nome == null) {
            throw new NullPointerException();
        }
        
        if(nome.length() == 0 || nome.length() > 24) {
            throw new IllegalArgumentException();
        }
        
        this.nome = nome;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getDescricao() {
        return this.descricao;
    }
}
