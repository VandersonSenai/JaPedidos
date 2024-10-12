package japedidos.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public final class BD {
    public static final String SGBD = "mysql";
    public static final String IP = "10.0.0.107";
    public static final String PORT = "3306";
    public static final String NAME = "japedidos";
    public static final String USER = "root";
    public static final String USER_PWD = "tmb";
    
    private BD() {}
    
    public final static Connection getConnection() {
        final String connStr = String.format("jdbc:%s://%s:%s/%s", SGBD, IP, PORT, NAME);
        
        Connection conn;
        try { // Gerar a conexão
            conn = DriverManager.getConnection(connStr, USER, USER_PWD);
            return conn;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Conexão com o banco de dados falhou", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    static public class Categoria {
        public static final String TABLE = "categoria";
        public static final String[] COLUMNS = {"id", "nome", "descricao"};
        
        public static int insert(japedidos.produto.Categoria c) {
            if (c == null) {
                throw new NullPointerException();
            }
            
//            if (c.getId() != -1) { // Categoria não pode ser existente
//                throw new IllegalArgumentException();
//            }
            if (c.getId() == -1) { // Só cadastra se não houver id
                try {
                    Connection conn = BD.getConnection();
                    PreparedStatement insert = conn.prepareStatement(String.format("INSERT INTO %s(nome, descricao) VALUES (?, ?)", TABLE));
                    insert.setString(1, c.getNome());
                    insert.setString(2, c.getDescricao());
                    int r = insert.executeUpdate();

                    insert.close();
                    conn.close();
                    return r;
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de cadastro", JOptionPane.ERROR_MESSAGE);    
                }
            }
            
            return -1;
        }
        
        public static int update(japedidos.produto.Categoria c) {
            if (c == null) {
                throw new NullPointerException();
            }
            
            if (c.getId() != -1) { // Só atualiza se for categoria existente
                try {
                    Connection conn = BD.getConnection();
                    PreparedStatement update = conn.prepareStatement(String.format("UPDATE %s SET nome = ?, descricao = ? WHERE id = ?", TABLE));
                    update.setString(1, c.getNome());
                    update.setString(2, c.getDescricao());
                    update.setString(3, String.valueOf(c.getId()));

                    int r = update.executeUpdate();

                    update.close();
                    conn.close();

                    return r;
                } catch(SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de alteração de categoria", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            return -1;
        }
        
        public static int delete(japedidos.produto.Categoria c) {
            if (c == null) {
                throw new NullPointerException();
            }
            
            if (c.getId() != -1) { // Só deleta se for categoria já existente no banco
                try {
                    Connection conn = BD.getConnection();
                    PreparedStatement delete = conn.prepareStatement(String.format("DELETE FROM %s WHERE id = ?", TABLE));
                    delete.setString(1, String.valueOf(c.getId()));
                    
                    int r = delete.executeUpdate();
                    
                    delete.close();
                    conn.close();
                    
                    return r;
                } catch(SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de deleção de categoria", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            return -1;
        }
        
        public static japedidos.produto.Categoria selectLast() {
            try {
                Connection conn = BD.getConnection();
                PreparedStatement select = conn.prepareStatement(String.format("SELECT id, nome, descricao FROM %s ORDER BY id DESC LIMIT 1", TABLE));
                ResultSet rs = select.executeQuery();
                rs.next();
                
                japedidos.produto.Categoria categoria= new japedidos.produto.Categoria(rs.getInt(1), rs.getString(2), rs.getString(3));
                select.close();
                conn.close();
                
                return categoria;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);    
            }
            return null;
        }
        
        public static japedidos.produto.Categoria[] selectAll() {
            try {
                Connection conn = BD.getConnection();
                PreparedStatement select = conn.prepareStatement(String.format("SELECT id, nome, descricao FROM %s", TABLE));
                ResultSet rs = select.executeQuery();
                
                japedidos.produto.Categoria[] categorias = parse(rs);
                
                select.close();
                conn.close();
                
                return categorias;
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);    
            }
            return null;
        }
        
        public static japedidos.produto.Categoria[] parse(ResultSet rs) {
            ArrayList<japedidos.produto.Categoria> c = new ArrayList<japedidos.produto.Categoria>();
            
            try {
                while(rs.next()) {
                    final int id = rs.getInt(1);
                    final String nome = rs.getString(2);
                    final String descricao = rs.getString(3);
                    japedidos.produto.Categoria categoria = new japedidos.produto.Categoria(id, nome, descricao);
                    c.add(categoria);
                }
                japedidos.produto.Categoria[] categorias = new japedidos.produto.Categoria[c.size()];
                c.toArray(categorias);
                return categorias;
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de parse", JOptionPane.ERROR_MESSAGE);
                
            }
            
            return null;
        }
    }
}
