/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.ContentSettings;
import unesco.elearning.controllers.LogCtrl;
import unesco.elearning.controllers.LoginPage;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class QuizController implements Initializable {

    @FXML
    private Label student_name;
    @FXML
    private ImageView student_image;
    @FXML
    private Label student_status;
    @FXML
    private TreeTableView<String> tableView;
    @FXML
    private TreeTableColumn<String, String> title;
    
    ContentSettings cs = new ContentSettings();
    
    TreeItem<String> root1 = new TreeItem<>("AVAILABLE COURSES");
    
    public static StringBuilder rBuild = new StringBuilder();
    
    TreeItem<String> pchild, pquiz;
    
    TreeTableView ttv;
    
    ImageView nstudent_image;
    Label nstudent_name, nstudent_status;
    
    LoginPage lpage = new LoginPage();
    LogCtrl lgctrl = new LogCtrl();
    DialogBoxes dbox = new DialogBoxes();
    
    public static float dscore=0;
    
    public static String path="";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        ttv = tableView;
        nstudent_image = student_image;
        nstudent_name = student_name;
        nstudent_status = student_status;
        
        nstudent_image.setImage(LoginPage.scaledImages);
        nstudent_name.setText(LoginPage.ufirstname+" "+LoginPage.ulastname);
        nstudent_status.setText(LoginPage.ustype);
        
        ttv.setStyle("-fx-font-size: 18; -fx-font-weight: BOLD; -fx-border-color: black; -fx-border-style: solid;");
        
        
        title.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<String, String>, ObservableValue<String>>(){
        @Override
        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<String, String> param){

            return new SimpleStringProperty(param.getValue().getValue());
            
        //return new SimpleStringProperty(param.getValue().getValue());
        }
        });
        
        
        tableView.setRoot(root1);
        
        int a = cs.getContentDirectory();
        if(a==0){
         
            String loc = ContentSettings.C_LOC;
            dscore = ContentSettings.dscore;
            
            List<String> lst = cs.loadQuizOne(loc+"/QUIZZES");
            
            for(int i=0; i<lst.size(); i++){
                
                System.out.println("LST IS "+lst.get(i));
                pchild = new TreeItem<>(lst.get(i));
                
                
                
                for(int x=1; x<4; x++)
                {
                    //loop terms..
                    System.out.println("X RECORD "+ x);
                    
                    if(x==1){
                        
                        TreeItem<String> ax = new TreeItem<>("1ST TERM");
                        pchild.getChildren().add(ax);
 
                        String myLoc = loc+"/QUIZZES/"+lst.get(i)+"/1ST TERM";
                        
                        System.out.println("MY LOC IS "+myLoc);
                        
                        List<String> lst1 = cs.loadQuizFiles(myLoc);
                for(int j=0; j<lst1.size(); j++){
                    TreeItem<String> abc = new TreeItem<>(lst1.get(j));
                    ax.getChildren().add(abc);
                }
                    }
                    
                    if(x==2){
                        TreeItem<String> ax = new TreeItem<>("2ND TERM");
                        pchild.getChildren().add(ax);
                        
                        List<String> lst2 = cs.loadQuizFiles(loc+"/QUIZZES/"+lst.get(i)+"/2ND TERM");
                for(int k=0; k<lst2.size(); k++){
                    TreeItem<String> abc1 = new TreeItem<>(lst2.get(k));
                    ax.getChildren().addAll(abc1);
                }
                    }
                    
                    if(x==3){
                        TreeItem<String> ax = new TreeItem<>("3RD TERM");
                        pchild.getChildren().add(ax);
                        
                        List<String> lst3 = cs.loadQuizFiles(loc+"/QUIZZES/"+lst.get(i)+"/3RD TERM");
                        for(int l=0; l<lst3.size(); l++){
                    TreeItem<String> abc2 = new TreeItem<>(lst3.get(l));
                    
                    JFXButton btn = new JFXButton("Start Quiz");
                    
                    
                   ax.getChildren().addAll(abc2);
                    }
                    
                }
 
                //second term..
                
                
                
                }
                root1.setExpanded(true);
            
                root1.getChildren().addAll(pchild);
                
            }
            
            
            
        }
        
        root1.isExpanded();
        
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //tableView.setStyle(".tree-table-cell -fx-border-color: black; -fx-border-style: solid;");
        
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
    private void startQuiz(ActionEvent event) {
        
        StartQuizController.whereIs="QUIZ";
        
        StringBuilder pathBuilder = new StringBuilder();
        
        String b="";
        String a="";
        TreeItem<String> item;
        
    for (item = tableView.getSelectionModel().getSelectedItem();
    item != null ; item = item.getParent()) {

        a = item.getValue();
        b = item.getValue();
        
        if(item.getValue().equals("AVAILABLE COURSES"))
        {
            a = ContentSettings.C_LOC+"/QUIZZES";
            b ="";
        }
        
        if(item.getValue().contains("/")){
            
            String[] lesson = item.getValue().split("/");
            a = lesson[1]+".txt";
            
            b = lesson[1];
        }
   
    pathBuilder.insert(0, a+"/");
    rBuild.insert(0, b+"/");
    //pathBuilder.insert(0, "/");
    }
    path = pathBuilder.toString();
    
    System.out.println("THE PATH US "+path);
    
    String xyz[] = path.split("/");
    
    System.out.println("COURSE NAME "+xyz[2]);
    System.out.println("TERM "+xyz[3]);
    
    String lsn = xyz[4].substring(0, xyz[4].lastIndexOf('.'));
    System.out.println("LESSON "+lsn);
    
    
        if(path.contains(".txt")){
            
            //int confirm = 0;
            
            int confirm = lgctrl.lessonAccessed(LoginPage.uID, xyz[2], xyz[3], lsn);
            
            System.out.println("CONFIRM IS "+confirm);
            
            if(confirm==0){
                
        String actv = LoginPage.ufirstname+" "+LoginPage.ulastname+" started quiz "+rBuild.toString();
        String l_name = LoginPage.ufirstname+" "+LoginPage.ulastname;
        
        lpage.loginLog(LoginPage.uID, actv, l_name);
        
        try{
        
        Parent root1 = FXMLLoader.load(getClass().getResource("StartQuiz.fxml"));
          
            
            //UnescoELearning.mainWindow.centerOnScreen();
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
        
        }
        catch(Exception nn){
            System.out.println("Error starting quiz page "+nn);
            
            nn.printStackTrace();
        }
                
            }
            if(confirm==1){
                dbox.showMessageDialog(Alert.AlertType.ERROR, "Error", "YOU CANNOT ACCESS THIS QUIZ", "You have to complete "+lsn+" before accessing the quiz");
                
                pathBuilder.setLength(0);
                rBuild.setLength(0);
                
            }
            if(confirm==2){
                dbox.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "Error confriming if you have access the lesson attached to this quiz");
            }
        }
        
        else{
            //It is not a quiz
            
            dbox.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "Please expand the node to select a quiz");
            
            //item.setExpanded(true);
            
        }

    }
    
   

    
    
}
