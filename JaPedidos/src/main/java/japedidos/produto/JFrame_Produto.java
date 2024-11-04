package japedidos.produto;

import japedidos.bd.BD;
import japedidos.exception.*;

/**
 *
 * @author t.baiense
 */
public class JFrame_Produto extends javax.swing.JFrame {

    /**
     * Creates new form JFrame_Produto
     */
    public JFrame_Produto() {
        initComponents();
        
        resetErrorLabels();
        
        jTable_Produto.getTable().getSelectionModel().addListSelectionListener((e) -> {
            int selectedRow = jTable_Produto.getTable().getSelectedRow();
            Produto selectedProduto;
            
            if (selectedRow != -1) {
                selectedProduto = jTable_Produto.getModel().getRow(selectedRow);
                setFieldsInfo(selectedProduto);
            } else {
                clearFieldsInfo();
            }
            resetErrorLabels();
        });
        Unidade[] unidades = BD.Unidade.selectAll();
        if (unidades != null) {
            for (Unidade u : unidades) {
                jcmb_unidade.addItem(u);
            }
        }
        
        Categoria[] categorias = BD.Categoria.selectAll();
        if (categorias != null) {
            for (Categoria c : categorias) {
                jcmb_categoria.addItem(c);
            }
        }
        jTable_Produto.getModel().refresh();
    }
    
    public void setFieldsInfo(Produto p) {
        if (p != null) {
            // Definição da categoria
            Categoria catProduto = p.getCategoria();
            if (!catProduto.isNew()) {
                int catCount = jcmb_categoria.getItemCount();
                for (int c = 0; c < catCount; c++) {
                    Categoria catVerificada = jcmb_categoria.getItemAt(c);
                    if (catVerificada.equals(catProduto)) {
                        jcmb_categoria.setSelectedIndex(c);
                    }
                }
            }
            
            // Definição de unidade
            Unidade unidProduto = p.getUnidade();
            if (!unidProduto.isNew()) {
                int unidCount = jcmb_unidade.getItemCount();
                for (int u = 0; u < unidCount; u++) {
                    Unidade unidVerificada = jcmb_unidade.getItemAt(u);
                    if (unidVerificada.equals(unidProduto)) {
                        jcmb_unidade.setSelectedIndex(u);
                    }
                }
            }
            
            jtxtf_precoCusto.setText(String.valueOf(p.getPrecoCusto()));
            jtxtf_precoVenda.setText(String.valueOf(p.getPrecoVenda()));
            
            String strId;
            if (p.isNew()) {
                strId = null;
            } else {
                strId = String.valueOf(p.getId());
            }
            jtxtf_id.setText(strId);
            
            jtxtf_nomeProduto.setText(p.getNome());
            jchb_ativo.setSelected(p.isAtivo());
        }
    }
    
    public void resetErrorLabels() {
        jlbl_erro_categoria.setVisible(false);
        jlbl_erro_id.setVisible(false);
        jlbl_erro_nome.setVisible(false);
        jlbl_erro_precoCusto.setVisible(false);
        jlbl_erro_precoVenda.setVisible(false);
        jlbl_erro_unidade.setVisible(false);
    }
    
    public void clearFieldsInfo() {
        if (jcmb_categoria.getItemCount() > 0) {
            jcmb_categoria.setSelectedIndex(0);
        }
        
        if (jcmb_unidade.getItemCount() > 0) {
            jcmb_unidade.setSelectedIndex(0);
        }
        
        jtxtf_precoCusto.setText(null);
        jtxtf_precoVenda.setText(null);
        jtxtf_id.setText(null);
        jtxtf_nomeProduto.setText(null);
        jchb_ativo.setSelected(false);
    }

    
    public Produto getFieldsInfo() {
        resetErrorLabels();
        
        IllegalArgumentsException exs = new IllegalArgumentsException();
        Produto p = null;
        String nome, strPrecoCusto, strPrecoVenda, strId;
        Unidade unidade;
        Categoria categoria;
        int id;
        double precoCusto, precoVenda;
        boolean ativo;
        
        id = 1;
        nome = strPrecoCusto = strPrecoVenda = strId = null;
        unidade = null;
        categoria = null;
        ativo = true;
        precoCusto = 0;
        precoVenda = 0;
        
        strId = jtxtf_id.getText().trim();
        if (!strId.isEmpty()) {
            try {
                id = Integer.valueOf(strId);
            } catch (NumberFormatException ex) {
                exs.addCause(new IllegalIdException("Formato de id inválido"));
            }
        }
        
        nome = jtxtf_nomeProduto.getText().trim();
        strPrecoCusto = jtxtf_precoCusto.getText().trim();
        if (!strPrecoCusto.isEmpty()) {
            try {
                precoCusto = Double.valueOf(strPrecoCusto);
            } catch (NumberFormatException ex) {
                exs.addCause(new IllegalPrecoCustoException("Formato de preço inválido"));
            }
        }
        
        strPrecoVenda = jtxtf_precoVenda.getText().trim();
        if (!strPrecoVenda.isEmpty()) {
            try {
                precoVenda = Double.valueOf(strPrecoVenda);
            } catch (NumberFormatException ex) {
                exs.addCause(new IllegalPrecoVendaException("Formato de preço inválido"));
            }
        }
        
        categoria = (Categoria)jcmb_categoria.getSelectedItem();
        unidade = (Unidade)jcmb_unidade.getSelectedItem();
        ativo = jchb_ativo.isSelected();
        
        Produto newProduto = null;
        try {
            newProduto = new Produto(id, nome, categoria, unidade, precoCusto, precoVenda);
            newProduto.setAtivo(ativo);
        } catch (IllegalArgumentsException newExs) {
            exs.addCause(newExs.getCauses());
        }
        
        if (exs.size() > 0) {
            for(Throwable ex : exs.getCauses()) {
                if (ex instanceof IllegalIdException) {
                    jlbl_erro_id.setText(ex.getMessage());
                    jlbl_erro_id.setVisible(true);
                } else if (ex instanceof IllegalNomeException) {
                    jlbl_erro_nome.setText(ex.getMessage());
                    jlbl_erro_nome.setVisible(true);
                } else if (ex instanceof IllegalCategoriaException) {
                    jlbl_erro_categoria.setText(ex.getMessage());
                    jlbl_erro_categoria.setVisible(true);
                } else if (ex instanceof IllegalUnidadeException) {
                    jlbl_erro_unidade.setText(ex.getMessage());
                    jlbl_erro_unidade.setVisible(true);
                } else if (ex instanceof IllegalPrecoCustoException) {
                    jlbl_erro_precoCusto.setText(ex.getMessage());
                    jlbl_erro_precoCusto.setVisible(true);
                } else if (ex instanceof IllegalPrecoVendaException) {
                    jlbl_erro_precoVenda.setText(ex.getMessage());
                    jlbl_erro_precoVenda.setVisible(true);
                }
            }
        } else if (newProduto != null) {
            p = newProduto;
        }
        return p;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTable_Produto = new japedidos.produto.JTable_Produto();
        jtxtf_precoCusto = new javax.swing.JTextField();
        jlbl_precoCusto = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtxtf_precoVenda = new javax.swing.JTextField();
        jcmb_categoria = new javax.swing.JComboBox<>();
        jcmb_unidade = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jlbl_id = new javax.swing.JLabel();
        jtxtf_id = new javax.swing.JTextField();
        jchb_ativo = new javax.swing.JCheckBox();
        jbtn_inserir = new javax.swing.JButton();
        jbtn_alterar = new javax.swing.JButton();
        jbtn_deletar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jtxtf_nomeProduto = new javax.swing.JTextField();
        jlbl_erro_precoVenda = new javax.swing.JLabel();
        jlbl_erro_id = new javax.swing.JLabel();
        jlbl_erro_categoria = new javax.swing.JLabel();
        jlbl_erro_unidade = new javax.swing.JLabel();
        jlbl_erro_precoCusto = new javax.swing.JLabel();
        jlbl_erro_nome = new javax.swing.JLabel();
        jbtn_limparSelecao = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jTable_Produto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, -1));
        getContentPane().add(jtxtf_precoCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 430, 100, -1));

        jlbl_precoCusto.setText("Preço custo");
        getContentPane().add(jlbl_precoCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 410, 80, -1));

        jLabel1.setText("Categoria");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 410, 70, -1));

        jLabel3.setText("Preço venda");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 410, 80, -1));
        getContentPane().add(jtxtf_precoVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 430, 110, -1));

        getContentPane().add(jcmb_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, 160, -1));

        getContentPane().add(jcmb_unidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, 150, -1));

        jLabel2.setText("Unidade");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 410, -1, -1));

        jlbl_id.setText("Id");
        getContentPane().add(jlbl_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 340, -1, 30));
        getContentPane().add(jtxtf_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, 70, -1));

        jchb_ativo.setText("Ativo");
        getContentPane().add(jchb_ativo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 340, -1, 30));

        jbtn_inserir.setText("Inserir");
        jbtn_inserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_inserirActionPerformed(evt);
            }
        });
        getContentPane().add(jbtn_inserir, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 510, 120, -1));

        jbtn_alterar.setText("Alterar");
        jbtn_alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_alterarActionPerformed(evt);
            }
        });
        getContentPane().add(jbtn_alterar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 510, 130, -1));

        jbtn_deletar.setText("Deletar");
        jbtn_deletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_deletarActionPerformed(evt);
            }
        });
        getContentPane().add(jbtn_deletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 510, 130, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 810, 20));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 810, 10));

        jLabel4.setText("Nome");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 340, -1, 30));
        getContentPane().add(jtxtf_nomeProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 340, 250, -1));

        jlbl_erro_precoVenda.setForeground(new java.awt.Color(204, 0, 51));
        jlbl_erro_precoVenda.setText("Mensagem de erro!");
        getContentPane().add(jlbl_erro_precoVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 460, -1, -1));

        jlbl_erro_id.setForeground(new java.awt.Color(204, 0, 51));
        jlbl_erro_id.setText("Mensagem de erro!");
        getContentPane().add(jlbl_erro_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, -1, -1));

        jlbl_erro_categoria.setForeground(new java.awt.Color(204, 0, 51));
        jlbl_erro_categoria.setText("Mensagem de erro!");
        getContentPane().add(jlbl_erro_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 460, -1, 20));

        jlbl_erro_unidade.setForeground(new java.awt.Color(204, 0, 51));
        jlbl_erro_unidade.setText("Mensagem de erro!");
        getContentPane().add(jlbl_erro_unidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 460, -1, -1));

        jlbl_erro_precoCusto.setForeground(new java.awt.Color(204, 0, 51));
        jlbl_erro_precoCusto.setText("Mensagem de erro!");
        getContentPane().add(jlbl_erro_precoCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 460, -1, -1));

        jlbl_erro_nome.setForeground(new java.awt.Color(204, 0, 51));
        jlbl_erro_nome.setText("Mensagem de erro!");
        getContentPane().add(jlbl_erro_nome, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 370, -1, -1));

        jbtn_limparSelecao.setText("Limpar seleção");
        jbtn_limparSelecao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_limparSelecaoActionPerformed(evt);
            }
        });
        getContentPane().add(jbtn_limparSelecao, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 330, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtn_limparSelecaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_limparSelecaoActionPerformed
        jTable_Produto.getTable().getSelectionModel().clearSelection();
    }//GEN-LAST:event_jbtn_limparSelecaoActionPerformed

    private void jbtn_inserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_inserirActionPerformed
        Produto recebido = getFieldsInfo();
        if (recebido != null) {
            BD.Produto.insert(recebido);
            jTable_Produto.getModel().refresh();
            clearFieldsInfo();
        }
    }//GEN-LAST:event_jbtn_inserirActionPerformed

    private void jbtn_alterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_alterarActionPerformed
        Produto recebido = getFieldsInfo();
        if (recebido != null) {
            BD.Produto.update(recebido);
            jTable_Produto.getModel().refresh();
            clearFieldsInfo();
        }
    }//GEN-LAST:event_jbtn_alterarActionPerformed

    private void jbtn_deletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_deletarActionPerformed
        Produto recebido = getFieldsInfo();
        if (recebido != null) {
            BD.Produto.delete(recebido);
            jTable_Produto.getModel().refresh();
            clearFieldsInfo();
        }
        
    }//GEN-LAST:event_jbtn_deletarActionPerformed

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
            java.util.logging.Logger.getLogger(JFrame_Produto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_Produto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_Produto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_Produto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_Produto().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private japedidos.produto.JTable_Produto jTable_Produto;
    private javax.swing.JButton jbtn_alterar;
    private javax.swing.JButton jbtn_deletar;
    private javax.swing.JButton jbtn_inserir;
    private javax.swing.JButton jbtn_limparSelecao;
    private javax.swing.JCheckBox jchb_ativo;
    private javax.swing.JComboBox<japedidos.produto.Categoria> jcmb_categoria;
    private javax.swing.JComboBox<japedidos.produto.Unidade> jcmb_unidade;
    private javax.swing.JLabel jlbl_erro_categoria;
    private javax.swing.JLabel jlbl_erro_id;
    private javax.swing.JLabel jlbl_erro_nome;
    private javax.swing.JLabel jlbl_erro_precoCusto;
    private javax.swing.JLabel jlbl_erro_precoVenda;
    private javax.swing.JLabel jlbl_erro_unidade;
    private javax.swing.JLabel jlbl_id;
    private javax.swing.JLabel jlbl_precoCusto;
    private javax.swing.JTextField jtxtf_id;
    private javax.swing.JTextField jtxtf_nomeProduto;
    private javax.swing.JTextField jtxtf_precoCusto;
    private javax.swing.JTextField jtxtf_precoVenda;
    // End of variables declaration//GEN-END:variables
}
