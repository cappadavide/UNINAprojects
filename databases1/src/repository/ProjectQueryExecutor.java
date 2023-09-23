package repository;
import com.toedter.calendar.JDateChooser;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import repository.tools.TextExtractor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Classe che esegue le query che riguardano i Progetti.
 * @author davso
 */
public class ProjectQueryExecutor {
    private ProjectQueryBuilder builder=new ProjectQueryBuilder();
    /**
     * Metodo che inserisce nel database un nuovo Progetto.
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo InsertFrame
     */
    void insertNewProject(InsertFrame frame){
        ArrayList <JTextField> projectFields=frame.getNewProjectInsertFields();
        JTextArea textarea=frame.getNewProjectDescription();
        ArrayList <JDateChooser> projectDates=frame.getNewProjectInsertDates();
        
        try{
            Connection conn=DBManager.getDefaultConnection();
            PreparedStatement prepStatement=conn.prepareStatement(builder.insertProjectQueryBuilder());
            prepStatement.setString(1,projectFields.get(0).getText());
            prepStatement.setString(2,projectFields.get(1).getText());
            prepStatement.setString(3,TextExtractor.fromJDateChooser(projectDates.get(0)));
            prepStatement.setString(4,textarea.getText());
            prepStatement.setString(5,TextExtractor.fromJDateChooser(projectDates.get(1)));
            System.out.println("Numero righe inserite: "+prepStatement.executeUpdate());
            JOptionPane.showMessageDialog(frame, "Progetto inserito!",
                        "Inserimento riuscito", JOptionPane.INFORMATION_MESSAGE);
        }

        catch(SQLException sqlex){
            if(sqlex.getMessage().startsWith("ORA-01400"))
                JOptionPane.showMessageDialog(frame, "Assicurati di aver compilato "
                        + "tutti i campi obbligatori",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else if(sqlex.getMessage().startsWith("ORA-00001"))
                JOptionPane.showMessageDialog(null, "Progetto già inserito",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else if(sqlex.getMessage().contains("CHECKIFDATAOK"))
                JOptionPane.showMessageDialog(null, "La data cancellazione deve essere successiva alla data creazione.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(frame, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    /**
     * Metodo che inserisce nel database una nuova Release.
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo InsertFrame
     */
    void insertNewProjectRelease(InsertFrame frame){
        ArrayList <JTextField> projectFields=frame.getNewProjectReleaseInsertFields();
        JDateChooser projectDate=frame.getNewProjectDateReleaseField();
        JComboBox projectCombo=frame.getProjectReleaseChooseProject();
        
        try{
            Connection conn=DBManager.getDefaultConnection();
            PreparedStatement prepStatement=conn.prepareStatement(builder.insertReleaseQueryBuilder());
            prepStatement.setString(1,projectFields.get(0).getText());
            prepStatement.setString(2, projectFields.get(1).getText());
            prepStatement.setString(3, TextExtractor.fromJDateChooser(projectDate));
            prepStatement.setString(4, TextExtractor.fromJComboBox(projectCombo));
            System.out.println("Numero righe inserite: "+prepStatement.executeUpdate());
            CallableStatement callStatement=conn.prepareCall("{call newrelease()}");
            callStatement.execute();
            JOptionPane.showMessageDialog(frame, "Nuova release inserita!",
                        "Inserimento riuscito", JOptionPane.INFORMATION_MESSAGE);
        }

        catch(SQLException sqlex){
            if(sqlex.getMessage().startsWith("ORA-01400"))
                JOptionPane.showMessageDialog(frame, "Assicurati di aver compilato "
                        + "tutti i campi obbligatori",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else if(sqlex.getMessage().startsWith("ORA-00001"))
                JOptionPane.showMessageDialog(frame, "Release già inserita?",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(frame, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Metodo che inserisce un nuovo autore di un progetto
     * @param frame Frame di tipo InsertFrame per ricavare i campi necessari
     * @param model Model della lista da cui ricavare gli autori
     */
    void insertNewProjectDev(InsertFrame frame,DefaultListModel model){
        String query=builder.insertNewProjectDevQueryBuilder();
        JComboBox combo=frame.getProjectDevChooseProject();
        String projectname=TextExtractor.fromJComboBox(combo);
        int insertedRows=0;
        try{
            Connection conn=DBManager.getDefaultConnection();
            for(int i = 0; i< model.getSize();i++){
                PreparedStatement prepStatement=conn.prepareStatement(query);
                prepStatement.setString(1,TextExtractor.getPrimaryKey((String)model.getElementAt(i)));
                prepStatement.setString(2,projectname);
                insertedRows+=prepStatement.executeUpdate();
            }
        }
        catch(SQLIntegrityConstraintViolationException e){
            System.out.println("Integrity constraint violation");
            JOptionPane.showMessageDialog(frame, "Un autore è stato già assegnato al progetto",
                        "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
        catch(SQLException sqlex){
            JOptionPane.showMessageDialog(frame, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
        finally{
            JOptionPane.showMessageDialog(null,"Autori assegnati al progetto: "+insertedRows,
                        "Informazione", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * Metodo esegue la query di Ricerca Progetto.
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella dentro la quale mostrare la query
     */
    void searchProject(SearchFrame frame,DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> projectFields=frame.getProjectFields();
        ArrayList <JDateChooser> projectDates=frame.getProjectDates();
        if(builder.checkProjectFields(frame)&&builder.checkProjectDates(frame)){
             try{
                String query=builder.projectQueryBuilder(frame);
                Connection conn=DBManager.getDefaultConnection();
                PreparedStatement prepStatement=conn.prepareStatement(query);
                if(projectFields.get(0).isEnabled()){
                    prepStatement.setString(i,projectFields.get(0).getText());
                    i++;
                }

                if(projectFields.get(1).isEnabled()){
                    prepStatement.setString(i,projectFields.get(1).getText());
                    i++;
                }

                if(projectDates.get(0).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJDateChooser(projectDates.get(0)));
                    i++;
                }
                if(projectDates.get(1).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJDateChooser(projectDates.get(1)));
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
     * Metodo esegue la query di Ricerca Release.
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella dentro la quale mostrare la query
     */
    void searchRelease(SearchFrame frame,DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> releaseFields=frame.getReleaseFields();
        ArrayList <JDateChooser> releaseDates=frame.getReleaseDates();
        ArrayList <JComboBox> releaseCombos=frame.getReleaseComboBoxes();
        if(builder.checkReleaseFields(frame)&&builder.checkReleaseDates(frame)){
             try{
                String query=builder.releaseQueryBuilder(frame);
                Connection conn=DBManager.getDefaultConnection();
                PreparedStatement prepStatement=conn.prepareStatement(query);
                if(releaseFields.get(0).isEnabled()){
                    prepStatement.setString(i,releaseFields.get(0).getText());
                    i++;
                }

                if(releaseFields.get(1).isEnabled()){
                    prepStatement.setString(i,releaseFields.get(1).getText());
                    i++;
                }
                if(releaseFields.get(2).isEnabled()){
                    prepStatement.setString(i,releaseFields.get(2).getText());
                    i++;
                }

                if(releaseDates.get(0).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJDateChooser(releaseDates.get(0)));
                    i++;
                }
                if(releaseCombos.get(0).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJComboBox(releaseCombos.get(0)));
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
     * Metodo che elimina un singolo Progetto.
     * @param code Nome del progetto da eliminare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deleteProject(String code){
        String query=builder.deleteProjectQueryBuilder();
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
     * Metodo che aggiorna un singolo Progetto.
     * @param row Array di Stringa contenente i parametri da aggiornare
     * (o lasciare invariati)
     * @param code Nome del progetto da modificare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int updateProject(String row[],String code){
        String query=builder.updateProjectQueryBuilder();
        int i;
        try{
            Connection conn=DBManager.getDefaultConnection();
            PreparedStatement prepStatement=conn.prepareStatement(query);
            for(i = 0; i< row.length;i++){
                System.out.println(row.length);
                System.out.println(row[i]);
                prepStatement.setString(i+1,row[i]);
            }
            System.out.println(i+1);
            prepStatement.setString(i+1,code);
            if(prepStatement.executeUpdate()>0)
                return 1;
            else
                return 0;
            
        }
        catch(SQLException sqlex){
            if(sqlex.getMessage().startsWith("ORA-00001")){
                JOptionPane.showMessageDialog(null, "Si sta aggiornando un progetto con\ninformazioni di un progetto già presente",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().startsWith("ORA-01407")){
                JOptionPane.showMessageDialog(null, "Stai provando a lasciare vuoto un campo obbligatorio.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().contains("CHECKIFDATAOK")){
                JOptionPane.showMessageDialog(null, "Data cancellazione deve essere posteriore alla data creazione.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(null, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    
    /**
     * Metodo che elimina una singola Release.
     * @param code Codice Release da eliminare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deleteRelease(String code){
        String query=builder.deleteReleaseQueryBuilder();
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
     * Metodo che aggiorna una singola Release.
     * @param row Array di Stringa contenente i parametri da aggiornare
     * (o lasciare invariati)
     * @param code Codice Release da aggiornare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int updateRelease(String row[],String code){
        String query=builder.updateReleaseQueryBuilder();
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
                JOptionPane.showMessageDialog(null, "Si sta aggiornando una releasecon\ninformazioni di una release già presente",
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
     * Metodo per cercare gli autori assegnati ai Progetti
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
     * Metodo per eliminare l'assegnazione di un autore a un Progetto
     * @param code1 Codice Autore
     * @param code2 Nome Progetto
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
