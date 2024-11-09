package japedidos.produto;

import japedidos.exception.*;
import japedidos.usuario.Registro;

/** Representa um produto, um item comercializável que será usado pelo utilizador
 * para inserir em instâncias de {@link japedidos.pedidos.Pedido Pedido}. <br>
 * Toda instância apta a ser utilizada deverá conter uma chave primária representada
 * pelo valor de {@link #id id}, um {@link #nome nome}, uma {@link #categoria categoria},
 * uma {@link #unidadeMedida unidadeMedida}, e preço de custo e de venda, representados
 * por {@link #precoCusto precoCusto} e {@link #precoVenda precoVenda}, respectivamente.
 *
 * @author Thiago M. Baiense
 */
public class Produto {
    
    public static final int NULL_ID = -1;
    
    /** Representa a chave primária do produto registrada no banco de dados. <br>
     * Para instâncias ainda não cadastradas no banco de dados, seu valor deve 
     * ser -1 por padrão. Seus valores válidos são de {@literal id >= 0}.
     * 
     * @see #setId(int)
     * @see #getId() 
     */
    private int id = NULL_ID;
    
    /** Representa o nome completo do produto. O tamanho máximo é de
     * 32 caracteres.
     */
    private String nome;
    
    /** Representa a {@link japedidos.produto.Categoria categoria} do produto.
     * 
     * @see japedidos.produto.Categoria Categoria
     */
    private Categoria categoria;
    
    /** Preço de custo do produto, que é usado para calcular a lucratividade
     * dos pedidos. Será valido sempre que {@code precoCusto >= 0}.
     */
    private double precoCusto;
    
    /** Preço de venda do produto, que é usado para calcular a lucratividade
     * dos pedidos. Será valido sempre que {@code precoVenda >= 0}.
     */
    private double precoVenda;
    
    /** Representa a {@link japedidos.produto.Unidade unidade de medida} do 
     * produto.
     * 
     * @see japedidos.produto.Unidade Unidade
     */
    private Unidade unidadeMedida;
    
    /** Contém o autor e o momento da última alteração feita neste produto. 
     * Deverá ser nulo caso não tenha sofrido alteração.
     */
    private Registro alteracao;
    
    /** Determina o estado do produto: se este é um produto ativo, o valor deverá
     * ser {@code true} e {@code false} caso desativado. Produtos desativados 
     * não poderão ser incluídos em novos pedidos. Por padrão, instancias são
     * determinadas como ativos.
     */
    private boolean estaAtivo = true;
    
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
        this(1, nome, categoria, unidadeMedida, precoCusto, precoVenda);
        this.id = -1;
    }


    /** Constrói uma nova instância de {@code Produto}.
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
        IllegalArgumentsException exs = new IllegalArgumentsException();
        
        // Id
        try {
            this.setId(id);
        } catch (IllegalIdException ex) {
            exs.addCause(ex);
        }
        
        // Nome
        try {
            this.setNome(nome);
        } catch (IllegalNomeException ex) {
            exs.addCause(ex);
        }
        
        // Categoria
        if (categoria == null) {
            exs.addCause(new IllegalCategoriaException("Categoria não pode ser null."));
        } else {
            this.categoria = categoria;
        }
        
        // Unidade
        if (unidadeMedida == null) {
            exs.addCause(new IllegalUnidadeException("Unidade não pode ser null."));
        } else {
            this.unidadeMedida = unidadeMedida;
        }
        
        // Preco Custo
        try {
            this.setPrecoCusto(precoCusto);
        } catch (IllegalPrecoCustoException ex) {
            exs.addCause(ex);
        }
        
        // Preco venda
        try {
            this.setPrecoVenda(precoVenda);
        } catch (IllegalPrecoVendaException ex) {
            exs.addCause(ex);
        }
        
        if (exs.size() > 0) {
            throw exs;
        }
    }
    
    public boolean isNew() {
        return this.getId() == NULL_ID;
    }
    
    /** Atribui o parâmetro {@code id} como chave primária da instância {@code Produto}.
     * 
     * @param id {@code int} contendo valor >= 0 a ser usado como chave primária
     * da instância de {@code Produto}.
     * 
     * @throws IllegalArgumentException se {@code id < 0}.
     */
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalIdException("Id não pode ser menor que 0.");
        }
        
        this.id = id;
    }
    
    /** Retorna o valor de chave primária da instância contida em {@link #id id}.
     * 
     * @return -1 se a instâcia não for cadastrada no banco de dados e >=0 se houver
     * cadastro associado no banco de dados.
     */
    public int getId() {
        return this.id;
    }
    
    
    /** Atribui o valor {@code estado} à {@link #estaAtivo estaAtivo}.
     * 
     * @param estado {@code true} se o estado do produto deverá ser ativo e 
     * {@code false}, se deverá ser desativado.
     * 
     * @see #isAtivo()
     */
    public void setAtivo(boolean estado) {
        this.estaAtivo = estado;
    }
    
    /** Retorna o estado do produto.
     * 
     * @return {@code true} se o estado do produto é ativo e {@code false}, se 
     * desativado.
     * @see #setAtivo(boolean) 
     */
    public boolean isAtivo() {
        return this.estaAtivo;
    }
    
    
    /** Recebendo {@code alteracao}, atribui um autor e momento responsável por 
     * alguma alteração no produto, por meio de um objeto 
     * {@link japedidos.usuario.Registro Registro}.
     * 
     * @param alteracao objeto {@link japedidos.usuario.Registro Registro}.
     * @throws NullPointerException se {@code alteracao} for {@code null}.
     * @see #alteracao
     * @see #getAlteracao() 
     */
    public void setAlteracao(Registro alteracao) {
        if (alteracao == null) {
            throw new IllegalAlteracaoException("Alteração não pode ser nula.");
        }
        this.alteracao = alteracao;
    }
    
    
    /** Retorna a informação de alteração presente no produto.
     * 
     * @return um objeto {@link japedidos.usuario.Registro Registro}, contendo o
     * autor e o momento da alteração.
     * @see #alteracao
     * @see #setAlteracao(japedidos.usuario.Registro) 
     */
    public Registro getAlteracao() {
        return this.alteracao;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        nome = nome.trim();
        
        if (nome.isEmpty()) {
            throw new IllegalNomeException("Nome não pode ser vazio.");
        } else if (nome.length() > 32) {
            throw new IllegalNomeException("Nome não deve exceder 32 caractes.");
        } else {
            this.nome = nome;
        }
    }
    
    public void setPrecoVenda(double preco) {
        if (preco < 0) {
            throw new IllegalPrecoVendaException("Preço não pode ser inferior a 0.");
        } else {
            this.precoVenda = preco;
        }
    }
    
    public void setPrecoCusto(double preco) {
        if (preco < 0) {
            throw new IllegalPrecoCustoException("Preço não pode ser inferior a 0.");
        } else {
            this.precoCusto = preco;
        }
    }
    
    public Unidade getUnidade() {
        return this.unidadeMedida;
    }
    
    public Categoria getCategoria() {
        return this.categoria;
    }
    
    public double getPrecoVenda() {
        return this.precoVenda;
    }
    
    public double getPrecoCusto() {
        return this.precoCusto;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s)", this.getNome(), this.getUnidade().getAbreviacao());
    }
    
    public boolean equals(Object o) {
        Produto p = (Produto)o;
        return this.getId() == p.getId();
    }
}