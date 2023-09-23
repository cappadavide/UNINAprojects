/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import repository.tools.TextExtractor;

/**
 * Classe che esegue le query che riguardano le Classi.
 * @author davso
 */
public class ClassQueryExecutor {
    ClassQueryBuilder builder=new ClassQueryBuilder();
    PackageQueryBuilder pkbuilder=new PackageQueryBuilder();
    
    /**
     * Metodo che inserisce nel database prima un nuovo Descrittore Classe
     * e poi una Classe (Prima versione del descrittore)
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo InsertFrame
     */
    void insertNewClass(InsertFrame frame )throws SQLException{
        Connection conn=null;
        PreparedStatement prepStatement=null;
        ArrayList <JTextField> classFields=frame.getNewClassInsertFields();
        ArrayList <JComboBox> classCombos=frame.getNewClassComboBoxes();
        try{
            conn=DBManager.getDefaultConnection();
            conn.setAutoCommit(false);
            prepStatement=conn.prepareStatement(builder.insertNewClassDescriptorQueryBuilder());
            prepStatement.setString(1, classFields.get(0).getText());
            prepStatement.setString(2,TextExtractor.fromJComboBox(classCombos.get(5)));
            prepStatement.setString(3, TextExtractor.fromJComboBox(classCombos.get(4)));
            prepStatement.setString(4,classFields.get(1).getText());
            prepStatement.setString(5,TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(classCombos.get(6))));
            prepStatement.setString(6,TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(classCombos.get(2))));
            System.out.println("Righe inserite: "+prepStatement.executeUpdate());
            ResultSet rs=prepStatement.executeQuery(builder.getLastClassDescriptorCodeQueryBuilder());
            rs.next();
            int lastClassCode=rs.getInt(1);
            prepStatement=conn.prepareStatement(pkbuilder.getPackageCodeQueryBuilder());
            prepStatement.setString(1, TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(classCombos.get(2))));
            prepStatement.setString(2,TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(classCombos.get(1))));
            rs=prepStatement.executeQuery();
            rs.next();
            int packCode=rs.getInt(1);
            prepStatement=conn.prepareStatement(builder.insertNewClassReleaseQueryBuilder());
            prepStatement.setInt(1,packCode);
            prepStatement.setInt(2,lastClassCode);
            prepStatement.setString(3,TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(classCombos.get(3))));
            prepStatement.setString(4,classFields.get(2).getText());
            prepStatement.setString(5,classFields.get(3).getText());
            //Prepara di nuovo statement e setta parametri
            System.out.println("Righe inserite: "+prepStatement.executeUpdate());
            conn.commit();
            JOptionPane.showMessageDialog(frame, "Descrittore Classe e Classe inseriti!",
                        "Inserimento riuscito", JOptionPane.INFORMATION_MESSAGE);
            
        }
        catch(SQLException sqlex){
            if(sqlex.getMessage().startsWith("ORA-01400"))
                    JOptionPane.showMessageDialog(frame, "Assicurati di aver compilato "
                        + "tutti i campi obbligatori",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else if(sqlex.getMessage().startsWith("ORA-00001"))
                    JOptionPane.showMessageDialog(frame, "Descrittore Classe già inserito",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else if(sqlex.getMessage().contains("CHECKRIGAC"))
                    JOptionPane.showMessageDialog(frame, "RigaI e RigaF scelti in maniera impropria.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else
                    JOptionPane.showMessageDialog(frame, sqlex.getMessage(),
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
     * Metodo che inserisce un nuovo autore di una classe
     * @param frame Frame di tipo InsertFrame per ricavare i campi necessari
     * @param model Model della lista da cui ricavare gli autori
     */
    void insertNewClassDev(InsertFrame frame,DefaultListModel model){
        String query=builder.insertNewClassDevQueryBuilder();
        ArrayList <JComboBox> classCombos=frame.getNewClassDevComboBoxes();
        String classcode=TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(classCombos.get(1)));
        int insertedRows=0;
        try{
            Connection conn=DBManager.getDefaultConnection();
            for(int i = 0; i< model.getSize();i++){
                PreparedStatement prepStatement=conn.prepareStatement(query);
                prepStatement.setString(1,TextExtractor.getPrimaryKey((String)model.getElementAt(i)));
                prepStatement.setString(2,classcode);
                insertedRows+=prepStatement.executeUpdate();
            }
            
        }
        catch(SQLIntegrityConstraintViolationException e){
            System.out.println("Integrity constraint violation");
            JOptionPane.showMessageDialog(frame, "Uno sviluppatore è già autore della classe",
                        "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
        catch(SQLException sqlex){
            JOptionPane.showMessageDialog(frame, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
        finally{
            JOptionPane.showMessageDialog(null,"Autori assegnati alla classe: "+insertedRows,
                        "Informazione", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Metodo esegue la query di Ricerca Descrittore Classe
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella dentro la quale mostrare la query
     */
    void searchClassDescriptor(SearchFrame frame, DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> classFields=frame.getClassDescriptorFields();
        ArrayList <JComboBox> classCombos=frame.getClassDescriptorComboboxes();
        if(builder.checkClassDescriptorFields(frame)){
            try{
                String query=builder.classDescriptorQueryBuilder(frame);
                Connection conn=DBManager.getDefaultConnection();
                PreparedStatement prepStatement=conn.prepareStatement(query);
                if(classFields.get(0).isEnabled()){
                    prepStatement.setString(i,classFields.get(0).getText());
                    i++;
                }
                if(classFields.get(1).isEnabled()){
                    prepStatement.setString(i,classFields.get(1).getText());
                    i++;
                }
                if(classFields.get(2).isEnabled()){
                    prepStatement.setString(i,classFields.get(2).getText());
                    i++;
                }
                if(classFields.get(3).isEnabled()){
                   prepStatement.setString(i,classFields.get(3).getText());
                    i++;
                } 
                if(classFields.get(4).isEnabled()){
                   prepStatement.setString(i,classFields.get(4).getText());
                    i++;
                } 
                if(classCombos.get(0).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJComboBox(classCombos.get(0)));
                    i++;
                }
                if(classCombos.get(1).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJComboBox(classCombos.get(1)));
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
     * Metodo esegue la query di Ricerca Classe
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella dentro la quale mostrare la query
     */
    void searchClass(SearchFrame frame, DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> classFields=frame.getClassFields();
        ArrayList <JComboBox> classCombos=frame.getClassComboboxes();
        if(builder.checkClassFields(frame)){
            try{
                String query=builder.classQueryBuilder(frame);
                Connection conn=DBManager.getDefaultConnection();
                PreparedStatement prepStatement=conn.prepareStatement(query);
                if(classFields.get(0).isEnabled()){
                    prepStatement.setString(i,classFields.get(0).getText());
                    i++;
                }
                if(classFields.get(1).isEnabled()){
                    prepStatement.setString(i,classFields.get(1).getText());
                    i++;
                }
                if(classFields.get(2).isEnabled()){
                    prepStatement.setString(i,classFields.get(2).getText());
                    i++;
                }
                if(classFields.get(3).isEnabled()){
                    prepStatement.setString(i,classFields.get(3).getText());
                    i++;
                }
                
                if(classCombos.get(0).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJComboBox(classCombos.get(0)));
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
     * Metodo che aggiorna un singolo Descrittore Classe .
     * @param row Array di Stringa contenente i parametri da aggiornare
     * (o lasciare invariati)
     * @param code Codice del Descrittore Classe da modificare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int updateClassDescriptor(String row[],String code){
        String query=builder.updateClassDescriptorQueryBuilder();
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
                JOptionPane.showMessageDialog(null, "Si sta aggiornando un Desc. Classe con\ninformazioni di un Desc.Classe già presente",
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
     * Metodo che aggiorna una singola Classe.
     * @param row Array di Stringa contenente i parametri da aggiornare
     * (o lasciare invariati)
     * @param code Codice della Classe da modificare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int updateClass(String row[],String code){
        String query=builder.updateClassQueryBuilder();
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
                JOptionPane.showMessageDialog(null, "Si sta aggiornando una Classe con\ninformazioni di una Classe già presente",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().startsWith("ORA-01407")){
                JOptionPane.showMessageDialog(null, "Stai provando a lasciare vuoto un campo obbligatorio.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().contains("CHECKCLASSSTATUS")){
                JOptionPane.showMessageDialog(null, "Devi inserire uno di questi stati:\n" +
                        "Nuovo,Immutato,Modificato,Cancellato",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().contains("CHECKRIGAC"))
                    JOptionPane.showMessageDialog(null, "RigaI e RigaF scelti in maniera impropria.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    
    /**
     * Metodo che elimina un singolo Descrittore Classe.
     * @param code Codice del Descrittore Classe da eliminare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deleteClassDescriptor(String code){
        String query=builder.deleteClassDescriptorQueryBuilder();
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
     * Metodo che elimina una singola Classe.
     * @param code Codice della Classe da eliminare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deleteClass(String code){
        String query=builder.deleteClassQueryBuilder();
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
     * Metodo per cercare gli autori assegnati alle classi
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella da riempire con i risultati della query
     */
    void searchAssignment(SearchFrame frame,DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> otherFields=frame.getOtherFields();

        if(frame.checkOtherFields()){
             try{
                String query=builder.authorsAssignmentQueryBuilder(frame);
                Connection conn=DBManager.getDefaultConnection();
                PreparedStatement prepStatement=conn.prepareStatement(query);
                if(otherFields.get(0).isEnabled()){
                    prepStatement.setString(i,otherFields.get(0).getText());
                    i++;
                }
                if(otherFields.get(1).isEnabled()){
                    prepStatement.setString(i,otherFields.get(1).getText());
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
     * Metodo per eliminare l'assegnazione di un autore a una classe
     * @param code1 Codice Autore
     * @param code2 Codice Descrittore Classe
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deleteAssignment(String code1,String code2){
        String query=builder.deleteAssignmentQueryBuilder();
        try{
            Connection conn=DBManager.getDefaultConnection();
            PreparedStatement prepStatement=conn.prepareStatement(query);
            prepStatement.setString(1,code1);
            prepStatement.setString(2,code2);
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
