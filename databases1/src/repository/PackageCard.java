/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import javax.swing.table.DefaultTableModel;

/**
 * Finestra di dialogo per approfondire circa un package specifico
 * @author davso
 */
public class PackageCard extends javax.swing.JDialog {

    /**
     * Creates new form PackageCard
     * @param parent
     * @param modal
     */
    public PackageCard(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    void setPackageName(String name){
        packageNameLabel.setText(name);
    }
    /**
     * Metodo che restituisce visivamente le informazioni di un package
     * @param code Codice del Descrittore Package
     */
    void showInformations(String code){
        showPackageReleases(code);//Mostra le versioni a cui appartiene il package
        showPackageChanges(code);//Mostra i commit che riguardano il package
    }
    private void showPackageReleases(String code){
        String query="SELECT P.CODP,R.VERSIONE,R.CODR,R.PATH,R.DATARILASCIO,P.STATO FROM RELEASES R JOIN PACKAGES P "
                + "ON R.CODR=P.CODR WHERE P.CODDP='"+code+"'";
        DefaultTableModel model= (DefaultTableModel)jPackageReleasesTable.getModel();
        TableManager tm=new TableManager();
        tm.buildRowsCard(model,query,this);   
            
    }
    private void showPackageChanges(String code){
        String query="SELECT C.CODL,C.TIPOMODIFICA,C.DESCRIZIONE,C.DATAAPERTURA,"
                + "C.DATACHIUSURA FROM DESCRITTORE_PACKAGES DP JOIN PACKAGES P ON "
                + "DP.CODDP=P.CODDP JOIN CHANGELOG C ON P.CODP=C.CODP WHERE DP.CODDP='"
                +code+"'";
        DefaultTableModel model= (DefaultTableModel)jPackageChangesTable.getModel();
        TableManager tm=new TableManager();
        tm.buildRowsCard(model,query,this);    
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        PackageCardTitlePanel = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        packageNameLabel = new java.awt.Label();
        PackageReleasesPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPackageReleasesTable = new javax.swing.JTable();
        PackageChangesPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPackageChangesTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Repository Software 1.0 - Scheda Package");
        setMinimumSize(new java.awt.Dimension(1, 1));
        setPreferredSize(new java.awt.Dimension(1000, 500));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jScrollPane1.getVerticalScrollBar().setUnitIncrement(20);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1114, 975));

        jPanel2.setLayout(new java.awt.GridBagLayout());

        PackageCardTitlePanel.setLayout(new java.awt.GridBagLayout());

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        label1.setText("Scheda Package");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        PackageCardTitlePanel.add(label1, gridBagConstraints);

        label2.setAlignment(java.awt.Label.CENTER);
        label2.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label2.setText("Nome:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        PackageCardTitlePanel.add(label2, gridBagConstraints);

        packageNameLabel.setAlignment(java.awt.Label.CENTER);
        packageNameLabel.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        packageNameLabel.setText("-");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        PackageCardTitlePanel.add(packageNameLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 0);
        jPanel2.add(PackageCardTitlePanel, gridBagConstraints);

        PackageReleasesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Presente nelle versioni"));
        PackageReleasesPanel.setMinimumSize(new java.awt.Dimension(100, 200));
        PackageReleasesPanel.setPreferredSize(new java.awt.Dimension(300, 200));
        PackageReleasesPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setMinimumSize(new java.awt.Dimension(600, 400));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(800, 150));

        jPackageReleasesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nome Progetto", "Path", "Data Creazione", "Descrizione", "Data Cancellazione"
            }
        ){public boolean isCellEditable(int row, int column){return false;}});
        jPackageReleasesTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jScrollPane2.setViewportView(jPackageReleasesTable);

        PackageReleasesPanel.add(jScrollPane2, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(PackageReleasesPanel, gridBagConstraints);

        PackageChangesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Storico cambiamenti"));
        PackageChangesPanel.setMinimumSize(new java.awt.Dimension(100, 200));
        PackageChangesPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane3.setMinimumSize(new java.awt.Dimension(600, 400));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(800, 150));

        jPackageChangesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ){public boolean isCellEditable(int row, int column){return false;}});
        jPackageChangesTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jScrollPane3.setViewportView(jPackageChangesTable);

        PackageChangesPanel.add(jScrollPane3, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 100.0;
        jPanel2.add(PackageChangesPanel, gridBagConstraints);

        jScrollPane1.setViewportView(jPanel2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPane1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PackageCardTitlePanel;
    private javax.swing.JPanel PackageChangesPanel;
    private javax.swing.JPanel PackageReleasesPanel;
    private javax.swing.JTable jPackageChangesTable;
    private javax.swing.JTable jPackageReleasesTable;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label packageNameLabel;
    // End of variables declaration//GEN-END:variables
}
