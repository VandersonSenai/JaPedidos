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
import java.util.HashMap;

/**
 *
 * @author Thor
 */
public class load_db_2_jtable_jcombbox {
    
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
    
}
