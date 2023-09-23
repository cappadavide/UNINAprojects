/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.tools;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JTextField;
/**
 * Classe che fornisce dei metodi utili per estrarre delle stringhe.
 * @author davso
 */
public class TextExtractor {
    /**
     * Metodo che estrae il testo da un JDateChooser
     * @param d Oggetto JDateChooser
     * @return Stringa estratta
     */
    static public String fromJDateChooser(JDateChooser d){
        System.out.println(((JTextField)d.getDateEditor().getUiComponent()).getText());
        return ((JTextField)d.getDateEditor().getUiComponent()).getText();
    }
    /**
     * Metodo che estrae il testo da un JComboBox
     * @param cb Oggetto JComboBox
     * @return Stringa estratta
     */
    static public String fromJComboBox(JComboBox cb){
        if(cb.getSelectedItem()!=null)
            return cb.getSelectedItem().toString();
        return "";
    }
    /**
     * Metodo per estrarre la prima parola.
     * Per "parola" s'intende la stringa prima del primo carattere spazio.
     * @param word Stringa su cui lavorare
     * @return Parola estratta
     */
    static public String getPrimaryKey(String word){
        if(word.length()>0){
            try{
                return word.substring(0,word.indexOf(" "));
            }
            catch(StringIndexOutOfBoundsException e){
                return word;
            }
        }
       return "";
    }
    /**
     * Restituisce la stringa senza spazi finali
     * @param s Stringa su cui lavorare
     * @return Stringa rtrimmata
     */
    public static String rtrim(String s) {
       int i = s.length()-1;
       while (i > 0 && Character.isWhitespace(s.charAt(i))) {
            i--;
       }
       return s.substring(0,i+1);
    }
}
