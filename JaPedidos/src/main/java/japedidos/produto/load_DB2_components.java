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
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Thor
 */
public class load_DB2_components {

        public static JTable listaPedidos(JTable  jTablecomponent, Connection banco, String sqlQuery)  throws SQLException {
        NumberFormat decimal = new DecimalFormat("#,##0.00");


        try
        {
            PreparedStatement stm_listaJTable = banco.prepareStatement(sqlQuery); 
            ResultSet resultado_listaJTable = stm_listaJTable.executeQuery();

            DefaultTableModel model =(DefaultTableModel) jTablecomponent.getModel();
            model.setRowCount(0);

            while(resultado_listaJTable.next())
            {
                Timestamp timestamp = resultado_listaJTable.getTimestamp("dthr_entregar");
                LocalDate data = timestamp.toLocalDateTime().toLocalDate();
                LocalTime hora = timestamp.toLocalDateTime().toLocalTime();
                model.addRow(new Object[]
                    {
                        resultado_listaJTable.getString("id"),
                        resultado_listaJTable.getString("nome_ultimo_est"),
                        resultado_listaJTable.getString("nome_cliente"),
                        resultado_listaJTable.getString("telefone_cliente"),
//                        resultado_listaJTable.getString("data"),
                        data,
                        hora,
                        decimal.format(resultado_listaJTable.getDouble("preco_final"))
                    });
                }
            stm_listaJTable.close();
            }
            catch (SQLException ex)
            {
//                System.out.println("O erro foi : " +ex);
                JOptionPane.showMessageDialog(jTablecomponent, 
                "Erro ao acessar banco.\n"+
                "Erro: " + ex, 
                "JaPedidos", 
                JOptionPane.INFORMATION_MESSAGE);
            }
        
    return jTablecomponent;
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
//                System.out.println("O erro foi : " +ex);
                JOptionPane.showMessageDialog(jTablecomponent, 
                "Erro ao acessar banco.\n", 
                "JaPedidos", 
                JOptionPane.INFORMATION_MESSAGE);
            }
        
    return jTablecomponent;
    }
    
        public static void excluirProduto(Component jframe,  Connection banco, String sqlQuery)  throws SQLException {

        try (PreparedStatement stm_tabela = banco.prepareStatement(sqlQuery)) {
        
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
//                System.out.println("O erro foi : " +ex);
//                JOptionPane.showMessageDialog(jframe, 
//                "Erro ao acessar banco.\n", 
//                "JaPedidos", 
//                JOptionPane.INFORMATION_MESSAGE);
                throw ex;
            }
    }

    public static void salvaProduto(Component referencia,  Connection banco , String sqlQuery, String[] dados)  throws SQLException, NumberFormatException {

        try (PreparedStatement stm_tabela = banco.prepareStatement(sqlQuery)) {
            // se o dados[0]="" que se refere ao codigo estiver em branco significa que é um novo item 
            // e recebera um codigo automatico/seguencial no banco.
            // caso contrario estamos apenas atualizando um existente.
            if (dados[0].equals("")){
                stm_tabela.setString((1), dados[1]); 
                stm_tabela.setString((2), dados[2]); 
                stm_tabela.setString((3), dados[3]); 
                dados[4] = dados[4].replace(",", ".");
                stm_tabela.setFloat((4), Float.parseFloat(dados[4])); 
                dados[5] = dados[5].replace(",", ".");
                stm_tabela.setFloat((5), Float.parseFloat(dados[5])); 
                stm_tabela.setInt((6), Integer.parseInt(dados[6]));  
            } else {
                stm_tabela.setString((1), dados[1]); 
                stm_tabela.setString((2), dados[2]); 
                stm_tabela.setString((3), dados[3]); 
                dados[4] = dados[4].replace(",", ".");
                stm_tabela.setFloat((4), Float.parseFloat(dados[4])); 
                dados[5] = dados[5].replace(",", ".");
                stm_tabela.setFloat((5), Float.parseFloat(dados[5])); 
                stm_tabela.setInt((6), Integer.parseInt(dados[6])); 
                stm_tabela.setString((7), dados[0]);                 
            }
            
            int linhasAfetadas = stm_tabela.executeUpdate();
            stm_tabela.close();
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
            catch (SQLException | NumberFormatException ex)
            {
//                System.out.println("O erro foi : " +ex);
//                JOptionPane.showMessageDialog(referencia, 
//                "Erro ao acessar banco.\n" + ex, 
//                "JaPedidos", 
//                JOptionPane.INFORMATION_MESSAGE);
                throw ex;
            }
    }    

    public static JComboBox<String> carregaComboBox(Component JFrame , JComboBox<String> comboBox, HashMap<String, Integer> mapaHash, Connection banco, String sqlQuery) throws SQLException {
        comboBox.removeAllItems();// Limpa os itens existentes
        mapaHash.clear();// Limpa mapa dos itens existentes
        
        try
        {
            PreparedStatement stm_comboBox = banco.prepareStatement(sqlQuery); 
            ResultSet resultado_comboBox = stm_comboBox.executeQuery();

            ResultSetMetaData metadata = resultado_comboBox.getMetaData();
            String primeiroItem = "Selecione";

//            System.out.println("metadata 1: " + metadata.getColumnName(2));
//            
            mapaHash.put(primeiroItem, 0);
            comboBox.addItem(primeiroItem);

            while (resultado_comboBox.next()) {
                int index = resultado_comboBox.getInt(1);  // Obtém o índice na primeira coluna
                String dadoColuna1 = resultado_comboBox.getString(2);  // Obtém o nome na segunda coluna
                mapaHash.put(dadoColuna1, index);
//                  Adiciona o nome ao JComboBox
                comboBox.addItem(dadoColuna1);
            }            

            stm_comboBox.close();
        } catch (SQLException erro) {
            erro.printStackTrace();
            System.out.println("O erro foi : " + erro);
                JOptionPane.showMessageDialog(JFrame, 
                "Erro ao carregar categorias\n" + erro.getMessage(), 
                "JaPedidos", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        return comboBox;
    }    
    public static Integer getSelectedID(JComboBox<String> comboBox, HashMap<String, Integer> mapaHash) {
        // Recuperar o nome selecionado
        String selectedName = (String) comboBox.getSelectedItem();
        // Retornar o ID associado ao nome
        return mapaHash.get(selectedName);
    }    
}
