package japedidos.produto;

/** Representa a categoria de determinado produto. <br>
 * Uma categoria deve conter uma chave primária, representada pelo 
 * {@link #id id}, um nome de identificação, contido em {@link #nome nome} e 
 * opcionalmente uma descrição, armazenada em {@link #descricao descricao}.
 *
 * @author Thiago M. Baiense
 */
public class Categoria {
    
    public static final int NULL_ID = -1;
    
    /** Representa a chave primária da categoria registrada no banco de dados. <br>
     * Para instâncias ainda não cadastradas no banco de dados, seu valor deve 
     * ser -1 por padrão. Seus valores válidos são de {@literal id >= 0}.
     * 
     * @see #setId(int id)
     * @see #getId()
     */
    private int id = NULL_ID;
    
    /** Representa o nome completo da categoria. O tamanho máximo é de
     * 24 caracteres.
     * 
     * @see #getNome()
     */
    private String nome;
    
    /** Descreve a função da categoria e em quais produtos poderá ser aplicada. <br>
     * Seu tamanho máximo é de 120 caracteres. Seu valor em uma instância deverá
     * ser {@code null} caso não tenha sido informada durante a criação.
     * 
     * @see #getDescricao() 
     */
    private String descricao;
    
    /** Constrói um novo objeto {@code Categoria} a partir de {@code id}, 
     * {@code nome} e {@code descricao}.
     * 
     * @param id {@code int} contendo o valor da chave primária da categoria no
     * banco de dados.
     * 
     * @param nome String contendo o nome da categoria. O valor recebido
     * como sofre ação do {@code nome.trim()}.
     * 
     * @param descricao String contendo a descrição do categoria. O valor 
     * recebido sofre ação do {@code descricao.trim()}. <br>
     * 
     * O valor de {@code descricao} 
     * do objeto instanciado será {@code null}, se o parâmetro {@code descricao}
     * for {@code null} ou se {@code descricao.isEmpty()} retornar {@code true}.
     * 
     * @throws NullPointerException se {@code nome} for {@code null}.
     * 
     * @throws IllegalArgumentException se {@code nome.isEmpty()} retornar 
     * {@code true}, se {@code nome} ou {@code descricao} possuírem tamanhos
     * inválidos ou se {@code id < 0}.
     * 
     * @see #id
     * @see #nome
     * @see #descricao
     */
    public Categoria(int id, String nome, String descricao) {
        this(nome, descricao);
        
        setId(id);
    }
    
    /** Constrói um novo objeto {@code Categoria} a partir de {@code id} e 
     * {@code nome}. <br>
     * 
     * @param id {@code int} contendo o valor da chave primária da categoria no
     * banco de dados.
     * 
     * @param nome String contendo o nome da categoria. O valor recebido
     * como sofre ação do {@code nome.trim()}.
     * 
     * @throws NullPointerException se {@code nome} for {@code null}.
     * 
     * @throws IllegalArgumentException se {@code nome.isEmpty()} retornar 
     * {@code true}, se {@code nome} possuir tamanho
     * inválido ou se {@code id < 0}.
     * 
     * @see #id
     * @see #nome
     */
    public Categoria(int id, String nome) {
        this(nome);
        
        setId(id);
    }
    
    /** Constrói um novo objeto {@code Categoria} a partir de {@code nome} e 
     * {@code descricao}. <br>
     * 
     * @param nome String contendo o nome da categoria. O valor recebido
     * como sofre ação do {@code nome.trim()}.
     * 
     * @param descricao String contendo a descrição do categoria. O valor 
     * recebido sofre ação do {@code descricao.trim()}. <br>
     * 
     * O valor de {@code descricao} 
     * do objeto instanciado será {@code null}, se o parâmetro {@code descricao}
     * for {@code null} ou se {@code descricao.isEmpty()} retornar {@code true}.
     * 
     * @throws NullPointerException se {@code nome} for {@code null}.
     * 
     * @throws IllegalArgumentException se {@code nome.isEmpty()} retornar 
     * {@code true}, se {@code nome} ou {@code descricao} possuírem tamanhos
     * inválidos.
     * 
     * @see #nome
     * @see #descricao
     */
    public Categoria(String nome, String descricao) {
        this(nome);
        
        if (descricao != null) {
            if(descricao.length() > 120) {
                throw new IllegalArgumentException();
            }
            descricao = descricao.trim();
            
            if (!descricao.isEmpty()) {
                this.descricao = descricao;
                return;
            }
        }
        
        this.descricao = null;
    }
    
    /** Constrói um novo objeto {@code Categoria} a partir de {@code nome}.<br>
     * 
     * @param nome String contendo o nome da categoria. O valor recebido
     * como sofre ação do {@code nome.trim()}.
     * 
     * @throws NullPointerException se {@code nome} for {@code null}.
     * 
     * @throws IllegalArgumentException se {@code nome.isEmpty()} retornar 
     * {@code true} ou se {@code nome} possuir tamanho inválido.
     * 
     * @see #nome
     */
    public Categoria(String nome) {
        if (nome == null) {
            throw new NullPointerException();
        }
        nome = nome.trim();
        
        if(nome.isEmpty() || nome.length() > 24) {
            throw new IllegalArgumentException();
        }
        
        this.nome = nome;
    }
    
    public boolean isNew() {
        return this.getId() == NULL_ID;
    }
    
    /** Atribuí o valor de {@code id} à instância.
     * 
     * @param id int contendo o valor de chave primária da categoria no banco de 
     * dados. Valor deve ser >= 0.
     * 
     * @throws IllegalStateException se a instância já tiver {@code id} definido.
     * @throws IllegalArgumentException se {@code id < 0}.
     * 
     * @see #id
     */
    public void setId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }
    
    /** Retorna o nome da instância.
     * 
     * @return String que representa o nome completo da categoria.
     * @see #nome
     */
    public String getNome() {
        return this.nome;
    }
    
    /** Retorna o {@code id} da instância.
     * 
     * @return int contendo que representa a chave primária do objeto Categoria.
     * @see #id
     */
    public int getId() {
        return this.id;
    }
    
    /** Retorna a descrição da instância, contida em {@code descricao}.
     * 
     * @return String que representa a descrição detalhada da categoria.
     * @see #descricao
     */
    public String getDescricao() {
        return this.descricao;
    }
    
    @Override
    public String toString() {
        return getNome();
    }
    
    @Override
    public boolean equals(Object o) {
        return (o instanceof Categoria && ((Categoria)o).getId() == this.getId());
    }
}
