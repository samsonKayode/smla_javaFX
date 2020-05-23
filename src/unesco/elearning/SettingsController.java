/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.ContentSettings;
import unesco.elearning.language.Language;


/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class SettingsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    Language lang = new Language();
    
    ContentSettings cs = new ContentSettings();
    DialogBoxes boxes = new DialogBoxes();
    
    @FXML
    private JFXComboBox<String> language;
    
    @FXML
    private TextField tid;
    @FXML
    private JFXButton browse;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXButton save;
    @FXML
    private JFXTextField score;
    @FXML
    private JFXTextField srv;
    
    @FXML
    void browse(ActionEvent event) {
        
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Content Directory");
        String userDirectoryString = System.getProperty("user.home");
        File defaultDirectory = new File(userDirectoryString);
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(UnescoELearning.mainWindow);
        if(selectedDirectory.getPath()==null){
            
        }else{
            String path = selectedDirectory.getPath();
        
        tid.setText(path);
        }
        
        
    }

    @FXML
    void close(ActionEvent event) {

        FormalDashBoardController.settingStage.close();
        
    }

    @FXML
    void save(ActionEvent event) {
        
        String data = tid.getText();
        String lang = language.getValue();
        String sc = score.getText();
        Float score = Float.parseFloat(sc);
        
        String server = srv.getText();
        
        if(data.length()<1 || lang.length()<1||score<0)
        {
            boxes.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "Please enter valid data into alll fields");
        }
        
        int res = cs.inserContenttDirectory(data, lang, score, server);
        
        if(res==0){
            //boxes.showMessageDialog(Alert.AlertType.INFORMATION, "Info", null, "Settings Saved");
            
            FormalDashBoardController.settingStage.close();
            
        }
        else{
            boxes.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "Error saving settings");
            
        }
        
        
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        List<String> lst = lang.loadFiles();
        ObservableList obList = FXCollections.observableList(lst);
        
        language.getItems().addAll(obList);
        
        int res = cs.getContentDirectory();
        
        if(res==0){
            
            language.setValue(ContentSettings.LANG);
            tid.setText(ContentSettings.C_LOC);
            score.setText(String.valueOf(ContentSettings.dscore));
            srv.setText(ContentSettings.SERVER);
        }
        
        if(res==2){
            boxes.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "Error retrieving settings");
        }
        
        // TODO
    }    
    
}
