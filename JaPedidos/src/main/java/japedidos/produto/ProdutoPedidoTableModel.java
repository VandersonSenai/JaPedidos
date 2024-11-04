package japedidos.produto;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author t.baiense
 */
public final class ProdutoPedidoTableModel extends AbstractTableModel {
    public static final String[] COLUMNS = {"Cód.", "Nome", "Categoria", "Quant.", "Unidade", "Preço"};
    
    private ArrayList<ProdutoPedido> data;
    
    public ProdutoPedidoTableModel() {
        data = new ArrayList<>();
    }
    
    public ProdutoPedidoTableModel(ProdutoPedido... produtos) {
        this();
        
        if (produtos == null) {
            return;
        }
        
        fillRows(produtos);
    }
    
    public void fillRows(ProdutoPedido... produtos) {
        if (produtos == null) {
            return;
        }
        
        for (ProdutoPedido p : produtos) {
            if (p != null) {
                this.addRow(p);
            }
        }
        
    }
    
    public void removeRow(int row) {
        if (row < 0) {
            throw new IllegalArgumentException();
        }
        
        if (row < getRowCount()) {
            data.remove(row);
            fireTableRowsDeleted(row, row);
        }
    }
    
    public void addRow(ProdutoPedido p) {
        if (p == null) {
            return;
        }
        
        data.add(p);
        int row = getRowCount() - 1;
        fireTableRowsInserted(row, row);
    }
    
    public ProdutoPedido getRow(int row) {
        if (row < 0) {
            throw new IllegalArgumentException();
        }
        
        if (row > getRowCount() -1) {
            return null;
        }
        
        return data.get(row);
    }
    
    public void clearRows() {
        this.data.clear();
        fireTableDataChanged();
    }
    
    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }
    
    @Override
    public String getColumnName(int col) {
        if (col < 0) {
            throw new IllegalArgumentException();
        }
        
        if (col >= getColumnCount()) {
            return null;
        }
        
        return COLUMNS[col];
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        if (col < 0 || row < 0) {
            throw new IllegalArgumentException();
        }
        
        if (row >= getRowCount() || col >= getColumnCount()) {
            return null;
        }
        
        return switch (col) {
            case 0 -> data.get(row).getProduto().getId();
            case 1 -> data.get(row).getProduto().getNome();
            case 2 -> data.get(row).getProduto().getCategoria();
            case 3 -> data.get(row).getQuantidade();
            case 4 -> data.get(row).getProduto().getUnidade().getAbreviacao();
            case 5 -> String.format("R$ %1.2f", data.get(row).getProduto().getPrecoVenda());
            default -> null;
        };
    }
    
    @Override
    public Class<?> getColumnClass(int col) {
        if (col < 0) {
            throw new IllegalArgumentException();
        }
        
        return switch (col) {
            case 0 -> Integer.class; // Código
            case 1 -> String.class; // Nome
            case 2 -> Categoria.class; 
            case 3 -> Integer.class; // Quantidade
            case 4 -> Unidade.class;
            case 5 -> String.class; // Precos
            default -> String.class;
        };
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
    
}
