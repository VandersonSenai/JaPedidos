package japedidos.pedidos;

import japedidos.exception.IllegalBairroException;
import japedidos.exception.IllegalCidadeException;
import japedidos.exception.IllegalLogradouroException;
import japedidos.exception.IllegalNumeroException;

/**
 *
 * @author thiago
 */
public class Destino {
    public static final String DEFAULT_ESTADO = "Espírito Santo";
    public static final String DEFAULT_PAIS = "Brasil";
    
    private String logradouro, numero, bairro, cidade, estado, pais;

    public Destino(String logradouro, String numero, String bairro, String cidade, String estado) {
        this(logradouro, numero, bairro, cidade, estado, DEFAULT_PAIS);
    }
    
    public Destino(String logradouro, String numero, String bairro, String cidade, String estado, String pais) {
        setLogradouro(logradouro);
        setNumero(numero);
        setBairro(bairro);
        setCidade(cidade);
        setEstado(estado);
        setPais(pais);
    }

    public final void setLogradouro(String logradouro) {
        if (logradouro == null) {
            throw new IllegalLogradouroException("Logradouro é nulo.");
        } else {
           logradouro = logradouro.trim();
        
            if (logradouro.isEmpty()) {
                throw new IllegalLogradouroException("Logradouro é vazio.");
            } else if (logradouro.length() > 45) {
                throw new IllegalLogradouroException("Logradouro excede 45 caracteres.");
            } else {
                this.logradouro = logradouro;
            }
        }
    }

    public final void setNumero(String numero) {
        if (numero == null) {
            throw new IllegalNumeroException("Número é nulo.");
        } else {
           numero = numero.trim();
        
            if (numero.isEmpty()) {
                throw new IllegalNumeroException("Número é vazio.");
            } else if (numero.length() > 20) {
                throw new IllegalNumeroException("Número excede 20 caracteres.");
            } else {
                this.numero = numero;
            }
        }
    }

    public final void setBairro(String bairro) {
        if (bairro == null) {
            throw new IllegalBairroException("Bairro é nulo.");
        } else {
           bairro = bairro.trim();
        
            if (bairro.isEmpty()) {
                throw new IllegalBairroException("Bairro é vazio.");
            } else if (bairro.length() > 45) {
                throw new IllegalBairroException("Bairro excede 45 caracteres.");
            } else {
                this.bairro = bairro;
            }
        }
    }

    private final void setCidade(String cidade) {
        if (cidade == null) {
            throw new IllegalCidadeException("Cidade é nulo.");
        } else {
           cidade = cidade.trim();
        
            if (cidade.isEmpty()) {
                throw new IllegalCidadeException("Cidade é vazio.");
            } else if (cidade.length() > 45) {
                throw new IllegalCidadeException("Cidade excede 45 caracteres.");
            } else {
                this.cidade = cidade;
            }
        }
    }

    private final void setEstado(String estado) {
        if (estado == null) {
            throw new IllegalCidadeException("Estado é nulo.");
        } else {
           estado = estado.trim();
        
            if (estado.isEmpty()) {
                throw new IllegalCidadeException("Estado é vazio.");
            } else if (estado.length() > 45) {
                throw new IllegalCidadeException("Estado excede 45 caracteres.");
            } else {
                this.estado = estado;
            }
        }
    }
    
    private final void setPais(String pais) {
        if (pais == null) {
            throw new IllegalCidadeException("País é nulo.");
        } else {
           pais = pais.trim();
        
            if (pais.isEmpty()) {
                throw new IllegalCidadeException("País é vazio.");
            } else if (pais.length() > 45) {
                throw new IllegalCidadeException("País excede 45 caracteres.");
            } else {
                this.pais = pais;
            }
        }
    }
}
