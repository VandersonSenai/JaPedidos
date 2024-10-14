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
        logradouro = logradouro.trim();
        
        if (logradouro.isEmpty() || logradouro.length() > 45) {
            throw new IllegalArgumentException();
        }
        
        this.logradouro = logradouro;
    }

    private final void setNumero(String numero) {
        numero = numero.trim();
        
        if (numero.isEmpty() || numero.length() > 20) {
            throw new IllegalArgumentException();
        }
        
        this.numero = numero;
    }

    private final void setBairro(String bairro) {
        bairro = bairro.trim();
        
        if(bairro.isEmpty() || bairro.length() > 45) {
            throw new IllegalArgumentException();
        }

        this.bairro = bairro;
    }

    private final void setCidade(String cidade) {
        cidade = cidade.trim();
        
        if(cidade.isEmpty() || cidade.length() > 45) {
            throw new IllegalArgumentException();
        }

        this.cidade = cidade;
    }

    private final void setEstado(String estado) {
        estado = estado.trim();
        
        if(estado.isEmpty() || estado.length() > 45) {
            throw new IllegalArgumentException();
        }

        this.estado = estado;
    }
    
    private final void setPais(String pais) {
        pais = pais.trim();
        
        if(pais.isEmpty() || pais.length() > 45) {
            throw new IllegalArgumentException();
        }

        this.pais = pais;
    }
}
