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
 * Classe che costruisce le query che riguardano i packages
 * @author davso
 */
public class PackageQueryBuilder {
    public PackageQueryBuilder(){
    
    }
    /**
    *Controlla che i JTextField abilitati siano compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
    boolean checkPackageDescriptorFields(SearchFrame frame){
        ArrayList packageFields=frame.getPackageDescriptorFields();
         for (int i = 0; i < packageFields.size(); i++){
             if(CheckLengthTextField.isEmpty((JTextField) packageFields.get(i)))
                 return false;
         }
         return true;
    }
    /**
    *Controlla che i JTextField abilitati siano compilati
    *@param frame Frame di tipo SearchFrame
    *@return Esito booleano del controllo
    */
    boolean checkPackageFields(SearchFrame frame){
        ArrayList packageFields=frame.getPackageFields();
         for (int i = 0; i < packageFields.size(); i++){
             if(CheckLengthTextField.isEmpty((JTextField) packageFields.get(i)))
                 return false;
         }
         return true;
    }
    /**
     * Metodo per costruire la query di ricerca Descrittore_Package
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String packageDescriptorQueryBuilder(SearchFrame frame){
        String firstPart="SELECT CODDP,NOME,"
                + "CODPACKCONTENENTE,NOMEPROGETTO";
        String secondPart=" FROM DESCRITTORE_PACKAGES ";
        String thirdPart=thirdPartDescriptorQueryBuilder(frame);
        return firstPart+secondPart+thirdPart;
    }
    
    private String thirdPartDescriptorQueryBuilder(SearchFrame frame){
        ArrayList <JTextField> packageFields=frame.getPackageDescriptorFields();
        ArrayList <JComboBox> packageCombos=frame.getPackageDescriptorComboboxes();
        int flag=0;
        String thirdPartQuery="";
        if(packageFields.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODDP=?";
            else{
                thirdPartQuery+="WHERE CODDP=?";
                flag=1;
            }
        }
        if(packageFields.get(1).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND NOME=?";
            else{
                thirdPartQuery+="WHERE NOME=?";
                flag=1;
            }
        }
        if(packageFields.get(2).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODPACKCONTENENTE=?";
            else{
                thirdPartQuery+="WHERE CODPACKCONTENENTE=?";
                flag=1;
            }
        }
        if(packageCombos.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND NOMEPROGETTO=?";
            else{
                thirdPartQuery+="WHERE NOMEPROGETTO=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODDP";
        return thirdPartQuery;
    }
    /**
     * Metodo per costruire la query di ricerca Package
     * @param frame Frame di tipo SearchFrame
     * @return Stringa della query desiderata
     */
    String packageQueryBuilder(SearchFrame frame){
        String firstPart="SELECT CODP,CODR,CODDP,STATO";
        String secondPart=" FROM PACKAGES ";
        String thirdPart=thirdPartQueryBuilder(frame);
        System.out.println(firstPart+secondPart+thirdPart);
        return firstPart+secondPart+thirdPart;
    }
    
    private String thirdPartQueryBuilder(SearchFrame frame){
        ArrayList <JTextField> packageFields=frame.getPackageFields();
        ArrayList <JComboBox> packageCombos=frame.getPackageComboboxes();
        int flag=0;
        String thirdPartQuery="";
        if(packageFields.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODP=?";
            else{
                thirdPartQuery+="WHERE CODP=?";
                flag=1;
            }
        }
        if(packageFields.get(1).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODR=?";
            else{
                thirdPartQuery+="WHERE CODR=?";
                flag=1;
            }
        }
        if(packageFields.get(2).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND CODDP=?";
            else{
                thirdPartQuery+="WHERE CODDP=?";
                flag=1;
            }
        }
        if(packageCombos.get(0).isEnabled()){
            if(flag==1)
                thirdPartQuery+="AND STATO=?";
            else{
                thirdPartQuery+="WHERE STATO=?";
                flag=1;
            }
        }
        thirdPartQuery+=" ORDER BY CODP";
        return thirdPartQuery;
    }
    /**
     * Metodo che costruisce una query per ottenere una lista
     * di descrittori packages che sono stati rilasciati nella release specificata
     * @param release Codice Release
     * @return Stringa della query desiderata
     */
    String packageListReleaseBasedQueryBuilder(String release){
        String query="SELECT DP.CODDP,DP.NOME FROM RELEASES R "
                + "JOIN PACKAGES P ON R.CODR=P.CODR JOIN DESCRITTORE_PACKAGES DP"
                + " ON P.CODDP=DP.CODDP WHERE R.CODR='"+release+"'";
        return query;
    }
    /**
     * Metodo che costruisce una query per ottenere una lista
     * di packages che sono stati rilasciati nella release specificata
     * @param release Codice Release
     * @return Stringa della query desiderata
     */
    String packageListQueryBuilder(String release){
        String query="SELECT P.CODP,DP.NOME FROM RELEASES R "
                + "JOIN PACKAGES P ON R.CODR=P.CODR JOIN DESCRITTORE_PACKAGES DP"
                + " ON P.CODDP=DP.CODDP WHERE R.CODR='"+release+"'";
        return query;
    }
    /**
     * Metodo che costruisce una query d'inserimento per un nuovo Descrittore Package
     * @return Stringa della query desiderata
     */
    String insertNewPackageQueryBuilder(){
        String query="INSERT INTO DESCRITTORE_PACKAGES VALUES ('',?,?,?)";
        return query;
    }
    /**
     * Metodo che costruisce una query per ottenere il più recente codice descrittore package
     * @return Stringa della query desiderata
     */
    String getLastPackageDescriptorCodeQueryBuilder(){
        String query="SELECT CODDP FROM DESCRITTORE_PACKAGES WHERE ROWNUM=1 ORDER BY CODDP DESC";
        return query;
    }
    /**
     * Metodo per costruire una query per ottenere il corrispondente codice Package
     * di un descrittore Package in una determinata release.
     * @return Stringa della query desiderata
     */
    String getPackageCodeQueryBuilder(){
        String query="SELECT CODP FROM PACKAGES WHERE CODDP=? AND CODR=?";
        return query;
    }
    /**
     * Metodo che costruisce una query di inserimento per un nuovo descrittore package.
     * @return Stringa della query desiderata.
     */
    String insertNewPackageReleaseQueryBuilder(){
        String query="INSERT INTO PACKAGES VALUES('',?,?,'Nuovo')";
        return query;
    }
    /**
     * Metodo che costruisce una query di cancellazione per un descrittore package.
     * @return Stringa della query desiderata
     */
    String deletePackageDescriptorQueryBuilder(){
        String query="DELETE FROM DESCRITTORE_PACKAGES WHERE CODDP=?";
        return query;
    }
    /**
     * Metodo che costruisce una query per aggiornare un descrittore package.
     * @return Stringa della query desiderata
     */
    String updatePackageDescriptorQueryBuilder(){
        String query="UPDATE DESCRITTORE_PACKAGES SET NOME=?,CODPACKCONTENENTE=?,"
                + "NOMEPROGETTO=? WHERE CODDP=?";
        return query;
    }
    /**
     * Metodo che costruisce una query di cancellazione per un Package
     * @return Stringa della query desiderata
     */
    String deletePackageQueryBuilder(){
        String query="DELETE FROM PACKAGES WHERE CODP=?";
        return query;
    }
    /**
     * Metodo che costruisce una query per aggiornare un package
     * @return Stringa della query desiderata
     */
    String updatePackageQueryBuilder(){
        String query="UPDATE PACKAGES SET CODR=?,CODDP=?,"
                + "STATO=? WHERE CODP=?";
        return query;
    }
            
}
