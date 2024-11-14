package japedidos.clientes;

import japedidos.exception.IllegalCnpjException;
import java.lang.StringBuilder;

/**
 *
 * @author thiago
 */
public class CNPJ {
    private final String numerico;
    private final int MAX_CHAR = 18; // Número máximo de caracteres, incluindo pontuação. Ex. 11.222.333/0001-xx
    
    public static void main(String[] args) {
        System.out.println(eValido(toNumerico("37.306.079/0001-93")));
    }
    
    CNPJ(String str) {
        String numerico = toNumerico(str); // Retira pontuacao, se houver
        if(!eValido(numerico)) { 
                throw new IllegalCnpjException("CNPJ inválido");
        }
        this.numerico = numerico;
    }
    
    // Converters
    /** Converte str em uma String contendo apenas os dígitos presentes.
     * Não realiza validação, apenas remove caracteres que não sejam entre 0-9.
     * @return String contendo apenas dígitos. Retorna null, se str for null.*/
    public static String toNumerico(String str) {
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
    
    public static boolean eValido(String str) {
        boolean valido = true;
        
        str = str.trim();
        if (str.length() == 14) { // Tamanho menos pontuacoes
            for (int d = 0; d < 2; d++) { // Verifica os dígitos de verificação
                int soma = 0;
                int i = (d == 0) ? 5 : 6; // Número inicial de multiplicação
                for (int n = 0; n <= 11 + d; n++) { // Avança nos dígitos e multiplica por i
                    int num = Character.digit(str.charAt(n), 10);
                    soma += num * i--;
                    if (i < 2) {
                        i = 9;
                    }
                }
                int resto = soma % 11;
                int rDv = (resto < 2) ? 0 : 11 - resto;
                
                if (rDv != Character.digit(str.charAt(12 + d), 10)) {
                    valido = false;
                    break;
                }
            }
        } else {
            valido = false;
        }

        return valido;
    }
    
    @Override
    public String toString() {
        return this.numerico;
    }
}
