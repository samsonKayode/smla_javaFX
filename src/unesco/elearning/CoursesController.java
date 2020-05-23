/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import unesco.elearning.controllers.LoginPage;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class CoursesController implements Initializable {
    @FXML
    private FontAwesomeIconView bck;
    @FXML
    private Label student_name;
    @FXML
    private ImageView student_image;
    @FXML
    private Label student_status;
    @FXML
    private JFXButton maths;
    @FXML
    private JFXButton english;
    @FXML
    private JFXButton science;
    @FXML
    private JFXButton ict;
    @FXML
    private JFXButton tech;
    @FXML
    private JFXButton phe;
    
    public static String title;
    public static Stage termStage;
    
    LoginPage lpage = new LoginPage();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //load student profile..
        
        lpage.loadData();
        student_name.setText(LoginPage.ufirstname+" "+LoginPage.ulastname);
        student_status.setText(LoginPage.ustype);
        student_image.setImage(LoginPage.scaledImages);
        
        // TODO
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
    private void maths(ActionEvent event) {
        
        title = "MATHEMATICS";
        selectTerm();
    }

    @FXML
    private void english(ActionEvent event) {
        title = "ENGLISH";
        selectTerm();
    }

    @FXML
    private void science(ActionEvent event) {
        title = "BASIC SCIENCE";
        selectTerm();
    }

    @FXML
    private void ict(ActionEvent event) {
        title = "BASIC ICT";
        selectTerm();
    }

    @FXML
    private void tech(ActionEvent event) {
        title = "BASIC TECH";
        
        //SelectTermController.loadData(title);
        //SelectTermController.cxTitle.setText(title);
        selectTerm();
        
    }

    @FXML
    private void phe(ActionEvent event) {
        title = "PHE";
        selectTerm();
    }
    
    private void selectTerm(){
        
        Parent root1;
        try {
            root1 = FXMLLoader.load(getClass().getResource("SelectTerm.fxml"));
            
            termStage = new Stage();
            termStage.initModality(Modality.APPLICATION_MODAL);
            termStage.initStyle(StageStyle.UNDECORATED);
            termStage.setScene(new Scene(root1));
            termStage.setAlwaysOnTop(true);
            termStage.show();
            
           
            
        } catch (IOException ex) {
            Logger.getLogger(FormalDashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Rollover effects...
    
    @FXML
    void rollin1(MouseEvent event) {
        maths.setStyle("-fx-background-color:#bfd4e5;");

    }

    @FXML
    void rollin2(MouseEvent event) {
        english.setStyle("-fx-background-color:#8fadbf;");
    }

    @FXML
    void rollin3(MouseEvent event) {
        science.setStyle("-fx-background-color:#bad0a6;");

    }

    @FXML
    void rollin4(MouseEvent event) {
        ict.setStyle("-fx-background-color:#83baaa;");

    }

    @FXML
    void rollin5(MouseEvent event) {
        tech.setStyle("-fx-background-color:#ffa9b1;");

    }

    @FXML
    void rollin6(MouseEvent event) {
        phe.setStyle("-fx-background-color:#feb6a3;");

    }

    @FXML
    void rollout1(MouseEvent event) {
        maths.setStyle("-fx-background-color:#7faacc;");
    }

    @FXML
    void rollout2(MouseEvent event) {
        english.setStyle("-fx-background-color:#1f5b7f;");

    }

    @FXML
    void rollout3(MouseEvent event) {
        science.setStyle("-fx-background-color:#75a24d;");

    }

    @FXML
    void rollout4(MouseEvent event) {
        ict.setStyle("-fx-background-color:#077655;");

    }

    @FXML
    void rollout5(MouseEvent event) {
        tech.setStyle("-fx-background-color:#ff5363;");

    }

    @FXML
    void rollout6(MouseEvent event) {
        phe.setStyle("-fx-background-color:#fd6d48;");

    }
    
}
