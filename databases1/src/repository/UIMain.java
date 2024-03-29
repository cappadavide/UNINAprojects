/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;
import javax.swing.table.*;
import javax.swing.JOptionPane;
/**
 * Finestra principale dell'applicazione;
 * @author davso
 * @version 2019
 */
public class UIMain extends javax.swing.JFrame {

    private DefaultTableModel model;
    public UIMain(){
        initComponents();
        start();
        this.addWindowListener(new java.awt.event.WindowAdapter(){
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        if (JOptionPane.showConfirmDialog(UIMain.this, 
            "Sei sicuro di voler chiudere questa finestra?", "Chiusura finestra", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
        {
            dispose();
            System.exit(0);
        }
        }
        });
        
    }
    
    private void start(){
        Welcome W=new Welcome(this,true);
        W.setLocationRelativeTo(null);
        W.setVisible(true);
        if (W.getPremuto() == Welcome.PREMUTO_ANNULLA) {
         dispose();
        } else {
         this.setLocationRelativeTo(null);
         setVisible(true);
       }
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

        jMainUI = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        jNuovo = new javax.swing.JButton();
        jCerca = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemInformations = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Repository Software 1.0 - Finestra Principale");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jMainUI.setLayout(new java.awt.GridBagLayout());

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        label1.setText("Benvenuto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 121, 0, 122);
        jMainUI.add(label1, gridBagConstraints);

        label2.setAlignment(java.awt.Label.CENTER);
        label2.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label2.setText("Che cosa vuoi fare?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jMainUI.add(label2, gridBagConstraints);

        jNuovo.setText("Nuovo");
        jNuovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNuovoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jMainUI.add(jNuovo, gridBagConstraints);

        jCerca.setText("Cerca");
        jCerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCercaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jMainUI.add(jCerca, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        getContentPane().add(jMainUI, gridBagConstraints);

        jMenu1.setText("Altro");

        jMenuItemInformations.setText("Informazioni");
        jMenuItemInformations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemInformationsActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemInformations);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jNuovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNuovoActionPerformed
        InsertFrame I= new InsertFrame();
        I.setVisible(true);
    }//GEN-LAST:event_jNuovoActionPerformed

    private void jCercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCercaActionPerformed
        SearchFrame S= new SearchFrame();
        S.setVisible(true);
    }//GEN-LAST:event_jCercaActionPerformed

    private void jMenuItemInformationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemInformationsActionPerformed
        AboutRepositorySW A=new AboutRepositorySW(this,false);
        A.setLocationRelativeTo(null);
        A.setVisible(true);
    }//GEN-LAST:event_jMenuItemInformationsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UIMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UIMain U=new UIMain();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jCerca;
    private javax.swing.JPanel jMainUI;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemInformations;
    private javax.swing.JButton jNuovo;
    private java.awt.Label label1;
    private java.awt.Label label2;
    // End of variables declaration//GEN-END:variables
}
