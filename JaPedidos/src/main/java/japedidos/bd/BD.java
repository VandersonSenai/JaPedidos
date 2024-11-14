package japedidos.bd;

import japedidos.clientes.Cliente;
import japedidos.clientes.Cliente.InfoAdicional;
import japedidos.pedidos.Destino;
import japedidos.pedidos.EstadoPedido;
import japedidos.pedidos.InfoEntrega;
import japedidos.pedidos.TipoEntrega;
import japedidos.produto.ProdutoPedido;
import japedidos.usuario.Registro;
import japedidos.usuario.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public final class BD {
    public static final String SGBD = "mysql";
//    public static final String IP = "162.241.203.86";
    public static final String PORT = "3306";
    public static final String NAME = "japedidos";
//    public static final String NAME = "titanw25_japedidos_hml";
//    public static final String USER = "titanw25_japedidos_hml";
//    public static final String USER_PWD = "seNai@2024proj";
    
    public static final String IP = "10.0.0.109";
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
            System.exit(-1);
        }
        return null;
    }
    
    static public class Pedido {
        public static final String TABLE = "pedido";
        
        public static int insert(japedidos.pedidos.Pedido p) {
            int r;
            Connection conn = null;
            PreparedStatement insertPedido = null;
            PreparedStatement selectLastPedido = null;
            PreparedStatement insertCliente = null;
            PreparedStatement selectCliente = null;
            PreparedStatement insertDestino = null;
            PreparedStatement insertDestinatario = null;
            PreparedStatement insertProdutosPedido = null;
            PreparedStatement insertInfoAdicionalCliente = null;
            PreparedStatement insertEstadoPedido = null;
            
            if (p != null) {
                try {
                    conn = BD.getConnection();
                    conn.setAutoCommit(false);
                    
                    // Controle de cadastro de cliente
                    japedidos.clientes.Cliente cliente = p.getCliente();
                    int id_cliente;
                    if (cliente.isNew()) {
                        // Cadastro do novo cliente
                        insertCliente = conn.prepareStatement("INSERT INTO cliente(nome, telefone) VALUE (?, ?)");
                        insertCliente.setString(1, cliente.getNome());
                        insertCliente.setString(2, cliente.getTelefone());
                        insertCliente.executeUpdate();
                        // Busca do novo cliente já cadastrado
                        selectCliente = conn.prepareStatement("SELECT id FROM cliente ORDER BY id DESC LIMIT 1");
                        ResultSet rsCliente = selectCliente.executeQuery();
                        rsCliente.next();
                        id_cliente = rsCliente.getInt("id");
                    } else {
                        id_cliente = cliente.getId();
                    }
                    
                    // Inserção do pedido
                    insertPedido = conn.prepareStatement(
                            String.format("INSERT INTO %s(id_cliente, id_usuario_autor, dthr_criacao, tipo_entrega, dthr_entregar, preco_frete, tx_desconto, preco_final, dt_venc_pagamento, preco_custo_total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", TABLE));
                    int i=1;
                    insertPedido.setInt(i++, id_cliente);
                    
                    Registro reg = p.getRegistroCriacao();
                    insertPedido.setInt(i++, reg.AUTOR.getId());
                    insertPedido.setTimestamp(i++, Timestamp.valueOf(reg.DATA_HORA));
                    
                    InfoEntrega infoEntrega = p.getInfoEntrega();
                    insertPedido.setString(i++, infoEntrega.getTipoEntrega().toString());
                    insertPedido.setTimestamp(i++, Timestamp.valueOf(infoEntrega.getDataHoraEntregar())); // dthr_entregar
                    insertPedido.setDouble(i++, infoEntrega.getPrecoFrete()); // preco_frete
                    insertPedido.setInt(i++, (int)(100.0 * p.getTaxaDesconto())); // tx_desconto
                    insertPedido.setDouble(i++, p.getPrecoFinal());// preco_final
                    
                        // Definindo dt_venc_pagamento
                    LocalDate venc_LocalDate = p.getDataVencimentoPagamento();
                    Date venc_Date = null;
                    if (venc_LocalDate != null) {
                        venc_Date = Date.valueOf(venc_LocalDate);
                    }
                    insertPedido.setDate(i++, venc_Date);
                    
                    insertPedido.setDouble(i++, p.getCustoTotal());// preco_custo_total
                    
                    r = insertPedido.executeUpdate();
                    
                    // Busca do id do pedido cadastrado
                    int id_pedido = -1;
                    selectLastPedido = conn.prepareStatement("SELECT id FROM " + TABLE +" ORDER BY id DESC LIMIT 1");
                    ResultSet rsUltimoPedido = selectLastPedido.executeQuery();
                    if (rsUltimoPedido.next()) {
                        id_pedido = rsUltimoPedido.getInt("id");
                    }
                    
                    // Controle do cadastro de novo destino
                    if (p.getInfoEntrega().getTipoEntrega() == TipoEntrega.ENVIO) {
                        Destino destino = p.getInfoEntrega().getDestino();
                        
                         // Insere destino do pedido
                        insertDestino = conn.prepareStatement("INSERT INTO destino(id_pedido, logradouro, numero, bairro, cidade, estado, pais) VALUE (?, ?, ?, ?, ?, ?, ?)");
                        int j = 1;
                        insertDestino.setInt(j++, id_pedido);
                        insertDestino.setString(j++, destino.getLogradouro());
                        insertDestino.setString(j++, destino.getNumero());
                        insertDestino.setString(j++, destino.getBairro());
                        insertDestino.setString(j++, destino.getCidade());
                        insertDestino.setString(j++, destino.getEstado());
                        insertDestino.setString(j++, destino.getPais());
                        insertDestino.executeUpdate();
                    }
                    
                    //Controle do cadastro de destinatário
                    if (p.getInfoEntrega().getDestinatario() != null) {
                        insertDestinatario = conn.prepareStatement("INSERT INTO destinatario(id_pedido, info) VALUE (?, ?)");
                        insertDestinatario.setInt(1, id_pedido);
                        insertDestinatario.setString(2, p.getInfoEntrega().getDestinatario());
                        insertDestinatario.executeUpdate();
                    }
                    
                    // Inserindo produtos do pedido
                    String strStmt = "INSERT INTO produto_pedido(id_produto, id_pedido, quantidade, preco_venda, preco_custo, info_adicional) VALUES (?, ?, ?, ?, ?, ?)";
                    insertProdutosPedido = conn.prepareStatement(strStmt);
                    for (japedidos.produto.ProdutoPedido prodPed : p.getProdutos()) {
                        if (prodPed != null) {
                            int j = 1;
                            japedidos.produto.Produto prod = prodPed.getProduto();
                            insertProdutosPedido.setInt(j++, prod.getId());
                            insertProdutosPedido.setInt(j++, id_pedido);
                            insertProdutosPedido.setInt(j++, prodPed.getQuantidade());
                            insertProdutosPedido.setDouble(j++, prod.getPrecoVenda());
                            insertProdutosPedido.setDouble(j++, prod.getPrecoCusto());
                            String infoAdicional = prodPed.getInfoAdicional();
                            if (infoAdicional != null) {
                                insertProdutosPedido.setString(j++, infoAdicional);
                            } else {
                                insertProdutosPedido.setNull(j++, java.sql.Types.VARCHAR);
                            }
                            insertProdutosPedido.addBatch();
                        }
                    }
                    insertProdutosPedido.executeBatch();
                    
                    // Controle de cadastro de informação adicional do cliente
                    InfoAdicional infoAdicionalCliente = cliente.getInfoAdicional();
                    if (infoAdicionalCliente != null) {
                        if (infoAdicionalCliente.isPF()) { // Se for pessoa física
                            Cliente.InfoPF infoPF = (Cliente.InfoPF)infoAdicionalCliente;
                            insertInfoAdicionalCliente = conn.prepareStatement("INSERT INTO info_pf(id_pedido, nome_cliente, cpf) VALUE (?, ?, ?)");
                            insertInfoAdicionalCliente.setInt(1, id_pedido);
                            insertInfoAdicionalCliente.setString(2, infoPF.getNome());
                            insertInfoAdicionalCliente.setString(3, infoPF.getCpf());
                            insertInfoAdicionalCliente.executeUpdate();
                        } else {
                            Cliente.InfoPJ infoPJ = (Cliente.InfoPJ)infoAdicionalCliente;
                            insertInfoAdicionalCliente = conn.prepareStatement("INSERT INTO info_pj(id_pedido, cnpj, nome_fantasia, nome_empresarial) VALUE (?, ?, ?, ?)");
                            insertInfoAdicionalCliente.setInt(1, id_pedido);
                            insertInfoAdicionalCliente.setString(2, infoPJ.getCnpj());
                            insertInfoAdicionalCliente.setString(3, infoPJ.getNomeFantasia());
                            insertInfoAdicionalCliente.setString(4, infoPJ.getRazaoSocial());
                            insertInfoAdicionalCliente.executeUpdate();
                        }
                    }
                    
                    // Cadastro do estado inicial do pedido
                    EstadoPedido estadoInicial = p.getEstadoAtualPedido();
                    insertEstadoPedido = conn.prepareStatement("INSERT INTO est_andamento_pedido(id_est_andamento, id_pedido, id_usuario_autor, dthr_criacao) VALUE (?, ?, ?, ?)");
                    insertEstadoPedido.setInt(1, estadoInicial.ESTADO.ID);
                    insertEstadoPedido.setInt(2, id_pedido);
                    insertEstadoPedido.setInt(3, estadoInicial.AUTOR.getId());
                    insertEstadoPedido.setTimestamp(4, Timestamp.valueOf(estadoInicial.CRIACAO));
                    insertEstadoPedido.executeUpdate();
                    
                    conn.commit();
                } catch (SQLException e) {
                    if (conn != null) {
                        try {
                            conn.rollback();
                        } catch (SQLException ex) {
                            
                        }
                    }
                   
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de cadastro", JOptionPane.ERROR_MESSAGE);
                    r = -1;
                }
                
                if (conn != null) {
                    try {
                        conn.setAutoCommit(true);
                    } catch (SQLException ex) {
                       System.out.println("Não foi possível definir autoCommit como true");
                    }
                    if (insertPedido != null) {
                        try {
                           insertPedido.close();
                        } catch (SQLException ex) {
                           System.out.println("Não foi possível fechar conexão com o banco.");
                        }
                    }
                    if (selectLastPedido != null) {
                        try {
                           selectLastPedido.close();
                        } catch (SQLException ex) {
                           System.out.println("Não foi possível fechar conexão com o banco.");
                        }                        
                    }
                    if (insertCliente != null) {
                        try {
                           insertCliente.close();
                        } catch (SQLException ex) {
                           System.out.println("Não foi possível fechar conexão com o banco.");
                        }                        
                    }
                    if (selectCliente != null){
                        try {
                           selectCliente.close();
                        } catch (SQLException ex) {
                           System.out.println("Não foi possível fechar conexão com o banco.");
                        }                        
                    }
                    if (insertDestino != null) {
                        try {
                           insertDestino.close();
                        } catch (SQLException ex) {
                           System.out.println("Não foi possível fechar conexão com o banco.");
                        }                        
                    }
                    
                    if (insertDestinatario != null) {
                        try {
                           insertDestinatario.close();
                        } catch (SQLException ex) {
                           System.out.println("Não foi possível fechar conexão com o banco.");
                        }                        
                    }
                    if (insertProdutosPedido != null) {
                        try {
                           insertProdutosPedido.close();
                        } catch (SQLException ex) {
                           System.out.println("Não foi possível fechar conexão com o banco.");
                        }                        
                    }
                    
                    if (insertInfoAdicionalCliente != null) {
                        try {
                           insertInfoAdicionalCliente.close();
                        } catch (SQLException ex) {
                           System.out.println("Não foi possível fechar conexão com o banco.");
                        }                        
                    }
                    if (insertEstadoPedido != null) {
                        try {
                           insertEstadoPedido.close();
                        } catch (SQLException ex) {
                           System.out.println("Não foi possível fechar conexão com o banco.");
                        }                        
                    }
                    
                    
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        
                    }
                }
 
            
            } else {
                r = 0;
            }
            return r;
        }
    
    
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
            if (p != null) {
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
                    
                    if (japedidos.usuario.Usuario.getAtual() != null) {
                        if (p.getAlteracao() == null) {
                            p.setAlteracao(new Registro());
                        }

                        update.setInt(7, p.getAlteracao().AUTOR.getId());
                        update.setTimestamp(8, Timestamp.valueOf(p.getAlteracao().DATA_HORA));
                        
                    } else {
                        update.setNull(7, java.sql.Types.INTEGER);
                        update.setNull(8, java.sql.Types.TIMESTAMP);
                    }
                    update.setInt(9, p.getId());
                    
                    int r = update.executeUpdate();

                    update.close();
                    conn.close();

                    return r;
                } catch (Exception e) {
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
                PreparedStatement select = conn.prepareStatement(String.format("SELECT id, id_categoria, id_unidade, nome, preco_venda, preco_custo, id_usuario_alt, dthr_alt, estado FROM %s ORDER BY nome ASC", TABLE));
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
    
    static {
        japedidos.usuario.Usuario.setAtual(BD.Usuario.selectLast());
    }
}
