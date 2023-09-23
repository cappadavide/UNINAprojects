/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.toedter.calendar.JDateChooser;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Classe che consente l'inserimento di elementi nel Database
 * @author davso
 */
public class InsertFrame extends javax.swing.JFrame {
    /*Model di tipo DefaultListModel da assegnare alle JList*/
    private final DefaultListModel AuthorProjectModel=new DefaultListModel();
    private final DefaultListModel AssignedAuthorProjectModel=new DefaultListModel();
    private final DefaultListModel AuthorClassModel=new DefaultListModel();
    private final DefaultListModel AssignedAuthorClassModel=new DefaultListModel();
    private final DefaultListModel AuthorMethodModel=new DefaultListModel();
    private final DefaultListModel AssignedAuthorMethodModel=new DefaultListModel();
    private final DefaultListModel ClassTestModel=new DefaultListModel();
    private final DefaultListModel AssignedClassTestModel=new DefaultListModel();
    private final DefaultListModel MethodTestModel=new DefaultListModel();
    private final DefaultListModel AssignedMethodTestModel=new DefaultListModel();
    private final DefaultListModel AuthorTestModel=new DefaultListModel();
    private final DefaultListModel AssignedAuthorTestModel=new DefaultListModel();
    private final DefaultListModel AuthorChangelogModel=new DefaultListModel();
    private final DefaultListModel AssignedAuthorChangelogModel=new DefaultListModel();
    ///
    /*Builders da usare per la costruzione delle query*/
    private final ClassQueryBuilder classBuilder=new ClassQueryBuilder();
    private final ProjectQueryBuilder projectBuilder=new ProjectQueryBuilder();
    private final PackageQueryBuilder packageBuilder=new PackageQueryBuilder();
    private final AuthorQueryBuilder authorBuilder= new AuthorQueryBuilder();
    private final MethodQueryBuilder methodBuilder=new MethodQueryBuilder();
    private final TestQueryBuilder testBuilder=new TestQueryBuilder();
    private final ChangelogQueryBuilder changelogBuilder=new ChangelogQueryBuilder();
    private final ListManager listBuilder=new ListManager();
    private final ComboBoxManager comboBuilder=new ComboBoxManager();
    ///
    /**
     * Variabile che previene comportamenti anomali delle JComboBox alla loro
     * inizializzazione. 
     * Se false, allora le ComboBox non devono controllare per eventuali
     * cambiamenti altrimenti devono. 
     */
    private boolean checkComboChanges=false;
    /**
     * Creates new form InsertFrame
     */
    public InsertFrame() {
        initComponents();
        initInsertPackageComboBoxes();
        initInsertClassComboBoxes();
        initInsertMethodComboBoxes();
        initInsertTestComboBoxes();
        initInsertChangelogComboBoxes();
        initLists();
        
    }
    /**
     * Metodo che setta i models delle liste e le riempie con delle query.
     */
    private void initLists() {
        jAuthorsList.setModel(AuthorProjectModel);
        jAssignedProjectDevsList.setModel(AssignedAuthorProjectModel);
        jAuthorsBasedOnProjectList.setModel(AuthorClassModel);
        jAssignedClassDevsList.setModel(AssignedAuthorClassModel);
        jAuthorsList2.setModel(AuthorMethodModel);
        jAssignedMethodDevsList.setModel(AssignedAuthorMethodModel);
        jClassBasedOnProjectList.setModel(ClassTestModel);
        jTestedClassList.setModel(AssignedClassTestModel);
        jMethodsTestList.setModel(MethodTestModel);
        jTestedMethodsList.setModel(AssignedMethodTestModel);
        jAuthorsBasedOnProjectList1.setModel(AuthorTestModel);
        jAssignedTestDevsList.setModel(AssignedAuthorTestModel);
        jAuthorsBasedOnProjectList2.setModel(AuthorChangelogModel);
        jAssignedChangelogDevsList.setModel(AssignedAuthorChangelogModel);
        String query=authorBuilder.authorsListQueryBuilder();
        listBuilder.buildAuthorList(AuthorProjectModel, query);
        String projectname=getPrimaryKey(jComboBoxClassDevChooseProject);
        query=authorBuilder.authorsBasedOnProjectListQueryBuilder(projectname);
        listBuilder.buildAuthorList(AuthorClassModel, query);
        listBuilder.buildAuthorList(AuthorMethodModel,authorBuilder.authorsListQueryBuilder());
        projectname=getPrimaryKey(jComboBoxProjectTest);
        query=classBuilder.classListProjectBasedQueryBuilder(projectname);
        listBuilder.buildAuthorList(ClassTestModel, query);
        listBuilder.buildAuthorList(MethodTestModel,methodBuilder.methodListQueryBuilder());
        projectname=getPrimaryKey(jComboBoxTestDevChooseProject);
        listBuilder.buildAuthorList(AuthorTestModel,authorBuilder.authorsBasedOnProjectListQueryBuilder(projectname));
        projectname=getPrimaryKey(jComboBoxChangelogDevChooseProject);
        listBuilder.buildAuthorList(AuthorChangelogModel,authorBuilder.authorsBasedOnProjectListQueryBuilder(projectname));
        
    }
    /**
     * Metodo che riguarda l'inizializzazione delle combobox 
     * nella sezione di inserimento package.
     */
    private void initInsertPackageComboBoxes(){
        checkComboChanges=false;
        comboBuilder.buildComboBox(jComboBoxProjectPackage,projectBuilder.projectsListQueryBuilder(),false);
        String projectname=getPrimaryKey(jComboBoxProjectPackage);
        comboBuilder.buildComboBox(jComboBoxReleasePackage,projectBuilder.projectReleasesQueryBuilder(projectname),false);
        String release=getPrimaryKey(jComboBoxReleasePackage);
        comboBuilder.buildComboBox(jComboBoxFatherPackage,packageBuilder.packageListReleaseBasedQueryBuilder(release),true);
        checkComboChanges=true;
    }
    /**
     * Metodo che riguarda l'inizializzazione delle combobox 
     * nella sezione di inserimento classe.
     */
    private void initInsertClassComboBoxes(){
        checkComboChanges=false;
        comboBuilder.buildComboBox(jComboBoxProjectClass,projectBuilder.projectsListQueryBuilder(),false);
        String projectname=getPrimaryKey(jComboBoxProjectClass);
        comboBuilder.buildComboBox(jComboBoxReleaseClass,projectBuilder.projectReleasesQueryBuilder(projectname),false);
        String release=getPrimaryKey(jComboBoxReleaseClass);
        comboBuilder.buildComboBox(jComboBoxClassPackage,packageBuilder.packageListReleaseBasedQueryBuilder(release),false);
        String codpackage=getPrimaryKey(jComboBoxClassPackage);
        comboBuilder.buildComboBox(jComboBoxEnclosingClass,classBuilder.classListReleaseBasedQueryBuilder(release,codpackage),true);
        comboBuilder.buildComboBox(jComboBoxExtendedClass,classBuilder.classListPackageBasedQueryBuilder(codpackage),true);
        comboBuilder.buildComboBox(jComboBoxClassDevChooseProject, projectBuilder.projectsListQueryBuilder(), false);
        projectname=getPrimaryKey(jComboBoxClassDevChooseProject);
        comboBuilder.buildComboBox(jComboBoxClassDevChooseClass,classBuilder.classListProjectBasedQueryBuilder(projectname),false);
        checkComboChanges=true;
    }
    /**
     * Metodo che riguarda l'inizializzazione delle combobox 
     * nella sezione di inserimento metodo.
     */
    private void initInsertMethodComboBoxes(){
        checkComboChanges=false;
        comboBuilder.buildComboBox(jComboBoxProjectMethod,projectBuilder.projectsListQueryBuilder(),false);
        String projectname=getPrimaryKey(jComboBoxProjectMethod);
        comboBuilder.buildComboBox(jComboBoxReleaseMethod,projectBuilder.projectReleasesQueryBuilder(projectname),false);
        String release=getPrimaryKey(jComboBoxReleaseMethod);
        comboBuilder.buildComboBox(jComboBoxMethodPackage,packageBuilder.packageListReleaseBasedQueryBuilder(release),false);
        String codpackage=getPrimaryKey(jComboBoxMethodPackage);
        comboBuilder.buildComboBox(jComboBoxMethodClass,classBuilder.classListReleaseBasedQueryBuilder(release,codpackage),false);
        comboBuilder.buildComboBox(jComboBoxMethodDevChooseMethod,methodBuilder.methodListQueryBuilder(),false);
        comboBuilder.buildComboBox(jComboBoxMethodClassChooseMethod,methodBuilder.methodListQueryBuilder(),false);
        checkComboChanges=true;
    }
    /**
     * Metodo che riguarda l'inizializzazione delle combobox 
     * nella sezione di inserimento test.
     */
    private void initInsertTestComboBoxes(){
        checkComboChanges=false;
        String query=projectBuilder.projectsListQueryBuilder();
        comboBuilder.buildComboBox(jComboBoxProjectTest, query, false);
        comboBuilder.buildComboBox(jComboBoxTestDevChooseProject,query,false);
        String projectname=getPrimaryKey(jComboBoxTestDevChooseProject);
        comboBuilder.buildComboBox(jComboBoxTestDevChooseTest,testBuilder.testBasedOnProjectList(projectname), false);
        comboBuilder.buildComboBox(jComboBoxExecutionChooseTest,testBuilder.testListQueryBuilder(),false);
        String code=getPrimaryKey(jComboBoxExecutionChooseTest);
        comboBuilder.buildComboBox(jComboBoxExecutionRelease,projectBuilder.projectReleasesBasedOnTest(code),false);
        
        checkComboChanges=true;
    }
    /**
     * Metodo che riguarda l'inizializzazione delle combobox 
     * nella sezione di inserimento changelog.
     */
    private void initInsertChangelogComboBoxes(){
        checkComboChanges=false;
        comboBuilder.buildComboBox(jComboBoxProjectChangelog,projectBuilder.projectsListQueryBuilder(),false);
        String projectname=getPrimaryKey(jComboBoxProjectChangelog);
        comboBuilder.buildComboBox(jComboBoxChangelogRelease,projectBuilder.projectReleasesQueryBuilder(projectname),false);
        String release=getPrimaryKey(jComboBoxChangelogRelease);
        comboBuilder.buildComboBox(jComboBoxChangelogPackage,packageBuilder.packageListQueryBuilder(release),false);
        comboBuilder.buildComboBox(jComboBoxChangelogClass,classBuilder.classListReleaseBasedQueryBuilder(release),false);
        comboBuilder.buildComboBox(jComboBoxChangelogMethod,methodBuilder.methodListReleaseBasedQueryBuilder(release),false);
        comboBuilder.buildComboBox(jComboBoxChangelogDevChooseProject,projectBuilder.projectsListQueryBuilder(),false);
        projectname=getPrimaryKey(jComboBoxChangelogDevChooseProject);
        comboBuilder.buildComboBox(jComboBoxChangelogDevChooseLog,changelogBuilder.logsListBasedOnProjectQueryBuilder(projectname),false);
        checkComboChanges=true;
    }
    
    private void refreshReleases(String projectname,JComboBox combobox){
        checkComboChanges=false;
        System.out.println("Refresh Releases in corso...");
        comboBuilder.buildComboBox(combobox, projectBuilder.projectReleasesQueryBuilder(projectname),false);
        checkComboChanges=true;
    }
    private void refreshPackages(String release,JComboBox combobox){
        checkComboChanges=false;
        System.out.println("Refresh Packages in corso...");
        comboBuilder.buildComboBox(combobox,packageBuilder.packageListReleaseBasedQueryBuilder(release),false);
        checkComboChanges=true;
    }
    private void refreshNestedClasses(String release,String codpackage,JComboBox combobox){
        checkComboChanges=false;
        System.out.println("Refresh NestedClasses in corso...");
        comboBuilder.buildComboBox(combobox,classBuilder.classListReleaseBasedQueryBuilder(release,codpackage),true);
        checkComboChanges=true;
    }
    private void refreshMethodClasses(String release,String codpackage,JComboBox combobox){
        checkComboChanges=false;
        System.out.println("Refresh MethodClasses in corso...");
        comboBuilder.buildComboBox(combobox,classBuilder.classListReleaseBasedQueryBuilder(release,codpackage),false);
        checkComboChanges=true;
    }
    private void refreshSuperClasses(String codpackage,JComboBox combobox){
        checkComboChanges=false;
        System.out.println("Refresh Classes in corso...");
        comboBuilder.buildComboBox(combobox,classBuilder.classListPackageBasedQueryBuilder(codpackage),true);
        checkComboChanges=true;
    }
    
    /**
     * Metodo per ricavare la chiave primaria di un item
     * @param combo JComboBox dal quale prelevare la chiave Primaria
     * @return Chiave Primaria (String)
     */
    private String getPrimaryKey(JComboBox combo){
        try{
            String word=combo.getSelectedItem().toString();
            return word.substring(0,word.indexOf(" "));
        }
        catch(NullPointerException e){
            return "";
        }
        catch(StringIndexOutOfBoundsException d){
            return combo.getSelectedItem().toString();
        }
    }
    
    /**
     * @return ArrayList con oggetti di tipo 
     * JTextField per l'inserimento di un autore
     */
    ArrayList getAuthorInsertFields(){
        ArrayList <JTextField> fields = new ArrayList<>();
        fields.add(jAuthorNameField);
        fields.add(jAuthorSurnameField);
        fields.add(jAuthorEmailField);
        return fields;
    }
     /**
     * @return ArrayList con oggetti di tipo 
     * JTextField per l'inserimento di un progetto
     */
    ArrayList getNewProjectInsertFields(){
        ArrayList <JTextField> fields = new ArrayList<>();
        fields.add(jProjectNameField);
        fields.add(jProjectPathField);
        return fields;
    }
     /**
     * @return ArrayList con oggetti di tipo 
     * JDateChooser per l'inserimento di un autore
     */
    ArrayList getNewProjectInsertDates(){
        ArrayList <JDateChooser> fields = new ArrayList<>();
        fields.add(jProjectCreationDateField);
        fields.add(jProjectCancelationDateField);
        return fields;
    }
     /**
     * @return Descrizione di un nuovo progetto (JTextArea)
     */
    JTextArea getNewProjectDescription(){
        return jProjectDescriptionArea;
    }
    /**
     * @return ArrayList con oggetti di tipo 
     * JTextField per l'inserimento di una nuova release.
     */
    ArrayList getNewProjectReleaseInsertFields(){
        ArrayList <JTextField> fields = new ArrayList<>();
        fields.add(jProjectReleaseNumberField);
        fields.add(jProjectReleasePathField);
        return fields;
    }
    /**
     * @return JDateChooser della data rilascio di
     * una nuova release di un progetto
     */
    JDateChooser getNewProjectDateReleaseField(){
        return jProjectDateReleaseField;
    }
     /**
     * @return JComboBox per la scelta di un progetto
     * di cui inserire la nuova release
     */
    JComboBox getProjectReleaseChooseProject(){
        return jComboBoxProjectReleaseChooseProject;
    }
     /**
     * @return JComboBox per la scelta di un progetto
     * a cui assegnare gli autori
     */
    JComboBox getProjectDevChooseProject(){
        return jComboBoxProjectDevChooseProject;
    }
     /**
     * @return JTextField per il nome del nuovo package
     */
    JTextField getNewPackageNameField(){
        return jPackageNameField;
    }
    /**
     * @return ArrayList con oggetti di tipo 
     * JComboBox per l'inserimento di un nuovo package.
     */
    ArrayList getNewPackageComboBoxes(){
        ArrayList <JComboBox> fields=new ArrayList<>();
        fields.add(jComboBoxProjectPackage);
        fields.add(jComboBoxReleasePackage);
        fields.add(jComboBoxFatherPackage);
        return fields;
    }
    /**
     * @return ArrayList con oggetti di tipo 
     * JTextField per l'inserimento di una nuova classe.
     */
    ArrayList getNewClassInsertFields(){
        ArrayList <JTextField> fields=new ArrayList <>();
        fields.add(jClassNameField);
        fields.add(jClassSourceField);
        fields.add(jStartingClassLineField);
        fields.add(jEndingClassLineField);
        return fields;
    }
    /**
     * @return ArrayList con oggetti di tipo 
     * JComboBox per l'inserimento di una nuova classe.
     */
    ArrayList getNewClassComboBoxes(){
        ArrayList <JComboBox> fields=new ArrayList<>();
        fields.add(jComboBoxProjectClass);
        fields.add(jComboBoxReleaseClass);
        fields.add(jComboBoxClassPackage);
        fields.add(jComboBoxEnclosingClass);
        fields.add(jComboBoxClassModifiers);
        fields.add(jComboBoxClassScope);
        fields.add(jComboBoxExtendedClass);
        return fields;
    }
    /**
     * @return ArrayList con oggetti di tipo 
     * JComboBox per l'inserimento di una nuova assegnazione Autori-Classe.
     */
    ArrayList getNewClassDevComboBoxes(){
        ArrayList <JComboBox> fields = new ArrayList<>();
        fields.add(jComboBoxClassDevChooseProject);
        fields.add(jComboBoxClassDevChooseClass);
        return fields;
    }
    /**
     * @return ArrayList con oggetti di tipo 
     * JTextField per l'inserimento di un nuovo metodo.
     */
    ArrayList getNewMethodInsertFields(){
        ArrayList <JTextField> fields= new ArrayList<>();
        fields.add(jMethodNameField);
        fields.add(jMethodParametersField);
        fields.add(jMethodReturnTypeField);
        return fields;
    }
    /**
     * @return ArrayList con oggetti di tipo 
     * JComboBox per l'inserimento di un nuovo metodo.
     */
    ArrayList getNewMethodComboBoxes(){
        ArrayList <JComboBox> fields = new ArrayList<>();
        fields.add(jComboBoxMethodScope);
        fields.add(jComboBoxMethodModifiers);

        return fields;
    }
    /**
     * @return ArrayList con oggetti di tipo 
     * JTextField per l'inserimento di un nuovo metodo (prima versione di rilascio).
     */
    ArrayList getNewMethodClassInsertFields(){
        ArrayList <JTextField> fields=new ArrayList();
        fields.add(jStartingMethodLineField);
        fields.add(jEndingMethodLineField);
        return fields;
    }
    /**
     * @return JComboBox per la scelta di un metodo
     * a cui assegnare gli autori
     */
    JComboBox getMethodDevChooseMethodComboBox(){
        return jComboBoxMethodDevChooseMethod;
    }
    /**
     * @return ArrayList con oggetti di tipo 
     * JComboBox per l'inserimento di un nuovo metodo (prima versione di rilascio).
     */
    ArrayList getNewMethodClassComboBoxes(){
        ArrayList <JComboBox> fields = new ArrayList<>();
        fields.add(jComboBoxMethodClassChooseMethod);
        fields.add(jComboBoxMethodClass);
        return fields;
    }
    /**
     * @return ArrayList con oggetti di tipo 
     * JTextField per l'inserimento di un nuovo test.
     */ 
    ArrayList getNewTestInsertFields(){
        ArrayList <JTextField> fields=new ArrayList();
        fields.add(jTestNameField);
        fields.add(jTestPathField);
        return fields;
    } 
    /**
     * @return JTextArea della descrizione su un nuovo test
     */
    JTextArea getTestDescriptionTextArea(){
        return jTestDescriptionTextArea;
    }
    
    /**
     * @return JComboBox per la scelta di un Progetto
     * a cui assegnare il test
     */
    JComboBox getNewTestProjectComboBox(){
        return jComboBoxProjectTest;
    }
    /**
     * @return JComboBox per la scelta di un test
     * a cui assegnare gli autori
     */
    JComboBox getNewTestDevTestComboBox(){
        return jComboBoxTestDevChooseTest;
    }
    /**
     * @return ArrayList con oggetti di tipo 
     * JComboBox per l'inserimento di una nuova esecuzione.
     */
    ArrayList getNewExecutionComboBoxes(){
        ArrayList <JComboBox> fields = new ArrayList<>();
        fields.add(jComboBoxExecutionChooseTest);
        fields.add(jComboBoxExecutionRelease);
        fields.add(jComboBoxExecutionResult);
        return fields;
    }
    /**
     * @return JDateChooser della data di un'esecuzione
     */
    JDateChooser getExecutionDate(){
        return jExecutionDateField;
    }
    
     /**
     * @return ArrayList con oggetti di tipo 
     * JComboBox per l'inserimento di un nuovo log.
     */
    ArrayList getNewChangelogComboBoxes(){
        ArrayList <JComboBox> fields = new ArrayList<>();
        fields.add(jComboBoxChangelogChangeType);
        fields.add(jComboBoxChangelogChangedObject);
        fields.add(jComboBoxChangelogPackage);
        fields.add(jComboBoxChangelogClass);
        fields.add(jComboBoxChangelogMethod);
        fields.add(jComboBoxProjectChangelog);
        return fields;
    }
    /**
     * @return ArrayList con oggetti di tipo 
     * JDateChooser per l'inserimento di un nuovo log.
     */
    ArrayList getNewChangelogDates(){
        ArrayList <JDateChooser> fields = new ArrayList<>();
        fields.add(jChangelogOpeningDateField);
        fields.add(jChangelogClosingDateField);
        return fields;
    }
    /**
     * @return JTextArea della descrizione di un log
     */
    JTextArea getNewChangelogDescription(){
        return jChangelogDescriptionTextArea;
    }
    /**
     * @return JComboBox per la scelta di un log al quale
     * assegnare degli autori
     */
    JComboBox getNewChangelogDevChangelogComboBox(){
        return jComboBoxChangelogDevChooseLog;
    }
    
    private void verifyChangelogCB(){
        if(jComboBoxChangelogChangedObject.getSelectedItem().toString().equals("Package")){
            jComboBoxChangelogClass.setEnabled(false);
            jComboBoxChangelogMethod.setEnabled(false);
            jComboBoxChangelogPackage.setEnabled(true);
        }
        else if(jComboBoxChangelogChangedObject.getSelectedItem().toString().equals("Classe")){
            jComboBoxChangelogClass.setEnabled(true);
            jComboBoxChangelogMethod.setEnabled(false);
            jComboBoxChangelogPackage.setEnabled(false);
        }
        else{
            jComboBoxChangelogClass.setEnabled(false);
            jComboBoxChangelogMethod.setEnabled(true);
            jComboBoxChangelogPackage.setEnabled(false);
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
        jAuthorInsertPanel = new javax.swing.JPanel();
        label5 = new java.awt.Label();
        jAuthorInsertFieldsPanel = new javax.swing.JPanel();
        jAuthorEmailField = new javax.swing.JTextField();
        jAuthorSurnameField = new javax.swing.JTextField();
        label1 = new java.awt.Label();
        jAuthorNameField = new javax.swing.JTextField();
        label2 = new java.awt.Label();
        label4 = new java.awt.Label();
        label3 = new java.awt.Label();
        jInsertAuthorButton = new javax.swing.JButton();
        jProjectInsertPanel = new javax.swing.JPanel();
        jProjectInsertOperationsPanel = new javax.swing.JPanel();
        jNewProjectButton = new javax.swing.JButton();
        jNewReleaseButton = new javax.swing.JButton();
        jNewProjectDevButton = new javax.swing.JButton();
        jNewProjectInsertPanel = new javax.swing.JPanel();
        label7 = new java.awt.Label();
        label8 = new java.awt.Label();
        jProjectNameField = new javax.swing.JTextField();
        label9 = new java.awt.Label();
        jProjectPathField = new javax.swing.JTextField();
        label10 = new java.awt.Label();
        label11 = new java.awt.Label();
        jProjectCreationDateField = new com.toedter.calendar.JDateChooser();
        label6 = new java.awt.Label();
        jProjectCancelationDateField = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jProjectDescriptionArea = new javax.swing.JTextArea();
        jInsertNewProjectButton = new javax.swing.JButton();
        jNewProjectReleaseInsertPanel = new javax.swing.JPanel();
        label12 = new java.awt.Label();
        label13 = new java.awt.Label();
        jComboBoxProjectReleaseChooseProject = new javax.swing.JComboBox<>();
        label16 = new java.awt.Label();
        jProjectReleaseNumberField = new javax.swing.JTextField();
        label15 = new java.awt.Label();
        jProjectReleasePathField = new javax.swing.JTextField();
        label14 = new java.awt.Label();
        jProjectDateReleaseField = new com.toedter.calendar.JDateChooser();
        jInsertProjectReleaseButton = new javax.swing.JButton();
        jNewProjectDeveloperInsertPanel = new javax.swing.JPanel();
        label18 = new java.awt.Label();
        jPanel1 = new javax.swing.JPanel();
        label17 = new java.awt.Label();
        jComboBoxProjectDevChooseProject = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jAuthorsList = new javax.swing.JList<>();
        jRefreshButton = new javax.swing.JButton();
        jRightArrowButton = new javax.swing.JButton();
        jLeftArrowButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jAssignedProjectDevsList = new javax.swing.JList<>();
        jInsertProjectDevsButton = new javax.swing.JButton();
        jPackageInsertPanel = new javax.swing.JPanel();
        label19 = new java.awt.Label();
        jPackageInsertFieldsPanel = new javax.swing.JPanel();
        label20 = new java.awt.Label();
        jPackageNameField = new javax.swing.JTextField();
        label21 = new java.awt.Label();
        label22 = new java.awt.Label();
        label23 = new java.awt.Label();
        jComboBoxProjectPackage = new javax.swing.JComboBox<>();
        jComboBoxReleasePackage = new javax.swing.JComboBox<>();
        jComboBoxFatherPackage = new javax.swing.JComboBox<>();
        label24 = new java.awt.Label();
        jInsertPackageButton = new javax.swing.JButton();
        jClassInsertPanel = new javax.swing.JPanel();
        jClassInsertOperationsPanel = new javax.swing.JPanel();
        jNewClassButton = new javax.swing.JButton();
        jNewClassDevButton = new javax.swing.JButton();
        jNewClassInsertPanel = new javax.swing.JPanel();
        jClassInsertFieldsPanel = new javax.swing.JPanel();
        label28 = new java.awt.Label();
        label33 = new java.awt.Label();
        jClassNameField = new javax.swing.JTextField();
        label27 = new java.awt.Label();
        jComboBoxProjectClass = new javax.swing.JComboBox<>();
        label30 = new java.awt.Label();
        jComboBoxReleaseClass = new javax.swing.JComboBox<>();
        label29 = new java.awt.Label();
        jComboBoxClassPackage = new javax.swing.JComboBox<>();
        label26 = new java.awt.Label();
        jStartingClassLineField = new javax.swing.JTextField();
        label31 = new java.awt.Label();
        jComboBoxEnclosingClass = new javax.swing.JComboBox<>();
        label32 = new java.awt.Label();
        jComboBoxClassModifiers = new javax.swing.JComboBox<>();
        label34 = new java.awt.Label();
        jComboBoxClassScope = new javax.swing.JComboBox<>();
        label36 = new java.awt.Label();
        jClassSourceField = new javax.swing.JTextField();
        label37 = new java.awt.Label();
        jComboBoxExtendedClass = new javax.swing.JComboBox<>();
        label35 = new java.awt.Label();
        jEndingClassLineField = new javax.swing.JTextField();
        jInsertClassButton = new javax.swing.JButton();
        jNewClassDeveloperInsertPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        label39 = new java.awt.Label();
        jComboBoxClassDevChooseProject = new javax.swing.JComboBox<>();
        label25 = new java.awt.Label();
        jComboBoxClassDevChooseClass = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jAuthorsBasedOnProjectList = new javax.swing.JList<>();
        jRefreshButton1 = new javax.swing.JButton();
        jRightArrowButton1 = new javax.swing.JButton();
        jLeftArrowButton1 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jAssignedClassDevsList = new javax.swing.JList<>();
        jInsertClassDevsButton = new javax.swing.JButton();
        label38 = new java.awt.Label();
        jMethodInsertPanel = new javax.swing.JPanel();
        jMethodInsertOperationsPanel = new javax.swing.JPanel();
        jNewMethodButton = new javax.swing.JButton();
        jNewMethodDevButton = new javax.swing.JButton();
        jNewMethodClassButton = new javax.swing.JButton();
        jNewMethodInsertPanel = new javax.swing.JPanel();
        label58 = new java.awt.Label();
        jMethodInsertFieldsPanel = new javax.swing.JPanel();
        label40 = new java.awt.Label();
        label50 = new java.awt.Label();
        jMethodNameField = new javax.swing.JTextField();
        label47 = new java.awt.Label();
        jComboBoxMethodModifiers = new javax.swing.JComboBox<>();
        label41 = new java.awt.Label();
        jMethodParametersField = new javax.swing.JTextField();
        label48 = new java.awt.Label();
        jComboBoxMethodScope = new javax.swing.JComboBox<>();
        label49 = new java.awt.Label();
        jMethodReturnTypeField = new javax.swing.JTextField();
        jInsertMethodButton = new javax.swing.JButton();
        jNewMethodDeveloperInsertPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        label53 = new java.awt.Label();
        jComboBoxMethodDevChooseMethod = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jAuthorsList2 = new javax.swing.JList<>();
        jRefreshButton2 = new javax.swing.JButton();
        jRightArrowButton2 = new javax.swing.JButton();
        jLeftArrowButton2 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jAssignedMethodDevsList = new javax.swing.JList<>();
        jInsertMethodDevsButton = new javax.swing.JButton();
        label54 = new java.awt.Label();
        jNewMethodClassInsertPanel = new javax.swing.JPanel();
        jNewMethodClassInsertFieldsPanel = new javax.swing.JPanel();
        label57 = new java.awt.Label();
        label61 = new java.awt.Label();
        jComboBoxMethodClassChooseMethod = new javax.swing.JComboBox<>();
        label46 = new java.awt.Label();
        jComboBoxMethodClass = new javax.swing.JComboBox<>();
        label42 = new java.awt.Label();
        jComboBoxProjectMethod = new javax.swing.JComboBox<>();
        label43 = new java.awt.Label();
        jComboBoxReleaseMethod = new javax.swing.JComboBox<>();
        label45 = new java.awt.Label();
        jStartingMethodLineField = new javax.swing.JTextField();
        label51 = new java.awt.Label();
        jEndingMethodLineField = new javax.swing.JTextField();
        label44 = new java.awt.Label();
        jComboBoxMethodPackage = new javax.swing.JComboBox<>();
        label60 = new java.awt.Label();
        label62 = new java.awt.Label();
        jInsertMethodClassButton = new javax.swing.JButton();
        jTestInsertPanel = new javax.swing.JPanel();
        jTestInsertOperationsPanel = new javax.swing.JPanel();
        jNewTestButton = new javax.swing.JButton();
        jNewExecutionButton = new javax.swing.JButton();
        jNewTestDevButton = new javax.swing.JButton();
        jNewTestInsertPanel = new javax.swing.JPanel();
        jTestInsertFieldsPanel = new javax.swing.JPanel();
        label55 = new java.awt.Label();
        jTestNameField = new javax.swing.JTextField();
        label56 = new java.awt.Label();
        jComboBoxProjectTest = new javax.swing.JComboBox<>();
        label59 = new java.awt.Label();
        jTestPathField = new javax.swing.JTextField();
        label89 = new java.awt.Label();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTestDescriptionTextArea = new javax.swing.JTextArea();
        jTestInsertListsPanel = new javax.swing.JPanel();
        jAssignClassTestPanel = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jClassBasedOnProjectList = new javax.swing.JList<>();
        jRefreshButton4 = new javax.swing.JButton();
        jRightArrowButton4 = new javax.swing.JButton();
        jLeftArrowButton4 = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTestedClassList = new javax.swing.JList<>();
        jAssignMethodTestPanel = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jMethodsTestList = new javax.swing.JList<>();
        jRefreshButton3 = new javax.swing.JButton();
        jRightArrowButton3 = new javax.swing.JButton();
        jLeftArrowButton3 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTestedMethodsList = new javax.swing.JList<>();
        jInsertTestButton = new javax.swing.JButton();
        label52 = new java.awt.Label();
        jNewTestDeveloperInsertPanel = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        label63 = new java.awt.Label();
        jComboBoxTestDevChooseProject = new javax.swing.JComboBox<>();
        label64 = new java.awt.Label();
        jComboBoxTestDevChooseTest = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jAuthorsBasedOnProjectList1 = new javax.swing.JList<>();
        jRefreshButton5 = new javax.swing.JButton();
        jRightArrowButton5 = new javax.swing.JButton();
        jLeftArrowButton5 = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        jAssignedTestDevsList = new javax.swing.JList<>();
        jInsertTestDevsButton = new javax.swing.JButton();
        label65 = new java.awt.Label();
        jNewTestExecutionPanel = new javax.swing.JPanel();
        label66 = new java.awt.Label();
        jExecutionInsertFieldsPanel = new javax.swing.JPanel();
        label68 = new java.awt.Label();
        jComboBoxExecutionChooseTest = new javax.swing.JComboBox<>();
        label69 = new java.awt.Label();
        jComboBoxExecutionRelease = new javax.swing.JComboBox<>();
        label70 = new java.awt.Label();
        jComboBoxExecutionResult = new javax.swing.JComboBox<>();
        label71 = new java.awt.Label();
        jExecutionDateField = new com.toedter.calendar.JDateChooser();
        label73 = new java.awt.Label();
        jInsertExecutionButton = new javax.swing.JButton();
        jChangelogInsertPanel = new javax.swing.JPanel();
        jChangelogInsertOperationsPanel = new javax.swing.JPanel();
        jNewChangelogButton = new javax.swing.JButton();
        jNewChangelogDevButton = new javax.swing.JButton();
        jNewChangelogInsertPanel = new javax.swing.JPanel();
        label74 = new java.awt.Label();
        label75 = new java.awt.Label();
        jPanel9 = new javax.swing.JPanel();
        label82 = new java.awt.Label();
        jComboBoxProjectChangelog = new javax.swing.JComboBox<>();
        label77 = new java.awt.Label();
        jComboBoxChangelogRelease = new javax.swing.JComboBox<>();
        label76 = new java.awt.Label();
        jComboBoxChangelogChangeType = new javax.swing.JComboBox<>();
        label78 = new java.awt.Label();
        jComboBoxChangelogChangedObject = new javax.swing.JComboBox<>();
        label79 = new java.awt.Label();
        jComboBoxChangelogPackage = new javax.swing.JComboBox<>();
        label80 = new java.awt.Label();
        jComboBoxChangelogClass = new javax.swing.JComboBox<>();
        label81 = new java.awt.Label();
        jComboBoxChangelogMethod = new javax.swing.JComboBox<>();
        label83 = new java.awt.Label();
        jChangelogOpeningDateField = new com.toedter.calendar.JDateChooser();
        label85 = new java.awt.Label();
        jChangelogClosingDateField = new com.toedter.calendar.JDateChooser();
        label84 = new java.awt.Label();
        jScrollPane15 = new javax.swing.JScrollPane();
        jChangelogDescriptionTextArea = new javax.swing.JTextArea();
        jInsertChangelogButton = new javax.swing.JButton();
        jNewChangelogDeveloperInsertPanel = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        label86 = new java.awt.Label();
        jComboBoxChangelogDevChooseProject = new javax.swing.JComboBox<>();
        label87 = new java.awt.Label();
        jComboBoxChangelogDevChooseLog = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        jAuthorsBasedOnProjectList2 = new javax.swing.JList<>();
        jRefreshButton6 = new javax.swing.JButton();
        jRightArrowButton6 = new javax.swing.JButton();
        jLeftArrowButton6 = new javax.swing.JButton();
        jScrollPane17 = new javax.swing.JScrollPane();
        jAssignedChangelogDevsList = new javax.swing.JList<>();
        jInsertChangelogDevsButton = new javax.swing.JButton();
        label88 = new java.awt.Label();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jRefreshAllMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Repository Software 1.0 - Inserimento nuovi elementi");
        setPreferredSize(new java.awt.Dimension(800, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jAuthorInsertPanel.setLayout(new java.awt.GridBagLayout());

        label5.setAlignment(java.awt.Label.CENTER);
        label5.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        label5.setText("Inserisci autore");
        jAuthorInsertPanel.add(label5, new java.awt.GridBagConstraints());

        jAuthorInsertFieldsPanel.setLayout(new java.awt.GridBagLayout());

        jAuthorEmailField.setPreferredSize(new java.awt.Dimension(150, 25));
        jAuthorEmailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAuthorEmailFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jAuthorInsertFieldsPanel.add(jAuthorEmailField, gridBagConstraints);

        jAuthorSurnameField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jAuthorInsertFieldsPanel.add(jAuthorSurnameField, gridBagConstraints);

        label1.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label1.setText("Nome*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jAuthorInsertFieldsPanel.add(label1, gridBagConstraints);

        jAuthorNameField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jAuthorInsertFieldsPanel.add(jAuthorNameField, gridBagConstraints);

        label2.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label2.setText("Cognome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 12);
        jAuthorInsertFieldsPanel.add(label2, gridBagConstraints);

        label4.setAlignment(java.awt.Label.CENTER);
        label4.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label4.setForeground(new java.awt.Color(255, 0, 0));
        label4.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jAuthorInsertFieldsPanel.add(label4, gridBagConstraints);

        label3.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label3.setText("Email*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 24);
        jAuthorInsertFieldsPanel.add(label3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jAuthorInsertPanel.add(jAuthorInsertFieldsPanel, gridBagConstraints);

        jInsertAuthorButton.setText("Inserisci");
        jInsertAuthorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertAuthorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jAuthorInsertPanel.add(jInsertAuthorButton, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Autore</body></html>", jAuthorInsertPanel);

        jProjectInsertPanel.setLayout(new java.awt.GridBagLayout());

        jProjectInsertOperationsPanel.setMinimumSize(new java.awt.Dimension(400, 25));
        jProjectInsertOperationsPanel.setPreferredSize(new java.awt.Dimension(400, 25));
        jProjectInsertOperationsPanel.setLayout(new java.awt.GridBagLayout());

        jNewProjectButton.setText("Nuovo Progetto");
        jNewProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewProjectButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jProjectInsertOperationsPanel.add(jNewProjectButton, gridBagConstraints);

        jNewReleaseButton.setText("Nuova Release");
        jNewReleaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewReleaseButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jProjectInsertOperationsPanel.add(jNewReleaseButton, gridBagConstraints);

        jNewProjectDevButton.setText("Assegna Progetto");
        jNewProjectDevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewProjectDevButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jProjectInsertOperationsPanel.add(jNewProjectDevButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 0, 0);
        jProjectInsertPanel.add(jProjectInsertOperationsPanel, gridBagConstraints);

        jNewProjectInsertPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Inserisci nuovo progetto"));
        jNewProjectInsertPanel.setMinimumSize(new java.awt.Dimension(400, 275));
        jNewProjectInsertPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        jNewProjectInsertPanel.setLayout(new java.awt.GridBagLayout());

        label7.setAlignment(java.awt.Label.CENTER);
        label7.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label7.setForeground(new java.awt.Color(255, 0, 0));
        label7.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jNewProjectInsertPanel.add(label7, gridBagConstraints);

        label8.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label8.setText("Nome*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jNewProjectInsertPanel.add(label8, gridBagConstraints);

        jProjectNameField.setToolTipText("");
        jProjectNameField.setMinimumSize(new java.awt.Dimension(150, 25));
        jProjectNameField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jNewProjectInsertPanel.add(jProjectNameField, gridBagConstraints);

        label9.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label9.setText("Path*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jNewProjectInsertPanel.add(label9, gridBagConstraints);

        jProjectPathField.setToolTipText("");
        jProjectPathField.setMinimumSize(new java.awt.Dimension(150, 25));
        jProjectPathField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jNewProjectInsertPanel.add(jProjectPathField, gridBagConstraints);

        label10.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label10.setText("Descrizione*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jNewProjectInsertPanel.add(label10, gridBagConstraints);

        label11.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label11.setText("Data Creazione *");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jNewProjectInsertPanel.add(label11, gridBagConstraints);

        jProjectCreationDateField.setMinimumSize(new java.awt.Dimension(150, 25));
        jProjectCreationDateField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jNewProjectInsertPanel.add(jProjectCreationDateField, gridBagConstraints);

        label6.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label6.setText("Data Cancellazione");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jNewProjectInsertPanel.add(label6, gridBagConstraints);

        jProjectCancelationDateField.setMinimumSize(new java.awt.Dimension(150, 25));
        jProjectCancelationDateField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jNewProjectInsertPanel.add(jProjectCancelationDateField, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(166, 76));

        jProjectDescriptionArea.setColumns(20);
        jProjectDescriptionArea.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jProjectDescriptionArea.setRows(5);
        jProjectDescriptionArea.setMinimumSize(new java.awt.Dimension(160, 74));
        jProjectDescriptionArea.setPreferredSize(new java.awt.Dimension(160, 74));
        jScrollPane1.setViewportView(jProjectDescriptionArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jNewProjectInsertPanel.add(jScrollPane1, gridBagConstraints);

        jInsertNewProjectButton.setText("Inserisci");
        jInsertNewProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertNewProjectButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        jNewProjectInsertPanel.add(jInsertNewProjectButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jProjectInsertPanel.add(jNewProjectInsertPanel, gridBagConstraints);

        jNewProjectReleaseInsertPanel.setVisible(false);
        jNewProjectReleaseInsertPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Inserisci nuova release"));
        jNewProjectReleaseInsertPanel.setMinimumSize(new java.awt.Dimension(400, 275));
        jNewProjectReleaseInsertPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        jNewProjectReleaseInsertPanel.setLayout(new java.awt.GridBagLayout());

        label12.setAlignment(java.awt.Label.CENTER);
        label12.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label12.setForeground(new java.awt.Color(255, 0, 0));
        label12.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jNewProjectReleaseInsertPanel.add(label12, gridBagConstraints);

        label13.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label13.setText("Progetto*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jNewProjectReleaseInsertPanel.add(label13, gridBagConstraints);

        jComboBoxProjectReleaseChooseProject.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxProjectReleaseChooseProject.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxProjectReleaseChooseProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProjectReleaseChooseProjectActionPerformed(evt);
            }
        });
        jNewProjectReleaseInsertPanel.add(jComboBoxProjectReleaseChooseProject, new java.awt.GridBagConstraints());
        comboBuilder.buildComboBox(jComboBoxProjectReleaseChooseProject,
            projectBuilder.projectsListQueryBuilder(),false);

        label16.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label16.setText("Versione*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jNewProjectReleaseInsertPanel.add(label16, gridBagConstraints);

        jProjectReleaseNumberField.setToolTipText("");
        jProjectReleaseNumberField.setMinimumSize(new java.awt.Dimension(150, 25));
        jProjectReleaseNumberField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jNewProjectReleaseInsertPanel.add(jProjectReleaseNumberField, gridBagConstraints);

        label15.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label15.setText("Path*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jNewProjectReleaseInsertPanel.add(label15, gridBagConstraints);

        jProjectReleasePathField.setToolTipText("");
        jProjectReleasePathField.setMinimumSize(new java.awt.Dimension(150, 25));
        jProjectReleasePathField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jNewProjectReleaseInsertPanel.add(jProjectReleasePathField, gridBagConstraints);

        label14.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label14.setText("Data Rilascio*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jNewProjectReleaseInsertPanel.add(label14, gridBagConstraints);

        jProjectDateReleaseField.setMinimumSize(new java.awt.Dimension(150, 25));
        jProjectDateReleaseField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jNewProjectReleaseInsertPanel.add(jProjectDateReleaseField, gridBagConstraints);

        jInsertProjectReleaseButton.setText("Inserisci");
        jInsertProjectReleaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertProjectReleaseButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        jNewProjectReleaseInsertPanel.add(jInsertProjectReleaseButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jProjectInsertPanel.add(jNewProjectReleaseInsertPanel, gridBagConstraints);

        jNewProjectDeveloperInsertPanel.setVisible(false);
        jNewProjectDeveloperInsertPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Assegnazione autori"));
        jNewProjectDeveloperInsertPanel.setMinimumSize(new java.awt.Dimension(400, 275));
        jNewProjectDeveloperInsertPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        jNewProjectDeveloperInsertPanel.setLayout(new java.awt.GridBagLayout());

        label18.setAlignment(java.awt.Label.CENTER);
        label18.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label18.setForeground(new java.awt.Color(255, 0, 0));
        label18.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jNewProjectDeveloperInsertPanel.add(label18, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        label17.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label17.setText("Progetto*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jPanel1.add(label17, gridBagConstraints);

        jComboBoxProjectDevChooseProject.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxProjectDevChooseProject.setPreferredSize(new java.awt.Dimension(150, 25));
        jPanel1.add(jComboBoxProjectDevChooseProject, new java.awt.GridBagConstraints());
        comboBuilder.buildComboBox(jComboBoxProjectDevChooseProject,
            projectBuilder.projectsListQueryBuilder(),false);

        jNewProjectDeveloperInsertPanel.add(jPanel1, new java.awt.GridBagConstraints());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setPreferredSize(new java.awt.Dimension(140, 130));

        jAuthorsList.setMinimumSize(new java.awt.Dimension(75, 60));
        jAuthorsList.setPreferredSize(new java.awt.Dimension(300, 300));
        jScrollPane2.setViewportView(jAuthorsList);

        jPanel2.add(jScrollPane2, new java.awt.GridBagConstraints());

        jRefreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/refresh+reload+update+icon-1320191166843452904_16.png"))); // NOI18N
        jRefreshButton.setMinimumSize(new java.awt.Dimension(20, 20));
        jRefreshButton.setPreferredSize(new java.awt.Dimension(20, 20));
        jRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRefreshButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jRefreshButton, gridBagConstraints);

        jRightArrowButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/rightarrowicon.png"))); // NOI18N
        jRightArrowButton.setPreferredSize(new java.awt.Dimension(20, 20));
        jRightArrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRightArrowButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jPanel2.add(jRightArrowButton, gridBagConstraints);

        jLeftArrowButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/leftarrowicon.png"))); // NOI18N
        jLeftArrowButton.setPreferredSize(new java.awt.Dimension(20, 20));
        jLeftArrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLeftArrowButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        jPanel2.add(jLeftArrowButton, gridBagConstraints);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(140, 130));

        jAssignedProjectDevsList.setMinimumSize(new java.awt.Dimension(75, 60));
        jAssignedProjectDevsList.setPreferredSize(new java.awt.Dimension(300, 300));
        jScrollPane3.setViewportView(jAssignedProjectDevsList);

        jPanel2.add(jScrollPane3, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jNewProjectDeveloperInsertPanel.add(jPanel2, gridBagConstraints);

        jInsertProjectDevsButton.setText("Inserisci");
        jInsertProjectDevsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertProjectDevsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jNewProjectDeveloperInsertPanel.add(jInsertProjectDevsButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jProjectInsertPanel.add(jNewProjectDeveloperInsertPanel, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Progetto</body></html>", jProjectInsertPanel);

        jPackageInsertPanel.setLayout(new java.awt.GridBagLayout());

        label19.setAlignment(java.awt.Label.CENTER);
        label19.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        label19.setText("Inserisci package");
        jPackageInsertPanel.add(label19, new java.awt.GridBagConstraints());

        jPackageInsertFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label20.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label20.setText("Nome*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jPackageInsertFieldsPanel.add(label20, gridBagConstraints);

        jPackageNameField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPackageInsertFieldsPanel.add(jPackageNameField, gridBagConstraints);

        label21.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label21.setText("Progetto*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 12);
        jPackageInsertFieldsPanel.add(label21, gridBagConstraints);

        label22.setAlignment(java.awt.Label.CENTER);
        label22.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label22.setForeground(new java.awt.Color(255, 0, 0));
        label22.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jPackageInsertFieldsPanel.add(label22, gridBagConstraints);

        label23.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label23.setText("Contenuto nel package");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 24);
        jPackageInsertFieldsPanel.add(label23, gridBagConstraints);

        jComboBoxProjectPackage.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxProjectPackage.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxProjectPackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProjectPackageActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPackageInsertFieldsPanel.add(jComboBoxProjectPackage, gridBagConstraints);

        jComboBoxReleasePackage.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxReleasePackage.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxReleasePackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxReleasePackageActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPackageInsertFieldsPanel.add(jComboBoxReleasePackage, gridBagConstraints);

        jComboBoxFatherPackage.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxFatherPackage.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        jPackageInsertFieldsPanel.add(jComboBoxFatherPackage, gridBagConstraints);

        label24.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label24.setText("Versione Esordio*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 24);
        jPackageInsertFieldsPanel.add(label24, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPackageInsertPanel.add(jPackageInsertFieldsPanel, gridBagConstraints);

        jInsertPackageButton.setText("Inserisci");
        jInsertPackageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertPackageButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPackageInsertPanel.add(jInsertPackageButton, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Package</body></html>", jPackageInsertPanel);

        jClassInsertPanel.setLayout(new java.awt.GridBagLayout());

        jClassInsertOperationsPanel.setMinimumSize(new java.awt.Dimension(400, 25));
        jClassInsertOperationsPanel.setPreferredSize(new java.awt.Dimension(400, 25));
        jClassInsertOperationsPanel.setLayout(new java.awt.GridBagLayout());

        jNewClassButton.setText("Nuova Classe");
        jNewClassButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewClassButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jClassInsertOperationsPanel.add(jNewClassButton, gridBagConstraints);

        jNewClassDevButton.setText("Assegna Classe");
        jNewClassDevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewClassDevButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jClassInsertOperationsPanel.add(jNewClassDevButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 0, 0);
        jClassInsertPanel.add(jClassInsertOperationsPanel, gridBagConstraints);

        jNewClassInsertPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Inserisci nuova classe"));
        jNewClassInsertPanel.setLayout(new java.awt.GridBagLayout());

        jClassInsertFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label28.setAlignment(java.awt.Label.CENTER);
        label28.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label28.setForeground(new java.awt.Color(255, 0, 0));
        label28.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jClassInsertFieldsPanel.add(label28, gridBagConstraints);

        label33.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label33.setText("Nome*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jClassInsertFieldsPanel.add(label33, gridBagConstraints);

        jClassNameField.setMinimumSize(new java.awt.Dimension(150, 25));
        jClassNameField.setPreferredSize(new java.awt.Dimension(150, 25));
        jClassNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jClassNameFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jClassInsertFieldsPanel.add(jClassNameField, gridBagConstraints);

        label27.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label27.setText("Progetto*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 12);
        jClassInsertFieldsPanel.add(label27, gridBagConstraints);

        jComboBoxProjectClass.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxProjectClass.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxProjectClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProjectClassActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jClassInsertFieldsPanel.add(jComboBoxProjectClass, gridBagConstraints);

        label30.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label30.setText("Versione Esordio*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 24);
        jClassInsertFieldsPanel.add(label30, gridBagConstraints);

        jComboBoxReleaseClass.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxReleaseClass.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxReleaseClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxReleaseClassActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jClassInsertFieldsPanel.add(jComboBoxReleaseClass, gridBagConstraints);

        label29.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label29.setText("Package*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 24);
        jClassInsertFieldsPanel.add(label29, gridBagConstraints);

        jComboBoxClassPackage.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxClassPackage.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxClassPackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxClassPackageActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jClassInsertFieldsPanel.add(jComboBoxClassPackage, gridBagConstraints);

        label26.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label26.setText("Riga Inizio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jClassInsertFieldsPanel.add(label26, gridBagConstraints);

        jStartingClassLineField.setMinimumSize(new java.awt.Dimension(150, 25));
        jStartingClassLineField.setPreferredSize(new java.awt.Dimension(150, 25));
        jStartingClassLineField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStartingClassLineFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jClassInsertFieldsPanel.add(jStartingClassLineField, gridBagConstraints);

        label31.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label31.setText("Innestato nella classe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 24);
        jClassInsertFieldsPanel.add(label31, gridBagConstraints);

        jComboBoxEnclosingClass.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxEnclosingClass.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jClassInsertFieldsPanel.add(jComboBoxEnclosingClass, gridBagConstraints);

        label32.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label32.setText("Modificatore*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 24);
        jClassInsertFieldsPanel.add(label32, gridBagConstraints);

        jComboBoxClassModifiers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "default", "static", "abstract", "final" }));
        jComboBoxClassModifiers.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxClassModifiers.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        jClassInsertFieldsPanel.add(jComboBoxClassModifiers, gridBagConstraints);

        label34.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label34.setText("Visibilità*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 24);
        jClassInsertFieldsPanel.add(label34, gridBagConstraints);

        jComboBoxClassScope.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "default", "public", "private", "protected" }));
        jComboBoxClassScope.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxClassScope.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        jClassInsertFieldsPanel.add(jComboBoxClassScope, gridBagConstraints);

        label36.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label36.setText("Nome Sorgente*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jClassInsertFieldsPanel.add(label36, gridBagConstraints);

        jClassSourceField.setMinimumSize(new java.awt.Dimension(150, 25));
        jClassSourceField.setPreferredSize(new java.awt.Dimension(150, 25));
        jClassSourceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jClassSourceFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        jClassInsertFieldsPanel.add(jClassSourceField, gridBagConstraints);

        label37.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label37.setText("Estende la classe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 24);
        jClassInsertFieldsPanel.add(label37, gridBagConstraints);

        jComboBoxExtendedClass.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxExtendedClass.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        jClassInsertFieldsPanel.add(jComboBoxExtendedClass, gridBagConstraints);

        label35.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label35.setText("Riga Fine");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jClassInsertFieldsPanel.add(label35, gridBagConstraints);

        jEndingClassLineField.setMinimumSize(new java.awt.Dimension(150, 25));
        jEndingClassLineField.setPreferredSize(new java.awt.Dimension(150, 25));
        jEndingClassLineField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEndingClassLineFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        jClassInsertFieldsPanel.add(jEndingClassLineField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jNewClassInsertPanel.add(jClassInsertFieldsPanel, gridBagConstraints);

        jInsertClassButton.setText("Inserisci");
        jInsertClassButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertClassButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jNewClassInsertPanel.add(jInsertClassButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jClassInsertPanel.add(jNewClassInsertPanel, gridBagConstraints);

        jNewClassDeveloperInsertPanel.setVisible(false);
        jNewClassDeveloperInsertPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Assegnazione autori"));
        jNewClassDeveloperInsertPanel.setLayout(new java.awt.GridBagLayout());

        jPanel3.setLayout(new java.awt.GridBagLayout());

        label39.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label39.setText("Progetto*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jPanel3.add(label39, gridBagConstraints);

        jComboBoxClassDevChooseProject.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxClassDevChooseProject.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxClassDevChooseProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxClassDevChooseProjectActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBoxClassDevChooseProject, new java.awt.GridBagConstraints());
        comboBuilder.buildComboBox(jComboBoxProjectDevChooseProject,
            projectBuilder.projectsListQueryBuilder(),false);

        label25.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label25.setText("Classe*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jPanel3.add(label25, gridBagConstraints);

        jComboBoxClassDevChooseClass.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxClassDevChooseClass.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel3.add(jComboBoxClassDevChooseClass, gridBagConstraints);
        comboBuilder.buildComboBox(jComboBoxProjectDevChooseProject,
            projectBuilder.projectsListQueryBuilder(),false);

        jNewClassDeveloperInsertPanel.add(jPanel3, new java.awt.GridBagConstraints());

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jScrollPane4.setPreferredSize(new java.awt.Dimension(140, 130));

        jAuthorsBasedOnProjectList.setMinimumSize(new java.awt.Dimension(75, 60));
        jAuthorsBasedOnProjectList.setPreferredSize(new java.awt.Dimension(300, 300));
        jScrollPane4.setViewportView(jAuthorsBasedOnProjectList);

        jPanel4.add(jScrollPane4, new java.awt.GridBagConstraints());

        jRefreshButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/refresh+reload+update+icon-1320191166843452904_16.png"))); // NOI18N
        jRefreshButton1.setMinimumSize(new java.awt.Dimension(20, 20));
        jRefreshButton1.setPreferredSize(new java.awt.Dimension(20, 20));
        jRefreshButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRefreshButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jRefreshButton1, gridBagConstraints);

        jRightArrowButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/rightarrowicon.png"))); // NOI18N
        jRightArrowButton1.setPreferredSize(new java.awt.Dimension(20, 20));
        jRightArrowButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRightArrowButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jPanel4.add(jRightArrowButton1, gridBagConstraints);

        jLeftArrowButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/leftarrowicon.png"))); // NOI18N
        jLeftArrowButton1.setPreferredSize(new java.awt.Dimension(20, 20));
        jLeftArrowButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLeftArrowButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        jPanel4.add(jLeftArrowButton1, gridBagConstraints);

        jScrollPane5.setPreferredSize(new java.awt.Dimension(140, 130));

        jAssignedClassDevsList.setMinimumSize(new java.awt.Dimension(75, 60));
        jAssignedClassDevsList.setPreferredSize(new java.awt.Dimension(300, 300));
        jScrollPane5.setViewportView(jAssignedClassDevsList);

        jPanel4.add(jScrollPane5, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jNewClassDeveloperInsertPanel.add(jPanel4, gridBagConstraints);

        jInsertClassDevsButton.setText("Inserisci");
        jInsertClassDevsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertClassDevsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jNewClassDeveloperInsertPanel.add(jInsertClassDevsButton, gridBagConstraints);

        label38.setAlignment(java.awt.Label.CENTER);
        label38.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label38.setForeground(new java.awt.Color(255, 0, 0));
        label38.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jNewClassDeveloperInsertPanel.add(label38, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jClassInsertPanel.add(jNewClassDeveloperInsertPanel, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Classe</body></html>", jClassInsertPanel);

        jMethodInsertPanel.setLayout(new java.awt.GridBagLayout());

        jMethodInsertOperationsPanel.setMinimumSize(new java.awt.Dimension(400, 25));
        jMethodInsertOperationsPanel.setPreferredSize(new java.awt.Dimension(400, 25));
        jMethodInsertOperationsPanel.setLayout(new java.awt.GridBagLayout());

        jNewMethodButton.setText("Nuovo Metodo");
        jNewMethodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewMethodButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jMethodInsertOperationsPanel.add(jNewMethodButton, gridBagConstraints);

        jNewMethodDevButton.setText("Assegna Autore");
        jNewMethodDevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewMethodDevButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jMethodInsertOperationsPanel.add(jNewMethodDevButton, gridBagConstraints);

        jNewMethodClassButton.setText("Assegna Metodo");
        jNewMethodClassButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewMethodClassButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jMethodInsertOperationsPanel.add(jNewMethodClassButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 0, 0);
        jMethodInsertPanel.add(jMethodInsertOperationsPanel, gridBagConstraints);

        jNewMethodInsertPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Inserisci nuovo metodo"));
        jNewMethodInsertPanel.setLayout(new java.awt.GridBagLayout());

        label58.setAlignment(java.awt.Label.CENTER);
        label58.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        label58.setText("Inserisci metodo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jNewMethodInsertPanel.add(label58, gridBagConstraints);

        jMethodInsertFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label40.setAlignment(java.awt.Label.CENTER);
        label40.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label40.setForeground(new java.awt.Color(255, 0, 0));
        label40.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jMethodInsertFieldsPanel.add(label40, gridBagConstraints);

        label50.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label50.setText("Nome*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jMethodInsertFieldsPanel.add(label50, gridBagConstraints);

        jMethodNameField.setMinimumSize(new java.awt.Dimension(150, 25));
        jMethodNameField.setPreferredSize(new java.awt.Dimension(150, 25));
        jMethodNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMethodNameFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jMethodInsertFieldsPanel.add(jMethodNameField, gridBagConstraints);

        label47.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label47.setText("Modificatore*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 24);
        jMethodInsertFieldsPanel.add(label47, gridBagConstraints);

        jComboBoxMethodModifiers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "default", "static", "abstract", "final" }));
        jComboBoxMethodModifiers.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxMethodModifiers.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jMethodInsertFieldsPanel.add(jComboBoxMethodModifiers, gridBagConstraints);

        label41.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label41.setText("Parametri");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jMethodInsertFieldsPanel.add(label41, gridBagConstraints);

        jMethodParametersField.setMinimumSize(new java.awt.Dimension(150, 25));
        jMethodParametersField.setPreferredSize(new java.awt.Dimension(150, 25));
        jMethodParametersField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMethodParametersFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jMethodInsertFieldsPanel.add(jMethodParametersField, gridBagConstraints);

        label48.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label48.setText("Visibilità*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 24);
        jMethodInsertFieldsPanel.add(label48, gridBagConstraints);

        jComboBoxMethodScope.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "default", "public", "private", "protected" }));
        jComboBoxMethodScope.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxMethodScope.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jMethodInsertFieldsPanel.add(jComboBoxMethodScope, gridBagConstraints);

        label49.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label49.setText("Tipo Ritorno*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jMethodInsertFieldsPanel.add(label49, gridBagConstraints);

        jMethodReturnTypeField.setMinimumSize(new java.awt.Dimension(150, 25));
        jMethodReturnTypeField.setPreferredSize(new java.awt.Dimension(150, 25));
        jMethodReturnTypeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMethodReturnTypeFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        jMethodInsertFieldsPanel.add(jMethodReturnTypeField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jNewMethodInsertPanel.add(jMethodInsertFieldsPanel, gridBagConstraints);

        jInsertMethodButton.setText("Inserisci");
        jInsertMethodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertMethodButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jNewMethodInsertPanel.add(jInsertMethodButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jMethodInsertPanel.add(jNewMethodInsertPanel, gridBagConstraints);

        jNewMethodDeveloperInsertPanel.setVisible(false);
        jNewMethodDeveloperInsertPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Assegnazione autori"));
        jNewMethodDeveloperInsertPanel.setLayout(new java.awt.GridBagLayout());

        jPanel5.setLayout(new java.awt.GridBagLayout());

        label53.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label53.setText("Metodo*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jPanel5.add(label53, gridBagConstraints);

        jComboBoxMethodDevChooseMethod.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxMethodDevChooseMethod.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel5.add(jComboBoxMethodDevChooseMethod, gridBagConstraints);
        comboBuilder.buildComboBox(jComboBoxProjectDevChooseProject,
            projectBuilder.projectsListQueryBuilder(),false);

        jNewMethodDeveloperInsertPanel.add(jPanel5, new java.awt.GridBagConstraints());

        jPanel6.setLayout(new java.awt.GridBagLayout());

        jScrollPane6.setPreferredSize(new java.awt.Dimension(140, 130));

        jAuthorsList2.setMinimumSize(new java.awt.Dimension(75, 60));
        jAuthorsList2.setPreferredSize(new java.awt.Dimension(300, 300));
        jScrollPane6.setViewportView(jAuthorsList2);

        jPanel6.add(jScrollPane6, new java.awt.GridBagConstraints());

        jRefreshButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/refresh+reload+update+icon-1320191166843452904_16.png"))); // NOI18N
        jRefreshButton2.setMinimumSize(new java.awt.Dimension(20, 20));
        jRefreshButton2.setPreferredSize(new java.awt.Dimension(20, 20));
        jRefreshButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRefreshButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel6.add(jRefreshButton2, gridBagConstraints);

        jRightArrowButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/rightarrowicon.png"))); // NOI18N
        jRightArrowButton2.setPreferredSize(new java.awt.Dimension(20, 20));
        jRightArrowButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRightArrowButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jPanel6.add(jRightArrowButton2, gridBagConstraints);

        jLeftArrowButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/leftarrowicon.png"))); // NOI18N
        jLeftArrowButton2.setPreferredSize(new java.awt.Dimension(20, 20));
        jLeftArrowButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLeftArrowButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        jPanel6.add(jLeftArrowButton2, gridBagConstraints);

        jScrollPane7.setPreferredSize(new java.awt.Dimension(140, 130));

        jAssignedMethodDevsList.setMinimumSize(new java.awt.Dimension(75, 60));
        jAssignedMethodDevsList.setPreferredSize(new java.awt.Dimension(300, 300));
        jScrollPane7.setViewportView(jAssignedMethodDevsList);

        jPanel6.add(jScrollPane7, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jNewMethodDeveloperInsertPanel.add(jPanel6, gridBagConstraints);

        jInsertMethodDevsButton.setText("Inserisci");
        jInsertMethodDevsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertMethodDevsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jNewMethodDeveloperInsertPanel.add(jInsertMethodDevsButton, gridBagConstraints);

        label54.setAlignment(java.awt.Label.CENTER);
        label54.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label54.setForeground(new java.awt.Color(255, 0, 0));
        label54.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jNewMethodDeveloperInsertPanel.add(label54, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jMethodInsertPanel.add(jNewMethodDeveloperInsertPanel, gridBagConstraints);

        jNewMethodClassInsertPanel.setVisible(false);
        jNewMethodClassInsertPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Assegnazione classi"));
        jNewMethodClassInsertPanel.setLayout(new java.awt.GridBagLayout());

        jNewMethodClassInsertFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label57.setAlignment(java.awt.Label.CENTER);
        label57.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label57.setForeground(new java.awt.Color(255, 0, 0));
        label57.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jNewMethodClassInsertFieldsPanel.add(label57, gridBagConstraints);

        label61.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label61.setText("Metodo*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jNewMethodClassInsertFieldsPanel.add(label61, gridBagConstraints);

        jComboBoxMethodClassChooseMethod.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxMethodClassChooseMethod.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jNewMethodClassInsertFieldsPanel.add(jComboBoxMethodClassChooseMethod, gridBagConstraints);
        comboBuilder.buildComboBox(jComboBoxProjectDevChooseProject,
            projectBuilder.projectsListQueryBuilder(),false);

        label46.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label46.setText("Classe*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 24);
        jNewMethodClassInsertFieldsPanel.add(label46, gridBagConstraints);

        jComboBoxMethodClass.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxMethodClass.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jNewMethodClassInsertFieldsPanel.add(jComboBoxMethodClass, gridBagConstraints);

        label42.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label42.setText("Progetto*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 12);
        jNewMethodClassInsertFieldsPanel.add(label42, gridBagConstraints);

        jComboBoxProjectMethod.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxProjectMethod.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxProjectMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProjectMethodActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jNewMethodClassInsertFieldsPanel.add(jComboBoxProjectMethod, gridBagConstraints);

        label43.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label43.setText("Versione Esordio*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 24);
        jNewMethodClassInsertFieldsPanel.add(label43, gridBagConstraints);

        jComboBoxReleaseMethod.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxReleaseMethod.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxReleaseMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxReleaseMethodActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jNewMethodClassInsertFieldsPanel.add(jComboBoxReleaseMethod, gridBagConstraints);

        label45.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label45.setText("Riga Inizio*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jNewMethodClassInsertFieldsPanel.add(label45, gridBagConstraints);

        jStartingMethodLineField.setMinimumSize(new java.awt.Dimension(150, 25));
        jStartingMethodLineField.setPreferredSize(new java.awt.Dimension(150, 25));
        jStartingMethodLineField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStartingMethodLineFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        jNewMethodClassInsertFieldsPanel.add(jStartingMethodLineField, gridBagConstraints);

        label51.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label51.setText("Riga Fine*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jNewMethodClassInsertFieldsPanel.add(label51, gridBagConstraints);

        jEndingMethodLineField.setMinimumSize(new java.awt.Dimension(150, 25));
        jEndingMethodLineField.setPreferredSize(new java.awt.Dimension(150, 25));
        jEndingMethodLineField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEndingMethodLineFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        jNewMethodClassInsertFieldsPanel.add(jEndingMethodLineField, gridBagConstraints);

        label44.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label44.setText("Package*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 24);
        jNewMethodClassInsertFieldsPanel.add(label44, gridBagConstraints);

        jComboBoxMethodPackage.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxMethodPackage.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxMethodPackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMethodPackageActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jNewMethodClassInsertFieldsPanel.add(jComboBoxMethodPackage, gridBagConstraints);

        label60.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label60.setText("Seleziona metodo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jNewMethodClassInsertFieldsPanel.add(label60, gridBagConstraints);

        label62.setFont(new java.awt.Font("Calibri", 1, 10)); // NOI18N
        label62.setText("Scegli classe a cui associarlo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jNewMethodClassInsertFieldsPanel.add(label62, gridBagConstraints);

        jNewMethodClassInsertPanel.add(jNewMethodClassInsertFieldsPanel, new java.awt.GridBagConstraints());

        jInsertMethodClassButton.setText("Inserisci");
        jInsertMethodClassButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertMethodClassButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jNewMethodClassInsertPanel.add(jInsertMethodClassButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jMethodInsertPanel.add(jNewMethodClassInsertPanel, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Metodo</body></html>", jMethodInsertPanel);

        jTestInsertPanel.setLayout(new java.awt.GridBagLayout());

        jTestInsertOperationsPanel.setMinimumSize(new java.awt.Dimension(400, 25));
        jTestInsertOperationsPanel.setPreferredSize(new java.awt.Dimension(400, 25));
        jTestInsertOperationsPanel.setLayout(new java.awt.GridBagLayout());

        jNewTestButton.setText("Nuovo Test");
        jNewTestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewTestButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jTestInsertOperationsPanel.add(jNewTestButton, gridBagConstraints);

        jNewExecutionButton.setText("Aggiungi Esecuzione");
        jNewExecutionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewExecutionButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jTestInsertOperationsPanel.add(jNewExecutionButton, gridBagConstraints);

        jNewTestDevButton.setText("Assegna Test");
        jNewTestDevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewTestDevButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jTestInsertOperationsPanel.add(jNewTestDevButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 0, 0);
        jTestInsertPanel.add(jTestInsertOperationsPanel, gridBagConstraints);

        jNewTestInsertPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Inserisci nuovo test"));
        jNewTestInsertPanel.setLayout(new java.awt.GridBagLayout());

        jTestInsertFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label55.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label55.setText("Nome*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jTestInsertFieldsPanel.add(label55, gridBagConstraints);

        jTestNameField.setMinimumSize(new java.awt.Dimension(150, 25));
        jTestNameField.setPreferredSize(new java.awt.Dimension(150, 25));
        jTestNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTestNameFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jTestInsertFieldsPanel.add(jTestNameField, gridBagConstraints);

        label56.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label56.setText("Progetto*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 12);
        jTestInsertFieldsPanel.add(label56, gridBagConstraints);

        jComboBoxProjectTest.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxProjectTest.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxProjectTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProjectTestActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jTestInsertFieldsPanel.add(jComboBoxProjectTest, gridBagConstraints);

        label59.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label59.setText("Path*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jTestInsertFieldsPanel.add(label59, gridBagConstraints);

        jTestPathField.setMinimumSize(new java.awt.Dimension(150, 25));
        jTestPathField.setPreferredSize(new java.awt.Dimension(150, 25));
        jTestPathField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTestPathFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jTestInsertFieldsPanel.add(jTestPathField, gridBagConstraints);

        label89.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label89.setText("Descrizione*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jTestInsertFieldsPanel.add(label89, gridBagConstraints);

        jScrollPane18.setMinimumSize(new java.awt.Dimension(300, 100));
        jScrollPane18.setPreferredSize(new java.awt.Dimension(300, 100));

        jTestDescriptionTextArea.setColumns(20);
        jTestDescriptionTextArea.setRows(5);
        jScrollPane18.setViewportView(jTestDescriptionTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        jTestInsertFieldsPanel.add(jScrollPane18, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jNewTestInsertPanel.add(jTestInsertFieldsPanel, gridBagConstraints);

        jTestInsertListsPanel.setLayout(new java.awt.GridBagLayout());

        jAssignClassTestPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Classi testate"));
        jAssignClassTestPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane10.setPreferredSize(new java.awt.Dimension(140, 130));

        jClassBasedOnProjectList.setMinimumSize(new java.awt.Dimension(75, 60));
        jClassBasedOnProjectList.setPreferredSize(new java.awt.Dimension(300, 300));
        jScrollPane10.setViewportView(jClassBasedOnProjectList);

        jAssignClassTestPanel.add(jScrollPane10, new java.awt.GridBagConstraints());

        jRefreshButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/refresh+reload+update+icon-1320191166843452904_16.png"))); // NOI18N
        jRefreshButton4.setMinimumSize(new java.awt.Dimension(20, 20));
        jRefreshButton4.setPreferredSize(new java.awt.Dimension(20, 20));
        jRefreshButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRefreshButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jAssignClassTestPanel.add(jRefreshButton4, gridBagConstraints);

        jRightArrowButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/rightarrowicon.png"))); // NOI18N
        jRightArrowButton4.setPreferredSize(new java.awt.Dimension(20, 20));
        jRightArrowButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRightArrowButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jAssignClassTestPanel.add(jRightArrowButton4, gridBagConstraints);

        jLeftArrowButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/leftarrowicon.png"))); // NOI18N
        jLeftArrowButton4.setPreferredSize(new java.awt.Dimension(20, 20));
        jLeftArrowButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLeftArrowButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        jAssignClassTestPanel.add(jLeftArrowButton4, gridBagConstraints);

        jScrollPane11.setPreferredSize(new java.awt.Dimension(140, 130));

        jTestedClassList.setMinimumSize(new java.awt.Dimension(75, 60));
        jTestedClassList.setPreferredSize(new java.awt.Dimension(300, 300));
        jScrollPane11.setViewportView(jTestedClassList);

        jAssignClassTestPanel.add(jScrollPane11, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jTestInsertListsPanel.add(jAssignClassTestPanel, gridBagConstraints);

        jAssignMethodTestPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Metodi testati"));
        jAssignMethodTestPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane8.setPreferredSize(new java.awt.Dimension(140, 130));

        jMethodsTestList.setMinimumSize(new java.awt.Dimension(75, 60));
        jMethodsTestList.setPreferredSize(new java.awt.Dimension(300, 300));
        jScrollPane8.setViewportView(jMethodsTestList);

        jAssignMethodTestPanel.add(jScrollPane8, new java.awt.GridBagConstraints());

        jRefreshButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/refresh+reload+update+icon-1320191166843452904_16.png"))); // NOI18N
        jRefreshButton3.setMinimumSize(new java.awt.Dimension(20, 20));
        jRefreshButton3.setPreferredSize(new java.awt.Dimension(20, 20));
        jRefreshButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRefreshButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jAssignMethodTestPanel.add(jRefreshButton3, gridBagConstraints);

        jRightArrowButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/rightarrowicon.png"))); // NOI18N
        jRightArrowButton3.setPreferredSize(new java.awt.Dimension(20, 20));
        jRightArrowButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRightArrowButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jAssignMethodTestPanel.add(jRightArrowButton3, gridBagConstraints);

        jLeftArrowButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/leftarrowicon.png"))); // NOI18N
        jLeftArrowButton3.setPreferredSize(new java.awt.Dimension(20, 20));
        jLeftArrowButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLeftArrowButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        jAssignMethodTestPanel.add(jLeftArrowButton3, gridBagConstraints);

        jScrollPane9.setPreferredSize(new java.awt.Dimension(140, 130));

        jTestedMethodsList.setMinimumSize(new java.awt.Dimension(75, 60));
        jTestedMethodsList.setPreferredSize(new java.awt.Dimension(300, 300));
        jScrollPane9.setViewportView(jTestedMethodsList);

        jAssignMethodTestPanel.add(jScrollPane9, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 21, 0, 0);
        jTestInsertListsPanel.add(jAssignMethodTestPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jNewTestInsertPanel.add(jTestInsertListsPanel, gridBagConstraints);

        jInsertTestButton.setText("Inserisci");
        jInsertTestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertTestButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(17, 0, 0, 0);
        jNewTestInsertPanel.add(jInsertTestButton, gridBagConstraints);

        label52.setAlignment(java.awt.Label.CENTER);
        label52.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label52.setForeground(new java.awt.Color(255, 0, 0));
        label52.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jNewTestInsertPanel.add(label52, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jTestInsertPanel.add(jNewTestInsertPanel, gridBagConstraints);

        jNewTestDeveloperInsertPanel.setVisible(false);
        jNewTestDeveloperInsertPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Assegnazione autori"));
        jNewTestDeveloperInsertPanel.setLayout(new java.awt.GridBagLayout());

        jPanel7.setLayout(new java.awt.GridBagLayout());

        label63.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label63.setText("Progetto*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jPanel7.add(label63, gridBagConstraints);

        jComboBoxTestDevChooseProject.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxTestDevChooseProject.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxTestDevChooseProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTestDevChooseProjectActionPerformed(evt);
            }
        });
        jPanel7.add(jComboBoxTestDevChooseProject, new java.awt.GridBagConstraints());
        comboBuilder.buildComboBox(jComboBoxProjectDevChooseProject,
            projectBuilder.projectsListQueryBuilder(),false);

        label64.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label64.setText("Test*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jPanel7.add(label64, gridBagConstraints);

        jComboBoxTestDevChooseTest.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxTestDevChooseTest.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel7.add(jComboBoxTestDevChooseTest, gridBagConstraints);
        comboBuilder.buildComboBox(jComboBoxProjectDevChooseProject,
            projectBuilder.projectsListQueryBuilder(),false);

        jNewTestDeveloperInsertPanel.add(jPanel7, new java.awt.GridBagConstraints());

        jPanel8.setLayout(new java.awt.GridBagLayout());

        jScrollPane12.setPreferredSize(new java.awt.Dimension(140, 130));

        jAuthorsBasedOnProjectList1.setMinimumSize(new java.awt.Dimension(75, 60));
        jAuthorsBasedOnProjectList1.setPreferredSize(new java.awt.Dimension(300, 300));
        jScrollPane12.setViewportView(jAuthorsBasedOnProjectList1);

        jPanel8.add(jScrollPane12, new java.awt.GridBagConstraints());

        jRefreshButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/refresh+reload+update+icon-1320191166843452904_16.png"))); // NOI18N
        jRefreshButton5.setMinimumSize(new java.awt.Dimension(20, 20));
        jRefreshButton5.setPreferredSize(new java.awt.Dimension(20, 20));
        jRefreshButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRefreshButton5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel8.add(jRefreshButton5, gridBagConstraints);

        jRightArrowButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/rightarrowicon.png"))); // NOI18N
        jRightArrowButton5.setPreferredSize(new java.awt.Dimension(20, 20));
        jRightArrowButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRightArrowButton5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jPanel8.add(jRightArrowButton5, gridBagConstraints);

        jLeftArrowButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/leftarrowicon.png"))); // NOI18N
        jLeftArrowButton5.setPreferredSize(new java.awt.Dimension(20, 20));
        jLeftArrowButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLeftArrowButton5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        jPanel8.add(jLeftArrowButton5, gridBagConstraints);

        jScrollPane13.setPreferredSize(new java.awt.Dimension(140, 130));

        jAssignedTestDevsList.setMinimumSize(new java.awt.Dimension(75, 60));
        jAssignedTestDevsList.setPreferredSize(new java.awt.Dimension(300, 300));
        jScrollPane13.setViewportView(jAssignedTestDevsList);

        jPanel8.add(jScrollPane13, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jNewTestDeveloperInsertPanel.add(jPanel8, gridBagConstraints);

        jInsertTestDevsButton.setText("Inserisci");
        jInsertTestDevsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertTestDevsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jNewTestDeveloperInsertPanel.add(jInsertTestDevsButton, gridBagConstraints);

        label65.setAlignment(java.awt.Label.CENTER);
        label65.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label65.setForeground(new java.awt.Color(255, 0, 0));
        label65.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jNewTestDeveloperInsertPanel.add(label65, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jTestInsertPanel.add(jNewTestDeveloperInsertPanel, gridBagConstraints);

        jNewTestExecutionPanel.setVisible(false);
        jNewTestExecutionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Inserisci nuova esecuzione"));
        jNewTestExecutionPanel.setLayout(new java.awt.GridBagLayout());

        label66.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        label66.setText("Inserisci esecuzione");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jNewTestExecutionPanel.add(label66, gridBagConstraints);

        jExecutionInsertFieldsPanel.setLayout(new java.awt.GridBagLayout());

        label68.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label68.setText("Test da eseguire*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jExecutionInsertFieldsPanel.add(label68, gridBagConstraints);

        jComboBoxExecutionChooseTest.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxExecutionChooseTest.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxExecutionChooseTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxExecutionChooseTestActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 0);
        jExecutionInsertFieldsPanel.add(jComboBoxExecutionChooseTest, gridBagConstraints);

        label69.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label69.setText("Versione da testare*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jExecutionInsertFieldsPanel.add(label69, gridBagConstraints);

        jComboBoxExecutionRelease.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxExecutionRelease.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 0, 0);
        jExecutionInsertFieldsPanel.add(jComboBoxExecutionRelease, gridBagConstraints);

        label70.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label70.setText("Esito*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jExecutionInsertFieldsPanel.add(label70, gridBagConstraints);

        jComboBoxExecutionResult.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Riuscito", "Fallito" }));
        jComboBoxExecutionResult.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxExecutionResult.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jExecutionInsertFieldsPanel.add(jComboBoxExecutionResult, gridBagConstraints);

        label71.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label71.setText("Data Esecuzione*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jExecutionInsertFieldsPanel.add(label71, gridBagConstraints);

        jExecutionDateField.setMinimumSize(new java.awt.Dimension(150, 25));
        jExecutionDateField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jExecutionInsertFieldsPanel.add(jExecutionDateField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jNewTestExecutionPanel.add(jExecutionInsertFieldsPanel, gridBagConstraints);

        label73.setAlignment(java.awt.Label.CENTER);
        label73.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label73.setForeground(new java.awt.Color(255, 0, 0));
        label73.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jNewTestExecutionPanel.add(label73, gridBagConstraints);

        jInsertExecutionButton.setText("Inserisci");
        jInsertExecutionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertExecutionButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 2;
        jNewTestExecutionPanel.add(jInsertExecutionButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jTestInsertPanel.add(jNewTestExecutionPanel, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Test</body></html>", jTestInsertPanel);

        jChangelogInsertPanel.setLayout(new java.awt.GridBagLayout());

        jChangelogInsertOperationsPanel.setLayout(new java.awt.GridBagLayout());

        jNewChangelogButton.setText("Nuovo Changelog");
        jNewChangelogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewChangelogButtonActionPerformed(evt);
            }
        });
        jChangelogInsertOperationsPanel.add(jNewChangelogButton, new java.awt.GridBagConstraints());

        jNewChangelogDevButton.setText("Assegna autori");
        jNewChangelogDevButton.setToolTipText("");
        jNewChangelogDevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewChangelogDevButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jChangelogInsertOperationsPanel.add(jNewChangelogDevButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 0, 0);
        jChangelogInsertPanel.add(jChangelogInsertOperationsPanel, gridBagConstraints);

        jNewChangelogInsertPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Inserisci nuovo log"));
        jNewChangelogInsertPanel.setLayout(new java.awt.GridBagLayout());

        label74.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        label74.setText("Inserisci log");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jNewChangelogInsertPanel.add(label74, gridBagConstraints);

        label75.setAlignment(java.awt.Label.CENTER);
        label75.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label75.setForeground(new java.awt.Color(255, 0, 0));
        label75.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jNewChangelogInsertPanel.add(label75, gridBagConstraints);

        jPanel9.setLayout(new java.awt.GridBagLayout());

        label82.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label82.setText("Progetto*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel9.add(label82, gridBagConstraints);

        jComboBoxProjectChangelog.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxProjectChangelog.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxProjectChangelog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProjectChangelogActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 0);
        jPanel9.add(jComboBoxProjectChangelog, gridBagConstraints);

        label77.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label77.setText("Versione*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel9.add(label77, gridBagConstraints);

        jComboBoxChangelogRelease.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxChangelogRelease.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxChangelogRelease.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxChangelogReleaseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 0);
        jPanel9.add(jComboBoxChangelogRelease, gridBagConstraints);

        label76.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label76.setText("Tipo Modifica*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel9.add(label76, gridBagConstraints);

        jComboBoxChangelogChangeType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Modifica", "Cancellazione" }));
        jComboBoxChangelogChangeType.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxChangelogChangeType.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 0);
        jPanel9.add(jComboBoxChangelogChangeType, gridBagConstraints);

        label78.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label78.setText("Tipo Modificato*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel9.add(label78, gridBagConstraints);

        jComboBoxChangelogChangedObject.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Package", "Classe", "Metodo" }));
        jComboBoxChangelogChangedObject.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxChangelogChangedObject.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxChangelogChangedObject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxChangelogChangedObjectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 0);
        jPanel9.add(jComboBoxChangelogChangedObject, gridBagConstraints);

        label79.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label79.setText("Package*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel9.add(label79, gridBagConstraints);

        jComboBoxChangelogPackage.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxChangelogPackage.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 0);
        jPanel9.add(jComboBoxChangelogPackage, gridBagConstraints);

        label80.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label80.setText("Classe*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel9.add(label80, gridBagConstraints);

        jComboBoxChangelogClass.setEnabled(false);
        jComboBoxChangelogClass.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxChangelogClass.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 0);
        jPanel9.add(jComboBoxChangelogClass, gridBagConstraints);

        label81.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label81.setText("Metodo*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel9.add(label81, gridBagConstraints);

        jComboBoxChangelogMethod.setEnabled(false);
        jComboBoxChangelogMethod.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxChangelogMethod.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 0);
        jPanel9.add(jComboBoxChangelogMethod, gridBagConstraints);

        label83.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label83.setText("Data Apertura*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel9.add(label83, gridBagConstraints);

        jChangelogOpeningDateField.setMinimumSize(new java.awt.Dimension(150, 25));
        jChangelogOpeningDateField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel9.add(jChangelogOpeningDateField, gridBagConstraints);

        label85.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label85.setText("Data Chiusura");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel9.add(label85, gridBagConstraints);

        jChangelogClosingDateField.setMinimumSize(new java.awt.Dimension(150, 25));
        jChangelogClosingDateField.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel9.add(jChangelogClosingDateField, gridBagConstraints);

        label84.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label84.setText("Descrizione*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel9.add(label84, gridBagConstraints);

        jScrollPane15.setMinimumSize(new java.awt.Dimension(300, 100));
        jScrollPane15.setPreferredSize(new java.awt.Dimension(300, 100));

        jChangelogDescriptionTextArea.setColumns(20);
        jChangelogDescriptionTextArea.setRows(5);
        jScrollPane15.setViewportView(jChangelogDescriptionTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 0);
        jPanel9.add(jScrollPane15, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jNewChangelogInsertPanel.add(jPanel9, gridBagConstraints);

        jInsertChangelogButton.setText("Inserisci");
        jInsertChangelogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertChangelogButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jNewChangelogInsertPanel.add(jInsertChangelogButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jChangelogInsertPanel.add(jNewChangelogInsertPanel, gridBagConstraints);

        jNewChangelogDeveloperInsertPanel.setVisible(false);
        jNewChangelogDeveloperInsertPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Assegnazione autori"));
        jNewChangelogDeveloperInsertPanel.setLayout(new java.awt.GridBagLayout());

        jPanel10.setLayout(new java.awt.GridBagLayout());

        label86.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label86.setText("Progetto*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jPanel10.add(label86, gridBagConstraints);

        jComboBoxChangelogDevChooseProject.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxChangelogDevChooseProject.setPreferredSize(new java.awt.Dimension(150, 25));
        jComboBoxChangelogDevChooseProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxChangelogDevChooseProjectActionPerformed(evt);
            }
        });
        jPanel10.add(jComboBoxChangelogDevChooseProject, new java.awt.GridBagConstraints());
        comboBuilder.buildComboBox(jComboBoxProjectDevChooseProject,
            projectBuilder.projectsListQueryBuilder(),false);

        label87.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        label87.setText("Log*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 16);
        jPanel10.add(label87, gridBagConstraints);

        jComboBoxChangelogDevChooseLog.setMinimumSize(new java.awt.Dimension(150, 25));
        jComboBoxChangelogDevChooseLog.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel10.add(jComboBoxChangelogDevChooseLog, gridBagConstraints);
        comboBuilder.buildComboBox(jComboBoxProjectDevChooseProject,
            projectBuilder.projectsListQueryBuilder(),false);

        jNewChangelogDeveloperInsertPanel.add(jPanel10, new java.awt.GridBagConstraints());

        jPanel11.setLayout(new java.awt.GridBagLayout());

        jScrollPane16.setPreferredSize(new java.awt.Dimension(140, 130));

        jAuthorsBasedOnProjectList2.setMinimumSize(new java.awt.Dimension(75, 60));
        jAuthorsBasedOnProjectList2.setPreferredSize(new java.awt.Dimension(300, 300));
        jScrollPane16.setViewportView(jAuthorsBasedOnProjectList2);

        jPanel11.add(jScrollPane16, new java.awt.GridBagConstraints());

        jRefreshButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/refresh+reload+update+icon-1320191166843452904_16.png"))); // NOI18N
        jRefreshButton6.setMinimumSize(new java.awt.Dimension(20, 20));
        jRefreshButton6.setPreferredSize(new java.awt.Dimension(20, 20));
        jRefreshButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRefreshButton6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel11.add(jRefreshButton6, gridBagConstraints);

        jRightArrowButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/rightarrowicon.png"))); // NOI18N
        jRightArrowButton6.setPreferredSize(new java.awt.Dimension(20, 20));
        jRightArrowButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRightArrowButton6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jPanel11.add(jRightArrowButton6, gridBagConstraints);

        jLeftArrowButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/repository/images/leftarrowicon.png"))); // NOI18N
        jLeftArrowButton6.setPreferredSize(new java.awt.Dimension(20, 20));
        jLeftArrowButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLeftArrowButton6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        jPanel11.add(jLeftArrowButton6, gridBagConstraints);

        jScrollPane17.setPreferredSize(new java.awt.Dimension(140, 130));

        jAssignedChangelogDevsList.setMinimumSize(new java.awt.Dimension(75, 60));
        jAssignedChangelogDevsList.setPreferredSize(new java.awt.Dimension(300, 300));
        jScrollPane17.setViewportView(jAssignedChangelogDevsList);

        jPanel11.add(jScrollPane17, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jNewChangelogDeveloperInsertPanel.add(jPanel11, gridBagConstraints);

        jInsertChangelogDevsButton.setText("Inserisci");
        jInsertChangelogDevsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertChangelogDevsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jNewChangelogDeveloperInsertPanel.add(jInsertChangelogDevsButton, gridBagConstraints);

        label88.setAlignment(java.awt.Label.CENTER);
        label88.setFont(new java.awt.Font("Calibri", 1, 8)); // NOI18N
        label88.setForeground(new java.awt.Color(255, 0, 0));
        label88.setText("I campi contrassegnati da (*) sono obbligatori");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 9, 0);
        jNewChangelogDeveloperInsertPanel.add(label88, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jChangelogInsertPanel.add(jNewChangelogDeveloperInsertPanel, gridBagConstraints);

        jTabbedPane1.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Changelog\n</body></html>", jChangelogInsertPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        jMenu1.setText("Opzioni");

        jRefreshAllMenuItem.setText("Aggiorna..");
        jRefreshAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRefreshAllMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(jRefreshAllMenuItem);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jAuthorEmailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAuthorEmailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jAuthorEmailFieldActionPerformed

    private void jNewProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewProjectButtonActionPerformed
        jNewProjectReleaseInsertPanel.setVisible(false);
        jNewProjectDeveloperInsertPanel.setVisible(false);
        jNewProjectInsertPanel.setVisible(true);
    }//GEN-LAST:event_jNewProjectButtonActionPerformed

    private void jNewReleaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewReleaseButtonActionPerformed
        jNewProjectDeveloperInsertPanel.setVisible(false);
        jNewProjectInsertPanel.setVisible(false);
        jNewProjectReleaseInsertPanel.setVisible(true);
    }//GEN-LAST:event_jNewReleaseButtonActionPerformed

    private void jNewProjectDevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewProjectDevButtonActionPerformed
        jNewProjectInsertPanel.setVisible(false);
        jNewProjectReleaseInsertPanel.setVisible(false);
        jNewProjectDeveloperInsertPanel.setVisible(true);
    }//GEN-LAST:event_jNewProjectDevButtonActionPerformed

    private void jRightArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRightArrowButtonActionPerformed
        String value = jAuthorsList.getSelectedValue();
        if (!value.isEmpty()) {
            AuthorProjectModel.removeElementAt(jAuthorsList.getSelectedIndex());
            AssignedAuthorProjectModel.addElement(value);
        }
    }//GEN-LAST:event_jRightArrowButtonActionPerformed

    private void jLeftArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLeftArrowButtonActionPerformed
        String value = jAssignedProjectDevsList.getSelectedValue();
        if (!value.isEmpty()) {
            AssignedAuthorProjectModel.removeElementAt(jAssignedProjectDevsList.getSelectedIndex());
            AuthorProjectModel.addElement(value);
        }
    }//GEN-LAST:event_jLeftArrowButtonActionPerformed

    private void jRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRefreshButtonActionPerformed
        AuthorProjectModel.clear();
        AssignedAuthorProjectModel.clear();
        listBuilder.buildAuthorList(AuthorProjectModel, authorBuilder.authorsListQueryBuilder());
    }//GEN-LAST:event_jRefreshButtonActionPerformed

    private void jInsertProjectReleaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertProjectReleaseButtonActionPerformed
        ProjectQueryExecutor project=new ProjectQueryExecutor();
        project.insertNewProjectRelease(this);
    }//GEN-LAST:event_jInsertProjectReleaseButtonActionPerformed

    private void jComboBoxProjectPackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProjectPackageActionPerformed
        if(checkComboChanges){
            refreshReleases(getPrimaryKey(jComboBoxProjectPackage),jComboBoxReleasePackage);
            refreshPackages(getPrimaryKey(jComboBoxReleasePackage),jComboBoxFatherPackage);
        }
    }//GEN-LAST:event_jComboBoxProjectPackageActionPerformed

    private void jComboBoxReleasePackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxReleasePackageActionPerformed
        if(checkComboChanges){
            refreshPackages(getPrimaryKey(jComboBoxReleasePackage),jComboBoxFatherPackage);
        }
    }//GEN-LAST:event_jComboBoxReleasePackageActionPerformed

    private void jNewClassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewClassButtonActionPerformed
        jNewClassDeveloperInsertPanel.setVisible(false);
        jNewClassInsertPanel.setVisible(true);
    }//GEN-LAST:event_jNewClassButtonActionPerformed

    private void jNewClassDevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewClassDevButtonActionPerformed
        jNewClassInsertPanel.setVisible(false);
        jNewClassDeveloperInsertPanel.setVisible(true);
    }//GEN-LAST:event_jNewClassDevButtonActionPerformed

    private void jComboBoxProjectClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProjectClassActionPerformed
        if(checkComboChanges){
            refreshReleases(getPrimaryKey(jComboBoxProjectClass)
                    ,jComboBoxReleaseClass);
            refreshPackages(getPrimaryKey(jComboBoxReleaseClass),jComboBoxClassPackage);
            refreshNestedClasses(getPrimaryKey(jComboBoxReleaseClass),
                    getPrimaryKey(jComboBoxClassPackage),jComboBoxEnclosingClass);
            refreshSuperClasses(getPrimaryKey(jComboBoxClassPackage), jComboBoxExtendedClass);
        }
    }//GEN-LAST:event_jComboBoxProjectClassActionPerformed

    private void jComboBoxReleaseClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxReleaseClassActionPerformed
        if(checkComboChanges){
            refreshPackages(getPrimaryKey(jComboBoxReleaseClass),jComboBoxClassPackage);
            refreshNestedClasses(getPrimaryKey(jComboBoxReleaseClass),
                    getPrimaryKey(jComboBoxClassPackage),jComboBoxEnclosingClass);
            refreshSuperClasses(getPrimaryKey(jComboBoxClassPackage), jComboBoxExtendedClass);
        }
    }//GEN-LAST:event_jComboBoxReleaseClassActionPerformed

    private void jStartingClassLineFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStartingClassLineFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jStartingClassLineFieldActionPerformed

    private void jClassNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClassNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jClassNameFieldActionPerformed

    private void jEndingClassLineFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEndingClassLineFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jEndingClassLineFieldActionPerformed

    private void jClassSourceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClassSourceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jClassSourceFieldActionPerformed

    private void jComboBoxClassPackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxClassPackageActionPerformed
        if(checkComboChanges){
            refreshSuperClasses(getPrimaryKey(jComboBoxClassPackage),jComboBoxExtendedClass);
            refreshNestedClasses(getPrimaryKey(jComboBoxReleaseClass),
                getPrimaryKey(jComboBoxClassPackage),jComboBoxEnclosingClass);
        }
    }//GEN-LAST:event_jComboBoxClassPackageActionPerformed

    private void jComboBoxProjectReleaseChooseProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProjectReleaseChooseProjectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxProjectReleaseChooseProjectActionPerformed

    private void jInsertAuthorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertAuthorButtonActionPerformed
        AuthorQueryExecutor author=new AuthorQueryExecutor();
        author.insertAuthor(this);
    }//GEN-LAST:event_jInsertAuthorButtonActionPerformed

    private void jInsertNewProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertNewProjectButtonActionPerformed
        ProjectQueryExecutor project=new ProjectQueryExecutor();
        project.insertNewProject(this);
    }//GEN-LAST:event_jInsertNewProjectButtonActionPerformed

    private void jInsertProjectDevsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertProjectDevsButtonActionPerformed
        ProjectQueryExecutor project=new ProjectQueryExecutor();
        project.insertNewProjectDev(this,AssignedAuthorProjectModel);
    }//GEN-LAST:event_jInsertProjectDevsButtonActionPerformed

    private void jInsertPackageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertPackageButtonActionPerformed
        PackageQueryExecutor pack=new PackageQueryExecutor();
        try {
            pack.insertNewPackage(this);
        } catch (SQLException sqlex) {
            JOptionPane.showMessageDialog(this, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jInsertPackageButtonActionPerformed

    private void jInsertClassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertClassButtonActionPerformed
        ClassQueryExecutor classe=new ClassQueryExecutor();
        try {
            classe.insertNewClass(this);
        } catch (SQLException sqlex) {
            JOptionPane.showMessageDialog(this, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jInsertClassButtonActionPerformed

    private void jRefreshButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRefreshButton1ActionPerformed
        AuthorClassModel.clear();
        AssignedAuthorClassModel.clear();
        String projectname=getPrimaryKey(jComboBoxClassDevChooseProject);
        listBuilder.buildAuthorList(AuthorClassModel, authorBuilder.authorsBasedOnProjectListQueryBuilder(projectname));
    }//GEN-LAST:event_jRefreshButton1ActionPerformed

    private void jRightArrowButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRightArrowButton1ActionPerformed
        String value = jAuthorsBasedOnProjectList.getSelectedValue();
        if (!value.isEmpty()) {
            AuthorClassModel.removeElementAt(jAuthorsBasedOnProjectList.getSelectedIndex());
            AssignedAuthorClassModel.addElement(value);
        }
    }//GEN-LAST:event_jRightArrowButton1ActionPerformed

    private void jLeftArrowButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLeftArrowButton1ActionPerformed
        String value = jAssignedClassDevsList.getSelectedValue();
        if (!value.isEmpty()) {
            AssignedAuthorClassModel.removeElementAt(jAssignedClassDevsList.getSelectedIndex());
            AuthorClassModel.addElement(value);
        }
    }//GEN-LAST:event_jLeftArrowButton1ActionPerformed

    private void jInsertClassDevsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertClassDevsButtonActionPerformed
        ClassQueryExecutor classes=new ClassQueryExecutor();
        classes.insertNewClassDev(this,AssignedAuthorClassModel);
    }//GEN-LAST:event_jInsertClassDevsButtonActionPerformed

    private void jComboBoxClassDevChooseProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxClassDevChooseProjectActionPerformed
        if(checkComboChanges){
            checkComboChanges=false;
            AuthorClassModel.clear();
            AssignedAuthorClassModel.clear();
            String projectname=getPrimaryKey(jComboBoxClassDevChooseProject);
            
            comboBuilder.buildComboBox(jComboBoxClassDevChooseClass,classBuilder.classListProjectBasedQueryBuilder(projectname),false);
            listBuilder.buildAuthorList(AuthorClassModel, authorBuilder.authorsBasedOnProjectListQueryBuilder(projectname));
            checkComboChanges=true;
        }
    }//GEN-LAST:event_jComboBoxClassDevChooseProjectActionPerformed

    private void jNewMethodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewMethodButtonActionPerformed
        jNewMethodDeveloperInsertPanel.setVisible(false);
        jNewMethodClassInsertPanel.setVisible(false);
        jNewMethodInsertPanel.setVisible(true);
    }//GEN-LAST:event_jNewMethodButtonActionPerformed

    private void jNewMethodDevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewMethodDevButtonActionPerformed
        jNewMethodInsertPanel.setVisible(false);
        jNewMethodClassInsertPanel.setVisible(false);
        jNewMethodDeveloperInsertPanel.setVisible(true);
    }//GEN-LAST:event_jNewMethodDevButtonActionPerformed

    private void jMethodParametersFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMethodParametersFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMethodParametersFieldActionPerformed

    private void jComboBoxProjectMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProjectMethodActionPerformed
        if(checkComboChanges){
            refreshReleases(getPrimaryKey(jComboBoxProjectMethod), jComboBoxReleaseMethod);
            String release=getPrimaryKey(jComboBoxReleaseMethod);
            refreshPackages(release, jComboBoxMethodPackage);
            refreshMethodClasses(release,getPrimaryKey(jComboBoxMethodPackage),jComboBoxMethodClass);
        }
    }//GEN-LAST:event_jComboBoxProjectMethodActionPerformed

    private void jComboBoxReleaseMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxReleaseMethodActionPerformed
        if(checkComboChanges){
            String release=getPrimaryKey(jComboBoxReleaseMethod);
            refreshPackages(release, jComboBoxMethodPackage);
            refreshMethodClasses(release,getPrimaryKey(jComboBoxMethodPackage),jComboBoxMethodClass);
        }
    }//GEN-LAST:event_jComboBoxReleaseMethodActionPerformed

    private void jStartingMethodLineFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStartingMethodLineFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jStartingMethodLineFieldActionPerformed

    private void jMethodReturnTypeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMethodReturnTypeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMethodReturnTypeFieldActionPerformed

    private void jEndingMethodLineFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEndingMethodLineFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jEndingMethodLineFieldActionPerformed

    private void jInsertMethodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertMethodButtonActionPerformed
        MethodQueryExecutor method=new MethodQueryExecutor();
        method.insertNewMethod(this);
    }//GEN-LAST:event_jInsertMethodButtonActionPerformed

    private void jRefreshButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRefreshButton2ActionPerformed
        AuthorMethodModel.clear();
        AssignedAuthorMethodModel.clear();
        listBuilder.buildAuthorList(AuthorMethodModel, authorBuilder.authorsListQueryBuilder());
    }//GEN-LAST:event_jRefreshButton2ActionPerformed

    private void jRightArrowButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRightArrowButton2ActionPerformed
        String value = jAuthorsList2.getSelectedValue();
        if (!value.isEmpty()) {
            AuthorMethodModel.removeElementAt(jAuthorsList2.getSelectedIndex());
            AssignedAuthorMethodModel.addElement(value);
        }
        
    }//GEN-LAST:event_jRightArrowButton2ActionPerformed

    private void jLeftArrowButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLeftArrowButton2ActionPerformed
        String value = jAssignedMethodDevsList.getSelectedValue();
        if (!value.isEmpty()) {
            AssignedAuthorMethodModel.removeElementAt(jAssignedMethodDevsList.getSelectedIndex());
            AuthorMethodModel.addElement(value);
        }
    }//GEN-LAST:event_jLeftArrowButton2ActionPerformed

    private void jInsertMethodDevsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertMethodDevsButtonActionPerformed
       MethodQueryExecutor method = new MethodQueryExecutor();
       method.insertNewMethodDev(this, AssignedAuthorMethodModel);
    }//GEN-LAST:event_jInsertMethodDevsButtonActionPerformed

    private void jMethodNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMethodNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMethodNameFieldActionPerformed

    private void jNewTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewTestButtonActionPerformed
        jNewTestDeveloperInsertPanel.setVisible(false);
        jNewTestExecutionPanel.setVisible(false);
        jNewTestInsertPanel.setVisible(true);
    }//GEN-LAST:event_jNewTestButtonActionPerformed

    private void jNewTestDevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewTestDevButtonActionPerformed
        jNewTestInsertPanel.setVisible(false);
        jNewTestExecutionPanel.setVisible(false);
        jNewTestDeveloperInsertPanel.setVisible(true);
        
    }//GEN-LAST:event_jNewTestDevButtonActionPerformed

    private void jTestNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTestNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTestNameFieldActionPerformed

    private void jComboBoxProjectTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProjectTestActionPerformed
        ClassTestModel.clear();
        AssignedClassTestModel.clear();
        String projectname=getPrimaryKey(jComboBoxProjectTest);
        listBuilder.buildAuthorList(ClassTestModel, classBuilder.classListProjectBasedQueryBuilder(projectname));
    }//GEN-LAST:event_jComboBoxProjectTestActionPerformed

    private void jTestPathFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTestPathFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTestPathFieldActionPerformed

    private void jInsertTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertTestButtonActionPerformed
        TestQueryExecutor t=new TestQueryExecutor();
        try{
            t.insertNewTest(this, AssignedClassTestModel, AssignedMethodTestModel);
        }
        catch(SQLException sqlex){
            JOptionPane.showMessageDialog(this, sqlex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jInsertTestButtonActionPerformed

    private void jNewExecutionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewExecutionButtonActionPerformed
        jNewTestInsertPanel.setVisible(false);
        jNewTestDeveloperInsertPanel.setVisible(false);
        jNewTestExecutionPanel.setVisible(true);
    }//GEN-LAST:event_jNewExecutionButtonActionPerformed

    private void jRefreshButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRefreshButton3ActionPerformed
        MethodTestModel.clear();
        AssignedMethodTestModel.clear();
        listBuilder.buildAuthorList(MethodTestModel,methodBuilder.methodListQueryBuilder());
    }//GEN-LAST:event_jRefreshButton3ActionPerformed

    private void jRightArrowButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRightArrowButton3ActionPerformed
        String value = jMethodsTestList.getSelectedValue();
        if (!value.isEmpty()) {
            MethodTestModel.removeElementAt(jMethodsTestList.getSelectedIndex());
            AssignedMethodTestModel.addElement(value);
        }
    }//GEN-LAST:event_jRightArrowButton3ActionPerformed

    private void jLeftArrowButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLeftArrowButton3ActionPerformed
        String value = jTestedMethodsList.getSelectedValue();
        if (!value.isEmpty()) {
            AssignedMethodTestModel.removeElementAt(jTestedMethodsList.getSelectedIndex());
            MethodTestModel.addElement(value);
        }
    }//GEN-LAST:event_jLeftArrowButton3ActionPerformed

    private void jRefreshButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRefreshButton4ActionPerformed
        ClassTestModel.clear();
        AssignedClassTestModel.clear();
        String projectname=getPrimaryKey(jComboBoxProjectTest);
        String query=classBuilder.classListProjectBasedQueryBuilder(projectname);
        listBuilder.buildAuthorList(ClassTestModel, query);
    }//GEN-LAST:event_jRefreshButton4ActionPerformed

    private void jRightArrowButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRightArrowButton4ActionPerformed
        String value = jClassBasedOnProjectList.getSelectedValue();
        if (!value.isEmpty()) {
            ClassTestModel.removeElementAt(jClassBasedOnProjectList.getSelectedIndex());
            AssignedClassTestModel.addElement(value);
        }
    }//GEN-LAST:event_jRightArrowButton4ActionPerformed

    private void jLeftArrowButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLeftArrowButton4ActionPerformed
        String value = jTestedClassList.getSelectedValue();
        if (!value.isEmpty()) {
            AssignedClassTestModel.removeElementAt(jTestedClassList.getSelectedIndex());
            ClassTestModel.addElement(value);
        }
    }//GEN-LAST:event_jLeftArrowButton4ActionPerformed

    private void jInsertMethodClassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertMethodClassButtonActionPerformed
        MethodQueryExecutor method=new MethodQueryExecutor();
        method.insertNewMethodClass(this);
    }//GEN-LAST:event_jInsertMethodClassButtonActionPerformed

    private void jComboBoxMethodPackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMethodPackageActionPerformed
        if(checkComboChanges){
            String release=getPrimaryKey(jComboBoxReleaseMethod);
            refreshMethodClasses(release,getPrimaryKey(jComboBoxMethodPackage),jComboBoxMethodClass);
        }
    }//GEN-LAST:event_jComboBoxMethodPackageActionPerformed

    private void jNewMethodClassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewMethodClassButtonActionPerformed
        jNewMethodInsertPanel.setVisible(false);
        jNewMethodDeveloperInsertPanel.setVisible(false);
        jNewMethodClassInsertPanel.setVisible(true);

    }//GEN-LAST:event_jNewMethodClassButtonActionPerformed

    private void jComboBoxTestDevChooseProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTestDevChooseProjectActionPerformed
        if(checkComboChanges){
            checkComboChanges=false;
            String projectname=getPrimaryKey(jComboBoxTestDevChooseProject);
            comboBuilder.buildComboBox(jComboBoxTestDevChooseTest, testBuilder.testBasedOnProjectList(projectname), false);
            AuthorTestModel.clear();
            AssignedAuthorTestModel.clear();
            listBuilder.buildAuthorList(AuthorTestModel, authorBuilder.authorsBasedOnProjectListQueryBuilder(projectname));
            checkComboChanges=true;
        }
    }//GEN-LAST:event_jComboBoxTestDevChooseProjectActionPerformed

    private void jRefreshButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRefreshButton5ActionPerformed
        AuthorTestModel.clear();
        AssignedAuthorTestModel.clear();
        String projectname=getPrimaryKey(jComboBoxTestDevChooseProject);
        String query=authorBuilder.authorsBasedOnProjectListQueryBuilder(projectname);
        listBuilder.buildAuthorList(AuthorTestModel, query);
    }//GEN-LAST:event_jRefreshButton5ActionPerformed

    private void jRightArrowButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRightArrowButton5ActionPerformed
        String value = jAuthorsBasedOnProjectList1.getSelectedValue();
        if (!value.isEmpty()) {
            AuthorTestModel.removeElementAt(jAuthorsBasedOnProjectList1.getSelectedIndex());
            AssignedAuthorTestModel.addElement(value);
        }
    }//GEN-LAST:event_jRightArrowButton5ActionPerformed

    private void jLeftArrowButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLeftArrowButton5ActionPerformed
        String value = jAssignedTestDevsList.getSelectedValue();
        if (!value.isEmpty()) {
            AssignedAuthorTestModel.removeElementAt(jAssignedTestDevsList.getSelectedIndex());
            AuthorTestModel.addElement(value);
        }
    }//GEN-LAST:event_jLeftArrowButton5ActionPerformed

    private void jInsertTestDevsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertTestDevsButtonActionPerformed
        TestQueryExecutor t = new TestQueryExecutor();
        t.insertNewTestDev(this, AssignedAuthorTestModel);
    }//GEN-LAST:event_jInsertTestDevsButtonActionPerformed

    private void jInsertExecutionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertExecutionButtonActionPerformed
       TestQueryExecutor t = new TestQueryExecutor();
       t.insertNewExecution(this);
    }//GEN-LAST:event_jInsertExecutionButtonActionPerformed

    private void jComboBoxProjectChangelogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProjectChangelogActionPerformed
        if(checkComboChanges){
            System.out.println("Sto qua progetto");
            checkComboChanges=false;
            String projectname=getPrimaryKey(jComboBoxProjectChangelog);
            comboBuilder.buildComboBox(jComboBoxChangelogRelease, projectBuilder.projectReleasesQueryBuilder(projectname), false);
            String release=getPrimaryKey(jComboBoxChangelogRelease);
            comboBuilder.buildComboBox(jComboBoxChangelogPackage,packageBuilder.packageListQueryBuilder(release),false);
            comboBuilder.buildComboBox(jComboBoxChangelogClass,classBuilder.classListReleaseBasedQueryBuilder(release),false);
            comboBuilder.buildComboBox(jComboBoxChangelogMethod,methodBuilder.methodListReleaseBasedQueryBuilder(release),false);
            checkComboChanges=true;     
        }
    }//GEN-LAST:event_jComboBoxProjectChangelogActionPerformed

    private void jComboBoxChangelogReleaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxChangelogReleaseActionPerformed
        if(checkComboChanges){
            checkComboChanges=false;
            System.out.println("Sto qua release");
            String release=getPrimaryKey(jComboBoxChangelogRelease);
            comboBuilder.buildComboBox(jComboBoxChangelogPackage,packageBuilder.packageListQueryBuilder(release),false);
            comboBuilder.buildComboBox(jComboBoxChangelogClass,classBuilder.classListReleaseBasedQueryBuilder(release),false);
            comboBuilder.buildComboBox(jComboBoxChangelogMethod,methodBuilder.methodListReleaseBasedQueryBuilder(release),false);
            checkComboChanges=true;     
        }
    }//GEN-LAST:event_jComboBoxChangelogReleaseActionPerformed

    private void jComboBoxChangelogChangedObjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxChangelogChangedObjectActionPerformed
        verifyChangelogCB();
    }//GEN-LAST:event_jComboBoxChangelogChangedObjectActionPerformed

    private void jInsertChangelogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertChangelogButtonActionPerformed
        ChangelogQueryExecutor cl = new ChangelogQueryExecutor();
        cl.insertNewChangelog(this);
    }//GEN-LAST:event_jInsertChangelogButtonActionPerformed

    private void jComboBoxChangelogDevChooseProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxChangelogDevChooseProjectActionPerformed
        if(checkComboChanges){
            checkComboChanges=false;
            String projectname = getPrimaryKey(jComboBoxChangelogDevChooseProject);
            AuthorChangelogModel.clear();
            AssignedAuthorChangelogModel.clear();
            comboBuilder.buildComboBox(jComboBoxChangelogDevChooseLog,changelogBuilder.logsListBasedOnProjectQueryBuilder(projectname), false);
            listBuilder.buildAuthorList(AuthorChangelogModel, authorBuilder.authorsBasedOnProjectListQueryBuilder(projectname));
            checkComboChanges=true;
        }
    }//GEN-LAST:event_jComboBoxChangelogDevChooseProjectActionPerformed

    private void jRefreshButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRefreshButton6ActionPerformed
        AuthorChangelogModel.clear();
        AssignedAuthorChangelogModel.clear();
        String projectname=getPrimaryKey(jComboBoxChangelogDevChooseProject);
        String query=authorBuilder.authorsBasedOnProjectListQueryBuilder(projectname);
        listBuilder.buildAuthorList(AuthorChangelogModel, query);
    }//GEN-LAST:event_jRefreshButton6ActionPerformed

    private void jRightArrowButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRightArrowButton6ActionPerformed
      String value = jAuthorsBasedOnProjectList2.getSelectedValue();
        if (!value.isEmpty()) {
            AuthorChangelogModel.removeElementAt(jAuthorsBasedOnProjectList2.getSelectedIndex());
            AssignedAuthorChangelogModel.addElement(value);
        }
    }//GEN-LAST:event_jRightArrowButton6ActionPerformed

    private void jLeftArrowButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLeftArrowButton6ActionPerformed
        String value = jAssignedChangelogDevsList.getSelectedValue();
        if (!value.isEmpty()) {
            AssignedAuthorChangelogModel.removeElementAt(jAssignedChangelogDevsList.getSelectedIndex());
            AuthorChangelogModel.addElement(value);
        }
    }//GEN-LAST:event_jLeftArrowButton6ActionPerformed

    private void jInsertChangelogDevsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertChangelogDevsButtonActionPerformed
        ChangelogQueryExecutor cl = new ChangelogQueryExecutor();
        cl.insertNewChangelogDev(this, AssignedAuthorChangelogModel);
    }//GEN-LAST:event_jInsertChangelogDevsButtonActionPerformed

    private void jNewChangelogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewChangelogButtonActionPerformed
        jNewChangelogDeveloperInsertPanel.setVisible(false);
        jNewChangelogInsertPanel.setVisible(true);
    }//GEN-LAST:event_jNewChangelogButtonActionPerformed

    private void jNewChangelogDevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewChangelogDevButtonActionPerformed
        jNewChangelogInsertPanel.setVisible(false);
        jNewChangelogDeveloperInsertPanel.setVisible(true);
        
    }//GEN-LAST:event_jNewChangelogDevButtonActionPerformed

    private void jRefreshAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRefreshAllMenuItemActionPerformed
        System.out.println("Aggiornamento...");
        checkComboChanges=false;
        comboBuilder.buildComboBox(jComboBoxProjectReleaseChooseProject,
            projectBuilder.projectsListQueryBuilder(),false);
        comboBuilder.buildComboBox(jComboBoxProjectDevChooseProject,
                projectBuilder.projectsListQueryBuilder(),false);
        checkComboChanges=true;
        initInsertPackageComboBoxes();
        initInsertClassComboBoxes();
        initInsertMethodComboBoxes();
        initInsertTestComboBoxes();
        initInsertChangelogComboBoxes();
        AuthorProjectModel.clear();
        AssignedAuthorProjectModel.clear();
        AuthorClassModel.clear();
        AssignedAuthorClassModel.clear();
        AuthorMethodModel.clear();
        AssignedAuthorMethodModel.clear();
        ClassTestModel.clear();
        AssignedClassTestModel.clear();
        MethodTestModel.clear();
        AssignedMethodTestModel.clear();
        AuthorTestModel.clear();
        AssignedAuthorTestModel.clear();
        AuthorChangelogModel.clear();
        AssignedAuthorChangelogModel.clear();
        String query=authorBuilder.authorsListQueryBuilder();
        listBuilder.buildAuthorList(AuthorProjectModel, query);
        String projectname=getPrimaryKey(jComboBoxClassDevChooseProject);
        query=authorBuilder.authorsBasedOnProjectListQueryBuilder(projectname);
        listBuilder.buildAuthorList(AuthorClassModel, query);
        listBuilder.buildAuthorList(AuthorMethodModel,authorBuilder.authorsListQueryBuilder());
        projectname=getPrimaryKey(jComboBoxProjectTest);
        query=classBuilder.classListProjectBasedQueryBuilder(projectname);
        listBuilder.buildAuthorList(ClassTestModel, query);
        listBuilder.buildAuthorList(MethodTestModel,methodBuilder.methodListQueryBuilder());
        projectname=getPrimaryKey(jComboBoxTestDevChooseProject);
        listBuilder.buildAuthorList(AuthorTestModel,authorBuilder.authorsBasedOnProjectListQueryBuilder(projectname));
        projectname=getPrimaryKey(jComboBoxChangelogDevChooseProject);
        listBuilder.buildAuthorList(AuthorChangelogModel,authorBuilder.authorsBasedOnProjectListQueryBuilder(projectname));
    }//GEN-LAST:event_jRefreshAllMenuItemActionPerformed

    private void jComboBoxExecutionChooseTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxExecutionChooseTestActionPerformed
        if(checkComboChanges){
            checkComboChanges=false;
            String code=getPrimaryKey(jComboBoxExecutionChooseTest);
            comboBuilder.buildComboBox(jComboBoxExecutionRelease,projectBuilder.projectReleasesBasedOnTest(code),false);
            checkComboChanges=true;            
        }
    }//GEN-LAST:event_jComboBoxExecutionChooseTestActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jAssignClassTestPanel;
    private javax.swing.JPanel jAssignMethodTestPanel;
    private javax.swing.JList<String> jAssignedChangelogDevsList;
    private javax.swing.JList<String> jAssignedClassDevsList;
    private javax.swing.JList<String> jAssignedMethodDevsList;
    private javax.swing.JList<String> jAssignedProjectDevsList;
    private javax.swing.JList<String> jAssignedTestDevsList;
    private javax.swing.JTextField jAuthorEmailField;
    private javax.swing.JPanel jAuthorInsertFieldsPanel;
    private javax.swing.JPanel jAuthorInsertPanel;
    private javax.swing.JTextField jAuthorNameField;
    private javax.swing.JTextField jAuthorSurnameField;
    private javax.swing.JList<String> jAuthorsBasedOnProjectList;
    private javax.swing.JList<String> jAuthorsBasedOnProjectList1;
    private javax.swing.JList<String> jAuthorsBasedOnProjectList2;
    private javax.swing.JList<String> jAuthorsList;
    private javax.swing.JList<String> jAuthorsList2;
    private com.toedter.calendar.JDateChooser jChangelogClosingDateField;
    private javax.swing.JTextArea jChangelogDescriptionTextArea;
    private javax.swing.JPanel jChangelogInsertOperationsPanel;
    private javax.swing.JPanel jChangelogInsertPanel;
    private com.toedter.calendar.JDateChooser jChangelogOpeningDateField;
    private javax.swing.JList<String> jClassBasedOnProjectList;
    private javax.swing.JPanel jClassInsertFieldsPanel;
    private javax.swing.JPanel jClassInsertOperationsPanel;
    private javax.swing.JPanel jClassInsertPanel;
    private javax.swing.JTextField jClassNameField;
    private javax.swing.JTextField jClassSourceField;
    private javax.swing.JComboBox<String> jComboBoxChangelogChangeType;
    private javax.swing.JComboBox<String> jComboBoxChangelogChangedObject;
    private javax.swing.JComboBox<String> jComboBoxChangelogClass;
    private javax.swing.JComboBox<String> jComboBoxChangelogDevChooseLog;
    private javax.swing.JComboBox<String> jComboBoxChangelogDevChooseProject;
    private javax.swing.JComboBox<String> jComboBoxChangelogMethod;
    private javax.swing.JComboBox<String> jComboBoxChangelogPackage;
    private javax.swing.JComboBox<String> jComboBoxChangelogRelease;
    private javax.swing.JComboBox<String> jComboBoxClassDevChooseClass;
    private javax.swing.JComboBox<String> jComboBoxClassDevChooseProject;
    private javax.swing.JComboBox<String> jComboBoxClassModifiers;
    private javax.swing.JComboBox<String> jComboBoxClassPackage;
    private javax.swing.JComboBox<String> jComboBoxClassScope;
    private javax.swing.JComboBox<String> jComboBoxEnclosingClass;
    private javax.swing.JComboBox<String> jComboBoxExecutionChooseTest;
    private javax.swing.JComboBox<String> jComboBoxExecutionRelease;
    private javax.swing.JComboBox<String> jComboBoxExecutionResult;
    private javax.swing.JComboBox<String> jComboBoxExtendedClass;
    private javax.swing.JComboBox<String> jComboBoxFatherPackage;
    private javax.swing.JComboBox<String> jComboBoxMethodClass;
    private javax.swing.JComboBox<String> jComboBoxMethodClassChooseMethod;
    private javax.swing.JComboBox<String> jComboBoxMethodDevChooseMethod;
    private javax.swing.JComboBox<String> jComboBoxMethodModifiers;
    private javax.swing.JComboBox<String> jComboBoxMethodPackage;
    private javax.swing.JComboBox<String> jComboBoxMethodScope;
    private javax.swing.JComboBox<String> jComboBoxProjectChangelog;
    private javax.swing.JComboBox<String> jComboBoxProjectClass;
    private javax.swing.JComboBox<String> jComboBoxProjectDevChooseProject;
    private javax.swing.JComboBox<String> jComboBoxProjectMethod;
    private javax.swing.JComboBox<String> jComboBoxProjectPackage;
    private javax.swing.JComboBox<String> jComboBoxProjectReleaseChooseProject;
    private javax.swing.JComboBox<String> jComboBoxProjectTest;
    private javax.swing.JComboBox<String> jComboBoxReleaseClass;
    private javax.swing.JComboBox<String> jComboBoxReleaseMethod;
    private javax.swing.JComboBox<String> jComboBoxReleasePackage;
    private javax.swing.JComboBox<String> jComboBoxTestDevChooseProject;
    private javax.swing.JComboBox<String> jComboBoxTestDevChooseTest;
    private javax.swing.JTextField jEndingClassLineField;
    private javax.swing.JTextField jEndingMethodLineField;
    private com.toedter.calendar.JDateChooser jExecutionDateField;
    private javax.swing.JPanel jExecutionInsertFieldsPanel;
    private javax.swing.JButton jInsertAuthorButton;
    private javax.swing.JButton jInsertChangelogButton;
    private javax.swing.JButton jInsertChangelogDevsButton;
    private javax.swing.JButton jInsertClassButton;
    private javax.swing.JButton jInsertClassDevsButton;
    private javax.swing.JButton jInsertExecutionButton;
    private javax.swing.JButton jInsertMethodButton;
    private javax.swing.JButton jInsertMethodClassButton;
    private javax.swing.JButton jInsertMethodDevsButton;
    private javax.swing.JButton jInsertNewProjectButton;
    private javax.swing.JButton jInsertPackageButton;
    private javax.swing.JButton jInsertProjectDevsButton;
    private javax.swing.JButton jInsertProjectReleaseButton;
    private javax.swing.JButton jInsertTestButton;
    private javax.swing.JButton jInsertTestDevsButton;
    private javax.swing.JButton jLeftArrowButton;
    private javax.swing.JButton jLeftArrowButton1;
    private javax.swing.JButton jLeftArrowButton2;
    private javax.swing.JButton jLeftArrowButton3;
    private javax.swing.JButton jLeftArrowButton4;
    private javax.swing.JButton jLeftArrowButton5;
    private javax.swing.JButton jLeftArrowButton6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jMethodInsertFieldsPanel;
    private javax.swing.JPanel jMethodInsertOperationsPanel;
    private javax.swing.JPanel jMethodInsertPanel;
    private javax.swing.JTextField jMethodNameField;
    private javax.swing.JTextField jMethodParametersField;
    private javax.swing.JTextField jMethodReturnTypeField;
    private javax.swing.JList<String> jMethodsTestList;
    private javax.swing.JButton jNewChangelogButton;
    private javax.swing.JButton jNewChangelogDevButton;
    private javax.swing.JPanel jNewChangelogDeveloperInsertPanel;
    private javax.swing.JPanel jNewChangelogInsertPanel;
    private javax.swing.JButton jNewClassButton;
    private javax.swing.JButton jNewClassDevButton;
    private javax.swing.JPanel jNewClassDeveloperInsertPanel;
    private javax.swing.JPanel jNewClassInsertPanel;
    private javax.swing.JButton jNewExecutionButton;
    private javax.swing.JButton jNewMethodButton;
    private javax.swing.JButton jNewMethodClassButton;
    private javax.swing.JPanel jNewMethodClassInsertFieldsPanel;
    private javax.swing.JPanel jNewMethodClassInsertPanel;
    private javax.swing.JButton jNewMethodDevButton;
    private javax.swing.JPanel jNewMethodDeveloperInsertPanel;
    private javax.swing.JPanel jNewMethodInsertPanel;
    private javax.swing.JButton jNewProjectButton;
    private javax.swing.JButton jNewProjectDevButton;
    private javax.swing.JPanel jNewProjectDeveloperInsertPanel;
    private javax.swing.JPanel jNewProjectInsertPanel;
    private javax.swing.JPanel jNewProjectReleaseInsertPanel;
    private javax.swing.JButton jNewReleaseButton;
    private javax.swing.JButton jNewTestButton;
    private javax.swing.JButton jNewTestDevButton;
    private javax.swing.JPanel jNewTestDeveloperInsertPanel;
    private javax.swing.JPanel jNewTestExecutionPanel;
    private javax.swing.JPanel jNewTestInsertPanel;
    private javax.swing.JPanel jPackageInsertFieldsPanel;
    private javax.swing.JPanel jPackageInsertPanel;
    private javax.swing.JTextField jPackageNameField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private com.toedter.calendar.JDateChooser jProjectCancelationDateField;
    private com.toedter.calendar.JDateChooser jProjectCreationDateField;
    private com.toedter.calendar.JDateChooser jProjectDateReleaseField;
    private javax.swing.JTextArea jProjectDescriptionArea;
    private javax.swing.JPanel jProjectInsertOperationsPanel;
    private javax.swing.JPanel jProjectInsertPanel;
    private javax.swing.JTextField jProjectNameField;
    private javax.swing.JTextField jProjectPathField;
    private javax.swing.JTextField jProjectReleaseNumberField;
    private javax.swing.JTextField jProjectReleasePathField;
    private javax.swing.JMenuItem jRefreshAllMenuItem;
    private javax.swing.JButton jRefreshButton;
    private javax.swing.JButton jRefreshButton1;
    private javax.swing.JButton jRefreshButton2;
    private javax.swing.JButton jRefreshButton3;
    private javax.swing.JButton jRefreshButton4;
    private javax.swing.JButton jRefreshButton5;
    private javax.swing.JButton jRefreshButton6;
    private javax.swing.JButton jRightArrowButton;
    private javax.swing.JButton jRightArrowButton1;
    private javax.swing.JButton jRightArrowButton2;
    private javax.swing.JButton jRightArrowButton3;
    private javax.swing.JButton jRightArrowButton4;
    private javax.swing.JButton jRightArrowButton5;
    private javax.swing.JButton jRightArrowButton6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jStartingClassLineField;
    private javax.swing.JTextField jStartingMethodLineField;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTestDescriptionTextArea;
    private javax.swing.JPanel jTestInsertFieldsPanel;
    private javax.swing.JPanel jTestInsertListsPanel;
    private javax.swing.JPanel jTestInsertOperationsPanel;
    private javax.swing.JPanel jTestInsertPanel;
    private javax.swing.JTextField jTestNameField;
    private javax.swing.JTextField jTestPathField;
    private javax.swing.JList<String> jTestedClassList;
    private javax.swing.JList<String> jTestedMethodsList;
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
    private java.awt.Label label68;
    private java.awt.Label label69;
    private java.awt.Label label7;
    private java.awt.Label label70;
    private java.awt.Label label71;
    private java.awt.Label label73;
    private java.awt.Label label74;
    private java.awt.Label label75;
    private java.awt.Label label76;
    private java.awt.Label label77;
    private java.awt.Label label78;
    private java.awt.Label label79;
    private java.awt.Label label8;
    private java.awt.Label label80;
    private java.awt.Label label81;
    private java.awt.Label label82;
    private java.awt.Label label83;
    private java.awt.Label label84;
    private java.awt.Label label85;
    private java.awt.Label label86;
    private java.awt.Label label87;
    private java.awt.Label label88;
    private java.awt.Label label89;
    private java.awt.Label label9;
    // End of variables declaration//GEN-END:variables
}
