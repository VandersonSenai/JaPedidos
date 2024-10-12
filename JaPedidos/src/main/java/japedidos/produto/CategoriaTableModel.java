package japedidos.produto;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import japedidos.produto.Categoria;
import japedidos.bd.BD;


/**
 *
 * @author thiago
 */
public class CategoriaTableModel extends AbstractTableModel {
    private String[] columnName;
    private ArrayList<Categoria> data;
    
    public CategoriaTableModel() {
        columnName = BD.Categoria.COLUMNS;
        data = new ArrayList<Categoria>();
    }
    
    public CategoriaTableModel(Categoria... categorias) {
        this();
        for (Categoria c: categorias) {
            if (c != null) {
                this.addRow(c);
            }
        }
    }
    
    public int getColumnCount() {
        return columnName.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        if (col >= 0 && col < getColumnCount()) {
            return columnName[col];
        } else {
            return null;
        }
    }

    public Object getValueAt(int row, int col) {
        switch(col) {
            case 0 -> {
                int id = Integer.valueOf(data.get(row).getId());
                if (id == -1) {
                    return null;
                } else {
                    return id;
                }
            } // Coluna id
            case 1 -> {return data.get(row).getNome();}
            case 2 -> {return data.get(row).getDescricao();}
            default -> {return null;}
        }
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public Class<?> getColumnClass(int col) {
        switch(col) {
            case 0 -> {return Integer.class;}
            case 1, 2 -> {return String.class;}
            default -> {return Object.class;}
        }
    }

    public void addRow(Categoria categoria) {
        if(categoria == null) {
            return;
        }
        
        data.add(categoria);
        int lastRow = getRowCount() - 1;
        fireTableRowsInserted(lastRow, lastRow);
    }
    
    public Categoria getRow(int row) {
        if (row < 0 || row > data.size() - 1) {
            throw new IllegalArgumentException();
        }
        
        return data.get(row);
    }
    
    public void fillRows(Categoria... categorias) {
        this.clearRows();
        for(Categoria c : categorias) {
            if (c != null) {
                this.data.add(c);
            }
        }
        fireTableDataChanged();
    }
    
    public void clearRows() {
        data.clear();
    }
}
