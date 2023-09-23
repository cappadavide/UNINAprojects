/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.toedter.calendar.JDateChooser;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javax.swing.SwingUtilities;
import repository.tools.CheckLengthTextField;

/**
 * Frame che consente la ricerca (ed eventuale eliminazione/modifica)
 * di oggetti all'interno del database.
 *
 * @author davso
 * @version 2019
 */
public class SearchFrame extends javax.swing.JFrame {

    private final ProjectQueryBuilder projectBuilder;
    private final ComboBoxManager comboBuilder;
    private ArrayList <String> projectNames = new ArrayList<>();
    private boolean isEditModeEnabled=false;//Indica se lo stato della Modalità Modifica

    public SearchFrame() {
        projectBuilder=new ProjectQueryBuilder();
        comboBuilder=new ComboBoxManager();
        initComponents();
        
    }
    //Attiva o disabilita l'editing delle tabelle
    private void enableOrDisableEditing(){
        RepositoryTableModel author=(RepositoryTableModel)jAuthorTable.getModel();
        RepositoryTableModel project=(RepositoryTableModel)jProjectTable.getModel();
        RepositoryTableModel releases=(RepositoryTableModel)jReleaseTable.getModel();
        RepositoryTableModel packagesdesc=(RepositoryTableModel)jPackageDescriptorTable.getModel();
        RepositoryTableModel classesdesc=(RepositoryTableModel)jClassDescriptorTable.getModel();
        RepositoryTableModel methoddesc=(RepositoryTableModel)jMethodDescriptorTable.getModel();
        RepositoryTableModel packages=(RepositoryTableModel)jPackageTable.getModel();
        RepositoryTableModel classes=(RepositoryTableModel)jClassTable.getModel();
        RepositoryTableModel method=(RepositoryTableModel)jMethodTable.getModel();
        RepositoryTableModel test=(RepositoryTableModel)jTestTable.getModel();
        RepositoryTableModel changelog=(RepositoryTableModel)jChangelogTable.getModel();
        RepositoryTableModel execution=(RepositoryTableModel)jExecutionTable.getModel();
        RepositoryTableModel other=(RepositoryTableModel)jOtherTable.getModel();
        if(isEditModeEnabled){
            author.setEditable(true);
            project.setEditable(true);
            releases.setEditable(true);
            packagesdesc.setEditable(true);
            classesdesc.setEditable(true);
            methoddesc.setEditable(true);
            packages.setEditable(true);
            classes.setEditable(true);
            method.setEditable(true);
            test.setEditable(true);
            changelog.setEditable(true);
            execution.setEditable(true);
            other.setEditable(true);
        }
        else{
            author.setEditable(false);
            project.setEditable(false);
            releases.setEditable(false);
            packagesdesc.setEditable(false);
            classesdesc.setEditable(false);
            methoddesc.setEditable(false);
            packages.setEditable(false);
            classes.setEditable(false);
            method.setEditable(false);
            test.setEditable(false);
            changelog.setEditable(false);
            execution.setEditable(false);
            other.setEditable(false);
        }
        
    }

    /**
     * @return Un arraylist di JTextField di Autore 
     */
    ArrayList getAuthorFields(){
        ArrayList <JTextField> fields = new ArrayList<>();
        fields.add(jAuthorCodeField);
        fields.add(jAuthorNameField);
        fields.add(jAuthorSurnameField);
        fields.add(jAuthorEmailField);
        
       
        return fields;     
    }
    /**
     * @return Un arraylist di JTextField di Progetto
     */
    ArrayList getProjectFields(){
        ArrayList <JTextField> fields = new ArrayList<>();
        fields.add(jProjectNameField);
        fields.add(jProjectPathField);
        return fields;
    }
    /**
     * @return Un arraylist di JDateChooser di Progetto
     */
    ArrayList getProjectDates(){
        ArrayList <JDateChooser> fields = new ArrayList<>();
        fields.add(jProjectDateCreationField);
        fields.add(jProjectDateCancellationField);
        return fields;
    }
    /**
     * @return Un arraylist di JTextField di Release 
     */
    ArrayList getReleaseFields(){
        ArrayList <JTextField> fields = new ArrayList<>();
        fields.add(jReleaseCodeField);
        fields.add(jReleaseVersionField);
        fields.add(jReleasePathField);
        return fields;
    }
    /**
     * @return Un arraylist di JDateChooser di Release 
     */
    ArrayList getReleaseDates(){
        ArrayList <JDateChooser> fields = new ArrayList<>();
        fields.add(jReleaseDateField);
        return fields;
    }
    /**
     * @return Un arraylist di JComboBox di Release 
     */
    ArrayList getReleaseComboBoxes(){
        ArrayList <JComboBox> fields=new ArrayList<>();
        fields.add(jComboBoxReleaseProject);
        return fields;
    }
    /**
     * @return Un arrayList di JTextField di Descrittore Package 
     */
    ArrayList getPackageDescriptorFields(){
        ArrayList <JTextField> fields=new ArrayList<>();
        fields.add(jPackageDescriptorCodeField);
        fields.add(jPackageDescriptorNameField);
        fields.add(jPackageDescriptorEncloserPackField);
        return fields;
    }
     /**
     * @return Un arrayList di JComboBox di Descrittore Package 
     */
    ArrayList getPackageDescriptorComboboxes(){
        ArrayList <JComboBox> fields=new ArrayList<>();
        fields.add(jComboBoxPackageDescriptorProject);
        return fields;
    }
    /**
     * @return Un arrayList di JTextField di Package 
     */
    ArrayList getPackageFields(){
        ArrayList <JTextField> fields=new ArrayList<>();
        fields.add(jPackageCodeField);
        fields.add(jPackageCodReleaseField);
        fields.add(jPackageCodPackageDescriptorField);
        return fields;
    }
    /**
     * @return Un arrayList di JComboBox di Package 
     */
    ArrayList getPackageComboboxes(){
        ArrayList <JComboBox> fields=new ArrayList<>();
        fields.add(jComboBoxPackageStatus);
        return fields;
    }

    /**
     * @return Un arraylist di JTextField di Descrittore Classe
     */
    ArrayList getClassDescriptorFields(){
        ArrayList <JTextField> fields = new ArrayList<>();
        fields.add(jClassDescriptorCodeField);
        fields.add(jClassDescriptorNameField);
        fields.add(jClassDescriptorSourceField);
        fields.add(jClassDescriptorCodSuperField);
        fields.add(jClassDescriptorCodPackageField);
        return fields;
    }
    /**
     * @return Un arraylist di JComboBox di Descrittore Classe
     */
    ArrayList getClassDescriptorComboboxes(){
        ArrayList <JComboBox> fields = new ArrayList<>();
        fields.add(jComboBoxClassDescriptorScope);
        fields.add(jComboBoxClassDescriptorModifier);
        return fields;
    }
    /**
     * @return Un arraylist di JTextField di Classe
     */
     ArrayList getClassFields(){
        ArrayList <JTextField> fields=new ArrayList<>();
        fields.add(jClassCodeField);
        fields.add(jClassCodPackageField);
        fields.add(jClassCodClassDescriptorField);
        fields.add(jClassCodEnclosingClassField);
        return fields;
    }
     /**
     * @return Un arraylist di JComboBox di Classe
     */
    ArrayList getClassComboboxes(){
        ArrayList <JComboBox> fields=new ArrayList<>();
        fields.add(jComboBoxClassStatus);
        return fields;
    }
    /**
     * @return Un arraylist di JTextField di Descrittore Metodo
     */
      ArrayList getMethodDescriptorFields(){
        ArrayList <JTextField> fields = new ArrayList<>();
        fields.add(jMethodDescriptorCodeField);
        fields.add(jMethodDescriptorNameField);
        fields.add(jMethodDescriptorReturnField);
        fields.add(jMethodDescriptorInputParametersField);
        return fields;
    }
    /**
     * @return Un arraylist di JComboBox di Descrittore Metodo
     */
    ArrayList getMethodDescriptorComboboxes(){
        ArrayList <JComboBox> fields = new ArrayList<>();
        fields.add(jComboBoxMethodDescriptorScope);
        fields.add(jComboBoxMethodDescriptorModifier);
        return fields;
    }
    /**
     * @return Un arraylist di JTextField di Metodo
     */
      ArrayList getMethodFields(){
        ArrayList <JTextField> fields = new ArrayList<>();
        fields.add(jMethodCodeField);
        fields.add(jMethodCodClassField);
        fields.add(jMethodCodMethodDescriptorField);
        return fields;
    }
    /**
     * @return Un arraylist di JComboBox di Metodo
     */
    ArrayList getMethodComboboxes(){
        ArrayList <JComboBox> fields = new ArrayList<>();
        fields.add(jComboBoxMethodStatus);
        return fields;
    }
    /**
     * @return Un arraylist di JTextField di Test
     */
    ArrayList getTestFields(){
        ArrayList <JTextField> fields=new ArrayList<>();
        fields.add(jTestCodeField);
        fields.add(jTestNameField);
        fields.add(jTestPathField);
        return fields;
    }
    /**
     * @return Un arraylist di JComboBox di Test
     */
    ArrayList getTestComboboxes(){
        ArrayList <JComboBox> fields=new ArrayList<>();
        fields.add(jComboBoxTestProject);
        return fields;
    }
    /**
     * @return Un arraylist di JTextField di Esecuzioni
     */
    ArrayList getExecutionFields(){
        ArrayList <JTextField> fields = new ArrayList<>();
        fields.add(jExecutionCodeField);
        fields.add(jExecutionCodTestField);
        fields.add(jExecutionCodReleaseField);
        return fields;
    }
    /**
     * @return Un arraylist di JDateChooser di Esecuzioni
     */
    ArrayList getExecutionDates(){
        ArrayList <JDateChooser> fields = new ArrayList<>();
        fields.add(jExecutionDateField);
        return fields;
    }
    /**
     * @return Un arraylist di JComboBox di Esecuzioni
     */
    ArrayList getExecutionComboBoxes(){
        ArrayList <JComboBox> fields=new ArrayList<>();
        fields.add(jComboBoxExecutionResult);
        return fields;
    }
    
    /**
     * @return Un arraylist di JTextField di Changelog
     */
    ArrayList getChangelogFields(){
        ArrayList <JTextField> fields=new ArrayList<>();
        fields.add(jChangelogCodeField);
        fields.add(jChangelogDescriptionField);
        fields.add(jChangelogCodPackageField);
        fields.add(jChangelogCodClassField);
        fields.add(jChangelogCodMethodField);
        return fields;
    }
    /**
     * @return Un arraylist di JComboBox di Changelog
     */
    ArrayList getChangelogComboboxes(){
        ArrayList <JComboBox> fields=new ArrayList<>();
        fields.add(jComboBoxChangelogChangeType);
        fields.add(jComboBoxChangelogChangedObject);
        fields.add(jComboBoxChangelogProject);
        return fields;
    }
    /**
     * @return Un arraylist JDateChooser di Changelog
     */
    ArrayList getChangelogDates(){
        ArrayList <JDateChooser> fields = new ArrayList<>();
        fields.add(jChangelogOpeningDateField);
        fields.add(jChangelogClosingDateField);
        return fields;
    }
    /**
     * @return Un arraylist di JTextField di Assegnazione Autori/Test 
     */
    ArrayList getOtherFields(){
        ArrayList <JTextField> fields = new ArrayList <>();
        fields.add(jOtherCodeField);
        fields.add(jOtherGeneralField);
        return fields;
    }
    /**
     * Controlla se i campi di testo dei filtri attivati siano compilati o meno
     * @return Esito del controllo
     */
    boolean checkOtherFields(){
        ArrayList otherFields=getOtherFields();
         for (int i = 0; i < otherFields.size(); i++){
             if(CheckLengthTextField.isEmpty((JTextField) otherFields.get(i)))
                 return false;
         }
         return true;
    }
    
    private void verifyChangelogCB(){
        if(jComboBoxChangelogChangedObject.getSelectedItem().toString().equals("Package")){
            jChangelogCodClassField.setEnabled(false);
            jChangelogCodMethodField.setEnabled(false);
            jChangelogCodPackageField.setEnabled(true);
        }
        else if(jComboBoxChangelogChangedObject.getSelectedItem().toString().equals("Classe")){
            jChangelogCodClassField.setEnabled(true);
            jChangelogCodMethodField.setEnabled(false);
            jChangelogCodPackageField.setEnabled(false);
        }
        else{
            jChangelogCodClassField.setEnabled(false);
            jChangelogCodMethodField.setEnabled(true);
            jChangelogCodPackageField.setEnabled(false);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jAuthorSearchPanel = new javax.swing.JPanel();
        jAuthorSearchFieldsPanel = new javax.swing.JPanel();
        label2 = new java.awt.Label();
        jAuthorNameField = new javax.swing.JTextField();
        label6 = new java.awt.Label();
        jAuthorSurnameField = new javax.swing.JTextField();
        label7 = new java.awt.Label();
        jAuthorEmailField = new javax.swing.JTextField();
        label8 = new java.awt.Label();
        jAuthorCodeField = new javax.swing.JTextField();
        jAuthorSearchFiltersPanel = new javax.swing.JPanel();
        jAuthorSearchByFiltersPanel = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        jCheckBoxAuthorName = new javax.swing.JCheckBox();
        jCheckBoxAuthorSurname = new javax.swing.JCheckBox();
        jCheckBoxAuthorEmail = new javax.swing.JCheckBox();
        jCheckBoxAuthorCode = new javax.swing.JCheckBox();
        jShowForEachAuthorFiltersPanel = new javax.swing.JPanel();
        jAuthorSearchResultsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jAuthorTable = new javax.swing.JTable();
        jAuthorButtons = new javax.swing.JPanel();
        jSearchAuthorButton = new javax.swing.JButton();
        jEditAuthorButton = new javax.swing.JButton();
        jDeleteAuthorButton = new javax.swing.JButton();
        jProjectSearchPanel = new javax.swing.JPanel();
        jProjectSearchFieldsPanel = new javax.swing.JPanel();
        label4 = new java.awt.Label();
        jProjectNameField = new javax.swing.JTextField();
        label5 = new java.awt.Label();
        jProjectDateCreationField = new com.toedter.calendar.JDateChooser();
        label19 = new java.awt.Label();
        jProjectDateCancellationField = new com.toedter.calendar.JDateChooser();
        label20 = new java.awt.Label();
        jProjectPathField = new javax.swing.JTextField();
        jProjectSearchFiltersPanel = new javax.swing.JPanel();
        jProjectSearchByFiltersPanel = new javax.swing.JPanel();
        label21 = new java.awt.Label();
        jCheckBoxProjectName = new javax.swing.JCheckBox();
        jCheckBoxProjectDateCreation = new javax.swing.JCheckBox();
        jCheckBoxProjectDateCancellation = new javax.swing.JCheckBox();
        jCheckBoxProjectPath = new javax.swing.JCheckBox();
        jShowForEachProjectFiltersPanel = new javax.swing.JPanel();
        jProjectSearchResultsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jProjectTable = new javax.swing.JTable();
        jProjectButton = new javax.swing.JPanel();
        jSearchProjectButton = new javax.swing.JButton();
        jEditProjectButton = new javax.swing.JButton();
        jDeleteProjectButton = new javax.swing.JButton();
        jReleaseSearchPanel = new javax.swing.JPanel();
        jReleaseSearchFieldsPanel = new javax.swing.JPanel();
        label61 = new java.awt.Label();
        jReleaseCodeField = new javax.swing.JTextField();
        label64 = new java.awt.Label();
        jReleaseVersionField = new javax.swing.JTextField();
        label65 = new java.awt.Label();
        jReleasePathField = new javax.swing.JTextField();
        label66 = new java.awt.Label();
        jReleaseDateField = new com.toedter.calendar.JDateChooser();
        label67 = new java.awt.Label();
        jComboBoxReleaseProject = new javax.swing.JComboBox<>();
        jReleaseSearchFiltersPanel = new javax.swing.JPanel();
        label68 = new java.awt.Label();
        jCheckBoxReleaseCode = new javax.swing.JCheckBox();
        jCheckBoxReleaseVersion = new javax.swing.JCheckBox();
        jCheckBoxReleasePath = new javax.swing.JCheckBox();
        jCheckBoxReleaseDate = new javax.swing.JCheckBox();
        jCheckBoxReleaseProject = new javax.swing.JCheckBox();
        jReleaseResultsPanel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jReleaseTable = new javax.swing.JTable();
        jReleaseButtons = new javax.swing.JPanel();
        jSearchReleaseButton = new javax.swing.JButton();
        jEditReleaseButton = new javax.swing.JButton();
        jDeleteReleaseButton = new javax.swing.JButton();
        jPackageDescriptorSearchPanel = new javax.swing.JPanel();
        jPackageDescriptorSearchFieldsPanel = new javax.swing.JPanel();
        label23 = new java.awt.Label();
        jPackageDescriptorCodeField = new javax.swing.JTextField();
        label24 = new java.awt.Label();
        jPackageDescriptorNameField = new javax.swing.JTextField();
        label25 = new java.awt.Label();
        jPackageDescriptorEncloserPackField = new javax.swing.JTextField();
        label48 = new java.awt.Label();
        jComboBoxPackageDescriptorProject = new javax.swing.JComboBox<>();
        jPackageDescriptorSearchFiltersPanel = new javax.swing.JPanel();
        jPackageDescriptorSearchByFiltersPanel = new javax.swing.JPanel();
        label26 = new java.awt.Label();
        jCheckBoxPackageDescriptorCode = new javax.swing.JCheckBox();
        jCheckBoxPackageDescriptorName = new javax.swing.JCheckBox();
        jCheckBoxPackageDescriptorEncloserPack = new javax.swing.JCheckBox();
        jCheckBoxPackageDescriptorProject = new javax.swing.JCheckBox();
        jShowForEachPackageDescriptorFiltersPanel = new javax.swing.JPanel();
        jPackageDescriptorSearchResultsPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPackageDescriptorTable = new javax.swing.JTable();
        jPackageDescriptorButtons = new javax.swing.JPanel();
        jSearchPackageDescriptorButton = new javax.swing.JButton();
        jEditPackageDescriptorButton = new javax.swing.JButton();
        jDeletePackageDescriptorButton = new javax.swing.JButton();
        jPackageSearchPanel = new javax.swing.JPanel();
        jPackageSearchFieldsPanel = new javax.swing.JPanel();
        label3 = new java.awt.Label();
        jPackageCodeField = new javax.swing.JTextField();
        label22 = new java.awt.Label();
        jPackageCodReleaseField = new javax.swing.JTextField();
        label27 = new java.awt.Label();
        jPackageCodPackageDescriptorField = new javax.swing.JTextField();
        label50 = new java.awt.Label();
        jComboBoxPackageStatus = new javax.swing.JComboBox<>();
        jPackageSearchFiltersPanel = new javax.swing.JPanel();
        jPackageSearchByFiltersPanel = new javax.swing.JPanel();
        label51 = new java.awt.Label();
        jCheckBoxPackageCode = new javax.swing.JCheckBox();
        jCheckBoxPackageCodRelease = new javax.swing.JCheckBox();
        jCheckBoxPackageCodPackageDescriptor = new javax.swing.JCheckBox();
        jCheckBoxPackageStatus = new javax.swing.JCheckBox();
        jPackageResultsPanel = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jPackageTable = new javax.swing.JTable();
        jPackageButtons = new javax.swing.JPanel();
        jSearchPackageButton = new javax.swing.JButton();
        jEditPackageButton = new javax.swing.JButton();
        jDeletePackageButton = new javax.swing.JButton();
        jClassDescriptorSearchPanel = new javax.swing.JPanel();
        jClassDescriptorSearchFieldsPanel = new javax.swing.JPanel();
        label9 = new java.awt.Label();
        jClassDescriptorCodeField = new javax.swing.JTextField();
        label12 = new java.awt.Label();
        jClassDescriptorNameField = new javax.swing.JTextField();
        label13 = new java.awt.Label();
        jClassDescriptorSourceField = new javax.swing.JTextField();
        label14 = new java.awt.Label();
        jClassDescriptorCodSuperField = new javax.swing.JTextField();
        label15 = new java.awt.Label();
        jClassDescriptorCodPackageField = new javax.swing.JTextField();
        label10 = new java.awt.Label();
        jComboBoxClassDescriptorScope = new javax.swing.JComboBox<>();
        label11 = new java.awt.Label();
        jComboBoxClassDescriptorModifier = new javax.swing.JComboBox<>();
        jClassDescriptorSearchFiltersPanel = new javax.swing.JPanel();
        jClassDescriptorSearchByFiltersPanel = new javax.swing.JPanel();
        label16 = new java.awt.Label();
        jCheckBoxClassDescriptorCode = new javax.swing.JCheckBox();
        jCheckBoxClassDescriptorName = new javax.swing.JCheckBox();
        jCheckBoxClassDescriptorSource = new javax.swing.JCheckBox();
        jCheckBoxClassDescriptorCodSuper = new javax.swing.JCheckBox();
        jCheckBoxClassDescriptorCodPackage = new javax.swing.JCheckBox();
        jCheckBoxClassDescriptorScope = new javax.swing.JCheckBox();
        jCheckBoxClassDescriptorModifier = new javax.swing.JCheckBox();
        jShowForEachClassDescriptorFiltersPanel = new javax.swing.JPanel();
        jClassDescriptorSearchResultsPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jClassDescriptorTable = new javax.swing.JTable();
        jClassDescriptorButtons = new javax.swing.JPanel();
        jSearchClassDescriptorButton = new javax.swing.JButton();
        jEditClassDescriptorButton = new javax.swing.JButton();
        jDeleteClassDescriptorButton = new javax.swing.JButton();
        jClassSearchPanel = new javax.swing.JPanel();
        jClassSearchFieldsPanel = new javax.swing.JPanel();
        label52 = new java.awt.Label();
        jClassCodeField = new javax.swing.JTextField();
        label53 = new java.awt.Label();
        jClassCodPackageField = new javax.swing.JTextField();
        label54 = new java.awt.Label();
        jClassCodClassDescriptorField = new javax.swing.JTextField();
        label62 = new java.awt.Label();
        jClassCodEnclosingClassField = new javax.swing.JTextField();
        label55 = new java.awt.Label();
        jComboBoxClassStatus = new javax.swing.JComboBox<>();
        jClassSearchFiltersPanel = new javax.swing.JPanel();
        jClassSearchByFiltersPanel = new javax.swing.JPanel();
        label63 = new java.awt.Label();
        jCheckBoxClassCode = new javax.swing.JCheckBox();
        jCheckBoxClassCodPackage = new javax.swing.JCheckBox();
        jCheckBoxClassCodClassDescriptor = new javax.swing.JCheckBox();
        jCheckBoxClassCodEnclosingClass = new javax.swing.JCheckBox();
        jCheckBoxClassStatus = new javax.swing.JCheckBox();
        jClassResultsPanel = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jClassTable = new javax.swing.JTable();
        jClassButtons = new javax.swing.JPanel();
        jSearchClassButton = new javax.swing.JButton();
        jEditClassButton = new javax.swing.JButton();
        jDeleteClassButton = new javax.swing.JButton();
        jMethodDescriptorSearchPanel = new javax.swing.JPanel();
        jMethodDescriptorSearchFieldsPanel = new javax.swing.JPanel();
        label17 = new java.awt.Label();
        jMethodDescriptorCodeField = new javax.swing.JTextField();
        label18 = new java.awt.Label();
        jMethodDescriptorNameField = new javax.swing.JTextField();
        label28 = new java.awt.Label();
        jMethodDescriptorReturnField = new javax.swing.JTextField();
        label29 = new java.awt.Label();
        jMethodDescriptorInputParametersField = new javax.swing.JTextField();
        label30 = new java.awt.Label();
        jComboBoxMethodDescriptorScope = new javax.swing.JComboBox<>();
        label31 = new java.awt.Label();
        jComboBoxMethodDescriptorModifier = new javax.swing.JComboBox<>();
        jMethodDescriptorSearchFiltersPanel = new javax.swing.JPanel();
        jMethodDescriptorSearchByFiltersPanel = new javax.swing.JPanel();
        label32 = new java.awt.Label();
        jCheckBoxMethodDescriptorCode = new javax.swing.JCheckBox();
        jCheckBoxMethodDescriptorName = new javax.swing.JCheckBox();
        jCheckBoxMethodDescriptorReturn = new javax.swing.JCheckBox();
        jCheckBoxMethodDescriptorInputParameters = new javax.swing.JCheckBox();
        jCheckBoxMethodDescriptorScope = new javax.swing.JCheckBox();
        jCheckBoxMethodDescriptorModifier = new javax.swing.JCheckBox();
        jShowForEachMethodDescriptorFiltersPanel = new javax.swing.JPanel();
        jMethodDescriptorSearchResultsPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jMethodDescriptorTable = new javax.swing.JTable();
        jMethodDescriptorButtons = new javax.swing.JPanel();
        jSearchMethodDescriptorButton = new javax.swing.JButton();
        jEditMethodDescriptorButton = new javax.swing.JButton();
        jDeleteMethodDescriptorButton = new javax.swing.JButton();
        jMethodSearchPanel = new javax.swing.JPanel();
        jMethodSearchFieldsPanel = new javax.swing.JPanel();
        label56 = new java.awt.Label();
        jMethodCodeField = new javax.swing.JTextField();
        label57 = new java.awt.Label();
        jMethodCodClassField = new javax.swing.JTextField();
        label58 = new java.awt.Label();
        jMethodCodMethodDescriptorField = new javax.swing.JTextField();
        label59 = new java.awt.Label();
        jComboBoxMethodStatus = new javax.swing.JComboBox<>();
        jMethodSearchFiltersPanel = new javax.swing.JPanel();
        jMethodSearchByFiltersPanel = new javax.swing.JPanel();
        label60 = new java.awt.Label();
        jCheckBoxMethodCode = new javax.swing.JCheckBox();
        jCheckBoxMethodCodClass = new javax.swing.JCheckBox();
        jCheckBoxMethodCodMethodDescriptor = new javax.swing.JCheckBox();
        jCheckBoxMethodStatus = new javax.swing.JCheckBox();
        jMethodResultsPanel = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jMethodTable = new javax.swing.JTable();
        jMethodButtons = new javax.swing.JPanel();
        jSearchMethodButton = new javax.swing.JButton();
        jEditMethodButton = new javax.swing.JButton();
        jDeleteMethodButton = new javax.swing.JButton();
        jTestSearchPanel = new javax.swing.JPanel();
        jTestSearchFieldsPanel = new javax.swing.JPanel();
        label33 = new java.awt.Label();
        jTestCodeField = new javax.swing.JTextField();
        label34 = new java.awt.Label();
        jTestNameField = new javax.swing.JTextField();
        label35 = new java.awt.Label();
        jTestPathField = new javax.swing.JTextField();
        label36 = new java.awt.Label();
        jComboBoxTestProject = new javax.swing.JComboBox<>();
        jTestSearchFiltersPanel = new javax.swing.JPanel();
        jTestSearchByFiltersPanel = new javax.swing.JPanel();
        label37 = new java.awt.Label();
        jCheckBoxTestCode = new javax.swing.JCheckBox();
        jCheckBoxTestName = new javax.swing.JCheckBox();
        jCheckBoxTestPath = new javax.swing.JCheckBox();
        jCheckBoxTestProject = new javax.swing.JCheckBox();
        jShowForEachTestFiltersPanel = new javax.swing.JPanel();
        jTestSearchResultsPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTestTable = new javax.swing.JTable();
        jTestButtons = new javax.swing.JPanel();
        jEditTestButton = new javax.swing.JButton();
        jSearchTestButton = new javax.swing.JButton();
        jDeleteTestButton = new javax.swing.JButton();
        jExecutionSearchPanel = new javax.swing.JPanel();
        jExecutionSearchFieldsPanel = new javax.swing.JPanel();
        label69 = new java.awt.Label();
        jExecutionCodeField = new javax.swing.JTextField();
        label70 = new java.awt.Label();
        jExecutionCodTestField = new javax.swing.JTextField();
        label71 = new java.awt.Label();
        jExecutionCodReleaseField = new javax.swing.JTextField();
        label73 = new java.awt.Label();
        jExecutionDateField = new com.toedter.calendar.JDateChooser();
        label72 = new java.awt.Label();
        jComboBoxExecutionResult = new javax.swing.JComboBox<>();
        jExecutionSearchFiltersPanel = new javax.swing.JPanel();
        label74 = new java.awt.Label();
        jCheckBoxExecutionCode = new javax.swing.JCheckBox();
        jCheckBoxExecutionCodTest = new javax.swing.JCheckBox();
        jCheckBoxExecutionCodRelease = new javax.swing.JCheckBox();
        jCheckBoxExecutionDate = new javax.swing.JCheckBox();
        jCheckBoxExecutionResult = new javax.swing.JCheckBox();
        jExecutionResultsPanel = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jExecutionTable = new javax.swing.JTable();
        jExecutionButtons = new javax.swing.JPanel();
        jSearchExecutionButton = new javax.swing.JButton();
        jEditExecutionButton = new javax.swing.JButton();
        jDeleteExecutionButton = new javax.swing.JButton();
        jChangelogSearchPanel = new javax.swing.JPanel();
        jChangelogSearchFieldsPanel = new javax.swing.JPanel();
        label38 = new java.awt.Label();
        jChangelogCodeField = new javax.swing.JTextField();
        label39 = new java.awt.Label();
        jChangelogDescriptionField = new javax.swing.JTextField();
        label40 = new java.awt.Label();
        jChangelogCodPackageField = new javax.swing.JTextField();
        label41 = new java.awt.Label();
        jChangelogCodClassField = new javax.swing.JTextField();
        label42 = new java.awt.Label();
        jChangelogCodMethodField = new javax.swing.JTextField();
        label43 = new java.awt.Label();
        jComboBoxChangelogChangeType = new javax.swing.JComboBox<>();
        label46 = new java.awt.Label();
        jComboBoxChangelogProject = new javax.swing.JComboBox<>();
        label45 = new java.awt.Label();
        jChangelogOpeningDateField = new com.toedter.calendar.JDateChooser();
        label44 = new java.awt.Label();
        jChangelogClosingDateField = new com.toedter.calendar.JDateChooser();
        label49 = new java.awt.Label();
        jComboBoxChangelogChangedObject = new javax.swing.JComboBox<>();
        jChangelogSearchFiltersPanel = new javax.swing.JPanel();
        jChangelogSearchByFiltersPanel = new javax.swing.JPanel();
        label47 = new java.awt.Label();
        jCheckBoxChangelogCode = new javax.swing.JCheckBox();
        jCheckBoxChangelogDescription = new javax.swing.JCheckBox();
        jCheckBoxChangelogChangeType = new javax.swing.JCheckBox();
        jCheckBoxChangelogChangedObject = new javax.swing.JCheckBox();
        jCheckBoxChangelogOpeningDate = new javax.swing.JCheckBox();
        jCheckBoxChangelogClosingDate = new javax.swing.JCheckBox();
        jCheckBoxChangelogProject = new javax.swing.JCheckBox();
        jShowForEachChangelogFiltersPanel = new javax.swing.JPanel();
        jChangelogSearchResultsPanel = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jChangelogTable = new javax.swing.JTable();
        jChangelogButtons = new javax.swing.JPanel();
        jSearchChangelogButton = new javax.swing.JButton();
        jEditChangelogButton = new javax.swing.JButton();
        jDeleteChangelogButton = new javax.swing.JButton();
        jOtherSearchPanel = new javax.swing.JPanel();
        jOtherChoosePanel = new javax.swing.JPanel();
        jAuthorsRadioButton = new javax.swing.JRadioButton();
        jTestsRadioButton = new javax.swing.JRadioButton();
        jComboBoxAuthors = new javax.swing.JComboBox<>();
        jComboBoxTests = new javax.swing.JComboBox<>();
        jOtherSearchFieldsPanel = new javax.swing.JPanel();
        firstFieldLabel = new java.awt.Label();
        jOtherCodeField = new javax.swing.JTextField();
        jOtherGeneralField = new javax.swing.JTextField();
        secondFieldLabel = new java.awt.Label();
        jOtherSearchFiltersPanel = new javax.swing.JPanel();
        jCheckBoxOtherCode = new javax.swing.JCheckBox();
        jCheckBoxOtherGeneral = new javax.swing.JCheckBox();
        label77 = new java.awt.Label();
        jOtherResultsPanel = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jOtherTable = new javax.swing.JTable();
        jOtherButtons = new javax.swing.JPanel();
        jSearchOtherButton = new javax.swing.JButton();
        jDeleteOtherButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuOptions = new javax.swing.JMenu();
        jMenuItemEnableDisableEdit = new javax.swing.JMenuItem();
        jMenuItemRefreshAll = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Repository Software 1.0 - Strumento di ricerca");
        setPreferredSize(new java.awt.Dimension(1200, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(817, 560));

        jAuthorSearchPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        jAuthorSearchPanel.setLayout(new java.awt.GridBagLayout());

        jAuthorSearchFieldsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Campi di Ricerca"));
        jAuthorSearchFieldsPanel.setMinimumSize(new java.awt.Dimension(700, 100));
        jAuthorSearchFieldsPanel.setPreferredSize(new java.awt.Dimension(700, 100));
        jAuthorSearchFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label2.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label2.setText("Nome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jAuthorSearchFieldsPanel.add(label2, gridBagConstraints);

        jAuthorNameField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jAuthorNameField.setEnabled(false);
        jAuthorNameField.setMinimumSize(new java.awt.Dimension(100, 25));
        jAuthorNameField.setPreferredSize(new java.awt.Dimension(100, 25));
        jAuthorNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAuthorNameFieldActionPerformed(evt);
            }
        });
        jAuthorSearchFieldsPanel.add(jAuthorNameField, new java.awt.GridBagConstraints());

        label6.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label6.setText("Cognome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 7);
        jAuthorSearchFieldsPanel.add(label6, gridBagConstraints);

        jAuthorSurnameField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jAuthorSurnameField.setEnabled(false);
        jAuthorSurnameField.setMinimumSize(new java.awt.Dimension(100, 25));
        jAuthorSurnameField.setPreferredSize(new java.awt.Dimension(100, 25));
        jAuthorSurnameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAuthorSurnameFieldActionPerformed(evt);
            }
        });
        jAuthorSearchFieldsPanel.add(jAuthorSurnameField, new java.awt.GridBagConstraints());

        label7.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label7.setText("Email");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 14, 0, 0);
        jAuthorSearchFieldsPanel.add(label7, gridBagConstraints);

        jAuthorEmailField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jAuthorEmailField.setEnabled(false);
        jAuthorEmailField.setMinimumSize(new java.awt.Dimension(100, 25));
        jAuthorEmailField.setPreferredSize(new java.awt.Dimension(100, 25));
        jAuthorEmailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAuthorEmailFieldActionPerformed(evt);
            }
        });
        jAuthorSearchFieldsPanel.add(jAuthorEmailField, new java.awt.GridBagConstraints());

        label8.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label8.setText("Codice");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 0);
        jAuthorSearchFieldsPanel.add(label8, gridBagConstraints);

        jAuthorCodeField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jAuthorCodeField.setEnabled(false);
        jAuthorCodeField.setMinimumSize(new java.awt.Dimension(100, 25));
        jAuthorCodeField.setPreferredSize(new java.awt.Dimension(100, 25));
        jAuthorCodeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAuthorCodeFieldActionPerformed(evt);
            }
        });
        jAuthorSearchFieldsPanel.add(jAuthorCodeField, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jAuthorSearchPanel.add(jAuthorSearchFieldsPanel, gridBagConstraints);

        jAuthorSearchFiltersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtri"));
        jAuthorSearchFiltersPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jAuthorSearchFiltersPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jAuthorSearchFiltersPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jAuthorSearchFiltersPanel.setLayout(new java.awt.GridBagLayout());

        jAuthorSearchByFiltersPanel.setLayout(new java.awt.GridBagLayout());

        label1.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label1.setText("Cerca per");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jAuthorSearchByFiltersPanel.add(label1, gridBagConstraints);

        jCheckBoxAuthorName.setText("Nome");
        jCheckBoxAuthorName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxAuthorNameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jAuthorSearchByFiltersPanel.add(jCheckBoxAuthorName, gridBagConstraints);

        jCheckBoxAuthorSurname.setText("Cognome");
        jCheckBoxAuthorSurname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxAuthorSurnameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jAuthorSearchByFiltersPanel.add(jCheckBoxAuthorSurname, gridBagConstraints);

        jCheckBoxAuthorEmail.setText("Email");
        jCheckBoxAuthorEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxAuthorEmailActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jAuthorSearchByFiltersPanel.add(jCheckBoxAuthorEmail, gridBagConstraints);

        jCheckBoxAuthorCode.setText("Codice");
        jCheckBoxAuthorCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxAuthorCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jAuthorSearchByFiltersPanel.add(jCheckBoxAuthorCode, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jAuthorSearchFiltersPanel.add(jAuthorSearchByFiltersPanel, gridBagConstraints);

        jShowForEachAuthorFiltersPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jAuthorSearchFiltersPanel.add(jShowForEachAuthorFiltersPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 20.0;
        jAuthorSearchPanel.add(jAuthorSearchFiltersPanel, gridBagConstraints);
        jAuthorSearchFiltersPanel.getAccessibleContext().setAccessibleDescription("");

        jAuthorSearchResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Risultati"));
        jAuthorSearchResultsPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(800, 200));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(800, 500));

        jAuthorTable.setModel(new RepositoryTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jAuthorTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jAuthorTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jAuthorTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jAuthorTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jAuthorTable);
        jAuthorTable.getAccessibleContext().setAccessibleParent(jScrollPane1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weighty = 20.0;
        jAuthorSearchResultsPanel.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 200.0;
        jAuthorSearchPanel.add(jAuthorSearchResultsPanel, gridBagConstraints);

        jAuthorButtons.setLayout(new java.awt.GridBagLayout());

        jSearchAuthorButton.setText("Cerca");
        jSearchAuthorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchAuthorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 100.0;
        jAuthorButtons.add(jSearchAuthorButton, gridBagConstraints);

        jEditAuthorButton.setText("Modifica");
        jEditAuthorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditAuthorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jAuthorButtons.add(jEditAuthorButton, gridBagConstraints);

        jDeleteAuthorButton.setText("Elimina");
        jDeleteAuthorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteAuthorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jAuthorButtons.add(jDeleteAuthorButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 100.0;
        jAuthorSearchPanel.add(jAuthorButtons, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Autori</body></html>", jAuthorSearchPanel);

        jProjectSearchPanel.setLayout(new java.awt.GridBagLayout());

        jProjectSearchFieldsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Campi di Ricerca"));
        jProjectSearchFieldsPanel.setMinimumSize(new java.awt.Dimension(700, 100));
        jProjectSearchFieldsPanel.setPreferredSize(new java.awt.Dimension(700, 100));
        jProjectSearchFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label4.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label4.setText("Nome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jProjectSearchFieldsPanel.add(label4, gridBagConstraints);

        jProjectNameField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jProjectNameField.setEnabled(false);
        jProjectNameField.setMinimumSize(new java.awt.Dimension(100, 25));
        jProjectNameField.setPreferredSize(new java.awt.Dimension(100, 25));
        jProjectSearchFieldsPanel.add(jProjectNameField, new java.awt.GridBagConstraints());

        label5.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label5.setText("Data Creazione");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jProjectSearchFieldsPanel.add(label5, gridBagConstraints);

        jProjectDateCreationField.setEnabled(false);
        jProjectDateCreationField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jProjectDateCreationField.setMinimumSize(new java.awt.Dimension(150, 25));
        jProjectDateCreationField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jProjectSearchFieldsPanel.add(jProjectDateCreationField, gridBagConstraints);

        label19.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label19.setText("Data Cancellazione");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jProjectSearchFieldsPanel.add(label19, gridBagConstraints);

        jProjectDateCancellationField.setEnabled(false);
        jProjectDateCancellationField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jProjectDateCancellationField.setMinimumSize(new java.awt.Dimension(150, 25));
        jProjectDateCancellationField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        jProjectSearchFieldsPanel.add(jProjectDateCancellationField, gridBagConstraints);

        label20.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label20.setText("Path");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jProjectSearchFieldsPanel.add(label20, gridBagConstraints);

        jProjectPathField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jProjectPathField.setEnabled(false);
        jProjectPathField.setMinimumSize(new java.awt.Dimension(100, 25));
        jProjectPathField.setPreferredSize(new java.awt.Dimension(100, 25));
        jProjectSearchFieldsPanel.add(jProjectPathField, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jProjectSearchPanel.add(jProjectSearchFieldsPanel, gridBagConstraints);

        jProjectSearchFiltersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtri"));
        jProjectSearchFiltersPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jProjectSearchFiltersPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jProjectSearchFiltersPanel.setLayout(new java.awt.GridBagLayout());

        jProjectSearchByFiltersPanel.setLayout(new java.awt.GridBagLayout());

        label21.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label21.setText("Cerca per");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jProjectSearchByFiltersPanel.add(label21, gridBagConstraints);

        jCheckBoxProjectName.setText("Nome");
        jCheckBoxProjectName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxProjectNameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jProjectSearchByFiltersPanel.add(jCheckBoxProjectName, gridBagConstraints);

        jCheckBoxProjectDateCreation.setText("Data Creazione");
        jCheckBoxProjectDateCreation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxProjectDateCreationActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jProjectSearchByFiltersPanel.add(jCheckBoxProjectDateCreation, gridBagConstraints);

        jCheckBoxProjectDateCancellation.setText("Data Cancellazione");
        jCheckBoxProjectDateCancellation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxProjectDateCancellationActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jProjectSearchByFiltersPanel.add(jCheckBoxProjectDateCancellation, gridBagConstraints);

        jCheckBoxProjectPath.setText("Path");
        jCheckBoxProjectPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxProjectPathActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jProjectSearchByFiltersPanel.add(jCheckBoxProjectPath, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jProjectSearchFiltersPanel.add(jProjectSearchByFiltersPanel, gridBagConstraints);

        jShowForEachProjectFiltersPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jProjectSearchFiltersPanel.add(jShowForEachProjectFiltersPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weighty = 20.0;
        jProjectSearchPanel.add(jProjectSearchFiltersPanel, gridBagConstraints);

        jProjectSearchResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Risultati"));
        jProjectSearchResultsPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setMinimumSize(new java.awt.Dimension(800, 200));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(800, 500));

        jProjectTable.setModel(new RepositoryTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jProjectTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jProjectTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jProjectTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jProjectTableMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jProjectTable);

        jProjectSearchResultsPanel.add(jScrollPane2, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weighty = 600.0;
        jProjectSearchPanel.add(jProjectSearchResultsPanel, gridBagConstraints);

        jProjectButton.setLayout(new java.awt.GridBagLayout());

        jSearchProjectButton.setText("Cerca");
        jSearchProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchProjectButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 250.0;
        jProjectButton.add(jSearchProjectButton, gridBagConstraints);

        jEditProjectButton.setText("Modifica");
        jEditProjectButton.setToolTipText("");
        jEditProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditProjectButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jProjectButton.add(jEditProjectButton, gridBagConstraints);

        jDeleteProjectButton.setText("Elimina");
        jDeleteProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteProjectButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jProjectButton.add(jDeleteProjectButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 250.0;
        jProjectSearchPanel.add(jProjectButton, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Progetti</body></html>", jProjectSearchPanel);

        jReleaseSearchPanel.setLayout(new java.awt.GridBagLayout());

        jReleaseSearchFieldsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Campi di Ricerca"));
        jReleaseSearchFieldsPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jReleaseSearchFieldsPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jReleaseSearchFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label61.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label61.setText("Codice Release");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jReleaseSearchFieldsPanel.add(label61, gridBagConstraints);

        jReleaseCodeField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jReleaseCodeField.setEnabled(false);
        jReleaseCodeField.setMinimumSize(new java.awt.Dimension(100, 25));
        jReleaseCodeField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jReleaseSearchFieldsPanel.add(jReleaseCodeField, gridBagConstraints);

        label64.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label64.setText("Versione");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jReleaseSearchFieldsPanel.add(label64, gridBagConstraints);

        jReleaseVersionField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jReleaseVersionField.setEnabled(false);
        jReleaseVersionField.setMinimumSize(new java.awt.Dimension(100, 25));
        jReleaseVersionField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jReleaseSearchFieldsPanel.add(jReleaseVersionField, gridBagConstraints);

        label65.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label65.setText("Path");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jReleaseSearchFieldsPanel.add(label65, gridBagConstraints);

        jReleasePathField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jReleasePathField.setEnabled(false);
        jReleasePathField.setMinimumSize(new java.awt.Dimension(100, 25));
        jReleasePathField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jReleaseSearchFieldsPanel.add(jReleasePathField, gridBagConstraints);

        label66.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label66.setText("Progetto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jReleaseSearchFieldsPanel.add(label66, gridBagConstraints);

        jReleaseDateField.setEnabled(false);
        jReleaseDateField.setMinimumSize(new java.awt.Dimension(100, 25));
        jReleaseDateField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jReleaseSearchFieldsPanel.add(jReleaseDateField, gridBagConstraints);

        label67.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label67.setText("Data Rilascio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jReleaseSearchFieldsPanel.add(label67, gridBagConstraints);

        jComboBoxReleaseProject.setEnabled(false);
        jComboBoxReleaseProject.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxReleaseProject.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jReleaseSearchFieldsPanel.add(jComboBoxReleaseProject, gridBagConstraints);
        comboBuilder.buildComboBox(jComboBoxReleaseProject, projectBuilder.projectsListQueryBuilder(), false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jReleaseSearchPanel.add(jReleaseSearchFieldsPanel, gridBagConstraints);

        jReleaseSearchFiltersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtri"));
        jReleaseSearchFiltersPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jReleaseSearchFiltersPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jReleaseSearchFiltersPanel.setLayout(new java.awt.GridBagLayout());

        label68.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label68.setText("Cerca per");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jReleaseSearchFiltersPanel.add(label68, gridBagConstraints);

        jCheckBoxReleaseCode.setText("Codice Release");
        jCheckBoxReleaseCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxReleaseCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jReleaseSearchFiltersPanel.add(jCheckBoxReleaseCode, gridBagConstraints);

        jCheckBoxReleaseVersion.setText("Versione");
        jCheckBoxReleaseVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxReleaseVersionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jReleaseSearchFiltersPanel.add(jCheckBoxReleaseVersion, gridBagConstraints);

        jCheckBoxReleasePath.setText("Path");
        jCheckBoxReleasePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxReleasePathActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jReleaseSearchFiltersPanel.add(jCheckBoxReleasePath, gridBagConstraints);

        jCheckBoxReleaseDate.setText("Data Rilascio");
        jCheckBoxReleaseDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxReleaseDateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jReleaseSearchFiltersPanel.add(jCheckBoxReleaseDate, gridBagConstraints);

        jCheckBoxReleaseProject.setText("Progetto");
        jCheckBoxReleaseProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxReleaseProjectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jReleaseSearchFiltersPanel.add(jCheckBoxReleaseProject, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jReleaseSearchPanel.add(jReleaseSearchFiltersPanel, gridBagConstraints);

        jReleaseResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Risultati"));
        jReleaseResultsPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane7.setMinimumSize(new java.awt.Dimension(800, 200));
        jScrollPane7.setPreferredSize(new java.awt.Dimension(800, 500));

        jReleaseTable.setModel(new RepositoryTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jReleaseTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jReleaseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jReleaseTableMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jReleaseTable);

        jReleaseResultsPanel.add(jScrollPane7, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 20.0;
        jReleaseSearchPanel.add(jReleaseResultsPanel, gridBagConstraints);

        jReleaseButtons.setLayout(new java.awt.GridBagLayout());

        jSearchReleaseButton.setText("Cerca");
        jSearchReleaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchReleaseButtonActionPerformed(evt);
            }
        });
        jReleaseButtons.add(jSearchReleaseButton, new java.awt.GridBagConstraints());

        jEditReleaseButton.setText("Modifica");
        jEditReleaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditReleaseButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jReleaseButtons.add(jEditReleaseButton, gridBagConstraints);

        jDeleteReleaseButton.setText("Elimina");
        jDeleteReleaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteReleaseButtonActionPerformed(evt);
            }
        });
        jReleaseButtons.add(jDeleteReleaseButton, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 12.0;
        jReleaseSearchPanel.add(jReleaseButtons, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Release</body></html>", jReleaseSearchPanel);

        jPackageDescriptorSearchPanel.setLayout(new java.awt.GridBagLayout());

        jPackageDescriptorSearchFieldsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Campi di Ricerca"));
        jPackageDescriptorSearchFieldsPanel.setMinimumSize(new java.awt.Dimension(700, 100));
        jPackageDescriptorSearchFieldsPanel.setPreferredSize(new java.awt.Dimension(700, 100));
        jPackageDescriptorSearchFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label23.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label23.setText("Codice");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPackageDescriptorSearchFieldsPanel.add(label23, gridBagConstraints);

        jPackageDescriptorCodeField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jPackageDescriptorCodeField.setEnabled(false);
        jPackageDescriptorCodeField.setMinimumSize(new java.awt.Dimension(100, 25));
        jPackageDescriptorCodeField.setPreferredSize(new java.awt.Dimension(100, 25));
        jPackageDescriptorSearchFieldsPanel.add(jPackageDescriptorCodeField, new java.awt.GridBagConstraints());

        label24.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label24.setText("Nome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPackageDescriptorSearchFieldsPanel.add(label24, gridBagConstraints);

        jPackageDescriptorNameField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jPackageDescriptorNameField.setEnabled(false);
        jPackageDescriptorNameField.setMinimumSize(new java.awt.Dimension(100, 25));
        jPackageDescriptorNameField.setPreferredSize(new java.awt.Dimension(100, 25));
        jPackageDescriptorSearchFieldsPanel.add(jPackageDescriptorNameField, new java.awt.GridBagConstraints());

        label25.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label25.setText("Codice Package Contenente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPackageDescriptorSearchFieldsPanel.add(label25, gridBagConstraints);

        jPackageDescriptorEncloserPackField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jPackageDescriptorEncloserPackField.setEnabled(false);
        jPackageDescriptorEncloserPackField.setMinimumSize(new java.awt.Dimension(100, 25));
        jPackageDescriptorEncloserPackField.setPreferredSize(new java.awt.Dimension(100, 25));
        jPackageDescriptorSearchFieldsPanel.add(jPackageDescriptorEncloserPackField, new java.awt.GridBagConstraints());

        label48.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label48.setText("Progetto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPackageDescriptorSearchFieldsPanel.add(label48, gridBagConstraints);

        jComboBoxPackageDescriptorProject.setEnabled(false);
        jComboBoxPackageDescriptorProject.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxPackageDescriptorProject.setPreferredSize(new java.awt.Dimension(100, 25));
        jPackageDescriptorSearchFieldsPanel.add(jComboBoxPackageDescriptorProject, new java.awt.GridBagConstraints());
        comboBuilder.buildComboBox(jComboBoxPackageDescriptorProject,
            projectBuilder.projectsListQueryBuilder(),false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jPackageDescriptorSearchPanel.add(jPackageDescriptorSearchFieldsPanel, gridBagConstraints);

        jPackageDescriptorSearchFiltersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtri"));
        jPackageDescriptorSearchFiltersPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jPackageDescriptorSearchFiltersPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jPackageDescriptorSearchFiltersPanel.setLayout(new java.awt.GridBagLayout());

        jPackageDescriptorSearchByFiltersPanel.setLayout(new java.awt.GridBagLayout());

        label26.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label26.setText("Cerca per\n");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPackageDescriptorSearchByFiltersPanel.add(label26, gridBagConstraints);

        jCheckBoxPackageDescriptorCode.setText("Codice");
        jCheckBoxPackageDescriptorCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPackageDescriptorCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPackageDescriptorSearchByFiltersPanel.add(jCheckBoxPackageDescriptorCode, gridBagConstraints);

        jCheckBoxPackageDescriptorName.setText("Nome");
        jCheckBoxPackageDescriptorName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPackageDescriptorNameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPackageDescriptorSearchByFiltersPanel.add(jCheckBoxPackageDescriptorName, gridBagConstraints);

        jCheckBoxPackageDescriptorEncloserPack.setText("Codice Package Contenente");
        jCheckBoxPackageDescriptorEncloserPack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPackageDescriptorEncloserPackActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPackageDescriptorSearchByFiltersPanel.add(jCheckBoxPackageDescriptorEncloserPack, gridBagConstraints);

        jCheckBoxPackageDescriptorProject.setText("Progetto");
        jCheckBoxPackageDescriptorProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPackageDescriptorProjectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPackageDescriptorSearchByFiltersPanel.add(jCheckBoxPackageDescriptorProject, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPackageDescriptorSearchFiltersPanel.add(jPackageDescriptorSearchByFiltersPanel, gridBagConstraints);

        jShowForEachPackageDescriptorFiltersPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPackageDescriptorSearchFiltersPanel.add(jShowForEachPackageDescriptorFiltersPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jPackageDescriptorSearchPanel.add(jPackageDescriptorSearchFiltersPanel, gridBagConstraints);

        jPackageDescriptorSearchResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Risultati"));
        jPackageDescriptorSearchResultsPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane3.setMinimumSize(new java.awt.Dimension(800, 200));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(800, 500));

        jPackageDescriptorTable.setModel(new RepositoryTableModel(
            new Object [][] {
            },
            new String [] {

            }
        ));
        jPackageDescriptorTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jPackageDescriptorTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPackageDescriptorTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPackageDescriptorTableMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(jPackageDescriptorTable);

        jPackageDescriptorSearchResultsPanel.add(jScrollPane3, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 20.0;
        jPackageDescriptorSearchPanel.add(jPackageDescriptorSearchResultsPanel, gridBagConstraints);

        jPackageDescriptorButtons.setLayout(new java.awt.GridBagLayout());

        jSearchPackageDescriptorButton.setText("Cerca");
        jSearchPackageDescriptorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchPackageDescriptorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 12.0;
        jPackageDescriptorButtons.add(jSearchPackageDescriptorButton, gridBagConstraints);

        jEditPackageDescriptorButton.setText("Modifica");
        jEditPackageDescriptorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditPackageDescriptorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jPackageDescriptorButtons.add(jEditPackageDescriptorButton, gridBagConstraints);

        jDeletePackageDescriptorButton.setText("Elimina");
        jDeletePackageDescriptorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeletePackageDescriptorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPackageDescriptorButtons.add(jDeletePackageDescriptorButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 12.0;
        jPackageDescriptorSearchPanel.add(jPackageDescriptorButtons, gridBagConstraints);

        jTabbedPane1.addTab("Descrittore Package", jPackageDescriptorSearchPanel);

        jPackageSearchPanel.setLayout(new java.awt.GridBagLayout());

        jPackageSearchFieldsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Campi di Ricerca"));
        jPackageSearchFieldsPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jPackageSearchFieldsPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jPackageSearchFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label3.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label3.setText("Codice Package");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPackageSearchFieldsPanel.add(label3, gridBagConstraints);

        jPackageCodeField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jPackageCodeField.setEnabled(false);
        jPackageCodeField.setMinimumSize(new java.awt.Dimension(100, 25));
        jPackageCodeField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPackageSearchFieldsPanel.add(jPackageCodeField, gridBagConstraints);

        label22.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label22.setText("Codice Release");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPackageSearchFieldsPanel.add(label22, gridBagConstraints);

        jPackageCodReleaseField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jPackageCodReleaseField.setEnabled(false);
        jPackageCodReleaseField.setMinimumSize(new java.awt.Dimension(100, 25));
        jPackageCodReleaseField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPackageSearchFieldsPanel.add(jPackageCodReleaseField, gridBagConstraints);

        label27.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label27.setText("Codice Desc.Package");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPackageSearchFieldsPanel.add(label27, gridBagConstraints);

        jPackageCodPackageDescriptorField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jPackageCodPackageDescriptorField.setEnabled(false);
        jPackageCodPackageDescriptorField.setMinimumSize(new java.awt.Dimension(100, 25));
        jPackageCodPackageDescriptorField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        jPackageSearchFieldsPanel.add(jPackageCodPackageDescriptorField, gridBagConstraints);

        label50.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label50.setText("Stato");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPackageSearchFieldsPanel.add(label50, gridBagConstraints);

        jComboBoxPackageStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nuovo", "Immutato", "Modificato", "Cancellato" }));
        jComboBoxPackageStatus.setEnabled(false);
        jComboBoxPackageStatus.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxPackageStatus.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        jPackageSearchFieldsPanel.add(jComboBoxPackageStatus, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jPackageSearchPanel.add(jPackageSearchFieldsPanel, gridBagConstraints);

        jPackageSearchFiltersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtri"));
        jPackageSearchFiltersPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jPackageSearchFiltersPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jPackageSearchFiltersPanel.setLayout(new java.awt.GridBagLayout());

        jPackageSearchByFiltersPanel.setLayout(new java.awt.GridBagLayout());

        label51.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label51.setText("Cerca per");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPackageSearchByFiltersPanel.add(label51, gridBagConstraints);

        jCheckBoxPackageCode.setText("Codice Package");
        jCheckBoxPackageCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPackageCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPackageSearchByFiltersPanel.add(jCheckBoxPackageCode, gridBagConstraints);

        jCheckBoxPackageCodRelease.setText("Codice Release");
        jCheckBoxPackageCodRelease.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPackageCodReleaseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPackageSearchByFiltersPanel.add(jCheckBoxPackageCodRelease, gridBagConstraints);

        jCheckBoxPackageCodPackageDescriptor.setText("Codice Desc.Package");
        jCheckBoxPackageCodPackageDescriptor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPackageCodPackageDescriptorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPackageSearchByFiltersPanel.add(jCheckBoxPackageCodPackageDescriptor, gridBagConstraints);

        jCheckBoxPackageStatus.setText("Stato");
        jCheckBoxPackageStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPackageStatusActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPackageSearchByFiltersPanel.add(jCheckBoxPackageStatus, gridBagConstraints);

        jPackageSearchFiltersPanel.add(jPackageSearchByFiltersPanel, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jPackageSearchPanel.add(jPackageSearchFiltersPanel, gridBagConstraints);

        jPackageResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Risultati"));
        jPackageResultsPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane10.setMinimumSize(new java.awt.Dimension(800, 200));
        jScrollPane10.setPreferredSize(new java.awt.Dimension(800, 500));

        jPackageTable.setModel(new RepositoryTableModel(
            new Object [][] {
            },
            new String [] {
            }
        ));
        jPackageTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jPackageTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPackageTableMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(jPackageTable);

        jPackageResultsPanel.add(jScrollPane10, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 20.0;
        jPackageSearchPanel.add(jPackageResultsPanel, gridBagConstraints);

        jPackageButtons.setLayout(new java.awt.GridBagLayout());

        jSearchPackageButton.setText("Cerca");
        jSearchPackageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchPackageButtonActionPerformed(evt);
            }
        });
        jPackageButtons.add(jSearchPackageButton, new java.awt.GridBagConstraints());

        jEditPackageButton.setText("Modifica");
        jEditPackageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditPackageButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jPackageButtons.add(jEditPackageButton, gridBagConstraints);

        jDeletePackageButton.setText("Elimina");
        jDeletePackageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeletePackageButtonActionPerformed(evt);
            }
        });
        jPackageButtons.add(jDeletePackageButton, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 12.0;
        jPackageSearchPanel.add(jPackageButtons, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Package</body></html>", jPackageSearchPanel);

        jClassDescriptorSearchPanel.setLayout(new java.awt.GridBagLayout());

        jClassDescriptorSearchFieldsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Campi di Ricerca"));
        jClassDescriptorSearchFieldsPanel.setMinimumSize(new java.awt.Dimension(700, 100));
        jClassDescriptorSearchFieldsPanel.setPreferredSize(new java.awt.Dimension(700, 100));
        jClassDescriptorSearchFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label9.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label9.setText("Codice");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jClassDescriptorSearchFieldsPanel.add(label9, gridBagConstraints);

        jClassDescriptorCodeField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jClassDescriptorCodeField.setEnabled(false);
        jClassDescriptorCodeField.setMinimumSize(new java.awt.Dimension(100, 25));
        jClassDescriptorCodeField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jClassDescriptorSearchFieldsPanel.add(jClassDescriptorCodeField, gridBagConstraints);

        label12.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label12.setText("Nome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassDescriptorSearchFieldsPanel.add(label12, gridBagConstraints);

        jClassDescriptorNameField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jClassDescriptorNameField.setEnabled(false);
        jClassDescriptorNameField.setMinimumSize(new java.awt.Dimension(100, 25));
        jClassDescriptorNameField.setPreferredSize(new java.awt.Dimension(100, 25));
        jClassDescriptorSearchFieldsPanel.add(jClassDescriptorNameField, new java.awt.GridBagConstraints());

        label13.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label13.setText("Nome Source");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassDescriptorSearchFieldsPanel.add(label13, gridBagConstraints);

        jClassDescriptorSourceField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jClassDescriptorSourceField.setEnabled(false);
        jClassDescriptorSourceField.setMinimumSize(new java.awt.Dimension(100, 25));
        jClassDescriptorSourceField.setPreferredSize(new java.awt.Dimension(100, 25));
        jClassDescriptorSearchFieldsPanel.add(jClassDescriptorSourceField, new java.awt.GridBagConstraints());

        label14.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label14.setText("Codice SuperClasse");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassDescriptorSearchFieldsPanel.add(label14, gridBagConstraints);

        jClassDescriptorCodSuperField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jClassDescriptorCodSuperField.setEnabled(false);
        jClassDescriptorCodSuperField.setMinimumSize(new java.awt.Dimension(100, 25));
        jClassDescriptorCodSuperField.setPreferredSize(new java.awt.Dimension(100, 25));
        jClassDescriptorSearchFieldsPanel.add(jClassDescriptorCodSuperField, new java.awt.GridBagConstraints());

        label15.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label15.setText("Codice Package");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jClassDescriptorSearchFieldsPanel.add(label15, gridBagConstraints);

        jClassDescriptorCodPackageField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jClassDescriptorCodPackageField.setEnabled(false);
        jClassDescriptorCodPackageField.setMinimumSize(new java.awt.Dimension(100, 25));
        jClassDescriptorCodPackageField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        jClassDescriptorSearchFieldsPanel.add(jClassDescriptorCodPackageField, gridBagConstraints);

        label10.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label10.setText("Visibilità");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassDescriptorSearchFieldsPanel.add(label10, gridBagConstraints);

        jComboBoxClassDescriptorScope.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "default", "private", "protected", "public" }));
        jComboBoxClassDescriptorScope.setEnabled(false);
        jComboBoxClassDescriptorScope.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxClassDescriptorScope.setPreferredSize(new java.awt.Dimension(100, 25));
        jComboBoxClassDescriptorScope.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxClassDescriptorScopeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jClassDescriptorSearchFieldsPanel.add(jComboBoxClassDescriptorScope, gridBagConstraints);

        label11.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label11.setText("Modificatore");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassDescriptorSearchFieldsPanel.add(label11, gridBagConstraints);

        jComboBoxClassDescriptorModifier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "default", "abstract", "static", "final" }));
        jComboBoxClassDescriptorModifier.setEnabled(false);
        jComboBoxClassDescriptorModifier.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxClassDescriptorModifier.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jClassDescriptorSearchFieldsPanel.add(jComboBoxClassDescriptorModifier, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jClassDescriptorSearchPanel.add(jClassDescriptorSearchFieldsPanel, gridBagConstraints);

        jClassDescriptorSearchFiltersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtri"));
        jClassDescriptorSearchFiltersPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jClassDescriptorSearchFiltersPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jClassDescriptorSearchFiltersPanel.setLayout(new java.awt.GridBagLayout());

        jClassDescriptorSearchByFiltersPanel.setLayout(new java.awt.GridBagLayout());

        label16.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label16.setText("Cerca per");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jClassDescriptorSearchByFiltersPanel.add(label16, gridBagConstraints);

        jCheckBoxClassDescriptorCode.setText("Codice Classe");
        jCheckBoxClassDescriptorCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxClassDescriptorCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jClassDescriptorSearchByFiltersPanel.add(jCheckBoxClassDescriptorCode, gridBagConstraints);

        jCheckBoxClassDescriptorName.setText("Nome");
        jCheckBoxClassDescriptorName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxClassDescriptorNameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassDescriptorSearchByFiltersPanel.add(jCheckBoxClassDescriptorName, gridBagConstraints);

        jCheckBoxClassDescriptorSource.setText("Nome Source");
        jCheckBoxClassDescriptorSource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxClassDescriptorSourceActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassDescriptorSearchByFiltersPanel.add(jCheckBoxClassDescriptorSource, gridBagConstraints);

        jCheckBoxClassDescriptorCodSuper.setText("Codice SuperClasse");
        jCheckBoxClassDescriptorCodSuper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxClassDescriptorCodSuperActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassDescriptorSearchByFiltersPanel.add(jCheckBoxClassDescriptorCodSuper, gridBagConstraints);

        jCheckBoxClassDescriptorCodPackage.setText("Codice Package");
        jCheckBoxClassDescriptorCodPackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxClassDescriptorCodPackageActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jClassDescriptorSearchByFiltersPanel.add(jCheckBoxClassDescriptorCodPackage, gridBagConstraints);

        jCheckBoxClassDescriptorScope.setText("Visibilità");
        jCheckBoxClassDescriptorScope.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxClassDescriptorScopeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassDescriptorSearchByFiltersPanel.add(jCheckBoxClassDescriptorScope, gridBagConstraints);

        jCheckBoxClassDescriptorModifier.setText("Modificatore");
        jCheckBoxClassDescriptorModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxClassDescriptorModifierActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassDescriptorSearchByFiltersPanel.add(jCheckBoxClassDescriptorModifier, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jClassDescriptorSearchFiltersPanel.add(jClassDescriptorSearchByFiltersPanel, gridBagConstraints);

        jShowForEachClassDescriptorFiltersPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jClassDescriptorSearchFiltersPanel.add(jShowForEachClassDescriptorFiltersPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jClassDescriptorSearchPanel.add(jClassDescriptorSearchFiltersPanel, gridBagConstraints);

        jClassDescriptorSearchResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Risultati"));
        jClassDescriptorSearchResultsPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane4.setMinimumSize(new java.awt.Dimension(800, 200));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(800, 500));

        jClassDescriptorTable.setModel(new RepositoryTableModel(
            new Object [][] {
            },
            new String [] {

            }
        ));
        jClassDescriptorTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jClassDescriptorTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jClassDescriptorTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jClassDescriptorTableMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(jClassDescriptorTable);

        jClassDescriptorSearchResultsPanel.add(jScrollPane4, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 20.0;
        jClassDescriptorSearchPanel.add(jClassDescriptorSearchResultsPanel, gridBagConstraints);

        jClassDescriptorButtons.setLayout(new java.awt.GridBagLayout());

        jSearchClassDescriptorButton.setText("Cerca");
        jSearchClassDescriptorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchClassDescriptorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 12.0;
        jClassDescriptorButtons.add(jSearchClassDescriptorButton, gridBagConstraints);

        jEditClassDescriptorButton.setText("Modifica");
        jEditClassDescriptorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditClassDescriptorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jClassDescriptorButtons.add(jEditClassDescriptorButton, gridBagConstraints);

        jDeleteClassDescriptorButton.setText("Elimina");
        jDeleteClassDescriptorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteClassDescriptorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jClassDescriptorButtons.add(jDeleteClassDescriptorButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 12.0;
        jClassDescriptorSearchPanel.add(jClassDescriptorButtons, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Descrittori Classe</body></html>", jClassDescriptorSearchPanel);

        jClassSearchPanel.setLayout(new java.awt.GridBagLayout());

        jClassSearchFieldsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Campi di Ricerca"));
        jClassSearchFieldsPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jClassSearchFieldsPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jClassSearchFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label52.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label52.setText("Codice Classe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jClassSearchFieldsPanel.add(label52, gridBagConstraints);

        jClassCodeField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jClassCodeField.setEnabled(false);
        jClassCodeField.setMinimumSize(new java.awt.Dimension(100, 25));
        jClassCodeField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jClassSearchFieldsPanel.add(jClassCodeField, gridBagConstraints);

        label53.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label53.setText("Codice Package");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassSearchFieldsPanel.add(label53, gridBagConstraints);

        jClassCodPackageField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jClassCodPackageField.setEnabled(false);
        jClassCodPackageField.setMinimumSize(new java.awt.Dimension(100, 25));
        jClassCodPackageField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jClassSearchFieldsPanel.add(jClassCodPackageField, gridBagConstraints);

        label54.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label54.setText("Codice Desc.Classe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassSearchFieldsPanel.add(label54, gridBagConstraints);

        jClassCodClassDescriptorField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jClassCodClassDescriptorField.setEnabled(false);
        jClassCodClassDescriptorField.setMinimumSize(new java.awt.Dimension(100, 25));
        jClassCodClassDescriptorField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        jClassSearchFieldsPanel.add(jClassCodClassDescriptorField, gridBagConstraints);

        label62.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label62.setText("Codice Classe Contenente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jClassSearchFieldsPanel.add(label62, gridBagConstraints);

        jClassCodEnclosingClassField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jClassCodEnclosingClassField.setEnabled(false);
        jClassCodEnclosingClassField.setMinimumSize(new java.awt.Dimension(100, 25));
        jClassCodEnclosingClassField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jClassSearchFieldsPanel.add(jClassCodEnclosingClassField, gridBagConstraints);

        label55.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label55.setText("Stato");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassSearchFieldsPanel.add(label55, gridBagConstraints);

        jComboBoxClassStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nuovo", "Immutato", "Modificato", "Cancellato" }));
        jComboBoxClassStatus.setEnabled(false);
        jComboBoxClassStatus.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxClassStatus.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jClassSearchFieldsPanel.add(jComboBoxClassStatus, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jClassSearchPanel.add(jClassSearchFieldsPanel, gridBagConstraints);

        jClassSearchFiltersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtri"));
        jClassSearchFiltersPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jClassSearchFiltersPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jClassSearchFiltersPanel.setLayout(new java.awt.GridBagLayout());

        jClassSearchByFiltersPanel.setLayout(new java.awt.GridBagLayout());

        label63.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label63.setText("Cerca per");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jClassSearchByFiltersPanel.add(label63, gridBagConstraints);

        jCheckBoxClassCode.setText("Codice Classe");
        jCheckBoxClassCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxClassCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jClassSearchByFiltersPanel.add(jCheckBoxClassCode, gridBagConstraints);

        jCheckBoxClassCodPackage.setText("Codice Package");
        jCheckBoxClassCodPackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxClassCodPackageActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassSearchByFiltersPanel.add(jCheckBoxClassCodPackage, gridBagConstraints);

        jCheckBoxClassCodClassDescriptor.setText("Codice Desc. Classe");
        jCheckBoxClassCodClassDescriptor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxClassCodClassDescriptorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassSearchByFiltersPanel.add(jCheckBoxClassCodClassDescriptor, gridBagConstraints);

        jCheckBoxClassCodEnclosingClass.setText("Codice Classe Contenente");
        jCheckBoxClassCodEnclosingClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxClassCodEnclosingClassActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jClassSearchByFiltersPanel.add(jCheckBoxClassCodEnclosingClass, gridBagConstraints);

        jCheckBoxClassStatus.setText("Stato");
        jCheckBoxClassStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxClassStatusActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jClassSearchByFiltersPanel.add(jCheckBoxClassStatus, gridBagConstraints);

        jClassSearchFiltersPanel.add(jClassSearchByFiltersPanel, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jClassSearchPanel.add(jClassSearchFiltersPanel, gridBagConstraints);

        jClassResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Risultati"));
        jClassResultsPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane11.setMinimumSize(new java.awt.Dimension(800, 200));
        jScrollPane11.setPreferredSize(new java.awt.Dimension(800, 500));

        jClassTable.setModel(new RepositoryTableModel(
            new Object [][] {
            },
            new String [] {

            }
        ));
        jClassTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jClassTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jClassTableMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(jClassTable);

        jClassResultsPanel.add(jScrollPane11, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 20.0;
        jClassSearchPanel.add(jClassResultsPanel, gridBagConstraints);

        jClassButtons.setLayout(new java.awt.GridBagLayout());

        jSearchClassButton.setText("Cerca");
        jSearchClassButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchClassButtonActionPerformed(evt);
            }
        });
        jClassButtons.add(jSearchClassButton, new java.awt.GridBagConstraints());

        jEditClassButton.setText("Modifica");
        jEditClassButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditClassButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jClassButtons.add(jEditClassButton, gridBagConstraints);

        jDeleteClassButton.setText("Elimina");
        jDeleteClassButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteClassButtonActionPerformed(evt);
            }
        });
        jClassButtons.add(jDeleteClassButton, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 12.0;
        jClassSearchPanel.add(jClassButtons, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Classe</body></html>", jClassSearchPanel);

        jMethodDescriptorSearchPanel.setLayout(new java.awt.GridBagLayout());

        jMethodDescriptorSearchFieldsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Campi di Ricerca"));
        jMethodDescriptorSearchFieldsPanel.setMinimumSize(new java.awt.Dimension(700, 100));
        jMethodDescriptorSearchFieldsPanel.setPreferredSize(new java.awt.Dimension(700, 100));
        jMethodDescriptorSearchFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label17.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label17.setText("Codice");
        jMethodDescriptorSearchFieldsPanel.add(label17, new java.awt.GridBagConstraints());

        jMethodDescriptorCodeField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jMethodDescriptorCodeField.setEnabled(false);
        jMethodDescriptorCodeField.setMinimumSize(new java.awt.Dimension(100, 25));
        jMethodDescriptorCodeField.setPreferredSize(new java.awt.Dimension(100, 25));
        jMethodDescriptorSearchFieldsPanel.add(jMethodDescriptorCodeField, new java.awt.GridBagConstraints());

        label18.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label18.setText("Nome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jMethodDescriptorSearchFieldsPanel.add(label18, gridBagConstraints);

        jMethodDescriptorNameField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jMethodDescriptorNameField.setEnabled(false);
        jMethodDescriptorNameField.setMinimumSize(new java.awt.Dimension(100, 25));
        jMethodDescriptorNameField.setPreferredSize(new java.awt.Dimension(100, 25));
        jMethodDescriptorSearchFieldsPanel.add(jMethodDescriptorNameField, new java.awt.GridBagConstraints());

        label28.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label28.setText("Tipo Ritorno");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jMethodDescriptorSearchFieldsPanel.add(label28, gridBagConstraints);

        jMethodDescriptorReturnField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jMethodDescriptorReturnField.setEnabled(false);
        jMethodDescriptorReturnField.setMinimumSize(new java.awt.Dimension(100, 25));
        jMethodDescriptorReturnField.setPreferredSize(new java.awt.Dimension(100, 25));
        jMethodDescriptorSearchFieldsPanel.add(jMethodDescriptorReturnField, new java.awt.GridBagConstraints());

        label29.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label29.setText("Parametri");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jMethodDescriptorSearchFieldsPanel.add(label29, gridBagConstraints);

        jMethodDescriptorInputParametersField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jMethodDescriptorInputParametersField.setEnabled(false);
        jMethodDescriptorInputParametersField.setMinimumSize(new java.awt.Dimension(100, 25));
        jMethodDescriptorInputParametersField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jMethodDescriptorSearchFieldsPanel.add(jMethodDescriptorInputParametersField, gridBagConstraints);

        label30.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label30.setText("Visibilità");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jMethodDescriptorSearchFieldsPanel.add(label30, gridBagConstraints);

        jComboBoxMethodDescriptorScope.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "default", "private", "protected", "public" }));
        jComboBoxMethodDescriptorScope.setEnabled(false);
        jComboBoxMethodDescriptorScope.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxMethodDescriptorScope.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jMethodDescriptorSearchFieldsPanel.add(jComboBoxMethodDescriptorScope, gridBagConstraints);

        label31.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label31.setText("Modificatore");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jMethodDescriptorSearchFieldsPanel.add(label31, gridBagConstraints);

        jComboBoxMethodDescriptorModifier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "default", "abstract", "static", "final" }));
        jComboBoxMethodDescriptorModifier.setEnabled(false);
        jComboBoxMethodDescriptorModifier.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxMethodDescriptorModifier.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jMethodDescriptorSearchFieldsPanel.add(jComboBoxMethodDescriptorModifier, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jMethodDescriptorSearchPanel.add(jMethodDescriptorSearchFieldsPanel, gridBagConstraints);

        jMethodDescriptorSearchFiltersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtri"));
        jMethodDescriptorSearchFiltersPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jMethodDescriptorSearchFiltersPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jMethodDescriptorSearchFiltersPanel.setLayout(new java.awt.GridBagLayout());

        jMethodDescriptorSearchByFiltersPanel.setLayout(new java.awt.GridBagLayout());

        label32.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label32.setText("Cerca per");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jMethodDescriptorSearchByFiltersPanel.add(label32, gridBagConstraints);

        jCheckBoxMethodDescriptorCode.setText("Codice");
        jCheckBoxMethodDescriptorCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMethodDescriptorCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jMethodDescriptorSearchByFiltersPanel.add(jCheckBoxMethodDescriptorCode, gridBagConstraints);

        jCheckBoxMethodDescriptorName.setText("Nome");
        jCheckBoxMethodDescriptorName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMethodDescriptorNameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jMethodDescriptorSearchByFiltersPanel.add(jCheckBoxMethodDescriptorName, gridBagConstraints);

        jCheckBoxMethodDescriptorReturn.setText("Tipo Ritorno");
        jCheckBoxMethodDescriptorReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMethodDescriptorReturnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jMethodDescriptorSearchByFiltersPanel.add(jCheckBoxMethodDescriptorReturn, gridBagConstraints);

        jCheckBoxMethodDescriptorInputParameters.setText("Parametri");
        jCheckBoxMethodDescriptorInputParameters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMethodDescriptorInputParametersActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jMethodDescriptorSearchByFiltersPanel.add(jCheckBoxMethodDescriptorInputParameters, gridBagConstraints);

        jCheckBoxMethodDescriptorScope.setText("Visibilità");
        jCheckBoxMethodDescriptorScope.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMethodDescriptorScopeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jMethodDescriptorSearchByFiltersPanel.add(jCheckBoxMethodDescriptorScope, gridBagConstraints);

        jCheckBoxMethodDescriptorModifier.setText("Modificatore");
        jCheckBoxMethodDescriptorModifier.setToolTipText("");
        jCheckBoxMethodDescriptorModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMethodDescriptorModifierActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jMethodDescriptorSearchByFiltersPanel.add(jCheckBoxMethodDescriptorModifier, gridBagConstraints);

        jMethodDescriptorSearchFiltersPanel.add(jMethodDescriptorSearchByFiltersPanel, new java.awt.GridBagConstraints());

        jShowForEachMethodDescriptorFiltersPanel.setLayout(new java.awt.GridBagLayout());
        jMethodDescriptorSearchFiltersPanel.add(jShowForEachMethodDescriptorFiltersPanel, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jMethodDescriptorSearchPanel.add(jMethodDescriptorSearchFiltersPanel, gridBagConstraints);

        jMethodDescriptorSearchResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Risultati"));
        jMethodDescriptorSearchResultsPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane5.setMinimumSize(new java.awt.Dimension(800, 200));
        jScrollPane5.setPreferredSize(new java.awt.Dimension(800, 500));

        jMethodDescriptorTable.setModel(new RepositoryTableModel(
            new Object [][] {
            },
            new String [] {

            }
        ));
        jMethodDescriptorTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jMethodDescriptorTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMethodDescriptorTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMethodDescriptorTableMousePressed(evt);
            }
        });
        jScrollPane5.setViewportView(jMethodDescriptorTable);

        jMethodDescriptorSearchResultsPanel.add(jScrollPane5, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 20.0;
        jMethodDescriptorSearchPanel.add(jMethodDescriptorSearchResultsPanel, gridBagConstraints);

        jMethodDescriptorButtons.setLayout(new java.awt.GridBagLayout());

        jSearchMethodDescriptorButton.setText("Cerca");
        jSearchMethodDescriptorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jSearchMethodDescriptorButtonMouseClicked(evt);
            }
        });
        jSearchMethodDescriptorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchMethodDescriptorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 12.0;
        jMethodDescriptorButtons.add(jSearchMethodDescriptorButton, gridBagConstraints);

        jEditMethodDescriptorButton.setText("Modifica");
        jEditMethodDescriptorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditMethodDescriptorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jMethodDescriptorButtons.add(jEditMethodDescriptorButton, gridBagConstraints);

        jDeleteMethodDescriptorButton.setText("Elimina");
        jDeleteMethodDescriptorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteMethodDescriptorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jMethodDescriptorButtons.add(jDeleteMethodDescriptorButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 12.0;
        jMethodDescriptorSearchPanel.add(jMethodDescriptorButtons, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Descrittore Metodo</body></html>", jMethodDescriptorSearchPanel);

        jMethodSearchPanel.setLayout(new java.awt.GridBagLayout());

        jMethodSearchFieldsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Campi di Ricerca"));
        jMethodSearchFieldsPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jMethodSearchFieldsPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jMethodSearchFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label56.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label56.setText("Codice Metodo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jMethodSearchFieldsPanel.add(label56, gridBagConstraints);

        jMethodCodeField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jMethodCodeField.setEnabled(false);
        jMethodCodeField.setMinimumSize(new java.awt.Dimension(100, 25));
        jMethodCodeField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jMethodSearchFieldsPanel.add(jMethodCodeField, gridBagConstraints);

        label57.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label57.setText("Codice Classe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jMethodSearchFieldsPanel.add(label57, gridBagConstraints);

        jMethodCodClassField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jMethodCodClassField.setEnabled(false);
        jMethodCodClassField.setMinimumSize(new java.awt.Dimension(100, 25));
        jMethodCodClassField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jMethodSearchFieldsPanel.add(jMethodCodClassField, gridBagConstraints);

        label58.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label58.setText("Codice Desc.Metodo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jMethodSearchFieldsPanel.add(label58, gridBagConstraints);

        jMethodCodMethodDescriptorField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jMethodCodMethodDescriptorField.setEnabled(false);
        jMethodCodMethodDescriptorField.setMinimumSize(new java.awt.Dimension(100, 25));
        jMethodCodMethodDescriptorField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        jMethodSearchFieldsPanel.add(jMethodCodMethodDescriptorField, gridBagConstraints);

        label59.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label59.setText("Stato");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jMethodSearchFieldsPanel.add(label59, gridBagConstraints);

        jComboBoxMethodStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nuovo", "Immutato", "Modificato", "Cancellato" }));
        jComboBoxMethodStatus.setEnabled(false);
        jComboBoxMethodStatus.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxMethodStatus.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        jMethodSearchFieldsPanel.add(jComboBoxMethodStatus, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jMethodSearchPanel.add(jMethodSearchFieldsPanel, gridBagConstraints);

        jMethodSearchFiltersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtri"));
        jMethodSearchFiltersPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jMethodSearchFiltersPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jMethodSearchFiltersPanel.setLayout(new java.awt.GridBagLayout());

        jMethodSearchByFiltersPanel.setLayout(new java.awt.GridBagLayout());

        label60.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label60.setText("Cerca per");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jMethodSearchByFiltersPanel.add(label60, gridBagConstraints);

        jCheckBoxMethodCode.setText("Codice Metodo");
        jCheckBoxMethodCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMethodCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jMethodSearchByFiltersPanel.add(jCheckBoxMethodCode, gridBagConstraints);

        jCheckBoxMethodCodClass.setText("Codice Classe");
        jCheckBoxMethodCodClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMethodCodClassActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jMethodSearchByFiltersPanel.add(jCheckBoxMethodCodClass, gridBagConstraints);

        jCheckBoxMethodCodMethodDescriptor.setText("Codice Desc.Metodo");
        jCheckBoxMethodCodMethodDescriptor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMethodCodMethodDescriptorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jMethodSearchByFiltersPanel.add(jCheckBoxMethodCodMethodDescriptor, gridBagConstraints);

        jCheckBoxMethodStatus.setText("Stato");
        jCheckBoxMethodStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMethodStatusActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jMethodSearchByFiltersPanel.add(jCheckBoxMethodStatus, gridBagConstraints);

        jMethodSearchFiltersPanel.add(jMethodSearchByFiltersPanel, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jMethodSearchPanel.add(jMethodSearchFiltersPanel, gridBagConstraints);

        jMethodResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Risultati"));
        jMethodResultsPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane12.setMinimumSize(new java.awt.Dimension(800, 200));
        jScrollPane12.setPreferredSize(new java.awt.Dimension(800, 500));

        jMethodTable.setModel(new RepositoryTableModel(
            new Object [][] {
            },
            new String [] {

            }
        ));
        jMethodTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jMethodTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMethodTableMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(jMethodTable);

        jMethodResultsPanel.add(jScrollPane12, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 20.0;
        jMethodSearchPanel.add(jMethodResultsPanel, gridBagConstraints);

        jMethodButtons.setLayout(new java.awt.GridBagLayout());

        jSearchMethodButton.setText("Cerca");
        jSearchMethodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchMethodButtonActionPerformed(evt);
            }
        });
        jMethodButtons.add(jSearchMethodButton, new java.awt.GridBagConstraints());

        jEditMethodButton.setText("Modifica");
        jEditMethodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditMethodButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jMethodButtons.add(jEditMethodButton, gridBagConstraints);

        jDeleteMethodButton.setText("Elimina");
        jDeleteMethodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteMethodButtonActionPerformed(evt);
            }
        });
        jMethodButtons.add(jDeleteMethodButton, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 12.0;
        jMethodSearchPanel.add(jMethodButtons, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Metodo</body></html>", jMethodSearchPanel);

        jTestSearchPanel.setLayout(new java.awt.GridBagLayout());

        jTestSearchFieldsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Campi di Ricerca"));
        jTestSearchFieldsPanel.setMinimumSize(new java.awt.Dimension(700, 100));
        jTestSearchFieldsPanel.setPreferredSize(new java.awt.Dimension(700, 100));
        jTestSearchFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label33.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label33.setText("Codice");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jTestSearchFieldsPanel.add(label33, gridBagConstraints);

        jTestCodeField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTestCodeField.setEnabled(false);
        jTestCodeField.setMinimumSize(new java.awt.Dimension(100, 25));
        jTestCodeField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jTestSearchFieldsPanel.add(jTestCodeField, gridBagConstraints);

        label34.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label34.setText("Nome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jTestSearchFieldsPanel.add(label34, gridBagConstraints);

        jTestNameField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTestNameField.setEnabled(false);
        jTestNameField.setMinimumSize(new java.awt.Dimension(100, 25));
        jTestNameField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jTestSearchFieldsPanel.add(jTestNameField, gridBagConstraints);

        label35.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label35.setText("Path");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jTestSearchFieldsPanel.add(label35, gridBagConstraints);

        jTestPathField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTestPathField.setEnabled(false);
        jTestPathField.setMinimumSize(new java.awt.Dimension(100, 25));
        jTestPathField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jTestSearchFieldsPanel.add(jTestPathField, gridBagConstraints);

        label36.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label36.setText("Progetto Testato");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jTestSearchFieldsPanel.add(label36, gridBagConstraints);

        jComboBoxTestProject.setEnabled(false);
        jComboBoxTestProject.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxTestProject.setPreferredSize(new java.awt.Dimension(100, 25));
        jComboBoxTestProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTestProjectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jTestSearchFieldsPanel.add(jComboBoxTestProject, gridBagConstraints);
        comboBuilder.buildComboBox(jComboBoxTestProject,
            projectBuilder.projectsListQueryBuilder(),false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jTestSearchPanel.add(jTestSearchFieldsPanel, gridBagConstraints);

        jTestSearchFiltersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtri"));
        jTestSearchFiltersPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jTestSearchFiltersPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jTestSearchFiltersPanel.setLayout(new java.awt.GridBagLayout());

        jTestSearchByFiltersPanel.setLayout(new java.awt.GridBagLayout());

        label37.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label37.setText("Cerca per");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jTestSearchByFiltersPanel.add(label37, gridBagConstraints);

        jCheckBoxTestCode.setText("Codice");
        jCheckBoxTestCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxTestCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jTestSearchByFiltersPanel.add(jCheckBoxTestCode, gridBagConstraints);

        jCheckBoxTestName.setText("Nome");
        jCheckBoxTestName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxTestNameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jTestSearchByFiltersPanel.add(jCheckBoxTestName, gridBagConstraints);

        jCheckBoxTestPath.setText("Path");
        jCheckBoxTestPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxTestPathActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jTestSearchByFiltersPanel.add(jCheckBoxTestPath, gridBagConstraints);

        jCheckBoxTestProject.setText("Progetto Testato");
        jCheckBoxTestProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxTestProjectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jTestSearchByFiltersPanel.add(jCheckBoxTestProject, gridBagConstraints);

        jTestSearchFiltersPanel.add(jTestSearchByFiltersPanel, new java.awt.GridBagConstraints());

        jShowForEachTestFiltersPanel.setLayout(new java.awt.GridBagLayout());
        jTestSearchFiltersPanel.add(jShowForEachTestFiltersPanel, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jTestSearchPanel.add(jTestSearchFiltersPanel, gridBagConstraints);

        jTestSearchResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Risultati"));
        jTestSearchResultsPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane6.setMinimumSize(new java.awt.Dimension(800, 200));
        jScrollPane6.setPreferredSize(new java.awt.Dimension(800, 500));

        jTestTable.setModel(new RepositoryTableModel(
            new Object [][] {
            },
            new String [] {

            }
        ));
        jTestTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jTestTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTestTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTestTableMousePressed(evt);
            }
        });
        jScrollPane6.setViewportView(jTestTable);

        jTestSearchResultsPanel.add(jScrollPane6, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 20.0;
        jTestSearchPanel.add(jTestSearchResultsPanel, gridBagConstraints);

        jTestButtons.setLayout(new java.awt.GridBagLayout());

        jEditTestButton.setText("Modifica");
        jEditTestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditTestButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jTestButtons.add(jEditTestButton, gridBagConstraints);

        jSearchTestButton.setText("Cerca");
        jSearchTestButton.setToolTipText("");
        jSearchTestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchTestButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 12.0;
        jTestButtons.add(jSearchTestButton, gridBagConstraints);

        jDeleteTestButton.setText("Elimina");
        jDeleteTestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteTestButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jTestButtons.add(jDeleteTestButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 12.0;
        jTestSearchPanel.add(jTestButtons, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Tests</body></html>", jTestSearchPanel);

        jExecutionSearchPanel.setLayout(new java.awt.GridBagLayout());

        jExecutionSearchFieldsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Campi di Ricerca"));
        jExecutionSearchFieldsPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jExecutionSearchFieldsPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jExecutionSearchFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label69.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label69.setText("Codice Esecuzione");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jExecutionSearchFieldsPanel.add(label69, gridBagConstraints);

        jExecutionCodeField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jExecutionCodeField.setEnabled(false);
        jExecutionCodeField.setMinimumSize(new java.awt.Dimension(100, 25));
        jExecutionCodeField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jExecutionSearchFieldsPanel.add(jExecutionCodeField, gridBagConstraints);

        label70.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label70.setText("Codice Test Eseguito");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jExecutionSearchFieldsPanel.add(label70, gridBagConstraints);

        jExecutionCodTestField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jExecutionCodTestField.setEnabled(false);
        jExecutionCodTestField.setMinimumSize(new java.awt.Dimension(100, 25));
        jExecutionCodTestField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jExecutionSearchFieldsPanel.add(jExecutionCodTestField, gridBagConstraints);

        label71.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label71.setText("Codice Release Testata");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jExecutionSearchFieldsPanel.add(label71, gridBagConstraints);

        jExecutionCodReleaseField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jExecutionCodReleaseField.setEnabled(false);
        jExecutionCodReleaseField.setMinimumSize(new java.awt.Dimension(100, 25));
        jExecutionCodReleaseField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jExecutionSearchFieldsPanel.add(jExecutionCodReleaseField, gridBagConstraints);

        label73.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label73.setText("Data Esecuzione");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jExecutionSearchFieldsPanel.add(label73, gridBagConstraints);

        jExecutionDateField.setEnabled(false);
        jExecutionDateField.setMinimumSize(new java.awt.Dimension(100, 25));
        jExecutionDateField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        jExecutionSearchFieldsPanel.add(jExecutionDateField, gridBagConstraints);

        label72.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label72.setText("Esito");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jExecutionSearchFieldsPanel.add(label72, gridBagConstraints);

        jComboBoxExecutionResult.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Riuscito", "Fallito" }));
        jComboBoxExecutionResult.setEnabled(false);
        jComboBoxExecutionResult.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxExecutionResult.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jExecutionSearchFieldsPanel.add(jComboBoxExecutionResult, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jExecutionSearchPanel.add(jExecutionSearchFieldsPanel, gridBagConstraints);

        jExecutionSearchFiltersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtri"));
        jExecutionSearchFiltersPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jExecutionSearchFiltersPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jExecutionSearchFiltersPanel.setLayout(new java.awt.GridBagLayout());

        label74.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label74.setText("Cerca per");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jExecutionSearchFiltersPanel.add(label74, gridBagConstraints);

        jCheckBoxExecutionCode.setText("Codice Esecuzione");
        jCheckBoxExecutionCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxExecutionCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jExecutionSearchFiltersPanel.add(jCheckBoxExecutionCode, gridBagConstraints);

        jCheckBoxExecutionCodTest.setText("Codice Test Eseguito");
        jCheckBoxExecutionCodTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxExecutionCodTestActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jExecutionSearchFiltersPanel.add(jCheckBoxExecutionCodTest, gridBagConstraints);

        jCheckBoxExecutionCodRelease.setText("Codice Release Testata");
        jCheckBoxExecutionCodRelease.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxExecutionCodReleaseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jExecutionSearchFiltersPanel.add(jCheckBoxExecutionCodRelease, gridBagConstraints);

        jCheckBoxExecutionDate.setText("Data Esecuzione");
        jCheckBoxExecutionDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxExecutionDateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jExecutionSearchFiltersPanel.add(jCheckBoxExecutionDate, gridBagConstraints);

        jCheckBoxExecutionResult.setText("Esito");
        jCheckBoxExecutionResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxExecutionResultActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jExecutionSearchFiltersPanel.add(jCheckBoxExecutionResult, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jExecutionSearchPanel.add(jExecutionSearchFiltersPanel, gridBagConstraints);

        jExecutionResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Risultati"));
        jExecutionResultsPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane9.setMinimumSize(new java.awt.Dimension(800, 200));
        jScrollPane9.setPreferredSize(new java.awt.Dimension(800, 500));

        jExecutionTable.setModel(new RepositoryTableModel(
            new Object [][] {
            },
            new String [] {

            }
        ));
        jExecutionTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jExecutionTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jExecutionTableMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(jExecutionTable);

        jExecutionResultsPanel.add(jScrollPane9, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 20.0;
        jExecutionSearchPanel.add(jExecutionResultsPanel, gridBagConstraints);

        jExecutionButtons.setLayout(new java.awt.GridBagLayout());

        jSearchExecutionButton.setText("Cerca");
        jSearchExecutionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchExecutionButtonActionPerformed(evt);
            }
        });
        jExecutionButtons.add(jSearchExecutionButton, new java.awt.GridBagConstraints());

        jEditExecutionButton.setText("Modifica");
        jEditExecutionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditExecutionButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jExecutionButtons.add(jEditExecutionButton, gridBagConstraints);

        jDeleteExecutionButton.setText("Elimina");
        jDeleteExecutionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteExecutionButtonActionPerformed(evt);
            }
        });
        jExecutionButtons.add(jDeleteExecutionButton, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 12.0;
        jExecutionSearchPanel.add(jExecutionButtons, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Esecuzione test</body></html>", jExecutionSearchPanel);

        jChangelogSearchPanel.setLayout(new java.awt.GridBagLayout());

        jChangelogSearchFieldsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Campi di Ricerca"));
        jChangelogSearchFieldsPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jChangelogSearchFieldsPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jChangelogSearchFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label38.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label38.setText("Codice");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jChangelogSearchFieldsPanel.add(label38, gridBagConstraints);

        jChangelogCodeField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jChangelogCodeField.setEnabled(false);
        jChangelogCodeField.setMinimumSize(new java.awt.Dimension(100, 25));
        jChangelogCodeField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jChangelogSearchFieldsPanel.add(jChangelogCodeField, gridBagConstraints);

        label39.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label39.setText("Descrizione");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jChangelogSearchFieldsPanel.add(label39, gridBagConstraints);

        jChangelogDescriptionField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jChangelogDescriptionField.setEnabled(false);
        jChangelogDescriptionField.setMinimumSize(new java.awt.Dimension(100, 25));
        jChangelogDescriptionField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jChangelogSearchFieldsPanel.add(jChangelogDescriptionField, gridBagConstraints);

        label40.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label40.setText("Codice Package");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jChangelogSearchFieldsPanel.add(label40, gridBagConstraints);

        jChangelogCodPackageField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jChangelogCodPackageField.setEnabled(false);
        jChangelogCodPackageField.setMinimumSize(new java.awt.Dimension(100, 25));
        jChangelogCodPackageField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jChangelogSearchFieldsPanel.add(jChangelogCodPackageField, gridBagConstraints);

        label41.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label41.setText("Codice Classe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jChangelogSearchFieldsPanel.add(label41, gridBagConstraints);

        jChangelogCodClassField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jChangelogCodClassField.setEnabled(false);
        jChangelogCodClassField.setMinimumSize(new java.awt.Dimension(100, 25));
        jChangelogCodClassField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jChangelogSearchFieldsPanel.add(jChangelogCodClassField, gridBagConstraints);

        label42.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label42.setText("Codice Metodo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jChangelogSearchFieldsPanel.add(label42, gridBagConstraints);

        jChangelogCodMethodField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jChangelogCodMethodField.setEnabled(false);
        jChangelogCodMethodField.setMinimumSize(new java.awt.Dimension(100, 25));
        jChangelogCodMethodField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        jChangelogSearchFieldsPanel.add(jChangelogCodMethodField, gridBagConstraints);

        label43.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label43.setText("Tipo Modifica");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jChangelogSearchFieldsPanel.add(label43, gridBagConstraints);

        jComboBoxChangelogChangeType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Modifica", "Cancellazione" }));
        jComboBoxChangelogChangeType.setEnabled(false);
        jComboBoxChangelogChangeType.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxChangelogChangeType.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        jChangelogSearchFieldsPanel.add(jComboBoxChangelogChangeType, gridBagConstraints);

        label46.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label46.setText("Progetto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jChangelogSearchFieldsPanel.add(label46, gridBagConstraints);

        jComboBoxChangelogProject.setEnabled(false);
        jComboBoxChangelogProject.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxChangelogProject.setPreferredSize(new java.awt.Dimension(100, 25));
        jComboBoxChangelogProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxChangelogProjectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jChangelogSearchFieldsPanel.add(jComboBoxChangelogProject, gridBagConstraints);
        comboBuilder.buildComboBox(jComboBoxChangelogProject,
            projectBuilder.projectsListQueryBuilder(),false);

        label45.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label45.setText("Data Apertura");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jChangelogSearchFieldsPanel.add(label45, gridBagConstraints);

        jChangelogOpeningDateField.setEnabled(false);
        jChangelogOpeningDateField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jChangelogOpeningDateField.setMinimumSize(new java.awt.Dimension(125, 25));
        jChangelogOpeningDateField.setPreferredSize(new java.awt.Dimension(125, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        jChangelogSearchFieldsPanel.add(jChangelogOpeningDateField, gridBagConstraints);

        label44.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label44.setText("Data Chiusura");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jChangelogSearchFieldsPanel.add(label44, gridBagConstraints);

        jChangelogClosingDateField.setEnabled(false);
        jChangelogClosingDateField.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jChangelogClosingDateField.setMinimumSize(new java.awt.Dimension(125, 25));
        jChangelogClosingDateField.setPreferredSize(new java.awt.Dimension(125, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jChangelogSearchFieldsPanel.add(jChangelogClosingDateField, gridBagConstraints);

        label49.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label49.setText("Oggetto modificato");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jChangelogSearchFieldsPanel.add(label49, gridBagConstraints);

        jComboBoxChangelogChangedObject.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Package", "Classe", "Metodo" }));
        jComboBoxChangelogChangedObject.setEnabled(false);
        jComboBoxChangelogChangedObject.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxChangelogChangedObject.setPreferredSize(new java.awt.Dimension(100, 25));
        jComboBoxChangelogChangedObject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxChangelogChangedObjectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jChangelogSearchFieldsPanel.add(jComboBoxChangelogChangedObject, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jChangelogSearchPanel.add(jChangelogSearchFieldsPanel, gridBagConstraints);

        jChangelogSearchFiltersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtri"));
        jChangelogSearchFiltersPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jChangelogSearchFiltersPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jChangelogSearchFiltersPanel.setLayout(new java.awt.GridBagLayout());

        jChangelogSearchByFiltersPanel.setLayout(new java.awt.GridBagLayout());

        label47.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label47.setText("Cerca per");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jChangelogSearchByFiltersPanel.add(label47, gridBagConstraints);

        jCheckBoxChangelogCode.setText("Codice");
        jCheckBoxChangelogCode.setToolTipText("");
        jCheckBoxChangelogCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxChangelogCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jChangelogSearchByFiltersPanel.add(jCheckBoxChangelogCode, gridBagConstraints);

        jCheckBoxChangelogDescription.setText("Descrizione");
        jCheckBoxChangelogDescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxChangelogDescriptionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jChangelogSearchByFiltersPanel.add(jCheckBoxChangelogDescription, gridBagConstraints);

        jCheckBoxChangelogChangeType.setText("Tipo Modifica");
        jCheckBoxChangelogChangeType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxChangelogChangeTypeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jChangelogSearchByFiltersPanel.add(jCheckBoxChangelogChangeType, gridBagConstraints);

        jCheckBoxChangelogChangedObject.setText("Oggetto Modificato");
        jCheckBoxChangelogChangedObject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxChangelogChangedObjectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jChangelogSearchByFiltersPanel.add(jCheckBoxChangelogChangedObject, gridBagConstraints);

        jCheckBoxChangelogOpeningDate.setText("Data Apertura");
        jCheckBoxChangelogOpeningDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxChangelogOpeningDateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jChangelogSearchByFiltersPanel.add(jCheckBoxChangelogOpeningDate, gridBagConstraints);

        jCheckBoxChangelogClosingDate.setText("Data Chiusura");
        jCheckBoxChangelogClosingDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxChangelogClosingDateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jChangelogSearchByFiltersPanel.add(jCheckBoxChangelogClosingDate, gridBagConstraints);

        jCheckBoxChangelogProject.setText("Progetto");
        jCheckBoxChangelogProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxChangelogProjectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jChangelogSearchByFiltersPanel.add(jCheckBoxChangelogProject, gridBagConstraints);

        jChangelogSearchFiltersPanel.add(jChangelogSearchByFiltersPanel, new java.awt.GridBagConstraints());

        jShowForEachChangelogFiltersPanel.setLayout(new java.awt.GridBagLayout());
        jChangelogSearchFiltersPanel.add(jShowForEachChangelogFiltersPanel, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jChangelogSearchPanel.add(jChangelogSearchFiltersPanel, gridBagConstraints);

        jChangelogSearchResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Risultati"));
        jChangelogSearchResultsPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane8.setMinimumSize(new java.awt.Dimension(800, 200));
        jScrollPane8.setPreferredSize(new java.awt.Dimension(800, 500));

        jChangelogTable.setModel(new RepositoryTableModel(
            new Object [][] {
            },
            new String [] {

            }
        ));
        jChangelogTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jChangelogTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jChangelogTableMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(jChangelogTable);

        jChangelogSearchResultsPanel.add(jScrollPane8, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 20.0;
        jChangelogSearchPanel.add(jChangelogSearchResultsPanel, gridBagConstraints);

        jChangelogButtons.setLayout(new java.awt.GridBagLayout());

        jSearchChangelogButton.setText("Cerca");
        jSearchChangelogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchChangelogButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 12.0;
        jChangelogButtons.add(jSearchChangelogButton, gridBagConstraints);

        jEditChangelogButton.setText("Modifica");
        jEditChangelogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditChangelogButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jChangelogButtons.add(jEditChangelogButton, gridBagConstraints);

        jDeleteChangelogButton.setText("Elimina");
        jDeleteChangelogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteChangelogButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jChangelogButtons.add(jDeleteChangelogButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 12.0;
        jChangelogSearchPanel.add(jChangelogButtons, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Changelog</body></html>", jChangelogSearchPanel);

        jOtherSearchPanel.setLayout(new java.awt.GridBagLayout());

        jOtherChoosePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Scegli Assegnazione"));
        jOtherChoosePanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jOtherChoosePanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jOtherChoosePanel.setLayout(new java.awt.GridBagLayout());

        jAuthorsRadioButton.setSelected(true);
        jAuthorsRadioButton.setText("Autori");
        jAuthorsRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAuthorsRadioButtonActionPerformed(evt);
            }
        });
        jOtherChoosePanel.add(jAuthorsRadioButton, new java.awt.GridBagConstraints());

        jTestsRadioButton.setText("Test");
        jTestsRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTestsRadioButtonActionPerformed(evt);
            }
        });
        jOtherChoosePanel.add(jTestsRadioButton, new java.awt.GridBagConstraints());

        jComboBoxAuthors.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Progetti", "Classi", "Metodi", "Test", "Logs" }));
        jComboBoxAuthors.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxAuthors.setPreferredSize(new java.awt.Dimension(100, 25));
        jComboBoxAuthors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAuthorsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jOtherChoosePanel.add(jComboBoxAuthors, gridBagConstraints);

        jComboBoxTests.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Classi testate", "Metodi testate" }));
        jComboBoxTests.setEnabled(false);
        jComboBoxTests.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBoxTests.setPreferredSize(new java.awt.Dimension(100, 25));
        jComboBoxTests.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTestsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
        jOtherChoosePanel.add(jComboBoxTests, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jOtherSearchPanel.add(jOtherChoosePanel, gridBagConstraints);

        jOtherSearchFieldsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Campi di Ricerca"));
        jOtherSearchFieldsPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jOtherSearchFieldsPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jOtherSearchFieldsPanel.setLayout(new java.awt.GridBagLayout());

        firstFieldLabel.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        firstFieldLabel.setText("Codice Autore");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jOtherSearchFieldsPanel.add(firstFieldLabel, gridBagConstraints);

        jOtherCodeField.setEnabled(false);
        jOtherCodeField.setMinimumSize(new java.awt.Dimension(100, 25));
        jOtherCodeField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jOtherSearchFieldsPanel.add(jOtherCodeField, gridBagConstraints);

        jOtherGeneralField.setEnabled(false);
        jOtherGeneralField.setMinimumSize(new java.awt.Dimension(100, 25));
        jOtherGeneralField.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jOtherSearchFieldsPanel.add(jOtherGeneralField, gridBagConstraints);

        secondFieldLabel.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        secondFieldLabel.setText("Nome Progetto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jOtherSearchFieldsPanel.add(secondFieldLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.5;
        jOtherSearchPanel.add(jOtherSearchFieldsPanel, gridBagConstraints);

        jOtherSearchFiltersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtri"));
        jOtherSearchFiltersPanel.setMinimumSize(new java.awt.Dimension(700, 125));
        jOtherSearchFiltersPanel.setPreferredSize(new java.awt.Dimension(700, 125));
        jOtherSearchFiltersPanel.setLayout(new java.awt.GridBagLayout());

        jCheckBoxOtherCode.setText("Autore");
        jCheckBoxOtherCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxOtherCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jOtherSearchFiltersPanel.add(jCheckBoxOtherCode, gridBagConstraints);

        jCheckBoxOtherGeneral.setText("Progetto");
        jCheckBoxOtherGeneral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxOtherGeneralActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
        jOtherSearchFiltersPanel.add(jCheckBoxOtherGeneral, gridBagConstraints);

        label77.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label77.setText("Cerca per");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jOtherSearchFiltersPanel.add(label77, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.5;
        jOtherSearchPanel.add(jOtherSearchFiltersPanel, gridBagConstraints);

        jOtherResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Risultati"));
        jOtherResultsPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane13.setMinimumSize(new java.awt.Dimension(800, 200));
        jScrollPane13.setPreferredSize(new java.awt.Dimension(800, 500));

        jOtherTable.setModel(new RepositoryTableModel(
            new Object [][] {
            },
            new String [] {

            }
        ));
        jOtherTable.setPreferredSize(new java.awt.Dimension(700, 400));
        jOtherTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jOtherTableMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(jOtherTable);

        jOtherResultsPanel.add(jScrollPane13, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 20.0;
        jOtherSearchPanel.add(jOtherResultsPanel, gridBagConstraints);

        jOtherButtons.setLayout(new java.awt.GridBagLayout());

        jSearchOtherButton.setText("Cerca");
        jSearchOtherButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchOtherButtonActionPerformed(evt);
            }
        });
        jOtherButtons.add(jSearchOtherButton, new java.awt.GridBagConstraints());

        jDeleteOtherButton.setText("Elimina");
        jDeleteOtherButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteOtherButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jOtherButtons.add(jDeleteOtherButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 12.0;
        jOtherSearchPanel.add(jOtherButtons, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Assegnazione Autori/Test</body></html>", jOtherSearchPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);
        jTabbedPane1.getAccessibleContext().setAccessibleName("Autori");

        jMenuOptions.setText("Opzioni");

        jMenuItemEnableDisableEdit.setText("Abilita modifica");
        jMenuItemEnableDisableEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEnableDisableEditActionPerformed(evt);
            }
        });
        jMenuOptions.add(jMenuItemEnableDisableEdit);

        jMenuItemRefreshAll.setText("Aggiorna");
        jMenuItemRefreshAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRefreshAllActionPerformed(evt);
            }
        });
        jMenuOptions.add(jMenuItemRefreshAll);

        jMenuBar1.add(jMenuOptions);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jSearchAuthorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchAuthorButtonActionPerformed
       DefaultTableModel model= (DefaultTableModel)jAuthorTable.getModel();
       AuthorQueryExecutor a=new AuthorQueryExecutor();
       a.searchAuthor(this,model);
    }//GEN-LAST:event_jSearchAuthorButtonActionPerformed

    private void jCheckBoxAuthorCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAuthorCodeActionPerformed
        if(jCheckBoxAuthorCode.isSelected()){
            jAuthorCodeField.setEnabled(true);
        }
        else{
            jAuthorCodeField.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxAuthorCodeActionPerformed

    private void jCheckBoxAuthorEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAuthorEmailActionPerformed
        if(jCheckBoxAuthorEmail.isSelected()){
            jAuthorEmailField.setEnabled(true);
        }
        else{
            jAuthorEmailField.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxAuthorEmailActionPerformed

    private void jCheckBoxAuthorSurnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAuthorSurnameActionPerformed
        if(jCheckBoxAuthorSurname.isSelected()){
            jAuthorSurnameField.setEnabled(true);
        }

        else{
            jAuthorSurnameField.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxAuthorSurnameActionPerformed

    private void jCheckBoxAuthorNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAuthorNameActionPerformed
        if(jCheckBoxAuthorName.isSelected()){
            jAuthorNameField.setEnabled(true);
        }
        else{
            jAuthorNameField.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxAuthorNameActionPerformed

    private void jAuthorCodeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAuthorCodeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jAuthorCodeFieldActionPerformed

    private void jAuthorEmailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAuthorEmailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jAuthorEmailFieldActionPerformed

    private void jAuthorSurnameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAuthorSurnameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jAuthorSurnameFieldActionPerformed

    private void jAuthorNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAuthorNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jAuthorNameFieldActionPerformed

    private void jAuthorTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jAuthorTableMousePressed
        //Se fatto il doppio click,aprire scheda di approfondimento sull'autore
        if (evt.getButton() == evt.BUTTON1 && evt.getClickCount() == 2 && !isEditModeEnabled) {
                String name = (String) jAuthorTable.getValueAt(jAuthorTable.getSelectedRow(), 3);
                String code = jAuthorTable.getValueAt(jAuthorTable.getSelectedRow(), 0).toString();
                String surname = (String) jAuthorTable.getValueAt(jAuthorTable.getSelectedRow(), 1);
                String email = (String) jAuthorTable.getValueAt(jAuthorTable.getSelectedRow(), 2);
                AuthorCard A=new AuthorCard(this,false);
                A.setLocationRelativeTo(null);
                A.setAuthorName(name);
                A.setAuthorSurname(surname);
                A.setAuthorEmail(email);
                A.showInformations(code);
                A.setVisible(true);
        }   
    }//GEN-LAST:event_jAuthorTableMousePressed

    private void jCheckBoxProjectNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxProjectNameActionPerformed
        if(jCheckBoxProjectName.isSelected()){
            jProjectNameField.setEnabled(true);
        }
        else{
            jProjectNameField.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxProjectNameActionPerformed

    private void jCheckBoxProjectDateCreationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxProjectDateCreationActionPerformed
        if(jCheckBoxProjectDateCreation.isSelected()){
            jProjectDateCreationField.setEnabled(true);
        }
        else{
            jProjectDateCreationField.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxProjectDateCreationActionPerformed

    private void jCheckBoxProjectDateCancellationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxProjectDateCancellationActionPerformed
        if(jCheckBoxProjectDateCancellation.isSelected()){
            jProjectDateCancellationField.setEnabled(true);
        }
        else{
            jProjectDateCancellationField.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxProjectDateCancellationActionPerformed

    private void jCheckBoxProjectPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxProjectPathActionPerformed
        if(jCheckBoxProjectPath.isSelected()){
            jProjectPathField.setEnabled(true);
        }
        else{
            jProjectPathField.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxProjectPathActionPerformed

    private void jSearchProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchProjectButtonActionPerformed
       DefaultTableModel model= (DefaultTableModel)jProjectTable.getModel();
       ProjectQueryExecutor p=new ProjectQueryExecutor();
       p.searchProject(this,model);
       projectNames.clear();
       for(int i=0;i<jProjectTable.getRowCount();i++){
            String code= jProjectTable.getValueAt(i,0).toString();
            projectNames.add(code);
       }
    }//GEN-LAST:event_jSearchProjectButtonActionPerformed

    private void jCheckBoxPackageDescriptorCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPackageDescriptorCodeActionPerformed
        if(jCheckBoxPackageDescriptorCode.isSelected())
            jPackageDescriptorCodeField.setEnabled(true);
        else
            jPackageDescriptorCodeField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxPackageDescriptorCodeActionPerformed

    private void jCheckBoxPackageDescriptorNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPackageDescriptorNameActionPerformed
        if(jCheckBoxPackageDescriptorName.isSelected())
            jPackageDescriptorNameField.setEnabled(true);
        else
            jPackageDescriptorNameField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxPackageDescriptorNameActionPerformed

    private void jCheckBoxPackageDescriptorEncloserPackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPackageDescriptorEncloserPackActionPerformed
        if(jCheckBoxPackageDescriptorEncloserPack.isSelected())
            jPackageDescriptorEncloserPackField.setEnabled(true);
        else
            jPackageDescriptorEncloserPackField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxPackageDescriptorEncloserPackActionPerformed

    private void jSearchPackageDescriptorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchPackageDescriptorButtonActionPerformed
       DefaultTableModel model= (DefaultTableModel)jPackageDescriptorTable.getModel();
       PackageQueryExecutor p=new PackageQueryExecutor();
       p.searchPackageDescriptor(this,model);
    }//GEN-LAST:event_jSearchPackageDescriptorButtonActionPerformed

    private void jComboBoxClassDescriptorScopeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxClassDescriptorScopeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxClassDescriptorScopeActionPerformed

    private void jCheckBoxClassDescriptorCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxClassDescriptorCodeActionPerformed
        if(jCheckBoxClassDescriptorCode.isSelected())
            jClassDescriptorCodeField.setEnabled(true);
        else
            jClassDescriptorCodeField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxClassDescriptorCodeActionPerformed

    private void jCheckBoxClassDescriptorNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxClassDescriptorNameActionPerformed
        if(jCheckBoxClassDescriptorName.isSelected())
            jClassDescriptorNameField.setEnabled(true);
        else
            jClassDescriptorNameField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxClassDescriptorNameActionPerformed

    private void jCheckBoxClassDescriptorSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxClassDescriptorSourceActionPerformed
        if(jCheckBoxClassDescriptorSource.isSelected())
            jClassDescriptorSourceField.setEnabled(true);
        else
            jClassDescriptorSourceField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxClassDescriptorSourceActionPerformed

    private void jCheckBoxClassDescriptorCodSuperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxClassDescriptorCodSuperActionPerformed
        if(jCheckBoxClassDescriptorCodSuper.isSelected())
            jClassDescriptorCodSuperField.setEnabled(true);
        else
            jClassDescriptorCodSuperField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxClassDescriptorCodSuperActionPerformed

    private void jCheckBoxClassDescriptorCodPackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxClassDescriptorCodPackageActionPerformed
        if(jCheckBoxClassDescriptorCodPackage.isSelected())
            jClassDescriptorCodPackageField.setEnabled(true);
        else
            jClassDescriptorCodPackageField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxClassDescriptorCodPackageActionPerformed

    private void jCheckBoxClassDescriptorScopeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxClassDescriptorScopeActionPerformed
        if(jCheckBoxClassDescriptorScope.isSelected())
            jComboBoxClassDescriptorScope.setEnabled(true);
        else
            jComboBoxClassDescriptorScope.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxClassDescriptorScopeActionPerformed

    private void jCheckBoxClassDescriptorModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxClassDescriptorModifierActionPerformed
        if(jCheckBoxClassDescriptorModifier.isSelected())
            jComboBoxClassDescriptorModifier.setEnabled(true);
        else
            jComboBoxClassDescriptorModifier.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxClassDescriptorModifierActionPerformed

    private void jSearchClassDescriptorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchClassDescriptorButtonActionPerformed
       DefaultTableModel model= (DefaultTableModel)jClassDescriptorTable.getModel();
       ClassQueryExecutor classes=new ClassQueryExecutor();
       classes.searchClassDescriptor(this,model);
    }//GEN-LAST:event_jSearchClassDescriptorButtonActionPerformed

    private void jSearchMethodDescriptorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchMethodDescriptorButtonActionPerformed
       DefaultTableModel model= (DefaultTableModel)jMethodDescriptorTable.getModel();
       MethodQueryExecutor m=new MethodQueryExecutor();
       m.searchDescriptorMethod(this,model);
    }//GEN-LAST:event_jSearchMethodDescriptorButtonActionPerformed

    private void jCheckBoxMethodDescriptorCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMethodDescriptorCodeActionPerformed
        if(jCheckBoxMethodDescriptorCode.isSelected())
            jMethodDescriptorCodeField.setEnabled(true);
        else
            jMethodDescriptorCodeField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxMethodDescriptorCodeActionPerformed

    private void jCheckBoxMethodDescriptorNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMethodDescriptorNameActionPerformed
        if(jCheckBoxMethodDescriptorName.isSelected())
            jMethodDescriptorNameField.setEnabled(true);
        else
            jMethodDescriptorNameField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxMethodDescriptorNameActionPerformed

    private void jCheckBoxMethodDescriptorReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMethodDescriptorReturnActionPerformed
        if(jCheckBoxMethodDescriptorReturn.isSelected())
            jMethodDescriptorReturnField.setEnabled(true);
        else
            jMethodDescriptorReturnField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxMethodDescriptorReturnActionPerformed

    private void jCheckBoxMethodDescriptorInputParametersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMethodDescriptorInputParametersActionPerformed
        if(jCheckBoxMethodDescriptorInputParameters.isSelected())
            jMethodDescriptorInputParametersField.setEnabled(true);
        else
            jMethodDescriptorInputParametersField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxMethodDescriptorInputParametersActionPerformed

    private void jCheckBoxMethodDescriptorScopeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMethodDescriptorScopeActionPerformed
        if(jCheckBoxMethodDescriptorScope.isSelected())
            jComboBoxMethodDescriptorScope.setEnabled(true);
        else
            jComboBoxMethodDescriptorScope.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxMethodDescriptorScopeActionPerformed

    private void jCheckBoxMethodDescriptorModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMethodDescriptorModifierActionPerformed
        if(jCheckBoxMethodDescriptorModifier.isSelected())
            jComboBoxMethodDescriptorModifier.setEnabled(true);
        else
            jComboBoxMethodDescriptorModifier.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxMethodDescriptorModifierActionPerformed

    private void jCheckBoxTestCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTestCodeActionPerformed
        if(jCheckBoxTestCode.isSelected())
            jTestCodeField.setEnabled(true);
        else
            jTestCodeField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxTestCodeActionPerformed

    private void jCheckBoxTestNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTestNameActionPerformed
        if(jCheckBoxTestName.isSelected())
            jTestNameField.setEnabled(true);
        else
            jTestNameField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxTestNameActionPerformed

    private void jCheckBoxTestPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTestPathActionPerformed
        if(jCheckBoxTestPath.isSelected())
            jTestPathField.setEnabled(true);
        else
            jTestPathField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxTestPathActionPerformed

    private void jSearchTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchTestButtonActionPerformed
       DefaultTableModel model= (DefaultTableModel)jTestTable.getModel();
       TestQueryExecutor t=new TestQueryExecutor();
       t.searchTest(this,model);
    }//GEN-LAST:event_jSearchTestButtonActionPerformed

    private void jComboBoxTestProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTestProjectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTestProjectActionPerformed

    private void jCheckBoxTestProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTestProjectActionPerformed
        if(jCheckBoxTestProject.isSelected())
            jComboBoxTestProject.setEnabled(true);
        else
            jComboBoxTestProject.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxTestProjectActionPerformed

    private void jCheckBoxChangelogOpeningDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxChangelogOpeningDateActionPerformed
        if(jCheckBoxChangelogOpeningDate.isSelected())
            jChangelogOpeningDateField.setEnabled(true);
        else
            jChangelogOpeningDateField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxChangelogOpeningDateActionPerformed

    private void jCheckBoxChangelogCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxChangelogCodeActionPerformed
        if(jCheckBoxChangelogCode.isSelected())
            jChangelogCodeField.setEnabled(true);
        else
            jChangelogCodeField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxChangelogCodeActionPerformed

    private void jCheckBoxChangelogDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxChangelogDescriptionActionPerformed
        if(jCheckBoxChangelogDescription.isSelected())
            jChangelogDescriptionField.setEnabled(true);
        else
            jChangelogDescriptionField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxChangelogDescriptionActionPerformed

    private void jCheckBoxChangelogChangeTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxChangelogChangeTypeActionPerformed
        if(jCheckBoxChangelogChangeType.isSelected())
            jComboBoxChangelogChangeType.setEnabled(true);
        else
            jComboBoxChangelogChangeType.setEnabled(false);
            
        
    }//GEN-LAST:event_jCheckBoxChangelogChangeTypeActionPerformed

    private void jCheckBoxChangelogChangedObjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxChangelogChangedObjectActionPerformed
        if(jCheckBoxChangelogChangedObject.isSelected()){
            jComboBoxChangelogChangedObject.setEnabled(true);
            verifyChangelogCB();
        }
        else{
            jComboBoxChangelogChangedObject.setEnabled(false);
            jChangelogCodClassField.setEnabled(false);
            jChangelogCodMethodField.setEnabled(false);
            jChangelogCodPackageField.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxChangelogChangedObjectActionPerformed

    private void jCheckBoxChangelogClosingDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxChangelogClosingDateActionPerformed
       if(jCheckBoxChangelogClosingDate.isSelected())
            jChangelogClosingDateField.setEnabled(true);
        else
            jChangelogClosingDateField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxChangelogClosingDateActionPerformed

    private void jComboBoxChangelogProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxChangelogProjectActionPerformed

    }//GEN-LAST:event_jComboBoxChangelogProjectActionPerformed

    private void jSearchChangelogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchChangelogButtonActionPerformed
       DefaultTableModel model= (DefaultTableModel)jChangelogTable.getModel();
       ChangelogQueryExecutor cl=new ChangelogQueryExecutor();
       cl.searchChangelog(this,model);
    }//GEN-LAST:event_jSearchChangelogButtonActionPerformed

    private void jClassDescriptorTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jClassDescriptorTableMouseClicked
        //Se è stato cliccato il tasto destro, copia la cella cliccata nella Clipboard
        if(SwingUtilities.isRightMouseButton(evt)){
            int row=jClassDescriptorTable.rowAtPoint(evt.getPoint());
            int column=jClassDescriptorTable.columnAtPoint(evt.getPoint());
            jClassDescriptorTable.changeSelection(row, column, false, false);
            jClassDescriptorTable.requestFocus();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(jClassDescriptorTable.getValueAt(row,column).toString());
		clipboard.setContents(strSel, null);
        }
    }//GEN-LAST:event_jClassDescriptorTableMouseClicked

    private void jChangelogTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jChangelogTableMouseClicked
        //Se è stato cliccato il tasto destro, copia la cella cliccata nella Clipboard
        if(SwingUtilities.isRightMouseButton(evt)){
            int row=jChangelogTable.rowAtPoint(evt.getPoint());
            int column=jChangelogTable.columnAtPoint(evt.getPoint());
            jChangelogTable.changeSelection(row, column, false, false);
            jChangelogTable.requestFocus();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(jChangelogTable.getValueAt(row,column).toString());
		clipboard.setContents(strSel, null);
        }
    }//GEN-LAST:event_jChangelogTableMouseClicked

    private void jTestTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTestTableMouseClicked
        //Se è stato cliccato il tasto destro, copia la cella cliccata nella Clipboard
        if(SwingUtilities.isRightMouseButton(evt)){
            int row=jTestTable.rowAtPoint(evt.getPoint());
            int column=jTestTable.columnAtPoint(evt.getPoint());
            jTestTable.changeSelection(row, column, false, false);
            jTestTable.requestFocus();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(jTestTable.getValueAt(row,column).toString());
		clipboard.setContents(strSel, null);
        }
    }//GEN-LAST:event_jTestTableMouseClicked

    private void jSearchMethodDescriptorButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSearchMethodDescriptorButtonMouseClicked
        
    }//GEN-LAST:event_jSearchMethodDescriptorButtonMouseClicked

    private void jMethodDescriptorTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMethodDescriptorTableMouseClicked
        //Se è stato cliccato il tasto destro, copia la cella cliccata nella Clipboard
        if(SwingUtilities.isRightMouseButton(evt)){
            int row=jMethodDescriptorTable.rowAtPoint(evt.getPoint());
            int column=jMethodDescriptorTable.columnAtPoint(evt.getPoint());
            jMethodDescriptorTable.changeSelection(row, column, false, false);
            jMethodDescriptorTable.requestFocus();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(jMethodDescriptorTable.getValueAt(row,column).toString());
		clipboard.setContents(strSel, null);
        }
    }//GEN-LAST:event_jMethodDescriptorTableMouseClicked

    private void jPackageDescriptorTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPackageDescriptorTableMouseClicked
        //Se è stato cliccato il tasto destro, copia la cella cliccata nella Clipboard
        if(SwingUtilities.isRightMouseButton(evt)){
            int row=jPackageDescriptorTable.rowAtPoint(evt.getPoint());
            int column=jPackageDescriptorTable.columnAtPoint(evt.getPoint());
            jPackageDescriptorTable.changeSelection(row, column, false, false);
            jPackageDescriptorTable.requestFocus();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(jPackageDescriptorTable.getValueAt(row,column).toString());
		clipboard.setContents(strSel, null);
        }
    }//GEN-LAST:event_jPackageDescriptorTableMouseClicked

    private void jProjectTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProjectTableMouseClicked
        //Se è stato cliccato il tasto destro, copia la cella cliccata nella Clipboard
        if(SwingUtilities.isRightMouseButton(evt)){
            int row=jProjectTable.rowAtPoint(evt.getPoint());
            int column=jProjectTable.columnAtPoint(evt.getPoint());
            jProjectTable.changeSelection(row, column, false, false);
            jProjectTable.requestFocus();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(jProjectTable.getValueAt(row,column).toString());
		clipboard.setContents(strSel, null);
        }
    }//GEN-LAST:event_jProjectTableMouseClicked

    private void jAuthorTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jAuthorTableMouseClicked
        //Se è stato cliccato il tasto destro, copia la cella cliccata nella Clipboard
        if(SwingUtilities.isRightMouseButton(evt)){
            int row=jAuthorTable.rowAtPoint(evt.getPoint());
            int column=jAuthorTable.columnAtPoint(evt.getPoint());
            jAuthorTable.changeSelection(row, column, false, false);
            jAuthorTable.requestFocus();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(jAuthorTable.getValueAt(row,column).toString());
		clipboard.setContents(strSel, null);
        }
    }//GEN-LAST:event_jAuthorTableMouseClicked

    private void jProjectTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProjectTableMousePressed
        //Se fatto il doppio click,aprire scheda di approfondimento sul progetto
        if (evt.getButton() == evt.BUTTON1 && evt.getClickCount() == 2 && !isEditModeEnabled) {
                String name = (String) jProjectTable.getValueAt(jProjectTable.getSelectedRow(), 0);
                ProjectCard P=new ProjectCard(this,false);
                P.setLocationRelativeTo(null);
                P.setProjectName(name);
                P.showInformations(name);
                P.setVisible(true);
        }   
    }//GEN-LAST:event_jProjectTableMousePressed

    private void jPackageDescriptorTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPackageDescriptorTableMousePressed
        //Se fatto il doppio click,aprire scheda di approfondimento sul package
        if (evt.getButton() == evt.BUTTON1 && evt.getClickCount() == 2 && !isEditModeEnabled) {
                String code= jPackageDescriptorTable.getValueAt(jPackageDescriptorTable.getSelectedRow(),0).toString();
                String name = (String) jPackageDescriptorTable.getValueAt(jPackageDescriptorTable.getSelectedRow(), 1);
                PackageCard P=new PackageCard(this,false);
                P.setLocationRelativeTo(null);
                P.setPackageName(name);
                P.showInformations(code);
                P.setVisible(true);
        }  
    }//GEN-LAST:event_jPackageDescriptorTableMousePressed

    private void jClassDescriptorTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jClassDescriptorTableMousePressed
        //Se fatto il doppio click,aprire scheda di approfondimento sulla classe
        if (evt.getButton() == evt.BUTTON1 && evt.getClickCount() == 2 && !isEditModeEnabled) {
                String code= jClassDescriptorTable.getValueAt(jClassDescriptorTable.getSelectedRow(),0).toString();
                String name = (String) jClassDescriptorTable.getValueAt(jClassDescriptorTable.getSelectedRow(), 1);
                ClassCard C=new ClassCard(this,false);
                C.setLocationRelativeTo(null);
                C.setClassName(name);
                C.showInformations(code);
                C.setVisible(true);
        }  
    }//GEN-LAST:event_jClassDescriptorTableMousePressed

    private void jMethodDescriptorTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMethodDescriptorTableMousePressed
        //Se fatto il doppio click,aprire scheda di approfondimento sul metodo
        if (evt.getButton() == evt.BUTTON1 && evt.getClickCount() == 2 && !isEditModeEnabled) {
                String code= jMethodDescriptorTable.getValueAt(jMethodDescriptorTable.getSelectedRow(),0).toString();
                String name = (String) jMethodDescriptorTable.getValueAt(jMethodDescriptorTable.getSelectedRow(), 1);
                MethodCard M=new MethodCard(this,false);
                M.setLocationRelativeTo(null);
                M.setMethodName(name);
                M.showInformations(code);
                M.setVisible(true);
        }  
    }//GEN-LAST:event_jMethodDescriptorTableMousePressed

    private void jTestTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTestTableMousePressed
        //Se fatto il doppio click,aprire scheda di approfondimento sul test
        if (evt.getButton() == evt.BUTTON1 && evt.getClickCount() == 2 && !isEditModeEnabled) {
                String code= jTestTable.getValueAt(jTestTable.getSelectedRow(),0).toString();
                String name = (String) jTestTable.getValueAt(jTestTable.getSelectedRow(), 1);
                TestCard T=new TestCard(this,false);
                T.setLocationRelativeTo(null);
                T.setTestName(name);
                T.showInformations(code);
                T.setVisible(true);
        }  
    }//GEN-LAST:event_jTestTableMousePressed

    private void jCheckBoxPackageDescriptorProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPackageDescriptorProjectActionPerformed
        if(jCheckBoxPackageDescriptorProject.isSelected())
            jComboBoxPackageDescriptorProject.setEnabled(true);
        else
            jComboBoxPackageDescriptorProject.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxPackageDescriptorProjectActionPerformed

    private void jComboBoxChangelogChangedObjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxChangelogChangedObjectActionPerformed
        verifyChangelogCB();
    }//GEN-LAST:event_jComboBoxChangelogChangedObjectActionPerformed

    private void jCheckBoxChangelogProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxChangelogProjectActionPerformed
        if(jCheckBoxChangelogProject.isSelected())
            jComboBoxChangelogProject.setEnabled(true);
        else
            jComboBoxChangelogProject.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxChangelogProjectActionPerformed

    private void jMenuItemEnableDisableEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEnableDisableEditActionPerformed
        if(jMenuItemEnableDisableEdit.getText().equals("Abilita modifica")){
        isEditModeEnabled=true;
        JOptionPane.showMessageDialog(null,"Modalità modifica attivata.Ora "
                + "potrai modificare le righe delle query\nma non potrai "
                + "visualizzare le schede di approfondimento.\nDisattivare la modalità"
                + " per visualizzarle di nuovo",
                        "Informazione", JOptionPane.INFORMATION_MESSAGE);
        enableOrDisableEditing();
        jMenuItemEnableDisableEdit.setText("Disabilita modifica");
        }
        else{
            isEditModeEnabled=false;
            enableOrDisableEditing();
            jMenuItemEnableDisableEdit.setText("Abilita modifica");
        }
    }//GEN-LAST:event_jMenuItemEnableDisableEditActionPerformed

    private void jEditTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditTestButtonActionPerformed
        TestQueryExecutor te=new TestQueryExecutor();
        int flag=1;
        System.out.println(jTestTable.getRowCount());
        if(jTestTable.getRowCount()>0){
            String row[]=new String[jTestTable.getColumnCount()-1];//Escludiamo il codice
            for(int i=0;i<jTestTable.getRowCount()&&flag==1;i++){
                String code= jTestTable.getValueAt(i,0).toString();
                for(int j=1;j<jTestTable.getColumnCount();j++){
                    try{
                        row[j-1]=jTestTable.getValueAt(i,j).toString();
                    }
                    catch(NullPointerException ex){
                        row[j-1]="";
                    }
                }
                flag=te.updateTest(row, code);
            }
            if(flag==1){
                JOptionPane.showMessageDialog(null, "Tutti gli elementi sono stati aggiornati",
                        "Aggiornamento riuscito", JOptionPane.INFORMATION_MESSAGE);   
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi modificare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jEditTestButtonActionPerformed

    private void jDeleteTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteTestButtonActionPerformed
        TestQueryExecutor te=new TestQueryExecutor();
        int flag=1;
        if(jTestTable.getRowCount()>0){
            if (JOptionPane.showConfirmDialog(null, 
            "Questa operazione cancellerà tutti i test e gli elementi relativi a essi. Continuare?", "Eliminazione", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    for(int i=0;i<jTestTable.getRowCount()&&flag==1;i++){
                        String code= jTestTable.getValueAt(i,0).toString();
                        flag=te.deleteTest(code);
                    }
                    if(flag==1){
                        JOptionPane.showMessageDialog(null, "Elementi eliminati",
                        "Eliminazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        new TableManager().refreshTable((DefaultTableModel)jTestTable.getModel());
                    }   
                        
                }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi eliminare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jDeleteTestButtonActionPerformed

    private void jEditChangelogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditChangelogButtonActionPerformed
        ChangelogQueryExecutor cl=new ChangelogQueryExecutor();
        int flag=1;
        System.out.println(jChangelogTable.getRowCount());
        if(jChangelogTable.getRowCount()>0){
            String row[]=new String[jChangelogTable.getColumnCount()-1];//Escludiamo il codice
            for(int i=0;i<jChangelogTable.getRowCount()&&flag==1;i++){
                String code= jChangelogTable.getValueAt(i,0).toString();
                for(int j=1;j<jChangelogTable.getColumnCount();j++){
                    try{
                        row[j-1]=jChangelogTable.getValueAt(i,j).toString();
                    }
                    catch(NullPointerException ex){
                        row[j-1]="";
                    }
                }
                flag=cl.updateChangelog(row, code);
            }
            if(flag==1){
                JOptionPane.showMessageDialog(null, "Tutti gli elementi sono stati aggiornati",
                        "Aggiornamento riuscito", JOptionPane.INFORMATION_MESSAGE);   
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi modificare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jEditChangelogButtonActionPerformed

    private void jDeleteChangelogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteChangelogButtonActionPerformed
        ChangelogQueryExecutor cl=new ChangelogQueryExecutor();
        int flag=1;
        if(jChangelogTable.getRowCount()>0){
            if (JOptionPane.showConfirmDialog(null, 
            "Questa operazione cancellerà tutti i log e gli elementi relativi a essi. Continuare?", "Eliminazione", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    for(int i=0;i<jChangelogTable.getRowCount()&&flag==1;i++){
                        String code= jChangelogTable.getValueAt(i,0).toString();
                        flag=cl.deleteChangelog(code);
                    }
                    if(flag==1){
                        JOptionPane.showMessageDialog(null, "Elementi eliminati",
                        "Eliminazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        new TableManager().refreshTable((DefaultTableModel)jChangelogTable.getModel());
                    }  
                    
                        
                }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi eliminare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jDeleteChangelogButtonActionPerformed

    private void jEditMethodDescriptorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditMethodDescriptorButtonActionPerformed
        MethodQueryExecutor m=new MethodQueryExecutor();
        int flag=1;
        System.out.println(jMethodDescriptorTable.getRowCount());
        if(jMethodDescriptorTable.getRowCount()>0){
            String row[]=new String[jMethodDescriptorTable.getColumnCount()-1];//Escludiamo il codice
            for(int i=0;i<jMethodDescriptorTable.getRowCount()&&flag==1;i++){
                String code= jMethodDescriptorTable.getValueAt(i,0).toString();
                for(int j=1;j<jMethodDescriptorTable.getColumnCount();j++){
                    try{
                        row[j-1]=jMethodDescriptorTable.getValueAt(i,j).toString();
                    }
                    catch(NullPointerException ex){
                        row[j-1]="";
                    }
                }
                flag=m.updateMethodDescriptor(row, code);
            }
            if(flag==1){
                JOptionPane.showMessageDialog(null, "Tutti gli elementi sono stati aggiornati",
                        "Aggiornamento riuscito", JOptionPane.INFORMATION_MESSAGE);   
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi modificare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jEditMethodDescriptorButtonActionPerformed

    private void jDeleteMethodDescriptorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteMethodDescriptorButtonActionPerformed
        MethodQueryExecutor m=new MethodQueryExecutor();
        int flag=1;
        if(jMethodDescriptorTable.getRowCount()>0){
            if (JOptionPane.showConfirmDialog(null, 
            "Questa operazione cancellerà tutti i descrittori dei metodi e gli elementi relativi a essi. Continuare?", "Eliminazione", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    for(int i=0;i<jMethodDescriptorTable.getRowCount()&&flag==1;i++){
                        String code= jMethodDescriptorTable.getValueAt(i,0).toString();
                        flag=m.deleteMethodDescriptor(code);
                    }
                    if(flag==1){
                        JOptionPane.showMessageDialog(null, "Elementi eliminati",
                        "Eliminazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        new TableManager().refreshTable((DefaultTableModel)jMethodDescriptorTable.getModel());
                    }  
                 
                        
                }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi eliminare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jDeleteMethodDescriptorButtonActionPerformed

    private void jEditClassDescriptorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditClassDescriptorButtonActionPerformed
        ClassQueryExecutor c=new ClassQueryExecutor();
        int flag=1;
        System.out.println(jClassDescriptorTable.getRowCount());
        if(jClassDescriptorTable.getRowCount()>0){
            String row[]=new String[jClassDescriptorTable.getColumnCount()-1];//Escludiamo il codice
            for(int i=0;i<jClassDescriptorTable.getRowCount()&&flag==1;i++){
                String code= jClassDescriptorTable.getValueAt(i,0).toString();
                for(int j=1;j<jClassDescriptorTable.getColumnCount();j++){
                    try{
                        row[j-1]=jClassDescriptorTable.getValueAt(i,j).toString();
                    }
                    catch(NullPointerException ex){
                        row[j-1]="";
                    }
                }
                flag=c.updateClassDescriptor(row, code);
            }
            if(flag==1){
                JOptionPane.showMessageDialog(null, "Tutti gli elementi sono stati aggiornati",
                        "Aggiornamento riuscito", JOptionPane.INFORMATION_MESSAGE);   
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi modificare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jEditClassDescriptorButtonActionPerformed

    private void jDeleteClassDescriptorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteClassDescriptorButtonActionPerformed
        ClassQueryExecutor c=new ClassQueryExecutor();
        int flag=1;
        if(jClassDescriptorTable.getRowCount()>0){
            if (JOptionPane.showConfirmDialog(null, 
            "Questa operazione cancellerà tutti i descrittori delle classi e gli elementi relativi a essi. Continuare?", "Eliminazione", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    for(int i=0;i<jClassDescriptorTable.getRowCount()&&flag==1;i++){
                        String code= jClassDescriptorTable.getValueAt(i,0).toString();
                        flag=c.deleteClassDescriptor(code);
                    }
                    if(flag==1){
                        JOptionPane.showMessageDialog(null, "Elementi eliminati",
                        "Eliminazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        new TableManager().refreshTable((DefaultTableModel)jClassDescriptorTable.getModel());
                    } 
                    
                        
                }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi eliminare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jDeleteClassDescriptorButtonActionPerformed

    private void jEditPackageDescriptorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditPackageDescriptorButtonActionPerformed
        PackageQueryExecutor p=new PackageQueryExecutor();
        int flag=1;
        System.out.println(jPackageDescriptorTable.getRowCount());
        if(jPackageDescriptorTable.getRowCount()>0){
            String row[]=new String[jPackageDescriptorTable.getColumnCount()-1];//Escludiamo il codice
            for(int i=0;i<jPackageDescriptorTable.getRowCount()&&flag==1;i++){
                String code= jPackageDescriptorTable.getValueAt(i,0).toString();
                for(int j=1;j<jPackageDescriptorTable.getColumnCount();j++){
                    try{
                        row[j-1]=jPackageDescriptorTable.getValueAt(i,j).toString();
                    }
                    catch(NullPointerException ex){
                        row[j-1]="";
                    }
                }
                flag=p.updatePackageDescriptor(row, code);
            }
            if(flag==1){
                JOptionPane.showMessageDialog(null, "Tutti gli elementi sono stati aggiornati",
                        "Aggiornamento riuscito", JOptionPane.INFORMATION_MESSAGE);   
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi modificare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jEditPackageDescriptorButtonActionPerformed

    private void jDeletePackageDescriptorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeletePackageDescriptorButtonActionPerformed
        PackageQueryExecutor p=new PackageQueryExecutor();
        int flag=1;
        if(jPackageDescriptorTable.getRowCount()>0){
            if (JOptionPane.showConfirmDialog(null, 
            "Questa operazione cancellerà tutti i descrittori package e gli elementi relativi a essi. Continuare?", "Eliminazione", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    for(int i=0;i<jPackageDescriptorTable.getRowCount()&&flag==1;i++){
                        String code= jPackageDescriptorTable.getValueAt(i,0).toString();
                        flag=p.deletePackageDescriptor(code);
                    }
                    if(flag==1){
                        JOptionPane.showMessageDialog(null, "Elementi eliminati",
                        "Eliminazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        new TableManager().refreshTable((DefaultTableModel)jPackageDescriptorTable.getModel());
                    } 
                   
                        
                }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi eliminare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jDeletePackageDescriptorButtonActionPerformed

    private void jEditProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditProjectButtonActionPerformed
        ProjectQueryExecutor p=new ProjectQueryExecutor();
        int flag=1;
        System.out.println(jProjectTable.getRowCount());
        if(jProjectTable.getRowCount()>0){
            String row[]=new String[jProjectTable.getColumnCount()];
            for(int i=0;i<jProjectTable.getRowCount()&&flag==1;i++){
                String code= projectNames.get(i);
                for(int j=0;j<jProjectTable.getColumnCount();j++){
                    try{
                        row[j]=jProjectTable.getValueAt(i,j).toString();
                    }
                    catch(NullPointerException ex){
                        row[j]="";
                    }
                }
                flag=p.updateProject(row, code);
            }
            if(flag==1){
                JOptionPane.showMessageDialog(null, "Tutti gli elementi sono stati aggiornati",
                        "Aggiornamento riuscito", JOptionPane.INFORMATION_MESSAGE);   
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi modificare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jEditProjectButtonActionPerformed

    private void jDeleteProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteProjectButtonActionPerformed
        ProjectQueryExecutor p=new ProjectQueryExecutor();
        int flag=1;
        if(jProjectTable.getRowCount()>0){
            if (JOptionPane.showConfirmDialog(null, 
            "Questa operazione cancellerà tutti i progetti e gli elementi relativi a essi. Continuare?", "Eliminazione", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    for(int i=0;i<jProjectTable.getRowCount()&&flag==1;i++){
                        String code= jProjectTable.getValueAt(i,0).toString();
                        flag=p.deleteProject(code);
                    }
                    if(flag==1){
                        JOptionPane.showMessageDialog(null, "Elementi eliminati",
                        "Eliminazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        new TableManager().refreshTable((DefaultTableModel)jProjectTable.getModel());
                    } 
                    
                        
                }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi eliminare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jDeleteProjectButtonActionPerformed

    private void jEditAuthorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditAuthorButtonActionPerformed
        AuthorQueryExecutor a=new AuthorQueryExecutor();
        int flag=1;
        System.out.println(jAuthorTable.getRowCount());
        if(jAuthorTable.getRowCount()>0){
            String row[]=new String[jAuthorTable.getColumnCount()-1];//Escludiamo il codice
            for(int i=0;i<jAuthorTable.getRowCount()&&flag==1;i++){
                String code= jAuthorTable.getValueAt(i,0).toString();
                for(int j=1;j<jAuthorTable.getColumnCount();j++){
                    try{
                        row[j-1]=jAuthorTable.getValueAt(i,j).toString();
                    }
                    catch(NullPointerException ex){
                        row[j-1]="";
                    }
                }
                flag=a.updateAuthor(row, code);
            }
            if(flag==1){
                JOptionPane.showMessageDialog(null, "Tutti gli elementi sono stati aggiornati",
                        "Aggiornamento riuscito", JOptionPane.INFORMATION_MESSAGE);   
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi modificare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jEditAuthorButtonActionPerformed

    private void jDeleteAuthorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteAuthorButtonActionPerformed
        AuthorQueryExecutor a=new AuthorQueryExecutor();
        int flag=1;
        if(jAuthorTable.getRowCount()>0){
            if (JOptionPane.showConfirmDialog(null, 
            "Questa operazione cancellerà tutti gli autori e gli elementi relativi a essi. Continuare?", "Eliminazione", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    for(int i=0;i<jAuthorTable.getRowCount()&&flag==1;i++){
                        String code= jAuthorTable.getValueAt(i,0).toString();
                        flag=a.deleteAuthor(code);
                    }
                    if(flag==1){
                        JOptionPane.showMessageDialog(null, "Elementi eliminati",
                        "Eliminazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        new TableManager().refreshTable((DefaultTableModel)jAuthorTable.getModel());
                    } 
                   
                        
                }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi eliminare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jDeleteAuthorButtonActionPerformed

    private void jCheckBoxPackageCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPackageCodeActionPerformed
        if(jCheckBoxPackageCode.isSelected())
            jPackageCodeField.setEnabled(true);
        else
            jPackageCodeField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxPackageCodeActionPerformed

    private void jCheckBoxPackageCodReleaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPackageCodReleaseActionPerformed
        if(jCheckBoxPackageCodRelease.isSelected())
            jPackageCodReleaseField.setEnabled(true);
        else
            jPackageCodReleaseField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxPackageCodReleaseActionPerformed

    private void jCheckBoxPackageCodPackageDescriptorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPackageCodPackageDescriptorActionPerformed
        if(jCheckBoxPackageCodPackageDescriptor.isSelected())
            jPackageCodPackageDescriptorField.setEnabled(true);
        else
            jPackageCodPackageDescriptorField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxPackageCodPackageDescriptorActionPerformed

    private void jCheckBoxPackageStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPackageStatusActionPerformed
        if(jCheckBoxPackageStatus.isSelected())
            jComboBoxPackageStatus.setEnabled(true);
        else
            jComboBoxPackageStatus.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxPackageStatusActionPerformed

    private void jSearchPackageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchPackageButtonActionPerformed
       DefaultTableModel model= (DefaultTableModel)jPackageTable.getModel();
       PackageQueryExecutor p=new PackageQueryExecutor();
       p.searchPackage(this,model);
    }//GEN-LAST:event_jSearchPackageButtonActionPerformed

    private void jCheckBoxClassCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxClassCodeActionPerformed
       if(jCheckBoxClassCode.isSelected())
           jClassCodeField.setEnabled(true);
       else
           jClassCodeField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxClassCodeActionPerformed

    private void jCheckBoxClassCodPackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxClassCodPackageActionPerformed
       if(jCheckBoxClassCodPackage.isSelected())
           jClassCodPackageField.setEnabled(true);
       else
           jClassCodPackageField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxClassCodPackageActionPerformed

    private void jCheckBoxClassCodClassDescriptorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxClassCodClassDescriptorActionPerformed
       if(jCheckBoxClassCodClassDescriptor.isSelected())
           jClassCodClassDescriptorField.setEnabled(true);
       else
           jClassCodClassDescriptorField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxClassCodClassDescriptorActionPerformed

    private void jCheckBoxClassCodEnclosingClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxClassCodEnclosingClassActionPerformed
        if(jCheckBoxClassCodEnclosingClass.isSelected())
           jClassCodEnclosingClassField.setEnabled(true);
       else
           jClassCodEnclosingClassField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxClassCodEnclosingClassActionPerformed

    private void jCheckBoxClassStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxClassStatusActionPerformed
        if(jCheckBoxClassStatus.isSelected())
           jComboBoxClassStatus.setEnabled(true);
       else
           jComboBoxClassStatus.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxClassStatusActionPerformed

    private void jSearchClassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchClassButtonActionPerformed
       DefaultTableModel model= (DefaultTableModel)jClassTable.getModel();
       ClassQueryExecutor c=new ClassQueryExecutor();
       c.searchClass(this,model);
    }//GEN-LAST:event_jSearchClassButtonActionPerformed

    private void jCheckBoxMethodCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMethodCodeActionPerformed
        if(jCheckBoxMethodCode.isSelected())
            jMethodCodeField.setEnabled(true);
        else
            jMethodCodeField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxMethodCodeActionPerformed

    private void jCheckBoxMethodCodClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMethodCodClassActionPerformed
        if(jCheckBoxMethodCodClass.isSelected())
            jMethodCodClassField.setEnabled(true);
        else
            jMethodCodClassField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxMethodCodClassActionPerformed

    private void jCheckBoxMethodCodMethodDescriptorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMethodCodMethodDescriptorActionPerformed
        if(jCheckBoxMethodCodMethodDescriptor.isSelected())
            jMethodCodMethodDescriptorField.setEnabled(true);
        else
            jMethodCodMethodDescriptorField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxMethodCodMethodDescriptorActionPerformed

    private void jCheckBoxMethodStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMethodStatusActionPerformed
        if(jCheckBoxMethodStatus.isSelected())
            jComboBoxMethodStatus.setEnabled(true);
        else
            jComboBoxMethodStatus.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxMethodStatusActionPerformed

    private void jEditPackageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditPackageButtonActionPerformed
        PackageQueryExecutor p=new PackageQueryExecutor();
        int flag=1;
        System.out.println(jPackageTable.getRowCount());
        if(jPackageTable.getRowCount()>0){
            String row[]=new String[jPackageTable.getColumnCount()-1];//Escludiamo il codice
            for(int i=0;i<jPackageTable.getRowCount()&&flag==1;i++){
                String code= jPackageTable.getValueAt(i,0).toString();
                for(int j=1;j<jPackageTable.getColumnCount();j++){
                    try{
                        row[j-1]=jPackageTable.getValueAt(i,j).toString();
                    }
                    catch(NullPointerException ex){
                        row[j-1]="";
                    }
                }
                flag=p.updatePackage(row, code);
            }
            if(flag==1){
                JOptionPane.showMessageDialog(null, "Tutti gli elementi sono stati aggiornati",
                        "Aggiornamento riuscito", JOptionPane.INFORMATION_MESSAGE);   
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi modificare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jEditPackageButtonActionPerformed

    private void jDeletePackageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeletePackageButtonActionPerformed
        PackageQueryExecutor p=new PackageQueryExecutor();
        int flag=1;
        if(jPackageTable.getRowCount()>0){
            if (JOptionPane.showConfirmDialog(null, 
            "Questa operazione cancellerà tutti i packages e gli elementi relativi a essi. Continuare?", "Eliminazione", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    for(int i=0;i<jPackageTable.getRowCount()&&flag==1;i++){
                        String code= jPackageTable.getValueAt(i,0).toString();
                        flag=p.deletePackage(code);
                    }
                    if(flag==1){
                        JOptionPane.showMessageDialog(null, "Elementi eliminati",
                        "Eliminazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        new TableManager().refreshTable((DefaultTableModel)jPackageTable.getModel());
                    } 
                    
                        
                }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi eliminare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jDeletePackageButtonActionPerformed

    private void jEditMethodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditMethodButtonActionPerformed
        MethodQueryExecutor m=new MethodQueryExecutor();
        int flag=1;
        System.out.println(jMethodTable.getRowCount());
        if(jMethodTable.getRowCount()>0){
            String row[]=new String[jMethodTable.getColumnCount()-1];//Escludiamo il codice
            for(int i=0;i<jMethodTable.getRowCount()&&flag==1;i++){
                String code= jMethodTable.getValueAt(i,0).toString();
                for(int j=1;j<jMethodTable.getColumnCount();j++){
                    try{
                        row[j-1]=jMethodTable.getValueAt(i,j).toString();
                    }
                    catch(NullPointerException ex){
                        row[j-1]="";
                    }
                }
                flag=m.updateMethod(row, code);
            }
            if(flag==1){
                JOptionPane.showMessageDialog(null, "Tutti gli elementi sono stati aggiornati",
                        "Aggiornamento riuscito", JOptionPane.INFORMATION_MESSAGE);   
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi modificare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jEditMethodButtonActionPerformed

    private void jDeleteMethodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteMethodButtonActionPerformed
        MethodQueryExecutor m=new MethodQueryExecutor();
        int flag=1;
        if(jMethodTable.getRowCount()>0){
            if (JOptionPane.showConfirmDialog(null, 
            "Questa operazione cancellerà tutti i metodi e gli elementi relativi a essi. Continuare?", "Eliminazione", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    for(int i=0;i<jMethodTable.getRowCount()&&flag==1;i++){
                        String code= jMethodTable.getValueAt(i,0).toString();
                        flag=m.deleteMethod(code);
                    }
                    if(flag==1){
                        JOptionPane.showMessageDialog(null, "Elementi eliminati",
                        "Eliminazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        new TableManager().refreshTable((DefaultTableModel)jMethodTable.getModel());
                    } 
                        
                }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi eliminare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jDeleteMethodButtonActionPerformed

    private void jEditClassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditClassButtonActionPerformed
        ClassQueryExecutor c=new ClassQueryExecutor();
        int flag=1;
        System.out.println(jClassTable.getRowCount());
        if(jClassTable.getRowCount()>0){
            String row[]=new String[jClassTable.getColumnCount()-1];//Escludiamo il codice
            for(int i=0;i<jClassTable.getRowCount()&&flag==1;i++){
                String code= jClassTable.getValueAt(i,0).toString();
                for(int j=1;j<jClassTable.getColumnCount();j++){
                    try{
                        row[j-1]=jClassTable.getValueAt(i,j).toString();
                    }
                    catch(NullPointerException ex){
                        row[j-1]="";
                    }
                }
                flag=c.updateClass(row, code);
            }
            if(flag==1){
                JOptionPane.showMessageDialog(null, "Tutti gli elementi sono stati aggiornati",
                        "Aggiornamento riuscito", JOptionPane.INFORMATION_MESSAGE);   
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi modificare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jEditClassButtonActionPerformed

    private void jDeleteClassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteClassButtonActionPerformed
        ClassQueryExecutor c=new ClassQueryExecutor();
        int flag=1;
        if(jClassTable.getRowCount()>0){
            if (JOptionPane.showConfirmDialog(null, 
            "Questa operazione cancellerà tutti le classi e gli elementi relativi a essi. Continuare?", "Eliminazione", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    for(int i=0;i<jClassTable.getRowCount()&&flag==1;i++){
                        String code= jClassTable.getValueAt(i,0).toString();
                        flag=c.deleteClass(code);
                    }
                    if(flag==1){
                        JOptionPane.showMessageDialog(null, "Elementi eliminati",
                        "Eliminazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        new TableManager().refreshTable((DefaultTableModel)jClassTable.getModel());
                    } 
                        
                }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi eliminare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jDeleteClassButtonActionPerformed

    private void jSearchMethodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchMethodButtonActionPerformed
       DefaultTableModel model= (DefaultTableModel)jMethodTable.getModel();
       MethodQueryExecutor m=new MethodQueryExecutor();
       m.searchMethod(this,model);
    }//GEN-LAST:event_jSearchMethodButtonActionPerformed

    private void jCheckBoxReleaseCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxReleaseCodeActionPerformed
        if(jCheckBoxReleaseCode.isSelected())
            jReleaseCodeField.setEnabled(true);
        else
            jReleaseCodeField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxReleaseCodeActionPerformed

    private void jCheckBoxReleaseVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxReleaseVersionActionPerformed
        if(jCheckBoxReleaseVersion.isSelected())
            jReleaseVersionField.setEnabled(true);
        else
            jReleaseVersionField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxReleaseVersionActionPerformed

    private void jCheckBoxReleasePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxReleasePathActionPerformed
       if(jCheckBoxReleasePath.isSelected())
            jReleasePathField.setEnabled(true);
        else
            jReleasePathField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxReleasePathActionPerformed

    private void jCheckBoxReleaseDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxReleaseDateActionPerformed
        if(jCheckBoxReleaseDate.isSelected())
            jReleaseDateField.setEnabled(true);
        else
            jReleaseDateField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxReleaseDateActionPerformed

    private void jCheckBoxReleaseProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxReleaseProjectActionPerformed
       if(jCheckBoxReleaseProject.isSelected())
            jComboBoxReleaseProject.setEnabled(true);
        else
            jComboBoxReleaseProject.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxReleaseProjectActionPerformed

    private void jSearchReleaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchReleaseButtonActionPerformed
       DefaultTableModel model= (DefaultTableModel)jReleaseTable.getModel();
       ProjectQueryExecutor p=new ProjectQueryExecutor();
       p.searchRelease(this,model);
    }//GEN-LAST:event_jSearchReleaseButtonActionPerformed

    private void jEditReleaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditReleaseButtonActionPerformed
        ProjectQueryExecutor p=new ProjectQueryExecutor();
        int flag=1;
        System.out.println(jReleaseTable.getRowCount());
        if(jReleaseTable.getRowCount()>0){
            String row[]=new String[jReleaseTable.getColumnCount()-1];//Escludiamo il codice
            for(int i=0;i<jReleaseTable.getRowCount()&&flag==1;i++){
                String code= jReleaseTable.getValueAt(i,0).toString();
                for(int j=1;j<jReleaseTable.getColumnCount();j++){
                    try{
                        row[j-1]=jReleaseTable.getValueAt(i,j).toString();
                    }
                    catch(NullPointerException ex){
                        row[j-1]="";
                    }
                }
                flag=p.updateRelease(row, code);
            }
            if(flag==1){
                JOptionPane.showMessageDialog(null, "Tutti gli elementi sono stati aggiornati",
                        "Aggiornamento riuscito", JOptionPane.INFORMATION_MESSAGE);   
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi modificare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jEditReleaseButtonActionPerformed

    private void jDeleteReleaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteReleaseButtonActionPerformed
        ProjectQueryExecutor p=new ProjectQueryExecutor();
        int flag=1;
        if(jReleaseTable.getRowCount()>0){
            if (JOptionPane.showConfirmDialog(null, 
            "Questa operazione cancellerà tutti le release e gli elementi relativi a essi. Continuare?", "Eliminazione", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    for(int i=0;i<jReleaseTable.getRowCount()&&flag==1;i++){
                        String code= jReleaseTable.getValueAt(i,0).toString();
                        flag=p.deleteRelease(code);
                    }
                    if(flag==1){
                        JOptionPane.showMessageDialog(null, "Elementi eliminati",
                        "Eliminazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        new TableManager().refreshTable((DefaultTableModel)jReleaseTable.getModel());
                    } 
                        
                }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi eliminare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jDeleteReleaseButtonActionPerformed

    private void jReleaseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jReleaseTableMouseClicked
        //Se è stato cliccato il tasto destro, copia la cella cliccata nella Clipboard
        if(SwingUtilities.isRightMouseButton(evt)){
            int row=jReleaseTable.rowAtPoint(evt.getPoint());
            int column=jReleaseTable.columnAtPoint(evt.getPoint());
            jReleaseTable.changeSelection(row, column, false, false);
            jReleaseTable.requestFocus();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(jReleaseTable.getValueAt(row,column).toString());
		clipboard.setContents(strSel, null);
        }
    }//GEN-LAST:event_jReleaseTableMouseClicked

    private void jClassTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jClassTableMouseClicked
        //Se è stato cliccato il tasto destro, copia la cella cliccata nella Clipboard
        if(SwingUtilities.isRightMouseButton(evt)){
            int row=jClassTable.rowAtPoint(evt.getPoint());
            int column=jClassTable.columnAtPoint(evt.getPoint());
            jClassTable.changeSelection(row, column, false, false);
            jClassTable.requestFocus();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(jClassTable.getValueAt(row,column).toString());
		clipboard.setContents(strSel, null);
        }
    }//GEN-LAST:event_jClassTableMouseClicked

    private void jPackageTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPackageTableMouseClicked
        //Se è stato cliccato il tasto destro, copia la cella cliccata nella Clipboard
        if(SwingUtilities.isRightMouseButton(evt)){
            int row=jPackageTable.rowAtPoint(evt.getPoint());
            int column=jPackageTable.columnAtPoint(evt.getPoint());
            jPackageTable.changeSelection(row, column, false, false);
            jPackageTable.requestFocus();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(jPackageTable.getValueAt(row,column).toString());
		clipboard.setContents(strSel, null);
        }
    }//GEN-LAST:event_jPackageTableMouseClicked

    private void jMethodTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMethodTableMouseClicked
        //Se è stato cliccato il tasto destro, copia la cella cliccata nella Clipboard
        if(SwingUtilities.isRightMouseButton(evt)){
            int row=jMethodTable.rowAtPoint(evt.getPoint());
            int column=jMethodTable.columnAtPoint(evt.getPoint());
            jMethodTable.changeSelection(row, column, false, false);
            jMethodTable.requestFocus();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(jMethodTable.getValueAt(row,column).toString());
		clipboard.setContents(strSel, null);
        }
    }//GEN-LAST:event_jMethodTableMouseClicked

    private void jCheckBoxExecutionCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxExecutionCodeActionPerformed
        if(jCheckBoxExecutionCode.isSelected())
            jExecutionCodeField.setEnabled(true);
        else
            jExecutionCodeField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxExecutionCodeActionPerformed

    private void jCheckBoxExecutionCodTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxExecutionCodTestActionPerformed
        if(jCheckBoxExecutionCodTest.isSelected())
            jExecutionCodTestField.setEnabled(true);
        else
            jExecutionCodTestField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxExecutionCodTestActionPerformed

    private void jCheckBoxExecutionCodReleaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxExecutionCodReleaseActionPerformed
        if(jCheckBoxExecutionCodRelease.isSelected())
            jExecutionCodReleaseField.setEnabled(true);
        else
            jExecutionCodReleaseField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxExecutionCodReleaseActionPerformed

    private void jCheckBoxExecutionDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxExecutionDateActionPerformed
        if(jCheckBoxExecutionDate.isSelected())
            jExecutionDateField.setEnabled(true);
        else
            jExecutionDateField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxExecutionDateActionPerformed

    private void jCheckBoxExecutionResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxExecutionResultActionPerformed
        if(jCheckBoxExecutionResult.isSelected())
            jComboBoxExecutionResult.setEnabled(true);
        else
            jComboBoxExecutionResult.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxExecutionResultActionPerformed

    private void jSearchExecutionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchExecutionButtonActionPerformed
       DefaultTableModel model= (DefaultTableModel)jExecutionTable.getModel();
       TestQueryExecutor te=new TestQueryExecutor();
       te.searchExecution(this,model);
    }//GEN-LAST:event_jSearchExecutionButtonActionPerformed

    private void jExecutionTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jExecutionTableMouseClicked
        //Se è stato cliccato il tasto destro, copia la cella cliccata nella Clipboard
        if(SwingUtilities.isRightMouseButton(evt)){
            int row=jExecutionTable.rowAtPoint(evt.getPoint());
            int column=jExecutionTable.columnAtPoint(evt.getPoint());
            jExecutionTable.changeSelection(row, column, false, false);
            jExecutionTable.requestFocus();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(jExecutionTable.getValueAt(row,column).toString());
		clipboard.setContents(strSel, null);
        }
    }//GEN-LAST:event_jExecutionTableMouseClicked

    private void jEditExecutionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditExecutionButtonActionPerformed
        TestQueryExecutor e=new TestQueryExecutor();
        int flag=1;
        System.out.println(jExecutionTable.getRowCount());
        if(jExecutionTable.getRowCount()>0){
            String row[]=new String[jExecutionTable.getColumnCount()-1];//Escludiamo il codice
            for(int i=0;i<jExecutionTable.getRowCount()&&flag==1;i++){
                String code= jExecutionTable.getValueAt(i,0).toString();
                for(int j=1;j<jExecutionTable.getColumnCount();j++){
                    try{
                        row[j-1]=jExecutionTable.getValueAt(i,j).toString();
                    }
                    catch(NullPointerException ex){
                        row[j-1]="";
                    }
                }
                flag=e.updateExecution(row, code);
            }
            if(flag==1){
                JOptionPane.showMessageDialog(null, "Tutti gli elementi sono stati aggiornati",
                        "Aggiornamento riuscito", JOptionPane.INFORMATION_MESSAGE);   
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi modificare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jEditExecutionButtonActionPerformed

    private void jDeleteExecutionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteExecutionButtonActionPerformed
        TestQueryExecutor e=new TestQueryExecutor();
        int flag=1;
        if(jExecutionTable.getRowCount()>0){
            if (JOptionPane.showConfirmDialog(null, 
            "Questa operazione cancellerà tutte le esecuzioni e gli elementi relativi a essi. Continuare?", "Eliminazione", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    for(int i=0;i<jExecutionTable.getRowCount()&&flag==1;i++){
                        String code= jExecutionTable.getValueAt(i,0).toString();
                        flag=e.deleteExecution(code);
                    }
                    if(flag==1){
                        JOptionPane.showMessageDialog(null, "Elementi eliminati",
                        "Eliminazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        new TableManager().refreshTable((DefaultTableModel)jExecutionTable.getModel());
                    } 
                        
                }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi eliminare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jDeleteExecutionButtonActionPerformed

    private void jTestsRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTestsRadioButtonActionPerformed
        if(jTestsRadioButton.isSelected()){
            DefaultTableModel model= (DefaultTableModel)jOtherTable.getModel();
            new TableManager().refreshTable(model);
            firstFieldLabel.setText("Codice Test");
            jCheckBoxOtherCode.setText("Test");
            jAuthorsRadioButton.setSelected(false);
            jComboBoxAuthors.setEnabled(false);
            jComboBoxTests.setEnabled(true);
            if(jComboBoxTests.getSelectedItem().toString().contains("Classi")){
                secondFieldLabel.setText("Codice Desc. Classe");
                jCheckBoxOtherGeneral.setText("Desc. Classe");
            }
            else{
                secondFieldLabel.setText("Codice Desc. Metodo");
                jCheckBoxOtherGeneral.setText("Desc. Metodo");
            }
                
        }
    }//GEN-LAST:event_jTestsRadioButtonActionPerformed

    private void jComboBoxTestsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTestsActionPerformed
        DefaultTableModel model= (DefaultTableModel)jOtherTable.getModel();
        new TableManager().refreshTable(model);
        if(jComboBoxTests.getSelectedItem().toString().contains("Classi")){
                secondFieldLabel.setText("Codice Desc. Classe");
                jCheckBoxOtherGeneral.setText("Desc. Classe");
            }
            else{
                secondFieldLabel.setText("Codice Desc. Metodo");
                jCheckBoxOtherGeneral.setText("Desc. Metodo");
        }
    }//GEN-LAST:event_jComboBoxTestsActionPerformed

    private void jAuthorsRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAuthorsRadioButtonActionPerformed
        if(jAuthorsRadioButton.isSelected()){
            DefaultTableModel model= (DefaultTableModel)jOtherTable.getModel();
            new TableManager().refreshTable(model);
            firstFieldLabel.setText("Codice Autore");
            jCheckBoxOtherCode.setText("Autore");
            jTestsRadioButton.setSelected(false);
            jComboBoxTests.setEnabled(false);
            jComboBoxAuthors.setEnabled(true);
            if(jComboBoxAuthors.getSelectedItem().toString().contains("Progetti")){
                secondFieldLabel.setText("Nome Progetto");
                jCheckBoxOtherGeneral.setText("Progetto");
            }
            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Classi")){
                secondFieldLabel.setText("Codice Desc. Classe");
                jCheckBoxOtherGeneral.setText("Desc. Classe");
            }
            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Metodi")){
                secondFieldLabel.setText("Codice Desc. Metodo");
                jCheckBoxOtherGeneral.setText("Desc. Metodo");
            }
            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Test")){
                secondFieldLabel.setText("Codice Test");
                jCheckBoxOtherGeneral.setText("Test");
            }
            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Logs")){
                secondFieldLabel.setText("Codice Log");
                jCheckBoxOtherGeneral.setText("Changelog");
            }
                
        }
    }//GEN-LAST:event_jAuthorsRadioButtonActionPerformed

    private void jComboBoxAuthorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAuthorsActionPerformed
            DefaultTableModel model= (DefaultTableModel)jOtherTable.getModel();
            new TableManager().refreshTable(model);
            if(jComboBoxAuthors.getSelectedItem().toString().contains("Progetti")){
                secondFieldLabel.setText("Nome Progetto");
                jCheckBoxOtherGeneral.setText("Progetto");
            }
            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Classi")){
                secondFieldLabel.setText("Codice Desc. Classe");
                jCheckBoxOtherGeneral.setText("Desc. Classe");
            }
            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Metodi")){
                secondFieldLabel.setText("Codice Desc. Metodo");
                jCheckBoxOtherGeneral.setText("Desc. Metodo");
            }
            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Test")){
                secondFieldLabel.setText("Codice Test");
                jCheckBoxOtherGeneral.setText("Test");
            }
            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Logs")){
                secondFieldLabel.setText("Codice Log");
                jCheckBoxOtherGeneral.setText("Changelog");
            }
                
    }//GEN-LAST:event_jComboBoxAuthorsActionPerformed

    private void jOtherTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jOtherTableMouseClicked
        //Se è stato cliccato il tasto destro, copia la cella cliccata nella Clipboard
        if(SwingUtilities.isRightMouseButton(evt)){
            int row=jOtherTable.rowAtPoint(evt.getPoint());
            int column=jOtherTable.columnAtPoint(evt.getPoint());
            jOtherTable.changeSelection(row, column, false, false);
            jOtherTable.requestFocus();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(jOtherTable.getValueAt(row,column).toString());
		clipboard.setContents(strSel, null);
        }
    }//GEN-LAST:event_jOtherTableMouseClicked

    private void jCheckBoxOtherCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxOtherCodeActionPerformed
        if(jCheckBoxOtherCode.isSelected())
            jOtherCodeField.setEnabled(true);
        else
            jOtherCodeField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxOtherCodeActionPerformed

    private void jCheckBoxOtherGeneralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxOtherGeneralActionPerformed
        if(jCheckBoxOtherGeneral.isSelected())
            jOtherGeneralField.setEnabled(true);
        else
            jOtherGeneralField.setEnabled(false);
    }//GEN-LAST:event_jCheckBoxOtherGeneralActionPerformed

    private void jSearchOtherButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchOtherButtonActionPerformed
       DefaultTableModel model= (DefaultTableModel)jOtherTable.getModel();
       if(jAuthorsRadioButton.isSelected()){
            if(jComboBoxAuthors.getSelectedItem().toString().contains("Progetti")){
                new ProjectQueryExecutor().searchAssignment(this, model);
            }
            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Classi")){
                new ClassQueryExecutor().searchAssignment(this,model);
            }
            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Metodi")){
                new MethodQueryExecutor().searchAssignment(this, model);
            }
            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Test")){
                new TestQueryExecutor().searchAssignment(this, model);
            }
            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Logs")){
                new ChangelogQueryExecutor().searchAssignment(this,model);
            }
       }
       else{
           if(jComboBoxTests.getSelectedItem().toString().contains("Classi")){
                new TestQueryExecutor().searchClassAssignment(this, model);
            }
            else{
                new TestQueryExecutor().searchMethodAssignment(this, model);
            }
       }
    }//GEN-LAST:event_jSearchOtherButtonActionPerformed

    private void jDeleteOtherButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteOtherButtonActionPerformed
       int flag=1;       
       if(jOtherTable.getRowCount()>0){
            if (JOptionPane.showConfirmDialog(null, 
            "Questa operazione cancellerà tutti gli elementi della ricerca. Continuare?", "Eliminazione", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                        
                    for(int i=0;i<jOtherTable.getRowCount()&&flag==1;i++){
                        String code1= jOtherTable.getValueAt(i,0).toString();
                        String code2=jOtherTable.getValueAt(i,1).toString();
                        if(jAuthorsRadioButton.isSelected()){
                            if(jComboBoxAuthors.getSelectedItem().toString().contains("Progetti")){
                                flag=new ProjectQueryExecutor().deleteAssignment(code1,code2);
                            }
                            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Classi")){
                                flag=new ClassQueryExecutor().deleteAssignment(code1,code2);
                            }
                            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Metodi")){
                                flag=new MethodQueryExecutor().deleteAssignment(code1,code2);
                            }
                            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Test")){
                                flag=new TestQueryExecutor().deleteAssignment(code1,code2);
                            }
                            else if(jComboBoxAuthors.getSelectedItem().toString().contains("Logs")){
                                flag=new ChangelogQueryExecutor().deleteAssignment(code1,code2);
                            }
                        }
                        else{
                            if(jComboBoxTests.getSelectedItem().toString().contains("Classi")){
                                 flag=new TestQueryExecutor().deleteClassAssignment(code1,code2);
                             }
                             else{
                                flag=new TestQueryExecutor().deleteMethodAssignment(code1,code2);
                             }
                        }
                    }
                    if(flag==1){
                        JOptionPane.showMessageDialog(null, "Elementi eliminati",
                        "Eliminazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        new TableManager().refreshTable((DefaultTableModel)jTestTable.getModel());
                    } 
                        
                }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Cerca prima cosa vuoi eliminare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jDeleteOtherButtonActionPerformed

    private void jMenuItemRefreshAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRefreshAllActionPerformed
        comboBuilder.buildComboBox(jComboBoxPackageDescriptorProject, projectBuilder.projectsListQueryBuilder(), false);
        comboBuilder.buildComboBox(jComboBoxReleaseProject, projectBuilder.projectsListQueryBuilder(), false);
        comboBuilder.buildComboBox(jComboBoxChangelogProject, projectBuilder.projectsListQueryBuilder(), false);
        comboBuilder.buildComboBox(jComboBoxTestProject, projectBuilder.projectsListQueryBuilder(), false);
    }//GEN-LAST:event_jMenuItemRefreshAllActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Label firstFieldLabel;
    private javax.swing.JPanel jAuthorButtons;
    private javax.swing.JTextField jAuthorCodeField;
    private javax.swing.JTextField jAuthorEmailField;
    private javax.swing.JTextField jAuthorNameField;
    private javax.swing.JPanel jAuthorSearchByFiltersPanel;
    private javax.swing.JPanel jAuthorSearchFieldsPanel;
    private javax.swing.JPanel jAuthorSearchFiltersPanel;
    private javax.swing.JPanel jAuthorSearchPanel;
    private javax.swing.JPanel jAuthorSearchResultsPanel;
    private javax.swing.JTextField jAuthorSurnameField;
    private javax.swing.JTable jAuthorTable;
    private javax.swing.JRadioButton jAuthorsRadioButton;
    private javax.swing.JPanel jChangelogButtons;
    private com.toedter.calendar.JDateChooser jChangelogClosingDateField;
    private javax.swing.JTextField jChangelogCodClassField;
    private javax.swing.JTextField jChangelogCodMethodField;
    private javax.swing.JTextField jChangelogCodPackageField;
    private javax.swing.JTextField jChangelogCodeField;
    private javax.swing.JTextField jChangelogDescriptionField;
    private com.toedter.calendar.JDateChooser jChangelogOpeningDateField;
    private javax.swing.JPanel jChangelogSearchByFiltersPanel;
    private javax.swing.JPanel jChangelogSearchFieldsPanel;
    private javax.swing.JPanel jChangelogSearchFiltersPanel;
    private javax.swing.JPanel jChangelogSearchPanel;
    private javax.swing.JPanel jChangelogSearchResultsPanel;
    private javax.swing.JTable jChangelogTable;
    private javax.swing.JCheckBox jCheckBoxAuthorCode;
    private javax.swing.JCheckBox jCheckBoxAuthorEmail;
    private javax.swing.JCheckBox jCheckBoxAuthorName;
    private javax.swing.JCheckBox jCheckBoxAuthorSurname;
    private javax.swing.JCheckBox jCheckBoxChangelogChangeType;
    private javax.swing.JCheckBox jCheckBoxChangelogChangedObject;
    private javax.swing.JCheckBox jCheckBoxChangelogClosingDate;
    private javax.swing.JCheckBox jCheckBoxChangelogCode;
    private javax.swing.JCheckBox jCheckBoxChangelogDescription;
    private javax.swing.JCheckBox jCheckBoxChangelogOpeningDate;
    private javax.swing.JCheckBox jCheckBoxChangelogProject;
    private javax.swing.JCheckBox jCheckBoxClassCodClassDescriptor;
    private javax.swing.JCheckBox jCheckBoxClassCodEnclosingClass;
    private javax.swing.JCheckBox jCheckBoxClassCodPackage;
    private javax.swing.JCheckBox jCheckBoxClassCode;
    private javax.swing.JCheckBox jCheckBoxClassDescriptorCodPackage;
    private javax.swing.JCheckBox jCheckBoxClassDescriptorCodSuper;
    private javax.swing.JCheckBox jCheckBoxClassDescriptorCode;
    private javax.swing.JCheckBox jCheckBoxClassDescriptorModifier;
    private javax.swing.JCheckBox jCheckBoxClassDescriptorName;
    private javax.swing.JCheckBox jCheckBoxClassDescriptorScope;
    private javax.swing.JCheckBox jCheckBoxClassDescriptorSource;
    private javax.swing.JCheckBox jCheckBoxClassStatus;
    private javax.swing.JCheckBox jCheckBoxExecutionCodRelease;
    private javax.swing.JCheckBox jCheckBoxExecutionCodTest;
    private javax.swing.JCheckBox jCheckBoxExecutionCode;
    private javax.swing.JCheckBox jCheckBoxExecutionDate;
    private javax.swing.JCheckBox jCheckBoxExecutionResult;
    private javax.swing.JCheckBox jCheckBoxMethodCodClass;
    private javax.swing.JCheckBox jCheckBoxMethodCodMethodDescriptor;
    private javax.swing.JCheckBox jCheckBoxMethodCode;
    private javax.swing.JCheckBox jCheckBoxMethodDescriptorCode;
    private javax.swing.JCheckBox jCheckBoxMethodDescriptorInputParameters;
    private javax.swing.JCheckBox jCheckBoxMethodDescriptorModifier;
    private javax.swing.JCheckBox jCheckBoxMethodDescriptorName;
    private javax.swing.JCheckBox jCheckBoxMethodDescriptorReturn;
    private javax.swing.JCheckBox jCheckBoxMethodDescriptorScope;
    private javax.swing.JCheckBox jCheckBoxMethodStatus;
    private javax.swing.JCheckBox jCheckBoxOtherCode;
    private javax.swing.JCheckBox jCheckBoxOtherGeneral;
    private javax.swing.JCheckBox jCheckBoxPackageCodPackageDescriptor;
    private javax.swing.JCheckBox jCheckBoxPackageCodRelease;
    private javax.swing.JCheckBox jCheckBoxPackageCode;
    private javax.swing.JCheckBox jCheckBoxPackageDescriptorCode;
    private javax.swing.JCheckBox jCheckBoxPackageDescriptorEncloserPack;
    private javax.swing.JCheckBox jCheckBoxPackageDescriptorName;
    private javax.swing.JCheckBox jCheckBoxPackageDescriptorProject;
    private javax.swing.JCheckBox jCheckBoxPackageStatus;
    private javax.swing.JCheckBox jCheckBoxProjectDateCancellation;
    private javax.swing.JCheckBox jCheckBoxProjectDateCreation;
    private javax.swing.JCheckBox jCheckBoxProjectName;
    private javax.swing.JCheckBox jCheckBoxProjectPath;
    private javax.swing.JCheckBox jCheckBoxReleaseCode;
    private javax.swing.JCheckBox jCheckBoxReleaseDate;
    private javax.swing.JCheckBox jCheckBoxReleasePath;
    private javax.swing.JCheckBox jCheckBoxReleaseProject;
    private javax.swing.JCheckBox jCheckBoxReleaseVersion;
    private javax.swing.JCheckBox jCheckBoxTestCode;
    private javax.swing.JCheckBox jCheckBoxTestName;
    private javax.swing.JCheckBox jCheckBoxTestPath;
    private javax.swing.JCheckBox jCheckBoxTestProject;
    private javax.swing.JPanel jClassButtons;
    private javax.swing.JTextField jClassCodClassDescriptorField;
    private javax.swing.JTextField jClassCodEnclosingClassField;
    private javax.swing.JTextField jClassCodPackageField;
    private javax.swing.JTextField jClassCodeField;
    private javax.swing.JPanel jClassDescriptorButtons;
    private javax.swing.JTextField jClassDescriptorCodPackageField;
    private javax.swing.JTextField jClassDescriptorCodSuperField;
    private javax.swing.JTextField jClassDescriptorCodeField;
    private javax.swing.JTextField jClassDescriptorNameField;
    private javax.swing.JPanel jClassDescriptorSearchByFiltersPanel;
    private javax.swing.JPanel jClassDescriptorSearchFieldsPanel;
    private javax.swing.JPanel jClassDescriptorSearchFiltersPanel;
    private javax.swing.JPanel jClassDescriptorSearchPanel;
    private javax.swing.JPanel jClassDescriptorSearchResultsPanel;
    private javax.swing.JTextField jClassDescriptorSourceField;
    private javax.swing.JTable jClassDescriptorTable;
    private javax.swing.JPanel jClassResultsPanel;
    private javax.swing.JPanel jClassSearchByFiltersPanel;
    private javax.swing.JPanel jClassSearchFieldsPanel;
    private javax.swing.JPanel jClassSearchFiltersPanel;
    private javax.swing.JPanel jClassSearchPanel;
    private javax.swing.JTable jClassTable;
    private javax.swing.JComboBox<String> jComboBoxAuthors;
    private javax.swing.JComboBox<String> jComboBoxChangelogChangeType;
    private javax.swing.JComboBox<String> jComboBoxChangelogChangedObject;
    private javax.swing.JComboBox<String> jComboBoxChangelogProject;
    private javax.swing.JComboBox<String> jComboBoxClassDescriptorModifier;
    private javax.swing.JComboBox<String> jComboBoxClassDescriptorScope;
    private javax.swing.JComboBox<String> jComboBoxClassStatus;
    private javax.swing.JComboBox<String> jComboBoxExecutionResult;
    private javax.swing.JComboBox<String> jComboBoxMethodDescriptorModifier;
    private javax.swing.JComboBox<String> jComboBoxMethodDescriptorScope;
    private javax.swing.JComboBox<String> jComboBoxMethodStatus;
    private javax.swing.JComboBox<String> jComboBoxPackageDescriptorProject;
    private javax.swing.JComboBox<String> jComboBoxPackageStatus;
    private javax.swing.JComboBox<String> jComboBoxReleaseProject;
    private javax.swing.JComboBox<String> jComboBoxTestProject;
    private javax.swing.JComboBox<String> jComboBoxTests;
    private javax.swing.JButton jDeleteAuthorButton;
    private javax.swing.JButton jDeleteChangelogButton;
    private javax.swing.JButton jDeleteClassButton;
    private javax.swing.JButton jDeleteClassDescriptorButton;
    private javax.swing.JButton jDeleteExecutionButton;
    private javax.swing.JButton jDeleteMethodButton;
    private javax.swing.JButton jDeleteMethodDescriptorButton;
    private javax.swing.JButton jDeleteOtherButton;
    private javax.swing.JButton jDeletePackageButton;
    private javax.swing.JButton jDeletePackageDescriptorButton;
    private javax.swing.JButton jDeleteProjectButton;
    private javax.swing.JButton jDeleteReleaseButton;
    private javax.swing.JButton jDeleteTestButton;
    private javax.swing.JButton jEditAuthorButton;
    private javax.swing.JButton jEditChangelogButton;
    private javax.swing.JButton jEditClassButton;
    private javax.swing.JButton jEditClassDescriptorButton;
    private javax.swing.JButton jEditExecutionButton;
    private javax.swing.JButton jEditMethodButton;
    private javax.swing.JButton jEditMethodDescriptorButton;
    private javax.swing.JButton jEditPackageButton;
    private javax.swing.JButton jEditPackageDescriptorButton;
    private javax.swing.JButton jEditProjectButton;
    private javax.swing.JButton jEditReleaseButton;
    private javax.swing.JButton jEditTestButton;
    private javax.swing.JPanel jExecutionButtons;
    private javax.swing.JTextField jExecutionCodReleaseField;
    private javax.swing.JTextField jExecutionCodTestField;
    private javax.swing.JTextField jExecutionCodeField;
    private com.toedter.calendar.JDateChooser jExecutionDateField;
    private javax.swing.JPanel jExecutionResultsPanel;
    private javax.swing.JPanel jExecutionSearchFieldsPanel;
    private javax.swing.JPanel jExecutionSearchFiltersPanel;
    private javax.swing.JPanel jExecutionSearchPanel;
    private javax.swing.JTable jExecutionTable;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemEnableDisableEdit;
    private javax.swing.JMenuItem jMenuItemRefreshAll;
    private javax.swing.JMenu jMenuOptions;
    private javax.swing.JPanel jMethodButtons;
    private javax.swing.JTextField jMethodCodClassField;
    private javax.swing.JTextField jMethodCodMethodDescriptorField;
    private javax.swing.JTextField jMethodCodeField;
    private javax.swing.JPanel jMethodDescriptorButtons;
    private javax.swing.JTextField jMethodDescriptorCodeField;
    private javax.swing.JTextField jMethodDescriptorInputParametersField;
    private javax.swing.JTextField jMethodDescriptorNameField;
    private javax.swing.JTextField jMethodDescriptorReturnField;
    private javax.swing.JPanel jMethodDescriptorSearchByFiltersPanel;
    private javax.swing.JPanel jMethodDescriptorSearchFieldsPanel;
    private javax.swing.JPanel jMethodDescriptorSearchFiltersPanel;
    private javax.swing.JPanel jMethodDescriptorSearchPanel;
    private javax.swing.JPanel jMethodDescriptorSearchResultsPanel;
    private javax.swing.JTable jMethodDescriptorTable;
    private javax.swing.JPanel jMethodResultsPanel;
    private javax.swing.JPanel jMethodSearchByFiltersPanel;
    private javax.swing.JPanel jMethodSearchFieldsPanel;
    private javax.swing.JPanel jMethodSearchFiltersPanel;
    private javax.swing.JPanel jMethodSearchPanel;
    private javax.swing.JTable jMethodTable;
    private javax.swing.JPanel jOtherButtons;
    private javax.swing.JPanel jOtherChoosePanel;
    private javax.swing.JTextField jOtherCodeField;
    private javax.swing.JTextField jOtherGeneralField;
    private javax.swing.JPanel jOtherResultsPanel;
    private javax.swing.JPanel jOtherSearchFieldsPanel;
    private javax.swing.JPanel jOtherSearchFiltersPanel;
    private javax.swing.JPanel jOtherSearchPanel;
    private javax.swing.JTable jOtherTable;
    private javax.swing.JPanel jPackageButtons;
    private javax.swing.JTextField jPackageCodPackageDescriptorField;
    private javax.swing.JTextField jPackageCodReleaseField;
    private javax.swing.JTextField jPackageCodeField;
    private javax.swing.JPanel jPackageDescriptorButtons;
    private javax.swing.JTextField jPackageDescriptorCodeField;
    private javax.swing.JTextField jPackageDescriptorEncloserPackField;
    private javax.swing.JTextField jPackageDescriptorNameField;
    private javax.swing.JPanel jPackageDescriptorSearchByFiltersPanel;
    private javax.swing.JPanel jPackageDescriptorSearchFieldsPanel;
    private javax.swing.JPanel jPackageDescriptorSearchFiltersPanel;
    private javax.swing.JPanel jPackageDescriptorSearchPanel;
    private javax.swing.JPanel jPackageDescriptorSearchResultsPanel;
    private javax.swing.JTable jPackageDescriptorTable;
    private javax.swing.JPanel jPackageResultsPanel;
    private javax.swing.JPanel jPackageSearchByFiltersPanel;
    private javax.swing.JPanel jPackageSearchFieldsPanel;
    private javax.swing.JPanel jPackageSearchFiltersPanel;
    private javax.swing.JPanel jPackageSearchPanel;
    private javax.swing.JTable jPackageTable;
    private javax.swing.JPanel jProjectButton;
    private com.toedter.calendar.JDateChooser jProjectDateCancellationField;
    private com.toedter.calendar.JDateChooser jProjectDateCreationField;
    private javax.swing.JTextField jProjectNameField;
    private javax.swing.JTextField jProjectPathField;
    private javax.swing.JPanel jProjectSearchByFiltersPanel;
    private javax.swing.JPanel jProjectSearchFieldsPanel;
    private javax.swing.JPanel jProjectSearchFiltersPanel;
    private javax.swing.JPanel jProjectSearchPanel;
    private javax.swing.JPanel jProjectSearchResultsPanel;
    private javax.swing.JTable jProjectTable;
    private javax.swing.JPanel jReleaseButtons;
    private javax.swing.JTextField jReleaseCodeField;
    private com.toedter.calendar.JDateChooser jReleaseDateField;
    private javax.swing.JTextField jReleasePathField;
    private javax.swing.JPanel jReleaseResultsPanel;
    private javax.swing.JPanel jReleaseSearchFieldsPanel;
    private javax.swing.JPanel jReleaseSearchFiltersPanel;
    private javax.swing.JPanel jReleaseSearchPanel;
    private javax.swing.JTable jReleaseTable;
    private javax.swing.JTextField jReleaseVersionField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JButton jSearchAuthorButton;
    private javax.swing.JButton jSearchChangelogButton;
    private javax.swing.JButton jSearchClassButton;
    private javax.swing.JButton jSearchClassDescriptorButton;
    private javax.swing.JButton jSearchExecutionButton;
    private javax.swing.JButton jSearchMethodButton;
    private javax.swing.JButton jSearchMethodDescriptorButton;
    private javax.swing.JButton jSearchOtherButton;
    private javax.swing.JButton jSearchPackageButton;
    private javax.swing.JButton jSearchPackageDescriptorButton;
    private javax.swing.JButton jSearchProjectButton;
    private javax.swing.JButton jSearchReleaseButton;
    private javax.swing.JButton jSearchTestButton;
    private javax.swing.JPanel jShowForEachAuthorFiltersPanel;
    private javax.swing.JPanel jShowForEachChangelogFiltersPanel;
    private javax.swing.JPanel jShowForEachClassDescriptorFiltersPanel;
    private javax.swing.JPanel jShowForEachMethodDescriptorFiltersPanel;
    private javax.swing.JPanel jShowForEachPackageDescriptorFiltersPanel;
    private javax.swing.JPanel jShowForEachProjectFiltersPanel;
    private javax.swing.JPanel jShowForEachTestFiltersPanel;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel jTestButtons;
    private javax.swing.JTextField jTestCodeField;
    private javax.swing.JTextField jTestNameField;
    private javax.swing.JTextField jTestPathField;
    private javax.swing.JPanel jTestSearchByFiltersPanel;
    private javax.swing.JPanel jTestSearchFieldsPanel;
    private javax.swing.JPanel jTestSearchFiltersPanel;
    private javax.swing.JPanel jTestSearchPanel;
    private javax.swing.JPanel jTestSearchResultsPanel;
    private javax.swing.JTable jTestTable;
    private javax.swing.JRadioButton jTestsRadioButton;
    private java.awt.Label label1;
    private java.awt.Label label10;
    private java.awt.Label label11;
    private java.awt.Label label12;
    private java.awt.Label label13;
    private java.awt.Label label14;
    private java.awt.Label label15;
    private java.awt.Label label16;
    private java.awt.Label label17;
    private java.awt.Label label18;
    private java.awt.Label label19;
    private java.awt.Label label2;
    private java.awt.Label label20;
    private java.awt.Label label21;
    private java.awt.Label label22;
    private java.awt.Label label23;
    private java.awt.Label label24;
    private java.awt.Label label25;
    private java.awt.Label label26;
    private java.awt.Label label27;
    private java.awt.Label label28;
    private java.awt.Label label29;
    private java.awt.Label label3;
    private java.awt.Label label30;
    private java.awt.Label label31;
    private java.awt.Label label32;
    private java.awt.Label label33;
    private java.awt.Label label34;
    private java.awt.Label label35;
    private java.awt.Label label36;
    private java.awt.Label label37;
    private java.awt.Label label38;
    private java.awt.Label label39;
    private java.awt.Label label4;
    private java.awt.Label label40;
    private java.awt.Label label41;
    private java.awt.Label label42;
    private java.awt.Label label43;
    private java.awt.Label label44;
    private java.awt.Label label45;
    private java.awt.Label label46;
    private java.awt.Label label47;
    private java.awt.Label label48;
    private java.awt.Label label49;
    private java.awt.Label label5;
    private java.awt.Label label50;
    private java.awt.Label label51;
    private java.awt.Label label52;
    private java.awt.Label label53;
    private java.awt.Label label54;
    private java.awt.Label label55;
    private java.awt.Label label56;
    private java.awt.Label label57;
    private java.awt.Label label58;
    private java.awt.Label label59;
    private java.awt.Label label6;
    private java.awt.Label label60;
    private java.awt.Label label61;
    private java.awt.Label label62;
    private java.awt.Label label63;
    private java.awt.Label label64;
    private java.awt.Label label65;
    private java.awt.Label label66;
    private java.awt.Label label67;
    private java.awt.Label label68;
    private java.awt.Label label69;
    private java.awt.Label label7;
    private java.awt.Label label70;
    private java.awt.Label label71;
    private java.awt.Label label72;
    private java.awt.Label label73;
    private java.awt.Label label74;
    private java.awt.Label label77;
    private java.awt.Label label8;
    private java.awt.Label label9;
    private java.awt.Label secondFieldLabel;
    // End of variables declaration//GEN-END:variables
}
