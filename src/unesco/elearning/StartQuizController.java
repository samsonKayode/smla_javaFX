/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.ContentSettings;
import unesco.elearning.controllers.LogCtrl;
import unesco.elearning.controllers.LoginPage;
import unesco.elearning.language.Language;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class StartQuizController implements Initializable {
    @FXML
    private Label quizTitle;
    @FXML
    private FontAwesomeIconView bck;
    @FXML
    private  Label student_name;
    @FXML
    private ImageView student_image;
    @FXML
    private Label student_status;
    @FXML
    private Label ques;
    @FXML
    private JFXRadioButton opta;
    @FXML
    private JFXRadioButton optb;
    @FXML
    private JFXRadioButton optc;
    @FXML
    private JFXRadioButton optd;
    @FXML
    private JFXButton cont;
    
    ImageView nstudent_image;
    Label nstudent_name, nstudent_status, ntitle;
    
    LoginPage lpage = new LoginPage();
    Language lang = new Language();
    DialogBoxes dbox = new DialogBoxes();
    
    Label nques;
    JFXRadioButton nopta, noptb, noptc, noptd;
    
    public String ANSWER="", selAnswer="";
    
    public static String sques, sopta,soptb, soptc, soptd;

    public static int sn = 1;
    
    public static Stage notif;
    
    public static int score=0;
    
    public static double fscore=0.0;
    
    public static String lesson="", course="", term="";
    
    ToggleGroup group = new ToggleGroup();
    
    LogCtrl logs = new LogCtrl();
    
    public static Vector vector = new Vector();
    
    public static String whereIs="", rbuild="";
    
    DecimalFormat df = new DecimalFormat("###");
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        nstudent_image = student_image;
        nstudent_name = student_name;
        nstudent_status = student_status;
        
        ntitle = quizTitle;
        
        if(whereIs.equals("LESSON")){
            
            System.out.println("LESSON WHERE IS "+whereIs);
            
        String[] ccontent = ViewLessonController.quizPath.split("/");
        course = ccontent[1];
        term = ccontent[2];
        lesson = ccontent[3];
        
        ntitle.setText(ViewLessonController.quizPath); 
            
        }
        else{
            
            System.out.println("QUIZ WHERE IS "+whereIs);
            
       String[] ccontent = QuizController.rBuild.toString().split("/");
        course = ccontent[1];
        term = ccontent[2];
        lesson = ccontent[3];
        
        ntitle.setText(QuizController.rBuild.toString()); 
        }
        
               
        nstudent_image.setImage(LoginPage.scaledImages);
        nstudent_name.setText(LoginPage.ufirstname+" "+LoginPage.ulastname);
        nstudent_status.setText(LoginPage.ustype);
        
        nques = ques;
        nopta = opta;
        noptb=optb;
        noptc = optc;
        noptd = optd;
        
        nopta.setToggleGroup(group);
        noptb.setToggleGroup(group);
        noptc.setToggleGroup(group);
        noptd.setToggleGroup(group);
        
        loadQuestion(sn);
        
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
        @Override
        public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

            RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
            selAnswer = chk.getText();

        }
    });
        
    }    

    @FXML
    private void back(MouseEvent event) {
       
        
        
        
        Optional<ButtonType> result = dbox.showConfirmDialog(null, "Are you sure you want to cancel the quiz page ?");
            if (result.get() == ButtonType.OK){
                
                vector.setSize(0);
        
        if(whereIs.equals("LESSON")){
            
            rbuild = ViewLessonController.quizPath;
            
        }else{
            rbuild = QuizController.rBuild.toString();
        }
                
        ntitle.setText("");
        
                String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" exited quiz "+rbuild+" without finishing.";
                String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
                
                lpage.loginLog(LoginPage.uID, actv, l_name);
                
                sn=1;
    
                try{
        Parent root1 = FXMLLoader.load(getClass().getResource("FormalDashBoard.fxml"));

            UnescoELearning.mainWindow.setScene(new Scene(root1));
    }
        catch(Exception nn){
            System.out.println("Error going back");
        }
                
                
        } else {
        // ... user chose CANCEL or closed the dialog
        }
    }

    @FXML
    private void goNext(ActionEvent event) {

        sn = sn+1;

        //check if answer is correct..
        
        System.out.println("ANSWER IS - "+ANSWER);
        System.out.println("selANSWER IS - "+selAnswer);
        
        if(selAnswer.equals(ANSWER)){
            System.out.println("Answer correct");
            
            score = score +1;
            
            System.out.println("Score is "+score);
            
            //dbox.showPassNotif(null, "CORRECT ANSWER!");
            
            vector.add(nques.getText()+"#YES");
            
            loadQuestion(sn);
            
        }
        else{
            //Answer not correct
            System.out.println("Answer Not correct");
            
            //dbox.showFailNotif(null, "WRONG ANSWER");
            vector.add(nques.getText()+"#NO");
            loadQuestion(sn);
        
        }     
        
    }
    
    public void loadQuestion(int i){

        String loc = "";
        
        //vector.setSize(0);
        
        
        if(whereIs.equals("LESSON")){
            
            loc = ViewLessonController.quizFile;
             rbuild = ViewLessonController.quizPath;
            
        }else{
            loc = QuizController.path;
            rbuild = QuizController.rBuild.toString();
        }
        
        
        //String loc = QuizController.path;
        
        System.out.println("THE LOC IS "+loc);
        
        nques = ques;
        nopta = opta;
        noptb=optb;
        noptc = optc;
        noptd = optd;
        
        //Question
        sques = lang.translate(loc, "QUES"+String.valueOf(i));
        sopta = lang.translate(loc, "OPTA"+String.valueOf(i));
        soptb = lang.translate(loc, "OPTB"+String.valueOf(i));
        soptc = lang.translate(loc, "OPTC"+String.valueOf(i));
        soptd = lang.translate(loc, "OPTD"+String.valueOf(i));
        
        ANSWER = lang.translate(loc, "ANS"+String.valueOf(i));

        
        if(sques.length()==0){
            
            int fsn = sn-1;
            
            fscore = (Float.parseFloat(String.valueOf(score))/fsn)*100;
            
            System.out.println("FSCORE COMP IS "+fscore);

            if(fscore>=ContentSettings.dscore){
                                
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" completed quiz "+rbuild+". PASSED ";
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        //Save quiz data..
            
               int x = logs.saveQuizResult(LoginPage.uID, course, term, lesson, Float.parseFloat(df.format(fscore)));
                
               if(x==0){
                   
                   try{
        
        Parent root1 = FXMLLoader.load(getClass().getResource("QuizResult.fxml"));

            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
        
        }
        catch(Exception nn){
            System.out.println("Error loading quiz success page "+nn);
            nn.printStackTrace();
        }
               }
             
                
            }else{
                //You failed
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" completed quiz "+rbuild+". FAILED ";
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv, l_name);
                
            ///save quiz data..
        
        int x = logs.saveQuizResult(LoginPage.uID, course, term, lesson, Float.parseFloat(df.format(fscore)));
                
               if(x==0){
                   
                   try{
        
        Parent root1 = FXMLLoader.load(getClass().getResource("QuizResultFail.fxml"));

            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
        
        }
        catch(Exception nn){
            System.out.println("Error loading quiz fail page "+nn);
        }
               }
        
            }

            sn = 1;
            score = 0;

        }
        
        else{
            
        nques.setText(i+". "+ sques);
        nopta.setText(sopta);
        noptb.setText(soptb);
        noptc.setText(soptc);
        noptd.setText(soptd);
        
        nopta.setSelected(false);
        noptb.setSelected(false);
        noptc.setSelected(false);
        noptd.setSelected(false);
            
        }
        
    }
    
}
