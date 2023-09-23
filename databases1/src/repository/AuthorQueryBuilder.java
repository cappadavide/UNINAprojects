/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import repository.tools.*;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 * Classe che costruisce le query che riguardano gli sviluppatori
 * @author davso
 */
public class AuthorQueryBuilder {  
    public AuthorQueryBuilder(){
        
    }
    /**
    *Controlla che i JTextField abilitati siano compilati.
    *Questa operazione richiede l'utilizzo di un SearchFrame
    *per ricavare i campi compilati.
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
    boolean checkAuthorFields(SearchFrame frame){
        ArrayList authorFields=frame.getAuthorFields();
         for (int i = 0; i < authorFields.size(); i++){
             if(CheckLengthTextField.isEmpty((JTextField) authorFields.get(i)))
                 return false;
         }
         return true;
    }
   
    /**
     * Metodo per costruire la query di ricerca Autore
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String authorQueryBuilder(SearchFrame frame){
        String firstPart="SELECT CODA,NOME,COGNOME,EMAIL";
        String secondPart=" FROM AUTORE ";
        String thirdPart=thirdPartQueryBuilder(frame);
        return firstPart+secondPart+thirdPart;
        
    }
   
    private String thirdPartQueryBuilder(SearchFrame frame){
        ArrayList <JTextField> authorFields=frame.getAuthorFields();
        int flag=0;
        String thirdPartQuery="";
        //Costruiamo la terza parte
        if(authorFields.get(0).isEnabled()){
            if(flag==1) thirdPartQuery+="AND CODA=?";
            else{
                thirdPartQuery+="WHERE CODA=?";
                flag=1;
            }
        }
        if(authorFields.get(1).isEnabled()){
            if(flag==1) thirdPartQuery+="AND NOME=?";
            else{
                thirdPartQuery+="WHERE NOME=?";
                flag=1;
            }
        }
        if(authorFields.get(2).isEnabled()){
            if(flag==1) thirdPartQuery+="AND COGNOME=?";
            else{
                thirdPartQuery+="WHERE COGNOME=?";
                flag=1;
            }
        }
        if(authorFields.get(3).isEnabled()){
            if(flag==1) thirdPartQuery+="AND EMAIL=?";
            else{
                thirdPartQuery+="WHERE EMAIL=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODA";
        return thirdPartQuery;
    }
    /**
     * Metodo per costruire una query per ottenere la lista degli autori
     * @param model 
     */
    String authorsListQueryBuilder(){
        String query = "SELECT CODA,NOME,COGNOME,EMAIL FROM AUTORE ORDER BY CODA";
        return query;
    }
    /**
     * Metodo per costruire la query di inserimento Autore
     * @return Stringa della query desiderata
     */
    String insertAuthorQueryBuilder(){
        String query="INSERT INTO AUTORE VALUES('',?,?,?)";
        return query;
    }
    /**
     * Metodo per costruire la query per la lista di Autori di un progetto specifico
     * @param projectname Nome del progetto
     * @return Stringa della query desiderata
     */
    String authorsBasedOnProjectListQueryBuilder(String projectname){
        String query = "SELECT A.CODA,A.NOME,A.COGNOME,A.EMAIL FROM AUTORE A "
                + "JOIN ASSEGNAZIONEPROGETTI AP ON A.CODA=AP.CODAUTORE "
                + "WHERE NOMEPROGETTO='"+projectname+"'"+" ORDER BY A.CODA";
        return query;
    }
    /**
     * Metodo per costruire la query di eliminazione Autore
     * @return Stringa della query desiderata
     */
    String deleteAuthorQueryBuilder(){
        String query="DELETE FROM AUTORE WHERE CODA=?";
        return query;
    }
    /**
     * Metodo per costruire la query di aggiornamento Autore
     * @return Stringa della query desiderata
     */
    String updateAuthorQueryBuilder(){
        String query="UPDATE AUTORE SET NOME=?,COGNOME=?,EMAIL=? WHERE CODA=?";
        return query;
    }
    
}