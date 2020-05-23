/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.LogCtrl;
import unesco.elearning.controllers.LoginPage;
import unesco.elearning.model.LogModel;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class LogsController implements Initializable {
    @FXML
    private FontAwesomeIconView back;
    @FXML
    private JFXDatePicker start_date;
    @FXML
    private JFXDatePicker end_date;
    @FXML
    private JFXTextField student_id;
    @FXML
    private JFXTextField others;
    @FXML
    private TableView<LogModel> tableView;
    @FXML
    private TableColumn<LogModel, Integer> sno;
    @FXML
    private TableColumn<LogModel, String> date;
    @FXML
    private TableColumn<LogModel, String> studentID, studentName;
    @FXML
    private TableColumn<LogModel, String> activity;
    
    LogCtrl lg = new LogCtrl();
    DialogBoxes dbox = new DialogBoxes();
    
    SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    
    String startDate, endDate, stID, more;
    
    ObservableList<LogModel> obList;
    
    public JFXTextField nstudent_id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nstudent_id = student_id;
        
        if(LoginPage.atype.equals("STUDENT")){
            nstudent_id.setText(LoginPage.uID);
            
        }
        else{
            nstudent_id.isEditable();
        }
        
        initCol();
        // TODO
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
    private void loadLogs(ActionEvent event) {
        
        LocalDate gg = start_date.getValue();
        startDate = String.valueOf(gg);
        
        LocalDate gg1 = end_date.getValue();
        endDate = String.valueOf(gg1);
        
        stID = student_id.getText().trim();
        more = others.getText().trim();
        
        if(startDate.length()==0||endDate.length()==0){
            //throw error..
            dbox.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "Please select the dates to continue");
        }
        else{
            loadData();
        }     
        
    }

    @FXML
    private void reset(ActionEvent event) {
    }
    
    public void initCol(){
                
        sno.setCellValueFactory(new PropertyValueFactory<>("sno"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        activity.setCellValueFactory(new PropertyValueFactory<>("activity"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        
    }
    
    public void loadData(){
        
        if(obList==null){
            
        }
        else
        {
        tableView.getItems().removeAll(obList);    
        }
        
        
        
        //ObservableList<LogModel> obList = lg.loadLog(startDate, endDate, stID, more);
        List<LogModel> lst = lg.loadLog(startDate, endDate, stID, more);        
        obList = FXCollections.observableArrayList(lst);
        
        tableView.getItems().addAll(obList);
    }

    @FXML
    private void exportToExcel(ActionEvent event) {
        
        String query1="";
        String query;
        
        HSSFWorkbook wb = new HSSFWorkbook();//for earlier version use HSSF

                HSSFSheet sheet = wb.createSheet("User Details");

                HSSFRow header = sheet.createRow(0);
                
                 header.createCell(0).setCellValue("Date");

                header.createCell(1).setCellValue("Student ID");

                header.createCell(2).setCellValue("Student Name");

                header.createCell(3).setCellValue("Activity");
                
                
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                 int index = 1;
                 
        if(stID.length()>0){
           query1 = "and id ='"+stID+"'";
        }
        if(more.length()>0){
            query1 = "and activity like '%"+more+"%'";            
        }
        
        query = "Select id, date, activity, name from LOGS where date between ? and ? " +query1;

        try{
            
            //System.out.println("FINAL QUERY "+query);
            
            //Connection cn = db.Connection();
            PreparedStatement st = LoginPage.cn.prepareStatement(query);
            
            st.setString(1, startDate);
            st.setString(2, endDate);
            
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                
                 HSSFRow row = sheet.createRow(index);
                 
                row.createCell(1).setCellValue(rs.getString(1));
                String date1 = rs.getString(2);
                Date dates = sdfIn.parse(date1);
                String date = sdfOut.format(dates);
                
                row.createCell(0).setCellValue(date);
                
                String activity = rs.getString(3).trim();
                String name = rs.getString(4).trim();
                
                row.createCell(2).setCellValue(name);
                row.createCell(3).setCellValue(activity);
                
                index++;
                
            }
            
            FileChooser fileChooser = new FileChooser();
            
            File file = fileChooser.showSaveDialog(null);
            //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel Files (*.xls)", "*.xls");
              //fileChooser.getExtensionFilters().add(extFilter);
            
            if(file != null){
                  FileOutputStream fileOut = new FileOutputStream(file+".xls");// before 2007 version xls

                wb.write(fileOut);

                fileOut.close();
                
                dbox.showMessageDialog(Alert.AlertType.INFORMATION, "Info", null, "Document exported");
              }
            
            
        }
        catch(Exception nn){
            System.out.println("Error saving excel data-- "+nn);
        }

        
    }
    
}