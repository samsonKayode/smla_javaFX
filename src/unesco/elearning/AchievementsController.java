/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import unesco.elearning.controllers.ContentSettings;
import unesco.elearning.controllers.LogCtrl;
import unesco.elearning.controllers.LoginPage;
import unesco.elearning.language.Language;
import unesco.elearning.model.LessonHistory;
import unesco.elearning.model.QuizHistory;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class AchievementsController implements Initializable {

    @FXML
    private Label lesson_name;

    @FXML
    private FontAwesomeIconView bck;

    @FXML
    private Label student_name;

    @FXML
    private ImageView student_image;

    @FXML
    private Label student_status;

    @FXML
    private TableView<LessonHistory> lessonTable;

    @FXML
    private TableColumn<LessonHistory, String> ldate;

    @FXML
    private TableColumn<LessonHistory, String> lcourse;

    @FXML
    private TableColumn<LessonHistory, String> lterm;

    @FXML
    private TableColumn<LessonHistory, String> llesson;

    @FXML
    private TableColumn<LessonHistory, String> lduration;

    @FXML
    private Label accessed;

    @FXML
    private Label incomplete;

    @FXML
    private TableView<QuizHistory> quizTable;

    @FXML
    private TableColumn<QuizHistory, String> qdate;

    @FXML
    private TableColumn<QuizHistory, String> qcourse;

    @FXML
    private TableColumn<QuizHistory, String> qterm;

    @FXML
    private TableColumn<QuizHistory, String> qlesson;

    @FXML
    private TableColumn<QuizHistory, Integer> qscore;

    @FXML
    private TableColumn<QuizHistory, String> qstatus;

    @FXML
    private Label attempts;

    @FXML
    private Label passed;

    @FXML
    private Label failed;

    @FXML
    private Label lessonsCompleted;

    @FXML
    private Label availableQuiz;

    @FXML
    private Label completedQuiz;

    @FXML
    private Label hoursSpent;
    
    Label nattempts, npassed, nfailed, nlessonsCompleted, navailableQuiz, ncompletedQuiz, nhoursSpent;
    
    Label naccessed, nincmplete, nstudent_status, nstudent_name;
    
    ImageView nstudent_image;

    String stID="";
    
    Image stImg;
    String stName, stType;
    
    LogCtrl lgctrl = new LogCtrl();
    Language lang = new Language();
    @FXML
    private Label lc1;
    @FXML
    private Label lc2;
    @FXML
    private Label a_quiz;
    @FXML
    private Label time_spent;
    @FXML
    private Label c_quiz;
    @FXML
    private Label a_lesson;
    @FXML
    private Label i_lesson;
    @FXML
    private Label passes;
    @FXML
    private Label fails;
    @FXML
    private Label i_attempt;
    @FXML
    private Label LAL;
    @FXML
    private Label LAQ;
    
    @FXML
    void back(MouseEvent event) {
        
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FormalDashBoard.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nattempts =attempts;
        npassed = passed;
        nfailed = failed; 
        nlessonsCompleted = lessonsCompleted;
        navailableQuiz = availableQuiz;
        ncompletedQuiz = completedQuiz;
        nhoursSpent = hoursSpent;
    
        naccessed = accessed;
        nincmplete = incomplete;
        nstudent_status = student_status;
        nstudent_name = student_name;
        
        if(LoginPage.atype.equals("STUDENT")||LoginPage.atype.equals("LEARNER")){
            stID = LoginPage.uID;
            stName = LoginPage.ufirstname +" "+LoginPage.ulastname;
            stType = LoginPage.ustype;
            stImg = LoginPage.scaledImages;
        }
        else{
            
            stID = FindStudentsController.stID;
            stName = FindStudentsController.stName;
            stType = FindStudentsController.stType;
            stImg = FindStudentsController.stImg;
            
        }
        
            student_name.setText(stName);
            student_status.setText(stType);
            student_image.setImage(stImg);
            
            //Hours spent..
            
            String str = lgctrl.hoursSpent(stID);
            nhoursSpent.setText(str);
            
            ContentSettings.resx=0;
            int x = ContentSettings.countQuizFile(ContentSettings.C_LOC+"/QUIZZES");
            navailableQuiz.setText(String.valueOf(x));
            
            //Lesson History
            
            ldate.setCellValueFactory(new PropertyValueFactory<>("date"));
            lcourse.setCellValueFactory(new PropertyValueFactory<>("course"));
            lterm.setCellValueFactory(new PropertyValueFactory<>("term"));
            llesson.setCellValueFactory(new PropertyValueFactory<>("lesson"));
            lduration.setCellValueFactory(new PropertyValueFactory<>("timeSpent"));

            List<LessonHistory> lst = lgctrl.loadHistory(stID, 0);
            ObservableList obList = FXCollections.observableArrayList(lst);
            lessonTable.getItems().addAll(obList);

            List<LessonHistory> cmlst = lgctrl.loadHistory(stID, 120);
            
            naccessed.setText(String.valueOf(lst.size()));
            nlessonsCompleted.setText(String.valueOf(cmlst.size()));
            
            nincmplete.setText(String.valueOf(lst.size()-cmlst.size()));
            
            //Quiz History..

            qdate.setCellValueFactory(new PropertyValueFactory<>("date"));
            qcourse.setCellValueFactory(new PropertyValueFactory<>("course"));
            qterm.setCellValueFactory(new PropertyValueFactory<>("term"));
            qlesson.setCellValueFactory(new PropertyValueFactory<>("lesson"));
            qscore.setCellValueFactory(new PropertyValueFactory<>("score"));
            qstatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            List<QuizHistory> qlst = lgctrl.loadQuizHistory(stID, ContentSettings.dscore);
            ObservableList qobList = FXCollections.observableArrayList(qlst);
        
            quizTable.getItems().addAll(qobList);
            
            int qpassed = lgctrl.quizPassed(stID, ContentSettings.dscore, ">=");
            npassed.setText(String.valueOf(qpassed));
            
            int qfailed = lgctrl.quizPassed(stID, ContentSettings.dscore, "<");
            nfailed.setText(String.valueOf(qfailed));
            
            nattempts.setText(String.valueOf(qlst.size()));
            
            int comQuiz = lgctrl.quizPassed(stID, 0, ">=");
            
            ncompletedQuiz.setText(String.valueOf(comQuiz));
            
            //Translation..
            
            if(MainPageController.STATUS.equals("Non Formal") && LoginPage.atype.equals("LEARNER"))
            {
                String FL = "lang/"+FXMLDocumentController.langValue.getValue()+".txt";
                
        String ava = lang.translate(FL, "achieve");
        lesson_name.setText(ava);
        
        String tsp = lang.translate(FL, "timeSpent");
        time_spent.setText(tsp);
        
        String aq = lang.translate(FL, "quizes");
        a_quiz.setText(aq);
        LAQ.setText(aq);
        
        String cq = lang.translate(FL, "quizCompleted");
        c_quiz.setText(cq);
        
        String lc11 = lang.translate(FL, "Lesson");
        lc1.setText(lc11);
        
        LAL.setText(lc11);
        
        String lc12 = lang.translate(FL, "quizCompleted");
        lc2.setText(lc12);
        
        String dates = lang.translate(FL, "Date");
        ldate.setText(dates);
        qdate.setText(dates);
        
        String crs1 = lang.translate(FL, "courQ");
        lcourse.setText(crs1);
        qcourse.setText(crs1);
        
        //String lsn = lang.translate(FL, "Lesson");
        llesson.setText(lc11);
        qlesson.setText(lc11);
        
        String trm = lang.translate(FL, "Term");
        lterm.setText(trm);
        qterm.setText(trm);
        
        String tm = lang.translate(FL, "TIME");
        lduration.setText(tm);
        
        String scr = lang.translate(FL, "SCORE");
        qscore.setText(scr);
        
        String STS = lang.translate(FL, "STATUS");
        qstatus.setText(STS);
        
        String INC = lang.translate(FL, "INCOMPLETE");
        i_lesson.setText(INC);
        
        String FAIL = lang.translate(FL, "FAILS");
        fails.setText(FAIL);
        
        String SUCCESS = lang.translate(FL, "SUCCESS");
        passes.setText(SUCCESS);
        
        String ACL = lang.translate(FL, "COMPLETED");
        a_lesson.setText(ACL);
        
        String ATM = lang.translate(FL, "ATTEMPTS");
        i_attempt.setText(ATM);
        
        
            }
        else{
            
        }
            
        // TODO
    }
    
    
}
