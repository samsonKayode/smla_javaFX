/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning.alerts;

import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import unesco.elearning.UnescoELearning;

/**
 *
 * @author SAMSON KAYODE
 */
public class DialogBoxes {
    
    public Optional<ButtonType> showConfirmDialog(String header, String content)
    {
        //Image img = new Image("/unesco/elearning/image/LOGO_01.png");
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(header);
        alert.setContentText(content);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/unesco/elearning/image/icon.png").toString()));
        
        alert.initOwner(UnescoELearning.mainWindow);
        
        
        Optional<ButtonType> result = alert.showAndWait();
           
        
    // ... user chose CANCEL or closed the dialog

        return result;
        
    }
    
    //Show Normal Dialog...
    
    public void showMessageDialog(AlertType x, String title, String header, String content)
    {
        Image img = new Image("/unesco/elearning/image/LOGO_01.png");
        
        Alert alert = new Alert(x);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        //alert.setGraphic(new ImageView(img));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/unesco/elearning/image/icon.png").toString()));
       
        stage.setAlwaysOnTop(true);
        
        alert.initOwner(UnescoELearning.mainWindow);
        alert.showAndWait();
        
       
    }
    
    public void showInfoDialog(String title, String content){
        
    }
    
    public void showPassNotif(String title, String content)
    {
        Image img = new Image("/unesco/elearning/image/correct.png");
        
       Notifications.create()
                .title(title)
                .text(content)
               .graphic(new ImageView(img))
                .darkStyle()
                .hideAfter(Duration.seconds(2))
                .position(Pos.CENTER)
                .show();
    }
    
    public void showFailNotif(String title, String content)
    {
        Image img = new Image("/unesco/elearning/image/wrong1.png");
        
      Notifications.create()
                .title(title)
                .text(content)
              .graphic(new ImageView(img))
                .darkStyle()
                .hideAfter(Duration.seconds(2))
                .position(Pos.CENTER)
        .show();
    }    
    
}
