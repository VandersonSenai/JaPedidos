package japedidos.usuario;

import java.time.LocalDateTime;

/** Representa o registro de uma ação, contendo um autor e o momento exato
 * do acontecimento.
 *
 * @author Thiago M. Baiense
 */
public class Registro {
    public final Usuario AUTOR;
    public final LocalDateTime DATA_HORA;
    
    public Registro() {
        this.AUTOR = Usuario.getAtual();
        this.DATA_HORA = LocalDateTime.now();
    }
    
    public Registro(Usuario autor, LocalDateTime dataHora) {
        if (autor == null || dataHora == null) {
            throw new NullPointerException();
        }
        this.AUTOR = autor;
        this.DATA_HORA = dataHora;
    }
    
    public String toString() {
        return String.format("%s - %s", AUTOR.toString(), DATA_HORA.toString());
    }
}
