/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package japedidos.bd;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author t.baiense
 */
public class JFrame_ConfiguracaoBanco extends javax.swing.JFrame {

    private JFrame opener;
    /**
     * Creates new form JFrame_ConfiguracaoBanco
     */
    public JFrame_ConfiguracaoBanco(JFrame opener) {
        initComponents();
        this.opener = opener;
        this.jtxtf_login.setText(BD.USER);
        this.jpwdf_senha.setText(BD.USER_PWD);
        this.jtxtf_string_banco.setText(String.format("%s:%s/%s",BD.IP, BD.PORT, BD.NAME));
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
        jtxtf_login = new javax.swing.JTextField();
        jtxtf_string_banco = new javax.swing.JTextField();
        jpwdf_senha = new javax.swing.JPasswordField();
        jlbl_btn_prosseguir = new javax.swing.JLabel();
        jlbl_btn_cancelar = new javax.swing.JLabel();
        jlbl_background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JaPedidos - Configurações de conexão");

        jPanel1.setMaximumSize(new java.awt.Dimension(640, 480));
        jPanel1.setMinimumSize(new java.awt.Dimension(640, 480));
        jPanel1.setPreferredSize(new java.awt.Dimension(640, 480));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtxtf_login.setFont(jtxtf_login.getFont().deriveFont((float)14));
        jtxtf_login.setForeground(new java.awt.Color(57, 67, 40));
        jtxtf_login.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jtxtf_login.setOpaque(false);
        jtxtf_login.setBackground(new java.awt.Color(0,0,0,0));
        jPanel1.add(jtxtf_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 190, 50));

        jtxtf_string_banco.setFont(jtxtf_string_banco.getFont().deriveFont((float)14));
        jtxtf_string_banco.setForeground(new java.awt.Color(57, 67, 40));
        jtxtf_string_banco.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jtxtf_string_banco.setOpaque(false);
        jtxtf_string_banco.setBackground(new java.awt.Color(0,0,0,0));
        jPanel1.add(jtxtf_string_banco, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 270, 310, 50));

        jpwdf_senha.setFont(jpwdf_senha.getFont().deriveFont((float)14));
        jpwdf_senha.setForeground(new java.awt.Color(57, 67, 40));
        jpwdf_senha.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jpwdf_senha.setOpaque(false);
        jpwdf_senha.setBackground(new java.awt.Color(0,0,0,0));
        jPanel1.add(jpwdf_senha, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 190, 60));

        jlbl_btn_prosseguir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_prosseguir.png"))); // NOI18N
        jlbl_btn_prosseguir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_btn_prosseguirMouseClicked(evt);
            }
        });
        jPanel1.add(jlbl_btn_prosseguir, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 340, -1, -1));

        jlbl_btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_cancelar.png"))); // NOI18N
        jlbl_btn_cancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_btn_cancelarMouseClicked(evt);
            }
        });
        jPanel1.add(jlbl_btn_cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 340, -1, -1));

        jlbl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tela_config_banco.png"))); // NOI18N
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
    }// </editor-fold>//GEN-END:initComponents

    private void jlbl_btn_cancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_cancelarMouseClicked
        this.setVisible(false);
        int x = this.getX() + this.getWidth() / 2 - opener.getWidth() / 2;
        int y = this.getY() + this.getHeight()/ 2 - opener.getHeight() / 2;
        opener.setLocation(x, y);
        opener.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jlbl_btn_cancelarMouseClicked

    private void jlbl_btn_prosseguirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_prosseguirMouseClicked
        String entradaLogin, entradaSenha, entradaBanco;
        entradaLogin = jtxtf_login.getText().trim();
        entradaSenha = jpwdf_senha.getText();
        entradaBanco = jtxtf_string_banco.getText();
        
        if (!entradaLogin.isEmpty() && !entradaSenha.isBlank() && !entradaBanco.isEmpty()) {
            boolean ip = false, port = false;
            StringBuilder builderIp, builderPort, builderBd;
            builderIp = new StringBuilder();
            builderPort = new StringBuilder();
            builderBd = new StringBuilder();
            
            // Pegar ip
            int c;
            for (c = 0; c < entradaBanco.length(); c++) {
                char atual = entradaBanco.charAt(c);
                if (atual == ':' && !builderIp.isEmpty()) {
                    ip = true;
                    c++;
                    break;
                } else {
                    builderIp.append(atual);
                }
            }
            // Pegar porta
            if (ip) {
                for (; c < entradaBanco.length(); c++) {
                    char atual = entradaBanco.charAt(c);
                    if (atual == '/' && !builderPort.isEmpty()) {
                        port = true;
                        c++;
                        break;
                    } else {
                        builderPort.append(atual);
                    }
                }
                // Pegar banco
                if (port) {
                    for (; c < entradaBanco.length(); c++) {
                        char atual = entradaBanco.charAt(c);
                            builderBd.append(atual);
                    }
                }
            }
            String ipStr, portStr, bdStr;
            ipStr = builderIp.toString().trim();
            portStr = builderPort.toString().trim();
            bdStr = builderBd.toString().trim();
            
            if (ip && port && !ipStr.isEmpty() && !portStr.isEmpty() && !bdStr.isEmpty()) {
                String connStr = BD.getConnectionString(ipStr, portStr, bdStr);
                try {
                    BD.tryConnection(connStr, entradaLogin, entradaSenha);
                    // Setar info na classe bd
                    BD.setConnectionUser(entradaLogin, entradaSenha);
                    BD.setConnectionString(ipStr, portStr, bdStr);
                    JOptionPane.showMessageDialog(null, "Configuração de conexão atualizada.", "Conexão bem sucedida", JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);
                    int x = this.getX() + this.getWidth() / 2 - opener.getWidth() / 2;
                    int y = this.getY() + this.getHeight()/ 2 - opener.getHeight() / 2;
                    opener.setLocation(x, y);
                    opener.setVisible(true);
                    this.dispose();
                    return;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Não foi possível conectar ao banco.\n\nMotivo: " + ex.getMessage() + "\n\nVerifique as configurações e tente novamente.", "Conexão com o banco de dados falhou", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        }
    }//GEN-LAST:event_jlbl_btn_prosseguirMouseClicked

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
            java.util.logging.Logger.getLogger(JFrame_ConfiguracaoBanco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_ConfiguracaoBanco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_ConfiguracaoBanco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_ConfiguracaoBanco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_ConfiguracaoBanco(new JFrame()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jlbl_background;
    private javax.swing.JLabel jlbl_btn_cancelar;
    private javax.swing.JLabel jlbl_btn_prosseguir;
    private javax.swing.JPasswordField jpwdf_senha;
    private javax.swing.JTextField jtxtf_login;
    private javax.swing.JTextField jtxtf_string_banco;
    // End of variables declaration//GEN-END:variables
}
