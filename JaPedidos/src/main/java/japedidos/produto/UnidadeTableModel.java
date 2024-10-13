package japedidos.produto;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import japedidos.bd.BD;

/**
 *
 * @author thiago
 */
public class UnidadeTableModel extends AbstractTableModel {
    private final String[] columnNames;
    private final ArrayList<Unidade> data;
    
    public UnidadeTableModel() {
        columnNames = BD.Unidade.COLUMNS;
        data = new ArrayList<Unidade>();
    }
    
    public UnidadeTableModel(Unidade... unidades) {
        this();
        
        if (unidades == null) {
            return;
        }
        
        fillRows(unidades);
    }
    
    // Metodos próprios
    public void addRow(Unidade u) {
        if (u == null) {
            return;
        }
        
        data.add(u);
        int rowAdded = data.size()-1; // Index da linha adicionada
        fireTableRowsInserted(rowAdded, rowAdded);
    }
    
    public Unidade getRow(int row) {
        if (row < 0) {
            throw new IllegalArgumentException();
        }
        
        if (row >= data.size()) {
            return null;
        }
        
        return data.get(row);
    }
    
    public void fillRows(Unidade... unidades) {
        this.clearRows();
        if (unidades != null) {
            for (Unidade u : unidades) {
                if (u != null) {
                    data.add(u);
                }
            }
        }
        
        fireTableDataChanged();
    }
    
    public void clearRows() {
        data.clear();
    }
    
    public void refreshRows() {
        fillRows(BD.Unidade.selectAll());
    }
    
    // Implementação de métodos abstratos de AbstractTableModel 
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int col) {
        if (col < 0) {
            throw new IllegalArgumentException();
        }
        
        if (col > columnNames.length - 1) {
            return null;
        }
        
        return columnNames[col];
    }
    
    @Override
    public Class<?> getColumnClass(int col) {
        if (col < 0) {
            throw new IllegalArgumentException();
        }
        
        return switch(col) {
            case 0 -> Integer.class;    // Coluna id
            case 1,2 -> String.class;   // Coluna nome e abreviacao
            default -> Object.class;    // Coluna inválida
        };
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException();
        }
        
        if (row < data.size()) {
            return switch(col) {
                case 0 -> data.get(row).getId();
                case 1 -> data.get(row).getNome();
                case 2 -> data.get(row).getAbreviacao();
                default -> null;
            };
        }
        
        return null;
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException();
        }
        
        return false;
    }
}
