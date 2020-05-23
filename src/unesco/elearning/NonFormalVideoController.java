/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.ContentSettings;
import unesco.elearning.controllers.LogCtrl;
import unesco.elearning.controllers.LoginPage;
import unesco.elearning.language.Language;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class NonFormalVideoController implements Initializable {
    
    @FXML
    private Label title;

    @FXML
    private MediaView myVideo;
    
    MediaPlayer mp;
    
    int stat =1;
    
    public static String LOC="";
    
    String actv="";
        String titl="";
        String term="";
        String item="";
        String fxmlLoc ="";
        
        DialogBoxes dbox = new DialogBoxes();
        LoginPage lpage = new LoginPage();
        LogCtrl lgctrl = new LogCtrl();
        
        File fx;
    @FXML
    private FontAwesomeIconView bck;
    @FXML
    private JFXButton play_button;
    @FXML
    private JFXButton pause_button;
    
    Language lang = new Language();
    
    String stop_lesson="Are you sure you want to stop playing this lesson";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        if(LOC.equals("NFL")){
            
            String s = NonFormalCoursesController.myLoc+"/"+NonFormalCoursesController.selItem+".mp4";
            fx = new File(s);
            
            title.setText(NonFormalOptionController.TITLE+"/"+NonFormalCoursesController.selItem);
            
        }else{
            
            String cLoc = ContentSettings.C_LOC+"/NON FORMAL"+FormalDashBoardController.lessonTitle+".mp4";
            
            System.out.println("CLOC NON FORMAL:::- "+cLoc);
            String s = FormalDashBoardController.playLesson+".mp4";
            fx = new File(cLoc);
            
            
            title.setText(FormalDashBoardController.lessonTitle);
        }
        
        
        if(MainPageController.STATUS.equals("Non Formal") && LoginPage.atype.equals("LEARNER"))
            {
                String FL = "lang/"+FXMLDocumentController.langValue.getValue()+".txt";
                
        
        stop_lesson = lang.translate(FL, "stopesson");
        
            }else{
            
        }
        
        
        Media media = new Media(fx.toURI().toString());
        mp = new MediaPlayer(media);
        
        DropShadow dropshadow = new DropShadow();
dropshadow.setOffsetY(5.0);
dropshadow.setOffsetX(5.0);
dropshadow.setColor(Color.BLACK);

//myVideo.setEffect(dropshadow);
        
        myVideo.setMediaPlayer(mp);
        
        mp.play();
        
pause_button.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
pause_button.setStyle("-fx-background-color: Red");
pause_button.setStyle("-fx-body-color: Black");
});


pause_button.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
pause_button.setStyle("-fx-background-color: Black");

});

play_button.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
play_button.setStyle("-fx-background-color: Red");
play_button.setStyle("-fx-body-color: Black");
});


play_button.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
play_button.setStyle("-fx-background-color: Black");

});
        
play_button.setVisible(false);

    }    
    
    @FXML
    void close(MouseEvent event) {

        if(LOC.equals("NFL")){
            actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" ended playing "+NonFormalCoursesController.selItem;
            titl = "NON-FORMAL";
            term=NonFormalOptionController.TITLE;
            item = NonFormalCoursesController.selItem;
            fxmlLoc = "NonFormalCourses.fxml";
        }
        
        else{
            
            actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" ended playing "+FormalDashBoardController.lessonTitle;
            titl = "NON-FORMAL";
            term= FormalDashBoardController.NFT;
            item = FormalDashBoardController.NFL;
            fxmlLoc = "FormalDashBoard.fxml";
            
        }
        
        Optional<ButtonType> result = dbox.showConfirmDialog(null, stop_lesson+" ?");
        
        if (result.get() == ButtonType.OK){
        
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        lgctrl.selectTime(LoginPage.uID, titl, term, item);
        
        mp.stop();

        
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource(fxmlLoc));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            
        }
        
        }
        else{
            
        }
        
    }

    @FXML
    private void play(ActionEvent event) {
        
        if(stat==1){
            //play_button.setVisible(false);
            
        }else{
        
            mp.play();
            stat=1;
            
            pause_button.setVisible(true);
            play_button.setVisible(false);
        }
        
    }

    @FXML
    private void pause(ActionEvent event) {
        
        
        if(stat==1){
            
            mp.pause();
            stat=0;
            play_button.setVisible(true);
            pause_button.setVisible(false);
        }
        
      
    }
    

    
}
