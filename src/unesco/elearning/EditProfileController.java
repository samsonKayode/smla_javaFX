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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.LoginPage;
import unesco.elearning.language.Language;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class EditProfileController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    String changeImg="NO";
    
    DialogBoxes dbox = new DialogBoxes();
    Date ndate = new Date();
    
    LoginPage lpage = new LoginPage();
    NewProfile np = new NewProfile();
    Language lang = new Language();
    
    
    @FXML
    private Label editprofiletitle;
    
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
    private JFXButton cancel, update, browse;
    
    public String error="", database_error="", user_exist="", fill_in_blank="", info="", user_created="", user_updated="";
    
    File mainfile;
    
    Image image;
    
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
            
            changeImg="YES";

            //to set image in image view
            image = new Image(file.toURI().toString());
            imgview.setImage(image);

        } else {
            //default return value
            path = null;
        }
        
    }
    
    
        @FXML
       public void editProfile()
        {

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
        
        if(fname.trim().length()==0||lname.trim().length()==0||uname.trim().length()==0
                ||upass.trim().length()==0||dob.trim().length()==0||sex.getValue().isEmpty()||stID.trim().length()==0)
        {
            dbox.showMessageDialog(Alert.AlertType.ERROR, error, null, fill_in_blank);
        }
        else{
        
            int res = lpage.editUserProfile(fname, lname, uname, upass, dob, sx, aty, mainfile, stype, stID);
            
            
            if(res==0)
            {
                //dbox.showMessageDialog(Alert.AlertType.INFORMATION, info, null, user_updated);
                
                lpage.loginLog(stID, fname +" "+lname +" Edited Profile", fname+" "+lname);
                
                FormalDashBoardController.n_studentName.setText(fname+" "+lname);
                
                if(changeImg.equals("NO")){
                    
                }else{
                FormalDashBoardController.n_student_image.setImage(image);    
                }
                
                FormalDashBoardController.profileStage.close();  
 
            }
            else{
                dbox.showMessageDialog(Alert.AlertType.ERROR, error, null, database_error);
            }
            
        }
        }
    
        @FXML
    void back(ActionEvent event) {
       
       FormalDashBoardController.profileStage.close();        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        sex.getItems().addAll("Male", "Female");
        atype.getItems().addAll("STUDENT", "FACILITATOR", "ADMINISTRATOR");
        student_type.getItems().addAll("Formal (JSS 2)", "Non Formal");
        
        lpage.loadData();
        
        firstname.setText(LoginPage.ufirstname);
        lastname.setText(LoginPage.ulastname);
        
        username.setText(LoginPage.username);
        password.setText(LoginPage.password);
        sex.setValue(LoginPage.usex);
        atype.setValue(LoginPage.atype);
        imgview.setImage(LoginPage.scaledImages);
        
        studentID.setText(LoginPage.uID);
        
        LocalDate ld = LocalDate.parse(LoginPage.udob);
        dob.setValue(ld);
        
        student_type.setValue(LoginPage.ustype);
        mainfile = LoginPage.mainfile;
        
        //Begin translation..
        
        String FL = FXMLDocumentController.lValue;
        
        System.out.println("EDIT FL "+FL);
        
        String tit = lang.translate(FL, "editprofiletitle");
        editprofiletitle.setText(tit);
        
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
        
        String regB = lang.translate(FL, "update");
        update.setText(regB);
        
        String canc = lang.translate(FL, "cancel");
        cancel.setText(canc);
        
        String brw = lang.translate(FL, "browse");
        browse.setText(brw);
        
        error = lang.translate(FL, "dberror");
        
        database_error = lang.translate(FL, "database_err");
        fill_in_blank = lang.translate(FL, "fill_in_blank");
        info = lang.translate(FL, "info");
        user_updated = lang.translate(FL, "user_updated");
        
        
        // TODO
    }    
    
}
