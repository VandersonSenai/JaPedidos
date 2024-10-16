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
        BD.Produto.insert(new Produto("Coxinha", BD.Categoria.selectById(55), BD.Unidade.selectById(13), 10, 15));
    }
}
