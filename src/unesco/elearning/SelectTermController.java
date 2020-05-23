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
import javafx.scene.input.MouseEvent;
import unesco.elearning.controllers.LoginPage;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class SelectTermController implements Initializable {
    @FXML
    private JFXButton term1;
    @FXML
    private JFXButton term2;
    @FXML
    private JFXButton term3;
    @FXML
    private FontAwesomeIconView close;
    @FXML
    private  Label c_title;
    
    public static String termSelected="", cxTerm="";
    
    public static Label cxTitle;
    
    LoginPage lpage = new LoginPage();
    
    //public String mama;
   

    /**
     * Initializes the controller class.
     */    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cxTitle = c_title;
        
       cxTitle.setText(CoursesController.title);
        
    }    

    @FXML
    private void term1(ActionEvent event) {
        
        termSelected= CoursesController.title+"/1ST TERM";
        cxTerm = "1ST TERM";
        
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" accessed "+cxTerm+" "+CoursesController.title;
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        closeAndChange();
        
    }

    @FXML
    private void term2(ActionEvent event) {
        
        termSelected = CoursesController.title+"/2ND TERM";
        cxTerm = "2ND TERM";
        
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" accessed "+cxTerm+" "+CoursesController.title;
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        closeAndChange();
        
    }

    @FXML
    private void term3(ActionEvent event) {
        
        
        termSelected = CoursesController.title+"/3RD TERM";
        cxTerm = "3RD TERM";
        
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" accessed "+cxTerm+" "+CoursesController.title;
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        closeAndChange();
    }
    
    public void closeAndChange(){
        
        try{
            
            CoursesController.termStage.close();
            
            Parent root1 = FXMLLoader.load(getClass().getResource("CourseList.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
        }
        catch(Exception nn){
            
        }
        
        
    }

    @FXML
    private void close(MouseEvent event) {
        CoursesController.termStage.close();
    }
    
}
