/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import javax.swing.table.DefaultTableModel;
/**
 * Classe che estende DefaultTableModel.
 * Con RepositoryTableModel è possibile rendere editabili o non, le celle di 
 * una tabella.
 * @author davso
 */
public class RepositoryTableModel extends DefaultTableModel {
       private boolean editable = false;

       
    public RepositoryTableModel(Object[][] data, Object[] columnNames){
        super(data,columnNames);
    }
    public boolean isEditable() {
        return editable;
    }

       @Override
    public boolean isCellEditable(int row, int column) {
        if(column==0 && editable &&!this.getColumnName(column).equals("NOME")) return false;
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        fireTableDataChanged();
    }
}
