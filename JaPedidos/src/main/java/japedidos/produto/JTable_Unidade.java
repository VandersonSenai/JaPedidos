package japedidos.produto;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.GridLayout;


/**
 *
 * @author thiago
 */
public class JTable_Unidade extends JPanel {
    private UnidadeTableModel model;
    private JTable table;
    
    public JTable_Unidade() {
        this(new UnidadeTableModel());
    }
    
    public JTable_Unidade(UnidadeTableModel model) {
        super(new GridLayout(1, 0));
        
        if (model == null) {
            throw new NullPointerException();
        }
        
        JTable table = new JTable(model);
        this.table = table;
        this.model = model;
        table.setPreferredScrollableViewportSize(new Dimension(400, 200));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
    }
    
    public UnidadeTableModel getModel() {
        return this.model;
    }
    
    public JTable getJTable() {
        return this.table;
    }
    
    public void refresh() {
        
    }
    
    public static void test() {
        JFrame frame = new JFrame("Tabela de unidades de medida");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTable_Unidade table = new JTable_Unidade();
        table.getModel().addRow(new Unidade(100, "Kilograma", "KG"));
        table.setOpaque(true);
        
        frame.setContentPane(table);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JTable_Unidade::test);
    }
}
