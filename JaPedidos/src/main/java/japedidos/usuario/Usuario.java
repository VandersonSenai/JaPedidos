package japedidos.usuario;

/** A classe Usuário representa um usuário cadastrado no banco de dados do software.
 * Nela, é possível instanciar novos objetos Usuário e definir o usuário atual, o
 * utilizador autenticado que atualmente está utilizando-o.
 * @author t.baiense
 */
public class Usuario {
    /** A chave primária do registro do Usuário no banco de dados. Ao ser
     instanciado, o construtor deverá receber o valor para atribuição à variável
     * diretamente do banco de dados configurado.*/
    public final int id;
    
    /** Identificador do utilizador que é usado durante a etapa de autenticação.*/
    public String login;
    
    /** Tipo de usuário atribuído ao registro do usuário.*/
    public Usuario.Tipo tipo;
    
    /** Define o tipo do usuário que o objeto se refere. "ATENDENTE" refere-se
     ao utilizador do software com permissões limitadas. "ADMINISTRADOR" refere-se
     ao utilizador com acesso a todas as funcionalidades implementadas.*/
    static public enum Tipo {ATENDENTE, ADMINISTRADOR}
    
    public static Usuario atual;
            
    /** Constroe um novo objeto Usuário com base em registro do banco de dados.
     * 
     * @param id Int que representa a chave primária do do registro de usuário
     * @param login String que identifica o usuário no software e é utilizado na
     * autenticação
     * @param tipo Usuario.Tipo que representa o tipo do usuário e especifica 
     * suas permissões
     */
    public Usuario(int id, String login, Usuario.Tipo tipo) {
            if(login == null || tipo == null) {
                throw new NullPointerException();
            } else if (id <= 0) {
                throw new IllegalArgumentException("id de usuario inválido");
            }

            this.id = id;
            this.login = login;
            this.tipo = tipo;
    }

    /** Constroe um novo objeto Usuário que representa o usuário atual do sistema
     * com base em registro do banco de dados. Deverá ser invocado logo após a 
     * autenticação bem-sucedida do utilizador.
     * 
     * @param usuario objeto Usuario que representa o utilizador atual
     */
    public static void carregarAtual(Usuario usuario) {
        if(Usuario.atual != null) {
            throw new IllegalStateException("não é possível carregar outro usuário com um já carregado.");
        }
        if(usuario == null) {
            throw new NullPointerException("Não é possivel carregar um usuário nulo.");
        }
        Usuario.atual = usuario;
    }
}

