/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package japedidos.relatorios;

/**
 *
 * @author v.gomes
 */
public class JFrame_Relatorios extends javax.swing.JFrame {

    /**
     * Creates new form JFrame_relaorioIntercorrencia
     */
    public JFrame_Relatorios() {
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

        jPanel1 = new javax.swing.JPanel();
        jpnl_sideMenu = new javax.swing.JPanel();
        jtb_linhaBranca = new javax.swing.JToolBar();
        jlbl_clientes = new javax.swing.JLabel();
        jlbl_produtos = new javax.swing.JLabel();
        jlbl_pedidos = new javax.swing.JLabel();
        jlbl_relatorios = new javax.swing.JLabel();
        jpnl_img_etiqueta = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpnl_relatorioFinanceiro = new javax.swing.JPanel();
        jBtt_novembro = new javax.swing.JButton();
        jBtt_junho = new javax.swing.JButton();
        jBtt_outubro = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtl_anual = new javax.swing.JTable();
        jBtt_abril = new javax.swing.JButton();
        jBtt_dezembro = new javax.swing.JButton();
        jBtt_fevereiro = new javax.swing.JButton();
        jBtt_agosto = new javax.swing.JButton();
        jBtt_julho = new javax.swing.JButton();
        jBtt_marco = new javax.swing.JButton();
        jCbox_ano = new javax.swing.JComboBox<>();
        jBtt_janeiro = new javax.swing.JButton();
        jBtt_maio = new javax.swing.JButton();
        jpn_relatorio = new javax.swing.JPanel();
        jLl_relatorio = new javax.swing.JLabel();
        jlbl_mes = new javax.swing.JLabel();
        jBtt_setembro = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtl_produto_mes = new javax.swing.JTable();
        jpnl_relatorioPerfomanceGeral = new javax.swing.JPanel();
        jpnl_relatorioIntercorrencia = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtbl_relatorioIntercorrencia = new javax.swing.JTable();
        jlbl_filtroPeridoIntercorrencia = new javax.swing.JLabel();
        jtxtf_periodoInicialIntercorrencia = new javax.swing.JTextField();
        jtxtf_periodoFinalIntercorrencia = new javax.swing.JTextField();
        jlbl_filtroPeridoIntercorrenciaAte = new javax.swing.JLabel();
        jbtn_pesquisarFiltroIntercorrencia = new javax.swing.JButton();
        jpnl_background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1024, 576));
        setMinimumSize(new java.awt.Dimension(1024, 576));
        setResizable(false);
        setSize(new java.awt.Dimension(1024, 576));

        jPanel1.setMaximumSize(new java.awt.Dimension(1024, 576));
        jPanel1.setMinimumSize(new java.awt.Dimension(1024, 576));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 576));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpnl_sideMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpnl_sideMenu.setMaximumSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.setMinimumSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.setOpaque(false);
        jpnl_sideMenu.setPreferredSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtb_linhaBranca.setBackground(new java.awt.Color(255, 255, 255));
        jtb_linhaBranca.setBorder(null);
        jtb_linhaBranca.setForeground(new java.awt.Color(255, 255, 255));
        jtb_linhaBranca.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jtb_linhaBranca.setBorderPainted(false);
        jpnl_sideMenu.add(jtb_linhaBranca, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 360, 114, 4));

        jlbl_clientes.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_clientes.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_clientes.setText("CLIENTES");
        jpnl_sideMenu.add(jlbl_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 210, -1, -1));

        jlbl_produtos.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_produtos.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_produtos.setForeground(new java.awt.Color(204, 204, 204));
        jlbl_produtos.setText("PRODUTOS");
        jlbl_produtos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jlbl_produtosFocusGained(evt);
            }
        });
        jpnl_sideMenu.add(jlbl_produtos, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 250, -1, -1));

        jlbl_pedidos.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_pedidos.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_pedidos.setText("PEDIDOS");
        jpnl_sideMenu.add(jlbl_pedidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 290, -1, -1));

        jlbl_relatorios.setBackground(new java.awt.Color(0, 0, 0));
        jlbl_relatorios.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_relatorios.setText("RELATÓRIOS");
        jpnl_sideMenu.add(jlbl_relatorios, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 330, -1, -1));

        jpnl_img_etiqueta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/painel_comandos_esquerda_05x.png"))); // NOI18N
        jpnl_img_etiqueta.setText("jLabel2");
        jpnl_img_etiqueta.setMaximumSize(new java.awt.Dimension(250, 576));
        jpnl_img_etiqueta.setMinimumSize(new java.awt.Dimension(250, 576));
        jpnl_sideMenu.add(jpnl_img_etiqueta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 250, -1));

        jPanel1.add(jpnl_sideMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 576));

        jpnl_relatorioFinanceiro.setOpaque(false);
        jpnl_relatorioFinanceiro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jBtt_novembro.setText("NOVEMBRO");
        jpnl_relatorioFinanceiro.add(jBtt_novembro, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, 90, -1));

        jBtt_junho.setText("JUNHO");
        jpnl_relatorioFinanceiro.add(jBtt_junho, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, 90, -1));

        jBtt_outubro.setText("OUTUBRO");
        jpnl_relatorioFinanceiro.add(jBtt_outubro, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 90, -1));

        jtl_anual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "PRODUTOS", "MEDIA ANUAL", "TOTAL ANUAL"
            }
        ));
        jScrollPane2.setViewportView(jtl_anual);

        jpnl_relatorioFinanceiro.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 170, 430, 240));

        jBtt_abril.setText("ABRIL");
        jpnl_relatorioFinanceiro.add(jBtt_abril, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 40, 80, -1));

        jBtt_dezembro.setText("DEZEMBRO");
        jpnl_relatorioFinanceiro.add(jBtt_dezembro, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, 80, -1));

        jBtt_fevereiro.setText("FEVEREIRO");
        jpnl_relatorioFinanceiro.add(jBtt_fevereiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 90, -1));

        jBtt_agosto.setText("AGOSTO");
        jpnl_relatorioFinanceiro.add(jBtt_agosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 70, 80, -1));

        jBtt_julho.setText("JULHO");
        jpnl_relatorioFinanceiro.add(jBtt_julho, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 90, -1));

        jBtt_marco.setText("MARÇO");
        jpnl_relatorioFinanceiro.add(jBtt_marco, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 90, -1));

        jCbox_ano.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2024", "2023", "2022", "2021", "2020" }));
        jpnl_relatorioFinanceiro.add(jCbox_ano, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, -1));

        jBtt_janeiro.setText("JANEIRO");
        jpnl_relatorioFinanceiro.add(jBtt_janeiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 90, -1));

        jBtt_maio.setText("MAIO");
        jpnl_relatorioFinanceiro.add(jBtt_maio, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 90, -1));

        jLl_relatorio.setText("RELATÓRIO MÊS:");

        jlbl_mes.setText("JANEIRO");

        javax.swing.GroupLayout jpn_relatorioLayout = new javax.swing.GroupLayout(jpn_relatorio);
        jpn_relatorio.setLayout(jpn_relatorioLayout);
        jpn_relatorioLayout.setHorizontalGroup(
            jpn_relatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_relatorioLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLl_relatorio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbl_mes, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jpn_relatorioLayout.setVerticalGroup(
            jpn_relatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpn_relatorioLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jpn_relatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLl_relatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbl_mes)))
        );

        jpnl_relatorioFinanceiro.add(jpn_relatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 270, 20));

        jBtt_setembro.setText("SETEMBRO");
        jpnl_relatorioFinanceiro.add(jBtt_setembro, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, 90, -1));

        jtl_produto_mes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", null, null},
                {"2", null, null},
                {"3", null, null},
                {"4", null, null},
                {"5", null, null},
                {"6", null, null},
                {"7", null, null},
                {"8", null, null},
                {"9", null, null},
                {"10", null, null},
                {"11", null, null},
                {"12", null, null},
                {"13", null, null},
                {"14", null, null},
                {"15", null, null},
                {"16", null, null},
                {"17", null, null},
                {"18", null, null},
                {"19", null, null},
                {"20", null, null},
                {"21", null, null},
                {"22", null, null}
            },
            new String [] {
                "CODIGO", "PRODUTOS", "TOTAL"
            }
        ));
        jtl_produto_mes.setGridColor(new java.awt.Color(153, 255, 255));
        jtl_produto_mes.setSelectionBackground(new java.awt.Color(102, 204, 255));
        jtl_produto_mes.setSelectionForeground(new java.awt.Color(153, 255, 255));
        jScrollPane1.setViewportView(jtl_produto_mes);

        jpnl_relatorioFinanceiro.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 270, 290));

        jTabbedPane1.addTab("FINANCEIRO", jpnl_relatorioFinanceiro);

        jpnl_relatorioPerfomanceGeral.setOpaque(false);
        jpnl_relatorioPerfomanceGeral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("PERFORMANCE GERAL", jpnl_relatorioPerfomanceGeral);

        jpnl_relatorioIntercorrencia.setOpaque(false);
        jpnl_relatorioIntercorrencia.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtbl_relatorioIntercorrencia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"ATRASO NA ENTREGA", null, null},
                {"PEDIDO VEIO FALTANDO", null, null},
                {"CLIENTE NÃO ESTAVA NO LOCAL", null, null},
                {"PACOTE FURTADO", null, null},
                {"PROBLEMA NO VEÍCULO DE ENTREGA", null, null},
                {"FORÇAS MAIORES", null, null},
                {"PRODUTO EM FALTA", null, null},
                {"CLIENTE NÃO PAGOU", null, null},
                {"PROBLEMA PRCESSUAL", null, null},
                {"PROBLEMA TÉCNICO", null, null},
                {"MOTOBOY NÃO APARECEU", null, null},
                {"OUTROS", null, null}
            },
            new String [] {
                "INTERCORRÊNCIA RELATADA", "REINCIDÊNIA/QUANTIDADE", "GRAFICO DE PERDA GERAL"
            }
        ));
        jScrollPane3.setViewportView(jtbl_relatorioIntercorrencia);

        jpnl_relatorioIntercorrencia.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 720, 280));

        jlbl_filtroPeridoIntercorrencia.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlbl_filtroPeridoIntercorrencia.setForeground(new java.awt.Color(0, 0, 0));
        jlbl_filtroPeridoIntercorrencia.setText("FILTRAR PERÍODO:");
        jpnl_relatorioIntercorrencia.add(jlbl_filtroPeridoIntercorrencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jtxtf_periodoInicialIntercorrencia.setText("01/10/2024");
        jtxtf_periodoInicialIntercorrencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtf_periodoInicialIntercorrenciaActionPerformed(evt);
            }
        });
        jpnl_relatorioIntercorrencia.add(jtxtf_periodoInicialIntercorrencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 90, 30));

        jtxtf_periodoFinalIntercorrencia.setText("31/10/2024");
        jpnl_relatorioIntercorrencia.add(jtxtf_periodoFinalIntercorrencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 90, 30));

        jlbl_filtroPeridoIntercorrenciaAte.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlbl_filtroPeridoIntercorrenciaAte.setForeground(new java.awt.Color(0, 0, 0));
        jlbl_filtroPeridoIntercorrenciaAte.setText(" -");
        jpnl_relatorioIntercorrencia.add(jlbl_filtroPeridoIntercorrenciaAte, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 20, -1));

        jbtn_pesquisarFiltroIntercorrencia.setText("PESQUISAR");
        jpnl_relatorioIntercorrencia.add(jbtn_pesquisarFiltroIntercorrencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 110, 30));

        jTabbedPane1.addTab("INTERCORRÊNCIA", jpnl_relatorioIntercorrencia);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, 750, 450));

        jpnl_background.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jpnl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/background_produtos.png"))); // NOI18N
        jpnl_background.setToolTipText("");
        jpnl_background.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jpnl_background.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel1.add(jpnl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jlbl_produtosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jlbl_produtosFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jlbl_produtosFocusGained

    private void jtxtf_periodoInicialIntercorrenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtf_periodoInicialIntercorrenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtf_periodoInicialIntercorrenciaActionPerformed

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
            java.util.logging.Logger.getLogger(JFrame_Relatorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_Relatorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_Relatorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_Relatorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new JFrame_Relatorios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtt_abril;
    private javax.swing.JButton jBtt_agosto;
    private javax.swing.JButton jBtt_dezembro;
    private javax.swing.JButton jBtt_fevereiro;
    private javax.swing.JButton jBtt_janeiro;
    private javax.swing.JButton jBtt_julho;
    private javax.swing.JButton jBtt_junho;
    private javax.swing.JButton jBtt_maio;
    private javax.swing.JButton jBtt_marco;
    private javax.swing.JButton jBtt_novembro;
    private javax.swing.JButton jBtt_outubro;
    private javax.swing.JButton jBtt_setembro;
    private javax.swing.JComboBox<String> jCbox_ano;
    private javax.swing.JLabel jLl_relatorio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbtn_pesquisarFiltroIntercorrencia;
    private javax.swing.JLabel jlbl_clientes;
    private javax.swing.JLabel jlbl_filtroPeridoIntercorrencia;
    private javax.swing.JLabel jlbl_filtroPeridoIntercorrenciaAte;
    private javax.swing.JLabel jlbl_mes;
    private javax.swing.JLabel jlbl_pedidos;
    private javax.swing.JLabel jlbl_produtos;
    private javax.swing.JLabel jlbl_relatorios;
    private javax.swing.JPanel jpn_relatorio;
    private javax.swing.JLabel jpnl_background;
    private javax.swing.JLabel jpnl_img_etiqueta;
    private javax.swing.JPanel jpnl_relatorioFinanceiro;
    private javax.swing.JPanel jpnl_relatorioIntercorrencia;
    private javax.swing.JPanel jpnl_relatorioPerfomanceGeral;
    private javax.swing.JPanel jpnl_sideMenu;
    private javax.swing.JToolBar jtb_linhaBranca;
    private javax.swing.JTable jtbl_relatorioIntercorrencia;
    private javax.swing.JTable jtl_anual;
    private javax.swing.JTable jtl_produto_mes;
    private javax.swing.JTextField jtxtf_periodoFinalIntercorrencia;
    private javax.swing.JTextField jtxtf_periodoInicialIntercorrencia;
    // End of variables declaration//GEN-END:variables
}
