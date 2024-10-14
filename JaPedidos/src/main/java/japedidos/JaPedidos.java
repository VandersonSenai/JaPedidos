package japedidos;

import japedidos.bd.BD;
import japedidos.produto.*;
import japedidos.clientes.*;
import javax.swing.JOptionPane;
import java.util.Arrays;

public class JaPedidos {

    public static void main(String[] args) {
        Cliente eu = new Cliente("thiago", "12313244");
        Cliente.InfoAdicional.PF info = new Cliente.InfoAdicional.PF(eu.nome, "928.814.780-26");
        eu.setInfoAdicional(info);
        System.out.print(eu.getInfoAdicional());
    }
}
