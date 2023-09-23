/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import repository.tools.CheckLengthTextField;
/**
 * Classe che costruisce le query che riguardano le classi
 * @author davso
 */
public class ClassQueryBuilder {
    public ClassQueryBuilder(){
        
    }
    /**
    *Controlla che i JTextField abilitati siano compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
    boolean checkClassDescriptorFields(SearchFrame frame){
        ArrayList classFields=frame.getClassDescriptorFields();
         for (int i = 0; i < classFields.size(); i++){
             if(CheckLengthTextField.isEmpty((JTextField) classFields.get(i)))
                 return false;
         }
         return true;
    }
    /**
    *Controlla che i JTextField abilitati siano compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
    boolean checkClassFields(SearchFrame frame){
        ArrayList classFields=frame.getClassFields();
         for (int i = 0; i < classFields.size(); i++){
             if(CheckLengthTextField.isEmpty((JTextField) classFields.get(i)))
                 return false;
         }
         return true;
    }
    
    /**
     * Metodo per costruire la query di ricerca Descrittore_Classe
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String classDescriptorQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM DESCRITTORE_CLASSE ";
        String thirdPart=thirdPartDescriptorQueryBuilder(frame);
        return firstPart+secondPart+thirdPart;
    }
    
    
    private String thirdPartDescriptorQueryBuilder(SearchFrame frame){
        ArrayList <JTextField> classFields=frame.getClassDescriptorFields();
        ArrayList <JComboBox>  classCombos=frame.getClassDescriptorComboboxes();
        int flag=0;
        String thirdPartQuery="";
        if(classFields.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODDC=?";
            else{
                thirdPartQuery+="WHERE CODDC=?";
                flag=1;
            }
        }
        if(classFields.get(1).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND NOME=?";
            else{
                thirdPartQuery+="WHERE NOME=?";
                flag=1;
            }
        }
        if(classFields.get(2).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND NOMESOURCE=?";
            else{
                thirdPartQuery+="WHERE NOMESOURCE=?";
                flag=1;
            }
        }
        if(classFields.get(3).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODSUPERCLASSE=?";
            else{
                thirdPartQuery+="WHERE CODSUPERCLASSE=?";
                flag=1;
            }
        }
        if(classFields.get(4).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODPACKAGE=?";
            else{
                thirdPartQuery+="WHERE CODPACKAGE=?";
                flag=1;
            }
        }
        if(classCombos.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND VISIBILITA=?";
            else{
                thirdPartQuery+="WHERE VISIBILITA=?";
                flag=1;
            }
        }
        if(classCombos.get(1).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND MODIFICATORE=?";
            else{
                thirdPartQuery+="WHERE MODIFICATORE=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODDC ";
        return thirdPartQuery;
    }
    
    /**
     * Metodo per costruire la query di ricerca Classe
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String classQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM CLASSE ";
        String thirdPart=thirdPartQueryBuilder(frame);
        return firstPart+secondPart+thirdPart;
    }
    
    
    private String thirdPartQueryBuilder(SearchFrame frame){
        ArrayList <JTextField> classFields=frame.getClassFields();
        ArrayList <JComboBox>  classCombos=frame.getClassComboboxes();
        int flag=0;
        String thirdPartQuery="";
        if(classFields.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODC=?";
            else{
                thirdPartQuery+="WHERE CODC=?";
                flag=1;
            }
        }
        if(classFields.get(1).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODP=?";
            else{
                thirdPartQuery+="WHERE CODP=?";
                flag=1;
            }
        }
        if(classFields.get(2).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODDC=?";
            else{
                thirdPartQuery+="WHERE CODDC=?";
                flag=1;
            }
        }
        if(classFields.get(3).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODCLASSECONTENENTE=?";
            else{
                thirdPartQuery+="WHERE CODCLASSECONTENENTE=?";
                flag=1;
            }
        }
        if(classCombos.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND STATO=?";
            else{
                thirdPartQuery+="WHERE STATO=?";
                flag=1;
            }
        }

        thirdPartQuery+=" ORDER BY CODC ";
        return thirdPartQuery;
    }
    
    
    /**
     * Metodo che costruisce una query per ottenere una lista di classi precisa
     * in base al descrittore package e alla release.
     * @param release
     * @param codpackage
     * @return Una stringa della query desiderata
     */
    String classListReleaseBasedQueryBuilder(String release,String codpackage){
        String query="SELECT C.CODC,DC.NOME FROM RELEASES R "
                + "JOIN PACKAGES P ON R.CODR=P.CODR "
                + "JOIN CLASSE C ON P.CODP=C.CODP JOIN DESCRITTORE_CLASSE DC "
                + "ON C.CODDC=DC.CODDC"
                +" WHERE R.CODR='"+release+"' AND P.CODDP='"+codpackage+"'";
        
        return query;
    }
    /**
     * Metodo che costruisce una query per ottenere una lista di classi precisa
     * in base alla release.
     * @param release
     * @return Una stringa della query desiderata
     */
    String classListReleaseBasedQueryBuilder(String release){
        String query="SELECT C.CODC,DC.NOME FROM RELEASES R "
                + "JOIN PACKAGES P ON R.CODR=P.CODR "
                + "JOIN CLASSE C ON P.CODP=C.CODP JOIN DESCRITTORE_CLASSE DC "
                + "ON C.CODDC=DC.CODDC"
                +" WHERE R.CODR='"+release+"'";
        
        return query;
    }
    /**
     * Metodo che costruisce una query per ottenere una lista di
     * descrittori classi in base a un descrittore package preciso
     * @param codpackage
     * @return Una stringa della query desiderata
     */
    String classListPackageBasedQueryBuilder(String codpackage){
        String query="SELECT DC.CODDC,DC.NOME FROM DESCRITTORE_CLASSE DC"
                + " WHERE DC.CODPACKAGE='"+codpackage+"'";
        return query;
    }
    /**
     * Metodo che costruisce una query per ottenere una lista di
     * descrittori classi in base al progetto
     * @param projectname
     * @return Una stringa della query desiderata
     */
    String classListProjectBasedQueryBuilder(String projectname){
        String query="SELECT DC.CODDC,DC.NOME FROM DESCRITTORE_PACKAGES DP JOIN"
                + " DESCRITTORE_CLASSE DC ON DP.CODDP=DC.CODPACKAGE WHERE "
                + "DP.NOMEPROGETTO='"+projectname+"'"+" ORDER BY DC.CODDC";
        return query;
    }
    /**
     * Metodo che costruisce la query di inserimento di un Descrittore Classe
     * @return Una stringa della query desiderata
     */
    String insertNewClassDescriptorQueryBuilder(){
        String query="INSERT INTO DESCRITTORE_CLASSE VALUES('',?,?,?,?,?,?)";
        return query;
    }
    /**
     * Metodo che costruisce la query per ottenere l'ultimo codice descrittore classe
     * @return Una stringa della query desiderata
     */
    String getLastClassDescriptorCodeQueryBuilder(){
        String query="SELECT CODDC FROM DESCRITTORE_CLASSE WHERE ROWNUM=1 ORDER BY CODDC DESC";
        return query;
    }
    /**
     * Metodo che costruisce la query di inserimento di una Classe
     * @return Una stringa della query desiderata
     */
    String insertNewClassReleaseQueryBuilder(){
        String query="INSERT INTO CLASSE VALUES('',?,?,?,'Nuovo',?,?)";
        return query;
    }
    /**
     * Metodo che costruisce la query di inserimento di ScritturaClassi
     * @return Una stringa della query desiderata
     */
    String insertNewClassDevQueryBuilder(){
        String query="INSERT INTO SCRITTURACLASSI VALUES(?,?)";
        return query;
    }
    /**
     * Metodo che costruisce la query di eliminazione di un descrittore classe.
     * @return Una stringa della query desiderata
     */
    String deleteClassDescriptorQueryBuilder(){
        String query="DELETE FROM DESCRITTORE_CLASSE WHERE CODDC=?";
        return query;
    }
    /**
     * Metodo che costruisce la query di eliminazione di una classe.
     * @return Una stringa della query desiderata
     */
    String deleteClassQueryBuilder(){
        String query="DELETE FROM CLASSE WHERE CODC=?";
        return query;
    }
    /**
     * Metodo che costruisce la query di modifica di descrittore classe.
     * @return Una stringa della query desiderata
     */
    String updateClassDescriptorQueryBuilder(){
        String query="UPDATE DESCRITTORE_CLASSE SET NOME=?,VISIBILITA=?,MODIFICATORE=?,"
                + "NOMESOURCE=?,CODSUPERCLASSE=?,CODPACKAGE=? WHERE CODDC=?";
        return query;
    }
    /**
     * Metodo che costruisce la query di modifica di classe.
     * @return Una stringa della query desiderata
     */
    String updateClassQueryBuilder(){
        String query="UPDATE CLASSE SET CODP=?,CODDC=?,CODCLASSECONTENENTE=?,"
                + "STATO=?,RIGAI=?,RIGAF=? WHERE CODC=?";
        return query;
    }
    
    /**
     * Metodo che costruisce la query di ricerca sviluppatori autori di classi
     * @param frame Frame di tipo SearchFrame
     * @return Una stringa della query desiderata
     */
    String authorsAssignmentQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM SCRITTURACLASSI ";
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
            if(flag==1) thirdPartQuery+="AND CODCLASSE=?";
            else{
                thirdPartQuery+="WHERE CODCLASSE=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODAUTORE";
        return thirdPartQuery;
    }
    /**
     * Metodo che costruisce la query di eliminazione di ScritturaClassi
     * @return Una stringa della query desiderata
     */
    String deleteAssignmentQueryBuilder(){
        String query="DELETE FROM SCRITTURACLASSI WHERE CODAUTORE=? AND CODCLASSE=?";
        return query;
    }
}

