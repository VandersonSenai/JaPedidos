package japedidos.clientes;

import japedidos.exception.IllegalArgumentsException;
import japedidos.exception.IllegalIdException;
import japedidos.exception.IllegalNomeException;
import japedidos.exception.IllegalTelefoneException;
import japedidos.pedidos.Pedido;

/**
 *
 * @author thiago
 */
public class Cliente {
    /** Representa a chave primária do registro do cliente no BD. Se o cliente
     houver registro associado no BD, seu id será maior que zero. Do contrário,
     será -1.*/
    private int id = NULL_ID;
    private String nome;
    private String telefone;
    private Pedido ultimoPedido;
    private InfoAdicional infoAdicional;
    public static int NULL_ID = -1;
    
//    public static void main(String[] args) {
//        Cliente teste = new Cliente("Jussara", "651651165");
//        System.out.println(teste);
//    }
//    
    public Cliente(String nome, String telefone) {
        this(1, nome, telefone);
        this.id = NULL_ID;
    }
    
    public Cliente(int id, String nome, String telefone) {
        IllegalArgumentsException exs = new IllegalArgumentsException();
        
        try {
            setId(id);
        } catch (IllegalIdException ex) {
            exs.addCause(ex);
        }
        
        try {
            setNome(nome); 
        } catch (IllegalNomeException ex) {
            exs.addCause(ex);
        }
        
        try {
            setTelefone(telefone);
        } catch (IllegalTelefoneException ex) {
            exs.addCause(ex);
        }
        
        if (exs.size() > 0) {
            throw exs;
        }
    }
    
    public boolean isNew() {
        return this.getId() == NULL_ID;
    }
    
    public void setNome(String nome) {
        if (nome == null) {
            throw new IllegalNomeException("Nome não pode ser null.");
        } else {
            nome = nome.trim();
            
            if (nome.isEmpty()) {
                throw new IllegalNomeException("Nome não pode ser vazio.");
            } else if (nome.length() > 80){
                throw new IllegalNomeException("Nome deve conter menos que 80 caracteres.");
            } else {
                this.nome = nome;
            }
        }
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setTelefone(String telefone) {
        if (telefone == null) {
            throw new IllegalTelefoneException("Telefone não pode ser null.");
        } else {
            telefone = telefone.trim();
            
            if (telefone.isEmpty()) {
                throw new IllegalTelefoneException("Telefone está vazio.");
            } else if (telefone.length() > 20){
                throw new IllegalTelefoneException("Telefone contém mais que 20 caracteres.");
            } else if (telefone.length() < 8){
                throw new IllegalTelefoneException("Telefone contém menos que 8 caracteres.");
            } else {
                this.telefone = telefone;
            }
        }
    }
    
    public String getTelefone() {
        return this.telefone;
    }
    
    public Cliente setId(int id) {
        if(id < 0) {
            throw new IllegalIdException("id inválido");
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
        this.infoAdicional = info;
    }
    
    @Override
    public String toString() {
        return this.getNome();
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
            
            public String getNome() {
                return this.nome;
            }
            
            public String getCpf() {
                return this.cpf;
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
        
        public String getNomeFantasia() {
            return this.nomeFantasia;
        }
        
        public String getRazaoSocial() {
            return this.razaoSocial;
        }
        
        public String getCnpj() {
            return this.cnpj;
        }
    }
}
