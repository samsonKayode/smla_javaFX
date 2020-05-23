/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class SplashScreenController implements Initializable {
    @FXML
    private AnchorPane pane;
    
 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        loadSplash();
    }    
    
    public void loadSplash(){
        
        try{
            
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(5), pane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), pane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);
        
        fadeIn.play();
        
        fadeIn.setOnFinished((e) ->{
            
            fadeOut.play();
        });
        
        fadeOut.setOnFinished((e)->{
            try{
            Parent root1 = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            System.out.println("Error displaying MainPage class "+nn);
        }
            
            
        });
        }

        catch(Exception nn){
            System.out.println("ERROR HERE "+nn);
        }
    }
    
}
