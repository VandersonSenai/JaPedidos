package japedidos.produto;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author t.baiense
 */
public class JTable_ProdutoPedido extends JPanel {
    private JTable table;
    private ProdutoPedidoTableModel model;
    
    public JTable_ProdutoPedido() {
        super(new GridLayout(1, 0));
        
        ProdutoPedidoTableModel model = new ProdutoPedidoTableModel();
        this.model = model;
        JTable table = new JTable(model);
        this.table = table;
        table.setPreferredScrollableViewportSize(new Dimension(730, 110));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
    }
    
    public JTable getTable() {
        return this.table;
    }
    
    public ProdutoPedidoTableModel getModel() {
        return this.model;
    }
    
    public static void test() {
        JFrame frame = new JFrame("Teste de produto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTable_ProdutoPedido tabela = new JTable_ProdutoPedido();
        frame.setContentPane(tabela);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            test();
        });
    }
}
