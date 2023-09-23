/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.toedter.calendar.JDateChooser;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import repository.tools.TextExtractor;

/**
 * Classe che esegue le query che riguardano Changelog
 * @author davso
 */
public class ChangelogQueryExecutor {
    ChangelogQueryBuilder builder = new ChangelogQueryBuilder();
    /**
     * Metodo che inserisce nel database un nuovo log.
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo InsertFrame
     */
    void insertNewChangelog(InsertFrame frame){
        ArrayList <JComboBox> combos = frame.getNewChangelogComboBoxes();
        ArrayList <JDateChooser>  dates=frame.getNewChangelogDates();
        JTextArea description=frame.getNewChangelogDescription();
        PreparedStatement prepStatement=null;
      
        try{
            Connection conn=DBManager.getDefaultConnection();
            if(combos.get(2).isEnabled()){
                prepStatement=conn.prepareStatement(builder.insertNewChangelogPackageQueryBuilder());
                prepStatement.setString(1, TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(combos.get(2))));
            }
            
            else if(combos.get(3).isEnabled()){
                prepStatement=conn.prepareStatement(builder.insertNewChangelogClassQueryBuilder());
                prepStatement.setString(1, TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(combos.get(3))));
            }
            else if(combos.get(4).isEnabled()){
                prepStatement=conn.prepareStatement(builder.insertNewChangelogMethodQueryBuilder());
                prepStatement.setString(1, TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(combos.get(4))));
            }
            prepStatement.setString(2,TextExtractor.fromJComboBox(combos.get(0)));
            prepStatement.setString(3,TextExtractor.fromJComboBox(combos.get(1)));
            prepStatement.setString(4, TextExtractor.fromJDateChooser(dates.get(0)));
            prepStatement.setString(5, TextExtractor.fromJDateChooser(dates.get(1))); 
            prepStatement.setString(6,description.getText());
            prepStatement.setString(7,TextExtractor.fromJComboBox(combos.get(5)));
            conn.createStatement().execute("ALTER TRIGGER TRIGGERCLASSE DISABLE");
            conn.createStatement().execute("ALTER TRIGGER TRIGGERMETODO DISABLE");
            System.out.println("Numero righe inserite: "+prepStatement.executeUpdate());
             conn.createStatement().execute("ALTER TRIGGER TRIGGERCLASSE ENABLE");
             conn.createStatement().execute("ALTER TRIGGER TRIGGERMETODO ENABLE");
            JOptionPane.showMessageDialog(frame, "Log inserito!",
                            "Inserimento riuscito", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException sqlex){
            if(sqlex.getMessage().startsWith("ORA-01400"))
                JOptionPane.showMessageDialog(frame, "Assicurati di aver compilato "
                        + "tutti i campi obbligatori",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(frame, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
                
            try {
                Connection conn=DBManager.getDefaultConnection();
                conn.createStatement().execute("ALTER TRIGGER TRIGGERMETODO ENABLE");
                conn.createStatement().execute("ALTER TRIGGER TRIGGERCLASSE ENABLE");
            } catch (SQLException ex) {
                
            }
        }
        
    }
    /**
     * Metodo che inserisce un nuovo autore di un log
     * @param frame Frame di tipo InsertFrame per ricavare i campi necessari
     * @param model Model della lista da cui ricavare gli autori
     */
    void insertNewChangelogDev(InsertFrame frame,DefaultListModel model){
        String query=builder.insertNewChangelogDevQueryBuilder();
        JComboBox combo=frame.getNewChangelogDevChangelogComboBox();
        String logcode=TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(combo));
        int insertedRows=0;
        try{
            Connection conn=DBManager.getDefaultConnection();
            for(int i = 0; i< model.getSize();i++){
                PreparedStatement prepStatement=conn.prepareStatement(query);
                prepStatement.setString(1,TextExtractor.getPrimaryKey((String)model.getElementAt(i)));
                prepStatement.setString(2,logcode);
                insertedRows+=prepStatement.executeUpdate();
            }
            
        }
        catch(SQLIntegrityConstraintViolationException e){
            System.out.println("Integrity constraint violation");
            JOptionPane.showMessageDialog(frame, "Uno sviluppatore è già autore del log",
                        "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
        catch(SQLException sqlex){
            JOptionPane.showMessageDialog(frame, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
        finally{
            JOptionPane.showMessageDialog(null,"Autori assegnati al log: "+insertedRows,
                        "Informazione", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * Metodo esegue la query di Ricerca Changelog
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella dentro la quale mostrare la query
     */
   void searchChangelog(SearchFrame frame,DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> changelogFields=frame.getChangelogFields();
        ArrayList <JDateChooser> changelogDates=frame.getChangelogDates();
        ArrayList <JComboBox>  changelogCombos=frame.getChangelogComboboxes();
        String query=builder.changelogQueryBuilder(frame);
        if(builder.checkChangelogFields(frame)&&builder.checkChangelogDates(frame)){
             try{
                Connection conn=DBManager.getDefaultConnection();
                PreparedStatement prepStatement=conn.prepareStatement(query);
                if(changelogFields.get(0).isEnabled()){
                    prepStatement.setString(i,changelogFields.get(0).getText());
                    i++;
                }
                if(changelogFields.get(1).isEnabled()){
                    prepStatement.setString(i,changelogFields.get(1).getText());
                    i++;
                }
                if(changelogFields.get(2).isEnabled()){
                    prepStatement.setString(i,changelogFields.get(2).getText());
                    i++;
                }
                if(changelogFields.get(3).isEnabled()){
                    prepStatement.setString(i,changelogFields.get(3).getText());
                    i++;
                }
                if(changelogFields.get(4).isEnabled()){
                    prepStatement.setString(i,changelogFields.get(4).getText());
                    i++;
                }
                if(changelogDates.get(0).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJDateChooser(changelogDates.get(0)));
                    i++;
                }
                if(changelogDates.get(1).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJDateChooser(changelogDates.get(1)));
                    i++;
                }
                if(changelogCombos.get(0).isEnabled()){
                    prepStatement.setString(i, TextExtractor.fromJComboBox(changelogCombos.get(0)));
                    i++;
                }
                if(changelogCombos.get(1).isEnabled()){
                    prepStatement.setString(i, TextExtractor.fromJComboBox(changelogCombos.get(1)));
                    i++;
                }
                if(changelogCombos.get(2).isEnabled()){
                    prepStatement.setString(i, TextExtractor.fromJComboBox(changelogCombos.get(2)));
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
     * Metodo che aggiorna un singolo log.
     * @param row Array di Stringa contenente i parametri da aggiornare
     * (o lasciare invariati)
     * @param code Codice del log da modificare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int updateChangelog(String row[],String code){
        String query=builder.updateChangelogQueryBuilder();
        int i;
        try{
            Connection conn=DBManager.getDefaultConnection();
            PreparedStatement prepStatement=conn.prepareStatement(query);
            for(i = 0; i< row.length;i++){
                prepStatement.setString(i+1,row[i]);
            }
            System.out.println("Sto per eseguire");
            prepStatement.setString(i+1,code);
            if(prepStatement.executeUpdate()>0)
                return 1;
            else
                return 0;
            
        }
        catch(SQLException sqlex){
            if(sqlex.getMessage().startsWith("ORA-01407")){
                JOptionPane.showMessageDialog(null, "Stai provando a lasciare vuoto un campo obbligatorio.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().contains("CHECKCOMMITTYPE")){
                JOptionPane.showMessageDialog(null, "Il tipo modifica può essere: Modifica o Cancellazione. ",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().contains("CHECKMODIFIEDTYPE")){
                JOptionPane.showMessageDialog(null, "Tipo Modificato può essere: Package,Classe,Metodo.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().contains("CHECKLOGVALUESACCURACY")){
                JOptionPane.showMessageDialog(null, "Attenzione: Se si è scelto di modificare un determinato oggetto\n"+
                        "(Package,Classe,Metodo) deve essere compilato solo il codice di quell'oggetto.\nGli altri campi devono essere vuoti.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(null, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
   
    /**
     * Metodo che elimina un singolo log.
     * @param code Codice del log da eliminare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    
    int deleteChangelog(String code){
        String query=builder.deleteChangelogQueryBuilder();
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
     * Metodo per cercare gli autori assegnati ai log
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
     * Metodo per eliminare l'assegnazione di un autore a un log
     * @param code1 Codice Autore
     * @param code2 Codice Log
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
