package japedidos.produto;

import java.awt.Color;
import javax.swing.ImageIcon;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import japedidos.bd.BD;

public class JFrame_ListaProdutos extends javax.swing.JFrame {
/*
    
    String diretorioBaseImagens = ".\\src\\main\\java\\japedidos\\imagens\\";
    ImageIcon imgBackground_01 = new ImageIcon(diretorioBaseImagens + "\\background_produtos_075x.png");
    */
    
    Color cor_padrao_txt_clientes = new Color(128,128,128);
    Color cor_padrao_txt_produtos = new Color(255,255,255);
    Color cor_padrao_txt_pedidos = new Color(128,128,128);
    Color cor_padrao_txt_relatorios = new Color(128,128,128);
    
    /**
     * Creates new form listaProdutos
     */
    public JFrame_ListaProdutos() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnl_sideMenu = new javax.swing.JPanel();
        jtb_linhaBranca = new javax.swing.JToolBar();
        jlbl_clientes = new javax.swing.JLabel();
        jlbl_produtos = new javax.swing.JLabel();
        jlbl_pedidos = new javax.swing.JLabel();
        jlbl_relatorios = new javax.swing.JLabel();
        jpnl_img_etiqueta = new javax.swing.JLabel();
        jpnl_background_01 = new javax.swing.JLabel();
        jpnl_corpo = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jtxtf_pesquisa = new javax.swing.JTextField();
        jlbl_codigo = new javax.swing.JLabel();
        jtxtf_codigo = new javax.swing.JTextField();
        jlbl_descricao = new javax.swing.JLabel();
        jtxtf_descricao = new javax.swing.JTextField();
        jlbl_categoria = new javax.swing.JLabel();
        jcmb_categoria = new javax.swing.JComboBox<>();
        jlbl_und = new javax.swing.JLabel();
        jcmb_und = new javax.swing.JComboBox<>();
        jlbl_valor = new javax.swing.JLabel();
        jtxtf_valor = new javax.swing.JTextField();
        jtb_linha = new javax.swing.JToolBar();
        jtb_linha1 = new javax.swing.JToolBar();
        jlbl_clientes1 = new javax.swing.JLabel();
        jlbl_produtos1 = new javax.swing.JLabel();
        jlbl_pedidos1 = new javax.swing.JLabel();
        jlbl_relatorios1 = new javax.swing.JLabel();
        jpnl_img_etiqueta1 = new javax.swing.JLabel();
        jpnl_background2 = new javax.swing.JLabel();
        jpnl_btn_novo = new javax.swing.JLabel();
        jpnl_btn_excluir = new javax.swing.JLabel();
        jpnl_btn_salvar = new javax.swing.JLabel();
        jtbl_listaprodutos = new javax.swing.JScrollPane();
        jtbl_lista_produtos = new javax.swing.JTable();
        jpnl_background_02 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusCycleRoot(false);
        setIconImages(null);
        setResizable(false);
        setSize(new java.awt.Dimension(1024, 576));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpnl_sideMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpnl_sideMenu.setMaximumSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.setMinimumSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.setPreferredSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtb_linhaBranca.setBackground(new java.awt.Color(255, 255, 255));
        jtb_linhaBranca.setBorder(null);
        jtb_linhaBranca.setForeground(new java.awt.Color(255, 255, 255));
        jtb_linhaBranca.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jtb_linhaBranca.setBorderPainted(false);
        jpnl_sideMenu.add(jtb_linhaBranca, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 280, 100, 4));

        jlbl_clientes.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_clientes.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_clientes.setForeground(new Color(128,128,128));
        jlbl_clientes.setText("CLIENTES");
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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jlbl_clientesMousePressed(evt);
            }
        });
        jpnl_sideMenu.add(jlbl_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 210, -1, -1));

        jlbl_produtos.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_produtos.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_produtos.setForeground(new Color(255,255,255));
        jlbl_produtos.setText("PRODUTOS");
        jlbl_produtos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jlbl_produtosFocusGained(evt);
            }
        });
        jlbl_produtos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_produtosMouseClicked(evt);
            }
        });
        jpnl_sideMenu.add(jlbl_produtos, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 250, -1, -1));

        jlbl_pedidos.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_pedidos.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_pedidos.setForeground(new Color(128,128,128));
        jlbl_pedidos.setText("PEDIDOS");
        jlbl_pedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_pedidosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jlbl_pedidosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jlbl_pedidosMouseExited(evt);
            }
        });
        jpnl_sideMenu.add(jlbl_pedidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 290, -1, -1));

        jlbl_relatorios.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_relatorios.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_relatorios.setForeground(new Color(128,128,128));
        jlbl_relatorios.setText("RELATÓRIOS");
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
        jpnl_sideMenu.add(jlbl_relatorios, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 330, -1, -1));

        jpnl_img_etiqueta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/painel_comandos_esquerda_05x.png"))); // NOI18N
        jpnl_img_etiqueta.setMaximumSize(new java.awt.Dimension(250, 576));
        jpnl_img_etiqueta.setMinimumSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.add(jpnl_img_etiqueta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 250, -1));

        jpnl_background_01.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jpnl_background_01.setIcon(new javax.swing.ImageIcon(getClass().getResource("/background_produtos_075x.png"))); // NOI18N
        jpnl_background_01.setToolTipText("");
        jpnl_background_01.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jpnl_background_01.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jpnl_background_01.setMaximumSize(new java.awt.Dimension(1024, 576));
        jpnl_background_01.setMinimumSize(new java.awt.Dimension(1024, 576));
        jpnl_background_01.setPreferredSize(new java.awt.Dimension(1024, 576));
        jpnl_sideMenu.add(jpnl_background_01, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jpnl_sideMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 576));

        jpnl_corpo.setBackground(new java.awt.Color(153, 204, 255));
        jpnl_corpo.setMaximumSize(new java.awt.Dimension(1024, 576));
        jpnl_corpo.setMinimumSize(new java.awt.Dimension(1024, 576));
        jpnl_corpo.setPreferredSize(new java.awt.Dimension(1024, 576));
        jpnl_corpo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("TESTE CONSULTA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jpnl_corpo.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 40, -1, -1));

        jtxtf_pesquisa.setBackground(new java.awt.Color(204, 204, 204));
        jtxtf_pesquisa.setForeground(new java.awt.Color(0, 0, 0));
        jtxtf_pesquisa.setText("Coxinha de frango");
        jtxtf_pesquisa.setBorder(jtxtf_codigo.getBorder());
        jtxtf_pesquisa.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jtxtf_pesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtf_pesquisaActionPerformed(evt);
            }
        });
        jpnl_corpo.add(jtxtf_pesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 680, 30));

        jlbl_codigo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_codigo.setForeground(new java.awt.Color(0, 0, 0));
        jlbl_codigo.setText("CÓDIGO :");
        jpnl_corpo.add(jlbl_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, -1, -1));

        jtxtf_codigo.setBackground(new java.awt.Color(204, 204, 204));
        jtxtf_codigo.setForeground(new java.awt.Color(0, 0, 0));
        jpnl_corpo.add(jtxtf_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 170, 90, 30));

        jlbl_descricao.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_descricao.setForeground(new java.awt.Color(0, 0, 0));
        jlbl_descricao.setText("DESCRIÇÃO :");
        jpnl_corpo.add(jlbl_descricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, -1, -1));

        jtxtf_descricao.setBackground(new java.awt.Color(204, 204, 204));
        jtxtf_descricao.setForeground(new java.awt.Color(0, 0, 0));
        jpnl_corpo.add(jtxtf_descricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 210, 280, 30));

        jlbl_categoria.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_categoria.setForeground(new java.awt.Color(0, 0, 0));
        jlbl_categoria.setText("CATEGORIA :");
        jpnl_corpo.add(jlbl_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, -1, -1));

        jcmb_categoria.setBackground(new java.awt.Color(204, 204, 204));
        jcmb_categoria.setForeground(new java.awt.Color(255, 255, 255));
        jcmb_categoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALIMENTO", "REFRIGERANTE", "MOLHOS" }));
        jpnl_corpo.add(jcmb_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 250, 140, 30));

        jlbl_und.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_und.setForeground(new java.awt.Color(0, 0, 0));
        jlbl_und.setText("UND :");
        jpnl_corpo.add(jlbl_und, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 250, -1, -1));

        jcmb_und.setBackground(new java.awt.Color(204, 204, 204));
        jcmb_und.setForeground(new java.awt.Color(255, 255, 255));
        jcmb_und.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UNI", "KG", "SC" }));
        jpnl_corpo.add(jcmb_und, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 250, 60, 30));

        jlbl_valor.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbl_valor.setForeground(new java.awt.Color(0, 0, 0));
        jlbl_valor.setText("VALOR :");
        jpnl_corpo.add(jlbl_valor, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 250, -1, -1));

        jtxtf_valor.setBackground(new java.awt.Color(204, 204, 204));
        jtxtf_valor.setForeground(new java.awt.Color(0, 0, 0));
        jpnl_corpo.add(jtxtf_valor, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 250, 70, 30));

        jtb_linha.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, java.awt.Color.darkGray, null));

        jtb_linha1.setBackground(new java.awt.Color(255, 255, 255));
        jtb_linha1.setBorder(null);
        jtb_linha1.setForeground(new java.awt.Color(255, 255, 255));
        jtb_linha.add(jtb_linha1);

        jlbl_clientes1.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_clientes1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_clientes1.setText("CLIENTES");
        jtb_linha.add(jlbl_clientes1);

        jlbl_produtos1.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_produtos1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_produtos1.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_produtos1.setText("PRODUTOS");
        jlbl_produtos1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jlbl_produtosFocusGained(evt);
            }
        });
        jtb_linha.add(jlbl_produtos1);

        jlbl_pedidos1.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_pedidos1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_pedidos1.setText("PEDIDOS");
        jtb_linha.add(jlbl_pedidos1);

        jlbl_relatorios1.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_relatorios1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_relatorios1.setText("RELATÓRIOS");
        jtb_linha.add(jlbl_relatorios1);

        jpnl_img_etiqueta1.setMaximumSize(new java.awt.Dimension(250, 576));
        jpnl_img_etiqueta1.setMinimumSize(new java.awt.Dimension(250, 576));
        jtb_linha.add(jpnl_img_etiqueta1);

        jpnl_background2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jpnl_background2.setToolTipText("");
        jpnl_background2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jpnl_background2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jpnl_background2.setMaximumSize(new java.awt.Dimension(1024, 576));
        jpnl_background2.setMinimumSize(new java.awt.Dimension(1024, 576));
        jpnl_background2.setPreferredSize(new java.awt.Dimension(1024, 576));
        jtb_linha.add(jpnl_background2);

        jpnl_corpo.add(jtb_linha, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 680, 2));

        jpnl_btn_novo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_novo_padrao.png"))); // NOI18N
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
        jpnl_corpo.add(jpnl_btn_novo, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 170, -1, -1));

        jpnl_btn_excluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_excluir_padrao.png"))); // NOI18N
        jpnl_btn_excluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jpnl_btn_excluirMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jpnl_btn_excluirMouseReleased(evt);
            }
        });
        jpnl_corpo.add(jpnl_btn_excluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 210, -1, -1));

        jpnl_btn_salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_salvar_padrao.png"))); // NOI18N
        jpnl_btn_salvar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jpnl_btn_salvarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jpnl_btn_salvarMouseReleased(evt);
            }
        });
        jpnl_corpo.add(jpnl_btn_salvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 250, -1, -1));

        jtbl_lista_produtos.setBackground(new java.awt.Color(153, 204, 255));
        jtbl_lista_produtos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtbl_lista_produtos.setForeground(new java.awt.Color(255, 255, 255));
        jtbl_lista_produtos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "BOLINHA DE QUEIJO", "ALIMENTO", null, null, "UNI", null},
                {null, "COXINHA DE FRANGO", "ALIMENTO", null, null, "UNI", null},
                {null, "KIBE DE CARNE", "ALIMENTO", null, null, "UNI", null},
                {null, "RISSOLE DE PIZZA", "ALIMENTO", null, null, "UNI", null},
                {null, "RISSOLE DE CAMARÃO", "ALIMENTO", null, null, "UNI", null},
                {null, "MINI CHURROS", "ALIMENTO", null, null, "UNI", null},
                {null, "ENROLADO DE SALSICHA ", "ALIMENTO", null, null, "UNI", null}
            },
            new String [] {
                "Codigo", "Descrição", "Categoria", "Custo(R$)", "Venda(R$)", "Unidade ", "Ativo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtbl_lista_produtos.setToolTipText("");
        jtbl_lista_produtos.setMinimumSize(new java.awt.Dimension(90, 160));
        jtbl_lista_produtos.setPreferredSize(new java.awt.Dimension(655, 204));
        jtbl_lista_produtos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbl_lista_produtosMouseClicked(evt);
            }
        });
        jtbl_listaprodutos.setViewportView(jtbl_lista_produtos);
        if (jtbl_lista_produtos.getColumnModel().getColumnCount() > 0) {
            jtbl_lista_produtos.getColumnModel().getColumn(0).setResizable(false);
            jtbl_lista_produtos.getColumnModel().getColumn(0).setPreferredWidth(60);
            jtbl_lista_produtos.getColumnModel().getColumn(1).setResizable(false);
            jtbl_lista_produtos.getColumnModel().getColumn(1).setPreferredWidth(350);
            jtbl_lista_produtos.getColumnModel().getColumn(2).setResizable(false);
            jtbl_lista_produtos.getColumnModel().getColumn(2).setPreferredWidth(90);
            jtbl_lista_produtos.getColumnModel().getColumn(3).setResizable(false);
            jtbl_lista_produtos.getColumnModel().getColumn(3).setPreferredWidth(66);
            jtbl_lista_produtos.getColumnModel().getColumn(4).setResizable(false);
            jtbl_lista_produtos.getColumnModel().getColumn(5).setResizable(false);
            jtbl_lista_produtos.getColumnModel().getColumn(5).setPreferredWidth(55);
            jtbl_lista_produtos.getColumnModel().getColumn(6).setResizable(false);
            jtbl_lista_produtos.getColumnModel().getColumn(6).setPreferredWidth(40);
        }

        jpnl_corpo.add(jtbl_listaprodutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 300, 680, 170));

        jpnl_background_02.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jpnl_background_02.setIcon(new javax.swing.ImageIcon(getClass().getResource("/background_produtos_075x.png"))); // NOI18N
        jpnl_background_02.setToolTipText("");
        jpnl_background_02.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jpnl_background_02.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jpnl_background_02.setMaximumSize(new java.awt.Dimension(1024, 576));
        jpnl_background_02.setMinimumSize(new java.awt.Dimension(1024, 576));
        jpnl_background_02.setPreferredSize(new java.awt.Dimension(1024, 576));
        jpnl_corpo.add(jpnl_background_02, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jpnl_corpo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jlbl_produtosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jlbl_produtosFocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jlbl_produtosFocusGained

    private void jpnl_btn_novoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnl_btn_novoMousePressed
        // TODO add your handling code here:
        jpnl_btn_novo.setIcon(new javax.swing.ImageIcon(".\\src\\main\\java\\japedidos\\imagens\\btn_novo_pressionado.png")); 
    }//GEN-LAST:event_jpnl_btn_novoMousePressed

    private void jpnl_btn_novoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnl_btn_novoMouseClicked
        // TODO add your handling code here:
    

    }//GEN-LAST:event_jpnl_btn_novoMouseClicked

    private void jpnl_btn_novoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnl_btn_novoMouseReleased
        // TODO add your handling code here:
        jpnl_btn_novo.setIcon(new javax.swing.ImageIcon(".\\src\\main\\java\\japedidos\\imagens\\btn_novo_padrao.png")); 
    }//GEN-LAST:event_jpnl_btn_novoMouseReleased

    private void jpnl_btn_excluirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnl_btn_excluirMousePressed
        // TODO add your handling code here:
        jpnl_btn_excluir.setIcon(new javax.swing.ImageIcon(".\\src\\main\\java\\japedidos\\imagens\\btn_excluir_pressionado.png")); 
    }//GEN-LAST:event_jpnl_btn_excluirMousePressed

    private void jpnl_btn_excluirMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnl_btn_excluirMouseReleased
        // TODO add your handling code here:
        jpnl_btn_excluir.setIcon(new javax.swing.ImageIcon(".\\src\\main\\java\\japedidos\\imagens\\btn_excluir_padrao.png")); 
    }//GEN-LAST:event_jpnl_btn_excluirMouseReleased

    private void jpnl_btn_salvarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnl_btn_salvarMouseReleased
        // TODO add your handling code here:
        jpnl_btn_salvar.setIcon(new javax.swing.ImageIcon(".\\src\\main\\java\\japedidos\\imagens\\btn_salvar_padrao.png")); 
    }//GEN-LAST:event_jpnl_btn_salvarMouseReleased

    private void jpnl_btn_salvarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnl_btn_salvarMousePressed
        // TODO add your handling code here:
        jpnl_btn_salvar.setIcon(new javax.swing.ImageIcon(".\\src\\main\\java\\japedidos\\imagens\\btn_salvar_pressionado.png")); 
    }//GEN-LAST:event_jpnl_btn_salvarMousePressed

    private void jlbl_clientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_clientesMouseEntered
        // TODO add your handling code here:
        jlbl_clientes.setForeground(new Color(187,187,187));
        
    }//GEN-LAST:event_jlbl_clientesMouseEntered

    private void jlbl_clientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_clientesMouseExited
        // TODO add your handling code here:
        jlbl_clientes.setForeground(new Color(128,128,128));
    }//GEN-LAST:event_jlbl_clientesMouseExited

    private void jlbl_pedidosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_pedidosMouseExited
        // TODO add your handling code here:
        if (jlbl_pedidos.getForeground().equals(new Color(255, 255, 255)) ){
             jlbl_pedidos.setForeground(new Color(128,128,128));
         }
    }//GEN-LAST:event_jlbl_pedidosMouseExited

    private void jlbl_pedidosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_pedidosMouseEntered
        // TODO add your handling code here:
        jlbl_pedidos.setForeground(new Color(187,187,187));
    }//GEN-LAST:event_jlbl_pedidosMouseEntered

    private void jlbl_relatoriosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_relatoriosMouseEntered
        // TODO add your handling code here:
        jlbl_relatorios.setForeground(new Color(187,187,187));
    }//GEN-LAST:event_jlbl_relatoriosMouseEntered

    private void jlbl_relatoriosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_relatoriosMouseExited
        // TODO add your handling code here:
        //jlbl_relatorios.setForeground(new Color(128,128,128));
        if (cor_padrao_txt_relatorios!= new Color(255,255,255)){
            jlbl_relatorios.setForeground(new Color(128,128,128));
        } else {
        }
    }//GEN-LAST:event_jlbl_relatoriosMouseExited

    private void jlbl_clientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_clientesMousePressed
        // TODO add your handling code here:
        //jtb_linhaBranca.locate(46, 240);
    }//GEN-LAST:event_jlbl_clientesMousePressed

    private void jlbl_clientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_clientesMouseClicked
        // TODO add your handling code here:
        cor_padrao_txt_clientes = new Color(255,255,255);
        jlbl_clientes.setForeground(cor_padrao_txt_clientes);
        
        cor_padrao_txt_produtos = new Color(128,128,128);
        cor_padrao_txt_pedidos = new Color(128,128,128);
        cor_padrao_txt_relatorios = new Color(128,128,128);

        
        
        
//        jtb_linhaBranca.setSize(jlbl_clientes.getWidth(), 4 );
//        jlbl_clientes.setForeground(new Color(255,255,255));
        
        jlbl_pedidos.setForeground(new Color(128,128,128));
        jlbl_produtos.setForeground(new Color(128,128,128));
        jlbl_relatorios.setForeground(new Color(128,128,128));
        
    }//GEN-LAST:event_jlbl_clientesMouseClicked

    private void jlbl_produtosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_produtosMouseClicked
        // TODO add your handling code here:
        jtb_linhaBranca.setLocation(46, 250+30);
        jtb_linhaBranca.setSize(jlbl_produtos.getWidth(), 4 );
        jlbl_produtos.setForeground(new Color(255,255,255));

    }//GEN-LAST:event_jlbl_produtosMouseClicked

    private void jlbl_pedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_pedidosMouseClicked
        // TODO add your handling code here:
        jtb_linhaBranca.setLocation(46, 290+30);
        jtb_linhaBranca.setSize(jlbl_pedidos.getWidth(), 4 );
        jlbl_pedidos.setForeground(new Color(255,255,255));
        
    }//GEN-LAST:event_jlbl_pedidosMouseClicked

    private void jlbl_relatoriosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_relatoriosMouseClicked
        // TODO add your handling code here:
        jtb_linhaBranca.setLocation(46, 330+30);
        jtb_linhaBranca.setSize(jlbl_relatorios.getWidth(), 4 );
        jlbl_relatorios.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_jlbl_relatoriosMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        TODO add your handling code here:
//        String sql = "SELECT * FROM produto Where nome='coxinha de frango'";
//        String sql = "SELECT * FROM listatodosprodutos GROUP BY nome LIKE '%PIZZA' "; // funciona
        String sql = "SELECT * FROM listatodosprodutos GROUP BY nome LIKE "; 

//        String sql = "SELECT * FROM listatodosprodutos GROUP BY nome ";
        Connection conexao = null;
        PreparedStatement statement = null;
        String url = "jdbc:mysql://localhost:3306/titanw25_japedidos_hml";
        String usuario = "root";
        String senha = "";
/*
        String url = "jdbc:mysql://162.241.203.86:3306/titanw25_japedidos_hml";
        String usuario = "titanw25_japedidos_hml";
        String senha = "seNai@2024proj";
        */
        try
        {
            
//            Connection con=(Connection)DriverManager.getConnection("jdbc:mysql://localhost/titanw25_japedidos_hml", "root", "");
//            PreparedStatement banco = (PreparedStatement)con.prepareStatement(sql);
            conexao = DriverManager.getConnection(url, usuario, senha);
            statement = conexao.prepareStatement(sql);
/*  
            String encontrar = jtxtf_pesquisa.getText();
            System.out.println("Texto = " + encontrar);
            sql = sql + "LIKE nome='%" + encontrar + "%'";
  */               
            String encontrar = jtxtf_pesquisa.getText();
            sql = sql + "'%" + encontrar + "'";
            statement = conexao.prepareStatement(sql);
/*
            String encontrar = jtxtf_pesquisa.getText();
            System.out.println("Texto = " + encontrar);
            statement.setString(1, encontrar);
*/
                    
            statement.execute(); // cria o vetor
            ResultSet resultado = statement.executeQuery(sql);

            DefaultTableModel model =(DefaultTableModel) jtbl_lista_produtos.getModel();
            model.setNumRows(0);
            

            while(resultado.next())
            {
                model.addRow(new Object[] 
                { 
                   //retorna os dados da tabela do BD, cada campo e um coluna.
                   resultado.getString("id"),
                   resultado.getString("nome"),
                   resultado.getString("categoria"),
                   resultado.getDouble("preco_custo"),
                   resultado.getDouble("preco_venda"),
                   resultado.getString("unidade"),
                   resultado.getBoolean("estado")
                }); 
           } 
            statement.close();
            statement.close();
        }
          catch (SQLException ex)
          {
             System.out.println("o erro foi " +ex);
            }            
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtbl_lista_produtosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbl_lista_produtosMouseClicked
        // TODO add your handling code here:
        System.out.println("Linha Selecionada - " + jtbl_lista_produtos.getSelectedRow());
        System.out.println("Coluna Selecionada - " + jtbl_lista_produtos.getSelectedColumn());
        System.out.println("Coluna Selecionada - " + jtbl_lista_produtos.getValueAt(jtbl_lista_produtos.getSelectedRow(), jtbl_lista_produtos.getSelectedColumn()));
        jtxtf_codigo.setText((String) jtbl_lista_produtos.getValueAt(jtbl_lista_produtos.getSelectedRow(), 0));
        jtxtf_descricao.setText((String) jtbl_lista_produtos.getValueAt(jtbl_lista_produtos.getSelectedRow(), 1));
        jcmb_categoria.setSelectedItem((String) jtbl_lista_produtos.getValueAt(jtbl_lista_produtos.getSelectedRow(), 2));
    }//GEN-LAST:event_jtbl_lista_produtosMouseClicked

    private void jtxtf_pesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtf_pesquisaActionPerformed
        // TODO add your handling code here:
//          String sql = "SELECT * FROM listatodosprodutos GROUP BY nome LIKE ";         
          String sql = "SELECT * FROM listatodosprodutos GROUP BY nome LIKE ";         

        Connection conexao = null;
        PreparedStatement statement = null;
        String url = "jdbc:mysql://localhost:3306/titanw25_japedidos_hml";
        String usuario = "root";
        String senha = "";
/*
        String url = "jdbc:mysql://162.241.203.86:3306/titanw25_japedidos_hml";
        String usuario = "titanw25_japedidos_hml";
        String senha = "seNai@2024proj";
        */
        try
        {
            
            conexao = DriverManager.getConnection(url, usuario, senha);
//            statement = conexao.prepareStatement(sql);

            String encontrar = jtxtf_pesquisa.getText();
            sql = sql + "'%" + encontrar + "%'";
            statement = conexao.prepareStatement(sql);
/*
            String encontrar = jtxtf_pesquisa.getText();
            System.out.println("Texto = " + encontrar);
            statement.setString(1, encontrar);
*/
                    
            statement.execute();
            ResultSet resultado = statement.executeQuery(sql);

            DefaultTableModel model =(DefaultTableModel) jtbl_lista_produtos.getModel();
            model.setNumRows(0);

            while(resultado.next())
            {
                model.addRow(new Object[] 
                { 
                   //retorna os dados da tabela do BD, cada campo e um coluna.
                   resultado.getString("id"),
                   resultado.getString("nome"),
                   resultado.getString("categoria"),
                   resultado.getDouble("preco_custo"),
                   resultado.getDouble("preco_venda"),
                   resultado.getString("unidade"),
                   resultado.getBoolean("estado")
                }); 
           } 
            statement.close();
            statement.close();
        }
          catch (SQLException ex)
          {
             System.out.println("o erro foi " +ex);
            }        
    }//GEN-LAST:event_jtxtf_pesquisaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        Path currentRelativePath = Paths.get("");
        String caminhoImagens = currentRelativePath.toAbsolutePath().toString();
        caminhoImagens = Paths.get(".").toAbsolutePath().normalize().toString();
        System.out.print("Current absolute path is: " + caminhoImagens);
        
                
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrame_ListaProdutos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_ListaProdutos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_ListaProdutos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_ListaProdutos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_ListaProdutos().setVisible(true);
                    Color cor_padrao_txt_clientes = new Color(128,128,128);
                    Color cor_padrao_txt_produtos = new Color(255,255,255);
                    Color cor_padrao_txt_pedidos = new Color(128,128,128);
                    Color cor_padrao_txt_relatorios = new Color(128,128,128);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jcmb_categoria;
    private javax.swing.JComboBox<String> jcmb_und;
    private javax.swing.JLabel jlbl_categoria;
    private javax.swing.JLabel jlbl_clientes;
    private javax.swing.JLabel jlbl_clientes1;
    private javax.swing.JLabel jlbl_codigo;
    private javax.swing.JLabel jlbl_descricao;
    private javax.swing.JLabel jlbl_pedidos;
    private javax.swing.JLabel jlbl_pedidos1;
    private javax.swing.JLabel jlbl_produtos;
    private javax.swing.JLabel jlbl_produtos1;
    private javax.swing.JLabel jlbl_relatorios;
    private javax.swing.JLabel jlbl_relatorios1;
    private javax.swing.JLabel jlbl_und;
    private javax.swing.JLabel jlbl_valor;
    private javax.swing.JLabel jpnl_background2;
    private javax.swing.JLabel jpnl_background_01;
    private javax.swing.JLabel jpnl_background_02;
    private javax.swing.JLabel jpnl_btn_excluir;
    private javax.swing.JLabel jpnl_btn_novo;
    private javax.swing.JLabel jpnl_btn_salvar;
    private javax.swing.JPanel jpnl_corpo;
    private javax.swing.JLabel jpnl_img_etiqueta;
    private javax.swing.JLabel jpnl_img_etiqueta1;
    private javax.swing.JPanel jpnl_sideMenu;
    private javax.swing.JToolBar jtb_linha;
    private javax.swing.JToolBar jtb_linha1;
    private javax.swing.JToolBar jtb_linhaBranca;
    private javax.swing.JTable jtbl_lista_produtos;
    private javax.swing.JScrollPane jtbl_listaprodutos;
    private javax.swing.JTextField jtxtf_codigo;
    private javax.swing.JTextField jtxtf_descricao;
    private javax.swing.JTextField jtxtf_pesquisa;
    private javax.swing.JTextField jtxtf_valor;
    // End of variables declaration//GEN-END:variables

}
