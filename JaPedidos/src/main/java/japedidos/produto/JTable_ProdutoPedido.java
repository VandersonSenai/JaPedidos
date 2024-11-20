package japedidos.produto;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.AbstractCellEditor;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

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
        table.getColumn(ProdutoPedidoTableModel.COLUMNS[ProdutoPedidoTableModel.COL_QUANTIDADE]).setCellEditor(new SpinnerCellEditor());
        table.setRowHeight(table.getFont().getSize() * 2);
        table.setPreferredScrollableViewportSize(new Dimension(730, 110));
        table.setFillsViewportHeight(true);
        TableColumn column;
        column = table.getColumnModel().getColumn(ProdutoPedidoTableModel.COL_CODIGO);
        column.setMaxWidth(100);
        column.setPreferredWidth(60);
        column = table.getColumnModel().getColumn(ProdutoPedidoTableModel.COL_NOME);
        column.setPreferredWidth(300);
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
    
    static public class SpinnerCellEditor extends AbstractCellEditor implements TableCellEditor {
        int valor;
        JSpinner spinner;
        
        public SpinnerCellEditor() {
            this.spinner = new JSpinner();
        }
        
        @Override
        public Object getCellEditorValue() {
            valor = (Integer)spinner.getValue();
            if (valor < 1) {
                valor = 1;
            }
            return valor;
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table,
                                      Object value,
                                      boolean isSelected,
                                      int row,
                                      int column) {
            if (column == ProdutoPedidoTableModel.COL_QUANTIDADE) {
                this.spinner.setValue((Integer)value);
                valor = (Integer)value;
                return this.spinner;
            }
            return null;
        }
    }
}
