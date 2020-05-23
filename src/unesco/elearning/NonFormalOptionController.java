/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import unesco.elearning.controllers.LoginPage;
import unesco.elearning.language.Language;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class NonFormalOptionController implements Initializable {
    @FXML
    private FontAwesomeIconView bck;
    @FXML
    private Label student_name;
    @FXML
    private ImageView student_image;
    @FXML
    private Label student_status;
    @FXML
    private JFXButton b_literacy;
    @FXML
    private JFXButton b_numeracy;
    @FXML
    private JFXButton b_lifeskill;
    
    public static String TITLE="";
    
    Language lang = new Language();
    @FXML
    private Label less_title;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        student_name.setText(LoginPage.ufirstname+" "+LoginPage.ulastname);
        student_status.setText(LoginPage.ustype);
        student_image.setImage(LoginPage.scaledImages);
        
        if(MainPageController.STATUS.equals("Non Formal") && LoginPage.atype.equals("LEARNER"))
            {
                String FL = "lang/"+FXMLDocumentController.langValue.getValue()+".txt";
                
        
        String literacy = lang.translate(FL, "ReadAndWrite");
        b_literacy.setText(literacy);
        
        String nums = lang.translate(FL, "Numbers");
        b_numeracy.setText(nums);
        
        String skills = lang.translate(FL, "Skills");
        b_lifeskill.setText(skills);
        
        String tits = lang.translate(FL, "LssonTitle");
        less_title.setText(tits);
        
        
        
        
        
            }
        else{
            
        }
    }    

    @FXML
    private void back(MouseEvent event) {
        
        try{
        Parent root1 = FXMLLoader.load(getClass().getResource("FormalDashBoard.fxml"));

            UnescoELearning.mainWindow.setScene(new Scene(root1));
    }
        catch(Exception nn){
            System.out.println("Error going back");
        }
    }


    @FXML
    private void literacy(ActionEvent event) {
        
        TITLE="LITERACY";
        gotoClass();
        
    }


    @FXML
    private void numeracy(ActionEvent event) {
        
        TITLE="NUMERACY";
        gotoClass();
    }


    @FXML
    private void lifeskill(ActionEvent event) {
        TITLE="LIFE SKILL";
        gotoClass();
    }
    
    public void gotoClass(){
        
        //Go to class Here..
        
        try{
        Parent root1 = FXMLLoader.load(getClass().getResource("NonFormalCourses.fxml"));

            UnescoELearning.mainWindow.setScene(new Scene(root1));
    }
        catch(Exception nn){
            System.out.println("Error going back");
        }
        
        
    }
    
    
     @FXML
    void rollin1(MouseEvent event) {
        b_literacy.setStyle("-fx-background-color:#bfd4e5;");

    }

    @FXML
    void rollin2(MouseEvent event) {
        b_numeracy.setStyle("-fx-background-color:#8fadbf;");
    }

    @FXML
    void rollin3(MouseEvent event) {
        b_lifeskill.setStyle("-fx-background-color:#bad0a6;");

    }
    
    @FXML
    void rollout1(MouseEvent event) {
        b_literacy.setStyle("-fx-background-color:#7faacc;");
    }

    @FXML
    void rollout2(MouseEvent event) {
        b_numeracy.setStyle("-fx-background-color:#1f5b7f;");

    }

    @FXML
    void rollout3(MouseEvent event) {
        b_lifeskill.setStyle("-fx-background-color:#75a24d;");

    }
    
}
