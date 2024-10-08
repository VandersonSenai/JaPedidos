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
}
