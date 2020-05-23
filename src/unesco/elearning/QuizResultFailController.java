/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.ContentSettings;
import unesco.elearning.controllers.LoginPage;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class QuizResultFailController implements Initializable {
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
    private Label m_score;
    @FXML
    private Label pscore;
    @FXML
    private VBox hPane;
    
    DecimalFormat df = new DecimalFormat("###,###,###.##");
    
    DialogBoxes dbox = new DialogBoxes();
    
    public static Stage profileStage;
    
    
    VBox nPane;
    Label nstudent_status, nlesson_name, nstudent_name, nm_score, npscore;
    ImageView nstudent_image;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nPane=hPane;
        
        nstudent_image = student_image;
        nstudent_name = student_name;
        nstudent_status = student_status;
        nlesson_name = lesson_name;
        nm_score = m_score;
        npscore = pscore;
            
        nlesson_name.setText(StartQuizController.rbuild);        
        nstudent_image.setImage(LoginPage.scaledImages);
        nstudent_name.setText(LoginPage.ufirstname+" "+LoginPage.ulastname);
        nstudent_status.setText(LoginPage.ustype);
        
        pscore.setText(String.valueOf(df.format(ContentSettings.dscore))+"%");
        //nm_score.setText(String.valueOf(df.format(StartQuizController.fscore))+"%");
        
        nm_score.setText(df.format(StartQuizController.fscore)+"%");
        
        System.out.println("FSCORE - "+StartQuizController.fscore);
        
        System.out.println("VECTOR SIZE:"+StartQuizController.vector.size());
        
        loadQuestions();
        
        StartQuizController.vector.setSize(0);
    }    

    @FXML
    private void back(MouseEvent event) {
        
        doneHere();
        
    }

    @FXML
    private void doneH(ActionEvent event) {
        
        doneHere();
    }
    
    public void loadQuestions(){
        
        Vector v = StartQuizController.vector;
        
        Image img;
        Label label;
        
        for(int i=0; i<StartQuizController.vector.size(); i++){
            
            String axx = String.valueOf(StartQuizController.vector.elementAt(i));
            System.out.println("AXX IS "+axx);
            
            String a = String.valueOf(StartQuizController.vector.elementAt(i));
            String ax[] = a.split("#");
            
            String ques = ax[0];
            String ans = ax[1];
            
            label = new Label(ques);
            
            if(ans.equals("YES")){
                img = new Image("/unesco/elearning/image/correct_tick.png");
            }else{
                img = new Image("/unesco/elearning/image/wrong_tick.png");
            }
            
            label.setGraphic(new ImageView(img));
            System.out.println(ques);
            hPane.getChildren().addAll(label);
        }
        
    }
    
    public void doneHere(){
        
        Parent root1;
        try {
            root1 = FXMLLoader.load(getClass().getResource("EndQuiz.fxml"));
            
            profileStage = new Stage();
            profileStage.initModality(Modality.APPLICATION_MODAL);
            profileStage.initStyle(StageStyle.UNDECORATED);
            profileStage.setScene(new Scene(root1));  
            profileStage.setAlwaysOnTop(true);
            profileStage.show();
            
           
            
        } catch (IOException ex) {
            Logger.getLogger(QuizResultFailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
