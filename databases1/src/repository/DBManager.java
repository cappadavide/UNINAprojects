/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;
import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;
/**
 * Classe che si occupa di gestire la connessione con il Database.
 * @author davso
 */
public class DBManager {
    /**
    * Indirizzo del server Oracle.
    */
   static public String host = "localhost";
   /**
    * Nome del servizio.
    */
   static public String servizio = "xe";
   /**
    * Porta utilizzata per la connessione.
    */
   static public int porta = 1521;
   /**
    * Nome utente per la connessione.
    */
   /**
    * Nome dello schema contenente le tabelle/viste/procedure cui si vuole
    * accedere; coincide di solito con il nome utente.
    */
   //static public String schema = "repository";
   static public String user = "";
   /**
    * Password corrispondente all'utente specificato.
    */
   static public String password = "";
  
    /**
    * Oggetto DataSource utilizzato nella connessione al DB
    */
   static private OracleDataSource ods;
   /**
    * Variabile che contiene la connessione attiva, se esiste
    */
   static private Connection defaultConnection;
   
   /**
    * Restituisce la connessione di default al DB.
    *
    * @return Connessione di default (quella gi&agrave; attiva, o una nuova
    * ottenuta in base ai parametri di connessione attualmente impostati
    * @throws SQLException In caso di problemi di connessione
    */
   static public Connection getDefaultConnection() throws SQLException {
      if (defaultConnection == null || defaultConnection.isClosed()) {
         defaultConnection = nuovaConnessione();
//         System.out.println("nuova connessione");
//      } else {
//         System.out.println("ricicla connessione");
      }

      return defaultConnection;
   }

   /**
    * Imposta una connessione specificata in input come default.
    *
    * @param c Connessione al DB
    */
   static public void setDefaultConnection(Connection c) {
      defaultConnection = c;
   }

   /**
    * Restituisce una nuova connessione al DB.
    *
    * @return Connessione al DB secondo i parametri attualmente impostati
    * @throws java.sql.SQLException in caso di problemi di connessione
    */
   static public Connection nuovaConnessione() throws SQLException {
      ods = new OracleDataSource();
      ods.setDriverType("thin");
      ods.setServerName(host);
      ods.setPortNumber(porta);
      ods.setUser(user);
      ods.setPassword(password);
      ods.setDatabaseName(servizio);
      return ods.getConnection();
   }
}
