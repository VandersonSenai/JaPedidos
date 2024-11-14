package japedidos.usuario;

import japedidos.bd.BD;

/** A classe Usuário representa um usuário cadastrado no banco de dados do software.
 * Nela, é possível instanciar novos objetos Usuário e definir o usuário atual, o
 * utilizador autenticado que atualmente está utilizando-o.
 * @author t.baiense
 */
public class Usuario {
    
    /** A chave primária do registro do Usuário no banco de dados. Ao ser
     instanciado, o construtor deverá receber o valor para atribuição à variável
     * diretamente do banco de dados configurado.*/
    private int id = -1;
    
    /** Identificador do utilizador que é usado durante a etapa de autenticação.*/
    public String login;
    
    public String nome;
    
    private String senha;
    
    /** Tipo de usuário atribuído ao registro do usuário.*/
    private Usuario.Tipo tipo;
    
    /** Define o tipo do usuário que o objeto se refere. "ATENDENTE" refere-se
     ao utilizador do software com permissões limitadas. "ADMINISTRADOR" refere-se
     ao utilizador com acesso a todas as funcionalidades implementadas.*/
    static public enum Tipo {
        ATENDENTE ("atendente"), 
        ADMINISTRADOR ("administrador");
    
        Tipo(String string) {
            this.string = string;
        }
        
        private final String string;
        
        @Override
        public String toString() {
            return this.string;
        }
        
    }
    
    private static Usuario atual;
    
    /** Constroe um novo objeto Usuário para registro no banco de dados.
     * 
     * @param login String que identifica o usuário no software e é utilizado na
     * autenticação
     * @param tipo Usuario.Tipo que representa o tipo do usuário e especifica 
     * suas permissões
     */
    public Usuario(String nome, String login, String senha, Usuario.Tipo tipo) {
            setNome(nome);
            setLogin(login);
            setSenha(senha);
            setTipo(tipo);
    }
    
    /** Constroe um novo objeto Usuário com base em registro do banco de dados.
     * 
     * @param id Int que representa a chave primária do do registro de usuário
     * @param login String que identifica o usuário no software e é utilizado na
     * autenticação
     * @param tipo Usuario.Tipo que representa o tipo do usuário e especifica 
     * suas permissões
     */
    public Usuario(int id, String nome, String login, Usuario.Tipo tipo) {
            if (id <= 0) {
                throw new IllegalArgumentException("id de usuario inválido");
            }
            
            this.id = id;
            setNome(nome);
            setLogin(login);
            setTipo(tipo);
    }
    
    // Somente para exibição
    public Usuario(int id, String nome, Usuario.Tipo tipo) {
            if (id <= 0) {
                throw new IllegalArgumentException("id de usuario inválido");
            }
            
            this.id = id;
            setNome(nome);
            setLogin(null);
            setTipo(tipo);
    }

    /** Constroe um novo objeto Usuário que representa o usuário atual do sistema
     * com base em registro do banco de dados. Deverá ser invocado logo após a 
     * autenticação bem-sucedida do utilizador.
     * 
     * @param usuario objeto Usuario que representa o utilizador atual
     */
    public static void setAtual(Usuario usuario) {
        if(Usuario.atual != null) {
            throw new IllegalStateException("não é possível carregar outro usuário com um já carregado.");
        }
        if(usuario == null) {
            throw new NullPointerException("Não é possivel carregar um usuário nulo.");
        }
        Usuario.atual = usuario;
    }
    
    public void setSenha(String senha) {
        if (senha == null) {
            throw new NullPointerException();
        }
        
        senha = senha.trim();
        
        if (senha.isEmpty() || senha.length() > 32) {
            throw new IllegalArgumentException();
        }
        
        this.senha = senha;
    }
    
    public String getSenha() {
        return this.senha;
    }
    
    public static Usuario getAtual() {
        return Usuario.atual;
    }
    
    public void setNome(String nome) {
        if (nome == null) {
            throw new NullPointerException();
        }
        nome = nome.trim();
        
        if (nome.isEmpty() || nome.length() > 48) {
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
    
    private void setLogin(String login) {
        if (login == null) {
//            throw new NullPointerException();
            this.login = null;
        }
        login = login.trim();
        
        if (login.isEmpty() || login.length() > 48) {
            throw new IllegalArgumentException();
        }
        
        this.login = login;
    }
    
    public String getLogin() {
        return this.login;
    }
    
    private void setTipo(Usuario.Tipo tipo) {
        if (tipo == null) {
            throw new NullPointerException();
        }
        
        this.tipo = tipo;
    }
    
    public Usuario.Tipo getTipo() {
        return this.tipo;
    }
    
    public static Usuario.Tipo getTipo(String tipo) {
        tipo = tipo.trim().toLowerCase();
        
        if (tipo.equals("atendente")) {
            return Usuario.Tipo.ATENDENTE;
        } else if (tipo.equals("administrador")) {
            return Usuario.Tipo.ADMINISTRADOR;
        } else {
            return null;
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s)", this.getNome(), this.getLogin());
    }
    
    public static void init() {
        Usuario.setAtual(BD.Usuario.selectById(1));
    }
}

