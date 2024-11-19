package japedidos.bd;

import japedidos.exception.IllegalArgumentsException;
import japedidos.pedidos.Estado;
import japedidos.pedidos.InfoEntrega;
import japedidos.pedidos.TipoEntrega;
import japedidos.usuario.Registro;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLTimeoutException;
import java.time.LocalDate;
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
//    
//    public static final String IP = "localhost";
//    public static final String NAME = "japedidos";
//    public static final String USER = "root";
//    public static final String USER_PWD = "";
    
    // Testes
    public static void main(String[] args) throws Exception {
        japedidos.pedidos.Pedido ped = BD.Pedido.selectById(20);
        japedidos.pedidos.Pedido ped17 = BD.Pedido.selectById(17);
//        System.out.println(ped.getProdutoCount());
        japedidos.clientes.Cliente.InfoAdicional info20 = ped.getCliente().getInfoAdicional();
        japedidos.clientes.Cliente.InfoPF infoPF = (japedidos.clientes.Cliente.InfoPF)info20;
        infoPF.nome = "Antônio Carlos";
        
        final String connStr = String.format("jdbc:%s://%s:%s/%s", SGBD, IP, PORT, NAME);
        Connection conn = null;
        try { // Gerar a conexão
            conn = DriverManager.getConnection(connStr, USER, USER_PWD);
            conn.setAutoCommit(false);
            Pedido.atualizarInfoAdicionalCliente(ped, info20, conn);
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Conexão com o banco de dados falhou", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        
        if (conn != null) {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
    
    private BD() {}
    
    public final static Connection getConnection() {
        final String connStr = String.format("jdbc:%s://%s:%s/%s", SGBD, IP, PORT, NAME);
        
        Connection conn;
        try { // Gerar a conexão
            conn = DriverManager.getConnection(connStr, USER, USER_PWD);
            return conn;
        } catch (SQLTimeoutException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Banco demorou a responder", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Conexão com o banco de dados falhou", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        return null;
    }
    
    static public class InfoAdicional {
        public static int insert(int id_pedido, japedidos.clientes.Cliente.InfoAdicional infoAdicional, Connection conn) throws SQLException {
            if (infoAdicional == null) {
                throw  new SQLException("Não é possível inserir info adicional nula");
            }
            
            int r;
            if (infoAdicional instanceof japedidos.clientes.Cliente.InfoPF) {
                japedidos.clientes.Cliente.InfoPF infoPF = (japedidos.clientes.Cliente.InfoPF)infoAdicional;
                r = InfoPF.insert(id_pedido, infoPF, conn);
            } else {
                japedidos.clientes.Cliente.InfoPJ infoPJ = (japedidos.clientes.Cliente.InfoPJ)infoAdicional;
                r = InfoPJ.insert(id_pedido, infoPJ, conn);
            }
            
            return r;
        }
    
        public static int update(int id_pedido, japedidos.clientes.Cliente.InfoAdicional infoAdicional, Connection conn) throws SQLException {
            if (infoAdicional == null) {
                throw  new SQLException("Não é possível atualizar info adicional nula");
            }
            
            int r;
            if (infoAdicional instanceof japedidos.clientes.Cliente.InfoPF) {
                japedidos.clientes.Cliente.InfoPF infoPF = (japedidos.clientes.Cliente.InfoPF)infoAdicional;
                r = InfoPF.update(id_pedido, infoPF, conn);
            } else {
                japedidos.clientes.Cliente.InfoPJ infoPJ = (japedidos.clientes.Cliente.InfoPJ)infoAdicional;
                r = InfoPJ.update(id_pedido, infoPJ, conn);
            }
            
            return r;
        }
        
        public static int delete(int id_pedido, japedidos.clientes.Cliente.InfoAdicional infoAdicional, Connection conn) throws SQLException {
            if (infoAdicional == null) {
                throw  new SQLException("Não é possível atualizar info adicional nula");
            }
            
            int r;
            if (infoAdicional instanceof japedidos.clientes.Cliente.InfoPF) {
                r = InfoPF.delete(id_pedido, conn);
            } else {
                r = InfoPJ.delete(id_pedido, conn);
            }
            
            return r;
        }
    }
    
    static public class InfoPF {
        public static final String TABLE = "info_pf";
        
        public static int insert(int id_pedido, japedidos.clientes.Cliente.InfoPF infoPF, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (id_pedido < 1) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
            if (infoPF == null) {
                throw new NullPointerException("Informação adicional de Pessoa Física do cliente do pedido é nula");
            }
            
            
            PreparedStatement insertInfoPF = null;
            int r = 0;
            Throwable doThrow = null;
            try {
                insertInfoPF = conn.prepareStatement(String.format("INSERT INTO %s(id_pedido, nome_cliente, cpf) VALUE (?, ?, ?)", TABLE));
                int j = 1;
                insertInfoPF.setInt(j++, id_pedido);
                insertInfoPF.setString(j++, infoPF.getNome());
                insertInfoPF.setString(j++, infoPF.getCpf());
                r = insertInfoPF.executeUpdate();
                
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (insertInfoPF != null) {
                    insertInfoPF.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao atualizar informação adicional de Pessoa Física do cliente do pedido: " + doThrow.getMessage());
            }
            
            return r;
        }
    
        public static int update(int id_pedido, japedidos.clientes.Cliente.InfoPF infoPF, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (id_pedido < 1) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
            if (infoPF == null) {
                throw new NullPointerException("informação adicional de Pessoa Física do cliente do pedido é nula");
            }
            
            
            PreparedStatement updateInfoPF = null;
            int r = 0;
            Throwable doThrow = null;
            try {
                updateInfoPF = conn.prepareStatement(String.format("UPDATE %s SET nome_cliente = ?, cpf = ? WHERE id_pedido = ?", TABLE));
                int j = 1;
                updateInfoPF.setString(j++, infoPF.getNome());
                updateInfoPF.setString(j++, infoPF.getCpf());
                updateInfoPF.setInt(j++, id_pedido);
                r = updateInfoPF.executeUpdate();
                
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (updateInfoPF != null) {
                    updateInfoPF.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao atualizar informação adicional de Pessoa Física do cliente do pedido: " + doThrow.getMessage());
            }
            
            return r;
        }
        
        public static int delete(int id_pedido, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (id_pedido < 1) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
            
            PreparedStatement deleteInfoPF = null;
            int r = 0;
            Throwable doThrow = null;
            try {
                deleteInfoPF = conn.prepareStatement(String.format("DELETE FROM %s WHERE id_pedido = ?", TABLE));
                deleteInfoPF.setInt(1, id_pedido);
                r = deleteInfoPF.executeUpdate();
                
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (deleteInfoPF != null) {
                    deleteInfoPF.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao deletar informação adicional de Pessoa Física do cliente do pedido: " + doThrow.getMessage());
            }
            
            return r;
        }
    }
    
    static public class InfoPJ {
        public static final String TABLE = "info_pj";
        
        public static int insert(int id_pedido, japedidos.clientes.Cliente.InfoPJ infoPJ, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (id_pedido < 1) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
            if (infoPJ == null) {
                throw new NullPointerException("Informação adicional de Pessoa Jurídica do cliente do pedido é nula");
            }
            
            
            PreparedStatement insertInfoPJ = null;
            int r = 0;
            Throwable doThrow = null;
            try {
                insertInfoPJ = conn.prepareStatement(String.format("INSERT INTO %s(id_pedido, nome_empresarial, nome_fantasia, cnpj) VALUE (?, ?, ?, ?)", TABLE));
                int j = 1;
                insertInfoPJ.setInt(j++, id_pedido);
                insertInfoPJ.setString(j++, infoPJ.getRazaoSocial());
                insertInfoPJ.setString(j++, infoPJ.getNomeFantasia());
                insertInfoPJ.setString(j++, infoPJ.getCnpj());
                r = insertInfoPJ.executeUpdate();
                
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (insertInfoPJ != null) {
                    insertInfoPJ.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao atualizar informação adicional de Pessoa Jurídica do cliente do pedido: " + doThrow.getMessage());
            }
            
            return r;
        }
    
        public static int update(int id_pedido, japedidos.clientes.Cliente.InfoPJ infoPJ, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (id_pedido < 1) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
            if (infoPJ == null) {
                throw new NullPointerException("informação adicional de Pessoa Jurídica do cliente do pedido é nula");
            }
            
            
            PreparedStatement updateInfoPF = null;
            int r = 0;
            Throwable doThrow = null;
            try {
                updateInfoPF = conn.prepareStatement(String.format("UPDATE %s SET nome_empresarial = ?, nome_fantasia = ?, cnpj = ? WHERE id_pedido = ?", TABLE));
                int j = 1;
                updateInfoPF.setString(j++, infoPJ.getRazaoSocial());
                updateInfoPF.setString(j++, infoPJ.getNomeFantasia());
                updateInfoPF.setString(j++, infoPJ.getCnpj());
                updateInfoPF.setInt(j++, id_pedido);
                r = updateInfoPF.executeUpdate();
                
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (updateInfoPF != null) {
                    updateInfoPF.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao atualizar informação adicional de Pessoa Jurídica do cliente do pedido: " + doThrow.getMessage());
            }
            
            return r;
        }
        
        public static int delete(int id_pedido, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (id_pedido < 1) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
            
            PreparedStatement deleteInfoPJ = null;
            int r = 0;
            Throwable doThrow = null;
            try {
                deleteInfoPJ = conn.prepareStatement(String.format("DELETE FROM %s WHERE id_pedido = ?", TABLE));
                deleteInfoPJ.setInt(1, id_pedido);
                r = deleteInfoPJ.executeUpdate();
                
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (deleteInfoPJ != null) {
                    deleteInfoPJ.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao deletar informação adicional de Pessoa Jurídica do cliente do pedido: " + doThrow.getMessage());
            }
            
            return r;
        }
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
                        japedidos.pedidos.Destino destino = p.getInfoEntrega().getDestino();
                        
                        // Insere destino do pedido
                        Destino.insert(id_pedido, destino, conn);
                    }
                    
                    //Controle do cadastro de destinatário
                    if (p.getInfoEntrega().getDestinatario() != null) {
                        BD.Destinatario.insert(id_pedido, p.getInfoEntrega().getDestinatario(), conn);
                    }
                    
                    // Inserindo produtos do pedido
                    String strStmt = "INSERT INTO produto_pedido(id_produto, id_pedido, quantidade, preco_venda, preco_custo, info_adicional) VALUES (?, ?, ?, ?, ?, ?)";
                    insertProdutosPedido = conn.prepareStatement(strStmt);
                    japedidos.produto.ProdutoPedido[] produtosPedido = p.getProdutos();
                    if (produtosPedido != null) {
                        for (japedidos.produto.ProdutoPedido prodPed : produtosPedido) {
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
                    } else {
                        throw new SQLException("Pedido não possui produtos");
                    }
                    
                    // Controle de cadastro de informação adicional do cliente
                    japedidos.clientes.Cliente.InfoAdicional infoAdicionalCliente = cliente.getInfoAdicional();
                    if (infoAdicionalCliente != null) {
                        InfoAdicional.insert(id_pedido, infoAdicionalCliente, conn);
                    }
                    
                    // Cadastro do estado inicial do pedido
                    japedidos.pedidos.EstadoPedido estadoInicial = p.getEstadoAtualPedido();
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
    
        // TODO: TESTAR
        public static int update(japedidos.pedidos.Pedido pAntigo, japedidos.pedidos.Pedido pNovo) {
            int r = 0;
            Connection conn = null;
            PreparedStatement updatePedido = null;
            
            if (pAntigo != null && pNovo != null) {
                try {
                    conn = BD.getConnection();
                    conn.setAutoCommit(false);
                    
                    // Atualização da tabela do pedido
                    updatePedido = conn.prepareStatement(
                            String.format("UPDATE %s SET id_usuario_alt = ?, dthr_alt = CURRENT_TIMESTAMP(3), dthr_entregar = ?, tx_desconto = ? WHERE id = ?", TABLE));
                    int i=1;
                    
                    Registro reg = new Registro();
                    updatePedido.setInt(i++, reg.AUTOR.getId());
//                    updatePedido.setTimestamp(i++, Timestamp.valueOf(reg.DATA_HORA));
                    
                    InfoEntrega infoEntrega = pNovo.getInfoEntrega();
                    if (!infoEntrega.equals(pAntigo.getInfoEntrega())) {
                        atualizarInfoEntrega(pAntigo, infoEntrega, conn); // Atualiza destino, destinatario, e preco do frete
                    }
                    
                    updatePedido.setTimestamp(i++, Timestamp.valueOf(infoEntrega.getDataHoraEntregar())); // dthr_entregar
                    updatePedido.setInt(i++, (int)(100.0 * pNovo.getTaxaDesconto())); // tx_desconto
                    
                    updatePedido.setInt(i++, pAntigo.getId());
                    r += updatePedido.executeUpdate();
                    
                    // Atualizando produtos do pedido (DEVE VIR APÓS A ATUALIZAÇÃO DA TAXA DE DESCONTO E FRETE!)
                    String strStmt = "INSERT INTO produto_pedido(id_produto, id_pedido, quantidade, preco_venda, preco_custo, info_adicional) VALUES (?, ?, ?, ?, ?, ?)";
                    japedidos.produto.ProdutoPedido[] antigosProdutos = pAntigo.getProdutos();
                    japedidos.produto.ProdutoPedido[] novosProdutos = pNovo.getProdutos();
                    if (novosProdutos != null && antigosProdutos != null) {
                        if (!japedidos.produto.ProdutoPedido.equals(novosProdutos, antigosProdutos)) { // Se produtos forem minimamente diferentes
                            atualizarProdutos(pAntigo, novosProdutos, conn); // Atualiza produtos e recomputa preço final e custo e atualiza na tabela do pedido
                        }
                    } else {
                        throw new SQLException("Um ou ambos pedidos não possuem produtos");
                    }
                    
                    // Controle de cadastro de informação adicional do cliente
                    japedidos.clientes.Cliente.InfoAdicional infoAdicionalClienteNova = pNovo.getCliente().getInfoAdicional();
                    atualizarInfoAdicionalCliente(pAntigo, infoAdicionalClienteNova, conn); // TODO: verificar se são iguais e controlar se altera ou não
                    
                    conn.commit();
                } catch (Exception e) {
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
                    
                    if (updatePedido != null) {
                        try {
                           updatePedido.close();
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
        
        public static japedidos.pedidos.Pedido selectById(int id) {
            japedidos.pedidos.Pedido p = null;
            if (id != japedidos.pedidos.Pedido.NULL_ID) {
                Connection conn = null;
                PreparedStatement stmt = null;
                ResultSet rs = null;
                try {
                    conn = BD.getConnection();
                    stmt = conn.prepareCall("SELECT * FROM vw_pedido WHERE id = ?");
                    stmt.setInt(1, id);
                    rs = stmt.executeQuery();
                    
                    japedidos.pedidos.Pedido[] pArray = parseView_pedido(rs);
                    p = pArray[0];
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);
                }
                
                // Fechamento da conexão
                try {
                    if (conn != null) {
                        conn.close();
                        if (stmt != null) {
                            stmt.close();
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de fechamento de conexão", JOptionPane.ERROR_MESSAGE);
                }
                
            }
            return p;
        }
        
        public static japedidos.pedidos.Pedido[] selectByEstado(japedidos.pedidos.Estado e) {
            japedidos.pedidos.Pedido[] p = null;
            if (e != null && e.ID != -1) {
                Connection conn = null;
                CallableStatement cstmt = null;
                ResultSet rs = null;
                try {
                    conn = BD.getConnection();
                    cstmt = conn.prepareCall("call select_pedidos_by_estado(?)");
                    cstmt.setInt(1, e.ID);
                    rs = cstmt.executeQuery();
                    
                    p = parseView_pedido(rs);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);
                }
                
                // Fechamento da conexão
                try {
                    if (conn != null) {
                        conn.close();
                        if (cstmt != null) {
                            cstmt.close();
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de fechamento de conexão", JOptionPane.ERROR_MESSAGE);
                }
                
            }
            return p;
        }
    
        public static int atualizarEstado(japedidos.pedidos.Pedido p, japedidos.pedidos.EstadoPedido e) {
            int r = 0;
            Connection conn = null;
            PreparedStatement insertEstado = null;
            PreparedStatement insertInfoCancelamento = null;
            PreparedStatement insertDataVencimento = null;
            PreparedStatement insertDataPago = null;
            
            if (p != null && e != null && !p.isNew()) {
                try {
                    conn = BD.getConnection();
                    conn.setAutoCommit(false);
                    
                    int id_pedido = p.getId();
                    int id_estado = e.ESTADO.ID;
                    japedidos.usuario.Usuario autor = e.AUTOR;
                    
                    // Inserindo cancelamento
                    if (e.ESTADO.equals(japedidos.pedidos.Estado.CANCELADO)) {
                        insertInfoCancelamento = conn.prepareStatement("INSERT INTO est_andamento_pedido (id_pedido, id_est_andamento, id_usuario_autor, dthr_criacao) VALUE (?, ?, ?, CURRENT_TIME(3))");
                        insertInfoCancelamento.setInt(1, id_pedido);
                        insertInfoCancelamento.setInt(2, japedidos.pedidos.Estado.CANCELADO.ID);
                        insertInfoCancelamento.setInt(3, autor.getId());
                        r += insertInfoCancelamento.executeUpdate();
                        
                        // Adiciona info de cancelamento, houver
                        insertInfoCancelamento = conn.prepareStatement("INSERT INTO info_cancelamento (id_pedido, justificativa) VALUE (?, ?)");
                        insertInfoCancelamento.setInt(1, id_pedido);
                        insertInfoCancelamento.setString(2, e.getInfoCancelamento());
                        r += insertInfoCancelamento.executeUpdate();
                        
                    } else {
                        // Inserindo data de vencimento de pagamento
                        if (e.getDataVencimentoPagamento() != null) {
                            insertDataVencimento = conn.prepareStatement("INSERT INTO est_andamento_pedido (id_pedido, id_est_andamento, id_usuario_autor, dthr_criacao) VALUE (?, ?, ?, CURRENT_TIME(3))");
                            insertDataVencimento.setInt(1, id_pedido);
                            insertDataVencimento.setInt(2, japedidos.pedidos.Estado.AGUARDANDO_PAGAMENTO.ID);
                            insertDataVencimento.setInt(3, autor.getId());
                            r += insertDataVencimento.executeUpdate();

                            // Atualizando pedido
                            insertDataVencimento = conn.prepareStatement("UPDATE pedido SET dt_venc_pagamento = ? WHERE id = ?");
                            insertDataVencimento.setDate(1, Date.valueOf(e.getDataVencimentoPagamento()));
                            insertDataVencimento.setInt(2, id_pedido);
                            r += insertDataVencimento.executeUpdate();
                        }

                        // Inserindo data de pagamento
                        if (e.getDataPago() != null) {
                            insertDataPago = conn.prepareStatement("INSERT INTO est_andamento_pedido (id_pedido, id_est_andamento, id_usuario_autor, dthr_criacao) VALUE (?, ?, ?, CURRENT_TIME(3))");
                            insertDataPago.setInt(1, id_pedido);
                            insertDataPago.setInt(2, japedidos.pedidos.Estado.PAGO.ID);
                            insertDataPago.setInt(3, autor.getId());
                            r += insertDataPago.executeUpdate();

                            // Atualizando pedido
                            insertDataPago = conn.prepareStatement("UPDATE pedido SET dt_pago = ? WHERE id = ?");
                            insertDataPago.setDate(1, Date.valueOf(e.getDataPago()));
                            insertDataPago.setInt(2, id_pedido);
                            r += insertDataPago.executeUpdate();
                        }

                        // Inserindo novo estado genérico
                        if (!e.ESTADO.equals(japedidos.pedidos.Estado.AGUARDANDO_PAGAMENTO) && !e.ESTADO.equals(japedidos.pedidos.Estado.PAGO)) {
                            insertEstado = conn.prepareStatement("INSERT INTO est_andamento_pedido (id_pedido, id_est_andamento, id_usuario_autor, dthr_criacao) VALUE (?, ?, ?, CURRENT_TIME(3))");
                            insertEstado.setInt(1, id_pedido);
                            insertEstado.setInt(2, id_estado);
                            insertEstado.setInt(3, autor.getId());
                            r += insertEstado.executeUpdate();
                        }
                    }
                    conn.commit();
                } catch (SQLException ex) {
                    if (conn != null) {
                        try {
                            conn.rollback();
                        } catch (SQLException ex2) {
                            
                        }
                    }
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de atualização de estado", JOptionPane.ERROR_MESSAGE);
                    r = -1;
                }
                
                if (conn != null) {
                    try {
                        conn.setAutoCommit(true);
                    } catch (SQLException ex) {
                       System.out.println("Não foi possível definir autoCommit como true");
                    }
                    if (insertEstado != null) {
                        try {
                           insertEstado.close();
                        } catch (SQLException ex) {
                           System.out.println("Não foi possível fechar conexão com o banco.");
                        }
                    }
                    if (insertInfoCancelamento != null) {
                        try {
                           insertInfoCancelamento.close();
                        } catch (SQLException ex) {
                           System.out.println("Não foi possível fechar conexão com o banco.");
                        }                        
                    }
                    if (insertDataVencimento != null) {
                        try {
                           insertDataVencimento.close();
                        } catch (SQLException ex) {
                           System.out.println("Não foi possível fechar conexão com o banco.");
                        }                        
                    }
                    if (insertDataPago != null) {
                        try {
                           insertDataPago.close();
                        } catch (SQLException ex) {
                           System.out.println("Não foi possível fechar conexão com o banco.");
                        }                        
                    }
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        
                    }
                }
            }
            return r;
        }
        
        public static int atualizarInfoAdicionalCliente(final japedidos.pedidos.Pedido pedidoAntigo, final japedidos.clientes.Cliente.InfoAdicional novaInfoAdicional, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (pedidoAntigo == null) {
                throw new NullPointerException("Pedido é nulo");
            } else if (pedidoAntigo.isNew()) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
//            if (novaInfoAdicional == null) {
//                throw new NullPointerException("Informações adicionais do cliente do pedido são nulas");
//            }
            
            
            PreparedStatement deleteInfoAdicionalPF, updateInfoAdicionalPF, insertInfoAdicionalPF, deleteInfoAdicionalPJ, updateInfoAdicionalPJ, insertInfoAdicionalPJ;
            deleteInfoAdicionalPF = updateInfoAdicionalPF = insertInfoAdicionalPF = deleteInfoAdicionalPJ = updateInfoAdicionalPJ = insertInfoAdicionalPJ = null;
            int rSoma = 0;
            Throwable doThrow = null;
            
            // Obtendo informações necessárias para controle da alteração
            int id_pedido = pedidoAntigo.getId();
            japedidos.clientes.Cliente.InfoAdicional infoAdicionalPedido = pedidoAntigo.getCliente().getInfoAdicional();
            
            try {
                if (infoAdicionalPedido == null && novaInfoAdicional != null) { // Criação de info adicional
                    InfoAdicional.insert(id_pedido, novaInfoAdicional, conn);
                } else if (infoAdicionalPedido != null) { // Modificação de info adicional
                    japedidos.clientes.Cliente.InfoAdicional.Tipo tipoPedido = infoAdicionalPedido.getTipo();
                    
                    if (novaInfoAdicional != null) { // Atualizar registros existentes
                        japedidos.clientes.Cliente.InfoAdicional.Tipo novoTipo = novaInfoAdicional.getTipo();
                        
                        // TODO: fazer checagem se são iguais, para não atualizar caso sejam
                        if (tipoPedido == novoTipo) {
                            InfoAdicional.update(id_pedido, novaInfoAdicional, conn);
                        } else { // Apagar antigo e criar novo
                            InfoAdicional.delete(id_pedido, infoAdicionalPedido, conn);
                            InfoAdicional.insert(id_pedido, novaInfoAdicional, conn);
                        }
                    } else { // Deletar registros existentes
                        InfoAdicional.delete(id_pedido, infoAdicionalPedido, conn);
                    }
                    
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (deleteInfoAdicionalPF != null) {
                    deleteInfoAdicionalPF.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            try {
                if (updateInfoAdicionalPF != null) {
                    updateInfoAdicionalPF.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            try {
                if (insertInfoAdicionalPF != null) {
                    deleteInfoAdicionalPF.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            try {
                if (deleteInfoAdicionalPJ != null) {
                    deleteInfoAdicionalPJ.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            try {
                if (updateInfoAdicionalPJ != null) {
                    updateInfoAdicionalPJ.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            try {
                if (insertInfoAdicionalPJ != null) {
                    insertInfoAdicionalPJ.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao atualizar informação adicional do cliente: " + doThrow.getMessage());
            }
            
            return rSoma;
        }
        
        public static int atualizarInfoEntrega(final japedidos.pedidos.Pedido pedidoAntigo, final japedidos.pedidos.InfoEntrega novaInfoEntrega, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (pedidoAntigo == null) {
                throw new NullPointerException("Pedido é nulo");
            } else if (pedidoAntigo.isNew()) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
            if (novaInfoEntrega == null) {
                throw new NullPointerException("Informações de entrega do pedido são nulas");
            }
            
            
            PreparedStatement deleteDestino, updateDestino, insertDestino, deleteDestinatario, updateDestinatario, insertDestinatario, updateTipoEntrega, updatePrecoFrete;
            deleteDestino = updateDestino = insertDestino = deleteDestinatario = updateDestinatario = insertDestinatario = updateTipoEntrega = updatePrecoFrete = null;
            int rSoma = 0;
            Throwable doThrow = null;
            
            // Obtendo informações necessárias para controle da alteração
            int id_pedido = pedidoAntigo.getId();
            japedidos.pedidos.InfoEntrega infoEntregaPedido = pedidoAntigo.getInfoEntrega();
            
            TipoEntrega tipoEntregaAtual, tipoEntregaNovo;
            tipoEntregaAtual = infoEntregaPedido.getTipoEntrega();
            tipoEntregaNovo = novaInfoEntrega.getTipoEntrega();
            try {
                if (infoEntregaPedido.getPrecoFrete() != novaInfoEntrega.getPrecoFrete()) {
                    // Atualizar preço do frete do pedido
                    updateTipoEntrega = conn.prepareStatement(String.format("UPDATE %s SET preco_frete = ? WHERE id = ?", TABLE));
                    updateTipoEntrega.setDouble(1, novaInfoEntrega.getPrecoFrete());
                    updateTipoEntrega.setInt(2, id_pedido);
                    updateTipoEntrega.executeUpdate();
                }
                
                // Se tipos de entrega diferem
                if (tipoEntregaAtual != tipoEntregaNovo) {
                    if (tipoEntregaAtual == TipoEntrega.ENVIO && tipoEntregaNovo == TipoEntrega.RETIRADA) {
                        // Apagar destino
                        BD.Destino.delete(id_pedido, conn);
                    } else {
                        // Criar destino
                        BD.Destino.insert(id_pedido, novaInfoEntrega.getDestino(), conn);
                    }
                    
                    // Atualizar tipo de entrega do pedido
                    updateTipoEntrega = conn.prepareStatement(String.format("UPDATE %s SET tipo_entrega = ? WHERE id = ?", TABLE));
                    updateTipoEntrega.setString(1, tipoEntregaNovo.toString());
                    updateTipoEntrega.setInt(2, id_pedido);
                    updateTipoEntrega.executeUpdate();
                } else if (tipoEntregaNovo == TipoEntrega.ENVIO){ // Se ambos forem envio
                    japedidos.pedidos.Destino destinoAtual = infoEntregaPedido.getDestino();
                    japedidos.pedidos.Destino  destinoNovo = novaInfoEntrega.getDestino();
                    
                    if (!destinoNovo.equals(destinoAtual)) { // Se forem minimamente diferentes
                        // Atualizar destino novo
                        BD.Destino.update(id_pedido, destinoNovo, conn);
                    } 
                    
                }
                
                // Destinatario
                String destinatarioPedido, destinatarioNovo;
                destinatarioPedido = infoEntregaPedido.getDestinatario();
                destinatarioNovo = novaInfoEntrega.getDestinatario();
                
                if (destinatarioPedido != null && destinatarioNovo != null) {
                    if (!destinatarioNovo.equals(destinatarioPedido)) {
                        // Atualizar destinatario
                        BD.Destinatario.update(id_pedido, destinatarioNovo, conn);
                    }
                } else if (destinatarioPedido == null) { // Existe um novo destinatário
                    // Inserir destinatário
                    BD.Destinatario.insert(id_pedido, destinatarioNovo, conn);
                    
                } else { // Não há mais destinatário
                    // Apagar destinatário
                    BD.Destinatario.delete(id_pedido, conn);
                }
                
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (deleteDestino != null) {
                    deleteDestino.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (updateDestino != null) {
                    updateDestino.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (insertDestino != null) {
                    deleteDestino.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (deleteDestinatario != null) {
                    deleteDestinatario.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (updateDestinatario != null) {
                    updateDestinatario.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (insertDestinatario != null) {
                    insertDestinatario.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (updateTipoEntrega != null) {
                    updateTipoEntrega.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (updatePrecoFrete != null) {
                    updatePrecoFrete.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao atualizar informação de entrega: " + doThrow.getMessage());
            }
            
            return rSoma;
        }
        
        public static int atualizarProdutos(final japedidos.pedidos.Pedido pedidoAntigo, final japedidos.produto.ProdutoPedido[] novosProdutos, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (pedidoAntigo == null) {
                throw new NullPointerException("Pedido é nulo");
            } else if (pedidoAntigo.isNew()) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
            if (novosProdutos == null) {
                throw new NullPointerException("Novos produtos do pedido são nulos");
            }
            
            
            PreparedStatement updateProdutos, deleteProdutos;
            CallableStatement updatePrecosPedido = null;
            updateProdutos = deleteProdutos = null;
            int rSoma = 0;
            Throwable doThrow = null;
            try {
                deleteProdutos = conn.prepareStatement(String.format("DELETE FROM %s WHERE id_pedido = ?", ProdutoPedido.TABLE));
                deleteProdutos.setInt(1, pedidoAntigo.getId());
                deleteProdutos.executeUpdate();

                updateProdutos = conn.prepareStatement(
                        String.format("INSERT INTO %s(id_produto, id_pedido, quantidade, preco_venda, preco_custo, info_adicional) VALUES "
                                    + "(?, ?, ?, preco_venda_produto(?), preco_custo_produto(?), ?)", ProdutoPedido.TABLE));
                for(var prodPed : novosProdutos) {
                    if (prodPed != null) {
                        int i = 1;
                        int id_produto = prodPed.getProduto().getId();
                        updateProdutos.setInt(i++, prodPed.getProduto().getId());
                        updateProdutos.setInt(i++, pedidoAntigo.getId());
                        updateProdutos.setInt(i++, prodPed.getQuantidade());
                        updateProdutos.setInt(i++, id_produto);
                        updateProdutos.setInt(i++, id_produto);
                        updateProdutos.setString(i++, prodPed.getInfoAdicional());
                        updateProdutos.addBatch();
                    }
                }
                int[] r = updateProdutos.executeBatch();
                
                for (int n : r) {
                    rSoma += n;
                }
                
                updatePrecosPedido = conn.prepareCall("call atualizar_precos_pedido(?)");
                updatePrecosPedido.setInt(1, pedidoAntigo.getId());
                updatePrecosPedido.executeUpdate();
                
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (deleteProdutos != null) {
                    deleteProdutos.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (updateProdutos != null) {
                    updateProdutos.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (updatePrecosPedido != null) {
                    updatePrecosPedido.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao atualizar produtos: " + doThrow.getMessage());
            }
            
            return rSoma;
        }
        
        /** Recebe um resultSet contendo informações de um pedido obtido por meio
         * da view vw_pedido. Recebe as informações básicas do pedido, do cliente
         * e dos produtos contidos e gera um array de Pedido. Não traz todos os estados dos pedidos, mas apenas o atual.
         * 
         */
        public static japedidos.pedidos.Pedido[] parseView_pedido(ResultSet rs) {
            return parseView_pedido(rs, false, false);
        }
        
        
        public static japedidos.pedidos.Pedido[] parseView_pedido(ResultSet rs, boolean getProdutos, boolean getEstados) {
            ArrayList<japedidos.pedidos.Pedido> pList = new ArrayList<>();
            japedidos.pedidos.Pedido[] pedidos = null;
            try {
                while(rs.next()) {
                    
                    int id_pedido = rs.getInt("id");
                    
                    // Cliente
                    int id_cliente = rs.getInt("id_cliente");
                    String nome_cliente = rs.getString("nome_cliente");
                    String telefone_cliente = rs.getString("telefone_cliente");

                    japedidos.clientes.Cliente cliente = new japedidos.clientes.Cliente(id_cliente, nome_cliente, telefone_cliente);

                    // Info adicional do cliente
                    japedidos.clientes.Cliente.InfoAdicional infoAdicional = null;
                    String cpf, cnpj;
                    if ((cpf = rs.getString("cpf_info_pf")) != null) { // Se houver info de pessoa física
                        String nome_cliente_info_pf = rs.getString("nome_cliente_info_pf");
                        japedidos.clientes.Cliente.InfoPF infoPF = new japedidos.clientes.Cliente.InfoPF(nome_cliente_info_pf, cpf);
                        infoAdicional = infoPF;
                    } else if ((cnpj = rs.getString("cnpj_info_pj")) != null) { // se houver info de pessoa juridica
                        String nome_fantasia_info_pj = rs.getString("nome_fantasia_info_pj");
                        String nome_empresarial_info_pj = rs.getString("nome_empresarial_info_pj");
                        japedidos.clientes.Cliente.InfoPJ infoPJ = new japedidos.clientes.Cliente.InfoPJ(nome_fantasia_info_pj, nome_empresarial_info_pj, cnpj) ;
                        infoAdicional = infoPJ;
                    }

                    cliente.setInfoAdicional(infoAdicional);
                    
                    // Entrega
                    japedidos.pedidos.TipoEntrega tipoEntrega = japedidos.pedidos.TipoEntrega.valueOf(rs.getString("tipo_entrega"));
                    
                    LocalDateTime dthrEntregar = rs.getTimestamp("dthr_entregar").toLocalDateTime();
                    double precoFrete = rs.getDouble("preco_frete");
                    japedidos.pedidos.InfoEntrega infoEntrega = new japedidos.pedidos.InfoEntrega(tipoEntrega, dthrEntregar, precoFrete);
                    
                    japedidos.pedidos.Destino destino = null;
                    if (tipoEntrega == japedidos.pedidos.TipoEntrega.ENVIO) {
                        destino = new japedidos.pedidos.Destino(
                                rs.getString("logradouro_destino"), 
                                rs.getString("numero_destino"), 
                                rs.getString("bairro_destino"), 
                                rs.getString("cidade_destino"), 
                                rs.getString("estado_destino"), 
                                rs.getString("pais_destino"));
                    }
                    infoEntrega.setDestino(destino);
                    
                    String destinatario = rs.getString("info_destinatario");
                    infoEntrega.setDestinatario(destinatario);
                    
                    
                    // Desconto
                    int taxaDesconto = rs.getInt("tx_desconto");
                    
                    japedidos.pedidos.Pedido p = null;
                    try {
                        p = new japedidos.pedidos.Pedido(id_pedido, cliente, infoEntrega, taxaDesconto); // Throw illegalArgumentsException se argumentos forem inválidos
                        if (getProdutos) {
                            p.setProdutos(BD.ProdutoPedido.selectAllBy_id_pedido(id_pedido));
                        }
                        
                        p.setPrecoFinal(rs.getDouble("preco_final"));
                        p.setCustoTotal(rs.getDouble("preco_custo_total"));
                    } catch (IllegalArgumentsException exs) {
                        System.out.println(String.format("ERRO AO RECEBER PEDIDO ID %d: %s", id_pedido, exs.getMessage()));
                        continue;
                    }
                    
                    // Informações de pagamento
                    LocalDate dtPago = null;
                    Date dt = rs.getDate("dt_pago");
                    if (dt != null) {
                        dtPago = dt.toLocalDate();
                        p.setDataPago(dtPago);
                    }
                    LocalDate dtVencimentoPagamento = null;
                    Date dtV = rs.getDate("dt_venc_pagamento");
                    if (dtV != null) {
                        dtVencimentoPagamento = dtV.toLocalDate();
                        p.setDataVencimentoPagamento(dtVencimentoPagamento);
                    }
                    
                    // Registros de criação e alteração
                    japedidos.usuario.Registro criacao = null;
                    int id_usuario_autor = rs.getInt("id_usuario_autor");
                    Timestamp tsCriacao = rs.getTimestamp("dthr_criacao");
                    if (id_usuario_autor != 0 && tsCriacao != null) { 
                        japedidos.usuario.Usuario usr = new japedidos.usuario.Usuario(id_usuario_autor, rs.getString("nome_usuario_autor"));
                        criacao = new japedidos.usuario.Registro(usr, tsCriacao.toLocalDateTime());
                        p.setRegistroCriacao(criacao);
                    }
                    
                    japedidos.usuario.Registro alteracao;
                    int id_usuario_alt = rs.getInt("id_usuario_alt");
                    Timestamp tsAlt = rs.getTimestamp("dthr_alt");
                    if (id_usuario_alt != 0 && tsAlt != null) { 
                        japedidos.usuario.Usuario usr = new japedidos.usuario.Usuario(id_usuario_alt, rs.getString("nome_usuario_alt"));
                        alteracao = new japedidos.usuario.Registro(usr, tsAlt.toLocalDateTime());
                        p.setRegistroAlteracao(alteracao);
                    }
                    
                    // Estados
                    if (getEstados) {
                        japedidos.pedidos.EstadoPedido[] estados = BD.EstadoPedido.selectAllByPedido(p);
                        p.setEstadosPedido(estados);
                    } else { // Pega só o atual
                        japedidos.pedidos.Estado estado = Estado.getEstado(rs.getInt("id_ultimo_est"));
                        japedidos.usuario.Usuario usrEstado = new japedidos.usuario.Usuario(rs.getInt("id_usuario_autor_ultimo_est"), rs.getString("nome_usuario_autor_ultimo_est"));
                        LocalDateTime dthr_criacao_ultimo_est = rs.getTimestamp("dthr_criacao_ultimo_est").toLocalDateTime();

                        japedidos.pedidos.EstadoPedido estadoAtual = new japedidos.pedidos.EstadoPedido(estado, usrEstado, dthr_criacao_ultimo_est);
                        p.setEstadoAtual(estadoAtual);
                    }
                    
                    
                    // Informação de cancelamento
                    String infoCancelamento = rs.getString("info_cancelamento");
                    if (infoCancelamento != null) {
                        p.setInfoCancelamento(infoCancelamento);
                    }
                    
                    pList.add(p);
                }
                
                if (!pList.isEmpty()) {
                    pedidos = new japedidos.pedidos.Pedido[pList.size()];
                    pList.toArray(pedidos);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de parse", JOptionPane.ERROR_MESSAGE);
            }
            
            return pedidos;
        }
    
    }
    
    static public class Destino {
        public static final String TABLE = "destino";
        
        public static int insert(int id_pedido, japedidos.pedidos.Destino destino, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (id_pedido < 1) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
            if (destino == null) {
                throw new NullPointerException("Destino do pedido é nulo");
            }
            
            
            PreparedStatement insertDestino = null;
            int r = 0;
            Throwable doThrow = null;
            try {
                insertDestino = conn.prepareStatement(String.format("INSERT INTO %s(id_pedido, logradouro, numero, bairro, cidade, estado, pais) VALUE (?, ?, ?, ?, ?, ?, ?)", TABLE));
                int j = 1;
                insertDestino.setInt(j++, id_pedido);
                insertDestino.setString(j++, destino.getLogradouro());
                insertDestino.setString(j++, destino.getNumero());
                insertDestino.setString(j++, destino.getBairro());
                insertDestino.setString(j++, destino.getCidade());
                insertDestino.setString(j++, destino.getEstado());
                insertDestino.setString(j++, destino.getPais());
                r = insertDestino.executeUpdate();
                
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (insertDestino != null) {
                    insertDestino.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao atualizar informação de entrega: " + doThrow.getMessage());
            }
            
            return r;
        }
    
        public static int update(int id_pedido, japedidos.pedidos.Destino destino, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (id_pedido < 1) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
            if (destino == null) {
                throw new NullPointerException("Destino do pedido é nulo");
            }
            
            
            PreparedStatement updateDestino = null;
            int r = 0;
            Throwable doThrow = null;
            try {
                updateDestino = conn.prepareStatement(String.format("UPDATE %s SET logradouro = ?, numero = ?, bairro = ?, cidade = ?, estado = ?, pais = ? WHERE id_pedido = ?", TABLE));
                int j = 1;
                updateDestino.setString(j++, destino.getLogradouro());
                updateDestino.setString(j++, destino.getNumero());
                updateDestino.setString(j++, destino.getBairro());
                updateDestino.setString(j++, destino.getCidade());
                updateDestino.setString(j++, destino.getEstado());
                updateDestino.setString(j++, destino.getPais());
                updateDestino.setInt(j++, id_pedido);
                r = updateDestino.executeUpdate();
                
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (updateDestino != null) {
                    updateDestino.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao atualizar destino de entrega: " + doThrow.getMessage());
            }
            
            return r;
        }
        
        public static int delete(int id_pedido, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (id_pedido < 1) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
            
            PreparedStatement deleteDestino = null;
            int r = 0;
            Throwable doThrow = null;
            try {
                deleteDestino = conn.prepareStatement(String.format("DELETE FROM %s WHERE id_pedido = ?", TABLE));
                deleteDestino.setInt(1, id_pedido);
                r = deleteDestino.executeUpdate();
                
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (deleteDestino != null) {
                    deleteDestino.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao deletar destino de entrega: " + doThrow.getMessage());
            }
            
            return r;
        }
    }
    
    static public class Destinatario {
        public static final String TABLE = "destinatario";
        
        public static int insert(int id_pedido, String destinatario, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (id_pedido < 1) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
            if (destinatario == null) {
                throw new NullPointerException("Destinatário do pedido é nulo");
            }
            
            
            PreparedStatement insertDestinatario = null;
            int r = 0;
            Throwable doThrow = null;
            try {
                insertDestinatario = conn.prepareStatement(String.format("INSERT INTO %s(id_pedido, info) VALUE (?, ?)", TABLE));
                insertDestinatario.setInt(1, id_pedido);
                insertDestinatario.setString(2, destinatario);
                r = insertDestinatario.executeUpdate();
                
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (insertDestinatario != null) {
                    insertDestinatario.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao inserir destinatario de entrega: " + doThrow.getMessage());
            }
            
            return r;
        }
    
        public static int update(int id_pedido, String destinatario, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (id_pedido < 1) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
            if (destinatario == null) {
                throw new NullPointerException("Destino do pedido é nulo");
            }
            
            
            PreparedStatement updateDestinatario = null;
            int r = 0;
            Throwable doThrow = null;
            try {
                updateDestinatario = conn.prepareStatement(String.format("UPDATE %s SET info = ? WHERE id_pedido = ?", TABLE));
                updateDestinatario.setString(1, destinatario);
                updateDestinatario.setInt(2, id_pedido);
                r = updateDestinatario.executeUpdate();
                
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (updateDestinatario != null) {
                    updateDestinatario.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao atualizar destinatario de entrega: " + doThrow.getMessage());
            }
            
            return r;
        }
        
        public static int delete(int id_pedido, Connection conn) throws SQLException {
            if (conn == null) {
                throw new NullPointerException("Conexão é nula");
            }
            
            if (id_pedido < 1) {
                throw new IllegalArgumentException("Pedido não é cadastrado");
            }
            
            
            PreparedStatement deleteDestinatario = null;
            int r = 0;
            Throwable doThrow = null;
            try {
                deleteDestinatario = conn.prepareStatement(String.format("DELETE FROM %s WHERE id_pedido = ?", TABLE));
                deleteDestinatario.setInt(1, id_pedido);
                r = deleteDestinatario.executeUpdate();
                
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            try {
                if (deleteDestinatario != null) {
                    deleteDestinatario.close();
                }
            } catch (SQLException ex) {
                doThrow = ex;
            }
            
            if (doThrow != null) {
                throw new SQLException("Falha ao deletar destinatario de entrega: " + doThrow.getMessage());
            }
            
            return r;
        }
    }
    
    static public class EstadoPedido {
        public static final String TABLE = "est_andamento_pedido";
        
        public static japedidos.pedidos.EstadoPedido[] selectAllByPedido(japedidos.pedidos.Pedido p) {
            japedidos.pedidos.EstadoPedido[] e = null;
            if (p != null && !p.isNew()) {
                Connection conn = null;
                CallableStatement cstmt = null;
                ResultSet rs = null;
                try {
                    conn = BD.getConnection();
                    cstmt = conn.prepareCall("call select_estados_pedido(?)");
                    cstmt.setInt(1, p.getId());
                    rs = cstmt.executeQuery();
                    
                    e = parseViewEstadoPedido(rs);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);
                }
                
                // Fechamento da conexão
                try {
                    if (conn != null) {
                        conn.close();
                        if (cstmt != null) {
                            cstmt.close();
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de fechamento de conexão", JOptionPane.ERROR_MESSAGE);
                }
                
            }
            return e;
        }
        
        public static japedidos.pedidos.EstadoPedido[] parseViewEstadoPedido(ResultSet rs) {
            ArrayList<japedidos.pedidos.EstadoPedido> eList = new ArrayList<>();
            japedidos.pedidos.EstadoPedido[] estados = null;
            japedidos.usuario.Usuario usrAtual = japedidos.usuario.Usuario.getAtual();
            try {
                while(rs.next()) {
                    
                    int id_pedido = rs.getInt("id_pedido");
                    
                    // Estados
                    japedidos.pedidos.Estado estado = japedidos.pedidos.Estado.getEstado(rs.getInt("id_est_andamento"));
                    
                    japedidos.usuario.Usuario usrEstado;
                    int id_usr = rs.getInt("id_usuario_autor");
                    if (id_usr == usrAtual.getId()) {
                        usrEstado = usrAtual;
                    } else {
                        usrEstado = new japedidos.usuario.Usuario(id_usr, rs.getString("nome_usuario_autor"));
                    }
                    LocalDateTime dthr_criacao_ultimo_est = rs.getTimestamp("dthr_criacao").toLocalDateTime();
                    
                    japedidos.pedidos.EstadoPedido estadoRecebido = new japedidos.pedidos.EstadoPedido(estado, usrEstado, dthr_criacao_ultimo_est);

                    eList.add(estadoRecebido);
                }
                
                if (!eList.isEmpty()) {
                    estados = new japedidos.pedidos.EstadoPedido[eList.size()];
                    eList.toArray(estados);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de parse", JOptionPane.ERROR_MESSAGE);
            }
            
            return estados;
        }
    }
    
    static public class Usuario {
        public static final String TABLE = "usuario";
        private static int get;
        
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
    
    static public class ProdutoPedido {
        public static final String TABLE = "produto_pedido";
        public static japedidos.produto.ProdutoPedido[] selectAllBy_id_pedido(int id_pedido) {
            japedidos.produto.ProdutoPedido[] p = null;
            if (id_pedido != -1) {
                Connection conn = null;
                CallableStatement cstmt = null;
                ResultSet rs = null;
                try {
                    conn = BD.getConnection();
                    cstmt = conn.prepareCall("call select_produtos_pedido(?)");
                    cstmt.setInt(1, id_pedido);
                    rs = cstmt.executeQuery();
                    
                    p = parseView_produtos_pedido(rs);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de busca", JOptionPane.ERROR_MESSAGE);
                }
                
                // Fechamento da conexão
                try {
                    if (conn != null) {
                        conn.close();
                        if (cstmt != null) {
                            cstmt.close();
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de fechamento de conexão", JOptionPane.ERROR_MESSAGE);
                }
                
            }
            return p;
        }
    
        public static japedidos.produto.ProdutoPedido[] parseView_produtos_pedido(ResultSet rs) {
            ArrayList<japedidos.produto.ProdutoPedido> pList = new ArrayList<>();
            japedidos.produto.ProdutoPedido[] produtosPedido = null;
            try {
                while (rs.next()) {
                    // Criação da Categoria
                    int id_categoria = rs.getInt("id_categoria");
                    String nome_categoria = rs.getString("nome_categoria");
                    String descricao_categoria = rs.getString("descricao_categoria");
                    japedidos.produto.Categoria categoria = new japedidos.produto.Categoria(id_categoria, nome_categoria, descricao_categoria);
                    
                    
                    // Criacao da Unidade
                    int id_unidade = rs.getInt("id_unidade");
                    String nome_unidade = rs.getString("nome_unidade");
                    String abreviacao_unidade = rs.getString("abreviacao_unidade");
                    japedidos.produto.Unidade unidade = new japedidos.produto.Unidade(id_unidade, nome_unidade, abreviacao_unidade);
                    
                    // Criação do Produto
                    int id_produto = rs.getInt("id_produto");
                    String nome_produto = rs.getString("nome_produto");
                    double preco_venda = rs.getDouble("preco_venda");
                    double preco_custo = rs.getDouble("preco_custo");
                    boolean estado = rs.getBoolean("estado");
                    
                    japedidos.produto.Produto produto = new japedidos.produto.Produto(id_produto, nome_produto, categoria, unidade, preco_custo, preco_venda );
                    produto.setAtivo(estado);
                    
                    // Criação do registro de alteração, se houver
                    japedidos.usuario.Registro alteracao = null;
                    int id_usuario_alt = rs.getInt("id_usuario_alt");
                    LocalDateTime dthr_alt = null;
                    Timestamp tsAlt = rs.getTimestamp("dthr_alt");
                    if (tsAlt != null) {
                        dthr_alt = tsAlt.toLocalDateTime();
                    }
                    
                    if (id_usuario_alt != 0 && dthr_alt != null) {
                        
                        String nome_usuario_alt = rs.getString("nome_usuario_alt");
                        String strTipo_usuario = rs.getString("tipo_usuario");
                        japedidos.usuario.Usuario.Tipo tipo_usuario = null;
                        if (strTipo_usuario.equals(japedidos.usuario.Usuario.Tipo.ADMINISTRADOR.toString())) {
                            tipo_usuario = japedidos.usuario.Usuario.Tipo.ADMINISTRADOR;
                        } else if (strTipo_usuario.equals(japedidos.usuario.Usuario.Tipo.ATENDENTE.toString())) {
                            tipo_usuario = japedidos.usuario.Usuario.Tipo.ATENDENTE;
                        }
                        
                        japedidos.usuario.Usuario usuario = new japedidos.usuario.Usuario(id_usuario_alt, nome_usuario_alt, tipo_usuario);
                        alteracao = new japedidos.usuario.Registro(usuario, dthr_alt);
                        produto.setAlteracao(alteracao);
                    }
                    
                    // Criacao do ProdutoPedido
                    int quantidade_produto = rs.getInt("quantidade_produto");
                    japedidos.produto.ProdutoPedido prodPed = new japedidos.produto.ProdutoPedido(produto, quantidade_produto);
                    String info_adicional = rs.getString("info_adicional_produto_pedido");
                    if (info_adicional != null) {
                        prodPed.setInfoAdicional(info_adicional);
                    }
                    
                    pList.add(prodPed);
                }
                
                if (!pList.isEmpty()) {
                    produtosPedido = new japedidos.produto.ProdutoPedido[pList.size()];
                    pList.toArray(produtosPedido);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de parse", JOptionPane.ERROR_MESSAGE);
            }
            
            return produtosPedido;
        }
    }
    
    static public class Produto {
        public static final String TABLE = "produto";
        public static final String VIEW = "vw_produto";
        
        
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
                    String.format("SELECT * FROM %s ORDER BY id DESC LIMIT 1", VIEW));
                ResultSet rs = select.executeQuery();
                
                japedidos.produto.Produto[] produto = parseView(rs);
                
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
                PreparedStatement select = conn.prepareStatement(String.format("SELECT * FROM %s ORDER BY nome_produto ASC", VIEW));
                ResultSet rs = select.executeQuery();
                
                japedidos.produto.Produto[] produtos = parseView(rs);
                
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
                        String.format("SELECT * FROM %s WHERE id = ?", VIEW));
                    select.setInt(1, id);
                    
                    ResultSet rs = select.executeQuery();

                    japedidos.produto.Produto[] produto = parseView(rs);

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
    
        public static japedidos.produto.Produto[] parseView(ResultSet rs) {
            ArrayList<japedidos.produto.Produto> pList = new ArrayList<japedidos.produto.Produto>();
            japedidos.usuario.Usuario usrAtual = japedidos.usuario.Usuario.getAtual();
            int id_usuario_atual = usrAtual.getId();
            try {
                while (rs.next()) {
                    int id_produto, id_categoria, id_unidade, id_usuario_alt;
                    String nome_produto;
                    double preco_venda, preco_custo;
                    boolean estado;
                    LocalDateTime dthr_alt = null;
                    
                    id_produto = rs.getInt("id");
                    
                    nome_produto = rs.getString("nome_produto");
                    preco_venda = rs.getDouble("preco_venda");
                    preco_custo = rs.getDouble("preco_custo");
                    id_usuario_alt = rs.getInt("id_usuario_alt");
                    
                    Timestamp ts = rs.getTimestamp("dthr_alt");
                    if (ts != null) {
                        dthr_alt = ts.toLocalDateTime();
                    }
                    
                    estado = rs.getBoolean("estado");
                    
                    // Busca de unidade e categoria
                    id_categoria = rs.getInt("id_categoria");
                    String nome_categoria = rs.getString("nome_categoria");
                    String descricao_categoria = rs.getString("descricao_categoria");
                    japedidos.produto.Categoria categoria = new japedidos.produto.Categoria(id_categoria, nome_categoria, descricao_categoria);
                    
                    
                    id_unidade = rs.getInt("id_unidade");
                    String nome_unidade = rs.getString("nome_unidade");
                    String abreviacao_unidade = rs.getString("abreviacao_unidade");
                    japedidos.produto.Unidade unidade = new japedidos.produto.Unidade(id_unidade, nome_unidade, abreviacao_unidade);
                    
                    japedidos.produto.Produto p = new japedidos.produto.Produto(id_produto, nome_produto, categoria, unidade, preco_custo, preco_venda );
                    p.setAtivo(estado);
                    
                    Registro alteracao = null;
                    if (id_usuario_alt != 0 && dthr_alt != null) {
                        japedidos.usuario.Usuario usrAlt;
                        if (id_usuario_alt == id_usuario_atual) {
                            usrAlt = usrAtual;
                        } else {
                            String nome_usuario_alt = rs.getString("nome_usuario_alt");
                            usrAlt = new japedidos.usuario.Usuario(id_usuario_alt, nome_usuario_alt);
                        }
                        alteracao = new Registro(usrAlt, dthr_alt); // TODO: ADICIONAR BUSCA DE USUARIO
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
