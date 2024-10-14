package japedidos.produto;

/** Representa um produto, um item comercializável que será usado pelo utilizador
 * para inserir em instâncias de {@link japedidos.pedidos.Pedido Pedido}.<br>
 * Toda instância apta a ser utilizada deverá conter uma chave primária representada
 * pelo valor de {@link #id id}, um {@link #nome nome}, uma {@link #categoria categoria},
 * uma {@link #unidadeMedida unidadeMedida}, e preço de custo e de venda, representados
 * por {@link #precoCusto precoCusto} e {@link #precoVenda precoVenda}, respectivamente.
 *
 * @author Thiago M. Baiense
 */
public class Produto {
    
    /** Representa a chave primária do produto registrada no banco de dados. Para
     * instâncias ainda não cadastradas no banco de dados, seu valor deve ser -1
     * por padrão. Seus valores válidos são de {@literal id >= 0}.
     * 
     */
    private int id = -1;
    
    /** Representa o nome completo do produto. O tamanho máximo é de
     * 32 caracteres.
     */
    public final String nome;
    
    /** Representa a {@link japedidos.produto.Categoria categoria} do produto.
     * 
     * @see japedidos.produto#Categoria Categoria
     */
    public final Categoria categoria;
    
    /** Preço de custo do produto, que é usado para calcular a lucratividade
     * dos pedidos. Será valido sempre que {@code precoCusto >= 0}.
     */
    public final double precoCusto;
    
    /** Preço de venda do produto, que é usado para calcular a lucratividade
     * dos pedidos. Será valido sempre que {@code precoVenda >= 0}.
     */
    public final double precoVenda;
    
    /** Representa a {@link japedidos.produto.Unidade unidade de medida} do 
     * produto.
     * 
     * @see japedidos.produto.Unidade Unidade
     */
    public final Unidade unidadeMedida;

    
    /** Constrói uma nova instância de {@code Produto}, com {@code id == -1}.
     * 
     * @param nome {@code String} contendo o nome da categoria. O valor recebido
     * como sofre ação do {@code nome.trim()}.
     * 
     * @param categoria {@link japedidos.produto.Categoria Categoria} que especifica
     * a categoria do produto.
     * 
     * @param unidadeMedida {@link japedidos.produto.Unidade Unidade} que especifica
     * a unidade de medida que será usada para quantificar o produto.
     * 
     * @param precoCusto {@code Double} que determina o preço de custo do produto. Deverá ser >= 0.
     * @param precoVenda {@code Double} que determina o preço de venda do produto. Deverá ser >= 0.
     */
    public Produto(String nome, Categoria categoria, Unidade unidadeMedida, double precoCusto, double precoVenda) {
        if (nome == null || categoria == null || unidadeMedida == null) {
            throw new NullPointerException();
        }
        
        nome = nome.trim();
        
        if (nome.isEmpty() || precoCusto < 0 || precoVenda < 0) {
            throw new IllegalArgumentException();
        }

        this.nome = nome;
        this.categoria = categoria;
        this.unidadeMedida = unidadeMedida;
        this.precoCusto  = precoCusto;
        this.precoVenda = precoVenda;
    }


    /** Constrói uma nova instância de {@code Produto}, com {@code id == -1}.
     * 
     * @param id {@code int} contendo o valor da chave primária do produto no
     * banco de dados.
     * 
     * @param nome {@code String} contendo o nome da categoria. O valor recebido
     * como sofre ação do {@code nome.trim()}.
     * 
     * @param categoria {@link japedidos.produto.Categoria Categoria} que especifica
     * a categoria do produto.
     * 
     * @param unidadeMedida {@link japedidos.produto.Unidade Unidade} que especifica
     * a unidade de medida que será usada para quantificar o produto.
     * 
     * @param precoCusto {@code Double} que determina o preço de custo do produto. Deverá ser >= 0.
     * @param precoVenda {@code Double} que determina o preço de venda do produto. Deverá ser >= 0.
     */
    public Produto(int id, String nome, Categoria categoria, Unidade unidadeMedida, double precoCusto, double precoVenda) {
        this(nome, categoria, unidadeMedida, precoCusto, precoVenda);
        
        if (id < 0) {
            throw new IllegalArgumentException();
        }
        
        this.id = id;
    }
    
    @Override
    public String toString() {
        return nome + " | " + " | " + precoCusto + " | " + precoVenda;
    }
}