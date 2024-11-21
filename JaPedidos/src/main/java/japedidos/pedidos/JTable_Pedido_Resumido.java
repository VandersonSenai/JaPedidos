package japedidos.pedidos;

import japedidos.pedidos.PedidoTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author thiago
 */
public class JTable_Pedido_Resumido extends JPanel {
    private JTable table;
    private PedidoTableModel model;
    public JTable_Pedido_Resumido() {
        super(new GridLayout(1, 0));
        PedidoTableModel model = new PedidoTableModel();
        this.model = model;
        JTable table = new JTable(model);
        this.table = table;
//        getTable().setDefaultRenderer(EstadoPedido.class, new EstadoRenderer());
//        JComboBox<EstadoPedido> jcmb_estados = new JComboBox<>();
//        jcmb_estados.addItem(new EstadoPedido(Estado.ABERTO));
//        jcmb_estados.addItem(new EstadoPedido(Estado.AGUARDANDO_PAGAMENTO));
//        getTable().setDefaultEditor(EstadoPedido.class, new DefaultCellEditor(jcmb_estados));
        table.setPreferredScrollableViewportSize(new Dimension(600, 300));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
    }
    
    static class EstadoRenderer extends JComboBox<EstadoPedido> implements TableCellRenderer {
        public Component getTableCellRendererComponent(
                            JTable table, Object color,
                            boolean isSelected, boolean hasFocus,
                            int row, int column) {
            return this;
        } 
    }
    
    public JTable getTable() {
        return this.table;
    }
    
    public PedidoTableModel getModel() {
        return this.model;
    }
    
    public void preencher(japedidos.pedidos.Estado estado) {
        this.setEnabled(false);
        this.getModel().fillRows(japedidos.bd.BD.Pedido.selectByEstado(estado));
        this.setEnabled(true);
    }
    
    public static void test() {
        JFrame frame = new JFrame("Teste de pedido");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.yellow);
        JTable_Pedido_Resumido tabela = new JTable_Pedido_Resumido();
        tabela.setBackground(Color.red);
        tabela.getTable().setBorder(javax.swing.BorderFactory.createEmptyBorder());
        tabela.getTable().setOpaque(false);
        tabela.getTable().setBackground(new Color(0,0,0,0));
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
