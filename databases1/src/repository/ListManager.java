/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.sql.*;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 * Classe che facilita il popolamento delle liste
 * laddove sia necessario riempirle con dei risultati di una query
 * @author DaviSomma
 */
public class ListManager {
    /**
     * Metodo che serve a popolare una lista con dati ottenuti mediante query.
     * @param model Parametro di tipo DefaultTableList
     * @param query Query attraverso cui ottenere i dati richiesti
     */
    public void buildAuthorList(DefaultListModel model,String query){
        try {
            Connection con = DBManager.getDefaultConnection();
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs=statement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                String element="";
                for (int k = 0; k < columnCount; k++) {
                    element+=rs.getObject(k + 1).toString()+" "; 
                }
                model.addElement(element);
                
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("list");
            JOptionPane.showMessageDialog(null, "Failed to Connect to Database", "Error Connection",
                    JOptionPane.WARNING_MESSAGE);
            
            
        }
    }
}
