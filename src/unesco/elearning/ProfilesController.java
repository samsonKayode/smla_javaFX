/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.LoginPage;
import unesco.elearning.controllers.StudentSearch;
import unesco.elearning.model.StudentList;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class ProfilesController implements Initializable {

    @FXML
    private Label lesson_name;
    @FXML
    private FontAwesomeIconView bck;
    @FXML
    private TableView<StudentList> studentTable;
    @FXML
    private TableColumn<StudentList, String> name;
    @FXML
    private TableColumn<StudentList, String> id;
    @FXML
    private TableColumn<StudentList, String> username;
    @FXML
    private TableColumn<StudentList, String> password;
    @FXML
    private TableColumn<StudentList, String> type;
    @FXML
    private ImageView student_image;
    
    public static String stID="", stName="";
    public static Image stImg;
    
    StudentSearch ss = new StudentSearch();
    DialogBoxes dbox = new DialogBoxes();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            username.setCellValueFactory(new PropertyValueFactory<>("username"));
            password.setCellValueFactory(new PropertyValueFactory<>("password"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            
            //studentTable.getSelectionModel().
            studentTable.setStyle("-fx-font-weight: BOLD");
            
            List<StudentList> lst = ss.loadProfiles();
        ObservableList obList  = FXCollections.observableArrayList(lst);
        studentTable.getItems().addAll(obList);
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
    private void getID(MouseEvent event) {
        
        StudentList lst = studentTable.getSelectionModel().getSelectedItem();
        
        stID =lst.getId();
        stName = lst.getUsername();
        
        
        stImg = ss.getImage(stID);
        student_image.setImage(stImg);
    }

    @FXML
    private void removeProfile(ActionEvent event) {
        
        Optional<ButtonType> result = dbox.showConfirmDialog(null, "Are you sure you want to remove profile");
            if (result.get() == ButtonType.OK){
                
                if(LoginPage.username.equals(stName)){
                    
                    dbox.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "You are currently logged into this profile");
                    
                }else{
                    int x = ss.removeProfile(stID, stName);
                
                if(x==1){
                    
                    //profile removed..
                    dbox.showMessageDialog(Alert.AlertType.INFORMATION, "Info", null, "Profile Removed");
                    
                    studentTable.getItems().remove(studentTable.getSelectionModel().getSelectedItem());
                    
                }else{
                    
                    //unable to delete profile.
                    dbox.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "Error removing profile");
                }
                }
                
                
                
        } else {
        // ... user chose CANCEL or closed the dialog
        }
        
    }
    
}
