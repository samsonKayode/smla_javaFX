/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
public class NonFormalCoursesController implements Initializable {
    
    Language lang = new Language();
    @FXML
    private Label lesson_name;
    @FXML
    private FontAwesomeIconView bck;
    @FXML
    private Label student_name;
    @FXML
    private ImageView student_image;
    @FXML
    private Label student_status;
    @FXML
    private Label availableLessons;
    @FXML
    private JFXListView<String> listItem;
    @FXML
    private JFXButton play;
    @FXML
    private JFXButton pin;
    
    JFXListView nlistItem;
    
    LoginPage lpage = new LoginPage();
    
    public static String myLoc="", cLoc="";
    
    public static String selItem="";
    
    public static Stage courseStage;
    public static String xLoc="";
    
    LogCtrl lgctrl = new LogCtrl();
    ContentSettings cset = new ContentSettings();
    DialogBoxes dbox = new DialogBoxes();
    
    JFXButton nplay, npin;
    
    String data_error="Error accessing content data";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nlistItem = listItem;
        //nstudent_image = student_image;
        //nstudent_name = student_name;
        //nstudent_status = student_status;
        
        student_image.setImage(LoginPage.scaledImages);
        student_name.setText(LoginPage.ufirstname+" "+LoginPage.ulastname);
        student_status.setText(LoginPage.ustype);
        
        if(MainPageController.STATUS.equals("Non Formal") && LoginPage.atype.equals("LEARNER"))
            {
                String FL = "lang/"+FXMLDocumentController.langValue.getValue()+".txt";
                
        
        String ava = lang.translate(FL, "LssonTitle");
        
        lesson_name.setText(ava);
        availableLessons.setText(ava);
        
        String playz = lang.translate(FL, "play");
        play.setText(playz);
        
        String save = lang.translate(FL, "save");
        pin.setText(save);
        
        data_error = lang.translate(FL, "dataError");
        
            }
        else{
            
        }
        
        int res = cset.getContentDirectory();
        
        if(res==0){
            cLoc = ContentSettings.C_LOC;
            myLoc = cLoc+"/NON FORMAL/"+NonFormalOptionController.TITLE;
        }
        else{
            //show error loading content
            dbox.showMessageDialog(Alert.AlertType.ERROR, FormalDashBoardController.dbError, null, data_error);
        }
        
        List<String> lst = cset.loadNonFormal(myLoc);
        ObservableList obList = FXCollections.observableList(lst);
        
        nlistItem.getItems().addAll(obList);
        
        nlistItem.setStyle("-fx-font-size: 15; -fx-font-weight: BOLD;");
        
        
    }    

    @FXML
    private void back(MouseEvent event) {
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("NonFormalOption.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            
        }
    }

    @FXML
    private void showVideo(MouseEvent event) {
        
            if(event.getClickCount()==2){
        
        String abc = listItem.getSelectionModel().getSelectedItem();
        
        if(abc==null){
            
        }
        else{
        
        String[] selItemc = abc.split("/");
        selItem = selItemc[1];
        
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" accessed "+selItem;
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv, l_name);        
        
        NonFormalVideoController.LOC ="NFL";
        
        try{
            
            lgctrl.accessLesson(LoginPage.uID, "NON-FORMAL", NonFormalOptionController.TITLE, selItem);
            
            Parent root1 = FXMLLoader.load(getClass().getResource("NonFormalVideo.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            nn.printStackTrace();
        }
        }
        }
        else
        {
            String abc = listItem.getSelectionModel().getSelectedItem();
        
            if(abc==null){
                
            }else{
            
        String[] selItemc = abc.split("/");
        selItem = selItemc[1];
            
            if(selItem.length()>5){
                nplay = play;
                npin = pin;
            nplay.setDisable(false);
            npin.setDisable(false);
            }else{
                
            }
            
            
        }

    }
    }

    @FXML
    private void play(ActionEvent event) {
        
        NonFormalVideoController.LOC ="NFL";
        
        nlistItem = listItem;
        
        String abc = listItem.getSelectionModel().getSelectedItem();
        
        String[] selItemc = abc.split("/");
        selItem = selItemc[1];
        
        
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" accessed "+selItem;
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        try{
            
            lgctrl.accessLesson(LoginPage.uID, "NON-FORMAL", NonFormalOptionController.TITLE, selItem);
            
            Parent root1 = FXMLLoader.load(getClass().getResource("NonFormalVideo.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            nn.printStackTrace();
        }
    }

    @FXML
    private void pin(ActionEvent event) {
        nlistItem = listItem;
        
        String abc = listItem.getSelectionModel().getSelectedItem();
        
        String[] selItemc = abc.split("/");
        selItem = selItemc[1];
        
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" Pinned "+selItem+" to home page.";
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        filePin();
    }
    
    public void filePin(){
        
        PinLessonController.xLoc="NON FORMAL";
        myLoc = ContentSettings.C_LOC+"/PINNED LESSONS/"+LoginPage.uID+".txt";
        
        File fl = new File(myLoc);
        
        if(fl.exists()){
            
            showPinMenu();
            
        }
        
        else{
            writeNewFile();
            showPinMenu();
        }
    }
    
    public void writeNewFile() {      
    PrintWriter fw = null;
    try {
        fw = new PrintWriter(myLoc);
        BufferedWriter bw = new BufferedWriter(new FileWriter(myLoc));
        bw.write("LESS1 #EMPTY");
        bw.newLine();
        bw.write("LESS2 #EMPTY");
        bw.newLine();
        bw.write("LESS3 #EMPTY");
        bw.newLine();
        bw.write("LESS4 #EMPTY");
        bw.newLine();
        bw.write("LESS5 #EMPTY");
        bw.newLine();
        bw.write("LESS6 #EMPTY");
        bw.close();
    } catch (IOException e) {
        e.printStackTrace();
        
    }
    
}
    
    public void showPinMenu(){
        
        Parent root1;
        try {
            root1 = FXMLLoader.load(getClass().getResource("PinLesson.fxml"));
            
            courseStage = new Stage();
            courseStage.initModality(Modality.APPLICATION_MODAL);
            courseStage.initStyle(StageStyle.UNDECORATED);
            courseStage.setScene(new Scene(root1));  
            courseStage.setAlwaysOnTop(true);
            courseStage.show(); 
        } catch (IOException ex) {
            Logger.getLogger(NonFormalCoursesController.class.getName()).log(Level.SEVERE, null, ex);
            
            ex.printStackTrace();
        }
        catch(Exception nn){
            nn.printStackTrace();
        }
        
    }

}