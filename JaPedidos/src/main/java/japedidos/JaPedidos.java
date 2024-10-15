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
        Usuario.setAtual(new Usuario(1, "Thiago", "t.baiense", Usuario.Tipo.ATENDENTE));
        
    }
}
