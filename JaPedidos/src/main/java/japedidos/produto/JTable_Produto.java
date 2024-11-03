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
public class JTable_Produto extends JPanel {
    private JTable table;
    private ProdutoTableModel model;
    
    public JTable_Produto() {
        super(new GridLayout(1, 0));
        
        ProdutoTableModel model = new ProdutoTableModel();
        this.model = model;
        JTable table = new JTable(model);
        this.table = table;
        table.setPreferredScrollableViewportSize(new Dimension(600, 300));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
    }
    
    public JTable getTable() {
        return this.table;
    }
    
    public ProdutoTableModel getModel() {
        return this.model;
    }
    
    public static void test() {
        JFrame frame = new JFrame("Teste de produto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTable_Produto tabela = new JTable_Produto();
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
