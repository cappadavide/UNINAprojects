/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Classe che facilita il popolamento delle tabelle
 * laddove sia necessario riempirle con i risultati di una query
 * @author davso
 */
public class TableManager {
    /**
     * Metodo che serve a popolare una tabella con dati ottenuti mediante query.
     * @param model Parametro di tipo DefaultTableModel
     * @param query Query attraverso cui ottenere i dati richiesti
     * @param frame JFrame chiamante
     */
    public void buildRows(DefaultTableModel model,String query,JFrame frame){
        refreshTable(model);
        Connection conn;
        Statement stm;
        ResultSet rs;
        try{
            conn=DBManager.getDefaultConnection();
            stm=conn.createStatement();
            rs= stm.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();
            //Estraggo il numero di colonne con l'apposita funzione
            int columnCount = metaData.getColumnCount();
            //Prelevo il nome delle colonne
            Object[] columnNames = new Object[columnCount];
            
            for (int column = 0; column < columnCount; column++) {
                System.out.println(metaData.getColumnName(column+1));
                columnNames[column] = metaData.getColumnName(column+1);
            }
            model.setColumnIdentifiers(columnNames);
            System.out.println("----------------");
            while (rs.next()) { 
                Object[] row = new Object[columnCount];
                
                for (int k = 0; k < columnCount; k++) {
                        row[k] = rs.getObject(k + 1); 
                }
                //Ogni volta aggiungo un nuovo rigo
                model.addRow(row);
            }
            
        }
        catch(SQLException sqlex){
            JOptionPane.showMessageDialog(frame, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(frame, e.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    /**
     * Metodo che serve a popolare una tabella con dati ottenuti mediante query.
     * @param model Parametro di tipo DefaultTableModel
     * @param rs ResultSet di una query
     */
    public void buildRows(ResultSet rs,DefaultTableModel model){
        refreshTable(model);
        try{
            ResultSetMetaData metaData = rs.getMetaData();
            //Estraggo il numero di colonne con l'apposita funzione
            int columnCount = metaData.getColumnCount();
            //Prelevo il nome delle colonne
            Object[] columnNames = new Object[columnCount];
            
            for (int column = 0; column < columnCount; column++) {
                System.out.println(metaData.getColumnName(column+1));
                columnNames[column] = metaData.getColumnName(column+1);
            }
            model.setColumnIdentifiers(columnNames);
            
            while (rs.next()) { 
                Object[] row = new Object[columnCount];
                for (int k = 0; k < columnCount; k++) {
                    if(metaData.getColumnName(k+1).contains("DATA")){
                        if(rs.getObject(k+1)!=null)
                            row[k]=(Object)new SimpleDateFormat("dd-MMM-yy").format(rs.getObject(k+1));
                        else
                            row[k]=rs.getObject(k+1);
                    }
                    else
                        row[k] = rs.getObject(k + 1); 
                }
                //Ogni volta aggiungo un nuovo rigo
                model.addRow(row);
            }
            rs.close();
            
        }
        catch(SQLException sqlex){
            JOptionPane.showMessageDialog(null, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Metodo che serve a popolare una tabella appartenente a un JDialog
     * con dati ottenuti mediante query.
     * @param model Parametro di tipo DefaultTableModel
     * @param query Query attraverso cui ottenere i dati richiesti
     * @param card JDialog chiamante
     */
     public void buildRowsCard(DefaultTableModel model,String query,JDialog card){
        refreshTable(model);
        Connection conn;
        Statement stm;
        ResultSet rs;
        try{
            conn=DBManager.getDefaultConnection();
            stm=conn.createStatement();
            rs= stm.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();
            //Estraggo il numero di colonne con l'apposita funzione
            int columnCount = metaData.getColumnCount();
            //Prelevo il nome delle colonne
            Object[] columnNames = new Object[columnCount];
            
            for (int column = 0; column < columnCount; column++) {
                System.out.println(metaData.getColumnName(column+1));
                columnNames[column] = metaData.getColumnName(column+1);
            }
            model.setColumnIdentifiers(columnNames);
            
            while (rs.next()) { 
                Object[] row = new Object[columnCount];
                for (int k = 0; k < columnCount; k++) {
                    row[k] = rs.getObject(k + 1); 
                }
                //Ogni volta aggiungo un nuovo rigo
                model.addRow(row);
            }
            conn.close();
            stm.close();
            rs.close();
            
        }
        catch(SQLException sqlex){
            JOptionPane.showMessageDialog(card, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(card, e.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    /**
     * Metodo di pulizia tabella
     * @param table 
     */
    public void refreshTable(DefaultTableModel table){
        int i=0;
        int nRows=table.getRowCount();
        for(i=nRows-1;i>=0;i--)
            table.removeRow(i);
    }
}
