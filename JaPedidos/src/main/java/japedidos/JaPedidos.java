package japedidos;

import japedidos.bd.BD;
import japedidos.produto.*;
import japedidos.clientes.*;
import japedidos.usuario.Usuario;
import javax.swing.JOptionPane;
import java.util.Arrays;

public class JaPedidos {

    public static void main(String[] args) {
        Usuario.setAtual(new Usuario(1, "Thiago", Usuario.Tipo.ATENDENTE));
        
        Produto coxinha = new Produto("Coxinha", BD.Categoria.selectLast(), BD.Unidade.selectLast(), 10, 5);
        System.out.print(coxinha.getCategoria());
    }
}
