/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.tools;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
/**
 *Classe che offre vari metodi per il controllo sulla lunghezza
 *dei campi compilati.
 * @author davso
 */
public class CheckLengthTextField {
    /**
     * Metodo che controlla se un JTextField abilitato è vuoto o meno
     * (niente testo)
     * @param insertedText Oggetto JTextField
     * @return true se il JTextField è vuoto, false altrimenti.
     */
    static public boolean isEmpty(JTextField insertedText){
        if(insertedText.isEnabled())
            return insertedText.getText().length()==0;
        else
            return false;
            
    }
    /**
     * Metodo che controlla se un JDateChooser abilitato è vuoto o meno
     * (niente testo)
     * @param date Oggetto JDateChooser
     * @return true se il JDateChooser è vuoto, false altrimenti.
     */
    static public boolean isEmptyDate(JDateChooser date){
        if(date.isEnabled()){
            return ((JTextField)date.getDateEditor().getUiComponent()).getText().length()==0;
        }
        else
            return false;
    }
    
}
