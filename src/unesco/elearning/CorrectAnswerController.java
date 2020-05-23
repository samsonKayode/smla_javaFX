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
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class CorrectAnswerController implements Initializable {
    @FXML
    private JFXButton close;

    /**
     * Initializes the controller class.
     */
    
    StartQuizController sqc = new StartQuizController();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void close(ActionEvent event) {
        
        StartQuizController.notif.close();
        //StartQuizController.group.selectToggle(null);
        
        
    }
    
}
