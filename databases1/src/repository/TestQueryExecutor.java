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
import repository.tools.*;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextArea;
/**
 * Classe che esegue le query che riguardano i Test e le Esecuzioni (test)
 * @author davso
 */
public class TestQueryExecutor {
    private TestQueryBuilder builder = new TestQueryBuilder();
    /**
     * Metodo che inserisce nel database prima un nuovo Test
     * e poi le varie Classi/Metodi coinvolti nel test in Test_Classi/Test_Metodi.
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo InsertFrame
     * @param classes model della lista da cui ricevare la/le classe/i
     * @param methods model della lista da cui ricavare il/i metodo/i
     */
    void insertNewTest(InsertFrame frame,DefaultListModel classes,DefaultListModel methods) throws SQLException
   {
        Connection conn=null;
        PreparedStatement prepStatement=null;
        ArrayList <JTextField> testFields=frame.getNewTestInsertFields();
        JTextArea description=frame.getTestDescriptionTextArea();
        JComboBox combo=frame.getNewTestProjectComboBox();
        if(!classes.isEmpty()||!methods.isEmpty()){
            try{
                conn=DBManager.getDefaultConnection();
                conn.setAutoCommit(false);
                prepStatement=conn.prepareStatement(builder.insertNewTestQueryBuilder());
                prepStatement.setString(1, testFields.get(0).getText());
                prepStatement.setString(2, testFields.get(1).getText());
                prepStatement.setString(3,TextExtractor.fromJComboBox(combo));
                prepStatement.setString(4,description.getText());
                System.out.println("Righe inserite: "+prepStatement.executeUpdate());
                ResultSet rs=prepStatement.executeQuery(builder.getLastTestCodeQueryBuilder());
                rs.next();
                int lastTestCode=rs.getInt(1);
                if(!classes.isEmpty()){
                    String query=builder.insertNewTestClassQueryBuilder();
                    for(int i = 0; i< classes.getSize();i++){
                        prepStatement=conn.prepareStatement(query);
                        prepStatement.setString(2,TextExtractor.getPrimaryKey((String)classes.getElementAt(i)));
                        prepStatement.setInt(1,lastTestCode);
                        System.out.println("Righe inserite: "+prepStatement.executeUpdate());
                    }
                }
                if(!methods.isEmpty()){
                    String query=builder.insertNewTestMethodQueryBuilder();
                    for(int i = 0; i< methods.getSize();i++){
                        prepStatement=conn.prepareStatement(query);
                        prepStatement.setString(2,TextExtractor.getPrimaryKey((String)methods.getElementAt(i)));
                        prepStatement.setInt(1,lastTestCode);
                        System.out.println("Righe inserite: "+prepStatement.executeUpdate());
                    }
                }
                conn.commit();
                JOptionPane.showMessageDialog(frame, "Test inserito!",
                        "Inserimento riuscito", JOptionPane.INFORMATION_MESSAGE);

            }

            catch(SQLException sqlex){
                if(sqlex.getMessage().startsWith("ORA-01400"))
                    JOptionPane.showMessageDialog(frame, "Assicurati di aver compilato "
                        + "tutti i campi obbligatori",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                else if(sqlex.getMessage().startsWith("ORA-00001"))
                    JOptionPane.showMessageDialog(frame, "Test già inserito",
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
        else{
            JOptionPane.showMessageDialog(frame, "Aggiungi almeno una classe o un metodo da testare",
                            "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo che inserisce nel database una nuova Esecuzione.
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo InsertFrame
     */
    void insertNewExecution(InsertFrame frame){
        ArrayList <JComboBox> combos = frame.getNewExecutionComboBoxes();
        JDateChooser date=frame.getExecutionDate();
        
        try{
            Connection conn=DBManager.getDefaultConnection();
            PreparedStatement prepStatement=conn.prepareStatement(builder.insertNewExecutionQueryBuilder());
            prepStatement.setString(1, TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(combos.get(0))));
            prepStatement.setString(2, TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(combos.get(1))));
            prepStatement.setString(3,TextExtractor.fromJDateChooser(date));
            prepStatement.setString(4,TextExtractor.fromJComboBox(combos.get(2)));
            System.out.println("Numero righe inserite: "+prepStatement.executeUpdate());
            JOptionPane.showMessageDialog(frame, "Esecuzione test inserita!",
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
        }
        
    }
    
    /**
     * Metodo che inserisce un nuovo autore di un test
     * @param frame Frame di tipo InsertFrame per ricavare i campi necessari
     * @param model Model della lista da cui ricavare gli autori
     */
    void insertNewTestDev(InsertFrame frame,DefaultListModel model){
        String query=builder.insertNewTestDevQueryBuilder();
        JComboBox combo=frame.getNewTestDevTestComboBox();
        String testcode=TextExtractor.getPrimaryKey(TextExtractor.fromJComboBox(combo));
        int insertedRows=0;
        try{
            Connection conn=DBManager.getDefaultConnection();
            for(int i = 0; i< model.getSize();i++){
                PreparedStatement prepStatement=conn.prepareStatement(query);
                prepStatement.setString(1,TextExtractor.getPrimaryKey((String)model.getElementAt(i)));
                prepStatement.setString(2,testcode);
                insertedRows+=prepStatement.executeUpdate();
            }
            
        }
        catch(SQLIntegrityConstraintViolationException e){
            System.out.println("Integrity constraint violation");
            JOptionPane.showMessageDialog(frame, "Uno sviluppatore è già autore del test",
                        "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
        catch(SQLException sqlex){
            JOptionPane.showMessageDialog(frame, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
        finally{
            JOptionPane.showMessageDialog(null,"Autori assegnati al test: "+insertedRows,
                        "Informazione", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * Metodo esegue la query di Ricerca Test
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella dentro la quale mostrare la query
     */
    void searchTest(SearchFrame frame,DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> testFields=frame.getTestFields();
        ArrayList <JComboBox>  testCombos=frame.getTestComboboxes();
        String query=builder.testQueryBuilder(frame);
        if(builder.checkTestFields(frame)){
             try{
                Connection conn=DBManager.getDefaultConnection();
                PreparedStatement prepStatement=conn.prepareStatement(query);
                if(testFields.get(0).isEnabled()){
                    prepStatement.setInt(i,Integer.parseInt(testFields.get(0).getText()));
                    i++;
                }
                if(testFields.get(1).isEnabled()){
                    prepStatement.setString(i,testFields.get(1).getText());
                    i++;
                }
                if(testFields.get(2).isEnabled()){
                    prepStatement.setString(i,testFields.get(2).getText());
                    i++;
                }
                if(testCombos.get(0).isEnabled()){
                    prepStatement.setString(i, TextExtractor.fromJComboBox(testCombos.get(0)));
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
     * Metodo esegue la query di Ricerca Esecuzione
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella dentro la quale mostrare la query
     */
    void searchExecution(SearchFrame frame, DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> executionFields=frame.getExecutionFields();
        ArrayList <JComboBox>  executionCombos=frame.getExecutionComboBoxes();
        ArrayList <JDateChooser> executionDates = frame.getExecutionDates();
        String query=builder.executionQueryBuilder(frame);
        if(builder.checkExecutionFields(frame)&&builder.checkExecutionDates(frame)){
             try{
                Connection conn=DBManager.getDefaultConnection();
                PreparedStatement prepStatement=conn.prepareStatement(query);
                if(executionFields.get(0).isEnabled()){
                    prepStatement.setInt(i,Integer.parseInt(executionFields.get(0).getText()));
                    i++;
                }
                if(executionFields.get(1).isEnabled()){
                    prepStatement.setString(i,executionFields.get(1).getText());
                    i++;
                }
                if(executionFields.get(2).isEnabled()){
                    prepStatement.setString(i,executionFields.get(2).getText());
                    i++;
                }
                if(executionDates.get(0).isEnabled()){
                    prepStatement.setString(i,TextExtractor.fromJDateChooser(executionDates.get(0)));
                    i++;
                }
                if(executionCombos.get(0).isEnabled()){
                    prepStatement.setString(i, TextExtractor.fromJComboBox(executionCombos.get(0)));
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
     * Metodo che aggiorna un singolo Test
     * @param row Array di Stringa contenente i parametri da aggiornare
     * (o lasciare invariati)
     * @param code Codice del Test da modificare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int updateTest(String row[],String code){
        String query=builder.updateTestQueryBuilder();
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
                JOptionPane.showMessageDialog(null, "Si sta aggiornando un Test con\ninformazioni di un Test già presente",
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
     * Metodo che aggiorna una singola Esecuzione
     * @param row Array di Stringa contenente i parametri da aggiornare
     * (o lasciare invariati)
     * @param code Codice dell'Esecuzione da modificare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int updateExecution(String row[],String code){
        String query=builder.updateExecutionQueryBuilder();
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
                JOptionPane.showMessageDialog(null, "Si sta aggiornando un Esecuzione con\ninformazioni di un Test già presente",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().startsWith("ORA-01407")){
                JOptionPane.showMessageDialog(null, "Stai provando a lasciare vuoto un campo obbligatorio.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else if(sqlex.getMessage().contains("CHECKESITOVALUE")){
                JOptionPane.showMessageDialog(null, "L'esito può essere Fallito o Riuscito",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(null, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    /**
     * Metodo che elimina un singolo Test.
     * @param code Codice del Test da eliminare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deleteTest(String code){
        String query=builder.deleteTestQueryBuilder();
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
     * Metodo che elimina una singola Esecuzione.
     * @param code Codice Esecuzione da eliminare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deleteExecution(String code){
        String query=builder.deleteExecutionQueryBuilder();
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
     * Metodo per cercare gli autori assegnati ai test
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
     * Metodo per eliminare l'assegnazione di un autore a un test
     * @param code1 Codice Autore
     * @param code2 Codice Test
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
    
    /**
     * Metodo per cercare le classi coinvolte nei test
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella da riempire con i risultati della query
     */
    void searchClassAssignment(SearchFrame frame,DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> otherFields=frame.getOtherFields();

        if(frame.checkOtherFields()){
             try{
                String query=builder.classAssignmentQueryBuilder(frame);
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
     * Metodo per eliminare l'assegnazione di una classe a un test
     * @param code1 Codice Test
     * @param code2 Codice Codice Descrittore Classe
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deleteClassAssignment(String code1,String code2){
        String query=builder.deleteClassAssignmentQueryBuilder();
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
    
    /**
     * Metodo per cercare i metodi coinvolte nei test
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella da riempire con i risultati della query
     */
     void searchMethodAssignment(SearchFrame frame,DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> otherFields=frame.getOtherFields();

        if(frame.checkOtherFields()){
             try{
                String query=builder.methodAssignmentQueryBuilder(frame);
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
     * Metodo per eliminare l'assegnazione di un metodo a un test
     * @param code1 Codice Test
     * @param code2 Codice Codice Descrittore Metodo
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deleteMethodAssignment(String code1,String code2){
        String query=builder.deleteMethodAssignmentQueryBuilder();
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

