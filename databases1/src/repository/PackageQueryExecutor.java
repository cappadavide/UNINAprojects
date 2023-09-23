/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import repository.tools.TextExtractor;

/**
 *
 * @author davso
 */
public class PackageQueryExecutor {
    private PackageQueryBuilder builder=new PackageQueryBuilder();
    /**
     * Metodo che inserisce nel database prima un nuovo Descrittore Package
     * e poi un Package (Prima versione del descrittore)
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo InsertFrame
     */
    void insertNewPackage(InsertFrame frame) throws SQLException{
        Connection conn=null;
        PreparedStatement prepStatement=null;
        JTextField packagename=frame.getNewPackageNameField();
        ArrayList <JComboBox> combo=frame.getNewPackageComboBoxes();
        try{
            conn=DBManager.getDefaultConnection();
            conn.setAutoCommit(false);
            prepStatement=conn.prepareStatement(builder.insertNewPackageQueryBuilder());
            prepStatement.setString(1, packagename.getText());
            prepStatement.setString(2,TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(combo.get(2))));
            prepStatement.setString(3, TextExtractor.fromJComboBox(combo.get(0)));
            System.out.println("Righe inserite: "+prepStatement.executeUpdate());
            ResultSet rs=prepStatement.executeQuery(builder.getLastPackageDescriptorCodeQueryBuilder());
            rs.next();
            int code=rs.getInt(1);
            prepStatement=conn.prepareStatement(builder.insertNewPackageReleaseQueryBuilder());
            prepStatement.setString(1,TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(combo.get(1))));
            prepStatement.setInt(2,code);
            System.out.println("Righe inserite: "+prepStatement.executeUpdate());
            conn.commit();
            JOptionPane.showMessageDialog(frame, "Descrittore Package e Package inseriti!",
                        "Inserimento riuscito", JOptionPane.INFORMATION_MESSAGE);
            
        }

        catch(SQLException sqlex){
            if(sqlex.getMessage().startsWith("ORA-01400"))
                JOptionPane.showMessageDialog(frame, "Assicurati di aver compilato "
                        + "tutti i campi obbligatori",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(frame, "Package già inserito?",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            if(conn!=null)
                conn.rollback();
        }
        finally{
            try{
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch(SQLException sqlex2){
                
            }
        }
    }
    /**
     * Metodo esegue la query di Ricerca Descrittore Package
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella dentro la quale mostrare la query
     */
    void searchPackageDescriptor(SearchFrame frame, DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> packageFields=frame.getPackageDescriptorFields();
        ArrayList <JComboBox> packageCombos=frame.getPackageDescriptorComboboxes();
        if(builder.checkPackageDescriptorFields(frame)){
            try{
                String query=builder.packageDescriptorQueryBuilder(frame);
                Connection conn=DBManager.getDefaultConnection();
                PreparedStatement prepStatement=conn.prepareStatement(query);
                if(packageFields.get(0).isEnabled()){
                    prepStatement.setString(i,packageFields.get(0).getText());
                    i++;
                }
                if(packageFields.get(1).isEnabled()){
                    prepStatement.setString(i,packageFields.get(1).getText());
                    i++;
                }
                if(packageFields.get(2).isEnabled()){
                    prepStatement.setString(i,packageFields.get(2).getText());
                    i++;
                }
                if(packageCombos.get(0).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJComboBox(packageCombos.get(0)));
                    i++;
                }
                ResultSet rs=prepStatement.executeQuery();
                TableManager tm=new TableManager();
                tm.buildRows(rs,model);  
            }
            catch(SQLException sqlex){
                JOptionPane.showMessageDialog(frame, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Controlla che tutti i campi "
                    + "siano compilati", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Metodo esegue la query di Ricerca Package
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella dentro la quale mostrare la query
     */
    void searchPackage(SearchFrame frame, DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> packageFields=frame.getPackageFields();
        ArrayList <JComboBox> packageCombos=frame.getPackageComboboxes();
        if(builder.checkPackageFields(frame)){
            try{
                String query=builder.packageQueryBuilder(frame);
                Connection conn=DBManager.getDefaultConnection();
                PreparedStatement prepStatement=conn.prepareStatement(query);
                if(packageFields.get(0).isEnabled()){
                    prepStatement.setString(i,packageFields.get(0).getText());
                    i++;
                }
                if(packageFields.get(1).isEnabled()){
                    prepStatement.setString(i,packageFields.get(1).getText());
                    i++;
                }
                if(packageFields.get(2).isEnabled()){
                    prepStatement.setString(i,packageFields.get(2).getText());
                    i++;
                }
                if(packageCombos.get(0).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJComboBox(packageCombos.get(0)));
                    i++;
                }
                ResultSet rs=prepStatement.executeQuery();
                TableManager tm=new TableManager();
                tm.buildRows(rs,model);  
            }
            catch(SQLException sqlex){
                JOptionPane.showMessageDialog(frame, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Controlla che tutti i campi "
                    + "siano compilati", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Metodo che aggiorna un singolo Descrittore Package .
     * @param row Array di Stringa contenente i parametri da aggiornare
     * (o lasciare invariati)
     * @param code Codice del Descrittore Package da modificare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int updatePackageDescriptor(String row[],String code){
        String query=builder.updatePackageDescriptorQueryBuilder();
        int i;
        try{
            Connection conn=DBManager.getDefaultConnection();
            PreparedStatement prepStatement=conn.prepareStatement(query);
            for(i = 0; i< row.length;i++){
                System.out.println(row.length);
                System.out.println(row[i]);
                prepStatement.setString(i+1,row[i]);
            }
            prepStatement.setString(i+1,code);
            if(prepStatement.executeUpdate()>0)
                return 1;
            else
                return 0;
            
        }
        catch(SQLException sqlex){
            if(sqlex.getMessage().startsWith("ORA-00001")){
                JOptionPane.showMessageDialog(null, "Si sta aggiornando un Desc. Package con\ninformazioni di un Desc.Package già presente",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().startsWith("ORA-01407")){
                JOptionPane.showMessageDialog(null, "Stai provando a lasciare vuoto un campo obbligatorio.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(null, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    /**
     * Metodo che aggiorna un singolo Package.
     * @param row Array di Stringa contenente i parametri da aggiornare
     * (o lasciare invariati)
     * @param code Codice del Package da modificare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int updatePackage(String row[],String code){
        String query=builder.updatePackageQueryBuilder();
        int i;
        try{
            Connection conn=DBManager.getDefaultConnection();
            PreparedStatement prepStatement=conn.prepareStatement(query);
            for(i = 0; i< row.length;i++){
                System.out.println(row.length);
                System.out.println(row[i]);
                prepStatement.setString(i+1,row[i]);
            }
            prepStatement.setString(i+1,code);
            if(prepStatement.executeUpdate()>0)
                return 1;
            else
                return 0;
            
        }
        catch(SQLException sqlex){
            if(sqlex.getMessage().startsWith("ORA-00001")){
                JOptionPane.showMessageDialog(null, "Si sta aggiornando un Package con\ninformazioni di un Package già presente",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().startsWith("ORA-01407")){
                JOptionPane.showMessageDialog(null, "Stai provando a lasciare vuoto un campo obbligatorio.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().contains("CHECKPACKSTATUS")){
                JOptionPane.showMessageDialog(null, "Devi inserire uno di questi stati:\n" +
                        "Nuovo,Immutato,Modificato,Cancellato",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(null, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            
            return 0;
        }
    }
    /**
     * Metodo che elimina un singolo Descrittore Package.
     * @param code Codice del Descrittore Package da eliminare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deletePackageDescriptor(String code){
        String query=builder.deletePackageDescriptorQueryBuilder();
        try{
            Connection conn=DBManager.getDefaultConnection();
            PreparedStatement prepStatement=conn.prepareStatement(query);
            prepStatement.setString(1,code);
            if(prepStatement.executeUpdate()>0)
                return 1;
            else
                return 0;
            
        }
        catch(SQLException sqlex){
            JOptionPane.showMessageDialog(null, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    /**
     * Metodo che elimina un singolo Package.
     * @param code Codice del Package da eliminare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deletePackage(String code){
        String query=builder.deletePackageQueryBuilder();
        try{
            Connection conn=DBManager.getDefaultConnection();
            PreparedStatement prepStatement=conn.prepareStatement(query);
            prepStatement.setString(1,code);
            if(prepStatement.executeUpdate()>0)
                return 1;
            else
                return 0;
            
        }
        catch(SQLException sqlex){
            JOptionPane.showMessageDialog(null, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
}
