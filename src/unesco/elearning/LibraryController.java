/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.ContentSettings;
import unesco.elearning.controllers.LoginPage;
import unesco.elearning.language.Language;
import unesco.elearning.model.LibraryModel;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class LibraryController implements Initializable {
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
    private TableColumn<LibraryModel, String> title;
    @FXML
    private TableColumn<LibraryModel, String> type;
    @FXML
    private TableColumn<LibraryModel, String> desc;
    @FXML
    private TableView<LibraryModel> libraryTable;
    
    ContentSettings cs = new ContentSettings();
    ObservableList<LibraryModel> obList;
    
    public static String iTitle="", iType="", iDesc="", iExt="", cLoc="";
    
    public static Stage lbStage;
    
    String TITLE="";
    
    LoginPage lpage = new LoginPage();
    
    DialogBoxes dbox = new DialogBoxes();
    
    Language lang = new Language();
    @FXML
    private JFXButton v_button;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        student_image.setImage(LoginPage.scaledImages);
        student_name.setText(LoginPage.ufirstname+" "+LoginPage.ulastname);
        student_status.setText(LoginPage.ustype);
        
         title.setCellValueFactory(new PropertyValueFactory<>("title"));
         type.setCellValueFactory(new PropertyValueFactory<>("type"));
         desc.setCellValueFactory(new PropertyValueFactory<>("description"));
         
         if(MainPageController.STATUS.equals("Non Formal") && LoginPage.atype.equals("LEARNER"))
            {
                String FL = "lang/"+FXMLDocumentController.langValue.getValue()+".txt";
                
        
        String bgn = lang.translate(FL, "TTLE");
        title.setText(bgn);
        
        String RES = lang.translate(FL, "TIYP");
        type.setText(RES);
        
        String LN = lang.translate(FL, "DESC");
        desc.setText(LN);
        
        String HD = lang.translate(FL, "library");
        lesson_name.setText(HD);
        
        
        String PRV = lang.translate(FL, "PREVIEW");
        v_button.setText(PRV);
        
            }else{
            
        }
    }    

    @FXML
    private void back(MouseEvent event) {
        
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FormalDashBoard.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            
        }
    }

    @FXML
    private void maths(ActionEvent event) {
        
        TITLE="MATHEMATICS";
        loadData();
        
    }

    @FXML
    private void english(ActionEvent event) {
        TITLE="ENGLISH";
        loadData();
    }

    @FXML
    private void science(ActionEvent event) {
        
        TITLE="BASIC SCIENCE";
        loadData();
    }

    @FXML
    private void ict(ActionEvent event) {
        
        TITLE="BASIC ICT";
        loadData();
    }

    @FXML
    private void btech(ActionEvent event) {
        
        TITLE="BASIC TECH";
        loadData();
    }

    @FXML
    private void phe(ActionEvent event) {
        
        TITLE="PHE";
        loadData();
    }

    @FXML
    private void nonformal(ActionEvent event) {
        
        TITLE="NON FORMAL";
        loadData();
    }

    
    public void loadData(){
        
        libraryTable.getItems().clear();
                
        cLoc = ContentSettings.C_LOC+"/LIBRARY/"+TITLE;
        
        System.out.println("LIBRARY CLOC "+cLoc);
        
        List<LibraryModel> lst = cs.loadLibrary(cLoc);
        obList = FXCollections.observableArrayList(lst);
        
        libraryTable.getItems().addAll(obList);
        
        iType="";
        iDesc="";
        iExt="";
        iTitle="";
        
    }

    @FXML
    private void open(ActionEvent event) {
        
        if(iTitle.equals("")){
            dbox.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "Select an item to continue");
        }
        else{
            
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" accessed library file "+TITLE+" - "+iTitle;
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        
        lpage.loginLog(LoginPage.uID, actv, l_name);
            
            if(iType.equals("Image")){
                //show Image..
                
                Parent root1;
        try {
            root1 = FXMLLoader.load(getClass().getResource("LibraryImage.fxml"));
            
            lbStage = new Stage();
            lbStage.initModality(Modality.APPLICATION_MODAL);
            lbStage.initStyle(StageStyle.UNDECORATED);
            lbStage.setScene(new Scene(root1));
            lbStage.setAlwaysOnTop(true);
            lbStage.show();
            
           
            
        } catch (IOException ex) {
            Logger.getLogger(LibraryController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
            }
            
            if(iType.equals("Video")){
                //show Video..
                Parent root1;
        try {
            root1 = FXMLLoader.load(getClass().getResource("LibraryVideo.fxml"));
            
            lbStage = new Stage();
            lbStage.initModality(Modality.APPLICATION_MODAL);
            lbStage.initStyle(StageStyle.UNDECORATED);
            lbStage.setScene(new Scene(root1));  
            lbStage.setAlwaysOnTop(true);
            lbStage.show();
            
           
            
        } catch (IOException ex) {
            Logger.getLogger(LibraryController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
            }
            
            if(iType.equals("Animation")){
                //show Animation....
                
                
            }
            
            if(iType.equals("Document")){
                //show document..
                
                
            }
                        
        }
        
    }

    @FXML
    private void getInfo(MouseEvent event) {
        
        LibraryModel lMode = libraryTable.getSelectionModel().getSelectedItem();
        
        iTitle = lMode.getTitle();
        iType = lMode.getType();
        iDesc = lMode.getDescription();
        iExt = lMode.getWithExtension();
    }
    
}
