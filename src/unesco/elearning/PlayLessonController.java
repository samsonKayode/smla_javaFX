/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.LogCtrl;
import unesco.elearning.controllers.LoginPage;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class PlayLessonController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label lesson_name;

    @FXML
    private WebView webview;
    
    WebView nwebView;
    
    Label nname;

    DialogBoxes dbox = new DialogBoxes();
    LoginPage lpage = new LoginPage();
    
    LogCtrl lgctrl = new LogCtrl();
    
    @FXML
    void close(MouseEvent event) {

        Optional<ButtonType> result = dbox.showConfirmDialog(null, "Are you sure you want to close the player ?");
        
        if (result.get() == ButtonType.OK){
            
            String all[] = FormalDashBoardController.lessonTitle.split("/");
            String a1 = all[0];
            String a2 = all[1];
            String a3 = all[2];
            
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" ended playing "+a3+" of "+a2+", "+a1;
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        lgctrl.selectTime(LoginPage.uID, a1, a2, a3);
            
        webview.getEngine().load(null);
        //CourseListController.lessonStage.close();
        
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FormalDashBoard.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            
        }
        
        }
        else{
            
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nname = lesson_name;
        
        String all[] = FormalDashBoardController.lessonTitle.split("/");
            String a1 = all[0];
            String a2 = all[1];
            String a3 = all[2];
            
            nname.setText(FormalDashBoardController.lessonTitle);
            
            lgctrl.accessLesson(LoginPage.uID, a1, a2, a3);
            
            nwebView = webview;
        
        WebEngine webEngine = nwebView.getEngine();
        
        webEngine.getLoadWorker().stateProperty()
        .addListener(new ChangeListener<Worker.State>() {
          @Override
          public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {

            if (newState == Worker.State.SUCCEEDED) {
              //stage.setTitle(webEngine.getLocation());
            }

          }
        });
        String s =FormalDashBoardController.playLesson+"/index.html";
        
        System.out.println("S IS "+s);
        
        File f = new File(s);
        
        try {
        nwebView.getEngine().load(f.toURI().toURL().toString());
    } catch (MalformedURLException ex) {
        
        System.out.println("ERROR "+ex);
    }
        
    }    
    
}
