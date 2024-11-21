package japedidos;

import japedidos.usuario.Usuario;
import javax.swing.JOptionPane;

/**
 *
 * @author thiago
 */
public class AccessController {
    private static boolean verificarLogin = false;
    
    public static void verificarLogin() {
        if (verificarLogin && !Usuario.logado()) {
            JOptionPane.showMessageDialog(null, "É necessário estar logado para acessar essa funcionalidade.");
            System.exit(0);
        }
    }
}
