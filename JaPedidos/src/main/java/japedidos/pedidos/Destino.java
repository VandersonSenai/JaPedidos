package japedidos.pedidos;

/**
 *
 * @author thiago
 */
public class Destino {
    public static final String DEFAULT_ESTADO = "Espírito Santo";
    public static final String DEFAULT_PAIS = "Brasil";
    
    private String logradouro, numero, bairro, cidade, estado, pais;

    public Destino(String logradouro, String numero, String bairro, String cidade, String estado, String pais) {
        setLogradouro(logradouro);
        setNumero(numero);
        setBairro(bairro);
        setCidade(cidade);
        setEstado(estado);
        setPais(pais);
    }

    private final void setLogradouro(String logradouro) {
        if(logradouro == null) {
            throw new IllegalArgumentException();
        }

        this.logradouro = logradouro;
    }

    private final void setNumero(String numero) {
        if(numero == null) {
            throw new IllegalArgumentException();
        }

        this.numero = numero;
    }

    private final void setBairro(String bairro) {
        if(bairro == null) {
            throw new IllegalArgumentException();
        }

        this.bairro = bairro;
    }

    private final void setCidade(String cidade) {
        if(cidade == null) {
            throw new IllegalArgumentException();
        }

        this.cidade = cidade;
    }

    private final void setPais(String pais) {
        if(cidade == null) {
            throw new IllegalArgumentException();
        }

        this.pais = pais;
    }

    private final void setEstado(String estado) {
        if(cidade == null) {
            throw new IllegalArgumentException();
        }

        this.estado = estado;
    }
}
