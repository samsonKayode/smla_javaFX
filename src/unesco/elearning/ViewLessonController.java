/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import static unesco.elearning.CourseListController.selItem;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.ContentSettings;
import unesco.elearning.controllers.LogCtrl;
import unesco.elearning.controllers.LoginPage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class ViewLessonController implements Initializable {

    @FXML
    private Label nowplaying;

    @FXML
    private JFXButton quizbutton;
    
    @FXML
    private WebView webView;

    public WebView nwebView;
    
    @FXML
    private FontAwesomeIconView close;
    
    LoginPage lpage = new LoginPage();
    
    DialogBoxes dbox = new DialogBoxes();
    
    public static String quizFile ="", quizPath="";
    
    public static float dscore=0;
    public static String playLoc="";
    
    LogCtrl lgctrl = new LogCtrl();
    
    
    
    @FXML
    void close(MouseEvent event) {
        String actv="";
        String title="";
        String term="";
        String item="";
        String fxmlLoc ="";
        
        if(playLoc.equals("FORMAL")){
             actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" ended playing "+selItem+" of "+SelectTermController.cxTerm+", "+CoursesController.title;
             title = CoursesController.title;
             term=SelectTermController.cxTerm;
             item =selItem;
             fxmlLoc="CourseList.fxml";
        }
        
        else{
            actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" ended playing "+NonFormalCoursesController.selItem;
            title = "NON-FORMAL";
            term="N-A";
            item = NonFormalCoursesController.selItem;
            fxmlLoc = "NonFormalCourses.fxml";
        }
        
        Optional<ButtonType> result = dbox.showConfirmDialog(null, "Are you sure you want to close the player ?");
        
        if (result.get() == ButtonType.OK){
        
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        lgctrl.selectTime(LoginPage.uID, title, term, item);
            
        webView.getEngine().load(null);
        //CourseListController.lessonStage.close();
        
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource(fxmlLoc));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            
        }
        
        }
        else{
            
        }
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nwebView = webView;

        WebEngine webEngine = nwebView.getEngine();
        
        webEngine.getLoadWorker().stateProperty()
        .addListener(new ChangeListener<Worker.State>() {
          @Override
          public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {

            if (newState == Worker.State.SUCCEEDED) {
              //stage.setTitle(webEngine.getLocation());
            }

          }
        });
        
        String s="";
        
        quizbutton.setVisible(false);
        
        if(playLoc.equals("FORMAL")){
            s =CourseListController.ncLoc+"/"+CourseListController.selItem+"/index.html";
            nowplaying.setText(CourseListController.selItem);
            //quizbutton.isVisible();
            //quizbutton.setVisible(true);
        }
        else{
            s = NonFormalCoursesController.myLoc+"/"+NonFormalCoursesController.selItem+"/index.html";
            nowplaying.setText(NonFormalCoursesController.selItem);
            
        }

        System.out.println("S IS "+s);
        
        File f = new File(s);
        
        try {
        nwebView.getEngine().load(f.toURI().toURL().toString());
    } catch (MalformedURLException ex) {
        
        System.out.println("ERROR "+ex);
    }
        
        Timer buttonTimer = new Timer();
        
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                
                quizbutton.setVisible(true);

            }
        }, 120000);
        
        // TODO
        
    }
    
    @FXML
    void gotoQuiz(ActionEvent event) {
        
        Optional<ButtonType> result = dbox.showConfirmDialog(null, "Are you sure you want to end the lesson and go to quiz now ?");
        
        if (result.get() == ButtonType.OK){
            
            webView.getEngine().load(null);
        
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" ended playing "+selItem+" of "+SelectTermController.cxTerm+", "+CoursesController.title;
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        lgctrl.selectTime(LoginPage.uID, CoursesController.title, SelectTermController.cxTerm, selItem);

        //check if quiz file exist..
        
        StartQuizController.whereIs="LESSON";
        
        quizFile = ContentSettings.C_LOC+"/QUIZZES/"+SelectTermController.termSelected+"/"+CourseListController.selItem+".txt";
        System.out.println("QUIZ FILE IS "+quizFile);
        
        quizPath ="/"+SelectTermController.termSelected+"/"+CourseListController.selItem;
        
        dscore = ContentSettings.dscore;
        
        File fl = new File(quizFile);
        if(fl.exists()){
            
            System.out.println("i got here 1");
            
        String actv1 = LoginPage.ufirstname+" "+LoginPage.ulastname+" started quiz "+quizPath;
        String l_name1 = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv1, l_name1);
            
            try{
                
            Parent root1 = FXMLLoader.load(getClass().getResource("StartQuiz.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            System.out.println("ERROR LOADING QUIZ PAGE "+nn);
            nn.printStackTrace();
                   
        }
            
            
            
        }
        else{
            //Quiz file does not exist...
            
            dbox.showMessageDialog(Alert.AlertType.INFORMATION, "Info", null, "No Quiz Available for this lesson");
            
            
            try{
            Parent root1 = FXMLLoader.load(getClass().getResource("CoursesList.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            
        }
        }
        }
        
        else{
            
        }
        
        
        
    }
    
}
