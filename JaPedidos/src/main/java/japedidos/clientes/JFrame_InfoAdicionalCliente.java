/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package japedidos.clientes;

/**
 *
 * @author t.baiense
 */
public class JFrame_InfoAdicionalCliente extends javax.swing.JFrame {

    /**
     * Creates new form InfoAdicionalCliente
     */
    public JFrame_InfoAdicionalCliente() {
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

        jpnl_principal = new javax.swing.JPanel();
        jtpn_tipoCliente = new javax.swing.JTabbedPane();
        jpnl_clientePF = new javax.swing.JPanel();
        jlbl_nomePF = new javax.swing.JLabel();
        jtxtf_nomePF = new javax.swing.JTextField();
        jlbl_cpf = new javax.swing.JLabel();
        jtxtf_cpf = new javax.swing.JTextField();
        jpnl_clientePJ = new javax.swing.JPanel();
        jlbl_nomeFantasiaPJ = new javax.swing.JLabel();
        jtxtf_nomeFantasiaPJ = new javax.swing.JTextField();
        jlbl_cnpj = new javax.swing.JLabel();
        jtxtf_cnpj = new javax.swing.JTextField();
        jlbl_razaoSocialPJ = new javax.swing.JLabel();
        jtxtf_razaoSocialPJ = new javax.swing.JTextField();
        jbtn_confirmar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Informação adicional do cliente");

        jpnl_clientePF.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlbl_nomePF.setText("Nome do cliente solicitante");
        jpnl_clientePF.add(jlbl_nomePF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

        jtxtf_nomePF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtf_nomePFActionPerformed(evt);
            }
        });
        jpnl_clientePF.add(jtxtf_nomePF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 224, -1));

        jlbl_cpf.setText("CPF");
        jpnl_clientePF.add(jlbl_cpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, -1, -1));

        jtxtf_cpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtf_cpfActionPerformed(evt);
            }
        });
        jpnl_clientePF.add(jtxtf_cpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 224, -1));

        jtpn_tipoCliente.addTab("Pessoa Física", jpnl_clientePF);

        jpnl_clientePJ.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlbl_nomeFantasiaPJ.setText("Nome fantasia");
        jpnl_clientePJ.add(jlbl_nomeFantasiaPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 124, -1));

        jtxtf_nomeFantasiaPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtf_nomeFantasiaPJActionPerformed(evt);
            }
        });
        jpnl_clientePJ.add(jtxtf_nomeFantasiaPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 224, -1));

        jlbl_cnpj.setText("CNPJ");
        jpnl_clientePJ.add(jlbl_cnpj, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, -1, -1));

        jtxtf_cnpj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtf_cnpjActionPerformed(evt);
            }
        });
        jpnl_clientePJ.add(jtxtf_cnpj, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 224, -1));

        jlbl_razaoSocialPJ.setText("Razão social");
        jpnl_clientePJ.add(jlbl_razaoSocialPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 124, -1));

        jtxtf_razaoSocialPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtf_razaoSocialPJActionPerformed(evt);
            }
        });
        jpnl_clientePJ.add(jtxtf_razaoSocialPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 224, -1));

        jtpn_tipoCliente.addTab("Pessoa Jurídica", jpnl_clientePJ);

        jbtn_confirmar.setText("Confirmar");
        jbtn_confirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_confirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnl_principalLayout = new javax.swing.GroupLayout(jpnl_principal);
        jpnl_principal.setLayout(jpnl_principalLayout);
        jpnl_principalLayout.setHorizontalGroup(
            jpnl_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnl_principalLayout.createSequentialGroup()
                .addComponent(jtpn_tipoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jpnl_principalLayout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(jbtn_confirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnl_principalLayout.setVerticalGroup(
            jpnl_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnl_principalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtpn_tipoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtn_confirmar)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnl_principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnl_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtf_nomePFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtf_nomePFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtf_nomePFActionPerformed

    private void jtxtf_cpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtf_cpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtf_cpfActionPerformed

    private void jbtn_confirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_confirmarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtn_confirmarActionPerformed

    private void jtxtf_nomeFantasiaPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtf_nomeFantasiaPJActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtf_nomeFantasiaPJActionPerformed

    private void jtxtf_cnpjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtf_cnpjActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtf_cnpjActionPerformed

    private void jtxtf_razaoSocialPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtf_razaoSocialPJActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtf_razaoSocialPJActionPerformed

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
            java.util.logging.Logger.getLogger(JFrame_InfoAdicionalCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_InfoAdicionalCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_InfoAdicionalCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_InfoAdicionalCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_InfoAdicionalCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtn_confirmar;
    private javax.swing.JLabel jlbl_cnpj;
    private javax.swing.JLabel jlbl_cpf;
    private javax.swing.JLabel jlbl_nomeFantasiaPJ;
    private javax.swing.JLabel jlbl_nomePF;
    private javax.swing.JLabel jlbl_razaoSocialPJ;
    private javax.swing.JPanel jpnl_clientePF;
    private javax.swing.JPanel jpnl_clientePJ;
    private javax.swing.JPanel jpnl_principal;
    private javax.swing.JTabbedPane jtpn_tipoCliente;
    private javax.swing.JTextField jtxtf_cnpj;
    private javax.swing.JTextField jtxtf_cpf;
    private javax.swing.JTextField jtxtf_nomeFantasiaPJ;
    private javax.swing.JTextField jtxtf_nomePF;
    private javax.swing.JTextField jtxtf_razaoSocialPJ;
    // End of variables declaration//GEN-END:variables
}
