/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.ContentSettings;
import unesco.elearning.controllers.LogCtrl;
import unesco.elearning.controllers.LoginPage;
import unesco.elearning.language.Language;
import unesco.elearning.model.LessonHistory;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class FormalDashBoardController implements Initializable {
    
    
    public static String NFT="", NFL="";
    
    public static Stage profileStage, settingStage;
    
    DialogBoxes dbox = new DialogBoxes();
    
    LoginPage lpage = new LoginPage();
    
    Language lang = new Language();
    LogCtrl lgctrl = new LogCtrl();
    
    public static Label n_studentName;
    public static ImageView n_student_image;

    @FXML
    private JFXButton lesson_1;

    @FXML
    private JFXButton lesson_2;

    @FXML
    private JFXButton lesson_3;

    @FXML
    private JFXButton lesson_4;

    @FXML
    private JFXButton lesson_5;

    @FXML
    private JFXButton lesson_6;
    
    @FXML
    private AnchorPane dashboardPane;
    
    @FXML
    private Label student_name;

    @FXML
    private Label student_status;

    @FXML
    private AnchorPane view_pane;
    
    @FXML
    private ImageView student_image;
    @FXML
    private FontAwesomeIconView exit_app;
    @FXML
    private FontAwesomeIconView edit_profileB;
    @FXML
    private Hyperlink dashboard;
    @FXML
    private Hyperlink courses;
    @FXML
    private Hyperlink quizes;
    @FXML
    private Hyperlink achievements;
    @FXML
    private Hyperlink settings;
    @FXML
    private Hyperlink logout;
    @FXML
    private Hyperlink ulogs;
    
    public String myLoc="";
    
    public static String playLesson="", lessonTitle="";
    @FXML
    private TableView<LessonHistory> coursetableView;
    @FXML
    private TableColumn<LessonHistory, String> cDate;
    @FXML
    private TableColumn<LessonHistory, String> cCourse, cTerm,cLesson;
    @FXML
    private TableColumn<LessonHistory, String> cTimeSpent;
    
    ObservableList<LessonHistory> obList;
    @FXML
    private Label hoursSpent;
    @FXML
    private Label quizPassed;
    
    Label nhoursSpent, nquizPassed;
    @FXML
    private Hyperlink sync;
    @FXML
    private Hyperlink library;
    
    public static String exit_application="Are you sure you want to exit this application ?", access_denied="You are not allowed to access this feature", dbError="Error";
    @FXML
    private Label tsp;
    @FXML
    private Label tqp;
    
    String end_session="Logging out will end your session.";
        String sure= "Are you sure you want to continue ?";
    @FXML
    private FontAwesomeIconView profile_button;
    
    //ContentSettings cset = new ContentSettings();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        n_studentName=student_name;
        n_student_image = student_image;
        nquizPassed = quizPassed;
        nhoursSpent = hoursSpent;
        
        //List<String> lst = lgctrl.quizPassed(LoginPage.uID, ContentSettings.dscore);
        
        int x = lgctrl.quizPassed(LoginPage.uID, ContentSettings.dscore, ">=");
        nquizPassed.setText(String.valueOf(x));
        
        String str = lgctrl.hoursSpent(LoginPage.uID);
        
        System.out.println("STR ISSS - "+str);
        nhoursSpent.setText(str);
        
            student_name.setText(LoginPage.ufirstname +" "+LoginPage.ulastname);
            student_status.setText(LoginPage.ustype);
            student_image.setImage(LoginPage.scaledImages);
            
            initCol();
            /*
            if(LoginPage.atype.equals("STUDENT")){
                settings.setVisible(false);
                ulogs.setVisible(false);
            }
                    */
            
            if(MainPageController.STATUS.equals("Non Formal") && LoginPage.atype.equals("LEARNER"))
            {
                String FL = "lang/"+FXMLDocumentController.langValue.getValue()+".txt";
                
        
        String dash = lang.translate(FL, "dashboard");
        dashboard.setText(dash);
        
        String crs = lang.translate(FL, "courses");
        courses.setText(crs);
        
        String qz = lang.translate(FL, "quizes");
        quizes.setText(qz);
        
        String ach = lang.translate(FL, "achieve");
        achievements.setText(ach);
        
        String sett = lang.translate(FL, "setting");
        settings.setText(sett);
        
        String snc = lang.translate(FL, "syncData");
        sync.setText(snc);
        
        String lbr = lang.translate(FL, "library");
        library.setText(lbr);
        
        String ulg = lang.translate(FL, "logs");
        ulogs.setText(ulg);
        
        String lout = lang.translate(FL, "logout");
        logout.setText(lout);
        
        exit_application = lang.translate(FL, "exit_application");
        
        access_denied = lang.translate(FL, "accessDenied");
        
        dbError = lang.translate(FL, "dberror");
        
        String hsp = lang.translate(FL, "timeSpent");
        tsp.setText(hsp);
        
        String qpass = lang.translate(FL, "quizPassed");
        tqp.setText(qpass);
        
        String dates = lang.translate(FL, "Date");
        cDate.setText(dates);
        
        String crs1 = lang.translate(FL, "courQ");
        cCourse.setText(crs1);
        
        String lsn = lang.translate(FL, "Lesson");
        cLesson.setText(lsn);
        
        String trm = lang.translate(FL, "Term");
        cTerm.setText(trm);
        
        String tm = lang.translate(FL, "TIME");
        cTimeSpent.setText(tm);
        
        lesson_1.setText(lsn+" 1");
        lesson_2.setText(lsn+" 2");
        lesson_3.setText(lsn+" 3");
        lesson_4.setText(lsn+" 4");
        lesson_5.setText(lsn+" 5");
        lesson_6.setText(lsn+" 6");
        
        sure = lang.translate(FL, "AreYouSure");
        end_session = lang.translate(FL, "endSession");

            }
            else{
                
            }
    }    
    
    @FXML
    void userLogs(ActionEvent event) {
        
        if(LoginPage.atype.equals("STUDENT")||LoginPage.atype.equals("LEARNER")){
            dbox.showMessageDialog(Alert.AlertType.ERROR, dbError, null, access_denied);
        }
        else{
        
        try{
        
        Parent root1 = FXMLLoader.load(getClass().getResource("Logs.fxml"));
          
            
            //UnescoELearning.mainWindow.centerOnScreen();
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
        
        }
        catch(Exception nn){
            System.out.println("Error loading logs page "+nn);
        }
    
    }
    }
    
    @FXML
    void closeApp(MouseEvent event) {

        Optional<ButtonType> result = dbox.showConfirmDialog(null, exit_application);
            if (result.get() == ButtonType.OK){
                
                String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" logged out of the portal";
                String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
                
                lpage.loginLog(LoginPage.uID, actv, l_name);
    
                Platform.exit();
                
        } else {
        // ... user chose CANCEL or closed the dialog
        }
    }
    
    @FXML
    void editProfile(MouseEvent event) {

        Parent root1;
        try {
            root1 = FXMLLoader.load(getClass().getResource("EditProfile.fxml"));
            
            profileStage = new Stage();
            profileStage.initModality(Modality.APPLICATION_MODAL);
            profileStage.initStyle(StageStyle.UNDECORATED);
            profileStage.setScene(new Scene(root1));
            profileStage.setAlwaysOnTop(true);
            profileStage.show();
            
           
            
        } catch (IOException ex) {
            Logger.getLogger(FormalDashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
                   
    }
    
    @FXML
    void showSettings(ActionEvent event) {
        
        if(LoginPage.atype.equals("STUDENT")||LoginPage.atype.equals("LEARNER")){
            dbox.showMessageDialog(Alert.AlertType.ERROR, dbError, null, access_denied);
        }
        else{
            Parent root1;
        try {
            root1 = FXMLLoader.load(getClass().getResource("Settings.fxml"));
            
            settingStage = new Stage();
            settingStage.initModality(Modality.APPLICATION_MODAL);
            settingStage.initStyle(StageStyle.UNDECORATED);
            settingStage.setScene(new Scene(root1));  
            settingStage.setAlwaysOnTop(true);
            settingStage.show();
            
           
            
        } catch (IOException ex) {
            Logger.getLogger(FormalDashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        

    }
    
    @FXML
    void goToCourses(ActionEvent event) {
        
        if(LoginPage.atype.equals("STUDENT")||LoginPage.atype.equals("LEARNER")||LoginPage.atype.equals("FACILITATOR")||LoginPage.atype.equals("TEACHER")){
            
            String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" opened the courses page";
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        if(LoginPage.ustype.equals("Formal (JSS 2)")){
            //Open formal..
            
            try{
        
        Parent root1 = FXMLLoader.load(getClass().getResource("Courses.fxml"));
          
            
            //UnescoELearning.mainWindow.centerOnScreen();
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
        
        }
        catch(Exception nn){
            System.out.println("Error loading courses page "+nn);
        }
        }
        else{
            //Open Non Formal..
            try{
        
            Parent root1 = FXMLLoader.load(getClass().getResource("NonFormalOption.fxml"));

            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
        
        }
        catch(Exception nn){
            System.out.println("Error loading courses page "+nn);
        }
            
        }
        
        
            
        }
        else
        {
            Parent root1;
        try {
            root1 = FXMLLoader.load(getClass().getResource("SelectStudentType.fxml"));
            
            profileStage = new Stage();
            profileStage.initModality(Modality.APPLICATION_MODAL);
            profileStage.initStyle(StageStyle.UNDECORATED);
            profileStage.setScene(new Scene(root1));  
            profileStage.setAlwaysOnTop(true);
            profileStage.show();
            
           
            
        } catch (IOException ex) {
            Logger.getLogger(FormalDashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        

    }
    
    @FXML
    void logout(ActionEvent event) {

        
        
        Optional<ButtonType> result = dbox.showConfirmDialog(end_session, sure);
            if (result.get() == ButtonType.OK){
    
                String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" logged out of the portal";
                String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
                
                lpage.loginLog(LoginPage.uID, actv, l_name);
                
                try{
                    Parent root1 = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
          
            
            //UnescoELearning.mainWindow.centerOnScreen();
            UnescoELearning.mainWindow.setScene(new Scene(root1));
             Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        UnescoELearning.mainWindow.setX((primScreenBounds.getWidth() - UnescoELearning.mainWindow.getWidth()) / 2);
        UnescoELearning.mainWindow.setY((primScreenBounds.getHeight() - UnescoELearning.mainWindow.getHeight()) / 2);
            //UnescoELearning.mainWindow.centerOnScreen();
                    
                }
                
                catch(Exception nn){
                    
                }
                
        } else {
        // ... user chose CANCEL or closed the dialog
        }
        
    }

    @FXML
    private void gotoQuiz(ActionEvent event) {
        
        if(LoginPage.atype.equals("STUDENT")||LoginPage.atype.equals("TEACHER")||LoginPage.atype.equals("ADMINISTRATOR")){
        
            String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" opened the quiz page";
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        try{
        
        Parent root1 = FXMLLoader.load(getClass().getResource("Quiz.fxml"));

            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
        
        }
        catch(Exception nn){
            System.out.println("Error loading quiz page "+nn);
        }
            
        }
        else{
            dbox.showMessageDialog(Alert.AlertType.ERROR, dbError, null, access_denied);            
        }
        
    }

    @FXML
    private void lesson1(ActionEvent event) {
        
        int a = pinConfirm();
        
        if(a==0){
            lessonTitle = lang.translate(myLoc, "LESS1");
            playLesson = ContentSettings.C_LOC+"/"+lessonTitle;
            
            System.out.println("PLAY LESSON IS "+playLesson);
            
            if(playLesson.contains("EMPTY")){
                //do nothing...
                
                dbox.showMessageDialog(Alert.AlertType.INFORMATION, "Information", null, "You have not saved any lesson in this position");
            }
            
            else{
                
                loadplayLesson();
            }
        }
    }

    @FXML
    private void lesson2(ActionEvent event) {
        
        int a = pinConfirm();
        
        if(a==0){
            lessonTitle = lang.translate(myLoc, "LESS2");
            playLesson = ContentSettings.C_LOC+"/"+lessonTitle;
            
            System.out.println("PLAY LESSON IS "+playLesson);
            
            if(playLesson.contains("EMPTY")){
                //do nothing...
                
                dbox.showMessageDialog(Alert.AlertType.INFORMATION, "Information", null, "You have not saved any lesson in this position");
            }
            
            else{
                loadplayLesson();
            }
        }
    }

    @FXML
    private void lesson3(ActionEvent event) {
        
        int a = pinConfirm();
        
        if(a==0){
            lessonTitle = lang.translate(myLoc, "LESS3");
            playLesson = ContentSettings.C_LOC+"/"+lessonTitle;
            
            System.out.println("PLAY LESSON IS "+playLesson);
            
            if(playLesson.contains("EMPTY")){
                //do nothing...
                
                dbox.showMessageDialog(Alert.AlertType.INFORMATION, "Information", null, "You have not saved any lesson in this position");
            }
            
            else{
                loadplayLesson();
            }
        }
    }

    @FXML
    private void lesson4(ActionEvent event) {
        
        int a = pinConfirm();
        
        if(a==0){
            lessonTitle = lang.translate(myLoc, "LESS4");
            playLesson = ContentSettings.C_LOC+"/"+lessonTitle;
            
            System.out.println("PLAY LESSON IS "+playLesson);
            
            if(playLesson.contains("EMPTY")){
                //do nothing...
                
                dbox.showMessageDialog(Alert.AlertType.INFORMATION, "Information", null, "You have not saved any lesson in this position");
            }
            
            else{
                loadplayLesson();
            }
        }
    }

    @FXML
    private void lesson5(ActionEvent event) {
        
        int a = pinConfirm();
        
        if(a==0){
            lessonTitle = lang.translate(myLoc, "LESS5");
            playLesson = ContentSettings.C_LOC+"/"+lessonTitle;
            
            System.out.println("PLAY LESSON IS "+playLesson);
            
            if(playLesson.contains("EMPTY")){
                //do nothing...
                
                dbox.showMessageDialog(Alert.AlertType.INFORMATION, "Information", null, "You have not saved any lesson in this position");
            }
            
            else{
                loadplayLesson();
            }
        }
    }

    @FXML
    private void lesson6(ActionEvent event) {
        
        int a = pinConfirm();
        
        if(a==0){
            lessonTitle = lang.translate(myLoc, "LESS6");
            playLesson = ContentSettings.C_LOC+"/"+lessonTitle;
            
            System.out.println("PLAY LESSON IS "+playLesson);
            
            if(playLesson.contains("EMPTY")){
                //do nothing...
                
                dbox.showMessageDialog(Alert.AlertType.INFORMATION, "Information", null, "You have not saved any lesson in this position");
            }
            
            else{
                loadplayLesson();
            }
        }
    }
    
    public int pinConfirm(){
        
        int res =1;
        
        myLoc = ContentSettings.C_LOC+"/PINNED LESSONS/"+LoginPage.uID+".txt";
        
        File fl = new File(myLoc);
        
        if(fl.exists()){
                
            res =0;
            
        }
        
        else{
            
            res = 1;
            dbox.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "You have not saved any lesson");
        }
        
        return res;
    }
    
    
    public void loadplayLesson(){
        
        
        
        if(LoginPage.ustype.equals("Non Formal")){
            
            NonFormalVideoController.LOC ="DASH";
            
            try{
        
        Parent root1 = FXMLLoader.load(getClass().getResource("NonFormalVideo.fxml"));

            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
            String abc[] = lessonTitle.split("/");
            
            //System.out.println("ABC 2 "+abc[2]);
            //System.out.println("ABC 1 "+abc[1]);
            
            NFT = abc[1];
            NFL = abc[2];
            
            lgctrl.accessLesson(LoginPage.uID, "NON-FORMAL", NFT, NFL);
        
        }
        catch(Exception nn){
            System.out.println("Error loading quiz page "+nn);
            
            nn.printStackTrace();
        }
            
        }else{
        
        try{
        
        Parent root1 = FXMLLoader.load(getClass().getResource("PlayLesson.fxml"));

            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
        
        }
        catch(Exception nn){
            System.out.println("Error loading quiz page "+nn);
        }
        }
    }
    
    @FXML
    void goToAchievements(ActionEvent event) {
        
        if(LoginPage.atype.equals("STUDENT")||LoginPage.atype.equals("LEARNER")){
            try{
        
        Parent root1 = FXMLLoader.load(getClass().getResource("Achievements.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
        
        }
        catch(Exception nn){
            System.out.println("Error loading courses page "+nn);
        }
        }
        
        else{
            //FACILITATOR PAGE..
            
            try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FindStudents.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            
        }
            
        }
        
        

    }
    
    
    //History table..
    
        public void initCol(){
            
            //Lesson History    
        cDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        cCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        cTerm.setCellValueFactory(new PropertyValueFactory<>("term"));
        cLesson.setCellValueFactory(new PropertyValueFactory<>("lesson"));
        cTimeSpent.setCellValueFactory(new PropertyValueFactory<>("timeSpent"));
        
        List<LessonHistory> lst = lgctrl.loadHistory(LoginPage.uID, 0);
        obList = FXCollections.observableArrayList(lst);
        
        coursetableView.getItems().addAll(obList);
        
        
    }
        
        //Rollover function..
        
        @FXML
    void rollin1(MouseEvent event) {
        lesson_1.setStyle("-fx-background-color:#bfd4e5;");

    }

    @FXML
    void rollin2(MouseEvent event) {
        lesson_2.setStyle("-fx-background-color:#8fadbf;");
    }

    @FXML
    void rollin3(MouseEvent event) {
        lesson_3.setStyle("-fx-background-color:#bad0a6;");

    }

    @FXML
    void rollin4(MouseEvent event) {
        lesson_4.setStyle("-fx-background-color:#83baaa;");

    }

    @FXML
    void rollin5(MouseEvent event) {
        lesson_5.setStyle("-fx-background-color:#ffa9b1;");

    }

    @FXML
    void rollin6(MouseEvent event) {
        lesson_6.setStyle("-fx-background-color:#feb6a3;");

    }

    @FXML
    void rollout1(MouseEvent event) {
        lesson_1.setStyle("-fx-background-color:#7faacc;");
    }

    @FXML
    void rollout2(MouseEvent event) {
        lesson_2.setStyle("-fx-background-color:#1f5b7f;");

    }

    @FXML
    void rollout3(MouseEvent event) {
        lesson_3.setStyle("-fx-background-color:#75a24d;");

    }

    @FXML
    void rollout4(MouseEvent event) {
        lesson_4.setStyle("-fx-background-color:#077655;");

    }

    @FXML
    void rollout5(MouseEvent event) {
        lesson_5.setStyle("-fx-background-color:#ff5363;");

    }

    @FXML
    void rollout6(MouseEvent event) {
        lesson_6.setStyle("-fx-background-color:#fd6d48;");

    }

    @FXML
    private void goSync(ActionEvent event) {
        
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("SyncData.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            System.out.println("Error displaying sync class "+nn);
            
            nn.printStackTrace();
        }
    }

    @FXML
    private void goToLibrary(ActionEvent event) {
        
        //dbox.showMessageDialog(Alert.AlertType.INFORMATION, "Info", "Under Construction", "The library module is currently under development");
        
         try{
            Parent root1 = FXMLLoader.load(getClass().getResource("Library.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            System.out.println("Error displaying library class "+nn);
        }
        
    }

    @FXML
    private void profilePage(MouseEvent event) {
        
        if(LoginPage.atype.equals("STUDENT")||LoginPage.atype.equals("LEARNER")){
            dbox.showMessageDialog(Alert.AlertType.ERROR, dbError, null, access_denied);
        }
        else{
            try{
            Parent root1 = FXMLLoader.load(getClass().getResource("Profiles.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            System.out.println("Error displaying profilePage class "+nn);
        }
        }
    }
        
    
}
