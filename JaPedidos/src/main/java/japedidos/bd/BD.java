package japedidos.bd;

import japedidos.usuario.Registro;
import japedidos.usuario.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public final class BD {
    public static final String SGBD = "mysql";
    public static final String IP = "162.241.203.86";
    public static final String PORT = "3306";
    public static final String NAME = "titanw25_japedidos_hml";
    public static final String USER = "titanw25_japedidos_hml";
    public static final String USER_PWD = "seNai@2024proj";
    
//    public static final String IP = "localhost";
//    public static final String USER_PWD = "";
//    public static final String USER = "root";
    
    private BD() {}
    
    public final static Connection getConnection() {
        final String connStr = String.format("jdbc:%s://%s:%s/%s", SGBD, IP, PORT, NAME);
        
        Connection conn;
        try { // Gerar a conexão
            conn = DriverManager.getConnection(connStr, USER, USER_PWD);
            return conn;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Conexão com o banco de dados falhou", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        return null;
    }
    
    static public class Usuario {
        public static final String TABLE = "usuario";
        
        public static int insert(japedidos.usuario.Usuario u) {           
            if (u != null && u.getId() == -1 && u.getSenha() != null) { // Só cadastra se não houver id, e tiver senha inserida
                try {
                    Connection conn = BD.getConnection();
                    PreparedStatement insert = conn.prepareStatement(
                            String.format("INSERT INTO %s(nome, login, senha, tipo) VALUES (?, ?, ?, ?)", TABLE));
                    insert.setString(1, u.getNome());
                    insert.setString(2, u.getLogin());
                    insert.setString(3, u.getSenha());
                    insert.setString(4, u.getTipo().toString().toLowerCase());
                    
                    int r = insert.executeUpdate();

                    insert.close();
                    conn.close();
                    return r;
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de cadastro", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            } else {
                return 0;
            }
        }
        
        public static japedidos.usuario.Usuario selectLast() {
            try {
                Connection conn = BD.getConnection();
                PreparedStatement select = conn.prepareStatement(String.format("SELECT id, nome, login, tipo FROM %s ORDER BY id DESC LIMIT 1", TABLE));

                ResultSet rs = select.executeQuery();
                japedidos.usuario.Usuario[] usuarios = parse(rs);

                select.close();
                conn.close();

                if (usuarios == null) {
                    return null;
                }

                return usuarios[0];
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);
            }
            return null;
        }
        
        public static japedidos.usuario.Usuario[] selectAll() {
            try {
                Connection conn = BD.getConnection();
                PreparedStatement select = conn.prepareStatement(String.format("SELECT id, nome, login, tipo FROM %s", TABLE));

                ResultSet rs = select.executeQuery();
                japedidos.usuario.Usuario[] usuarios = parse(rs);

                select.close();
                conn.close();

                if (usuarios == null) {
                    return null;
                }

                return usuarios;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);
            }
            return null;
        }
        
        public static japedidos.usuario.Usuario selectById(int id) {
            if (id > 0) {
                try {
                    Connection conn = BD.getConnection();
                    PreparedStatement select = conn.prepareStatement(String.format("SELECT id, nome, login, tipo FROM %s WHERE id = ?", TABLE));
                    select.setInt(1, id);
                    
                    ResultSet rs = select.executeQuery();
                    japedidos.usuario.Usuario[] usuarios = parse(rs);
                    
                    select.close();
                    conn.close();
                    
                    if (usuarios == null) {
                        return null;
                    }
                    
                    return usuarios[0];
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);
                }
            }
            return null;
        }
        
        public static japedidos.usuario.Usuario[] parse(ResultSet rs) {
            ArrayList<japedidos.usuario.Usuario> uList = new ArrayList<japedidos.usuario.Usuario>();
            
            try {
                while (rs.next()) {
                    int id;
                    String nome, login;
                    japedidos.usuario.Usuario.Tipo tipo;
                    japedidos.usuario.Usuario usuario;
                    
                    id = rs.getInt(1);
                    nome = rs.getString(2);
                    login = rs.getString(3);
                    tipo = japedidos.usuario.Usuario.getTipo(rs.getString(4));
                    
                    usuario = new japedidos.usuario.Usuario(id, nome, login, tipo);
                    
                    uList.add(usuario);
                }
                
                if (uList.isEmpty()) {
                    return null;
                }
                
                japedidos.usuario.Usuario[] usuarios = new japedidos.usuario.Usuario[uList.size()];
                uList.toArray(usuarios);
                
                return usuarios;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de parse", JOptionPane.ERROR_MESSAGE);
            }
            
            return null;
        }
    }
    
    static public class Produto {
        public static final String TABLE = "produto";
        
        public static int insert(japedidos.produto.Produto p) {
            if (p != null && p.getId() == -1) {
                try {
                    Connection conn = BD.getConnection();
                    PreparedStatement insert = conn.prepareStatement(
                            String.format("INSERT INTO %s(id_categoria, id_unidade, nome, preco_venda, preco_custo, estado) "
                                    + "VALUES (?, ?, ?, ? , ? , ?)", TABLE));
                    insert.setInt(1, p.getCategoria().getId());
                    insert.setInt(2, p.getUnidade().getId());
                    insert.setString(3, p.getNome());
                    insert.setDouble(4, p.getPrecoVenda());
                    insert.setDouble(5, p.getPrecoCusto());
                    insert.setBoolean(6, p.isAtivo());
                    
                    int r = insert.executeUpdate();

                    insert.close();
                    conn.close();
                    return r;
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de cadastro", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            } else {
                return 0;
            }
        }
        
        public static int update(japedidos.produto.Produto p) {
            if (p != null && p.getId() != -1) {
                try {
                    Connection conn = BD.getConnection();
                    PreparedStatement update = conn.prepareStatement(
                            String.format("UPDATE %s SET "
                                    + "id_categoria = ?, "
                                    + "id_unidade = ?, "
                                    + "nome = ?, "
                                    + "preco_venda = ?, "
                                    + "preco_custo = ?, "
                                    + "estado = ?, "
                                    + "id_usuario_alt = ?, "
                                    + "dthr_alt = ? "
                                    + "WHERE id = ?", TABLE));
                    update.setInt(1, p.getCategoria().getId());
                    update.setInt(2, p.getUnidade().getId());
                    update.setString(3, p.getNome());
                    update.setDouble(4, p.getPrecoVenda());
                    update.setDouble(5, p.getPrecoCusto());
                    update.setBoolean(6, p.isAtivo());
                    
                    if (p.getAlteracao() == null) {
                        p.setAlteracao(new Registro());
                    }
                    
                    update.setInt(7, p.getAlteracao().AUTOR.getId());
                    update.setTimestamp(8, Timestamp.valueOf(p.getAlteracao().DATA_HORA));
                    update.setInt(9, p.getId());
                    
                    int r = update.executeUpdate();

                    update.close();
                    conn.close();

                    return r;
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de alteração de produto", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            } else {
                return 0;
            }
        }
        
        public static int delete(japedidos.produto.Produto p) {
            if (p != null && p.getId() != -1) {
                try {
                    Connection conn = BD.getConnection();
                    PreparedStatement delete = conn.prepareStatement(
                            String.format("DELETE FROM %s WHERE id = ?", TABLE));
                    
                    delete.setInt(1, p.getId());
                    int r = delete.executeUpdate();
                    
                    delete.close();
                    conn.close();

                    return r;
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao deletar produto", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            } else {
                return 0;
            }
        }
        
        public static japedidos.produto.Produto selectLast() {
            try {
                Connection conn = BD.getConnection();
                PreparedStatement select = conn.prepareStatement(
                    String.format("SELECT id, id_categoria, id_unidade, nome, preco_venda, preco_custo, id_usuario_alt, dthr_alt, estado FROM %s ORDER BY id DESC LIMIT 1", TABLE));
                ResultSet rs = select.executeQuery();
                
                japedidos.produto.Produto[] produto = parse(rs);
                
                if (produto == null) {
                    return null;
                }
                
                return produto[0];
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);
                return null;
            } 
            
        }
        
        public static japedidos.produto.Produto[] selectAll() {
            try {
                Connection conn = BD.getConnection();
                PreparedStatement select = conn.prepareStatement(String.format("SELECT id, id_categoria, id_unidade, nome, preco_venda, preco_custo, id_usuario_alt, dthr_alt, estado FROM %s", TABLE));
                ResultSet rs = select.executeQuery();
                
                japedidos.produto.Produto[] produtos = parse(rs);
                
                select.close();
                conn.close();
                
                return produtos;
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);    
            }
            return null;
        }
        
        public static japedidos.produto.Produto selectById(int id) {
            if (id > 0) {
                try {
                    Connection conn = BD.getConnection();
                    PreparedStatement select = conn.prepareStatement(
                        String.format("SELECT id, id_categoria, id_unidade, nome, preco_venda, preco_custo, id_usuario_alt, dthr_alt, estado FROM %s WHERE id = ?", TABLE));
                    select.setInt(1, id);
                    
                    ResultSet rs = select.executeQuery();

                    japedidos.produto.Produto[] produto = parse(rs);

                    if (produto == null) {
                        return null;
                    }

                    return produto[0];
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);
                }
            }
            return null;
        }
        
        public static japedidos.produto.Produto[] parse(ResultSet rs) {
            ArrayList<japedidos.produto.Produto> pList = new ArrayList<japedidos.produto.Produto>();
            
            try {
                while (rs.next()) {
                    int id_produto, id_categoria, id_unidade, id_usuario_alt;
                    String nome_produto;
                    double preco_venda, preco_custo;
                    boolean estado;
                    LocalDateTime dthr_alt = null;
                    
                    id_produto = rs.getInt(1);
                    id_categoria = rs.getInt(2);
                    id_unidade = rs.getInt(3);
                    nome_produto = rs.getString(4);
                    preco_venda = rs.getDouble(5);
                    preco_custo = rs.getDouble(6);
                    id_usuario_alt = rs.getInt(7);
                    
                    Timestamp ts = rs.getTimestamp(8);
                    if (ts != null) {
                        dthr_alt = ts.toLocalDateTime();
                    }
                    
                    estado = rs.getBoolean(9);
                    
                    // Busca de unidade e categoria
                    japedidos.produto.Categoria categoria = BD.Categoria.selectById(id_categoria);
                    japedidos.produto.Unidade unidade = BD.Unidade.selectById(id_unidade);
                    
                    japedidos.produto.Produto p = new japedidos.produto.Produto(id_produto, nome_produto, categoria, unidade, preco_custo, preco_venda );
                    p.setAtivo(estado);
                    
                    Registro alteracao = null;
                    if (id_usuario_alt != 0 && dthr_alt != null) {
                        alteracao = new Registro(BD.Usuario.selectById(id_usuario_alt), dthr_alt); // TODO: ADICIONAR BUSCA DE USUARIO
                    }
                    
                    if (alteracao != null) {
                        p.setAlteracao(alteracao);
                    }
                    
                    pList.add(p);
                }
                
                if (!pList.isEmpty()) {
                    japedidos.produto.Produto[] produtos = new japedidos.produto.Produto[pList.size()];
                    pList.toArray(produtos);
                    
                    return produtos;
                }
                
                return null;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de parse", JOptionPane.ERROR_MESSAGE);
            }
            
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
                    update.setInt(3, c.getId());

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
                    delete.setInt(1, c.getId());
                    
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
                
                japedidos.produto.Categoria[] categorias = parse(rs);
                
                select.close();
                conn.close();
                
                if (categorias == null) {
                    return null;
                }
                
                return categorias[0];
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
        
        public static japedidos.produto.Categoria selectById(int id) {
            if (id > 0) {
               try {
                    Connection conn = BD.getConnection();
                    PreparedStatement select = conn.prepareStatement(String.format("SELECT id, nome, descricao FROM %s WHERE id = ?", TABLE));
                    select.setInt(1, id);
                    ResultSet rs = select.executeQuery();
                    japedidos.produto.Categoria[] categoria = parse(rs);
                    
                    select.close();
                    conn.close();
                    
                    if (categoria == null) {
                        return null;
                    }
                    
                    return categoria[0];
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);    
                } 
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
                if (!c.isEmpty()) {
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
                    update.setInt(3, u.getId());

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
                    delete.setInt(1, u.getId());
                    
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
                
                japedidos.produto.Unidade[] unidades = parse(rs);
                
                select.close();
                conn.close();
                
                if (unidades == null) {
                    return null;
                }
                
                return unidades[0];
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
        
        public static japedidos.produto.Unidade selectById(int id) {
            if (id > 0) {
                try {
                    Connection conn = BD.getConnection();
                    PreparedStatement select = conn.prepareStatement(String.format("SELECT id, nome, abreviacao FROM %s WHERE id = ?", TABLE));
                    select.setInt(1, id);
                    
                    ResultSet rs = select.executeQuery();

                    japedidos.produto.Unidade[] unidades = parse(rs);
                    
                    select.close();
                    conn.close();
                    
                    if (unidades == null) {
                        return null;
                    }
                    return unidades[0];

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);    
                }
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
