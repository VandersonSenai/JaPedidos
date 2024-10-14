package japedidos.produto;

/** Esta classe representa o atributo "Unidade de medida" dos produtos. Uma unidade
 * de medida válida deve conter id, nome e abreviação, que serão utilizados no
 * cadastro de novos produtos e na seleção de produtos a serem adicionados em um
 * pedido.
 * 
 * @author Thiago M. Baiense
 */
public class Unidade {
    
    /** Representa a chave primária da unidade registrada no banco de dados. Para
     * instâncias ainda não cadastradas no banco de dados, seu valor deve ser -1
     * por padrão. Seus valores válidos são de {@literal id >= 0}.
     * 
     * @see #setId(int id)
     * @see #getId()
     */
    private int id = -1;
    
    /** Representa o nome completo da unidade de medida. O tamanho máximo é de
     * 16 caracteres.
     */
    private String nome;
    
    /** Representa o nome abreviado da unidade de medida. Esta abreviação será
     * associada a produtos para especificar como eles serão quantificados durante
     * a adição deles no pedido. Seu tamanho máximo é de 5 caracteres.
     */
    private String abreviacao;
    
    /** Constrói um novo objeto Unidade, contendo {@code nome} e {@code abreviação}.
     * 
     * @param nome          String contendo o nome da unidade de medida. O valor recebido
     * como sofre ação do {@code nome.trim()}.
     * 
     * @param abreviacao    String contendo a abreviação do nome da unidade de 
     * medida.  O valor recebido como sofre ação do {@code abreviacao.trim()} e
     * {@code abreviacao.toUpperCase}.
     * 
     * @throws NullPointerException se {@code nome} ou {@code abreviacao} for {@code null}.
     * 
     * @throws IllegalArgumentException se {@code nome.length()} ou 
     * {@code abreviacao.length()} retornar {@code true} ou se possuírem tamanhos inválidos.
     * 
     * @see #nome
     * @see #abreviacao
     */
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
        Integer s = new Integer(3);
    }
    
    /** Constrói um novo objeto Unidade, contendo {@code id}, {@code nome} e {@code abreviação}.
     * O {@code id} é aplicado à instância por meio do método {@link #setId(int) setId}.
     * @param id int contendo o valor da chave primária da unidade de medida no
     * banco de dados.
     * 
     * @param nome String contendo o nome da unidade de medida. O valor recebido
     * como sofre ação do {@code nome.trim()}.
     * 
     * @param abreviacao String contendo a abreviação do nome da unidade de 
     * medida.  O valor recebido como sofre ação do {@code abreviacao.trim()} e
     * {@code abreviacao.toUpperCase}.
     * 
     * @throws NullPointerException se {@code nome} ou {@code abreviacao} for {@code null}.
     * 
     * @throws IllegalArgumentException se {@code nome.length()} ou 
     * {@code abreviacao.length()} retornar {@code true} ou se possuírem tamanhos inválidos.
     * 
     * @see #id
     * @see #nome
     * @see #abreviacao
     */
    public Unidade(int id, String nome, String abreviacao) {
        this(nome, abreviacao);
        this.setId(id);
    }
    
    
    /** Atribuí o valor de {@code id} à instância.
     * 
     * @param id int contendo o valor de chave primária da unidade no banco de 
     * dados. Valor deve ser >= 0.
     * 
     * @throws IllegalStateException se a instância já tiver {@code id} definido.
     * @throws IllegalArgumentException se {@code id < 0}.
     */
    public void setId(int id) {
        if (this.id != -1) {
            throw new IllegalStateException();
        }
        
        if (id < 0) {
            throw new IllegalArgumentException();
        }
        
        this.id = id;
    }
    
    /** Retorna o {@code id} da instância.
     * 
     * @return int contendo que representa a chave primária do objeto Unidade.
     * @see #id
     */
    public int getId() {
        return this.id;
    }
    
    /** Retorna o nome da instância.
     * 
     * @return String que representa o nome completo da unidade.
     * @see #nome
     */
    public String getNome() {
        return this.nome;
    }
    
    /** Retorna o nome abreviado da instância.
     * 
     * @return String contendo o nome abreviado da instância.
     * @see #abreviacao
     */
    public String getAbreviacao() {
        return this.abreviacao;
    }

    /** Retorna a representação da unidade de medida no formato, exemplo:
     * "Kilograma (KG)". Empregado na exibição do objeto em 
     * {@link javax.swing.JComboBox JComboBox}.
     * 
     * @return String contendo a representação textual da unidade de medida, com
     * sua abreviação entre parênteses.
     */
    public String toString() {
        return String.format("%s (%s)", getNome(), getAbreviacao());
    }
}
