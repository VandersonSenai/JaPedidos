package japedidos.produto;

import japedidos.bd.BD;
import javax.swing.JOptionPane;

/**
 *
 * @author thiago
 */
public class JFrame_Unidade extends javax.swing.JFrame {


    public JFrame_Unidade() {
        initComponents();
        jtbl_unidade.getJTable().getSelectionModel().addListSelectionListener((e) -> {
            int selRow = jtbl_unidade.getJTable().getSelectedRow();
            
            if (selRow == -1) {
                clearFieldsInfo();
                return;
            }
            
            setFieldsInfo(jtbl_unidade.getModel().getRow(selRow));
        });
        jtbl_unidade.getModel().refreshRows();
    }

    public void setFieldsInfo(Unidade u) {
        if (u == null) {
            clearFieldsInfo();
            return;
        }
        
        jtxtf_id.setText(String.valueOf(u.getId()));
        jtxtf_nome.setText(u.getNome());
        jtxtf_abreviacao.setText(u.getAbreviacao());
    }
    
    public void clearFieldsInfo() {
        jtxtf_id.setText(null);
        jtxtf_nome.setText(null);
        jtxtf_abreviacao.setText(null);
    }
    
    public Unidade getFieldsInfo() {
        String nome = jtxtf_nome.getText().trim();
        String abreviacao = jtxtf_abreviacao.getText().trim().toUpperCase();
        
        Unidade unidade;
        try {
            int id = Integer.valueOf(jtxtf_id.getText().trim());
            unidade = new Unidade(id, nome, abreviacao);
        } catch (NumberFormatException e) {
            unidade = new Unidade(nome, abreviacao);
        }
        
        return unidade;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        jtbl_unidade = new japedidos.produto.JTable_Unidade();
        jSeparator = new javax.swing.JSeparator();
        jbtn_inserir = new javax.swing.JButton();
        jbtn_alterar = new javax.swing.JButton();
        jbtn_excluir = new javax.swing.JButton();
        jlbl_id = new javax.swing.JLabel();
        jtxtf_id = new javax.swing.JTextField();
        jlbl_nome = new javax.swing.JLabel();
        jtxtf_abreviacao = new javax.swing.JTextField();
        jtxtf_nome = new javax.swing.JTextField();
        jlbl_abreviacao = new javax.swing.JLabel();
        jbtn_limparSelecao = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Unidade");

        jPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel.add(jtbl_unidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 170));
        jPanel.add(jSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 460, 10));

        jbtn_inserir.setText("Inserir");
        jbtn_inserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_inserirActionPerformed(evt);
            }
        });
        jPanel.add(jbtn_inserir, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 110, -1));

        jbtn_alterar.setText("Alterar");
        jbtn_alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_alterarActionPerformed(evt);
            }
        });
        jPanel.add(jbtn_alterar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 310, 110, -1));

        jbtn_excluir.setText("Excluir");
        jbtn_excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_excluirActionPerformed(evt);
            }
        });
        jPanel.add(jbtn_excluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, 110, -1));

        jlbl_id.setText("Id");
        jPanel.add(jlbl_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));
        jPanel.add(jtxtf_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 110, -1));

        jlbl_nome.setText("Nome");
        jPanel.add(jlbl_nome, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, -1, -1));
        jPanel.add(jtxtf_abreviacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 250, 110, -1));
        jPanel.add(jtxtf_nome, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 150, -1));

        jlbl_abreviacao.setText("Abreviação");
        jPanel.add(jlbl_abreviacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 230, -1, -1));

        jbtn_limparSelecao.setText("Limpar seleção");
        jbtn_limparSelecao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_limparSelecaoActionPerformed(evt);
            }
        });
        jPanel.add(jbtn_limparSelecao, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, -1, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtn_inserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_inserirActionPerformed

        try {
            Unidade unidade = getFieldsInfo();
            if (unidade == null) {
                return;
            }

            if (BD.Unidade.insert(unidade) > 0) {
                jtbl_unidade.getModel().refreshRows();
                clearFieldsInfo();
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(rootPane, "Não foi possível inserir a nova unidade. As informações fornecidas são inválidas");
        }
    }//GEN-LAST:event_jbtn_inserirActionPerformed

    private void jbtn_alterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_alterarActionPerformed
        try {
            Unidade unidade = getFieldsInfo();
            if (unidade == null || unidade.getId() == -1) {
                return;
            }

            if (BD.Unidade.update(unidade) == 0) {
                JOptionPane.showMessageDialog(null, "O item especificado não foi encontrado.");
                clearFieldsInfo();
            } else {
                jtbl_unidade.getModel().refreshRows();
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(rootPane, "Não foi possível alterar a unidade. As informações fornecidas são inválidas");
        }
    }//GEN-LAST:event_jbtn_alterarActionPerformed

    private void jbtn_excluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_excluirActionPerformed
        try {
            Unidade unidade = getFieldsInfo();
            if (unidade == null || unidade.getId() == -1) {
                return;
            }

            if (BD.Unidade.delete(unidade) == 0) {
                JOptionPane.showMessageDialog(null, "O item especificado não foi encontrado.");
            } else {
                clearFieldsInfo();
                jtbl_unidade.getModel().refreshRows();
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(rootPane, "Não foi possível excluir a unidade. As informações fornecidas são inválidas");
        }
    }//GEN-LAST:event_jbtn_excluirActionPerformed

    private void jbtn_limparSelecaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_limparSelecaoActionPerformed
        clearFieldsInfo();
        jtbl_unidade.getJTable().getSelectionModel().clearSelection();
    }//GEN-LAST:event_jbtn_limparSelecaoActionPerformed

    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(JFrame_Unidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_Unidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_Unidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_Unidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_Unidade().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel;
    private javax.swing.JSeparator jSeparator;
    private javax.swing.JButton jbtn_alterar;
    private javax.swing.JButton jbtn_excluir;
    private javax.swing.JButton jbtn_inserir;
    private javax.swing.JButton jbtn_limparSelecao;
    private javax.swing.JLabel jlbl_abreviacao;
    private javax.swing.JLabel jlbl_id;
    private javax.swing.JLabel jlbl_nome;
    private japedidos.produto.JTable_Unidade jtbl_unidade;
    private javax.swing.JTextField jtxtf_abreviacao;
    private javax.swing.JTextField jtxtf_id;
    private javax.swing.JTextField jtxtf_nome;
    // End of variables declaration//GEN-END:variables
}
