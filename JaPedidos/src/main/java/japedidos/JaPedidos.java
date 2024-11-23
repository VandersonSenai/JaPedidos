package japedidos;

import japedidos.bd.DatabaseConfigurationFile;
import java.io.File;
import java.io.IOException;
import javax.swing.UIManager;

public class JaPedidos {

    public static final String ROOT_DIRECTORY = new File(System.getProperty("user.dir")).getAbsolutePath();
    
    public static void main(String[] args) throws IOException {
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
