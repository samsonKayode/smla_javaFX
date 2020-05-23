/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class SelectStudentTypeController implements Initializable {

    @FXML
    private JFXButton fbutton;

    @FXML
    private JFXButton nfbutton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void close(MouseEvent event) {
        
        FormalDashBoardController.profileStage.close();
    }

    @FXML
    private void goToFormal(ActionEvent event) {
        
        FormalDashBoardController.profileStage.close();
        
        try{
        
        Parent root1 = FXMLLoader.load(getClass().getResource("Courses.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
        
        }
        catch(Exception nn){
            System.out.println("Error loading formal courses page "+nn);
        }
    }

    @FXML
    private void goToFormal2(ActionEvent event) {
        
        FormalDashBoardController.profileStage.close();
        
        try{
        
        Parent root1 = FXMLLoader.load(getClass().getResource("NonFormalOption.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
        
        }
        catch(Exception nn){
            System.out.println("Error loading non formal courses page "+nn);
        }
    }

    @FXML
    private void rollout(MouseEvent event) {
        
        fbutton.setStyle("-fx-background-color:#FF5363;");
        
    }
 
    @FXML
    private void rollin(MouseEvent event) {
        
        fbutton.setStyle("-fx-background-color:transparent;");
        
        
    }
    
     @FXML
    private void rollout1(MouseEvent event) {
        
        
        nfbutton.setStyle("-fx-background-color: #FD6D4B;");
    }
 
    @FXML
    private void rollin1(MouseEvent event) {
        
        
        nfbutton.setStyle("-fx-background-color:transparent;");
        
    }
    
}
