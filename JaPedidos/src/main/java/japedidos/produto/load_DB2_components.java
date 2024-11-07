/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package japedidos.produto;

import javax.swing.JComboBox;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Thor
 */
public class load_DB2_components {
    
    public static JComboBox<String> loadComboBox(JComboBox<String> comboBox, Connection banco, String sqlQuery) {
        
        comboBox.removeAllItems();  // Limpa os itens existentes
        HashMap<Integer, String[]> comboBoxMap = new HashMap<>();
        
        try
        {

            PreparedStatement stm_comboBox = banco.prepareStatement(sqlQuery); 
            ResultSet resultado_comboBox = stm_comboBox.executeQuery();
            while (resultado_comboBox.next()) {
                int index = resultado_comboBox.getInt(1);  // Obtém o índice
//                int index = resultado_comboBox.getInt("id");  // Obtém o índice
                String dadoColuna1 = resultado_comboBox.getString(2);  // Obtém o nome

                comboBoxMap.put(index, new String[]{dadoColuna1});
//                  Adiciona o nome ao JComboBox
                comboBox.addItem(dadoColuna1);
            }            
            /*
            String sql_lista_produtos_view = "SELECT * FROM listaTodosProdutos ORDER BY nome ASC";
            String sql_categoria = "SELECT id, nome, descricao FROM categoria ORDER BY nome ASC";
            String sql_unidade = "SELECT id, abreviacao FROM unidade ORDER BY abreviacao ASC";
        */
        stm_comboBox.close();
        } catch (SQLException erro) {
            erro.printStackTrace();
            System.out.println("O erro foi  :  " + erro);
        }

        return comboBox;
    }
    
    
    
    public static JTable loadComboBox(JTable  jTablecomponent, Connection banco, String sqlQuery) {
        NumberFormat decimal = new DecimalFormat("#,##0.00");
//        PreparedStatement stm_lista_produtos_view = null;
        //
        String url = "jdbc:mysql://localhost:3306/titanw25_japedidos_hml";
        String usuario = "root";
        String senha = "";
        //        String url = "jdbc:mysql://162.241.203.86:3306/titanw25_japedidos_hml";
        //       String usuario = "titanw25_japedidos_hml";
        //      String senha = "seNai@2024proj";

        try
        {
            banco = DriverManager.getConnection(url, usuario, senha);
            String sql_lista_produtos_view = "SELECT * FROM listaTodosProdutos ORDER BY nome ASC";
            
            PreparedStatement stm_lista_produtos_view = banco.prepareStatement(sqlQuery);
            
//            stm_lista_produtos_view.execute(); // cria o vetor
            ResultSet resultado_lista_produtos_view = stm_lista_produtos_view.executeQuery(sqlQuery);

//          Define modelo de tabela a partir da  que ja existe neste frame
            DefaultTableModel model =(DefaultTableModel) jTablecomponent.getModel();
//          Zera a lista caso ja exista algum dado anterior
            model.setRowCount(0);
            while(resultado_lista_produtos_view.next())
            {
                model.addRow(new Object[]
                    {
                        //  Retorna os dados da tabela do lista_produtos_view, cada campo e um coluna.
                        resultado_lista_produtos_view.getString("id"),
                        resultado_lista_produtos_view.getString("nome"),
                        resultado_lista_produtos_view.getString("categoria"),
                        decimal.format(resultado_lista_produtos_view.getDouble("preco_custo")),
                        decimal.format(resultado_lista_produtos_view.getDouble("preco_venda")),
                        resultado_lista_produtos_view.getString("unidade"),
                        resultado_lista_produtos_view.getBoolean("estado")
                    });
                }
            stm_lista_produtos_view.close();
            }
            catch (SQLException ex)
            {
                System.out.println("O erro foi  X:  " +ex);
            }

        
    return jTablecomponent;
    }
}
