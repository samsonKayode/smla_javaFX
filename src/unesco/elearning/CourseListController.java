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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import static unesco.elearning.NonFormalCoursesController.courseStage;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.ContentSettings;
import unesco.elearning.controllers.LogCtrl;
import unesco.elearning.controllers.LoginPage;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class CourseListController implements Initializable {
    
    @FXML
    private FontAwesomeIconView bck;

    @FXML
    private Label student_name;

    @FXML
    private ImageView student_image;

    @FXML
    private Label student_status;

    @FXML
    private JFXListView<String> listItem;
    
    @FXML
    private Label lesson_name;
    @FXML
    private Label term;
    
    @FXML
    private JFXButton play;

    @FXML
    private JFXButton pin;
    
    public JFXButton nplay, npin;
    
    public static String myLoc="";
    
    public JFXListView<String> nlistItem;
    
    ContentSettings cset = new ContentSettings();
    LoginPage lpage = new LoginPage();
    DialogBoxes dbox = new DialogBoxes();
    
    public Label nstudent_name, nstudent_status, nlesson_name, nterm;
    public ImageView nstudent_image;
    
    public static Stage lessonStage;
    
    public static String selItem="", ncLoc;
    
    public static Stage courseStage;
    
    String cLoc;
    
    LogCtrl lgctrl = new LogCtrl();
    
    

    @FXML
    void pin(ActionEvent event) {

        nlistItem = listItem;
        
        String abc = listItem.getSelectionModel().getSelectedItem();
        
        String[] selItemc = abc.split("/");
        selItem = selItemc[1];
        
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" Pinned "+selItem+" of "+SelectTermController.cxTerm+", "+CoursesController.title +" to home page.";
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        filePin();
    }

    @FXML
    void play(ActionEvent event) {
        
        ViewLessonController.playLoc ="FORMAL";

        nlistItem = listItem;
        
        String abc = listItem.getSelectionModel().getSelectedItem();
        
        String[] selItemc = abc.split("/");
        selItem = selItemc[1];
        
        
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" accessed "+selItem+" of "+SelectTermController.cxTerm+", "+CoursesController.title;
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        try{
            
            lgctrl.accessLesson(LoginPage.uID, CoursesController.title, SelectTermController.cxTerm, selItem);
            
            Parent root1 = FXMLLoader.load(getClass().getResource("ViewLesson.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            
        }
        
        
    }
    
    @FXML
    void back(MouseEvent event) {

        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("Courses.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            
        }
        
        
    }
    
    @FXML
    void showVideo(MouseEvent event) {
        
        if(event.getClickCount()==2){
        
        String abc = listItem.getSelectionModel().getSelectedItem();
        
        if(abc==null){
            
        }
        else{
        
        String[] selItemc = abc.split("/");
        selItem = selItemc[1];
        
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" accessed "+selItem+" of "+SelectTermController.cxTerm+", "+CoursesController.title;
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        lpage.loginLog(LoginPage.uID, actv, l_name);      
        
        ViewLessonController.playLoc ="FORMAL";
        
        try{
            
            lgctrl.accessLesson(LoginPage.uID, CoursesController.title, SelectTermController.cxTerm, selItem);
            
            Parent root1 = FXMLLoader.load(getClass().getResource("ViewLesson.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nlistItem = listItem;
        nlesson_name = lesson_name;
        nstudent_image = student_image;
        nstudent_name = student_name;
        nstudent_status = student_status;
        nterm = term;
        
        lpage.loadData();
        
        nlesson_name.setText(CoursesController.title);
        nterm.setText(SelectTermController.cxTerm);
        nstudent_image.setImage(LoginPage.scaledImages);
        nstudent_name.setText(LoginPage.ufirstname+" "+LoginPage.ulastname);
        nstudent_status.setText(LoginPage.ustype);
        
        
        int res = cset.getContentDirectory();
        if(res==0){
            cLoc = ContentSettings.C_LOC;
        }
        else{
            //show error loading content
            dbox.showMessageDialog(Alert.AlertType.ERROR, "ERROR", null, "Error loading content settings");
        }
        
        //Load folders..
        ncLoc = cLoc+"/"+SelectTermController.termSelected;
        
        System.out.println("MLOCATION - "+ncLoc);
        
        List<String> lst = cset.loadFiles(ncLoc);
        ObservableList obList = FXCollections.observableList(lst);
        
        nlistItem.getItems().addAll(obList);
        
        nlistItem.setStyle("-fx-font-size: 18; -fx-font-weight: BOLD;");
        nlistItem.setExpanded(true);
        
        //nlistItem.setStyle("-fx-font-weight: BOLD");
        
    }    
    
    
    public void filePin(){
        
        PinLessonController.xLoc="FORMAL";
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
    
    //write to textFile..
    
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
            Logger.getLogger(CourseListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  
}
