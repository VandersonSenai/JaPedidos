/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package japedidos.usuario;

/**
 *
 * @author v.gomes
 */
public class JFrame_LoginUsuario extends javax.swing.JFrame {

    /**
     * Creates new form LoginUsuario
     */
    public JFrame_LoginUsuario() {
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
        jlbl_barra_sup = new javax.swing.JLabel();
        jtxtf_login = new javax.swing.JTextField();
        jpwf_senha = new javax.swing.JPasswordField();
        jlbl_btn_config = new javax.swing.JLabel();
        jlbl_txt_flogin1 = new javax.swing.JLabel();
        jlbl_txt_flogin = new javax.swing.JLabel();
        jlbl_btn_entrar = new javax.swing.JLabel();
        jlbl_background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(640, 480));
        setMinimumSize(new java.awt.Dimension(640, 480));
        setSize(new java.awt.Dimension(640, 480));

        jPanel1.setMaximumSize(new java.awt.Dimension(640, 480));
        jPanel1.setMinimumSize(new java.awt.Dimension(640, 480));
        jPanel1.setPreferredSize(new java.awt.Dimension(640, 480));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlbl_barra_sup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Barra superior.png"))); // NOI18N
        jPanel1.add(jlbl_barra_sup, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 50));

        jtxtf_login.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jtxtf_login.setForeground(new java.awt.Color(0, 74, 173));
        jtxtf_login.setText("Nome do usuário");
        jtxtf_login.setBorder(null);
        jtxtf_login.setMargin(new java.awt.Insets(10, 10, 10, 18));
        jtxtf_login.setOpaque(false);
        jtxtf_login.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jtxtf_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtf_loginActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtf_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 240, 40));

        jpwf_senha.setForeground(new java.awt.Color(0, 74, 173));
        jpwf_senha.setText("Senha do Usuário");
        jpwf_senha.setBorder(null);
        jpwf_senha.setMargin(new java.awt.Insets(10, 10, 10, 10));
        jpwf_senha.setMaximumSize(new java.awt.Dimension(260, 40));
        jpwf_senha.setMinimumSize(new java.awt.Dimension(260, 40));
        jpwf_senha.setOpaque(false);
        jpwf_senha.setPreferredSize(new java.awt.Dimension(260, 40));
        jPanel1.add(jpwf_senha, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 240, 40));

        jlbl_btn_config.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bt_config.png"))); // NOI18N
        jlbl_btn_config.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_btn_configMouseClicked(evt);
            }
        });
        jPanel1.add(jlbl_btn_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 370, -1, -1));

        jlbl_txt_flogin1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/text_box_login.png"))); // NOI18N
        jPanel1.add(jlbl_txt_flogin1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, -1, -1));

        jlbl_txt_flogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/text_box_login.png"))); // NOI18N
        jPanel1.add(jlbl_txt_flogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, -1, -1));

        jlbl_btn_entrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bnt_entrar.png"))); // NOI18N
        jPanel1.add(jlbl_btn_entrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 360, -1, -1));

        jlbl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bg_login.png"))); // NOI18N
        jPanel1.add(jlbl_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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

    private void jtxtf_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtf_loginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtf_loginActionPerformed

    private void jlbl_btn_configMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_configMouseClicked
        japedidos.bd.JFrame_ConfiguracaoBanco frame = new japedidos.bd.JFrame_ConfiguracaoBanco();
        frame.setVisible(true);
    }//GEN-LAST:event_jlbl_btn_configMouseClicked

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
            java.util.logging.Logger.getLogger(JFrame_LoginUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_LoginUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_LoginUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_LoginUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_LoginUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jlbl_background;
    private javax.swing.JLabel jlbl_barra_sup;
    private javax.swing.JLabel jlbl_btn_config;
    private javax.swing.JLabel jlbl_btn_entrar;
    private javax.swing.JLabel jlbl_txt_flogin;
    private javax.swing.JLabel jlbl_txt_flogin1;
    private javax.swing.JPasswordField jpwf_senha;
    private javax.swing.JTextField jtxtf_login;
    // End of variables declaration//GEN-END:variables
}
