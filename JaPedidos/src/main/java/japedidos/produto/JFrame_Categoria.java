/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package japedidos.produto;

import japedidos.AccessController;
import japedidos.bd.BD;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTable;
import javax.swing.JOptionPane;
/**
 *
 * @author thiago
 */
public class JFrame_Categoria extends javax.swing.JFrame {

    /**
     * Creates new form JFrame_Categoria
     */
    public JFrame_Categoria() {
        if (BD.isAccessible()) {
            AccessController.verificarLogin();
        } else {
            System.exit(0);
        }
        initComponents();
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        jtbl_categoria.getModel().fillRows(BD.Categoria.selectAll());
        
        JTable table = jtbl_categoria.getJTable();
        table.getSelectionModel().addListSelectionListener((e) -> {
            int selRow = table.getSelectedRow();
            if (selRow != -1) {
                Categoria categoriaSelecionada = jtbl_categoria.getModel().getRow(selRow);
                this.setFieldsInfo(categoriaSelecionada);
            }
        });
    }
    
    private void clearFieldsInfo() {
        jtxtf_id.setText(null);
        jtxtf_nome.setText(null);
        jtxtf_descricao.setText(null);
    }
    
    private void setFieldsInfo(Categoria categoria) {
        if (categoria == null) {
            throw new NullPointerException();
        }
        
        jtxtf_id.setText(String.valueOf(categoria.getId()));
        jtxtf_nome.setText(categoria.getNome());
        jtxtf_descricao.setText(categoria.getDescricao());
    }
    
    private Categoria getFieldsInfo() {
        String nome = jtxtf_nome.getText().trim();
        String descricao = jtxtf_descricao.getText().trim();
        
        if (nome.isEmpty()) {
            return null;
        }
        
        Categoria categoria;
        try {
            int id = Integer.parseInt(jtxtf_id.getText().trim()); // Throw se id for vazio ou não-inteiro
            categoria = new Categoria(id, nome, descricao);
        } catch (NumberFormatException e) {
            categoria = new Categoria(nome, descricao);
        }
        return categoria;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnl_tudo = new javax.swing.JPanel();
        jpn_sideMenu = new javax.swing.JPanel();
        jlbl_clientes = new javax.swing.JLabel();
        jlbl_clientes1 = new javax.swing.JLabel();
        jlbl_clientes2 = new javax.swing.JLabel();
        jlbl_clientes3 = new javax.swing.JLabel();
        jlbl_etiqueta = new javax.swing.JLabel();
        jpnl_principal = new javax.swing.JPanel();
        jtbl_categoria = new japedidos.produto.JTable_Categoria();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtxtf_nome = new javax.swing.JTextField();
        jtxtf_id = new javax.swing.JTextField();
        jtxtf_descricao = new javax.swing.JTextField();
        jbtn_limparSelecao = new javax.swing.JButton();
        jlbl_btn_novo = new javax.swing.JLabel();
        jlbl_btn_excluir = new javax.swing.JLabel();
        jlbl_btn_salvar = new javax.swing.JLabel();
        jlbl_campo = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jlbl_bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Categoria");
        setMaximumSize(new java.awt.Dimension(1024, 576));
        setMinimumSize(new java.awt.Dimension(1024, 576));
        setPreferredSize(new java.awt.Dimension(1024, 576));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpnl_tudo.setMaximumSize(new java.awt.Dimension(1024, 576));
        jpnl_tudo.setMinimumSize(new java.awt.Dimension(1024, 576));
        jpnl_tudo.setPreferredSize(new java.awt.Dimension(1024, 576));
        jpnl_tudo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpn_sideMenu.setMaximumSize(new java.awt.Dimension(250, 576));
        jpn_sideMenu.setMinimumSize(new java.awt.Dimension(250, 576));
        jpn_sideMenu.setOpaque(false);
        jpn_sideMenu.setPreferredSize(new java.awt.Dimension(250, 576));
        jpn_sideMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlbl_clientes.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_clientes.setText("RELATÓRIOS");
        jpn_sideMenu.add(jlbl_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 130, -1));

        jlbl_clientes1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_clientes1.setText("CLIENTES");
        jlbl_clientes1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_clientes1MouseClicked(evt);
            }
        });
        jpn_sideMenu.add(jlbl_clientes1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 90, -1));

        jlbl_clientes2.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_clientes2.setForeground(new java.awt.Color(204, 204, 204));
        jlbl_clientes2.setText("PRODUTOS");
        jpn_sideMenu.add(jlbl_clientes2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 120, -1));

        jlbl_clientes3.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlbl_clientes3.setText("PEDIDOS");
        jlbl_clientes3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_clientes3MouseClicked(evt);
            }
        });
        jpn_sideMenu.add(jlbl_clientes3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 100, -1));

        jlbl_etiqueta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/painel_comandos_esquerda_05x.png"))); // NOI18N
        jpn_sideMenu.add(jlbl_etiqueta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, -1));

        jpnl_tudo.add(jpn_sideMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 576));

        jpnl_principal.setOpaque(false);
        jpnl_principal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpnl_principal.add(jtbl_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 550, 240));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(30, 30, 30));
        jLabel1.setText("ID:");
        jpnl_principal.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, -1, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(30, 30, 30));
        jLabel2.setText("NOME:");
        jpnl_principal.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(30, 30, 30));
        jLabel3.setText("DESCRIÇÃO:");
        jpnl_principal.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, -1, -1));
        jpnl_principal.add(jtxtf_nome, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 310, 40));
        jpnl_principal.add(jtxtf_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 100, 40));
        jpnl_principal.add(jtxtf_descricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 430, 40));

        jbtn_limparSelecao.setText("Limpar seleção");
        jbtn_limparSelecao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_limparSelecaoActionPerformed(evt);
            }
        });
        jpnl_principal.add(jbtn_limparSelecao, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 480, 140, -1));

        jlbl_btn_novo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_novo_padrao.png"))); // NOI18N
        jlbl_btn_novo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_btn_novoMouseClicked(evt);
            }
        });
        jpnl_principal.add(jlbl_btn_novo, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, -1, -1));

        jlbl_btn_excluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_excluir_padrao.png"))); // NOI18N
        jlbl_btn_excluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_btn_excluirMouseClicked(evt);
            }
        });
        jpnl_principal.add(jlbl_btn_excluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 180, -1, -1));

        jlbl_btn_salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/btn_salvar_padrao.png"))); // NOI18N
        jlbl_btn_salvar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbl_btn_salvarMouseClicked(evt);
            }
        });
        jpnl_principal.add(jlbl_btn_salvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 140, -1, -1));
        jpnl_principal.add(jlbl_campo, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, -1, -1));

        jButton1.setText("Voltar");
        jpnl_principal.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 510, 140, -1));

        jpnl_tudo.add(jpnl_principal, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 750, 570));

        jlbl_bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/background_produtos_075x.png"))); // NOI18N
        jpnl_tudo.add(jlbl_bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jpnl_tudo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -2, 1024, 576));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtn_limparSelecaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_limparSelecaoActionPerformed
        clearFieldsInfo();
        jtbl_categoria.getJTable().getSelectionModel().clearSelection();
    }//GEN-LAST:event_jbtn_limparSelecaoActionPerformed

    private void jlbl_btn_novoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_novoMouseClicked
        Categoria newCategoria = this.getFieldsInfo();
        if (BD.Categoria.insert(newCategoria) > 0) {
            jtbl_categoria.refresh();
            this.clearFieldsInfo();
        }
    }//GEN-LAST:event_jlbl_btn_novoMouseClicked

    private void jlbl_btn_salvarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_salvarMouseClicked
        if(BD.Categoria.update(this.getFieldsInfo()) == 0) {
            JOptionPane.showMessageDialog(null, "O item especificado não foi encontrado.");
            this.clearFieldsInfo();
        } else {
            jtbl_categoria.refresh();
        }
    }//GEN-LAST:event_jlbl_btn_salvarMouseClicked

    private void jlbl_btn_excluirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_btn_excluirMouseClicked
       
        if (BD.Categoria.delete(this.getFieldsInfo()) == 0) {
            JOptionPane.showMessageDialog(null, "Não é possível excluir o item novamente.");
        } else {
            jtbl_categoria.refresh();
        }
        
        this.clearFieldsInfo();
    }//GEN-LAST:event_jlbl_btn_excluirMouseClicked

    private void jlbl_clientes1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_clientes1MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        japedidos.clientes.JFrame_Cliente frame = new japedidos.clientes.JFrame_Cliente();
        int x = this.getX() + this.getWidth() / 2 - frame.getWidth() / 2;
        int y = this.getY() + this.getHeight()/ 2 - frame.getHeight() / 2;
        frame.setLocation(x, y);
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jlbl_clientes1MouseClicked

    private void jlbl_clientes3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbl_clientes3MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        japedidos.pedidos.JFrame_GerenciamentoPedidos frame = new japedidos.pedidos.JFrame_GerenciamentoPedidos();
        int x = this.getX() + this.getWidth() / 2 - frame.getWidth() / 2;
        int y = this.getY() + this.getHeight()/ 2 - frame.getHeight() / 2;
        frame.setLocation(x, y);
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jlbl_clientes3MouseClicked

    public int getSelectedRow() {
        return jtbl_categoria.getJTable().getSelectedRow();
    }
    
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
            java.util.logging.Logger.getLogger(JFrame_Categoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_Categoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_Categoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_Categoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_Categoria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton jbtn_limparSelecao;
    private javax.swing.JLabel jlbl_bg;
    private javax.swing.JLabel jlbl_btn_excluir;
    private javax.swing.JLabel jlbl_btn_novo;
    private javax.swing.JLabel jlbl_btn_salvar;
    private javax.swing.JLabel jlbl_campo;
    private javax.swing.JLabel jlbl_clientes;
    private javax.swing.JLabel jlbl_clientes1;
    private javax.swing.JLabel jlbl_clientes2;
    private javax.swing.JLabel jlbl_clientes3;
    private javax.swing.JLabel jlbl_etiqueta;
    private javax.swing.JPanel jpn_sideMenu;
    private javax.swing.JPanel jpnl_principal;
    private javax.swing.JPanel jpnl_tudo;
    private japedidos.produto.JTable_Categoria jtbl_categoria;
    private javax.swing.JTextField jtxtf_descricao;
    private javax.swing.JTextField jtxtf_id;
    private javax.swing.JTextField jtxtf_nome;
    // End of variables declaration//GEN-END:variables
}
