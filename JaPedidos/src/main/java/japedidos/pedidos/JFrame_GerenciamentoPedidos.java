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
import japedidos.usuario.Usuario;
import javax.swing.JFrame;

/**
 *
 * @author t.baiense
 */
public class JFrame_GerenciamentoPedidos extends javax.swing.JFrame implements InfoAdicionalReceiver {
    Cliente.InfoAdicional infoAdicionalCliente;

    /**
     * Creates new form CadastroPedido
     */
    public JFrame_GerenciamentoPedidos() {
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
                            jbtn_incluirProduto.doClick();
                        }
                    });
                }
            }
        });
        javax.swing.SwingUtilities.invokeLater(() -> {
            preencherHistoricoPedidos();
        });
    }
    public void fillEstadosComboBoxPedido() {
        jcmb_estadoInicial.addItem(Estado.ABERTO);
        jcmb_estadoInicial.addItem(Estado.AGUARDANDO_PAGAMENTO);
        jcmb_estadoInicial.addItem(Estado.PAGO);
        jcmb_estadoInicial.addItem(Estado.EM_PREPARO);
        jcmb_estadoInicial.addItem(Estado.AGUARDANDO_ENVIO);
        jcmb_estadoInicial.addItem(Estado.AGUARDANDO_RETIRADA);
        jcmb_estadoInicial.addItem(Estado.SAIU_PARA_ENTREGA);
        jcmb_estadoInicial.addItem(Estado.CONCLUIDO);
        jcmb_estadoInicial.addItem(Estado.CANCELADO);
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
        telefone = jtxtf_telefoneCliente.getText();
        
        try {
            cliente = new Cliente(nome, telefone);
            cliente.setInfoAdicional(infoAdicionalCliente);
//            cliente.setId(1); // RETIRAR quando tiver método de criar usuario
        } catch (IllegalArgumentsException newExs) {
            exs.addCause(newExs.getCauses());
            cliente = null;
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
                System.out.println(t.getMessage());
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
        jlbl_img_intercorrencia = new javax.swing.JLabel();
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
        jTable_Pedido_Resumido1 = new japedidos.pedidos.JTable_Pedido_Resumido();
        jButton3 = new javax.swing.JButton();
        jpnl_incluirPedido = new javax.swing.JPanel();
        jTable_ProdutoPedido = new japedidos.produto.JTable_ProdutoPedido();
        jscp_destinatario = new javax.swing.JScrollPane();
        jtxta_observacoes = new javax.swing.JTextArea();
        jcmb_estadoInicial = new javax.swing.JComboBox<>();
        jlbl_horaEntrega = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jbtn_incluirProduto = new javax.swing.JButton();
        jtxtf_valorTotal = new javax.swing.JTextField();
        jpnl_btn_novo = new javax.swing.JLabel();
        jcmb_tipoEntrega = new javax.swing.JComboBox<>();
        jtxtf_nomeCliente = new javax.swing.JTextField();
        jlbl_pct = new javax.swing.JLabel();
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
        timeSettings.initialTime = LocalTime.of(java.time.LocalTime.now().getHour(), 00);
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
        jlbl_produtoAdicionados = new javax.swing.JLabel();
        jlbl_infoAdicionalCliente = new javax.swing.JLabel();
        jlbl_dataEntrega = new javax.swing.JLabel();
        jspn_quantidade = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(0, 0, 10000, 1));
        jlbl_observações = new javax.swing.JLabel();
        jlbl_nomeCliente = new javax.swing.JLabel();
        jtxtf_numero = new javax.swing.JTextField();
        jlbl_numero = new javax.swing.JLabel();
        jbtn_excluirProduto = new javax.swing.JButton();
        jlbl_produto = new javax.swing.JLabel();
        jlbl_valorEntrega = new javax.swing.JLabel();
        jlbl_reais2 = new javax.swing.JLabel();
        jlbl_reais = new javax.swing.JLabel();
        jbtn_criarPedido1 = new javax.swing.JButton();
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
        jLabel1 = new javax.swing.JLabel();
        jpnl_historicoPedidos = new javax.swing.JPanel();
        jtxtf_pesquisarHistoricoPedido = new javax.swing.JTextField();
        jlbl_filtroHistoricoPedido = new javax.swing.JLabel();
        jscp_pedidosEmAberto1 = new javax.swing.JScrollPane();
        jtbl_HistoricoPedido = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jbtn_visualizarPedido = new javax.swing.JButton();
        jpnl_sideMenu = new javax.swing.JPanel();
        jlbl_clientes = new javax.swing.JLabel();
        jlbl_produtos = new javax.swing.JLabel();
        jlbl_pedidos = new javax.swing.JLabel();
        jlbl_relatorios = new javax.swing.JLabel();
        jpnl_img_etiqueta = new javax.swing.JLabel();
        jpnl_background1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1024, 576));
        setMinimumSize(new java.awt.Dimension(1024, 576));
        setResizable(false);

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

        jtxtf_pesquisarPedidos.setText("PESQUISAR..");
        jtxtf_pesquisarPedidos.setPreferredSize(new java.awt.Dimension(96, 22));
        jtxtf_pesquisarPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtf_pesquisarPedidosActionPerformed(evt);
            }
        });
        jpnl_pedidosAberto.add(jtxtf_pesquisarPedidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 21, 360, 40));

        jlbl_filtroPedidosEmAberto.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlbl_filtroPedidosEmAberto.setText("FILTRO:");
        jpnl_pedidosAberto.add(jlbl_filtroPedidosEmAberto, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, -1));

        jlbl_img_intercorrencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Intercorrencia.png"))); // NOI18N
        jpnl_pedidosAberto.add(jlbl_img_intercorrencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 480, 30, 30));

        jButton1.setText("Alterar pedido");
        jButton1.setFont(new java.awt.Font("Noto Sans", 0, 13)); // NOI18N
        jpnl_pedidosAberto.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 480, 140, 30));

        jButton2.setText("Relatar intercorrência");
        jpnl_pedidosAberto.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 480, -1, 30));

        jpnl_pedidosAberto.add(jcmb_filtro_pedidos_aberto, new org.netbeans.lib.awtextra.AbsoluteConstraints(536, 20, 180, 40));
        jpnl_pedidosAberto.add(jTable_Pedido_Resumido1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 720, 410));

        jButton3.setText("Alterar estado");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jpnl_pedidosAberto.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 480, 120, -1));

        jTabbedPane1.addTab("Pedidos em aberto", jpnl_pedidosAberto);

        jpnl_incluirPedido.setOpaque(false);
        jpnl_incluirPedido.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jpnl_incluirPedidoComponentShown(evt);
            }
        });
        jpnl_incluirPedido.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpnl_incluirPedido.add(jTable_ProdutoPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 730, 110));

        jtxta_observacoes.setColumns(20);
        jtxta_observacoes.setRows(5);
        jtxta_observacoes.setText("Dados destinatário, ponto de referência...");
        jscp_destinatario.setViewportView(jtxta_observacoes);

        jpnl_incluirPedido.add(jscp_destinatario, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 100, 320, 100));

        jpnl_incluirPedido.add(jcmb_estadoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 450, 190, -1));

        jlbl_horaEntrega.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_horaEntrega.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_horaEntrega.setText("HORA:");
        jpnl_incluirPedido.add(jlbl_horaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, -1, 20));

        jSeparator1.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jpnl_incluirPedido.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 730, 10));

        jbtn_incluirProduto.setText("Incluir");
        jbtn_incluirProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_incluirProdutoActionPerformed(evt);
            }
        });
        jpnl_incluirPedido.add(jbtn_incluirProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 250, 100, -1));

        jtxtf_valorTotal.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jtxtf_valorTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jpnl_incluirPedido.add(jtxtf_valorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 450, 100, -1));

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

        jpnl_incluirPedido.add(jcmb_tipoEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 90, -1));
        jpnl_incluirPedido.add(jtxtf_nomeCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 250, -1));

        jlbl_pct.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_pct.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_pct.setText("%");
        jpnl_incluirPedido.add(jlbl_pct, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 20, -1));

        jlbl_bairro.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_bairro.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_bairro.setText("BAIRRO:");
        jpnl_incluirPedido.add(jlbl_bairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jlbl_rua.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_rua.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_rua.setText("RUA:");
        jpnl_incluirPedido.add(jlbl_rua, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));
        jpnl_incluirPedido.add(jtxtf_cidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 120, -1));

        jcmb_produto.setEnabled(false);
        jcmb_produto.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jcmb_produtoComponentShown(evt);
            }
        });
        jpnl_incluirPedido.add(jcmb_produto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 260, -1));

        jlbl_cidade.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_cidade.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_cidade.setText("CIDADE:");
        jpnl_incluirPedido.add(jlbl_cidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, -1, -1));

        jlbl_estadoInicial.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_estadoInicial.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_estadoInicial.setText("ESTADO INICIAL:");
        jpnl_incluirPedido.add(jlbl_estadoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 430, 150, 20));

        jlbl_tipoEntrega.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_tipoEntrega.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_tipoEntrega.setText("ENTREGA:");
        jpnl_incluirPedido.add(jlbl_tipoEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, -1, 20));

        datePicker1.getComponentDateTextField().setPreferredSize(new java.awt.Dimension(80, 20));
        datePicker1.setDateToToday();

        javax.swing.JButton datePickerButton = datePicker1.getComponentToggleCalendarButton();
        datePickerButton.setPreferredSize(new java.awt.Dimension(22, 22));
        datePickerButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        datePickerButton.setBorder(null);
        datePickerButton.setText("");

        javax.swing.ImageIcon dateExampleIcon = new javax.swing.ImageIcon(getClass().getResource("/datepickerbutton1.png"));
        java.awt.Dimension newDateButtonSize = new java.awt.Dimension(dateExampleIcon.getIconWidth() + 4, dateExampleIcon.getIconHeight() + 4);
        datePickerButton.setIcon(dateExampleIcon);
        datePickerButton.setPreferredSize(newDateButtonSize);
        datePickerButton.setOpaque(false);
        datePickerButton.setContentAreaFilled(false);
        datePickerButton.setBorderPainted(false);
        datePickerButton.setFocusPainted(false);
        jpnl_incluirPedido.add(datePicker1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, 110, 30));

        timePicker1.getComponentTimeTextField().setPreferredSize(new java.awt.Dimension(40, 20));
        javax.swing.JButton timePickerButton = timePicker1.getComponentToggleTimeMenuButton();
        timePickerButton.setText("");
        javax.swing.ImageIcon timeExampleIcon = new javax.swing.ImageIcon(this.getClass().getResource("/timepickerbutton1.png"));
        timePickerButton.setIcon(timeExampleIcon);
        // Adjust the button size to fit the new icon.
        java.awt.Dimension newTimeButtonSize =
        new java.awt.Dimension(timeExampleIcon.getIconWidth() + 4, timeExampleIcon.getIconHeight() + 4);
        timePickerButton.setPreferredSize(newTimeButtonSize);
        jpnl_incluirPedido.add(timePicker1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, 90, -1));
        jpnl_incluirPedido.add(jtxtf_rua, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 300, -1));

        jlbl_uf.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_uf.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_uf.setText("UF:");
        jpnl_incluirPedido.add(jlbl_uf, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, -1, -1));
        jpnl_incluirPedido.add(jtxtf_bairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 170, -1));

        jlbl_desconto.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_desconto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_desconto.setText("DESC.:");
        jpnl_incluirPedido.add(jlbl_desconto, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 430, 60, -1));
        jpnl_incluirPedido.add(jtxtf_telefoneCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 130, -1));

        jlbl_telefoneCliente.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_telefoneCliente.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_telefoneCliente.setText("TELEFONE:");
        jpnl_incluirPedido.add(jlbl_telefoneCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jlbl_quantidadeProduto.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_quantidadeProduto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_quantidadeProduto.setText("QUANTIDADE:");
        jpnl_incluirPedido.add(jlbl_quantidadeProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 130, 30));

        jlbl_valorTotal.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_valorTotal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_valorTotal.setText("TOTAL:");
        jpnl_incluirPedido.add(jlbl_valorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 430, 70, -1));

        jlbl_produtoAdicionados.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_produtoAdicionados.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_produtoAdicionados.setText("PRODUTOS ADICIONADOS:");
        jpnl_incluirPedido.add(jlbl_produtoAdicionados, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, -1, 20));

        jlbl_infoAdicionalCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_info_adicional.png"))); // NOI18N
        jlbl_infoAdicionalCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_infoAdicionalClienteMouseClicked(evt);
            }
        });
        jpnl_incluirPedido.add(jlbl_infoAdicionalCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 430, -1, -1));

        jlbl_dataEntrega.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_dataEntrega.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_dataEntrega.setText("DATA:");
        jpnl_incluirPedido.add(jlbl_dataEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 60, 20));

        jspn_quantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jspn_quantidadeKeyPressed(evt);
            }
        });
        jpnl_incluirPedido.add(jspn_quantidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 250, 90, -1));

        jlbl_observações.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_observações.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_observações.setText("OBSERVAÇÕES:");
        jpnl_incluirPedido.add(jlbl_observações, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, 150, 20));

        jlbl_nomeCliente.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_nomeCliente.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_nomeCliente.setText("NOME:");
        jpnl_incluirPedido.add(jlbl_nomeCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 70, -1));
        jpnl_incluirPedido.add(jtxtf_numero, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 80, -1));

        jlbl_numero.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_numero.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_numero.setText("Nº:");
        jpnl_incluirPedido.add(jlbl_numero, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 60, -1));

        jbtn_excluirProduto.setText("Excluir");
        jbtn_excluirProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_excluirProdutoActionPerformed(evt);
            }
        });
        jpnl_incluirPedido.add(jbtn_excluirProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 250, 100, -1));

        jlbl_produto.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_produto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_produto.setText("ADICIONAR PRODUTOS:");
        jpnl_incluirPedido.add(jlbl_produto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, 20));

        jlbl_valorEntrega.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_valorEntrega.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_valorEntrega.setText("ENTREGA:");
        jpnl_incluirPedido.add(jlbl_valorEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 90, -1));

        jlbl_reais2.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_reais2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_reais2.setText("R$");
        jpnl_incluirPedido.add(jlbl_reais2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 450, -1, -1));

        jlbl_reais.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_reais.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_reais.setText("R$");
        jpnl_incluirPedido.add(jlbl_reais, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 30, -1));

        jbtn_criarPedido1.setText("Criar pedido");
        jbtn_criarPedido1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_criarPedido1ActionPerformed(evt);
            }
        });
        jpnl_incluirPedido.add(jbtn_criarPedido1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 500, 180, -1));

        jlbl_erro_estadoInicial.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_estadoInicial.setForeground(new java.awt.Color(255, 0, 0));
        jlbl_erro_estadoInicial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_estadoInicial.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_estadoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 468, 190, 30));

        jlbl_erro_bairroEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_bairroEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jlbl_erro_bairroEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_bairroEntrega.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_bairroEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 196, 170, 20));

        jlbl_erro_desconto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_desconto.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_erro_desconto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_desconto.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_desconto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 470, 110, 30));

        jlbl_erro_horaEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_horaEntrega.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_erro_horaEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_horaEntrega.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_horaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, 80, -1));

        jlbl_erro_observacoesEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_observacoesEntrega.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_erro_observacoesEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_observacoesEntrega.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_observacoesEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, 300, -1));

        jlbl_erro_telefone.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_telefone.setForeground(new java.awt.Color(255, 0, 0));
        jlbl_erro_telefone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_telefone.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_telefone, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 56, 130, 20));

        jlbl_erro_ruaEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_ruaEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jlbl_erro_ruaEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_ruaEntrega.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_ruaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 126, 280, -1));

        jlbl_erro_numeroEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_numeroEntrega.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_erro_numeroEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_numeroEntrega.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_numeroEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 126, 80, -1));

        jlbl_erro_cidadeEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_cidadeEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jlbl_erro_cidadeEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_cidadeEntrega.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_cidadeEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 196, 120, 20));

        jlbl_erro_ufEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_ufEntrega.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_erro_ufEntrega.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_ufEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 196, 90, 20));

        jlbl_erro_nome.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_nome.setForeground(new java.awt.Color(255, 0, 0));
        jlbl_erro_nome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_nome.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_nome, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 54, 230, 20));

        jlbl_erro_tipoEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_tipoEntrega.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_erro_tipoEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_tipoEntrega.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_tipoEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 56, 80, -1));

        jlbl_erro_dataEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_dataEntrega.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_erro_dataEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_dataEntrega.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_dataEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 80, 20));

        jlbl_erro_produto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_produto.setText("Info inválida!");
        jlbl_erro_produto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_produto.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_produto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 276, 240, 20));

        jlbl_erro_quantidadeProduto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_quantidadeProduto.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_erro_quantidadeProduto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_quantidadeProduto.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_quantidadeProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 190, -1));

        jlbl_erro_valorEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_valorEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jlbl_erro_valorEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_valorEntrega.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_valorEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 120, 30));

        jlbl_erro_valorTotal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_valorTotal.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_erro_valorTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_valorTotal.setText("Info inválida!");
        jpnl_incluirPedido.add(jlbl_erro_valorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 470, 150, 30));

        jspn_desconto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jspn_descontoFocusLost(evt);
            }
        });
        jpnl_incluirPedido.add(jspn_desconto, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 450, 70, -1));

        jspn_valorEntrega.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jspn_valorEntregaFocusLost(evt);
            }
        });
        jpnl_incluirPedido.add(jspn_valorEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, 90, -1));

        jcmb_uf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        jcmb_uf.setSelectedIndex(7);
        jpnl_incluirPedido.add(jcmb_uf, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, 80, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datepickerbutton1.png"))); // NOI18N
        jpnl_incluirPedido.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, -1, 30));

        jTabbedPane1.addTab("Incluir pedido", jpnl_incluirPedido);

        jpnl_historicoPedidos.setOpaque(false);
        jpnl_historicoPedidos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtxtf_pesquisarHistoricoPedido.setText("PESQUISAR..");
        jtxtf_pesquisarHistoricoPedido.setPreferredSize(new java.awt.Dimension(96, 22));
        jtxtf_pesquisarHistoricoPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtf_pesquisarHistoricoPedidoActionPerformed(evt);
            }
        });
        jpnl_historicoPedidos.add(jtxtf_pesquisarHistoricoPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 21, 390, 40));

        jlbl_filtroHistoricoPedido.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlbl_filtroHistoricoPedido.setText("FILTRO:");
        jpnl_historicoPedidos.add(jlbl_filtroHistoricoPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, -1));

        jtbl_HistoricoPedido.setBackground(new java.awt.Color(153, 204, 255));
        jtbl_HistoricoPedido.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtbl_HistoricoPedido.setForeground(new java.awt.Color(255, 255, 255));
        jtbl_HistoricoPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Integer(1), "NOME",  new Integer(27999999), "RUA TAL Nº X", "10/10/2024",  new Double(1.0)},
                { new Integer(2), "NOME",  new Integer(27999999), "RUA TAL Nº X", "10/10/2024",  new Double(1.0)},
                { new Integer(3), "NOME",  new Integer(27999999), "RUA TAL Nº X", "10/10/2024",  new Double(1.0)},
                { new Integer(4), "NOME",  new Integer(27999999), "RUA TAL Nº X", "10/10/2024",  new Double(1.0)},
                { new Integer(5), "NOME",  new Integer(27999999), "RUA TAL Nº X", "10/10/2024",  new Double(1.0)}
            },
            new String [] {
                "CODIGO", "NOME", "TELEFONE", "ENDEREÇO", "DATA/HORA ENTREGA", "VALOR"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jtbl_HistoricoPedido.setMinimumSize(new java.awt.Dimension(20, 160));
        jtbl_HistoricoPedido.setPreferredSize(new java.awt.Dimension(655, 204));
        jscp_pedidosEmAberto1.setViewportView(jtbl_HistoricoPedido);

        jpnl_historicoPedidos.add(jscp_pedidosEmAberto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 730, 390));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Produtos", "Cliente", "Estado", "Data" }));
        jpnl_historicoPedidos.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, 180, 40));

        jbtn_visualizarPedido.setFont(new java.awt.Font("Noto Sans", 0, 13)); // NOI18N
        jbtn_visualizarPedido.setText("Visualizar Pedido");
        jpnl_historicoPedidos.add(jbtn_visualizarPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 490, 150, 30));

        jTabbedPane1.addTab("Histórico de pedidos", jpnl_historicoPedidos);

        jpnl_principal.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 750, 580));

        jpnl_sideMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpnl_sideMenu.setMaximumSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.setMinimumSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.setPreferredSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.setOpaque(false);
        jpnl_sideMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlbl_clientes.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_clientes.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_clientes.setText("CLIENTES");
        jpnl_sideMenu.add(jlbl_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        jlbl_produtos.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_produtos.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_produtos.setForeground(new java.awt.Color(204, 204, 204));
        jlbl_produtos.setText("PRODUTOS");
        jlbl_produtos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jlbl_produtosFocusGained(evt);
            }
        });
        jpnl_sideMenu.add(jlbl_produtos, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        jlbl_pedidos.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_pedidos.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_pedidos.setText("PEDIDOS");
        jpnl_sideMenu.add(jlbl_pedidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, -1, -1));

        jlbl_relatorios.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_relatorios.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_relatorios.setText("RELATÓRIOS");
        jpnl_sideMenu.add(jlbl_relatorios, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, -1, -1));

        jpnl_img_etiqueta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/painel_comandos_esquerda_05x.png"))); // NOI18N
        jpnl_img_etiqueta.setText("jLabel2");
        jpnl_img_etiqueta.setMaximumSize(new java.awt.Dimension(250, 576));
        jpnl_img_etiqueta.setMinimumSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.add(jpnl_img_etiqueta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 250, -1));

        jpnl_principal.add(jpnl_sideMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 576));

        jpnl_background1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jpnl_background1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/background_pedidos.png"))); // NOI18N
        jpnl_background1.setToolTipText("");
        jpnl_background1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jpnl_background1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
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

    private void jtxtf_pesquisarHistoricoPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtf_pesquisarHistoricoPedidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtf_pesquisarHistoricoPedidoActionPerformed

    private void jtxtf_pesquisarPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtf_pesquisarPedidosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtf_pesquisarPedidosActionPerformed

    private void jlbl_infoAdicionalClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_infoAdicionalClienteMouseClicked
        JFrame_InfoAdicionalCliente frame;
        if (infoAdicionalCliente == null) {
            frame = new JFrame_InfoAdicionalCliente(this);
        } else {
            frame = new JFrame_InfoAdicionalCliente(this, this.infoAdicionalCliente);
        }
        frame.setVisible(true);
    }//GEN-LAST:event_jlbl_infoAdicionalClienteMouseClicked

    private void jbtn_criarPedido1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_criarPedido1ActionPerformed
        Pedido p = getFieldsInfo();
        if (p != null) {
            int r = BD.Pedido.insert(p);
            preencherHistoricoPedidos();
            if (r > 0) {
                clearFieldsInfo();
                atualizarValoresPedido();
                JOptionPane.showMessageDialog(null, "Pedido cadastrado com sucesso!", "Cadastro de pedido", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Cadastro do pedido falhou!", "Cadastro de pedido", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }//GEN-LAST:event_jbtn_criarPedido1ActionPerformed

    private void jbtn_incluirProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_incluirProdutoActionPerformed
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
    }//GEN-LAST:event_jbtn_incluirProdutoActionPerformed

    private void jbtn_excluirProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_excluirProdutoActionPerformed
        int selectedRow = jTable_ProdutoPedido.getTable().getSelectedRow();
        if (selectedRow != -1) {
            jTable_ProdutoPedido.getModel().removeRow(selectedRow);
            clearProdutoFieldsInfo();
            hideProdutoErrorLabels();
        }
        atualizarValoresPedido();
    }//GEN-LAST:event_jbtn_excluirProdutoActionPerformed

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

    private void preencherHistoricoPedidos() {
        jTable_Pedido_Resumido1.preencher((japedidos.pedidos.Estado)jcmb_filtro_pedidos_aberto.getSelectedItem());
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
                frame.add(new JPanel_AtualizarEstado(pSelecionado, pSelecionado.getEstadoAtualPedido().ESTADO, Usuario.getAtual(), this::preencherHistoricoPedidos));
                frame.pack();
                int x = this.getX() + this.getWidth() / 2 - frame.getWidth() / 2;
                int y = this.getY() + this.getHeight()/ 2 - frame.getHeight() / 2;
                frame.setLocation(x, y);
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

    private void jpnl_incluirPedidoComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jpnl_incluirPedidoComponentShown
        // TODO add your handling code here:
        if (jcmb_produto.getItemCount() == 0) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                // Preencher lista de produtos
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
            });
        }
    }//GEN-LAST:event_jpnl_incluirPedidoComponentShown

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
                frame.preencherHistoricoPedidos();
                frame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.github.lgooddatepicker.components.DatePicker datePicker1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private japedidos.pedidos.JTable_Pedido_Resumido jTable_Pedido_Resumido1;
    private japedidos.produto.JTable_ProdutoPedido jTable_ProdutoPedido;
    private javax.swing.JButton jbtn_criarPedido1;
    private javax.swing.JButton jbtn_excluirProduto;
    private javax.swing.JButton jbtn_incluirProduto;
    private javax.swing.JButton jbtn_visualizarPedido;
    private javax.swing.JComboBox<japedidos.pedidos.Estado> jcmb_estadoInicial;
    private javax.swing.JComboBox<japedidos.pedidos.Estado> jcmb_filtro_pedidos_aberto;
    private javax.swing.JComboBox<japedidos.produto.Produto> jcmb_produto;
    private javax.swing.JComboBox<japedidos.pedidos.TipoEntrega> jcmb_tipoEntrega;
    private javax.swing.JComboBox<String> jcmb_uf;
    private javax.swing.JLabel jlbl_bairro;
    private javax.swing.JLabel jlbl_cidade;
    private javax.swing.JLabel jlbl_clientes;
    private javax.swing.JLabel jlbl_dataEntrega;
    private javax.swing.JLabel jlbl_desconto;
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
    private javax.swing.JLabel jlbl_filtroHistoricoPedido;
    private javax.swing.JLabel jlbl_filtroPedidosEmAberto;
    private javax.swing.JLabel jlbl_horaEntrega;
    private javax.swing.JLabel jlbl_img_intercorrencia;
    private javax.swing.JLabel jlbl_infoAdicionalCliente;
    private javax.swing.JLabel jlbl_nomeCliente;
    private javax.swing.JLabel jlbl_numero;
    private javax.swing.JLabel jlbl_observações;
    private javax.swing.JLabel jlbl_pct;
    private javax.swing.JLabel jlbl_pedidos;
    private javax.swing.JLabel jlbl_produto;
    private javax.swing.JLabel jlbl_produtoAdicionados;
    private javax.swing.JLabel jlbl_produtos;
    private javax.swing.JLabel jlbl_quantidadeProduto;
    private javax.swing.JLabel jlbl_reais;
    private javax.swing.JLabel jlbl_reais2;
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
    private javax.swing.JScrollPane jscp_pedidosEmAberto1;
    private javax.swing.JSpinner jspn_desconto;
    private javax.swing.JSpinner jspn_quantidade;
    private javax.swing.JSpinner jspn_valorEntrega;
    private javax.swing.JTable jtbl_HistoricoPedido;
    private javax.swing.JTextArea jtxta_observacoes;
    private javax.swing.JTextField jtxtf_bairro;
    private javax.swing.JTextField jtxtf_cidade;
    private javax.swing.JTextField jtxtf_nomeCliente;
    private javax.swing.JTextField jtxtf_numero;
    private javax.swing.JTextField jtxtf_pesquisarHistoricoPedido;
    private javax.swing.JTextField jtxtf_pesquisarPedidos;
    private javax.swing.JTextField jtxtf_rua;
    private javax.swing.JTextField jtxtf_telefoneCliente;
    private javax.swing.JTextField jtxtf_valorTotal;
    private com.github.lgooddatepicker.components.TimePicker timePicker1;
    // End of variables declaration//GEN-END:variables
}
