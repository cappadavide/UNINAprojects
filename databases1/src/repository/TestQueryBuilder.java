/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.toedter.calendar.JDateChooser;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import repository.tools.CheckLengthTextField;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Classe che costruisce le query che riguardano i tests
 * @author davso
 */
public class TestQueryBuilder {
    public TestQueryBuilder(){
        
    }
    /**
    *Controlla che i JTextField abilitati siano compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
    boolean checkTestFields(SearchFrame frame){
        ArrayList testFields=frame.getTestFields();
         for (int i = 0; i < testFields.size(); i++){
             if(CheckLengthTextField.isEmpty((JTextField) testFields.get(i)))
                 return false;
         }
         return true;
    }
    /**
    *Controlla che i JTextField abilitati siano compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
    boolean checkExecutionFields(SearchFrame frame){
        ArrayList executionFields=frame.getExecutionFields();
         for (int i = 0; i < executionFields.size(); i++){
             if(CheckLengthTextField.isEmpty((JTextField) executionFields.get(i)))
                 return false;
         }
         return true;
    }
    /**
    *Controlla che i JDateChooser abilitati siano compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
     boolean checkExecutionDates(SearchFrame frame){
        ArrayList <JDateChooser> executionDates=frame.getExecutionDates();
        for(int i=0;i<executionDates.size();i++){
            if(CheckLengthTextField.isEmptyDate((executionDates.get(i))))
                    return false;
        }
        return true;
    }
    
    /**
     * Metodo per costruire la query di ricerca Test
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String testQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM TEST ";//Da vedere bene
        String thirdPart=thirdPartQueryBuilder(frame);
        return firstPart+secondPart+thirdPart;
    } 
     
    private String thirdPartQueryBuilder(SearchFrame frame){//Da modificare le query
        ArrayList <JTextField> testFields=frame.getTestFields();
        ArrayList <JComboBox>  testCombos=frame.getTestComboboxes();
        int flag=0;
        String thirdPartQuery="";
        if(testFields.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODT=?";
            else{
                thirdPartQuery+="WHERE CODT=?";
                flag=1;
            }
        }
        if(testFields.get(1).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND NOME=?";
            else{
                thirdPartQuery+="WHERE NOME=?";
                flag=1;
            }
        }
        if(testFields.get(2).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND PATH=?";
            else{
                thirdPartQuery+="WHERE PATH=?";
                flag=1;
            }
        }
        if(testCombos.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND PROGETTOTESTATO=?";
            else{
                thirdPartQuery+="WHERE PROGETTOTESTATO=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODT";
        return thirdPartQuery;
    }
    /**
     * Metodo per costruire la query di ricerca Esecuzione
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String executionQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM ESECUZIONI ";
        String thirdPart=thirdPartExecutionQueryBuilder(frame);
        return firstPart+secondPart+thirdPart;
    } 
     
    private String thirdPartExecutionQueryBuilder(SearchFrame frame){
        ArrayList <JTextField> executionFields=frame.getExecutionFields();
        ArrayList <JComboBox>  executionCombos=frame.getExecutionComboBoxes();
        ArrayList <JDateChooser> executionDates = frame.getExecutionDates();
        int flag=0;
        String thirdPartQuery="";
        if(executionFields.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODE=?";
            else{
                thirdPartQuery+="WHERE CODE=?";
                flag=1;
            }
        }
        if(executionFields.get(1).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND TESTESEGUITO=?";
            else{
                thirdPartQuery+="WHERE TESTESEGUITO=?";
                flag=1;
            }
        }
        if(executionFields.get(2).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODRELEASETESTATA=?";
            else{
                thirdPartQuery+="WHERE CODRELEASETESTATA=?";
                flag=1;
            }
        }
        if(executionDates.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND DATAESECUZIONE=?";
            else{
                thirdPartQuery+="WHERE DATAESECUZIONE=?";
                flag=1;
            }
        }
        if(executionCombos.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND ESITO=?";
            else{
                thirdPartQuery+="WHERE ESITO=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODE";
        return thirdPartQuery;
    }
    /*
    void comboBoxQueryBuilder(JComboBox combobox){
        String query = "SELECT NOME FROM PROGETTO";
        try {
            Connection con = DBManager.getDefaultConnection();
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                combobox.addItem(rs.getString("NOME"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to Connect to Database", "Error Connection",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }*/
    /**
     * Metodo che costruisce la query d'inserimento per un nuovo test
     * @return Stringa della query desiderata
     */
    String insertNewTestQueryBuilder(){
        String query="INSERT INTO TEST VALUES('',?,?,?,?)";
        return query;
    }
    /**
     * Metodo che costruisce la query per ottenere il più recente codice test
     * @return Stringa della query desiderata
     */
    String getLastTestCodeQueryBuilder(){
        String query="SELECT CODT FROM TEST WHERE ROWNUM=1 ORDER BY CODT DESC";
        return query;
    }
    /**
     * Metodo che costruisce la query per inserire in Test_Classi
     * @return Stringa della query desiderata
     */
    String insertNewTestClassQueryBuilder(){
        String query="INSERT INTO TEST_CLASSI VALUES(?,?)";
        return query;
    }
    /**
     * Metodo che costruisce la query per inserire in Test_Metodi
     * @return Stringa della query desiderata
     */
    String insertNewTestMethodQueryBuilder(){
        String query="INSERT INTO TEST_METODI VALUES(?,?)";
        return query;
    }
    /**
     * Metodo che costruisce la query per ottenere una lista codice+nome
     * dei test di uno specifico progetto
     * @param projectname Nome del progetto
     * @return Stringa della query desiderata
     */
    String testBasedOnProjectList(String projectname){
        String query="SELECT CODT,NOME FROM TEST"
                + " WHERE PROGETTOTESTATO='"+projectname+"' ORDER BY CODT";
        return query;
    }
    /**
     * Metodo che costruisce la query per ottenere una lista codice+nome
     * di tutti i test
     * @return Stringa della query desiderata
     */
    String testListQueryBuilder(){
        String query="SELECT CODT,NOME FROM TEST ORDER BY CODT";
        return query;
    }
    
    /**
     * Metodo che costruisce la query per inserire un nuovo autore di un test
     * @return Stringa della query desiderata
     */
    String insertNewTestDevQueryBuilder(){
        String query="INSERT INTO SCRITTURATEST VALUES(?,?)";
        return query;
    }
    /**
     * Metodo che costruisce la query per inserire una nuova esecuzione
     * @return Stringa della query desiderata
     */
    String insertNewExecutionQueryBuilder(){
        String query="INSERT INTO ESECUZIONI VALUES('',?,?,?,?)";
        return query;
    }
    /**
     * Metodo che costruisce la query per aggiornare un test
     * @return Stringa della query desiderata
     */
    String updateTestQueryBuilder(){
        String query="UPDATE TEST SET NOME=?,PATH=?,PROGETTOTESTATO=?,DESCRIZIONE=? WHERE CODT=?";
        return query;
    }
    /**
     * Metodo che costruisce la query per eliminare un test
     * @return Stringa della query desiderata
     */
    String deleteTestQueryBuilder(){
        String query="DELETE FROM TEST WHERE CODT=?";
        return query;
    }
    /**
     * Metodo che costruisce la query per aggiornare un'esecuzione
     * @return Stringa della query desiderata
     */
    String updateExecutionQueryBuilder(){
        String query="UPDATE ESECUZIONI SET TESTESEGUITO=?,CODRELEASETESTATA=?,"
                + "DATAESECUZIONE=?,ESITO=? WHERE CODE=?";
        return query;
    }
    /**
     * Metodo che costruisce la query per eliminare un'esecuzione
     * @return Stringa della query desiderata
     */
    String deleteExecutionQueryBuilder(){
        String query="DELETE FROM ESECUZIONI WHERE CODE=?";
        return query;
    }
    
    
    /**
     * Metodo per costruire la query di ricerca ScritturaTest
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String authorsAssignmentQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM SCRITTURATEST ";
        String thirdPart=thirdPartAssignmentQueryBuilder(frame);
        System.out.println(firstPart+secondPart+thirdPart);
        return firstPart+secondPart+thirdPart;
    }
    private String thirdPartAssignmentQueryBuilder(SearchFrame frame){
        ArrayList <JTextField> otherFields=frame.getOtherFields();
        int flag=0;
        String thirdPartQuery="";
        if(otherFields.get(0).isEnabled()){
            if(flag==1) thirdPartQuery+="AND CODAUTORE=?";
            else{
                thirdPartQuery+="WHERE CODAUTORE=?";
                flag=1;
            }
        }
        if(otherFields.get(1).isEnabled()){
            if(flag==1) thirdPartQuery+="AND CODTEST=?";
            else{
                thirdPartQuery+="WHERE CODTEST=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODAUTORE";
        return thirdPartQuery;
    }
    String deleteAssignmentQueryBuilder(){
        String query="DELETE FROM SCRITTURATEST WHERE CODAUTORE=? AND CODTEST=?";
        return query;
    }
    /**
     * Metodo per costruire la query di ricerca Test_Classi
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String classAssignmentQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM TEST_CLASSI ";
        String thirdPart=thirdPartClassAssignmentQueryBuilder(frame);
        System.out.println(firstPart+secondPart+thirdPart);
        return firstPart+secondPart+thirdPart;
    }
    private String thirdPartClassAssignmentQueryBuilder(SearchFrame frame){
        ArrayList <JTextField> otherFields=frame.getOtherFields();
        int flag=0;
        String thirdPartQuery="";
        if(otherFields.get(0).isEnabled()){
            if(flag==1) thirdPartQuery+="AND CODTEST=?";
            else{
                thirdPartQuery+="WHERE CODTEST=?";
                flag=1;
            }
        }
        if(otherFields.get(1).isEnabled()){
            if(flag==1) thirdPartQuery+="AND CODCLASSE=?";
            else{
                thirdPartQuery+="WHERE CODCLASSE=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODTEST";
        return thirdPartQuery;
    }
    /**
     * Metodo che costruisce la query per eliminare in Test_Classi
     * @return Stringa della query desiderata
     */
    String deleteClassAssignmentQueryBuilder(){
        String query="DELETE FROM TEST_CLASSI WHERE CODTEST=? AND CODCLASSE=?";
        return query;
    }
    
    /**
     * Metodo per costruire la query di ricerca Test_Metodi
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String methodAssignmentQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM TEST_METODI ";
        String thirdPart=thirdPartMethodAssignmentQueryBuilder(frame);
        System.out.println(firstPart+secondPart+thirdPart);
        return firstPart+secondPart+thirdPart;
    }
    private String thirdPartMethodAssignmentQueryBuilder(SearchFrame frame){
        ArrayList <JTextField> otherFields=frame.getOtherFields();
        int flag=0;
        String thirdPartQuery="";
        if(otherFields.get(0).isEnabled()){
            if(flag==1) thirdPartQuery+="AND CODTEST=?";
            else{
                thirdPartQuery+="WHERE CODTEST=?";
                flag=1;
            }
        }
        if(otherFields.get(1).isEnabled()){
            if(flag==1) thirdPartQuery+="AND CODMETODO=?";
            else{
                thirdPartQuery+="WHERE CODMETODO=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODTEST";
        return thirdPartQuery;
    }
    /**
     * Metodo che costruisce la query per eliminare in Test_Metodi
     * @return Stringa della query desiderata
     */
    String deleteMethodAssignmentQueryBuilder(){
        String query="DELETE FROM TEST_METODI WHERE CODTEST=? AND CODMETODO=?";
        return query;
    }
}
