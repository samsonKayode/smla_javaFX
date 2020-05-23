/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.List;
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
public class FindStudentsController implements Initializable {

    @FXML
    private JFXTextField tname;


    @FXML
    private TableView<StudentList> studentTable;

    @FXML
    private TableColumn<StudentList, String> name;

    @FXML
    private TableColumn<StudentList, String> id;
    @FXML
    private TableColumn<StudentList, String> type;

    @FXML
    private TableColumn<StudentList, String> sex, dob;

    @FXML
    private ImageView student_image;
    
    ObservableList obList;
    StudentSearch ss = new StudentSearch();
    DialogBoxes dbox = new DialogBoxes();
    
    public static String stID="", stName="", stType="";
    public static Image stImg;
    @FXML
    private Label lesson_name;
    @FXML
    private FontAwesomeIconView bck;

    @FXML
    void back(MouseEvent event) {

        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FormalDashBoard.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            
        }
        
    }

    @FXML
    void goToAchievements(ActionEvent event) {

        if(stID.equals("")){
            dbox.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "Select a student to continue");
        }
        else{
            try{
            Parent root1 = FXMLLoader.load(getClass().getResource("Achievements.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            
        }
        }
        
    }
    
    @FXML
    void search(ActionEvent event) {
        
        String more="";

        if(tname.getText().length()<3){
            dbox.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "Please enter student name or id to search");
        }
        else{
        
        if(obList==null){
            
        }
        else
        {
        studentTable.getItems().removeAll(obList);   
        student_image.setImage(null);
        stID="";
        }
        
        if(LoginPage.atype.equals("FACILITATOR")){
            more = " AND ACCOUNT_TYPE='LEARNER'";
        }
        
        if(LoginPage.atype.equals("TEACHER")){
            more = " AND ACCOUNT_TYPE='STUDENT'";
        }
        
        if(LoginPage.atype.equals("ADMINISTRATOR")){
            more = "";
        }

        List<StudentList> lst = ss.findStudent(tname.getText(), more);
        obList  = FXCollections.observableArrayList(lst);
        
        studentTable.getItems().addAll(obList);
        
    }
    
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
            sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            
            //studentTable.getSelectionModel().
            studentTable.setStyle("-fx-font-weight: BOLD");
            
        // TODO
    }    
    
    
    @FXML
    void getID(MouseEvent event) {

        StudentList lst = studentTable.getSelectionModel().getSelectedItem();
        
        stID =lst.getId();
        stName = lst.getName();
        stType = lst.getType();
        
        stImg = ss.getImage(stID);
        student_image.setImage(stImg);
        
    }

    
    
}
