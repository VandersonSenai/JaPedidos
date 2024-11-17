/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package japedidos.produto;

import java.awt.Component;
import javax.swing.JComboBox;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Thor
 */
public class load_DB2_components {
    
    public static JComboBox<String> carregaComboBox(JComboBox<String> comboBox, Connection banco, String sqlQuery) throws SQLException {
        comboBox.removeAllItems();  // Limpa os itens existentes
        HashMap<Integer, String[]> comboBoxMap = new HashMap<>();
        
        try
        {
            PreparedStatement stm_comboBox = banco.prepareStatement(sqlQuery); 
            ResultSet resultado_comboBox = stm_comboBox.executeQuery();

            ResultSetMetaData metadata = resultado_comboBox.getMetaData();
            String primeiroItem = metadata.getTableName(1);
            comboBoxMap.put(0, new String[]{primeiroItem});
            comboBox.addItem(primeiroItem);

            while (resultado_comboBox.next()) {
                int index = resultado_comboBox.getInt(1);  // Obtém o índice
                String dadoColuna1 = resultado_comboBox.getString(2);  // Obtém o nome
                comboBoxMap.put(index, new String[]{dadoColuna1});
//                  Adiciona o nome ao JComboBox
                comboBox.addItem(dadoColuna1);
            }            

            stm_comboBox.close();
        } catch (SQLException erro) {
            erro.printStackTrace();
            System.out.println("O erro foi : " + erro);
        }

        return comboBox;
    }
       
    
    public static JTable carregaJTable(JTable  jTablecomponent, Connection banco, String sqlQuery)  throws SQLException {
        NumberFormat decimal = new DecimalFormat("#,##0.00");

        try
        {
            PreparedStatement stm_listaJTable = banco.prepareStatement(sqlQuery); 
            ResultSet resultado_listaJTable = stm_listaJTable.executeQuery();

            DefaultTableModel model =(DefaultTableModel) jTablecomponent.getModel();
            model.setRowCount(0);

            while(resultado_listaJTable.next())
            {
                model.addRow(new Object[]
                    {
                        resultado_listaJTable.getString("id"),
                        resultado_listaJTable.getString("nome"),
                        resultado_listaJTable.getString("categoria"),
                        decimal.format(resultado_listaJTable.getDouble("preco_custo")),
                        decimal.format(resultado_listaJTable.getDouble("preco_venda")),
                        resultado_listaJTable.getString("unidade"),
                        resultado_listaJTable.getBoolean("estado")
                    });
                }
            stm_listaJTable.close();
            }
            catch (SQLException ex)
            {
                System.out.println("O erro foi : " +ex);
            }
        
    return jTablecomponent;
    }
    
        public static void excluirProduto(Component jframe,  String url, String usuario, String senha, String sqlQuery)  throws SQLException {

        try (Connection banco = DriverManager.getConnection(url, usuario, senha); PreparedStatement stm_tabela = banco.prepareStatement(sqlQuery)) {
        
            int linhasAfetadas = stm_tabela.executeUpdate();
            if (linhasAfetadas > 0) {
                
                System.out.println("Dados excluidos com sucesso!");
                JOptionPane.showMessageDialog(jframe, 
                "Excluido com sucesso.\n", 
                "JaPedidos", 
                JOptionPane.INFORMATION_MESSAGE);
                
            } else {
                System.out.println("Nenhum dado excluido.");
                JOptionPane.showMessageDialog(jframe, 
                "Nenhum dado excluido.\n", 
                "JaPedidos", 
                JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch (SQLException ex)
            {
                System.out.println("O erro foi : " +ex);
                JOptionPane.showMessageDialog(jframe, 
                "Erro ao acessar banco.\n", 
                "JaPedidos", 
                JOptionPane.INFORMATION_MESSAGE);
            }
    }
    public static void salvaProduto(Component referencia,  Connection banco , String sqlQuery)  throws SQLException {

        
        try (PreparedStatement stm_tabela = banco.prepareStatement(sqlQuery)) {
//
//            stm_tabela.setString(1, referencia.jtxtf_descricao.getText()); // descricao do item.
//            stm_tabela.setString(1, referencia.jtxtf_descricao.getText()); // descricao do item.
//            stm_tabela.setString(1, referencia.jtxtf_descricao.getText()); // descricao do item.
//            stm_tabela.setString(1, referencia.jtxtf_descricao.getText()); // descricao do item.
//            stm_tabela.setString(1, referencia.jtxtf_descricao.getText()); // descricao do item.
//            stm_tabela.setString(1, referencia.jtxtf_descricao.getText()); // descricao do item.
            int linhasAfetadas = stm_tabela.executeUpdate();
            if (linhasAfetadas > 0) {
                
                //System.out.println("Dados excluidos com sucesso!");
                JOptionPane.showMessageDialog(referencia, 
                "Item atualizado/cadastrado com sucesso.\n", 
                "JaPedidos", 
                JOptionPane.INFORMATION_MESSAGE);
            } else {
                //System.out.println("Nenhum dado excluido.");
                JOptionPane.showMessageDialog(referencia, 
                "Nenhum dado salvo.\n", 
                "JaPedidos", 
                JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch (SQLException ex)
            {
                //System.out.println("O erro foi : " +ex);
                JOptionPane.showMessageDialog(referencia, 
                "Erro ao acessar banco.\n", 
                "JaPedidos", 
                JOptionPane.INFORMATION_MESSAGE);
            }
    }
}
