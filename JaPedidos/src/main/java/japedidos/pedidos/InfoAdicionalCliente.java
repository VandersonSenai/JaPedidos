package japedidos.pedidos;

import japedidos.clientes.CPF;


/**
 *
 * @author thiago
 */
public abstract class InfoAdicionalCliente {
    protected Tipo tipo;
            
    static enum Tipo {PF, PJ}
    
    public String getTipo() {
        return switch(tipo) {
            case PF -> "Pessoa Física";
            case PJ -> "Pessoa Jurídica";
            default -> "Indefinido";
        };
    }
    
    static public class PF extends InfoAdicionalCliente {
        public final String nome;
        public final String cpf;

        public PF(String nome, String cpf) {
            this.tipo = Tipo.PF;
            this.nome = nome;
            this.cpf = new CPF(cpf).toString();
        }
    }
    
    static public class PJ extends InfoAdicionalCliente {
        public final String nomeFantasia;
        public final String razaoSocial;
        public final String cnpj;

        public PJ(String nomeFantasia, String razaoSocial, String cnpj) {
            this.tipo = Tipo.PJ;
            this.nomeFantasia = nomeFantasia;
            this.razaoSocial = razaoSocial;
            this.cnpj = cnpj;
        }
    }
}
