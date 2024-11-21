package japedidos.produto;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author t.baiense
 */
public final class ProdutoPedidoTableModel extends AbstractTableModel {
    public static final String[] COLUMNS = {"Código", "Nome", "Categoria", "Qtd.", "Und.", "Preço", "Subtotal"};
    
    public static final int COL_CODIGO = 0;
    public static final int COL_NOME = 1;
    public static final int COL_CATEGORIA = 2;
    public static final int COL_QUANTIDADE = 3;
    public static final int COL_UNIDADE = 4;
    public static final int COL_PRECO = 5;
    public static final int COL_SUBTOTAL = 6;
    
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
        clearRows();
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
        // Incrementa quantidade de produto se já existir na tabela
        for (int i = 0; i < getRowCount(); i++) {
            ProdutoPedido prodPed = getRow(i);
            if (prodPed.getProduto().equals(p.getProduto())) {
                int novaQuant = prodPed.getQuantidade();
                novaQuant += p.getQuantidade();
                prodPed.setQuantidade(novaQuant);
                fireTableRowsUpdated(i, i);
                return;
            }
        }
        
        data.add(p);
        int r = getRowCount()-1;
        fireTableRowsInserted(r, r);
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
    
    public ProdutoPedido[] getRows() {
        int rows = getRowCount();
        if (rows > 0) {
            return this.data.toArray(new ProdutoPedido[getRowCount()]);
        } else {
            return null;
        }
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
            case COL_CODIGO -> data.get(row).getProduto().getId();
            case COL_NOME -> data.get(row).getProduto().getNome();
            case COL_CATEGORIA -> data.get(row).getProduto().getCategoria();
            case COL_QUANTIDADE -> data.get(row).getQuantidade();
            case COL_UNIDADE -> data.get(row).getProduto().getUnidade().getAbreviacao();
            case COL_PRECO -> String.format("R$ %1.2f", data.get(row).getProduto().getPrecoVenda());
            case COL_SUBTOTAL -> String.format("R$ %1.2f", data.get(row).getProduto().getPrecoVenda() * data.get(row).getQuantidade());
            default -> null;
        };
    }
    
    @Override
    public Class<?> getColumnClass(int col) {
        if (col < 0) {
            throw new IllegalArgumentException();
        }
        
        return switch (col) {
            case COL_CODIGO -> Integer.class; // Código
            case COL_NOME -> String.class; // Nome
            case COL_CATEGORIA -> Categoria.class; 
            case COL_QUANTIDADE -> Integer.class; // Quantidade
            case COL_UNIDADE -> Unidade.class;
            case COL_PRECO -> String.class; // Precos
            case COL_SUBTOTAL -> String.class; // Precos
            default -> String.class;
        };
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        if (col == COL_QUANTIDADE) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        if (col == COL_QUANTIDADE && value != null) {
            ProdutoPedido mudar = this.getRow(row);
            if (mudar != null) {
                mudar.setQuantidade((Integer)value);
                fireTableCellUpdated(row, col);
            }
        } 
    }
    
}
