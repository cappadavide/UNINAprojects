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
 * Classe che esegue le query che riguardano i Metodi.
 * @author davso
 */
public class MethodQueryExecutor {
    MethodQueryBuilder mBuilder=new MethodQueryBuilder();
    /**
     * Metodo che inserisce nel database un nuovo Descrittore Metodo
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo InsertFrame
     */
    void insertNewMethod(InsertFrame frame ){
        ArrayList <JTextField> methodFields=frame.getNewMethodInsertFields();
        ArrayList <JComboBox> methodCombos=frame.getNewMethodComboBoxes();
        try{
            Connection  conn=DBManager.getDefaultConnection();
            PreparedStatement prepStatement=conn.prepareStatement(mBuilder.insertNewMethodDescriptorQueryBuilder());
            prepStatement.setString(1, methodFields.get(0).getText());
            prepStatement.setString(2,methodFields.get(2).getText());
            prepStatement.setString(3,TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(methodCombos.get(0))));
            prepStatement.setString(4,TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(methodCombos.get(1))));
            prepStatement.setString(5,methodFields.get(1).getText());

            System.out.println("Numero righe inserite: "+prepStatement.executeUpdate());
            JOptionPane.showMessageDialog(null,"Descrittore Metodo inserito!",
                        "Inserimento riuscito", JOptionPane.INFORMATION_MESSAGE);   
        }
        catch(SQLException sqlex){
            if(sqlex.getMessage().startsWith("ORA-01400"))
                JOptionPane.showMessageDialog(frame, "Assicurati di aver compilato "
                        + "tutti i campi obbligatori",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else if(sqlex.getMessage().startsWith("ORA-00001"))
                JOptionPane.showMessageDialog(frame, "Metodo già inserito",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(frame, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Metodo che inserisce nel database un nuovo Metodo
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo InsertFrame
     */
    void insertNewMethodClass(InsertFrame frame){
        ArrayList <JTextField> methodFields=frame.getNewMethodClassInsertFields();
        ArrayList <JComboBox> methodCombos=frame.getNewMethodClassComboBoxes();
        try{
            Connection  conn=DBManager.getDefaultConnection();
            PreparedStatement prepStatement=conn.prepareStatement(mBuilder.insertNewMethodReleaseQueryBuilder());
            prepStatement.setString(1,TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(methodCombos.get(1))));
            prepStatement.setString(2,TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(methodCombos.get(0))));
            prepStatement.setString(3, methodFields.get(0).getText());
            prepStatement.setString(4,methodFields.get(1).getText());
            System.out.println("Numero righe inserite: "+prepStatement.executeUpdate());
            JOptionPane.showMessageDialog(null,"Metodo inserito!",
                        "Inserimento riuscito", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException sqlex){
            if(sqlex.getMessage().startsWith("ORA-01400"))
                JOptionPane.showMessageDialog(frame, "Assicurati di aver compilato "
                        + "tutti i campi obbligatori",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else if(sqlex.getMessage().startsWith("ORA-00001"))
                JOptionPane.showMessageDialog(frame, "Il metodo è stato già rilasciato\nper la versione di questo progetto.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else if(sqlex.getMessage().contains("CHECKRIGAM"))
                    JOptionPane.showMessageDialog(frame, "RigaI e RigaF scelti in maniera impropria.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(frame, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
     /**
     * Metodo esegue la query di Ricerca Descrittore Metodo
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella dentro la quale mostrare la query
     */
    void searchDescriptorMethod(SearchFrame frame,DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> methodFields=frame.getMethodDescriptorFields();
        ArrayList <JComboBox> methodCombos=frame.getMethodDescriptorComboboxes();
        if(mBuilder.checkMethodDescriptorFields(frame)){
            try{
                String query=mBuilder.methodDescriptorQueryBuilder(frame);
                Connection conn=DBManager.getDefaultConnection();
                PreparedStatement prepStatement=conn.prepareStatement(query);
                if(methodFields.get(0).isEnabled()){
                    prepStatement.setString(i,methodFields.get(0).getText());
                    i++;
                }
                if(methodFields.get(1).isEnabled()){
                    prepStatement.setString(i,methodFields.get(1).getText());
                    i++;
                }
                if(methodFields.get(2).isEnabled()){
                    prepStatement.setString(i,methodFields.get(2).getText());
                    i++;
                }
                if(methodFields.get(3).isEnabled()){
                    prepStatement.setString(i,methodFields.get(3).getText());
                    i++;
                }

                if(methodCombos.get(0).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJComboBox(methodCombos.get(0)));
                    i++;
                }
                if(methodCombos.get(1).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJComboBox(methodCombos.get(1)));
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
     * Metodo esegue la query di Ricerca Metodo
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella dentro la quale mostrare la query
     */
    void searchMethod(SearchFrame frame,DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> methodFields=frame.getMethodFields();
        ArrayList <JComboBox> methodCombos=frame.getMethodComboboxes();
        if(mBuilder.checkMethodFields(frame)){
            try{
                String query=mBuilder.methodQueryBuilder(frame);
                Connection conn=DBManager.getDefaultConnection();
                PreparedStatement prepStatement=conn.prepareStatement(query);
                if(methodFields.get(0).isEnabled()){
                    prepStatement.setString(i,methodFields.get(0).getText());
                    i++;
                }
                if(methodFields.get(1).isEnabled()){
                    prepStatement.setString(i,methodFields.get(1).getText());
                    i++;
                }
                if(methodFields.get(2).isEnabled()){
                    prepStatement.setString(i,methodFields.get(2).getText());
                    i++;
                }

                if(methodCombos.get(0).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJComboBox(methodCombos.get(0)));
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
     * Metodo che inserisce un nuovo autore di un metodo
     * @param frame Frame di tipo InsertFrame per ricavare i campi necessari
     * @param model Model della lista da cui ricavare gli autori
     */
    void insertNewMethodDev(InsertFrame frame,DefaultListModel model){
        String query=mBuilder.insertNewMethodDevQueryBuilder();
        JComboBox combo=frame.getMethodDevChooseMethodComboBox();
        String methodcode=TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(combo));
        int insertedRows=0;
        try{
            Connection conn=DBManager.getDefaultConnection();
            for(int i = 0; i< model.getSize();i++){
                PreparedStatement prepStatement=conn.prepareStatement(query);
                prepStatement.setString(1,TextExtractor.getPrimaryKey((String)model.getElementAt(i)));
                prepStatement.setString(2,methodcode);
                insertedRows+=prepStatement.executeUpdate();
            }
            
        }
        catch(SQLIntegrityConstraintViolationException e){
            System.out.println("Integrity constraint violation");
            JOptionPane.showMessageDialog(frame, "Uno sviluppatore è già autore del metodo",
                        "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
        catch(SQLException sqlex){
            JOptionPane.showMessageDialog(frame, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
        finally{
            JOptionPane.showMessageDialog(null,"Autori assegnati al metodo: "+insertedRows,
                        "Informazione", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * Metodo che aggiorna un singolo Descrittore Metodo.
     * @param row Array di Stringa contenente i parametri da aggiornare
     * (o lasciare invariati)
     * @param code Codice del Descrittore Metodo da modificare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int updateMethodDescriptor(String row[],String code){
        String query=mBuilder.updateMethodDescriptorQueryBuilder();
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
                JOptionPane.showMessageDialog(null, "Si sta aggiornando un Desc. Metodo con\ninformazioni di un Desc. Metodo già presente",
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
     * Metodo che aggiorna un singolo Metodo.
     * @param row Array di Stringa contenente i parametri da aggiornare
     * (o lasciare invariati)
     * @param code Codice del Metodo da modificare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int updateMethod(String row[],String code){
        String query=mBuilder.updateMethodQueryBuilder();
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
                JOptionPane.showMessageDialog(null, "Si sta aggiornando un Metodo con\ninformazioni di un Metodo già presente",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().startsWith("ORA-01407")){
                JOptionPane.showMessageDialog(null, "Stai provando a lasciare vuoto un campo obbligatorio.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().contains("CHECKMETHODSTATUS")){
                JOptionPane.showMessageDialog(null, "Devi inserire uno di questi stati:\n" +
                        "Nuovo,Immutato,Modificato,Cancellato",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().contains("CHECKRIGAM"))
                    JOptionPane.showMessageDialog(null, "RigaI e RigaF scelti in maniera impropria.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    
    /**
     * Metodo che elimina un singolo Descrittore Metodo.
     * @param code Codice del Descrittore Metodo da eliminare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deleteMethodDescriptor(String code){
        String query=mBuilder.deleteMethodDescriptorQueryBuilder();
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
     * Metodo che elimina un singolo Metodo.
     * @param code Codice del Metodo da eliminare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deleteMethod(String code){
        String query=mBuilder.deleteMethodQueryBuilder();
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
     * Metodo per cercare gli autori assegnati ai metodi
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella da riempire con i risultati della query
     */
    void searchAssignment(SearchFrame frame,DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> otherFields=frame.getOtherFields();

        if(frame.checkOtherFields()){
             try{
                String query=mBuilder.authorsAssignmentQueryBuilder(frame);
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
     * Metodo per eliminare l'assegnazione di un autore a un metodo
     * @param code1 Codice Autore
     * @param code2 Codice Descrittore Metodo
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deleteAssignment(String code1,String code2){
        String query=mBuilder.deleteAssignmentQueryBuilder();
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
