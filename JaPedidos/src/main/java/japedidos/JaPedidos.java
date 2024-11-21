package japedidos;

import javax.swing.UIManager;

public class JaPedidos {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new com.formdev.flatlaf.FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
//        Usuario.init();
        japedidos.usuario.JFrame_LoginUsuario frameLogin = new japedidos.usuario.JFrame_LoginUsuario();
        frameLogin.setVisible(true);
    }
}
