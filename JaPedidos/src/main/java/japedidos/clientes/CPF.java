package japedidos.clientes;

/** A classe CPF representa o número de Cadastro de Pessoa Física.
 * Seu formato é composto por 11 dígitos ao total, sendo os últimos 2 digitos
 * usados para a verificação dos anteriores. Um CPF é considerado formatado 
 * contendo apenas dígitos ou com os dígitos e pontuação, no formato
 * a seguir: 123456789-1x.
 * @author t.baiense
 */
public class CPF {
	
	/** Armazena informação de CPF no formato puramente numérico, 
	 * sem nenhuma pontuação.*/
	private final String numerico;
	
	public static final int DIG_VERIFICACAO = 2;
	public static final int DIG_ID = 9;
	public static final int TAM_CPF = DIG_ID+DIG_VERIFICACAO;
	
	/** Cria um objeto CPF a partir de uma String str. O parametro deve 
	 * conter apenas numeros e '.' e '-' possivelmente. Espaços são
	 * removidos do início e final de str aplicando str.trim().
	 * @param str String contendo a informação do CPF no formato numérico
	 * ou com pontuação, contendo 11 dígitos ao total. 
	 * @throws IllegalArgumentException se str for uma representação inválida de CPF.
	 */
	public CPF(String str) {
		String numerico = toNumerico(str); // Retira pontuacao, se houver
		if(!eValido(numerico)) {
			throw new IllegalArgumentException("CPF invalido");
		}
		this.numerico = numerico;
	}
	// Getters
	/** Retorna a informação do CPF no formato numérico (sem pontuação e
	 * com espaços removidos). 
	 * @return A representação de um CPF contendo apenas os 11 dígitos.*/
	public final String validarCPF() {return this.numerico;}
	
	// Converters
	/** Converte str em uma String contendo apenas os dígitos presentes.
	 * @return String contendo apenas dígitos. Retorna null, se str for null.*/
	public final static String toNumerico(String str) {
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
	
	// Checkers
	/** Valida uma representação numérica de CPF.
	 *  Para um CPF é considerado válido se conter 11 dígitos e se os dígitos 
	 *  verificadores informados forem iguais aos obtidos aplicando o algoritmo 
	 *  seguinte:
	 * 
	 * 1º Dígito verificador:
	 *  1. Multiplica-se o primeiro número por 10;
	 *  2. Multiplica-se o segundo número por 9 e soma-se ao anterior;
	 *  3. Multiplica-se o terceiro número por 8 e soma-se ao resultado da soma 
	 *     anterior;
	 *  4. Repete-se a multiplicação para os números seguintes até o 9º número, 
	 *  diminuindo o valor pelo qual são multiplicados em 1 a medida que for 
	 *  avançando. O número obtido deve ser somando ao resultado da soma anterior.
	 *  5. A soma final deve ser dividida por 11 e armazenado o resto da divisão.
	 *  6. Se o resto for inferior a 2, o dígito verificador deve ser 0. Se o resto
	 *  for superior ou igual a 2, o dígito verificador deve ser igual ao resultado
	 *  da subtração de 11 pelo resto da divisão.
	 *  
	 * 2º Dígito verificador:
	 *  1. Multiplica-se o primeiro número por 11;
	 *  2. Multiplica-se o segundo número por 9 e soma-se ao anterior;
	 *  3. Multiplica-se o terceiro número por 8 e soma-se ao resultado da soma 
	 *     anterior;
	 *  4. Repete-se a multiplicação para os números seguintes até o 10º número 
	 *  (primeiro dígito verificador), diminuindo o valor pelo qual são multiplicados
	 *  em 1 a medida que for avançando. O número obtido deve ser somando ao resultado 
	 *  da soma anterior.
	 *  5. A soma final deve ser dividida por 11 e armazenado o resto da divisão.
	 *  6. Se o resto for inferior a 2, o dígito verificador deve ser 0. Se o resto
	 *  for superior ou igual a 2, o dígito verificador deve ser igual ao resultado
	 *  da subtração de 11 pelo resto da divisão.
	 *  
	 * @param str String contendo apenas os 11 dígitos de um CPF.
	 * @return true se str contém um CPF válido.
	 * @return false se str não contém um CPF válido.
	 * @throws NullPointerException se str conter null.
	 */
	public final static boolean eValido(String str) { // Deve receber um cpf contendo somente os numeros
		if (str == null) {
			throw new NullPointerException("String de verificacao nao pode ser null");
		} else if (str.length() < TAM_CPF) {
			return false;
		}
		
		str = str.trim();
		if (str.length() < TAM_CPF || str.length() > TAM_CPF) {
			return false;
		}
		
		//String strNumerico = toNumerico(str); // Garante conversao para String contendo somente com numeros
		
		for (int d=1; d <= DIG_VERIFICACAO; d++) { // Valida os digitos verificadores
			short soma=0;
			for (int m=DIG_ID+d,i=0; m >=2; m--, i++) {// Avança nos números do CPF e soma cada um à multiplicacao por m
				int valor = Character.digit(str.charAt(i), 10); // Valor em inteiro do numero sendo verificado
				soma += valor * m;
			}
			int resto = soma % 11;
			int digVerificador = Character.digit(str.charAt(DIG_ID+d-1), 10);
			
			if(resto < 2 && digVerificador != 0) {
				return false;
			} else if (resto >= 2 && digVerificador != (TAM_CPF - resto)) {
				return false;
			}
		}
		return true;
	}
	
	// Equals
	public final boolean equals(String str) {
		String numerico = toNumerico(str);
		return this.numerico.equals(numerico);
	}
	
	public final boolean equals(CPF outroCPF) {
		if (outroCPF == null) {
			return false;
		}
		
		return this.equals(outroCPF.validarCPF());
	}
}


