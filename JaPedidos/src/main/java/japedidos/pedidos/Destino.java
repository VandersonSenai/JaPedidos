package japedidos.pedidos;

import japedidos.exception.IllegalArgumentsException;
import japedidos.exception.IllegalBairroException;
import japedidos.exception.IllegalCidadeException;
import japedidos.exception.IllegalEstadoException;
import japedidos.exception.IllegalLogradouroException;
import japedidos.exception.IllegalNumeroException;
import japedidos.exception.IllegalPaisException;

/**
 *
 * @author thiago
 */
public class Destino {
    public static final String DEFAULT_ESTADO = "ES";
    public static final String DEFAULT_PAIS = "Brasil";
    
    private String logradouro, numero, bairro, cidade, estado, pais;

    public Destino(String logradouro, String numero, String bairro, String cidade, String estado) {
        this(logradouro, numero, bairro, cidade, estado, DEFAULT_PAIS);
    }
    
    public Destino(String logradouro, String numero, String bairro, String cidade, String estado, String pais) {
        IllegalArgumentsException exs = new IllegalArgumentsException();
        
        try {
            setLogradouro(logradouro);
        } catch (IllegalLogradouroException ex) {
            exs.addCause(ex);
        }
        
        try {
            setNumero(numero);
        } catch (IllegalNumeroException ex) {
            exs.addCause(ex);
        }
        
        try {
            setBairro(bairro);
        } catch (IllegalBairroException ex) {
            exs.addCause(ex);
        }
        
        try {
            setCidade(cidade);
        } catch (IllegalCidadeException ex) {
            exs.addCause(ex);
        }
        
        try {
            setEstado(estado);
        } catch (IllegalEstadoException ex) {
            exs.addCause(ex);
        }
        
        try {
            setPais(pais);
        } catch (IllegalPaisException ex) {
            exs.addCause(ex);
        }
        
        if (exs.size() > 0) {
            throw exs;
        }
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
            throw new IllegalEstadoException("Estado é nulo.");
        } else {
           estado = estado.trim();
        
            if (estado.isEmpty()) {
                throw new IllegalEstadoException("Estado é vazio.");
            } else if (estado.length() > 45) {
                throw new IllegalEstadoException("Estado excede 45 caracteres.");
            } else {
                this.estado = estado;
            }
        }
    }
    
    private final void setPais(String pais) {
        if (pais == null) {
            throw new IllegalPaisException("País é nulo.");
        } else {
           pais = pais.trim();
        
            if (pais.isEmpty()) {
                throw new IllegalPaisException("País é vazio.");
            } else if (pais.length() > 45) {
                throw new IllegalPaisException("País excede 45 caracteres.");
            } else {
                this.pais = pais;
            }
        }
    }
    
    public String getLogradouro() {
        return this.logradouro;
    }
    
    public String getNumero() {
        return this.numero;
    }
    
    public String getBairro() {
        return this.bairro;
    }
    
    public String getCidade() {
        return this.cidade;
    }
    
    public String getEstado() {
        return this.estado;
    }
    
    public String getPais() {
        return this.pais;
    }
}
