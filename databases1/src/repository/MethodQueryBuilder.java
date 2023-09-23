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
 * Classe che costruisce le query che riguardano i metodi
 * @author DaviSomma
 */
public class MethodQueryBuilder {
    public MethodQueryBuilder(){
        
    }
    /**
    *Controlla che i JTextField abilitati siano compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
     boolean checkMethodDescriptorFields(SearchFrame frame){
        ArrayList methodFields=frame.getMethodDescriptorFields();
         for (int i = 0; i < methodFields.size(); i++){
             if(CheckLengthTextField.isEmpty((JTextField) methodFields.get(i)))
                 return false;
         }
         return true;
    }
    /**
    *Controlla che i JTextField abilitati siano compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
    boolean checkMethodFields(SearchFrame frame){
        ArrayList methodFields=frame.getMethodFields();
         for (int i = 0; i < methodFields.size(); i++){
             if(CheckLengthTextField.isEmpty((JTextField) methodFields.get(i)))
                 return false;
         }
         return true;
    }
    
     /**
     * Metodo per costruire la query di ricerca Descrittore Metodo
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String methodDescriptorQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM DESCRITTORE_METODO ";
        String thirdPart=thirdPartDescriptorQueryBuilder(frame);
        System.out.println(firstPart+secondPart+thirdPart);
        return firstPart+secondPart+thirdPart;
    }
    
    private String thirdPartDescriptorQueryBuilder(SearchFrame frame){
        ArrayList <JTextField> methodFields=frame.getMethodDescriptorFields();
        ArrayList <JComboBox>  methodCombos=frame.getMethodDescriptorComboboxes();
        int flag=0;
        String thirdPartQuery="";
        if(methodFields.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODDM=?";
            else{
                thirdPartQuery+="WHERE CODDM=?";
                flag=1;
            }
        }
        if(methodFields.get(1).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND NOME=?";
            else{
                thirdPartQuery+="WHERE NOME=?";
                flag=1;
            }
        }
        if(methodFields.get(2).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND TIPORITORNO=?";
            else{
                thirdPartQuery+="WHERE TIPORITORNO=?";
                flag=1;
            }
        }
        if(methodFields.get(3).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND PARAMETRIINPUT=?";
            else{
                thirdPartQuery+="WHERE PARAMETRIINPUT=?";
                flag=1;
            }
        }
        
        if(methodCombos.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND VISIBILITA=?";
            else{
                thirdPartQuery+="WHERE VISIBILITA=?";
                flag=1;
            }
        }
        if(methodCombos.get(1).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND MODIFICATORE=?";
            else{
                thirdPartQuery+="WHERE MODIFICATORE=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODDM";
        return thirdPartQuery;
    } 
    /**
     * Metodo per costruire la query di ricerca Metodo
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String methodQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM METODO ";
        String thirdPart=thirdPartQueryBuilder(frame);
        return firstPart+secondPart+thirdPart;
    }
   
     private String thirdPartQueryBuilder(SearchFrame frame){
        ArrayList <JTextField> methodFields=frame.getMethodFields();
        ArrayList <JComboBox>  methodCombos=frame.getMethodComboboxes();
        int flag=0;
        String thirdPartQuery="";
        if(methodFields.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODM=?";
            else{
                thirdPartQuery+="WHERE CODM=?";
                flag=1;
            }
        }
        if(methodFields.get(1).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODC=?";
            else{
                thirdPartQuery+="WHERE CODC=?";
                flag=1;
            }
        }
        if(methodFields.get(2).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODDM=?";
            else{
                thirdPartQuery+="WHERE CODDM=?";
                flag=1;
            }
        }
 
        if(methodCombos.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND STATO=?";
            else{
                thirdPartQuery+="WHERE STATO=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODM";
        return thirdPartQuery;
    } 
    /**
     * Metodo che costruisce una query di inserimento
     * per un nuovo descrittore metodo.
     * @return Stringa della query desiderata
     */
    String insertNewMethodDescriptorQueryBuilder(){
        String query="INSERT INTO DESCRITTORE_METODO VALUES('',?,?,?,?,?)";
        return query;
    }
    /**
     * Metodo che costruisce una query per ottenere 
     * il più recente codice descrittore metodo
     * @return Stringa della query desiderata
     */
    String getLastMethodDescriptorCodeQueryBuilder(){
        String query="SELECT CODDM FROM DESCRITTORE_METODO WHERE ROWNUM=1 ORDER BY CODDM DESC";
        return query;
    }
    /**
    * Metodo che costruisce una query di inserimento
    * per un nuovo metodo
    * @return Stringa della query desiderata
    */
    String insertNewMethodReleaseQueryBuilder(){
        String query="INSERT INTO METODO VALUES('',?,?,'Nuovo',?,?)";
        return query;
    }
    /**
    * Metodo che costruisce una query per ottenere una lista di tutti
    * i descrittori metodo
    * @return Stringa della query desiderata
    */
    String methodListQueryBuilder(){
        String query="SELECT CODDM,NOME FROM DESCRITTORE_METODO ORDER BY CODDM";
        return query;
    }
    /** 
     * Metodo che costruisce una query per ottenere una lista di Metodi
     * in base al codice release
     * @param release Codice release
     * @return Stringa della query desiderata
     */
    String methodListReleaseBasedQueryBuilder(String release){
        String query="SELECT M.CODM,DM.NOME FROM RELEASES R JOIN "
                + "PACKAGES P ON R.CODR=P.CODR JOIN CLASSE C ON P.CODP=C.CODP "
                + "JOIN METODO M ON C.CODC=M.CODC JOIN DESCRITTORE_METODO DM"
                + " ON M.CODDM=DM.CODDM WHERE R.CODR='"+release+"' ORDER BY M.CODM";
        return query;
    }
    /**
     * Metodo che costruisce una query di inserimento per un nuova associazione
     * Autore-Descrittore Metodo
     * @return Stringa della Query Desiderata
     */
    String insertNewMethodDevQueryBuilder(){
        String query="INSERT INTO SCRITTURAMETODO VALUES(?,?)";
        return query;
    }
    /**
     * Metodo che costruisce una query di cancellazione di un descrittore metodo
     * @return Stringa della query desiderata
     */
    String deleteMethodDescriptorQueryBuilder(){
        String query="DELETE FROM DESCRITITORE_METODO WHERE CODDM=?";
        return query;
    }
    /**
     * Metodo che  costruisce una query di cancellazione di un metodo
     * @return Stringa della query desiderata
     */
    String deleteMethodQueryBuilder(){
        String query="DELETE FROM METODO WHERE CODM=?";
        return query;
    }
    /**
     * Metodo che costruisce una query per aggiornare un descrittore metodo
     * @return Stringa della query desiderata
     */
    String updateMethodDescriptorQueryBuilder(){
        String query="UPDATE DESCRITTORE_METODO SET NOME=?,TIPORITORNO=?,VISIBILITA=?,"
                + "MODIFICATORE=?,PARAMETRIINPUT=? WHERE CODDM=?";
        return query;
    }
    /**
     * Metodo che costruisce una query per aggiornare un metodo
     * @return Stringa della query desiderats
     */
    String updateMethodQueryBuilder(){
        String query="UPDATE METODO SET CODC=?,CODDM=?,STATO=?,"
                + "RIGAI=?,RIGAF=? WHERE CODM=?";
        return query;
    }
    
    
    /**
     * Metodo che costruisce la query di ricerca sviluppatori autori di metodi
     * @param frame Frame di tipo SearchFrame
     * @return Una stringa della query desiderata
     */
    String authorsAssignmentQueryBuilder(SearchFrame frame){
        String firstPart="SELECT *";
        String secondPart=" FROM SCRITTURAMETODO ";
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
            if(flag==1) thirdPartQuery+="AND CODMETODO=?";
            else{
                thirdPartQuery+="WHERE CODMETODO=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODAUTORE";
        return thirdPartQuery;
    }
    /**
     * Metodo che costruisce la query di eliminazione di ScritturaMetodo
     * @return Stringa della query desiderata
     */
    String deleteAssignmentQueryBuilder(){
        String query="DELETE FROM SCRITTURAMETODO WHERE CODAUTORE=? AND CODMETODO=?";
        return query;
    }
}
