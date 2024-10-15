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
        
        public static int insert(japedidos.produto.Categoria c) {
//            if (c.getId() != -1) { // Categoria não pode ser existente 
//                throw new IllegalArgumentException();
//            }
            if (c != null && c.getId() == -1) { // Só cadastra se não houver id
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
            
            return 0;
        }
        
        public static int update(japedidos.produto.Categoria c) {
            if (c != null && c.getId() != -1) { // Só atualiza se for categoria existente
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
            
            return 0;
        }
        
        public static int delete(japedidos.produto.Categoria c) {
            if (c != null && c.getId() != -1) { // Só deleta se for categoria já existente no banco
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
            
            return 0;
        }
        
        public static japedidos.produto.Categoria selectLast() {
            try {
                Connection conn = BD.getConnection();
                PreparedStatement select = conn.prepareStatement(String.format("SELECT id, nome, descricao FROM %s ORDER BY id DESC LIMIT 1", TABLE));
                ResultSet rs = select.executeQuery();
                japedidos.produto.Categoria categoria = null;
                
                if (rs.next()) {
                    categoria = new japedidos.produto.Categoria(rs.getInt(1), rs.getString(2), rs.getString(3));
                }
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
            
            try {
                
                ArrayList<japedidos.produto.Categoria> c = new ArrayList<japedidos.produto.Categoria>();
                while(rs.next()) {
                    final int id = rs.getInt(1);
                    final String nome = rs.getString(2);
                    final String descricao = rs.getString(3);
                    japedidos.produto.Categoria categoria = new japedidos.produto.Categoria(id, nome, descricao);
                    c.add(categoria);
                }
                if (c.size() > 0) {
                    japedidos.produto.Categoria[] categorias = new japedidos.produto.Categoria[c.size()];
                    c.toArray(categorias);
                    return categorias;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de parse", JOptionPane.ERROR_MESSAGE);
                
            }
            
            return null;
        }
    }

    static public class Unidade {
        public static final String TABLE = "unidade";
        
        public static int insert(japedidos.produto.Unidade u) {
//            if (u.getId() != -1) { // Unidade não pode ser existente
//                throw new IllegalArgumentException();
//            }
            if (u != null && u.getId() == -1) { // Só cadastra se não houver id
                try {
                    Connection conn = BD.getConnection();
                    PreparedStatement insert = conn.prepareStatement(String.format("INSERT INTO %s(nome, abreviacao) VALUES (?, ?)", TABLE));
                    insert.setString(1, u.getNome());
                    insert.setString(2, u.getAbreviacao());
                    int r = insert.executeUpdate();

                    insert.close();
                    conn.close();
                    return r;
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de cadastro", JOptionPane.ERROR_MESSAGE);    
                }
            }
            return 0;
        }
        public static int update(japedidos.produto.Unidade u) {
            if (u != null && u.getId() != -1) { // Só atualiza se for unidade existente
                try {
                    Connection conn = BD.getConnection();
                    PreparedStatement update = conn.prepareStatement(String.format("UPDATE %s SET nome = ?, abreviacao = ? WHERE id = ?", TABLE));
                    update.setString(1, u.getNome());
                    update.setString(2, u.getAbreviacao());
                    update.setString(3, String.valueOf(u.getId()));

                    int r = update.executeUpdate();

                    update.close();
                    conn.close();

                    return r;
                } catch(SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de alteração de unidade", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            return 0;
        }
        public static int delete(japedidos.produto.Unidade u) {
            if (u != null && u.getId() != -1) { // Só deleta se for unidade já existente no banco
                try {
                    Connection conn = BD.getConnection();
                    PreparedStatement delete = conn.prepareStatement(String.format("DELETE FROM %s WHERE id = ?", TABLE));
                    delete.setString(1, String.valueOf(u.getId()));
                    
                    int r = delete.executeUpdate();
                    
                    delete.close();
                    conn.close();
                    
                    return r;
                } catch(SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de deleção de unidade", JOptionPane.ERROR_MESSAGE);
                }
            }
            return 0;
        }
        
        public static japedidos.produto.Unidade selectLast() {
            try {
                Connection conn = BD.getConnection();
                PreparedStatement select = conn.prepareStatement(String.format("SELECT id, nome, abreviacao FROM %s ORDER BY id DESC LIMIT 1", TABLE));
                ResultSet rs = select.executeQuery();
                japedidos.produto.Unidade unidade = null;
                
                if (rs.next()) {
                    unidade = new japedidos.produto.Unidade(rs.getInt(1), rs.getString(2), rs.getString(3));
                }
                
                select.close();
                conn.close();
                
                return unidade;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);    
            }
            return null;
        }
        
        public static japedidos.produto.Unidade[] selectAll() {
            try {
                Connection conn = BD.getConnection();
                PreparedStatement select = conn.prepareStatement(String.format("SELECT id, nome, abreviacao FROM %s", TABLE));
                ResultSet rs = select.executeQuery();
                
                japedidos.produto.Unidade[] unidades = parse(rs);
                
                select.close();
                conn.close();
                
                return unidades;
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);    
            }
            return null;
        }
        
        public static japedidos.produto.Unidade[] parse(ResultSet rs) {
            try {
                ArrayList<japedidos.produto.Unidade> u = new ArrayList<japedidos.produto.Unidade>();
                while(rs.next()) {
                    final int id = rs.getInt(1);
                    final String nome = rs.getString(2);
                    final String abreviacao = rs.getString(3);
                    japedidos.produto.Unidade unidade = new japedidos.produto.Unidade(id, nome, abreviacao);
                    u.add(unidade);
                }
                if (u.size() > 0) {
                    japedidos.produto.Unidade[] unidades = new japedidos.produto.Unidade[u.size()];
                    u.toArray(unidades);
                    return unidades;                    
                } else {
                    return null;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de parse", JOptionPane.ERROR_MESSAGE);
            }
            return null;
        }
    }
}
