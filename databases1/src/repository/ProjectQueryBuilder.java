/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;
import com.toedter.calendar.JDateChooser;
import repository.tools.*;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTextField;
/**
 *Classe che costruisce le query che riguardano i progetti
 * @author davso
 */
public class ProjectQueryBuilder {
    public ProjectQueryBuilder(){
        
    }
    /**
    *Controlla che i JTextField abilitati siano compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
    boolean checkProjectFields(SearchFrame frame){
        ArrayList projectFields=frame.getProjectFields();
         for (int i = 0; i < projectFields.size(); i++){
             if(CheckLengthTextField.isEmpty((JTextField) projectFields.get(i)))
                 return false;
         }
         return true;
    }
    /**
    *Controlla che i JDateChooser abilitati siano compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
    boolean checkProjectDates(SearchFrame frame){
        ArrayList <JDateChooser> projectDates=frame.getProjectDates();
        for(int i=0;i<projectDates.size();i++){
            if(CheckLengthTextField.isEmptyDate((projectDates.get(i))))
                    return false;
        }
        return true;
    }
    /**
    *Controlla che i JTextField abilitati siano compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
    boolean checkReleaseFields(SearchFrame frame){
        ArrayList releaseFields=frame.getReleaseFields();
         for (int i = 0; i < releaseFields.size(); i++){
             if(CheckLengthTextField.isEmpty((JTextField) releaseFields.get(i)))
                 return false;
         }
         return true;
    }
    /**
    *Controlla che i JDateChooser abilitati siano compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
    boolean checkReleaseDates(SearchFrame frame){
        ArrayList <JDateChooser> releaseDates=frame.getReleaseDates();
        for(int i=0;i<releaseDates.size();i++){
            if(CheckLengthTextField.isEmptyDate((releaseDates.get(i))))
                    return false;
        }
        return true;
    }
     /**
     * Metodo per costruire la query di ricerca Progetto
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String projectQueryBuilder(SearchFrame frame){
        String firstPart="SELECT NOME,PATH,DATACREAZIONE,"
                + "DESCRIZIONE,DATACANCELLAZIONE";
        String secondPart=" FROM PROGETTO ";
        String thirdPart=thirdPartQueryBuilder(frame);
        return firstPart+secondPart+thirdPart;
        
    }
    private String thirdPartQueryBuilder(SearchFrame frame){
        ArrayList <JTextField> projectFields=frame.getProjectFields();
        ArrayList <JDateChooser> projectDates=frame.getProjectDates();
        int flag=0;
        String thirdPartQuery="";
        if(projectFields.get(0).isEnabled()){
            if(flag==1) thirdPartQuery+="AND NOME=?";
            else{
                thirdPartQuery+="WHERE NOME=?";
                flag=1;
            }
        }
        
        if(projectFields.get(1).isEnabled()){
            if(flag==1) thirdPartQuery+="AND PATH=?";
            else{
                thirdPartQuery+="WHERE PATH=?";
                flag=1;
            }
        }
        
        if(projectDates.get(0).isEnabled()){
            if(flag==1) thirdPartQuery+="AND DATACREAZIONE=?";
            else{
                thirdPartQuery+="WHERE DATACREAZIONE=?";
                flag=1;
            }
        }
        if(projectDates.get(1).isEnabled()){
            if(flag==1) thirdPartQuery+="AND DATACANCELLAZIONE=?";
            else{
                thirdPartQuery+="WHERE DATACANCELLAZIONE=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY NOME";
        return thirdPartQuery;
    }
    
    /**
     * Metodo per costruire la query di ricerca Releases
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String releaseQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM RELEASES ";
        String thirdPart=thirdPartReleaseQueryBuilder(frame);
        return firstPart+secondPart+thirdPart;
        
    }
    private String thirdPartReleaseQueryBuilder(SearchFrame frame){
        ArrayList <JTextField> releaseFields=frame.getReleaseFields();
        ArrayList <JDateChooser> releaseDates=frame.getReleaseDates();
        ArrayList <JComboBox> releaseCombos=frame.getReleaseComboBoxes();
        int flag=0;
        String thirdPartQuery="";
        if(releaseFields.get(0).isEnabled()){
            if(flag==1) thirdPartQuery+="AND CODR=?";
            else{
                thirdPartQuery+="WHERE CODR=?";
                flag=1;
            }
        }
        
        if(releaseFields.get(1).isEnabled()){
            if(flag==1) thirdPartQuery+="AND VERSIONE=?";
            else{
                thirdPartQuery+="WHERE VERSIONE=?";
                flag=1;
            }
        }
        if(releaseFields.get(2).isEnabled()){
            if(flag==1) thirdPartQuery+="AND PATH=?";
            else{
                thirdPartQuery+="WHERE PATH=?";
                flag=1;
            }
        }
        if(releaseDates.get(0).isEnabled()){
            if(flag==1) thirdPartQuery+="AND DATARILASCIO=?";
            else{
                thirdPartQuery+="WHERE DATARILASCIO=?";
                flag=1;
            }
        }
        if(releaseCombos.get(0).isEnabled()){
            if(flag==1) thirdPartQuery+="AND NOMEPROGETTO=?";
            else{
                thirdPartQuery+="WHERE NOMEPROGETTO=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODR";
        return thirdPartQuery;
    }
    /**
     * Metodo che costruisce una query per ottenere una lista di nomi di progetti
     * @return Stringa della query desiderata
     */
    String projectsListQueryBuilder(){
        String query = "SELECT NOME FROM PROGETTO";
        return query;
    }
    /**
     * Metodo che costruisce una query per ottenere una lista di 
     * codici release + versione di un progetto specifico
     * @param projectname Nome del progetto
     * @return Stringa della query desiderata
     */
    String projectReleasesQueryBuilder(String projectname){
        String query = "SELECT CODR,VERSIONE FROM RELEASES WHERE NOMEPROGETTO='"+
                projectname+"'";
        return query;
    }
    /**
     * Metodo che costruisce una query per ottenere una lista di 
     * codici release + versione di un progetto specifico partendo da un codice Test
     * @param code Codice Test
     * @return Stringa della query desiderata
     */
    String projectReleasesBasedOnTest(String code){
        String query = "SELECT CODR,VERSIONE FROM TEST T JOIN PROGETTO P"
                + " ON T.PROGETTOTESTATO=P.NOME JOIN RELEASES R ON P.NOME=R.NOMEPROGETTO"
                + " WHERE T.CODT='"+code+"'";
        return query;
    }
    /**
     * Metodo che costruisce una query d'inserimento di un nuovo progetto
     * @return Stringa della query desiderata
     */
    String insertProjectQueryBuilder(){
        String query="INSERT INTO PROGETTO VALUES(?,?,?,?,?)";
        return query;
    }
    /**
     * Metodo che costruisce una query d'inserimento di una nuova release
     * @return Stringa della query desiderata
     */
    String insertReleaseQueryBuilder(){
        String query="INSERT INTO RELEASES VALUES('',?,?,?,?)";
        return query;
    }
    /**
     * Metodo che costruisce una query d'inserimento per AssegnazioneProgetti
     * @return 
     */
    String insertNewProjectDevQueryBuilder(){
        String query="INSERT INTO ASSEGNAZIONEPROGETTI VALUES(?,?)";
        return query;
    }
    /**
     * Metodo che costruisce una query di cancellazione per eliminare un progetto
     * @return Stringa della query desiderata
     */
    String deleteProjectQueryBuilder(){
        String query="DELETE FROM PROGETTO WHERE NOME=?";
        return query;
    }
    /**
     * Metodo che costruisce una query per aggiornare un progetto
     * @return Stringa della query desiderata
     */
    String updateProjectQueryBuilder(){
        String query="UPDATE PROGETTO SET NOME=?,PATH=?,DATACREAZIONE=?,DESCRIZIONE=?"
                + ",DATACANCELLAZIONE=? WHERE NOME=?";
        return query;   
    }
    /**
     * Metodo che costruisce una query per cancellare una singola release
     * @return Stringa della query desiderata
     */
    String deleteReleaseQueryBuilder(){
        String query="DELETE FROM RELEASES WHERE CODR=?";
        return query;
    }
    /**
     * Metodo che costruisce una query per aggiornare una release
     * @return Stringa della query desiderata
     */
    String updateReleaseQueryBuilder(){
        String query="UPDATE RELEASES SET VERSIONE=?,PATH=?,DATARILASCIO=?,NOMEPROGETTO=?"
                + " WHERE CODR=?";
        return query;   
    }
    
     /**
     * Metodo per costruire la query di ricerca AssegnazioneProgetti
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String authorsAssignmentQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM ASSEGNAZIONEPROGETTI ";
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
            if(flag==1) thirdPartQuery+="AND NOMEPROGETTO=?";
            else{
                thirdPartQuery+="WHERE NOMEPROGETTO=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY NOMEPROGETTO";
        return thirdPartQuery;
    }
    /**
     * Metodo che costruisce una query per eliminare un'assegnazione
     * di un autore a un progetto
     * @return Stringa della query desiderata
     */
    String deleteAssignmentQueryBuilder(){
        String query="DELETE FROM ASSEGNAZIONEPROGETTI WHERE CODAUTORE=? AND NOMEPROGETTO=?";
        return query;
    }
    
    
}
