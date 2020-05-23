/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class EndQuizController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void retake(ActionEvent event) {
        
        QuizResultFailController.profileStage.close();
        
        try{
        
        Parent root1 = FXMLLoader.load(getClass().getResource("StartQuiz.fxml"));

            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
        
        }
        catch(Exception nn){
            System.out.println("Error loading quiz page "+nn);
        }
    }

    @FXML
    private void dash(ActionEvent event) {
        
        QuizResultFailController.profileStage.close();
        
        try{
        
        Parent root1 = FXMLLoader.load(getClass().getResource("FormalDashBoard.fxml"));

            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
        
        }
        catch(Exception nn){
            System.out.println("Error loading quiz page "+nn);
        }
    }
    
}
