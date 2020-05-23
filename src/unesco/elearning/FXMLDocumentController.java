/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.ContentSettings;
import unesco.elearning.controllers.LoginPage;
import unesco.elearning.language.Language;

/**
 *
 * @author SAMSON KAYODE
 */
public class FXMLDocumentController implements Initializable {
    
    LoginPage lpage = new LoginPage();
    Language lang = new Language();
    
    DialogBoxes dbox = new DialogBoxes();
    
    ContentSettings cs = new ContentSettings();
    
    UnescoELearning elearn = new UnescoELearning();
    
    public static String lValue;
    
    public static  JFXComboBox<String> langValue;
    
    @FXML
    private JFXComboBox<String> language;
    
    @FXML
    private AnchorPane loginPane;
    
    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;
    
    @FXML
    private Label l_note;
    
    private Hyperlink forgot_password;
    
    @FXML
    private JFXButton login, new_user;
    
    String invalid_login="", error="", exit_application="", info="", db_selectLanguage ="", database_error="";
    
    public static int resx=0;


    @FXML
    void newUser(ActionEvent event) {
        
        langValue=language;
        
        String default_language = language.getValue();
        
        lValue = default_language;
        
        if(default_language==null){
         
            dbox.showMessageDialog(AlertType.ERROR, null, error, db_selectLanguage);
          }
        
        else{
            try{
            
            Parent root1 = FXMLLoader.load(getClass().getResource("NewProfile.fxml"));
            
            //UnescoELearning.mainWindow.centerOnScreen();
            elearn.mainWindow.setScene(new Scene(root1));
            elearn.mainWindow.centerOnScreen();
          }
        
        catch(Exception nn){
        System.out.println(nn);
        }
        }
        
    }
    
    @FXML
    void closePage(MouseEvent event) {
        
        Optional<ButtonType> result = dbox.showConfirmDialog(null, exit_application);
            if (result.get() == ButtonType.OK){
    
                Platform.exit();
                
        } else {
        // ... user chose CANCEL or closed the dialog
        }
        
    }

    @FXML
    void validateLogin(ActionEvent event) {

        String user = username.getText();
        String pass = password.getText();
        String stype = MainPageController.STATUS;
        
        String default_language = language.getValue();
        
        if(default_language==null){
         
            dbox.showMessageDialog(AlertType.ERROR, error, null, db_selectLanguage);
            
        }
        else
        {
                        
                   
        int res = lpage.validateLogin(user, pass, stype);        
        
        if(res==0)
        {
            String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" Logged into the portal";
            String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
            
            lpage.loginLog(LoginPage.uID, actv, l_name);
            
                try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FormalDashBoard.fxml"));
            Stage stage = new Stage();
            
            //UnescoELearning.mainWindow.centerOnScreen();
            
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            //UnescoELearning.mainWindow.setFullScreen(true);
            //UnescoELearning.mainWindow.setFullScreenExitHint(" ");
            
          }
        
        catch(Exception nn){
        System.out.println(nn);
        
        nn.printStackTrace();
        }
        }
        
        if(res==1)
        {

            dbox.showMessageDialog(AlertType.ERROR, error, null, invalid_login);
            
        }
        
        if(res > 1)
        {
            System.out.println("Database Connection Error");
            dbox.showMessageDialog(AlertType.ERROR, error, null, database_error);
        }
        
        
    }
    }
    
    /*
    
    public void loadSplashScreen() throws IOException
    {
        elearn.splashLoaded=true;
        
        
        AnchorPane pane = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
        loginPane.getChildren().setAll(pane);
        
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
        
        AnchorPane parent;
            try {
                parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                loginPane.getChildren().setAll(parent);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
    }
    
    */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO      
        
        List<String> lst = lang.loadFiles();
        ObservableList obList = FXCollections.observableList(lst);
        
        language.getItems().addAll(obList);
        //language.getSelectionModel().selectFirst();
        
        //Set the default language and location..
        
        int res = cs.getContentDirectory();
        
        if(res==0){
            
            language.setValue(ContentSettings.LANG);
            //tid.setText(ContentSettings.C_LOC);
        }
        if(res==1){
            language.setValue("EN");
        }
        
        if(res==2){
            language.setValue("EN");
            //boxes.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "Error retrieving settings");
            System.out.println("ERROR LOADING DEFAULT LANGUAGE");
        }
        
        if(MainPageController.STATUS.equals("Non Formal")){
            
            language.setValue("HU");
            
        }else{
            language.setValue("EN");
        }
        
        //perform translations..
        
         langValue=language;
        
        String FL = "lang/"+language.getValue()+".txt";
        
        lValue = FL;        
        
        System.out.println("FL IS   - "+FL);
        System.out.println("lValue IS   - "+lValue);
        
        String lnote = lang.translate(FL, "l_note");
        l_note.setText(lnote);
        
        String regs = lang.translate(FL, "login_register_Button");
        new_user.setText(regs);
        
        String logins = lang.translate(FL, "lgin");
        login.setText(logins);
        
        String fgp = lang.translate(FL, "forgot_pword");
        //forgot_password.setText(fgp);
        
        String upt = lang.translate(FL, "username");
        username.setPromptText(upt);
        
        String passt = lang.translate(FL, "password");
        password.setPromptText(passt);
        
        //Dialog boxes..
        
        exit_application = lang.translate(FL, "exit_application");
        error = lang.translate(FL, "dberror");
        invalid_login = lang.translate(FL, "invalid_login");
        
        db_selectLanguage = lang.translate(FL, "db_selectLanguage");
        database_error = lang.translate(FL, "database_err");
        
    }    
    
     void forgotPassword(ActionEvent event) {

       //int a = new File(ContentSettings.C_LOC+"/QUIZZES").listFiles().length;
       
       //System.out.println(" NO OF FILES IS "+a);
        
        resx=0;
       
        //int x = getFile(ContentSettings.C_LOC+"/QUIZZES");
        
        //System.out.println("RESX IS - "+x);
        
        
        
    }
    
    
    private int getFile(String dirPath) {
    File f = new File(dirPath);
    File[] files = f.listFiles();

    if (files != null)
    for (int i = 0; i < files.length; i++) {
        
            //resx++;

        File file = files[i];
        
        if(file.isFile()){
            resx++;
        }

        if (file.isDirectory()) {   
             getFile(file.getAbsolutePath()); 
        }
    }
    
    return resx;
}
    
    
}
