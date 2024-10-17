package japedidos.produto;

import japedidos.bd.BD;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author t.baiense
 */
public class ProdutoTableModel extends AbstractTableModel {
    public static final String[] COLUMNS = {"id", "nome", "categoria", "unidade", "preco custo", "preco venda", "estado"};
    
    private ArrayList<Produto> data;
    
    public ProdutoTableModel() {
        data = new ArrayList<Produto>();
    }
    
    public ProdutoTableModel(Produto... produtos) {
        this();
        
        if (produtos == null) {
            return;
        }
        
        fillRows(produtos);
    }
    
    public void fillRows(Produto... produtos) {
        if (produtos == null) {
            return;
        }
        
        for (Produto p : produtos) {
            if (p != null) {
                this.addRow(p);
            }
        }
        
    }
    
    public void addRow(Produto p) {
        if (p == null) {
            return;
        }
        
        data.add(p);
        int row = getRowCount() - 1;
        fireTableRowsInserted(row, row);
    }
    
    public Produto getRow(int row) {
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
        fillRows(BD.Produto.selectAll());
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
            case 1 -> data.get(row).getNome();
            case 2 -> data.get(row).getCategoria();
            case 3 -> data.get(row).getUnidade();
            case 4 -> data.get(row).getPrecoCusto();
            case 5 -> data.get(row).getPrecoVenda();
            case 6 -> data.get(row).isAtivo();
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
            case 3 -> Unidade.class;
            case 4, 5 -> Double.class;
            case 6 -> Boolean.class;
            default -> String.class;
        };
    }
    
    public boolean isCellEditable(int row, int col) {
        return false;
    }
    
}
