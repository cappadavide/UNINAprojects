/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import repository.tools.TextExtractor;
/**
 * Classe che facilita il popolamento delle combobox
 * laddove sia necessario effettuare delle query su un Database.
 * @author davso
 */
public class ComboBoxManager {
    /**
     * Metodo che serve a popolare una tabella con dati ottenuti mediante query.
     * @param combobox Parametro di tipo JComboBox
     * @param query Query attraverso cui ottenere i dati richiesti
     * @param addEmptyItem Indica se aggiungere un elemento vuoto o meno all'inizio
     * 
     */
    void buildComboBox(JComboBox combobox,String query,boolean addEmptyItem){
        try {
            refreshCombo(combobox);
            if(addEmptyItem)
                combobox.addItem("");
            Connection con = DBManager.getDefaultConnection();
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                String element="";
                for (int k = 0; k < columnCount; k++) {
                    element+=rs.getObject(k + 1).toString()+" "; 
                }
                element=TextExtractor.rtrim(element);
                combobox.addItem(element);
            }
            con.close();
            statement.close();
            rs.close();
            
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Errore",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }
    /**
     * Metodo che elimina gli items da una combobox
     * @param combo JComboBox da pulire
     */
    void refreshCombo(JComboBox combo){
        if(combo.getItemCount()>0){
            combo.removeAllItems();
        }
    }
}
