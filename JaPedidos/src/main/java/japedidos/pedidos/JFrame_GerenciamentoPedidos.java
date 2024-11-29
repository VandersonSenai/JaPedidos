package japedidos.pedidos;
import japedidos.bd.BD;
import japedidos.clientes.Cliente;
import japedidos.clientes.InfoAdicionalReceiver;
import japedidos.clientes.JFrame_InfoAdicionalCliente;
import japedidos.exception.*;
import japedidos.pedidos.Estado;
import japedidos.pedidos.Pedido;
import japedidos.produto.Produto;
import japedidos.produto.ProdutoPedido;
import japedidos.usuario.Registro;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import com.github.lgooddatepicker.components.*;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import japedidos.AccessController;
import japedidos.produto.ProdutoPedidoTableModel;
import japedidos.produto.db_config;
import japedidos.produto.load_DB2_components;
import japedidos.usuario.Usuario;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;


/**
 *
 * @author t.baiense
 */
public class JFrame_GerenciamentoPedidos extends javax.swing.JFrame implements InfoAdicionalReceiver {
    Cliente.InfoAdicional infoAdicionalCliente;
    Cliente clienteEncontrado = null;
    JPanel_AlterarPedido pnl_alterarPedido;
    final Runnable onFinalizarAlteracao;
    private boolean telefoneDigitado = false;
    final TimeVetoPolicy vetoPolicy = new TimeVetoPolicy() {
        public boolean isTimeAllowed(LocalTime time) {
            return PickerUtilities.isLocalTimeInRange(time, LocalTime.now(), null, true);
        }
    };
    
    /**
     * Creates new form CadastroPedido
     */
    public JFrame_GerenciamentoPedidos() {
        if (BD.isAccessible()) {
            AccessController.verificarLogin();
        }
        
        initComponents();
        fillEstadosComboBoxPedido();
        fillTipoEntregaComboBoxPedido();
        hideErrorLabels();
        jspn_quantidade.setValue(1);
        
        jspn_valorEntrega.addChangeListener((e) -> {
            atualizarValoresPedido();
        });
        
        jspn_desconto.addChangeListener((e) -> {
            atualizarValoresPedido();
        });
        
        // Faz com que o produto selecionado seja adicionado ao pressionar ENTER no JSpinner de quantidade
        ((JSpinner.NumberEditor)jspn_quantidade.getEditor()).getTextField().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent ev) {
                if (ev.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    javax.swing.SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            incluirProduto();
                        }
                    });
                }
            }
        });
        javax.swing.SwingUtilities.invokeLater(() -> {
            preencherPedidosPorEstado();
        });
        
        onFinalizarAlteracao = () -> {
            jTabbedPane1.setSelectedIndex(0);
            jTabbedPane1.remove(3);
            preencherPedidosPorEstado();
            pnl_alterarPedido = null;
        };
        
        jTable_ProdutoPedido.getModel().addTableModelListener((e) -> {
            ProdutoPedidoTableModel model = (ProdutoPedidoTableModel)e.getSource();
            if (e.getColumn() == ProdutoPedidoTableModel.COL_QUANTIDADE && e.getType() == TableModelEvent.UPDATE) {
                atualizarValoresPedido();
            }
        });
        
        datePicker1.addDateChangeListener((e) -> {
            if (e.getNewDate().equals(LocalDate.now())) {
                timePicker1.getSettings().setVetoPolicy(vetoPolicy);
                timePicker1.getSettings().generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);
                timePicker1.setTime(LocalTime.of(java.time.LocalTime.now().plusHours(1).getHour(), 00));
            } else {
                timePicker1.getSettings().setVetoPolicy(null);
                timePicker1.getSettings().generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);
            }
        });
    }

    public void incluirProduto() {
        ProdutoPedido produtoAdicionar = null;
        Produto produtoSelecionado;
        
        produtoSelecionado = (Produto)jcmb_produto.getSelectedItem();
        try {
            produtoAdicionar = new ProdutoPedido(produtoSelecionado, (int)jspn_quantidade.getValue());
            jTable_ProdutoPedido.getModel().addRow(produtoAdicionar);
            clearProdutoFieldsInfo();
            hideProdutoErrorLabels();
        } catch (IllegalArgumentsException exs) {
            for (Throwable t : exs.getCauses()) {
                if (t instanceof IllegalProdutoException) {
                    jlbl_erro_produto.setText(t.getMessage());
                    jlbl_erro_produto.setVisible(true);
                } else if (t instanceof IllegalQuantidadeException) {
                    jlbl_erro_quantidadeProduto.setText(t.getMessage());
                    jlbl_erro_quantidadeProduto.setVisible(true);
                }
            }
        }
        atualizarValoresPedido();
    }
    
    public void fillEstadosComboBoxPedido() {
        jcmb_estadoInicial.addItem(Estado.ABERTO);
        jcmb_estadoInicial.addItem(Estado.AGUARDANDO_PAGAMENTO);
        jcmb_estadoInicial.addItem(Estado.PAGO);
        jcmb_estadoInicial.addItem(Estado.EM_PREPARO);
//        jcmb_estadoInicial.addItem(Estado.AGUARDANDO_ENVIO);
//        jcmb_estadoInicial.addItem(Estado.AGUARDANDO_RETIRADA);
//        jcmb_estadoInicial.addItem(Estado.SAIU_PARA_ENTREGA);
//        jcmb_estadoInicial.addItem(Estado.CONCLUIDO);
//        jcmb_estadoInicial.addItem(Estado.CANCELADO);
    }
    
    public void fillTipoEntregaComboBoxPedido() {
        jcmb_tipoEntrega.addItem(TipoEntrega.ENVIO);
        jcmb_tipoEntrega.addItem(TipoEntrega.RETIRADA);
    }
    
    public void clearFieldsInfo() {
        jtxtf_telefoneCliente.setText(null);
        jtxtf_nomeCliente.setText(null);
        if (jcmb_tipoEntrega.getItemCount() > 0) {
            jcmb_tipoEntrega.setSelectedIndex(0);
        }
        
        datePicker1.setDateToToday();
        timePicker1.setTime(java.time.LocalTime.of(java.time.LocalTime.now().getHour(), 00));
        jtxtf_rua.setText(null);
        jtxtf_numero.setText(null);
        jtxtf_bairro.setText(null);
        jtxtf_cidade.setText(null);
        jcmb_uf.setSelectedIndex(7);
        jtxta_observacoes.setText(null);
        clearProdutoFieldsInfo();
        jspn_valorEntrega.setValue(0.0);
        jspn_desconto.setValue(0);
        infoAdicionalCliente = null;
        jTable_ProdutoPedido.getModel().clearRows();
        
    }
    
    public Pedido getFieldsInfo() {
        hideErrorLabels();
        
        int txDesconto;
        double valorEntrega, valorTotal, custoTotal;
        String nome, telefone, strDataEntregar, strHoraEntregar, rua, numeroDestino, bairro, cidade, uf, observacoes, strTaxaDesconto, strValorEntrega, strValorTotal, strCustoTotal;
        Cliente cliente;
        TipoEntrega tipoEntrega;
        Destino destinoEntrega;
        LocalDate dataEntregar;
        LocalTime horaEntregar;
        InfoEntrega infoEntrega;
        Registro criacao;
        LocalDateTime dthrEntregar;
        LocalDate dtPago;
        LocalDate dtVencimentoPagamento;
        ProdutoPedido[] produtosAdicionados;
        EstadoPedido estadoInicial;
        Pedido p;
        
        IllegalArgumentsException exs = new IllegalArgumentsException();
        
        // Criação de cliente
        nome = jtxtf_nomeCliente.getText().trim();
        telefone = jtxtf_telefoneCliente.getText().trim();
        StringBuilder newStr = new StringBuilder();

        for (int i=0; i < telefone.length(); i++) {
            char c = telefone.charAt(i);

            if(Character.isDigit(c)) {
                newStr.append(c);
            }
        }
        telefone = newStr.toString();
        
        if (clienteEncontrado == null) {
            try {
                cliente = new Cliente(nome, telefone);
                cliente.setInfoAdicional(infoAdicionalCliente);
    //            cliente.setId(1); // RETIRAR quando tiver método de criar usuario
            } catch (IllegalArgumentsException newExs) {
                exs.addCause(newExs.getCauses());
                cliente = null;
            }
        } else {
            cliente = clienteEncontrado;
        }
        
        // Tipo de entrega
        tipoEntrega = (TipoEntrega)jcmb_tipoEntrega.getSelectedItem();
        
        dataEntregar = datePicker1.getDate();
        horaEntregar = timePicker1.getTime();
        dthrEntregar = LocalDateTime.of(dataEntregar, horaEntregar);
        
        // Criação do destino de entrega
        if (tipoEntrega == TipoEntrega.ENVIO) {
            rua = jtxtf_rua.getText().trim();
            numeroDestino = jtxtf_numero.getText().trim();
            bairro = jtxtf_bairro.getText().trim();
            cidade = jtxtf_cidade.getText().trim();
            uf = (String)jcmb_uf.getSelectedItem();
            
            try {
                destinoEntrega = new Destino(rua, numeroDestino, bairro, cidade, uf);
            } catch (IllegalArgumentsException newExs) {
                exs.addCause(newExs.getCauses());
                destinoEntrega = null;
            }
        } else {
            destinoEntrega = null;
        }
        observacoes = jtxta_observacoes.getText().trim();
        
        
        // Info de entrega
        try {
            valorEntrega = (Double)jspn_valorEntrega.getValue();
            if (valorEntrega < 0.0 || valorEntrega > (Double)((javax.swing.SpinnerNumberModel)jspn_valorEntrega.getModel()).getMaximum()) {
                infoEntrega = null;
                throw new NumberFormatException("Preço do frete não aceito.");
            } else {
                infoEntrega = new InfoEntrega(tipoEntrega, dthrEntregar, valorEntrega);
            }
        } catch (NumberFormatException ex) {
            exs.addCause(new IllegalPrecoFreteException("Preço de frete inválido."));
            infoEntrega = null;
        } catch (IllegalArgumentsException ex) {
            exs.addCause(ex.getCauses());
            infoEntrega = null;
        }
        
        if (infoEntrega != null) {
            try {
                infoEntrega.setDestino(destinoEntrega);
            } catch (IllegalDestinoException newEx) {
                exs.addCause(newEx);
            }
            
            try {
                infoEntrega.setDestinatario(observacoes);
            } catch (IllegalDestinatarioException newEx) {
                exs.addCause(newEx);
            }
        }
        
        // Produtos adicionados
        produtosAdicionados = jTable_ProdutoPedido.getModel().getRows(); // Retorna null, se não houverem itens na tabela
        
        // Desconto
        txDesconto = (int)jspn_desconto.getValue();
        
        // Definição do estado inicial
        Estado recebido = (Estado)jcmb_estadoInicial.getSelectedItem();
        if (recebido != null) {
            estadoInicial = new EstadoPedido(recebido);
        } else {
            estadoInicial = new EstadoPedido(Estado.ABERTO);
        }
        
        // Tentativa de criação do Pedido
        try {
            p = new Pedido(cliente, infoEntrega, produtosAdicionados, txDesconto);
            p.setEstadoAtual(estadoInicial);
            // TODO: definir data de vencimento do pagamento, se houver
        } catch (IllegalArgumentsException newExs) {
            Throwable[] causes = newExs.getCauses();
            for (Throwable t : causes) {
                if (t instanceof IllegalTaxaDescontoException) {
                    exs.addCause(t);
                } else if (t instanceof IllegalProdutoPedidoArrayException) {
                    exs.addCause(t);
                }
            }
            
            p = null;
        }
        
        // Verificação de erros
        if (exs.size() > 0) {
            Throwable[] causes = exs.getCauses();
            for (Throwable t : causes) {
                if (t instanceof IllegalNomeException) {
                    jlbl_erro_nome.setVisible(true);
                    jlbl_erro_nome.setText(t.getMessage());
                } else if (t instanceof IllegalTelefoneException) {
                    jlbl_erro_telefone.setVisible(true);
                    jlbl_erro_telefone.setText(t.getMessage());
                } else if (t instanceof IllegalTipoEntregaException) {
                    jlbl_erro_tipoEntrega.setVisible(true);
                    jlbl_erro_tipoEntrega.setText(t.getMessage());
                } else if (t instanceof IllegalDataHoraEntregarException) {
                    jlbl_erro_dataEntrega.setVisible(true);
                    jlbl_erro_dataEntrega.setText(t.getMessage());
                    jlbl_erro_horaEntrega.setVisible(true);
                    jlbl_erro_horaEntrega.setText(t.getMessage());
                } else if (t instanceof IllegalLogradouroException) {
                    jlbl_erro_ruaEntrega.setVisible(true);
                    jlbl_erro_ruaEntrega.setText(t.getMessage());
                } else if (t instanceof IllegalNumeroException) {
                    jlbl_erro_numeroEntrega.setVisible(true);
                    jlbl_erro_numeroEntrega.setText(t.getMessage());
                } else if (t instanceof IllegalBairroException) {
                    jlbl_erro_bairroEntrega.setVisible(true);
                    jlbl_erro_bairroEntrega.setText(t.getMessage());
                } else if (t instanceof IllegalCidadeException) {
                    jlbl_erro_cidadeEntrega.setVisible(true);
                    jlbl_erro_cidadeEntrega.setText(t.getMessage());
                } else if (t instanceof IllegalEstadoException) {
                    jlbl_erro_ufEntrega.setVisible(true);
                    jlbl_erro_ufEntrega.setText(t.getMessage());
                } else if (t instanceof IllegalPaisException) {

                } else if (t instanceof IllegalDestinoException) {
                    p = null;
                } else if (t instanceof IllegalDestinatarioException) {
                    jlbl_erro_observacoesEntrega.setVisible(true);
                    jlbl_erro_observacoesEntrega.setText(t.getMessage());
                } if (t instanceof IllegalProdutoPedidoArrayException) {
                    JOptionPane.showMessageDialog(null, "Insira ao menos um produto antes de completar o pedido.", "Falha ao cadastrar pedido", JOptionPane.INFORMATION_MESSAGE);
                } else if (t instanceof IllegalTaxaDescontoException) {
                    jlbl_erro_desconto.setVisible(true);
                    jlbl_erro_desconto.setText(t.getMessage());
                } else if (t instanceof IllegalPrecoFreteException) {
                    jlbl_erro_valorEntrega.setVisible(true);
                    jlbl_erro_valorEntrega.setText(t.getMessage());
                }
            }
        }
        
        
        return p;
    }
    
    public void clearProdutoFieldsInfo() {
        if (jcmb_produto.getItemCount() > 0) {
            jcmb_produto.setSelectedIndex(0);
        }
        
        jspn_quantidade.setValue(1);
        jTable_ProdutoPedido.getTable().getSelectionModel().clearSelection();
    }
    
    public void hideErrorLabels() {
        jlbl_erro_bairroEntrega.setVisible(false);
        jlbl_erro_cidadeEntrega.setVisible(false);
        jlbl_erro_dataEntrega.setVisible(false);
        jlbl_erro_desconto.setVisible(false);
        jlbl_erro_estadoInicial.setVisible(false);
        jlbl_erro_horaEntrega.setVisible(false);
        jlbl_erro_nome.setVisible(false);
        jlbl_erro_numeroEntrega.setVisible(false);
        jlbl_erro_observacoesEntrega.setVisible(false);
        hideProdutoErrorLabels();
        jlbl_erro_ruaEntrega.setVisible(false);
        jlbl_erro_telefone.setVisible(false);
        jlbl_erro_tipoEntrega.setVisible(false);
        jlbl_erro_ufEntrega.setVisible(false);
        jlbl_erro_valorEntrega.setVisible(false);
        jlbl_erro_valorTotal.setVisible(false);
    }
    
    public void hideProdutoErrorLabels() {
        jlbl_erro_produto.setVisible(false);
        jlbl_erro_quantidadeProduto.setVisible(false);
    }
    
    public void setInfoAdicionalCliente(Cliente.InfoAdicional info) {
        this.infoAdicionalCliente = info;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnl_principal = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpnl_pedidosAberto = new javax.swing.JPanel();
        jtxtf_pesquisarPedidos = new javax.swing.JTextField();
        jlbl_filtroPedidosEmAberto = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jcmb_filtro_pedidos_aberto = new javax.swing.JComboBox<>();
        for (var e : japedidos.pedidos.Estado.getAll()) {
            jcmb_filtro_pedidos_aberto.addItem(e);
        }
        jcmb_filtro_pedidos_aberto.addItemListener((e) -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                japedidos.pedidos.Estado estadoSelecionado = (japedidos.pedidos.Estado)e.getItem();
                jTable_Pedido_Resumido1.preencher(estadoSelecionado);
            }
        });
        jButton3 = new javax.swing.JButton();
        jTable_Pedido_Resumido1 = new japedidos.pedidos.JTable_Pedido_Resumido();
        jpnl_incluirPedido = new javax.swing.JPanel();
        jscp_destinatario = new javax.swing.JScrollPane();
        jtxta_observacoes = new javax.swing.JTextArea();
        jcmb_estadoInicial = new javax.swing.JComboBox<>();
        jlbl_horaEntrega = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jtxtf_valorTotal = new javax.swing.JTextField();
        jpnl_btn_novo = new javax.swing.JLabel();
        jcmb_tipoEntrega = new javax.swing.JComboBox<>();
        jtxtf_nomeCliente = new javax.swing.JTextField();
        jlbl_bairro = new javax.swing.JLabel();
        jlbl_rua = new javax.swing.JLabel();
        jtxtf_cidade = new javax.swing.JTextField();
        jcmb_produto = new javax.swing.JComboBox<>();
        jlbl_cidade = new javax.swing.JLabel();
        jlbl_estadoInicial = new javax.swing.JLabel();
        jlbl_tipoEntrega = new javax.swing.JLabel();
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("d MMM yyyy");
        dateSettings.setFormatForDatesBeforeCommonEra("d MMM uuuu");
        dateSettings.setAllowEmptyDates(false);
        datePicker1 = new com.github.lgooddatepicker.components.DatePicker(dateSettings);
        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setAllowEmptyTimes(false);
        timeSettings.use24HourClockFormat();
        timeSettings.initialTime = LocalTime.of(java.time.LocalTime.now().plusHours(1).getHour(), 00);
        timeSettings.generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.FifteenMinutes, null, null);
        timePicker1 = new TimePicker(timeSettings);
        jtxtf_rua = new javax.swing.JTextField();
        jlbl_uf = new javax.swing.JLabel();
        jtxtf_bairro = new javax.swing.JTextField();
        jlbl_desconto = new javax.swing.JLabel();
        jtxtf_telefoneCliente = new javax.swing.JTextField();
        jlbl_telefoneCliente = new javax.swing.JLabel();
        jlbl_quantidadeProduto = new javax.swing.JLabel();
        jlbl_valorTotal = new javax.swing.JLabel();
        jlbl_dataEntrega = new javax.swing.JLabel();
        jspn_quantidade = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(0, 0, 10000, 1));
        jlbl_observações = new javax.swing.JLabel();
        jlbl_nomeCliente = new javax.swing.JLabel();
        jtxtf_numero = new javax.swing.JTextField();
        jlbl_numero = new javax.swing.JLabel();
        jlbl_produto = new javax.swing.JLabel();
        jlbl_valorEntrega = new javax.swing.JLabel();
        jlbl_erro_estadoInicial = new javax.swing.JLabel();
        jlbl_erro_bairroEntrega = new javax.swing.JLabel();
        jlbl_erro_desconto = new javax.swing.JLabel();
        jlbl_erro_horaEntrega = new javax.swing.JLabel();
        jlbl_erro_observacoesEntrega = new javax.swing.JLabel();
        jlbl_erro_telefone = new javax.swing.JLabel();
        jlbl_erro_ruaEntrega = new javax.swing.JLabel();
        jlbl_erro_numeroEntrega = new javax.swing.JLabel();
        jlbl_erro_cidadeEntrega = new javax.swing.JLabel();
        jlbl_erro_ufEntrega = new javax.swing.JLabel();
        jlbl_erro_nome = new javax.swing.JLabel();
        jlbl_erro_tipoEntrega = new javax.swing.JLabel();
        jlbl_erro_dataEntrega = new javax.swing.JLabel();
        jlbl_erro_produto = new javax.swing.JLabel();
        jlbl_erro_quantidadeProduto = new javax.swing.JLabel();
        jlbl_erro_valorEntrega = new javax.swing.JLabel();
        jlbl_erro_valorTotal = new javax.swing.JLabel();
        jspn_desconto = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        jspn_valorEntrega = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(0.0, 0.0, 100000.0, 0.01));
        jcmb_uf = new javax.swing.JComboBox<>();
        jlbl_btn_excluir = new javax.swing.JLabel();
        jlbl_btn_incluir = new javax.swing.JLabel();
        jlbl_btn_criarPedido = new javax.swing.JLabel();
        jlbl_btn_inforAdicional = new javax.swing.JLabel();
        jTable_ProdutoPedido = new japedidos.produto.JTable_ProdutoPedido();
        jpnl_historicoPedidos = new javax.swing.JPanel();
        jbtn_visualizarPedido = new javax.swing.JButton();
        jlbl_encontrar = new javax.swing.JLabel();
        jtb_linhaEncontrar = new javax.swing.JToolBar();
        jtxtf_pesquisarHistoricoPedido = new javax.swing.JTextField();
        jtbl_pedidos = new japedidos.pedidos.JTable_Pedido_Resumido();
        jpnl_sideMenu = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jlbl_clientes = new javax.swing.JLabel();
        jlbl_produtos = new javax.swing.JLabel();
        jlbl_pedidos = new javax.swing.JLabel();
        jlbl_relatorios = new javax.swing.JLabel();
        jpnl_img_etiqueta = new javax.swing.JLabel();
        jpnl_background1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JaPedidos - Gerenciar Pedidos");
        setMinimumSize(new java.awt.Dimension(1024, 576));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jpnl_principal.setBackground(new java.awt.Color(153, 204, 255));
        jpnl_principal.setMaximumSize(new java.awt.Dimension(1024, 576));
        jpnl_principal.setMinimumSize(new java.awt.Dimension(1024, 576));
        jpnl_principal.setPreferredSize(new java.awt.Dimension(1024, 576));
        jpnl_principal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });
        jTabbedPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jTabbedPane1ComponentShown(evt);
            }
        });

        jpnl_pedidosAberto.setOpaque(false);
        jpnl_pedidosAberto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jpnl_pedidosAbertoFocusGained(evt);
            }
        });
        jpnl_pedidosAberto.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jpnl_pedidosAbertoComponentShown(evt);
            }
        });
        jpnl_pedidosAberto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        // Pegar pedidos do BD
        javax.swing.SwingUtilities.invokeLater(() -> {});

        jtxtf_pesquisarPedidos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtxtf_pesquisarPedidos.setText("PESQUISAR..");
        jtxtf_pesquisarPedidos.setForeground(new java.awt.Color(102, 102, 102));
        jtxtf_pesquisarPedidos.setPreferredSize(new java.awt.Dimension(96, 22));
        jtxtf_pesquisarPedidos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtf_pesquisarPedidosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtf_pesquisarPedidosFocusLost(evt);
            }
        });
        jtxtf_pesquisarPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtf_pesquisarPedidosActionPerformed(evt);
            }
        });
        jpnl_pedidosAberto.add(jtxtf_pesquisarPedidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 440, 30));

        jlbl_filtroPedidosEmAberto.setText("FILTRO:");
        jlbl_filtroPedidosEmAberto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_pedidosAberto.add(jlbl_filtroPedidosEmAberto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 24, -1, -1));

        jButton1.setText("Alterar pedido");
        jButton1.setFont(new java.awt.Font("Noto Sans", 0, 13)); // NOI18N
        jButton1.setMargin(new java.awt.Insets(4, 14, 4, 14));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jpnl_pedidosAberto.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(534, 386, 120, 30));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Intercorrencia.png"))); // NOI18N
        jButton2.setText("Relatar intercorrência");
        jButton2.setMargin(new java.awt.Insets(4, 14, 4, 14));
        jpnl_pedidosAberto.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 386, 230, 30));

        jpnl_pedidosAberto.add(jcmb_filtro_pedidos_aberto, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 170, 30));

        jButton3.setText("Alterar estado");
        jButton3.setMargin(new java.awt.Insets(4, 14, 4, 14));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jpnl_pedidosAberto.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 386, 120, 30));
        jpnl_pedidosAberto.add(jTable_Pedido_Resumido1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 59, 690, 320));

        jTabbedPane1.addTab("Pedidos em aberto", jpnl_pedidosAberto);

        jpnl_incluirPedido.setMaximumSize(new java.awt.Dimension(740, 453));
        jpnl_incluirPedido.setPreferredSize(new java.awt.Dimension(740, 200));
        jpnl_incluirPedido.setOpaque(false);
        jpnl_incluirPedido.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jpnl_incluirPedidoComponentShown(evt);
            }
        });
        jpnl_incluirPedido.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtxta_observacoes.setColumns(20);
        jtxta_observacoes.setFont(jtxta_observacoes.getFont().deriveFont((float)12));
        jtxta_observacoes.setLineWrap(true);
        jtxta_observacoes.setRows(4);
        jtxta_observacoes.setTabSize(4);
        jtxta_observacoes.setWrapStyleWord(true);
        jscp_destinatario.setViewportView(jtxta_observacoes);

        jpnl_incluirPedido.add(jscp_destinatario, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 260, 50));

        jpnl_incluirPedido.add(jcmb_estadoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 394, 140, -1));

        jlbl_horaEntrega.setText("HORA:");
        jlbl_horaEntrega.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_horaEntrega.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_horaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, 20));

        jSeparator1.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jpnl_incluirPedido.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 690, 10));

        jtxtf_valorTotal.setEditable(false);
        jtxtf_valorTotal.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jtxtf_valorTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jpnl_incluirPedido.add(jtxtf_valorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 398, 90, -1));

        jpnl_btn_novo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpnl_btn_novoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jpnl_btn_novoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jpnl_btn_novoMouseReleased(evt);
            }
        });
        jpnl_incluirPedido.add(jpnl_btn_novo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, -1, -1));

        jcmb_tipoEntrega.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcmb_tipoEntregaItemStateChanged(evt);
            }
        });
        jpnl_incluirPedido.add(jcmb_tipoEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 90, -1));
        jpnl_incluirPedido.add(jtxtf_nomeCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 30, 220, -1));

        jlbl_bairro.setText("BAIRRO:");
        jlbl_bairro.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_bairro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_bairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(436, 90, 60, -1));

        jlbl_rua.setText("RUA:");
        jlbl_rua.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_rua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_rua, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 90, -1, -1));
        jpnl_incluirPedido.add(jtxtf_cidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 120, -1));

        jcmb_produto.setEnabled(false);
        jcmb_produto.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jcmb_produtoComponentShown(evt);
            }
        });
        jpnl_incluirPedido.add(jcmb_produto, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 240, -1));

        jlbl_cidade.setText("CIDADE:");
        jlbl_cidade.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_cidade.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_cidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 140, -1, -1));

        jlbl_estadoInicial.setText("ESTADO INICIAL:");
        jlbl_estadoInicial.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_estadoInicial.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_estadoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 396, 120, 20));

        jlbl_tipoEntrega.setText("ENTREGA:");
        jlbl_tipoEntrega.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_tipoEntrega.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_tipoEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, -1, 20));

        datePicker1.getComponentDateTextField().setPreferredSize(new java.awt.Dimension(80, 20));
        datePicker1.setDateToToday();
        dateSettings.setDateRangeLimits(LocalDate.now(), LocalDate.now().plusYears(3));
        javax.swing.JButton datePickerButton = datePicker1.getComponentToggleCalendarButton();
        datePickerButton.setPreferredSize(new java.awt.Dimension(22, 22));
        datePickerButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        datePickerButton.setOpaque(false);
        datePickerButton.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        datePickerButton.setBackground(new java.awt.Color(0, 0, 0, 0));
        datePickerButton.setText("");
        javax.swing.ImageIcon dateExampleIcon = new javax.swing.ImageIcon(getClass().getResource("/datepickerbutton1.png"));
        datePickerButton.setIcon(dateExampleIcon);
        java.awt.Dimension newDateButtonSize = new java.awt.Dimension(dateExampleIcon.getIconWidth() + 4, dateExampleIcon.getIconHeight() + 4);
        datePickerButton.setPreferredSize(newDateButtonSize);

        datePickerButton.setContentAreaFilled(false);
        datePickerButton.setBorderPainted(false);
        datePickerButton.setFocusPainted(false);
        datePicker1.setBackground(new java.awt.Color(0,0,0,0));
        datePicker1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                datePicker1MouseExited(evt);
            }
        });
        jpnl_incluirPedido.add(datePicker1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 120, -1));

        timeSettings.setVetoPolicy(vetoPolicy);
        timeSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);
        timePicker1.getComponentTimeTextField().setPreferredSize(new java.awt.Dimension(40, 20));
        javax.swing.JButton timePickerButton = timePicker1.getComponentToggleTimeMenuButton();
        timePickerButton.setText("");
        javax.swing.ImageIcon timeExampleIcon = new javax.swing.ImageIcon(this.getClass().getResource("/timepickerbutton1.png"));
        timePickerButton.setIcon(timeExampleIcon);
        // Adjust the button size to fit the new icon.
        java.awt.Dimension newTimeButtonSize =
        new java.awt.Dimension(timeExampleIcon.getIconWidth() + 4, timeExampleIcon.getIconHeight() + 4);
        timePickerButton.setPreferredSize(newTimeButtonSize);
        jpnl_incluirPedido.add(timePicker1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 80, -1));
        jpnl_incluirPedido.add(jtxtf_rua, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 260, -1));

        jlbl_uf.setText("UF:");
        jlbl_uf.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_uf.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_uf, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, -1, -1));
        jpnl_incluirPedido.add(jtxtf_bairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(504, 90, 190, -1));

        jlbl_desconto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbl_desconto.setText("DESC. %:");
        jlbl_desconto.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_desconto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_desconto, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 316, 90, -1));

        jtxtf_telefoneCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtf_telefoneClienteFocusLost(evt);
            }
        });
        jtxtf_telefoneCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtf_telefoneClienteActionPerformed(evt);
            }
        });
        jpnl_incluirPedido.add(jtxtf_telefoneCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 30, 110, -1));

        jlbl_telefoneCliente.setText("TELEFONE:");
        jlbl_telefoneCliente.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_telefoneCliente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_telefoneCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 10, -1, -1));

        jlbl_quantidadeProduto.setText("QTD:");
        jlbl_quantidadeProduto.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_quantidadeProduto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_quantidadeProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 216, 40, 30));

        jlbl_valorTotal.setText("TOTAL R$ :");
        jlbl_valorTotal.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_valorTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbl_valorTotal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jpnl_incluirPedido.add(jlbl_valorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 376, 90, -1));

        jlbl_dataEntrega.setText("DATA:");
        jlbl_dataEntrega.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_dataEntrega.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_dataEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 60, 20));

        jspn_quantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jspn_quantidadeKeyPressed(evt);
            }
        });
        jpnl_incluirPedido.add(jspn_quantidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 220, 60, -1));

        jlbl_observações.setText("OBSERVAÇÕES:");
        jlbl_observações.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_observações.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_observações, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 150, 20));

        jlbl_nomeCliente.setText("NOME:");
        jlbl_nomeCliente.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_nomeCliente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_nomeCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 10, 70, -1));
        jpnl_incluirPedido.add(jtxtf_numero, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 50, -1));

        jlbl_numero.setText("Nº:");
        jlbl_numero.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_numero.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_numero, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 30, -1));

        jlbl_produto.setText("ADICIONAR:");
        jlbl_produto.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_produto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_produto, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 220, -1, 20));

        jlbl_valorEntrega.setText("ENTREGA R$:");
        jlbl_valorEntrega.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_valorEntrega.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_valorEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 256, 90, -1));

        jlbl_erro_estadoInicial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_estadoInicial.setText("Info inválida!");
        jlbl_erro_estadoInicial.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_estadoInicial.setForeground(new java.awt.Color(255, 255, 255));
        jpnl_incluirPedido.add(jlbl_erro_estadoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 416, 140, 20));

        jlbl_erro_bairroEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_bairroEntrega.setText("Info inválida!");
        jlbl_erro_bairroEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_bairroEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_bairroEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 114, 180, 20));

        jlbl_erro_desconto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_desconto.setText("Info inválida!");
        jlbl_erro_desconto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_desconto.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_desconto, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 358, 90, 30));

        jlbl_erro_horaEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_horaEntrega.setText("Info inválida!");
        jlbl_erro_horaEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_horaEntrega.setForeground(new java.awt.Color(255, 255, 255));
        jpnl_incluirPedido.add(jlbl_erro_horaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 90, 20));

        jlbl_erro_observacoesEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_observacoesEntrega.setText("Info inválida!");
        jlbl_erro_observacoesEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_observacoesEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_observacoesEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, 260, 30));

        jlbl_erro_telefone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_telefone.setText("Info inválida!");
        jlbl_erro_telefone.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_telefone.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_telefone, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 150, 20));

        jlbl_erro_ruaEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_ruaEntrega.setText("Info inválida!");
        jlbl_erro_ruaEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_ruaEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_ruaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 260, -1));

        jlbl_erro_numeroEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_numeroEntrega.setText("Info inválida!");
        jlbl_erro_numeroEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_numeroEntrega.setForeground(new java.awt.Color(255, 255, 255));
        jpnl_incluirPedido.add(jlbl_erro_numeroEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, 150, 20));

        jlbl_erro_cidadeEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_cidadeEntrega.setText("Info inválida!");
        jlbl_erro_cidadeEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_cidadeEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_cidadeEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 164, 120, 20));

        jlbl_erro_ufEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_ufEntrega.setText("Info inválida!");
        jlbl_erro_ufEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_ufEntrega.setForeground(new java.awt.Color(255, 255, 255));
        jpnl_incluirPedido.add(jlbl_erro_ufEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 164, 80, 20));

        jlbl_erro_nome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_nome.setText("Info inválida!");
        jlbl_erro_nome.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_nome.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_nome, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 54, 240, 20));

        jlbl_erro_tipoEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_tipoEntrega.setText("Info inválida!");
        jlbl_erro_tipoEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_tipoEntrega.setForeground(new java.awt.Color(255, 255, 255));
        jpnl_incluirPedido.add(jlbl_erro_tipoEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 54, 90, 20));

        jlbl_erro_dataEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_dataEntrega.setText("Info inválida!");
        jlbl_erro_dataEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_dataEntrega.setForeground(new java.awt.Color(255, 255, 255));
        jpnl_incluirPedido.add(jlbl_erro_dataEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, 120, 20));

        jlbl_erro_produto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_produto.setText("Info inválida!");
        jlbl_erro_produto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_produto.setForeground(new java.awt.Color(255, 255, 255));
        jpnl_incluirPedido.add(jlbl_erro_produto, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 242, 240, 20));

        jlbl_erro_quantidadeProduto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_quantidadeProduto.setText("Info inválida!");
        jlbl_erro_quantidadeProduto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_quantidadeProduto.setForeground(new java.awt.Color(255, 255, 255));
        jpnl_incluirPedido.add(jlbl_erro_quantidadeProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 242, 200, 20));

        jlbl_erro_valorEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_valorEntrega.setText("Info inválida!");
        jlbl_erro_valorEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_valorEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_valorEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 298, 90, 30));

        jlbl_erro_valorTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_valorTotal.setText("Info inválida!");
        jlbl_erro_valorTotal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_valorTotal.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_valorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 420, 90, 30));

        jspn_desconto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jspn_descontoFocusLost(evt);
            }
        });
        jpnl_incluirPedido.add(jspn_desconto, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 336, 90, -1));

        jspn_valorEntrega.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jspn_valorEntregaFocusLost(evt);
            }
        });
        jpnl_incluirPedido.add(jspn_valorEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 276, 90, -1));

        jcmb_uf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        jcmb_uf.setSelectedIndex(7);
        jpnl_incluirPedido.add(jcmb_uf, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 80, -1));

        jlbl_btn_excluir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_btn_excluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_excluir_padrao.png"))); // NOI18N
        jlbl_btn_excluir.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jlbl_btn_excluir.setPreferredSize(null);
        jlbl_btn_excluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_btn_excluirMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jlbl_btn_excluirMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jlbl_btn_excluirMouseReleased(evt);
            }
        });
        jpnl_incluirPedido.add(jlbl_btn_excluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 220, 86, 33));

        jlbl_btn_incluir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_btn_incluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_incluir_padrao.png"))); // NOI18N
        jlbl_btn_incluir.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jlbl_btn_incluir.setPreferredSize(null);
        jlbl_btn_incluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_btn_incluirMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jlbl_btn_incluirMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jlbl_btn_incluirMouseReleased(evt);
            }
        });
        jpnl_incluirPedido.add(jlbl_btn_incluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, 86, 33));

        jlbl_btn_criarPedido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_btn_criarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_criar_pedido_padrao.png"))); // NOI18N
        jlbl_btn_criarPedido.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jlbl_btn_criarPedido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_btn_criarPedidoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jlbl_btn_criarPedidoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jlbl_btn_criarPedidoMouseReleased(evt);
            }
        });
        jpnl_incluirPedido.add(jlbl_btn_criarPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 390, 160, 33));

        jlbl_btn_inforAdicional.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_btn_inforAdicional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_infor_adicional_padrao.png"))); // NOI18N
        jlbl_btn_inforAdicional.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jlbl_btn_inforAdicional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_btn_inforAdicionalMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jlbl_btn_inforAdicionalMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jlbl_btn_inforAdicionalMouseReleased(evt);
            }
        });
        jpnl_incluirPedido.add(jlbl_btn_inforAdicional, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 390, 160, 33));
        jpnl_incluirPedido.add(jTable_ProdutoPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, 260, 600, 120));

        jTabbedPane1.addTab("Incluir pedido", jpnl_incluirPedido);

        jpnl_historicoPedidos.setOpaque(false);
        jpnl_historicoPedidos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbtn_visualizarPedido.setText("Visualizar Pedido");
        jbtn_visualizarPedido.setFont(new java.awt.Font("Noto Sans", 0, 13)); // NOI18N
        jbtn_visualizarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_visualizarPedidoActionPerformed(evt);
            }
        });
        jpnl_historicoPedidos.add(jbtn_visualizarPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 400, 150, 30));

        jlbl_encontrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon_encontrar.png"))); // NOI18N
        jlbl_encontrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_encontrarMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jlbl_encontrarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jlbl_encontrarMouseReleased(evt);
            }
        });
        jpnl_historicoPedidos.add(jlbl_encontrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 24, -1, -1));

        jtb_linhaEncontrar.setBackground(new java.awt.Color(102, 102, 102));
        jtb_linhaEncontrar.setBorder(null);
        jtb_linhaEncontrar.setBorderPainted(false);
        jtb_linhaEncontrar.setEnabled(false);
        jtb_linhaEncontrar.setFocusable(false);
        jtb_linhaEncontrar.setForeground(new java.awt.Color(102, 102, 102));
        jtb_linhaEncontrar.setRequestFocusEnabled(false);
        jpnl_historicoPedidos.add(jtb_linhaEncontrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 26, 3, 22));

        jtxtf_pesquisarHistoricoPedido.setText("Digite aqui que deseja encontrar...");
        jtxtf_pesquisarHistoricoPedido.setBackground(new java.awt.Color(204, 204, 204));
        jtxtf_pesquisarHistoricoPedido.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jtxtf_pesquisarHistoricoPedido.setFocusCycleRoot(true);
        jtxtf_pesquisarHistoricoPedido.setFocusTraversalPolicyProvider(true);
        jtxtf_pesquisarHistoricoPedido.setForeground(new java.awt.Color(102, 102, 102));
        jtxtf_pesquisarHistoricoPedido.setMargin(new java.awt.Insets(2, 54, 2, 6));
        jtxtf_pesquisarHistoricoPedido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtf_pesquisarHistoricoPedidoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtf_pesquisarHistoricoPedidoFocusLost(evt);
            }
        });
        jtxtf_pesquisarHistoricoPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtf_pesquisarHistoricoPedidoActionPerformed(evt);
            }
        });
        jtxtf_pesquisarHistoricoPedido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtf_pesquisarHistoricoPedidoKeyTyped(evt);
            }
        });
        jpnl_historicoPedidos.add(jtxtf_pesquisarHistoricoPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 18, 690, 36));
        jpnl_historicoPedidos.add(jtbl_pedidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 690, -1));

        jTabbedPane1.addTab("Histórico de pedidos", jpnl_historicoPedidos);

        jpnl_principal.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 690, 470));

        jpnl_sideMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpnl_sideMenu.setMaximumSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.setMinimumSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.setPreferredSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.setOpaque(false);
        jpnl_sideMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jToolBar1.setRollover(true);
        jpnl_sideMenu.add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 80, -1));

        jlbl_clientes.setText("CLIENTES");
        jlbl_clientes.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_clientes.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_clientesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jlbl_clientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jlbl_clientesMouseExited(evt);
            }
        });
        jpnl_sideMenu.add(jlbl_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        jlbl_produtos.setText("PRODUTOS");
        jlbl_produtos.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_produtos.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_produtos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jlbl_produtosFocusGained(evt);
            }
        });
        jlbl_produtos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_produtosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jlbl_produtosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jlbl_produtosMouseExited(evt);
            }
        });
        jpnl_sideMenu.add(jlbl_produtos, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        jlbl_pedidos.setText("PEDIDOS");
        jlbl_pedidos.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_pedidos.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_pedidos.setForeground(new java.awt.Color(255, 255, 255));
        jpnl_sideMenu.add(jlbl_pedidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, -1, -1));

        jlbl_relatorios.setText("RELATÓRIOS");
        jlbl_relatorios.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_relatorios.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_relatorios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_relatoriosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jlbl_relatoriosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jlbl_relatoriosMouseExited(evt);
            }
        });
        jpnl_sideMenu.add(jlbl_relatorios, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, -1, -1));

        jpnl_img_etiqueta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/painel_comandos_esquerda_05x.png"))); // NOI18N
        jpnl_img_etiqueta.setText("jLabel2");
        jpnl_img_etiqueta.setMaximumSize(new java.awt.Dimension(250, 576));
        jpnl_img_etiqueta.setMinimumSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.add(jpnl_img_etiqueta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 250, -1));

        jpnl_principal.add(jpnl_sideMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 576));

        jpnl_background1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jpnl_background1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/background_pedidos.png"))); // NOI18N
        jpnl_background1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jpnl_background1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jpnl_background1.setMaximumSize(new java.awt.Dimension(1024, 576));
        jpnl_background1.setMinimumSize(new java.awt.Dimension(1024, 576));
        jpnl_background1.setPreferredSize(new java.awt.Dimension(1024, 576));
        jpnl_background1.setToolTipText("");
        jpnl_principal.add(jpnl_background1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnl_principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnl_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void atualizarValoresPedido() {
        double valorFrete, valorFinalVenda;
        double taxaDesconto;
        
        try {
            valorFrete = (Double)jspn_valorEntrega.getValue();
            taxaDesconto = (int)jspn_desconto.getValue() / 100.0;
            valorFinalVenda = Pedido.precoFinalVenda(jTable_ProdutoPedido.getModel().getRows(), taxaDesconto, valorFrete);
            jtxtf_valorTotal.setText(String.format("%1.2f", valorFinalVenda));
        } catch (NumberFormatException ex) {
            System.out.println("Erro ao atualizar preço do pedido.");
        }
    }
    
    private void jlbl_produtosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jlbl_produtosFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_jlbl_produtosFocusGained

    private void jpnl_btn_novoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnl_btn_novoMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jpnl_btn_novoMouseClicked

    private void jpnl_btn_novoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnl_btn_novoMousePressed
        // TODO add your handling code here:
        jpnl_btn_novo.setIcon(new javax.swing.ImageIcon("C:\\SENAI\\TECHNIGHT\\JaPedidos\\JaPedidos\\src\\main\\java\\japedidos\\imagens\\btn_novo_pressionado.png"));
    }//GEN-LAST:event_jpnl_btn_novoMousePressed

    private void jpnl_btn_novoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnl_btn_novoMouseReleased
        // TODO add your handling code here:
        //jpnl_btn_novo.setIcon(new javax.swing.ImageIcon("C:\\JaPedidos\\JaPedidos\\src\\main\\java\\japedidos\\imagens\\btn_novo_padrao.png"));
        jpnl_btn_novo.setIcon(new javax.swing.ImageIcon("C:\\SENAI\\TECHNIGHT\\JaPedidos\\JaPedidos\\src\\main\\java\\japedidos\\imagens\\btn_novo_padrao.png"));

    }//GEN-LAST:event_jpnl_btn_novoMouseReleased

    private void jtxtf_pesquisarPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtf_pesquisarPedidosActionPerformed
        // TODO add your handling code here:
        String sql_listaPedidos = "";
        String encontrar = jtxtf_pesquisarPedidos.getText().trim();
        Estado selecionado = (Estado)jcmb_filtro_pedidos_aberto.getSelectedItem();
        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        // se a caixa de pesquisa estiver vazia quando pressionar o enter , traga todos os produtos na view.
        if (encontrar.isEmpty() || encontrar.equals("Digite aqui que deseja encontrar...")){
            preencherPedidosPorEstado();
        } else {
            // se a caixa de pesquisa tiver dado quando pressionar o enter , procue nas descriçoes ou categorias.
            jTable_Pedido_Resumido1.getModel().fillRows(BD.Pedido.selectAllLike(encontrar, selecionado));
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jtxtf_pesquisarPedidosActionPerformed

    private void jspn_valorEntregaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jspn_valorEntregaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jspn_valorEntregaFocusLost

    private void jspn_descontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jspn_descontoFocusLost
        atualizarValoresPedido();
    }//GEN-LAST:event_jspn_descontoFocusLost

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jspn_quantidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jspn_quantidadeKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jspn_quantidadeKeyPressed

    private void preencherPedidosPorEstado() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        jTable_Pedido_Resumido1.preencher((japedidos.pedidos.Estado)jcmb_filtro_pedidos_aberto.getSelectedItem());
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    
    private void jpnl_pedidosAbertoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jpnl_pedidosAbertoFocusGained
        
    }//GEN-LAST:event_jpnl_pedidosAbertoFocusGained

    private void jpnl_pedidosAbertoComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jpnl_pedidosAbertoComponentShown
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jpnl_pedidosAbertoComponentShown

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int rSel = jTable_Pedido_Resumido1.getTable().getSelectedRow();
        if (rSel != -1) {
            Pedido pSelecionado = jTable_Pedido_Resumido1.getModel().getRow(rSel);
            Estado estadoAtual = pSelecionado.getEstadoAtualPedido().ESTADO;
            if (!estadoAtual.equals(Estado.CANCELADO) && !estadoAtual.equals(Estado.CONCLUIDO)) {
                JFrame frame = new JFrame("Atualizar estado do pedido");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(new JPanel_AtualizarEstado(pSelecionado, pSelecionado.getEstadoAtualPedido().ESTADO, Usuario.getAtual(), this::preencherPedidosPorEstado));
                frame.pack();
                int x = this.getX() + this.getWidth() / 2 - frame.getWidth() / 2;
                int y = this.getY() + this.getHeight()/ 2 - frame.getHeight() / 2;
                frame.setLocation(x, y);
                frame.setResizable(false);
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Não é possível alterar o estado de um pedido cancelado ou concluído.", "Atualizar pedido", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTabbedPane1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTabbedPane1ComponentShown
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTabbedPane1ComponentShown

    private void jcmb_produtoComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jcmb_produtoComponentShown
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jcmb_produtoComponentShown

    public void setEnabledDestinoFields(boolean valor) {
        jtxtf_rua.setEnabled(valor);
        jtxtf_numero.setEnabled(valor);
        jtxtf_bairro.setEnabled(valor);
        jtxtf_cidade.setEnabled(valor);
        jcmb_uf.setEnabled(valor);
    } 
    
    private void jpnl_incluirPedidoComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jpnl_incluirPedidoComponentShown
        // TODO add your handling code here:
        if (jcmb_produto.getItemCount() == 0) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                // Preencher lista de produtos
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                jlbl_erro_produto.setText("Recebendo produtos...");
                jlbl_erro_produto.setEnabled(true);
                Produto[] recebidos = BD.Produto.selectAll();
                if (recebidos != null) {
                    for (Produto p : recebidos) {
                        if (p != null) {
                            jcmb_produto.addItem(p);
                        }
                    }
                    jlbl_erro_produto.setText("Produtos recebidos!");
                    jlbl_erro_produto.setEnabled(false);
                    jcmb_produto.setEnabled(true);
                } else {
                    jlbl_erro_produto.setText("Nenhum produto recebido.");
                }
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            });
        }
    }//GEN-LAST:event_jpnl_incluirPedidoComponentShown

    private void jcmb_tipoEntregaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcmb_tipoEntregaItemStateChanged
        if (((JComboBox)evt.getSource()).getSelectedItem() == TipoEntrega.RETIRADA) {
            setEnabledDestinoFields(false);
        } else {
            setEnabledDestinoFields(true);
        }
    }//GEN-LAST:event_jcmb_tipoEntregaItemStateChanged

    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int rowSel = jTable_Pedido_Resumido1.getTable().getSelectedRow();
        
        if (rowSel != -1) {
            if (this.pnl_alterarPedido == null) {
                pnl_alterarPedido = new JPanel_AlterarPedido();
                pnl_alterarPedido.setOnFinishAction(onFinalizarAlteracao);
                jTabbedPane1.add("Editar pedido", pnl_alterarPedido);
            } else {
                int fechar = JOptionPane.showConfirmDialog(null, "Já existe um pedido em edição. Deseja cancelar e editar este?", "Ops! Edição em andamento",JOptionPane.YES_NO_OPTION);
                if (fechar != JOptionPane.YES_OPTION) {
                    jTabbedPane1.setSelectedIndex(3);
                    return;
                }
            }
            
            pnl_alterarPedido.definirPedidoAlterar(jTable_Pedido_Resumido1.getModel().getRow(rowSel));;
            jTabbedPane1.setSelectedIndex(3);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtxtf_pesquisarPedidosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtf_pesquisarPedidosFocusGained
        // TODO add your handling code here:
        this.jtxtf_pesquisarPedidos.setForeground(java.awt.Color.BLACK);
        this.jtxtf_pesquisarPedidos.setText("");
    }//GEN-LAST:event_jtxtf_pesquisarPedidosFocusGained

    private void jtxtf_pesquisarPedidosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtf_pesquisarPedidosFocusLost
        // TODO add your handling code here:
        this.jtxtf_pesquisarPedidos.setForeground(new java.awt.Color(102,102,102));
        this.jtxtf_pesquisarPedidos.setText("PESQUISAR..");
    }//GEN-LAST:event_jtxtf_pesquisarPedidosFocusLost

    private void jlbl_produtosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_produtosMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        japedidos.produto.JFrame_ListaProdutos frame = new japedidos.produto.JFrame_ListaProdutos();
        int x = this.getX() + this.getWidth() / 2 - frame.getWidth() / 2;
        int y = this.getY() + this.getHeight()/ 2 - frame.getHeight() / 2;
        frame.setLocation(x, y);
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jlbl_produtosMouseClicked

    private void jlbl_clientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_clientesMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        japedidos.clientes.JFrame_Cliente frame = new japedidos.clientes.JFrame_Cliente();
        int x = this.getX() + this.getWidth() / 2 - frame.getWidth() / 2;
        int y = this.getY() + this.getHeight()/ 2 - frame.getHeight() / 2;
        frame.setLocation(x, y);
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jlbl_clientesMouseClicked

    private void jlbl_produtosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_produtosMouseEntered
        // TODO add your handling code here:
        jlbl_produtos.setForeground(new Color(187,187,187));
    }//GEN-LAST:event_jlbl_produtosMouseEntered

    private void jlbl_produtosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_produtosMouseExited
        // TODO add your handling code here:
        jlbl_produtos.setForeground(Color.BLACK);
    }//GEN-LAST:event_jlbl_produtosMouseExited

    private void jlbl_clientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_clientesMouseEntered
        // TODO add your handling code here:
        jlbl_clientes.setForeground(new Color(187,187,187));
        
    }//GEN-LAST:event_jlbl_clientesMouseEntered

    private void jlbl_clientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_clientesMouseExited
        // TODO add your handling code here:
        jlbl_clientes.setForeground(Color.BLACK);
    }//GEN-LAST:event_jlbl_clientesMouseExited

    private void jlbl_relatoriosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_relatoriosMouseEntered
        // TODO add your handling code here:
        jlbl_relatorios.setForeground(new Color(187,187,187));
        
    }//GEN-LAST:event_jlbl_relatoriosMouseEntered

    private void jlbl_relatoriosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_relatoriosMouseExited
        // TODO add your handling code here:
        jlbl_relatorios.setForeground(Color.BLACK);
    }//GEN-LAST:event_jlbl_relatoriosMouseExited

    private void jlbl_btn_incluirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_incluirMouseClicked
        //        TODO add your handling code here:
        ProdutoPedido produtoAdicionar = null;
        Produto produtoSelecionado;
        
        produtoSelecionado = (Produto)jcmb_produto.getSelectedItem();
        try {
            produtoAdicionar = new ProdutoPedido(produtoSelecionado, (int)jspn_quantidade.getValue());
            jTable_ProdutoPedido.getModel().addRow(produtoAdicionar);
            clearProdutoFieldsInfo();
            hideProdutoErrorLabels();
        } catch (IllegalArgumentsException exs) {
            for (Throwable t : exs.getCauses()) {
                if (t instanceof IllegalProdutoException) {
                    jlbl_erro_produto.setText(t.getMessage());
                    jlbl_erro_produto.setVisible(true);
                } else if (t instanceof IllegalQuantidadeException) {
                    jlbl_erro_quantidadeProduto.setText(t.getMessage());
                    jlbl_erro_quantidadeProduto.setVisible(true);
                }
            }
        }
        atualizarValoresPedido();

    }//GEN-LAST:event_jlbl_btn_incluirMouseClicked

    private void jlbl_btn_incluirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_incluirMousePressed
        // TODO add your handling code here:
        jlbl_btn_incluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_incluir_pressionado.png")));
    }//GEN-LAST:event_jlbl_btn_incluirMousePressed

    private void jlbl_btn_incluirMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_incluirMouseReleased
        // TODO add your handling code here:
        jlbl_btn_incluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_incluir_padrao.png")));
    }//GEN-LAST:event_jlbl_btn_incluirMouseReleased

    private void jlbl_btn_excluirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_excluirMouseClicked
        // TODO add your handling code here:
                int selectedRow = jTable_ProdutoPedido.getTable().getSelectedRow();
        if (selectedRow != -1) {
            jTable_ProdutoPedido.getModel().removeRow(selectedRow);
            clearProdutoFieldsInfo();
            hideProdutoErrorLabels();
        }
        atualizarValoresPedido();
    }//GEN-LAST:event_jlbl_btn_excluirMouseClicked

    private void jlbl_btn_excluirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_excluirMousePressed
        // TODO add your handling code here:
        jlbl_btn_excluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_excluir_pressionado.png")));
    }//GEN-LAST:event_jlbl_btn_excluirMousePressed

    private void jlbl_btn_excluirMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_excluirMouseReleased
        // TODO add your handling code here:
        jlbl_btn_excluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_excluir_padrao.png")));
    }//GEN-LAST:event_jlbl_btn_excluirMouseReleased

    private void jlbl_btn_criarPedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_criarPedidoMouseClicked
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Pedido p = getFieldsInfo();
        if (p != null) {
            int r = BD.Pedido.insert(p);
            preencherPedidosPorEstado();
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            if (r > 0) {
                clearFieldsInfo();
                atualizarValoresPedido();
                JOptionPane.showMessageDialog(null, "Pedido cadastrado com sucesso!", "Cadastro de pedido", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Cadastro do pedido falhou!", "Cadastro de pedido", JOptionPane.ERROR_MESSAGE);
            }
            clienteEncontrado = null;
            telefoneDigitado = false;
        } else {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_jlbl_btn_criarPedidoMouseClicked

    private void jlbl_btn_criarPedidoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_criarPedidoMousePressed
        // TODO add your handling code here:
        jlbl_btn_criarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_criar_pedido_padrao.png")));
    }//GEN-LAST:event_jlbl_btn_criarPedidoMousePressed

    private void jlbl_btn_criarPedidoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_criarPedidoMouseReleased
        // TODO add your handling code here:
        jlbl_btn_criarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_criar_pedido_pressionado.png")));
    }//GEN-LAST:event_jlbl_btn_criarPedidoMouseReleased

    private void jlbl_btn_inforAdicionalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_inforAdicionalMouseClicked
        // TODO add your handling code here:
        JFrame_InfoAdicionalCliente frame;
        if (infoAdicionalCliente == null) {
            frame = new JFrame_InfoAdicionalCliente(this);
        } else {
            frame = new JFrame_InfoAdicionalCliente(this, this.infoAdicionalCliente);
        }
        int x = this.getX() + this.getWidth() / 2 - frame.getWidth() / 2;
        int y = this.getY() + this.getHeight()/ 2 - frame.getHeight() / 2;
        frame.setLocation(x, y);
        frame.setVisible(true);
        frame.setResizable(false);
    }//GEN-LAST:event_jlbl_btn_inforAdicionalMouseClicked

    private void jlbl_btn_inforAdicionalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_inforAdicionalMousePressed
        // TODO add your handling code here:
        
        jlbl_btn_inforAdicional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_infor_adicional_pressionado.png")));
        
    }//GEN-LAST:event_jlbl_btn_inforAdicionalMousePressed

    private void jlbl_btn_inforAdicionalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_inforAdicionalMouseReleased
        // TODO add your handling code here:
        jlbl_btn_inforAdicional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_infor_adicional_padrao.png")));
    }//GEN-LAST:event_jlbl_btn_inforAdicionalMouseReleased

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:

       setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

       try (Connection banco = DriverManager.getConnection(BD.CONNECTION_STRING, BD.USER, BD.USER_PWD)) {

            String sql_listaPedidos = "SELECT * FROM vw_pedido where nome_ultimo_est = \"Cancelado\" or nome_ultimo_est = \"Concluido\" GROUP BY nome_cliente ORDER BY  dthr_entregar DESC";
            
//            String sql_listaPedidos = "SELECT * FROM vw_pedido ORDER BY  dthr_entregar DESC";
            
            
           PreparedStatement stm_listaJTable = banco.prepareStatement(sql_listaPedidos);
            ResultSet resultado_listaJTable = stm_listaJTable.executeQuery();
            Pedido[] pedidosHistorico = BD.Pedido.parseView_pedido(resultado_listaJTable);
            if (pedidosHistorico != null) {
                jtbl_pedidos.getModel().fillRows(pedidosHistorico);
            }
            stm_listaJTable.close();
            banco.close();
       } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
            "Nao foi possivel conectar ao banco\n", 
            "JaPedidos", 
            JOptionPane.INFORMATION_MESSAGE);
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_formWindowOpened

    private void jbtn_visualizarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_visualizarPedidoActionPerformed
        // TODO add your handling code here:
//        String linhaSelecionada = (String) jtbl_pedidos.getValueAt(jtbl_pedidos.getSelectedRow(),0);
        int rowSel = jtbl_pedidos.getTable().getSelectedRow();

        System.out.println("rowSel - "+ rowSel);
        
        
        if (rowSel != -1) {
            if (this.pnl_alterarPedido == null) {
                pnl_alterarPedido = new JPanel_AlterarPedido();
                pnl_alterarPedido.setOnFinishAction(onFinalizarAlteracao);
                jTabbedPane1.add("Visualizar Pedido", pnl_alterarPedido);
            } else {
                int fechar = JOptionPane.showConfirmDialog(null, "Já existe um pedido em edição. Deseja cancelar e editar este?", "Ops! Edição em andamento",JOptionPane.YES_NO_OPTION);
                if (fechar != JOptionPane.YES_OPTION) {
                    jTabbedPane1.setSelectedIndex(3);
                    return;
                }
            }
            
//            pnl_alterarPedido.definirPedidoAlterar(jtbl_pedidos.getSelectedRows());
            pnl_alterarPedido.definirPedidoAlterar(jtbl_pedidos.getModel().getRow(rowSel));
            jTabbedPane1.setSelectedIndex(3);
            
        }        
        
        
        
        
    }//GEN-LAST:event_jbtn_visualizarPedidoActionPerformed

    private void jtxtf_pesquisarHistoricoPedidoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtf_pesquisarHistoricoPedidoFocusGained
        // TODO add your handling code here:
        jtxtf_pesquisarHistoricoPedido.setText("");
    }//GEN-LAST:event_jtxtf_pesquisarHistoricoPedidoFocusGained

    private void jtxtf_pesquisarHistoricoPedidoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtf_pesquisarHistoricoPedidoFocusLost
        // TODO add your handling code here:
        jtxtf_pesquisarHistoricoPedido.setText("Digite aqui que deseja encontrar e pressione ENTER...");
    }//GEN-LAST:event_jtxtf_pesquisarHistoricoPedidoFocusLost

    private void jtxtf_pesquisarHistoricoPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtf_pesquisarHistoricoPedidoActionPerformed
        String sql_listaPedidos = "";
        String encontrar = jtxtf_pesquisarHistoricoPedido.getText();

        // se a caixa de pesquisa estiver vazia quando pressionar o enter , traga todos os produtos na view.
        if (jtxtf_pesquisarHistoricoPedido.getText().equals("")|jtxtf_pesquisarHistoricoPedido.getText().equals("Digite aqui que deseja encontrar...")){
            sql_listaPedidos = "SELECT * FROM vw_pedido where nome_ultimo_est = \"Cancelado\" or nome_ultimo_est = \"Concluido\" GROUP BY nome_cliente ORDER BY  dthr_entregar DESC";
        } else {
            // se a caixa de pesquisa tiver dado quando pressionar o enter , procue nas descriçoes ou categorias.

            sql_listaPedidos = "SELECT * FROM vw_pedido where nome_ultimo_est = \"Cancelado\" or nome_ultimo_est =  \"Concluido\" GROUP BY nome_cliente having nome_cliente LIKE";
//select * from vw_pedido group by nome_cliente having nome_cliente LIKE " ;
//      sql_listaProdutos = "select * from vw_pedido group by nome_cliente having nome_cliente LIKE " ;
            sql_listaPedidos =     sql_listaPedidos + "'%" +
            encontrar + "%'" + " or dthr_entregar LIKE " +
            "'%" + encontrar + "%'" + " or telefone_cliente LIKE " +
            "'%" + encontrar + "%'" +" ORDER BY nome_cliente ASC";
            
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try(Connection banco = DriverManager.getConnection(BD.CONNECTION_STRING, BD.USER, BD.USER_PWD)) {

            PreparedStatement stm_listaJTable = banco.prepareStatement(sql_listaPedidos);
            ResultSet resultado_listaJTable = stm_listaJTable.executeQuery();
            Pedido[] pedidosHistorico = BD.Pedido.parseView_pedido(resultado_listaJTable);
            if (pedidosHistorico != null) {
                jtbl_pedidos.getModel().fillRows(pedidosHistorico);
            } else {
                jtbl_pedidos.getModel().clearRows();
            }
            stm_listaJTable.close();
            banco.close();

        } catch (SQLException ex) {
            //            e.printStackTrace();
            Logger.getLogger(JFrame_GerenciamentoPedidos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,
                "Erro ao acessar banco.\n"+
                "Erro: "+ ex,
                "JaPedidos",
                JOptionPane.INFORMATION_MESSAGE);
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jtxtf_pesquisarHistoricoPedidoActionPerformed

    private void jtxtf_pesquisarHistoricoPedidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtf_pesquisarHistoricoPedidoKeyTyped

    }//GEN-LAST:event_jtxtf_pesquisarHistoricoPedidoKeyTyped

    private void jlbl_encontrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_encontrarMouseClicked
        String sql_listaPedidos = "";
        String encontrar = jtxtf_pesquisarHistoricoPedido.getText();

        // se a caixa de pesquisa estiver vazia quando pressionar o enter , traga todos os produtos na view.
        if (jtxtf_pesquisarHistoricoPedido.getText().equals("")|jtxtf_pesquisarHistoricoPedido.getText().equals("Digite aqui que deseja encontrar...")){
            sql_listaPedidos = "SELECT * FROM vw_pedido where nome_ultimo_est = \"Cancelado\" or nome_ultimo_est = \"Concluido\" GROUP BY nome_cliente ORDER BY  dthr_entregar DESC";
        } else {
            // se a caixa de pesquisa tiver dado quando pressionar o enter , procue nas descriçoes ou categorias.

            sql_listaPedidos = "SELECT * FROM vw_pedido where nome_ultimo_est = \"Cancelado\" or nome_ultimo_est =  \"Concluido\" GROUP BY nome_cliente having nome_cliente LIKE";
//select * from vw_pedido group by nome_cliente having nome_cliente LIKE " ;
//      sql_listaProdutos = "select * from vw_pedido group by nome_cliente having nome_cliente LIKE " ;
            sql_listaPedidos =     sql_listaPedidos + "'%" +
            encontrar + "%'" + " or dthr_entregar LIKE " +
            "'%" + encontrar + "%'" + " ORDER BY nome_cliente ASC";
            
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try(Connection banco = DriverManager.getConnection(BD.CONNECTION_STRING, BD.USER, BD.USER_PWD)) {

            PreparedStatement stm_listaJTable = banco.prepareStatement(sql_listaPedidos);
            ResultSet resultado_listaJTable = stm_listaJTable.executeQuery();
            Pedido[] pedidosHistorico = BD.Pedido.parseView_pedido(resultado_listaJTable);
            if (pedidosHistorico != null) {
                jtbl_pedidos.getModel().fillRows(pedidosHistorico);
            }
            stm_listaJTable.close();
            banco.close();
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch (SQLException ex) {
                //            e.printStackTrace();
                Logger.getLogger(JFrame_GerenciamentoPedidos.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this,
                    "Erro ao acessar banco.\n"+
                    "Erro: "+ ex,
                    "JaPedidos",
                    JOptionPane.INFORMATION_MESSAGE);
            }
    }//GEN-LAST:event_jlbl_encontrarMouseClicked

    private void jlbl_encontrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_encontrarMousePressed
        // TODO add your handling code here:
        jlbl_encontrar.setIcon(new javax.swing.ImageIcon(".\\src\\main\\java\\japedidos\\imagens\\icon_encontrar_pressionado.png"));
    }//GEN-LAST:event_jlbl_encontrarMousePressed

    private void jlbl_encontrarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_encontrarMouseReleased
        // TODO add your handling code here:
        jlbl_encontrar.setIcon(new javax.swing.ImageIcon(".\\src\\main\\java\\japedidos\\imagens\\icon_encontrar_padrao.png"));
    }//GEN-LAST:event_jlbl_encontrarMouseReleased

    private void jlbl_relatoriosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_relatoriosMouseClicked
        this.setVisible(false);
        japedidos.relatorios.JFrame_Relatorios frame = new japedidos.relatorios.JFrame_Relatorios();
        int x = this.getX() + this.getWidth() / 2 - frame.getWidth() / 2;
        int y = this.getY() + this.getHeight()/ 2 - frame.getHeight() / 2;
        frame.setLocation(x, y);
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jlbl_relatoriosMouseClicked

    private void datePicker1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datePicker1MouseExited
        // TODO add your handling code here:
        this.datePicker1.getComponentToggleCalendarButton().repaint();
    }//GEN-LAST:event_datePicker1MouseExited

    private void setFieldsInfo(Pedido p) {
        clearFieldsInfo();
        hideErrorLabels();
        if (p == null) {
            throw new NullPointerException("Pedido para alteração não pode ser nulo.");
        }

        // Cliente
        Cliente cliente = p.getCliente();
        jtxtf_telefoneCliente.setText(cliente.getTelefone());
        jtxtf_nomeCliente.setText(cliente.getNome());
        
        // Entrega
        InfoEntrega infoEntrega = p.getInfoEntrega();
        jcmb_tipoEntrega.setSelectedItem(infoEntrega.getTipoEntrega());
        datePicker1.setDate(infoEntrega.getDataHoraEntregar().toLocalDate());
        timePicker1.setTime(infoEntrega.getDataHoraEntregar().toLocalTime());
        
        if (infoEntrega.getTipoEntrega() == TipoEntrega.ENVIO) {
            Destino destino = infoEntrega.getDestino();
            jtxtf_rua.setText(destino.getLogradouro());
            jtxtf_numero.setText(destino.getNumero());
            jtxtf_bairro.setText(destino.getBairro());
            jtxtf_cidade.setText(destino.getBairro());
            jcmb_uf.setSelectedItem(destino.getEstado());
        }
        
        jtxta_observacoes.setText(infoEntrega.getDestinatario());
        
        jTable_ProdutoPedido.getModel().fillRows(p.getProdutos());
        
//        jspn_desconto.setValue((int)(p.getTaxaDesconto() * 100.0));
        jspn_valorEntrega.setValue(infoEntrega.getPrecoFrete());
        atualizarValoresPedido();
//        jcmb_estadoInicial.setSelectedItem(p.getEstadoAtualPedido().ESTADO);
        this.infoAdicionalCliente = cliente.getInfoAdicional();
    }
    
    private void autoPreencherPedido() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String conteudoTelefone = jtxtf_telefoneCliente.getText().trim();
        StringBuilder newStr = new StringBuilder();

        for (int i=0; i < conteudoTelefone.length(); i++) {
            char c = conteudoTelefone.charAt(i);

            if(Character.isDigit(c)) {
                newStr.append(c);
            }
        }
        conteudoTelefone = newStr.toString();
        if (! conteudoTelefone.isEmpty()) {
            if ( ! telefoneDigitado || telefoneDigitado && clienteEncontrado!= null && !conteudoTelefone.equals(clienteEncontrado.getTelefone()) ) {
                if (conteudoTelefone.length() > 7) {
                    telefoneDigitado= true;
                    Cliente encontrado = BD.Cliente.selectByTelefone(conteudoTelefone);
                    if (encontrado != null) {
                        clienteEncontrado = encontrado;
                        jtxtf_telefoneCliente.setText(encontrado.getTelefone());
                        jtxtf_nomeCliente.setEnabled(false);
                        jtxtf_nomeCliente.setText(encontrado.getNome());
                        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        int preencher = JOptionPane.showConfirmDialog(this, "Um cliente foi encontrado com este número.\nDeseja carregar o último pedido?", "Auto-preenchimento de pedido", JOptionPane.YES_NO_OPTION);
                        if (preencher == JOptionPane.YES_OPTION) {
                            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            Pedido ultimo = BD.Pedido.selectLastByCliente(encontrado);
                            this.setFieldsInfo(ultimo);
                            this.datePicker1.setDateToToday();
                            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        }
                    } else if (clienteEncontrado != null) {
                        clienteEncontrado = null;
                        jtxtf_nomeCliente.setEnabled(true);
                        jtxtf_nomeCliente.setText(null);
                        telefoneDigitado = false;
                        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        int limpar = JOptionPane.showConfirmDialog(this, "Deseja limpar os campos do pedido?", "Descartar pedido", JOptionPane.YES_NO_OPTION);
                        if (limpar == JOptionPane.YES_OPTION) {
                            this.clearFieldsInfo();
                        }
                    } else {
                        telefoneDigitado = false;
                    }
                }  else {
                    telefoneDigitado = false;
                }
            }
        } else if (telefoneDigitado) {
            telefoneDigitado = false;
            clienteEncontrado = null;
            jtxtf_nomeCliente.setEnabled(true);
            jtxtf_nomeCliente.setText(null);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            int limpar = JOptionPane.showConfirmDialog(this, "Deseja limpar os campos do pedido?", "Descartar pedido", JOptionPane.YES_NO_OPTION);
            if (limpar == JOptionPane.YES_OPTION) {
                this.clearFieldsInfo();
            }
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    
    private void jtxtf_telefoneClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtf_telefoneClienteFocusLost
        // TODO add your handling code here:
        autoPreencherPedido();
    }//GEN-LAST:event_jtxtf_telefoneClienteFocusLost

    private void jtxtf_telefoneClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtf_telefoneClienteActionPerformed
        // TODO add your handling code here:
        autoPreencherPedido();
    }//GEN-LAST:event_jtxtf_telefoneClienteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrame_GerenciamentoPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_GerenciamentoPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_GerenciamentoPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_GerenciamentoPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame_GerenciamentoPedidos frame = new JFrame_GerenciamentoPedidos();
                frame.preencherPedidosPorEstado();
                frame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.github.lgooddatepicker.components.DatePicker datePicker1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private japedidos.pedidos.JTable_Pedido_Resumido jTable_Pedido_Resumido1;
    private japedidos.produto.JTable_ProdutoPedido jTable_ProdutoPedido;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton jbtn_visualizarPedido;
    private javax.swing.JComboBox<japedidos.pedidos.Estado> jcmb_estadoInicial;
    private javax.swing.JComboBox<japedidos.pedidos.Estado> jcmb_filtro_pedidos_aberto;
    private javax.swing.JComboBox<japedidos.produto.Produto> jcmb_produto;
    private javax.swing.JComboBox<japedidos.pedidos.TipoEntrega> jcmb_tipoEntrega;
    private javax.swing.JComboBox<String> jcmb_uf;
    private javax.swing.JLabel jlbl_bairro;
    javax.swing.JLabel jlbl_btn_criarPedido;
    javax.swing.JLabel jlbl_btn_excluir;
    javax.swing.JLabel jlbl_btn_incluir;
    javax.swing.JLabel jlbl_btn_inforAdicional;
    private javax.swing.JLabel jlbl_cidade;
    private javax.swing.JLabel jlbl_clientes;
    private javax.swing.JLabel jlbl_dataEntrega;
    private javax.swing.JLabel jlbl_desconto;
    javax.swing.JLabel jlbl_encontrar;
    private javax.swing.JLabel jlbl_erro_bairroEntrega;
    private javax.swing.JLabel jlbl_erro_cidadeEntrega;
    private javax.swing.JLabel jlbl_erro_dataEntrega;
    private javax.swing.JLabel jlbl_erro_desconto;
    private javax.swing.JLabel jlbl_erro_estadoInicial;
    private javax.swing.JLabel jlbl_erro_horaEntrega;
    private javax.swing.JLabel jlbl_erro_nome;
    private javax.swing.JLabel jlbl_erro_numeroEntrega;
    private javax.swing.JLabel jlbl_erro_observacoesEntrega;
    private javax.swing.JLabel jlbl_erro_produto;
    private javax.swing.JLabel jlbl_erro_quantidadeProduto;
    private javax.swing.JLabel jlbl_erro_ruaEntrega;
    private javax.swing.JLabel jlbl_erro_telefone;
    private javax.swing.JLabel jlbl_erro_tipoEntrega;
    private javax.swing.JLabel jlbl_erro_ufEntrega;
    private javax.swing.JLabel jlbl_erro_valorEntrega;
    private javax.swing.JLabel jlbl_erro_valorTotal;
    private javax.swing.JLabel jlbl_estadoInicial;
    private javax.swing.JLabel jlbl_filtroPedidosEmAberto;
    private javax.swing.JLabel jlbl_horaEntrega;
    private javax.swing.JLabel jlbl_nomeCliente;
    private javax.swing.JLabel jlbl_numero;
    private javax.swing.JLabel jlbl_observações;
    private javax.swing.JLabel jlbl_pedidos;
    private javax.swing.JLabel jlbl_produto;
    private javax.swing.JLabel jlbl_produtos;
    private javax.swing.JLabel jlbl_quantidadeProduto;
    private javax.swing.JLabel jlbl_relatorios;
    private javax.swing.JLabel jlbl_rua;
    private javax.swing.JLabel jlbl_telefoneCliente;
    private javax.swing.JLabel jlbl_tipoEntrega;
    private javax.swing.JLabel jlbl_uf;
    private javax.swing.JLabel jlbl_valorEntrega;
    private javax.swing.JLabel jlbl_valorTotal;
    private javax.swing.JLabel jpnl_background1;
    private javax.swing.JLabel jpnl_btn_novo;
    private javax.swing.JPanel jpnl_historicoPedidos;
    private javax.swing.JLabel jpnl_img_etiqueta;
    private javax.swing.JPanel jpnl_incluirPedido;
    private javax.swing.JPanel jpnl_pedidosAberto;
    private javax.swing.JPanel jpnl_principal;
    private javax.swing.JPanel jpnl_sideMenu;
    private javax.swing.JScrollPane jscp_destinatario;
    private javax.swing.JSpinner jspn_desconto;
    private javax.swing.JSpinner jspn_quantidade;
    private javax.swing.JSpinner jspn_valorEntrega;
    javax.swing.JToolBar jtb_linhaEncontrar;
    private japedidos.pedidos.JTable_Pedido_Resumido jtbl_pedidos;
    private javax.swing.JTextArea jtxta_observacoes;
    private javax.swing.JTextField jtxtf_bairro;
    private javax.swing.JTextField jtxtf_cidade;
    private javax.swing.JTextField jtxtf_nomeCliente;
    private javax.swing.JTextField jtxtf_numero;
    javax.swing.JTextField jtxtf_pesquisarHistoricoPedido;
    private javax.swing.JTextField jtxtf_pesquisarPedidos;
    private javax.swing.JTextField jtxtf_rua;
    private javax.swing.JTextField jtxtf_telefoneCliente;
    private javax.swing.JTextField jtxtf_valorTotal;
    private com.github.lgooddatepicker.components.TimePicker timePicker1;
    // End of variables declaration//GEN-END:variables
}
