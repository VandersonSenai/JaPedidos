package japedidos;

import japedidos.usuario.Usuario;
import javax.swing.JOptionPane;

/**
 *
 * @author thiago
 */
public class AccessController {
    private static boolean loginObrigatorio = true;
    
    public static boolean isLoginObrigatorio() {
        return loginObrigatorio;
    }
    
    public static void verificarLogin() {
        if (loginObrigatorio && !Usuario.logado()) {
            JOptionPane.showMessageDialog(null, "É necessário estar logado para acessar essa funcionalidade.");
            System.exit(0);
        }
    }
}
