package japedidos.clientes;

import japedidos.pedidos.Pedido;

/**
 *
 * @author thiago
 */
public class Cliente {
    /** Representa a chave primária do registro do cliente no BD. Se o cliente
     houver registro associado no BD, seu id será maior que zero. Do contrário,
     será -1.*/
    private int id = -1;
    public final String nome;
    public final String telefone;
    public Pedido ultimoPedido;
    private InfoAdicional infoAdicional;
            
    public Cliente(String nome, String telefone) {
        if (nome == null) {
            throw new NullPointerException("nome informado é nulo");
        }
        if (telefone == null) {
            throw new NullPointerException("telefone informado é nulo");
        }
        if (nome.length() < 3 || nome.length() > 80 || telefone.length() < 8 || telefone.length() > 20) {
            throw new IllegalArgumentException();
        }
        
        this.nome = nome;
        this.telefone = telefone;
    }
    
    public Cliente setId(int id) {
        if(id < 1) {
            throw new IllegalArgumentException("id inválido");
        }
        
        this.id = id;
        return this;
    }
    
    public int getId() {
        return this.id;
    }
    
    public Cliente.InfoAdicional getInfoAdicional() {
        return this.infoAdicional;
    }
    
    public void setInfoAdicional(InfoAdicional info) {
        if (info == null) {
            throw new NullPointerException();
        }
        
        this.infoAdicional = info;
    }
    
    static public abstract class InfoAdicional {
        protected Tipo tipo;

        public static enum Tipo {PF, PJ}

        public Tipo getTipo() {
            return this.tipo;
        }
        
        public boolean isPF() {
            if (this.getTipo() == Tipo.PF) {
                return true;
            }
            return false;
        }
        
        public boolean isPJ() {
            if (this.getTipo() == Tipo.PJ) {
                return true;
            }
            return false;
        }
        
        @Override
        public String toString() {
            if (this.getTipo() == Tipo.PF) {
                return "Pessoa Física";
            } else {
                return "Pessoa Jurídica";
            }
        }
    }
    static public class InfoPF extends InfoAdicional {
            public String nome;
            public String cpf;

            public InfoPF() {
                this.tipo = Tipo.PF;
            }
            
            public InfoPF(String nome, String cpf) {
                this();
                this.nome = nome;
                this.cpf = new CPF(cpf).toString();
            }
        }
    static public class InfoPJ extends InfoAdicional {
        public String nomeFantasia;
        public String razaoSocial;
        public String cnpj;

        public InfoPJ() {
            this.tipo = Tipo.PJ;
        }
        
        public InfoPJ(String nomeFantasia, String razaoSocial, String cnpj) {
            this();
            this.nomeFantasia = nomeFantasia;
            this.razaoSocial = razaoSocial;
            this.cnpj = cnpj;
        }
    }
}
