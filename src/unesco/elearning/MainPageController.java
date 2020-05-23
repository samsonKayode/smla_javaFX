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
public class MainPageController implements Initializable {
    
    public static String STATUS ="";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void login(ActionEvent event) {
        
        STATUS = "Non Formal";
        
        try{
                
            Parent root1 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

            UnescoELearning.mainWindow.setScene(new Scene(root1));
            //SMLA.stage.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            }
            catch(Exception io){
                System.out.println("Error displaying page.." +io);
                
                io.printStackTrace();
            }
    }
    
    @FXML
    private void login2(ActionEvent event) {
        
        STATUS = "Formal (JSS 2)";
        
        try{
                
            Parent root1 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

            UnescoELearning.mainWindow.setScene(new Scene(root1));
            //SMLA.stage.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            }
            catch(Exception io){
                System.out.println("Error displaying page.." +io);
                
                io.printStackTrace();
            }
    }
    
    
    
}
