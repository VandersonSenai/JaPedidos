/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package japedidos.produto;

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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTable_Produto1 = new japedidos.produto.JTable_Produto();
        jlbl_id = new javax.swing.JLabel();
        jtxtf_id = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jcmb_unidade = new javax.swing.JComboBox<>();
        jcmb_categoria = new javax.swing.JComboBox<>();
        jlbl_precoCusto = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtxtf_precoCusto = new javax.swing.JTextField();
        jtxtf_precoVenda = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jTable_Produto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, -1));

        jlbl_id.setText("Id");
        getContentPane().add(jlbl_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 410, -1, 20));
        getContentPane().add(jtxtf_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 410, 70, -1));

        jLabel1.setText("Categoria");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 70, -1));

        jLabel2.setText("Unidade");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 340, -1, -1));

        jcmb_unidade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jcmb_unidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 360, 160, -1));

        jcmb_categoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jcmb_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 160, -1));

        jlbl_precoCusto.setText("Preço custo");
        getContentPane().add(jlbl_precoCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 340, 80, -1));

        jLabel3.setText("Preço venda");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 340, 80, -1));
        getContentPane().add(jtxtf_precoCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 360, 130, -1));
        getContentPane().add(jtxtf_precoVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 360, 120, -1));

        jCheckBox1.setText("Ativo");
        getContentPane().add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 410, -1, 30));

        jButton1.setText("Inserir");
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 470, 90, -1));

        jButton2.setText("Alterar");
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 470, 90, -1));

        jButton3.setText("Deletar");
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 470, 90, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 690, 10));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 690, 10));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private japedidos.produto.JTable_Produto jTable_Produto1;
    private javax.swing.JComboBox<String> jcmb_categoria;
    private javax.swing.JComboBox<String> jcmb_unidade;
    private javax.swing.JLabel jlbl_id;
    private javax.swing.JLabel jlbl_precoCusto;
    private javax.swing.JTextField jtxtf_id;
    private javax.swing.JTextField jtxtf_precoCusto;
    private javax.swing.JTextField jtxtf_precoVenda;
    // End of variables declaration//GEN-END:variables
}
