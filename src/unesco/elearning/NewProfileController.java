/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.LoginPage;
import unesco.elearning.controllers.SessionIdentifierGenerator;
import unesco.elearning.language.Language;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class NewProfileController implements Initializable {
    
    String selectImage="NO";
    
    Language lang = new Language();
    
    DialogBoxes dbox = new DialogBoxes();
    Date ndate = new Date();
    
    LoginPage lpage = new LoginPage();
    
    NewProfile np = new NewProfile();
    
    @FXML
    private ImageView imgview;
    
    @FXML
    private JFXTextField firstname;

    @FXML
    private JFXTextField lastname;

    @FXML
    private JFXTextField username, studentID;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXDatePicker dob;

    @FXML
    private JFXComboBox<String> sex, atype, student_type;

    @FXML
    public Label newprofiletitle;
    
    @FXML
    public JFXButton register, cancel, browse;
    
    public String error="", database_error="", user_exist="", fill_in_blank="", info="", user_created="", invalid_student_type="Invalid Student Type";
    
    String canc="";
    
    public File mainfile;
    
    
    @FXML
    void back(ActionEvent event) {
       
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Stage stage = new Stage();
            
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
          }
        
        catch(Exception nn){
        System.out.println(nn);
        }
        
    }
    
    
    @FXML
    public void searchImage() {
        FileChooser fileChooser = new FileChooser();
        //Extention filter remove below two comments for extension filter
        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("Images (.png, .jpg, .bmp)", "*.jpg", "*.png", "*.bmp");
        fileChooser.getExtensionFilters().add(extentionFilter);
        //Set to user directory or go to default if cannot access
        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        if (!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fileChooser.setInitialDirectory(userDirectory);

        //Choose the file
        File chosenFile = fileChooser.showOpenDialog(UnescoELearning.mainWindow);
        //Make sure a file was selected, if not return default
        String path;
        if (chosenFile != null) {
            path = chosenFile.getPath();
            File file = new File(path);
            mainfile = file;
            selectImage="YES";
            
            //to set image in image view
            Image image = new Image(file.toURI().toString());
            imgview.setImage(image);

        } else {
            //default return value
            path = null;
        }
    }

    
    @FXML
    void newProfile(ActionEvent event) {

        String fname = firstname.getText();
        String lname = lastname.getText();
        String uname = username.getText();
        String upass = password.getText();
        String stype = student_type.getValue();
        String stID = studentID.getText();
        
        LocalDate gg = dob.getValue();
        String dob = String.valueOf(gg);
        
        String sx = sex.getValue();
        String aty = atype.getValue();
        
        if((aty.equals("FACILITATOR")||aty.equals("TEACHER"))&& stype.equals("NOT APPLICABLE")){
            
            dbox.showMessageDialog(Alert.AlertType.ERROR, error, null, invalid_student_type);
            return;
        }
        
        else{
            
        
        
        if(atype.getValue()==null){
            
        }else{
            if(aty.equals("FACILITATOR")||aty.equals("ADMINISTRATOR")||aty.equals("TEACHER")){
                stID = SessionIdentifierGenerator.nextSessionId().toUpperCase();
            }
        }
        
        if(fname.trim().length()==0||lname.trim().length()==0||uname.trim().length()==0||upass.trim().length()==0
                ||dob.trim().length()==0||sex.getValue().isEmpty()||selectImage.equals("NO")|| stID.trim().length()==0)
        {
            dbox.showMessageDialog(Alert.AlertType.ERROR, error, null, fill_in_blank);
        }
        else{       

        int abc = lpage.checkUser(uname, upass, stID);
        
        if(abc==0)
        {
            //continue to create account..
            int xyz = lpage.insertProfile(fname, lname, uname, upass, dob, sx, aty, mainfile, stype, stID);
            
            if(xyz==0){
                
                dbox.showMessageDialog(Alert.AlertType.INFORMATION, info, null, user_created);
                
                lpage.loginLog(stID, "Profile created for "+fname +" "+lname, fname+" "+lname);
                
                String FL1 = "lang/"+FXMLDocumentController.lValue+".txt";    
                canc = lang.translate(FL1, "GOINNOW");
                
                register.setVisible(false);
                cancel.setText(canc);
                
            }
            else
            {
                //Show error..
                dbox.showMessageDialog(Alert.AlertType.ERROR, error, null, database_error);
            }
            
        }
        
        if(abc==1)
            
        {
            //throw profile already exist..           
            dbox.showMessageDialog(Alert.AlertType.ERROR, error, null, user_exist);
          
        }
        
        if(abc>1){
            // Throw database error.
            dbox.showMessageDialog(Alert.AlertType.ERROR, error, null, database_error);
        }
        
        }
        
    }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        if(MainPageController.STATUS.equals("Formal (JSS 2)"))
        {
        sex.getItems().addAll("Male", "Female");
        atype.getItems().addAll("STUDENT", "TEACHER");
        student_type.getItems().addAll("Formal (JSS 2)", "NOT APPLICABLE");
        }else
        {
            
            String txt = SessionIdentifierGenerator.nextSessionId().toUpperCase();
            
            studentID.setText(txt);
            studentID.setEditable(false);
            
        sex.getItems().addAll("Female", "Male");
        atype.getItems().addAll("LEARNER", "FACILITATOR");
        student_type.getItems().addAll("Non Formal", "NOT APPLICABLE");
        }
        
        //sex.getItems().addAll("Male", "Female");
        //atype.getItems().addAll("STUDENT", "FACILITATOR", "ADMINISTRATOR");
        //student_type.getItems().addAll("Formal (JSS 2)", "Non Formal", "NOT APPLICABLE");
        
        //Begin translation..
        
        String FL = "lang/"+FXMLDocumentController.lValue+".txt";    
        
        
        String tit = lang.translate(FL, "newprofiletitle");
        newprofiletitle.setText(tit);
        
        String fname = lang.translate(FL, "firstname");
        firstname.setPromptText(fname);
        
        String lname = lang.translate(FL, "lastname");
        lastname.setPromptText(lname);
        
        String usr = lang.translate(FL, "username");
        username.setPromptText(usr);
        
        String pwd = lang.translate(FL, "password ");
        password.setPromptText(pwd);
        
        String dobs = lang.translate(FL, "dob");
        dob.setPromptText(dobs);
        
        String sxx = lang.translate(FL, "sex");
        sex.setPromptText(sxx);
        
        String atypes = lang.translate(FL, "atype");
        atype.setPromptText(atypes);
        
        String styp = lang.translate(FL, "student_type");
        student_type.setPromptText(styp);
        
        String regB = lang.translate(FL, "register");
        register.setText(regB);
        
        String canc = lang.translate(FL, "cancel");
        cancel.setText(canc);
        
        String brw = lang.translate(FL, "browse");
        browse.setText(brw);
        
        error = lang.translate(FL, "dberror");
        
        user_exist = lang.translate(FL, "user_exist");
        database_error = lang.translate(FL, "database_err");
        fill_in_blank = lang.translate(FL, "fill_in_blank");
        info = lang.translate(FL, "info");
        user_created = lang.translate(FL, "user_created");
        
        invalid_student_type = lang.translate(FL, "STYPEERROR");
        
        
        
        //System.out.println("1..CANC:::-"+canc);
        
    }    
    
}
