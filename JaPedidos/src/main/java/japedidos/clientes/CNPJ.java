package japedidos.clientes;

import java.lang.StringBuilder;

/**
 *
 * @author thiago
 */
public class CNPJ {
    private final String numerico;
    
    CNPJ(String str) {
        String numerico = toNumerico(str); // Retira pontuacao, se houver
        if(!eValido(numerico)) { 
                throw new IllegalArgumentException("CNPJ inválido");
        }
        this.numerico = numerico;
    }
    
    // Converters
    /** Converte str em uma String contendo apenas os dígitos presentes.
     * Não realiza validação, apenas remove caracteres que não sejam entre 0-9.
     * @return String contendo apenas dígitos. Retorna null, se str for null.*/
    public String toNumerico(String str) {
        if(str == null) {
            return null;
        }

        StringBuilder newStr = new StringBuilder();

        for (int i=0; i < str.length(); i++) {
            char c = str.charAt(i);

            if(Character.isDigit(c)) {
                newStr.append(c);
            }
        }

        return newStr.toString();
    }
    
    public boolean eValido(String str) {
//        
//        TODO: VALIDAÇÃO
//
        return true;
    }
    
    @Override
    public String toString() {
        return this.numerico;
    }
}
