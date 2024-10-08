package japedidos;

import japedidos.clientes.*;
import javax.swing.JOptionPane;

public class JaPedidos {

    public static void main(String[] args) {
        Cliente thiago;
        try {
            thiago = new Cliente("Thiago", "+55 (27) 99969-4568");
            thiago.setId(2);
            InfoAdicionalCliente.PJ info_thiago = new InfoAdicionalCliente.PJ("bebidas mil", "distribuidora bebidas regianne ltda", "6468464656/0001");
            
            System.out.print(info_thiago.getTipo());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
