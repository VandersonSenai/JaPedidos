package japedidos;

import japedidos.bd.BD;
import japedidos.produto.*;
import japedidos.clientes.*;
import japedidos.usuario.Registro;
import japedidos.usuario.Usuario;
import javax.swing.JOptionPane;
import java.util.Arrays;

public class JaPedidos {

    public static void main(String[] args) {
        Usuario.init();
        
        for (Usuario u : BD.Usuario.selectAll()) {
            System.out.println(u);
        }
    }
}
