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
import japedidos.AccessController;
import japedidos.produto.ProdutoPedidoTableModel;
import japedidos.usuario.Usuario;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.TableModelEvent;

/**
 *
 * @author thiago
 */
public final class JPanel_AlterarPedido extends javax.swing.JPanel implements InfoAdicionalReceiver {

    Cliente.InfoAdicional infoAdicionalCliente;
    Pedido pedidoAlterar;
    Runnable runOnFinish;
    ProdutoPedido[] produtosNovos;
    
    public JPanel_AlterarPedido() {
        initComponents();
        fillEstadosComboBoxPedido();
        fillTipoEntregaComboBoxPedido();
        // Faz com que o produto selecionado seja adicionado ao pressionar ENTER no JSpinner de quantidade
        ((JSpinner.NumberEditor)jspn_quantidade.getEditor()).getTextField().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent ev) {
                if (ev.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    javax.swing.SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
//                            jbtn_incluirProduto.doClick();
                        }
                    });
                }
            }
        });
        
        jTable_ProdutoPedido.getModel().addTableModelListener((e) -> {
            ProdutoPedidoTableModel model = (ProdutoPedidoTableModel)e.getSource();
            if (e.getColumn() == ProdutoPedidoTableModel.COL_QUANTIDADE && e.getType() == TableModelEvent.UPDATE) {
                atualizarValoresPedido();
            }
        });
        inicializarPainel();
    }

    public void setOnFinishAction(Runnable run) {
        this.runOnFinish = run;
    }
    
    public void inicializarPainel() {
        popularProdutosDisponiveis();
        jspn_quantidade.setValue(1);
        
        jspn_valorEntrega.addChangeListener((e) -> {
            atualizarValoresPedido();
        });
        
        jspn_desconto.addChangeListener((e) -> {
            atualizarValoresPedido();
        });
    }
    
    private void fillEstadosComboBoxPedido() {
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
    
    private void fillTipoEntregaComboBoxPedido() {
        jcmb_tipoEntrega.addItem(TipoEntrega.ENVIO);
        jcmb_tipoEntrega.addItem(TipoEntrega.RETIRADA);
    }
    
    private void clearDestinoFieldsInfo() {
        jtxtf_rua.setText(null);
        jtxtf_numero.setText(null);
        jtxtf_bairro.setText(null);
        jtxtf_cidade.setText(null);
        jcmb_uf.setSelectedIndex(7);
    }
    
    public void setEnabledDestinoFields(boolean valor) {
        jtxtf_rua.setEnabled(valor);
        jtxtf_numero.setEnabled(valor);
        jtxtf_bairro.setEnabled(valor);
        jtxtf_cidade.setEnabled(valor);
        jcmb_uf.setEnabled(valor);
    }
    
    private void clearFieldsInfo() {
        jtxtf_telefoneCliente.setText(null);
        jtxtf_nomeCliente.setText(null);
        if (jcmb_tipoEntrega.getItemCount() > 0) {
            jcmb_tipoEntrega.setSelectedIndex(0);
        }
        
        datePicker1.setDateToToday();
        timePicker1.setTime(java.time.LocalTime.of(java.time.LocalTime.now().getHour(), 00));
        clearDestinoFieldsInfo();
        jtxta_observacoes.setText(null);
        clearProdutoFieldsInfo();
        jspn_valorEntrega.setValue(0.0);
        jspn_desconto.setValue(0);
        infoAdicionalCliente = null;
        jTable_ProdutoPedido.getModel().clearRows();
        
    }
    
    public void definirPedidoAlterar(Pedido pAlterar) {
        this.pedidoAlterar = (Pedido)pAlterar.clone();
        this.produtosNovos = pedidoAlterar.getProdutos();
        setFieldsInfo(this.pedidoAlterar);
    }
    
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
        
        jTable_ProdutoPedido.getModel().fillRows(this.produtosNovos);
        
        jspn_desconto.setValue((int)(p.getTaxaDesconto() * 100.0));
        jspn_valorEntrega.setValue(infoEntrega.getPrecoFrete());
        atualizarValoresPedido();
        jcmb_estadoInicial.setSelectedItem(p.getEstadoAtualPedido().ESTADO);
        this.infoAdicionalCliente = cliente.getInfoAdicional();
    }
    
    private void popularProdutosDisponiveis() {
        jcmb_produto.removeAllItems();
        if (jcmb_produto.getItemCount() == 0) {
            jlbl_erro_produto.setText("Recebendo produtos...");
            jlbl_erro_produto.setEnabled(true);
            javax.swing.SwingUtilities.invokeLater(() -> {
                // Preencher lista de produtos
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
    }
    
    private Pedido getFieldsInfo() {
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnl_incluirPedido = new javax.swing.JPanel();
        jTable_ProdutoPedido = new japedidos.produto.JTable_ProdutoPedido();
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
        jlbl_infoAdicionalCliente = new javax.swing.JLabel();
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
        jlbl_btn_incluir = new javax.swing.JLabel();
        jlbl_btn_excluir = new javax.swing.JLabel();
        jlbl_alterarPedido = new javax.swing.JLabel();
        jlbl_btn_voltar = new javax.swing.JLabel();
        jpnl_background1 = new javax.swing.JLabel();

        setOpaque(false);

        jpnl_incluirPedido.setOpaque(false);
        jpnl_incluirPedido.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                jpnl_incluirPedidoComponentHidden(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jpnl_incluirPedidoComponentShown(evt);
            }
        });
        jpnl_incluirPedido.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpnl_incluirPedido.add(jTable_ProdutoPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 260, 580, 120));

        jtxta_observacoes.setColumns(20);
        jtxta_observacoes.setFont(jtxta_observacoes.getFont().deriveFont((float)12));
        jtxta_observacoes.setLineWrap(true);
        jtxta_observacoes.setRows(4);
        jtxta_observacoes.setTabSize(4);
        jtxta_observacoes.setWrapStyleWord(true);
        jscp_destinatario.setViewportView(jtxta_observacoes);

        jpnl_incluirPedido.add(jscp_destinatario, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 260, 50));

        jcmb_estadoInicial.setEnabled(false);
        jpnl_incluirPedido.add(jcmb_estadoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 394, 140, -1));

        jlbl_horaEntrega.setText("HORA:");
        jlbl_horaEntrega.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_horaEntrega.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_horaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, 20));

        jSeparator1.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jpnl_incluirPedido.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 690, 10));

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
        jpnl_incluirPedido.add(jtxtf_nomeCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 30, 240, -1));

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
        datePicker1.setBackground(new java.awt.Color(0,0,0,0));
        jpnl_incluirPedido.add(datePicker1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 120, -1));

        timePicker1.getComponentTimeTextField().setPreferredSize(new java.awt.Dimension(40, 20));
        javax.swing.JButton timePickerButton = timePicker1.getComponentToggleTimeMenuButton();
        timePickerButton.setText("");
        javax.swing.ImageIcon timeExampleIcon = new javax.swing.ImageIcon(this.getClass().getResource("/timepickerbutton1.png"));
        timePickerButton.setIcon(timeExampleIcon);
        // Adjust the button size to fit the new icon.
        java.awt.Dimension newTimeButtonSize =
        new java.awt.Dimension(timeExampleIcon.getIconWidth() + 4, timeExampleIcon.getIconHeight() + 4);
        timePickerButton.setPreferredSize(newTimeButtonSize);
        jpnl_incluirPedido.add(timePicker1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 90, -1));
        jpnl_incluirPedido.add(jtxtf_rua, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 260, -1));

        jlbl_uf.setText("UF:");
        jlbl_uf.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_uf.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_uf, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, -1, -1));
        jpnl_incluirPedido.add(jtxtf_bairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(504, 90, 190, -1));

        jlbl_desconto.setText("DESC.%:");
        jlbl_desconto.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_desconto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_desconto, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 316, 60, -1));
        jpnl_incluirPedido.add(jtxtf_telefoneCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 30, 110, -1));

        jlbl_telefoneCliente.setText("TELEFONE:");
        jlbl_telefoneCliente.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_telefoneCliente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_telefoneCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 10, -1, -1));

        jlbl_quantidadeProduto.setText("QTD:");
        jlbl_quantidadeProduto.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_quantidadeProduto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_quantidadeProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 216, 40, 30));

        jlbl_valorTotal.setText("TOTAL R$:");
        jlbl_valorTotal.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_valorTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_valorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 376, 90, -1));

        jlbl_infoAdicionalCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_info_adicional.png"))); // NOI18N
        jlbl_infoAdicionalCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_infoAdicionalClienteMouseClicked(evt);
            }
        });
        jpnl_incluirPedido.add(jlbl_infoAdicionalCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 430, -1, -1));

        jlbl_dataEntrega.setText("DATA:");
        jlbl_dataEntrega.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_dataEntrega.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jpnl_incluirPedido.add(jlbl_dataEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, -1, 20));

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
        jlbl_erro_estadoInicial.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_estadoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 418, 140, 20));

        jlbl_erro_bairroEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_bairroEntrega.setText("Info inválida!");
        jlbl_erro_bairroEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_bairroEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_bairroEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 114, 180, 20));

        jlbl_erro_desconto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_desconto.setText("Info inválida!");
        jlbl_erro_desconto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_desconto.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_desconto, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 360, 90, 20));

        jlbl_erro_horaEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_horaEntrega.setText("Info inválida!");
        jlbl_erro_horaEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_horaEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_horaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 56, 90, 20));

        jlbl_erro_observacoesEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_observacoesEntrega.setText("Info inválida!");
        jlbl_erro_observacoesEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_observacoesEntrega.setForeground(new java.awt.Color(255, 255, 255));
        jpnl_incluirPedido.add(jlbl_erro_observacoesEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 194, 260, 20));

        jlbl_erro_telefone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_telefone.setText("Info inválida!");
        jlbl_erro_telefone.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_telefone.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_telefone, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 56, 110, 20));

        jlbl_erro_ruaEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_ruaEntrega.setText("Info inválida!");
        jlbl_erro_ruaEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_ruaEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_ruaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 114, 260, 20));

        jlbl_erro_numeroEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_numeroEntrega.setText("Info inválida!");
        jlbl_erro_numeroEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_numeroEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_numeroEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, 50, 20));

        jlbl_erro_cidadeEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_cidadeEntrega.setText("Info inválida!");
        jlbl_erro_cidadeEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_cidadeEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_cidadeEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 164, 120, 20));

        jlbl_erro_ufEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_ufEntrega.setText("Info inválida!");
        jlbl_erro_ufEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_ufEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jlbl_erro_ufEntrega.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jpnl_incluirPedido.add(jlbl_erro_ufEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 164, 80, 20));

        jlbl_erro_nome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_nome.setText("Info inválida!");
        jlbl_erro_nome.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_nome.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_nome, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 56, 240, 20));

        jlbl_erro_tipoEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_tipoEntrega.setText("Info inválida!");
        jlbl_erro_tipoEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_tipoEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_tipoEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 56, 90, 20));

        jlbl_erro_dataEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_dataEntrega.setText("Info inválida!");
        jlbl_erro_dataEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_dataEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_dataEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 56, 120, 20));

        jlbl_erro_produto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_produto.setText("Info inválida!");
        jlbl_erro_produto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_produto.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_produto, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 242, 240, 20));

        jlbl_erro_quantidadeProduto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_quantidadeProduto.setText("Info inválida!");
        jlbl_erro_quantidadeProduto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_quantidadeProduto.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_quantidadeProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 242, 100, 20));

        jlbl_erro_valorEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_valorEntrega.setText("Info inválida!");
        jlbl_erro_valorEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_valorEntrega.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_valorEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 298, 90, 20));

        jlbl_erro_valorTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_erro_valorTotal.setText("Info inválida!");
        jlbl_erro_valorTotal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlbl_erro_valorTotal.setForeground(new java.awt.Color(255, 0, 0));
        jpnl_incluirPedido.add(jlbl_erro_valorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 418, 90, 20));

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
        jpnl_incluirPedido.add(jlbl_btn_excluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(604, 220, 86, 33));

        jlbl_alterarPedido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_alterarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_alterarPedido_padrao.png"))); // NOI18N
        jlbl_alterarPedido.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jlbl_alterarPedido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_alterarPedidoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jlbl_alterarPedidoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jlbl_alterarPedidoMouseReleased(evt);
            }
        });
        jpnl_incluirPedido.add(jlbl_alterarPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(284, 390, 140, 33));

        jlbl_btn_voltar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbl_btn_voltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_voltar_padrao.png"))); // NOI18N
        jlbl_btn_voltar.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jlbl_btn_voltar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_btn_voltarMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jlbl_btn_voltarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jlbl_btn_voltarMouseReleased(evt);
            }
        });
        jpnl_incluirPedido.add(jlbl_btn_voltar, new org.netbeans.lib.awtextra.AbsoluteConstraints(444, 390, 140, 33));

        jpnl_background1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jpnl_background1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/background_pedidos.png"))); // NOI18N
        jpnl_background1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jpnl_background1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jpnl_background1.setMaximumSize(new java.awt.Dimension(1024, 576));
        jpnl_background1.setMinimumSize(new java.awt.Dimension(1024, 576));
        jpnl_background1.setPreferredSize(new java.awt.Dimension(1024, 576));
        jpnl_background1.setToolTipText("");
        jpnl_incluirPedido.add(jpnl_background1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-270, -70, -1, 630));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jpnl_incluirPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 545, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jpnl_incluirPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

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

    private void jcmb_produtoComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jcmb_produtoComponentShown
        // TODO add your handling code here:

    }//GEN-LAST:event_jcmb_produtoComponentShown

    private void jlbl_infoAdicionalClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_infoAdicionalClienteMouseClicked
        JFrame_InfoAdicionalCliente frame;
        if (infoAdicionalCliente == null) {
            frame = new JFrame_InfoAdicionalCliente(this);
        } else {
            frame = new JFrame_InfoAdicionalCliente(this, this.infoAdicionalCliente);
        }
        JFrame frameAncestral = (JFrame)this.getTopLevelAncestor();
        int x = frameAncestral.getX() + frameAncestral.getWidth() / 2 - frame.getWidth() / 2;
        int y = frameAncestral.getY() + frameAncestral.getHeight()/ 2 - frame.getHeight() / 2;
        frame.setLocation(x, y);
        frame.setVisible(true);
        frame.setResizable(false);
    }//GEN-LAST:event_jlbl_infoAdicionalClienteMouseClicked

    private void jspn_quantidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jspn_quantidadeKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jspn_quantidadeKeyPressed

    private void jspn_descontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jspn_descontoFocusLost
        atualizarValoresPedido();
    }//GEN-LAST:event_jspn_descontoFocusLost

    private void jspn_valorEntregaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jspn_valorEntregaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jspn_valorEntregaFocusLost

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

    private void jcmb_tipoEntregaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcmb_tipoEntregaItemStateChanged
        // TODO add your handling code here:
        if (((JComboBox)evt.getSource()).getSelectedItem() == TipoEntrega.RETIRADA) {
            setEnabledDestinoFields(false);
        } else {
            setEnabledDestinoFields(true);
        }
    }//GEN-LAST:event_jcmb_tipoEntregaItemStateChanged

    private void jpnl_incluirPedidoComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jpnl_incluirPedidoComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_jpnl_incluirPedidoComponentHidden

   
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

    
    private void jlbl_alterarPedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_alterarPedidoMouseClicked
        Pedido pNovo = getFieldsInfo();
        if (pNovo != null) {
     
            int r = BD.Pedido.update(pedidoAlterar, pNovo);
            
            if (r > 0) {
//                clearFieldsInfo();
//                this.pedidoAlterar = BD.Pedido.selectById(pedidoAlterar.getId());
//                setFieldsInfo(pedidoAlterar);
//                atualizarValoresPedido();
                JOptionPane.showMessageDialog(null, "Pedido alterado com sucesso!", "Atualização de pedido", JOptionPane.INFORMATION_MESSAGE);
                runOnFinish.run();
            } else {
                JOptionPane.showMessageDialog(null, "Atualização do pedido falhou!", "Atualização de pedido", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_jlbl_alterarPedidoMouseClicked

    private void jlbl_alterarPedidoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_alterarPedidoMousePressed
        // TODO add your handling code here:
        jlbl_alterarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_alterarPedido_pressionado.png")));
        
    }//GEN-LAST:event_jlbl_alterarPedidoMousePressed

    private void jlbl_alterarPedidoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_alterarPedidoMouseReleased
        // TODO add your handling code here:
        jlbl_alterarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_alterarPedido_padrao.png")));
    }//GEN-LAST:event_jlbl_alterarPedidoMouseReleased

    private void jlbl_btn_voltarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_voltarMouseClicked
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
    }//GEN-LAST:event_jlbl_btn_voltarMouseClicked

    private void jlbl_btn_voltarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_voltarMousePressed
        // TODO add your handling code here:

        jlbl_btn_voltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_infor_adicional_pressionado.png")));

    }//GEN-LAST:event_jlbl_btn_voltarMousePressed

    private void jlbl_btn_voltarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_voltarMouseReleased
        // TODO add your handling code here:
        jlbl_btn_voltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_infor_adicional_padrao.png")));
    }//GEN-LAST:event_jlbl_btn_voltarMouseReleased

//    public static void main(String[] args) {
//        javax.swing.SwingUtilities.invokeLater(() -> {
//            JPanel_AlterarPedido panel = new JPanel_AlterarPedido(BD.Pedido.selectById(3), () -> {System.out.println("Finalizou");});
//            JFrame frame = new JFrame("Teste de alteração de pedido");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.add(panel);
//            frame.pack();
//            frame.setVisible(true);
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.github.lgooddatepicker.components.DatePicker datePicker1;
    private javax.swing.JSeparator jSeparator1;
    private japedidos.produto.JTable_ProdutoPedido jTable_ProdutoPedido;
    private javax.swing.JComboBox<japedidos.pedidos.Estado> jcmb_estadoInicial;
    private javax.swing.JComboBox<japedidos.produto.Produto> jcmb_produto;
    private javax.swing.JComboBox<japedidos.pedidos.TipoEntrega> jcmb_tipoEntrega;
    private javax.swing.JComboBox<String> jcmb_uf;
    javax.swing.JLabel jlbl_alterarPedido;
    private javax.swing.JLabel jlbl_bairro;
    javax.swing.JLabel jlbl_btn_excluir;
    javax.swing.JLabel jlbl_btn_incluir;
    javax.swing.JLabel jlbl_btn_voltar;
    private javax.swing.JLabel jlbl_cidade;
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
    private javax.swing.JLabel jlbl_horaEntrega;
    private javax.swing.JLabel jlbl_infoAdicionalCliente;
    private javax.swing.JLabel jlbl_nomeCliente;
    private javax.swing.JLabel jlbl_numero;
    private javax.swing.JLabel jlbl_observações;
    private javax.swing.JLabel jlbl_produto;
    private javax.swing.JLabel jlbl_quantidadeProduto;
    private javax.swing.JLabel jlbl_rua;
    private javax.swing.JLabel jlbl_telefoneCliente;
    private javax.swing.JLabel jlbl_tipoEntrega;
    private javax.swing.JLabel jlbl_uf;
    private javax.swing.JLabel jlbl_valorEntrega;
    private javax.swing.JLabel jlbl_valorTotal;
    private javax.swing.JLabel jpnl_background1;
    private javax.swing.JLabel jpnl_btn_novo;
    private javax.swing.JPanel jpnl_incluirPedido;
    private javax.swing.JScrollPane jscp_destinatario;
    private javax.swing.JSpinner jspn_desconto;
    private javax.swing.JSpinner jspn_quantidade;
    private javax.swing.JSpinner jspn_valorEntrega;
    private javax.swing.JTextArea jtxta_observacoes;
    private javax.swing.JTextField jtxtf_bairro;
    private javax.swing.JTextField jtxtf_cidade;
    private javax.swing.JTextField jtxtf_nomeCliente;
    private javax.swing.JTextField jtxtf_numero;
    private javax.swing.JTextField jtxtf_rua;
    private javax.swing.JTextField jtxtf_telefoneCliente;
    private javax.swing.JTextField jtxtf_valorTotal;
    private com.github.lgooddatepicker.components.TimePicker timePicker1;
    // End of variables declaration//GEN-END:variables
}
