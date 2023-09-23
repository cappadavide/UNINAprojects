/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import javax.swing.JTextField;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Classe che esegue le Query che riguardano gli autori.
 * @author davso
 */
public class AuthorQueryExecutor {
    private final AuthorQueryBuilder builder=new AuthorQueryBuilder();
    /**
     * Metodo che inserisce nel database un nuovo autore.
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo InsertFrame
     */
    void insertAuthor(InsertFrame frame){
        ArrayList <JTextField> authorFields=frame.getAuthorInsertFields();
        try{
            Connection conn=DBManager.getDefaultConnection();
            PreparedStatement prepStatement=conn.prepareStatement(builder.insertAuthorQueryBuilder());
            prepStatement.setString(1,authorFields.get(0).getText());
            prepStatement.setString(2,authorFields.get(1).getText());
            prepStatement.setString(3,authorFields.get(2).getText());
            System.out.println("Numero righe inserite: "+prepStatement.executeUpdate());
            conn.close();
            prepStatement.close();
            JOptionPane.showMessageDialog(frame, "Autore inserito!",
                        "Inserimento riuscito", JOptionPane.INFORMATION_MESSAGE);
            
        }
        /*catch(SQLIntegrityConstraintViolationException p){
            JOptionPane.showMessageDialog(frame, "Autore già inserito",
                        "Attenzione", JOptionPane.WARNING_MESSAGE);
        }*/
        catch(SQLException sqlex){
            if(sqlex.getMessage().contains("CHECKREGEXP"))
                JOptionPane.showMessageDialog(frame, "Formato Email non valido",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else if(sqlex.getMessage().startsWith("ORA-01400"))
                JOptionPane.showMessageDialog(frame, "Assicurati di aver compilato "
                        + "tutti i campi obbligatori",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(frame, "Autore già inserito",
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Metodo esegue la query di Ricerca Autore
     * Questa operazione richiede il frame per ricavare i campi compilati
     * @param frame Frame di tipo SearchFrame
     * @param model Model della tabella dentro la quale mostrare la query
     */
    void searchAuthor(SearchFrame frame,DefaultTableModel model){
        int i=1;
        ArrayList <JTextField> authorFields=frame.getAuthorFields();
        
        String query=builder.authorQueryBuilder(frame);
        if(builder.checkAuthorFields(frame)){
             try{
                Connection conn=DBManager.getDefaultConnection();
                PreparedStatement prepStatement=conn.prepareStatement(query);
                if(authorFields.get(0).isEnabled()){
                    prepStatement.setInt(i,Integer.parseInt(authorFields.get(0).getText()));
                    i++;
                }
                if(authorFields.get(1).isEnabled()){
                    prepStatement.setString(i,authorFields.get(1).getText());
                    i++;
                }
                if(authorFields.get(2).isEnabled()){
                    prepStatement.setString(i,authorFields.get(2).getText());
                    i++;
                }
                if(authorFields.get(3).isEnabled()){
                    prepStatement.setString(i,authorFields.get(3).getText());
                    i++;
                }
                ResultSet rs=prepStatement.executeQuery();
                TableManager tm=new TableManager();
                tm.buildRows(rs,model);
                conn.close();
                prepStatement.close();
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
     * Metodo che elimina un singolo autore.
     * @param code Codice dell'autore da eliminare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int deleteAuthor(String code){
        String query=builder.deleteAuthorQueryBuilder();
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
     * Metodo che aggiorna un singolo autore.
     * @param row Array di Stringa contenente i parametri da aggiornare
     * (o lasciare invariati)
     * @param code Codice dell'autore da modificare
     * @return Restituisce 1 se l'operazione è andata a buon fine, 0 altrimenti.
     */
    int updateAuthor(String row[],String code){
        String query=builder.updateAuthorQueryBuilder();
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
            if(sqlex.getMessage().contains("CHECKREGEXP"))
                JOptionPane.showMessageDialog(null, "Formato Email non valido",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else if(sqlex.getMessage().startsWith("ORA-00001"))
                JOptionPane.showMessageDialog(null, "Autore già inserito",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
}
