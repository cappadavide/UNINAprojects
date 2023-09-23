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

/**
 * Classe che costruisce le query che riguardano i logs
 * @author davso
 */
public class ChangelogQueryBuilder {
    public ChangelogQueryBuilder(){
        
    }
    /**
    *Controlla che i JTextField abilitati siano compilati.
    *Questa operazione richiede l'utilizzo di un SearchFrame 
    *per ricavare i campi compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
    boolean checkChangelogFields(SearchFrame frame){
        ArrayList changelogFields=frame.getChangelogFields();
         for (int i = 0; i < changelogFields.size(); i++){
             if(CheckLengthTextField.isEmpty((JTextField) changelogFields.get(i)))
                 return false;
         }
         return true;
    }
    /**
    *Controlla che i JDateChooser abilitati siano compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
    boolean checkChangelogDates(SearchFrame frame){
        ArrayList <JDateChooser> changelogDates=frame.getChangelogDates();
        for(int i=0;i<changelogDates.size();i++){
            if(CheckLengthTextField.isEmptyDate((changelogDates.get(i))))
                    return false;
        }
        return true;
    }
    /**
     * Metodo per costruire la query di ricerca Changelog
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String changelogQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM CHANGELOG ";
        String thirdPart=thirdPartQueryBuilder(frame);
        return firstPart+secondPart+thirdPart;
    }
     
    private String thirdPartQueryBuilder(SearchFrame frame){
        ArrayList <JTextField> changelogFields=frame.getChangelogFields();
        ArrayList <JDateChooser> changelogDates=frame.getChangelogDates();
        ArrayList <JComboBox>  changelogCombos=frame.getChangelogComboboxes();
        int flag=0;
        String thirdPartQuery="";
        if(changelogFields.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODL=?";
            else{
                thirdPartQuery+="WHERE CODL=?";
                flag=1;
            }
        }
        if(changelogFields.get(1).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND DESCRIZIONE=?";
            else{
                thirdPartQuery+="WHERE DESCRIZIONE=?";
                flag=1;
            }
        }
        if(changelogFields.get(2).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODP=?";
            else{
                thirdPartQuery+="WHERE CODP=?";
                flag=1;
            }
        }
        if(changelogFields.get(3).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODC=?";
            else{
                thirdPartQuery+="WHERE CODC=?";
                flag=1;
            }
        }
        if(changelogFields.get(4).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODM=?";
            else{
                thirdPartQuery+="WHERE CODM=?";
                flag=1;
            }
        }
        if(changelogDates.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND DATAAPERTURA=?";
            else{
                thirdPartQuery+="WHERE DATAAPERTURA=?";
                flag=1;
            }
        }
        if(changelogDates.get(1).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND DATACHIUSURA=?";
            else{
                thirdPartQuery+="WHERE DATACHIUSURA=?";
                flag=1;
            }
        }
        if(changelogCombos.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND TIPOMODIFICA=?";
            else{
                thirdPartQuery+="WHERE TIPOMODIFICA=?";
                flag=1;
            }
        }
        if(changelogCombos.get(1).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND TIPOMODIFICATO=?";
            else{
                thirdPartQuery+="WHERE TIPOMODIFICATO=?";
                flag=1;
            }
        }
        if(changelogCombos.get(2).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND NOMEPROGETTO=?";
            else{
                thirdPartQuery+="WHERE NOMEPROGETTO=?";
                flag=1;
            }
        }
        thirdPartQuery+="ORDER BY CODL";
        return thirdPartQuery;
    }
    /**
     * Metodo per costruire la query di
     * inserimento di un log che riguarda un package.
     * @return Stringa della query desiderata
     */
    String insertNewChangelogPackageQueryBuilder(){
        String query="INSERT INTO CHANGELOG VALUES('',?,'','',?,?,?,?,?,?)";
        return query;
    }
    /**
     * Metodo per costruire la query di 
     * inserimento di un log che riguarda una classe.
     * @return Stringa della query desiderata
     */
    String insertNewChangelogClassQueryBuilder(){
        String query="INSERT INTO CHANGELOG VALUES('','',?,'',?,?,?,?,?,?)";
        return query;
    }
    /**
     * Metodo per costruire la query di inserimento 
     *di un log che riguarda un metodo.
     * @return Stringa della query desiderata
     */
    String insertNewChangelogMethodQueryBuilder(){
        String query="INSERT INTO CHANGELOG VALUES('','','',?,?,?,?,?,?,?)";
        return query;
    }
    /**
     * Metodo per costruire la query per la lista dei logs di un progetto specifico.
     * @return Stringa della query desiderata
     */
    String logsListBasedOnProjectQueryBuilder(String projectname){
        String query="SELECT CODL FROM CHANGELOG WHERE NOMEPROGETTO='"+projectname
                +"' ORDER BY CODL";
        return query;
    }
    /**
     * Metodo per costruire la query di inserimento di autore di un log.
     * @return Stringa della query desiderata
     */
    String insertNewChangelogDevQueryBuilder(){
        String query="INSERT INTO ASSEGNAZIONEMODIFICHE VALUES(?,?)";
        return query;
    }
    /**
     * Metodo per costruire la query di eliminazione di un log.
     * @return Stringa della query desiderata
     */
    String deleteChangelogQueryBuilder(){
        String query="DELETE FROM CHANGELOG WHERE CODL=?";
        return query;
    }
     /**
     * Metodo per costruire la query di modifica di un log.
     * @return Stringa della query desiderata
     */
    String updateChangelogQueryBuilder(){
        String query="UPDATE CHANGELOG SET CODP=?,CODC=?,CODM=?,TIPOMODIFICA=?,"
                + "TIPOMODIFICATO=?,DATAAPERTURA=?,DATACHIUSURA=?,DESCRIZIONE=?,"
                + "NOMEPROGETTO=? WHERE CODL=?";
        return query;
    }
    
    /**
     * Metodo che costruisce la query di Ricerca di AssegnazioneModifiche
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String authorsAssignmentQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM ASSEGNAZIONEMODIFICHE ";
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
            if(flag==1) thirdPartQuery+="AND CODLOG=?";
            else{
                thirdPartQuery+="WHERE CODLOG=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODAUTORE";
        return thirdPartQuery;
    }
    /**
     * Metodo per costruire la query di eliminazione in AssegnazioneModifiche
     * @return Stringa della query desiderata 
     */
    String deleteAssignmentQueryBuilder(){
        String query="DELETE FROM ASSEGNAZIONEMODIFICHE WHERE CODAUTORE=? AND CODLOG=?";
        return query;
    }
}
