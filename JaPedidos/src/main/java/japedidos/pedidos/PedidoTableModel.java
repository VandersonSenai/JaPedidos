package japedidos.pedidos;

import japedidos.bd.BD;
import japedidos.produto.Categoria;
import japedidos.produto.Produto;
import japedidos.produto.Unidade;
import java.util.ArrayList;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
/**
 *
 * @author thiago
 */
public class PedidoTableModel extends AbstractTableModel {
    public static final int COMPLETO = 1;
    public static final int RESUMIDO = 2;
    
    public static final String[] COLUMNS = {"Cód.", "Cliente", "Telefone", "Data ent.", "Hora ent.", "Total", "Estado atual"};
    
    private ArrayList<Pedido> data;
    
    public PedidoTableModel() {
        data = new ArrayList<Pedido>();
    }
    
    public PedidoTableModel(Pedido... pedidos) {
        this();
        
        if (pedidos == null) {
            return;
        }
        
        fillRows(pedidos);
    }
    
    public void fillRows(Pedido... pedidos) {
        this.clearRows();
        if (pedidos == null) {
            return;
        }
        
        for (Pedido p : pedidos) {
            if (p != null) {
                this.addRow(p);
            }
        }
        
    }
    
    public void addRow(Pedido p) {
        if (p == null) {
            return;
        }
        
        data.add(p);
        int row = getRowCount() - 1;
        fireTableRowsInserted(row, row);
    }
    
    public Pedido getRow(int row) {
        if (row < 0) {
            throw new IllegalArgumentException();
        }
        
        if (row > getRowCount() -1) {
            return null;
        }
        
        return data.get(row);
    }
    
    public void refresh() {
        this.clearRows();
//        fillRows(BD.Pedido.selectAll());
    }
    
    public void clearRows() {
        this.data.clear();
        fireTableDataChanged();
    }
    
    public int getColumnCount() {
        return COLUMNS.length;
    }
    
    public int getRowCount() {
        return data.size();
    }
    
    public String getColumnName(int col) {
        if (col < 0) {
            throw new IllegalArgumentException();
        }
        
        if (col >= getColumnCount()) {
            return null;
        }
        
        return COLUMNS[col];
    }
    
    public Object getValueAt(int row, int col) {
        if (col < 0 || row < 0) {
            throw new IllegalArgumentException();
        }
        
        if (row >= getRowCount() || col >= getColumnCount()) {
            return null;
        }
        
        return switch (col) {
            case 0 -> data.get(row).getId();
            case 1 -> data.get(row).getCliente().getNome();
            case 2 -> data.get(row).getCliente().getTelefone();
            case 3 -> data.get(row).getInfoEntrega().getDataHoraEntregar().toLocalDate();
            case 4 -> data.get(row).getInfoEntrega().getDataHoraEntregar().toLocalTime();
            case 5 -> String.format("R$ %1.2f", data.get(row).getPrecoFinal());
            case 6 -> data.get(row).getEstadoAtualPedido().ESTADO;
            default -> null;
        };
    }
    
    public Class<?> getColumnClass(int col) {
        if (col < 0) {
            throw new IllegalArgumentException();
        }
        
        return switch (col) {
            case 0 -> Integer.class;
            case 1 -> String.class;
            case 2 -> Categoria.class;
            case 3 -> java.time.LocalDate.class;
            case 4 -> java.time.LocalTime.class;
            case 5 -> String.class; // Precos
            case 6 -> japedidos.pedidos.Estado.class;
            default -> String.class;
        };
    }
    
    public boolean isCellEditable(int row, int col) {
        return false;
    }
    
}
