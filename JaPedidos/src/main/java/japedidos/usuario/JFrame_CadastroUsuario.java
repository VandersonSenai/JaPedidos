/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package japedidos.usuario;

/**
 *
 * @author t.baiense
 */
public class JFrame_CadastroUsuario extends javax.swing.JFrame {

    /**
     * Creates new form CadastroUsuario
     */
    public JFrame_CadastroUsuario() {
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

        jpnl_cadastro = new javax.swing.JPanel();
        jlbl_login = new javax.swing.JLabel();
        jlbl_senha = new javax.swing.JLabel();
        jlbl_banco = new javax.swing.JLabel();
        jtxtf_login = new javax.swing.JTextField();
        jpwf_senha = new javax.swing.JPasswordField();
        jtxtf_banco = new javax.swing.JTextField();
        jbtn_prosseguir = new javax.swing.JButton();
        jbtn_cancelar = new javax.swing.JButton();
        jpnl_btn_novo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(640, 480));

        jpnl_cadastro.setPreferredSize(new java.awt.Dimension(640, 480));

        jlbl_login.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbl_login.setText("Login");

        jlbl_senha.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbl_senha.setText("Senha");

        jlbl_banco.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbl_banco.setText("Banco");

        jtxtf_login.setText("admin");

        jpwf_senha.setText("admin");
        jpwf_senha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpwf_senhaActionPerformed(evt);
            }
        });

        jtxtf_banco.setText("localhost:3306/japedidos");
        jtxtf_banco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtf_bancoActionPerformed(evt);
            }
        });

        jbtn_prosseguir.setText("Prosseguir");

        jbtn_cancelar.setText("Cancelar");

        jpnl_btn_novo.setIcon(new javax.swing.ImageIcon("C:\\SENAI\\TECHNIGHT\\JaPedidos\\JaPedidos\\src\\main\\java\\japedidos\\imagens\\btn_novo_padrao.png")); // NOI18N
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

        javax.swing.GroupLayout jpnl_cadastroLayout = new javax.swing.GroupLayout(jpnl_cadastro);
        jpnl_cadastro.setLayout(jpnl_cadastroLayout);
        jpnl_cadastroLayout.setHorizontalGroup(
            jpnl_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnl_cadastroLayout.createSequentialGroup()
                .addGap(196, 196, 196)
                .addGroup(jpnl_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnl_cadastroLayout.createSequentialGroup()
                        .addComponent(jlbl_login)
                        .addGap(21, 21, 21)
                        .addComponent(jtxtf_login))
                    .addGroup(jpnl_cadastroLayout.createSequentialGroup()
                        .addComponent(jlbl_senha)
                        .addGap(19, 19, 19)
                        .addComponent(jpwf_senha))
                    .addGroup(jpnl_cadastroLayout.createSequentialGroup()
                        .addComponent(jlbl_banco)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtf_banco))
                    .addGroup(jpnl_cadastroLayout.createSequentialGroup()
                        .addComponent(jbtn_prosseguir, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtn_cancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)))
                .addGap(202, 202, 202))
            .addGroup(jpnl_cadastroLayout.createSequentialGroup()
                .addGap(275, 275, 275)
                .addComponent(jpnl_btn_novo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnl_cadastroLayout.setVerticalGroup(
            jpnl_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnl_cadastroLayout.createSequentialGroup()
<<<<<<< HEAD:JaPedidos/src/main/java/japedidos/usuario/CadastroUsuario.java
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jpnl_btn_novo)
                .addGap(52, 52, 52)
=======
                .addContainerGap(127, Short.MAX_VALUE)
>>>>>>> 49c93973be3150ee1be360e013b106c549d3087a:JaPedidos/src/main/java/japedidos/usuario/JFrame_CadastroUsuario.java
                .addGroup(jpnl_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbl_login)
                    .addComponent(jtxtf_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jpnl_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbl_senha)
                    .addComponent(jpwf_senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jpnl_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbl_banco)
                    .addComponent(jtxtf_banco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jpnl_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtn_prosseguir)
                    .addComponent(jbtn_cancelar))
                .addGap(131, 131, 131))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnl_cadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnl_cadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtf_bancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtf_bancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtf_bancoActionPerformed

    private void jpwf_senhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpwf_senhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jpwf_senhaActionPerformed

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
            java.util.logging.Logger.getLogger(JFrame_CadastroUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_CadastroUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_CadastroUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_CadastroUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_CadastroUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtn_cancelar;
    private javax.swing.JButton jbtn_prosseguir;
    private javax.swing.JLabel jlbl_banco;
    private javax.swing.JLabel jlbl_login;
    private javax.swing.JLabel jlbl_senha;
    private javax.swing.JLabel jpnl_btn_novo;
    private javax.swing.JPanel jpnl_cadastro;
    private javax.swing.JPasswordField jpwf_senha;
    private javax.swing.JTextField jtxtf_banco;
    private javax.swing.JTextField jtxtf_login;
    // End of variables declaration//GEN-END:variables
}