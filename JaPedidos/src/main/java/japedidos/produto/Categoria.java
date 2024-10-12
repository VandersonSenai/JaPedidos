package japedidos.produto;

/**
 *
 * @author thiago
 */
public class Categoria {
    private int id = -1;
    private String nome;
    private String descricao;
    
    public Categoria(int id, String nome, String descricao) {
        this(nome, descricao);
        
        setId(id);
    }
    
    public Categoria(int id, String nome) {
        this(nome);
        
        setId(id);
    }
    
    public Categoria(String nome, String descricao) {
        this(nome);
        
        if (descricao != null) {
            if(descricao.length() > 120) {
                throw new IllegalArgumentException();
            }

            if (!descricao.isEmpty()) {
                this.descricao = descricao;
                return;
            }
        }
        
        this.descricao = null;
    }
    
    public Categoria(String nome) {
        if (nome == null) {
            throw new NullPointerException();
        }
        
        if(nome.length() == 0 || nome.length() > 24) {
            throw new IllegalArgumentException();
        }
        
        this.nome = nome;
    }
    
    public void setId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException();
        }
        this.id = id;
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
