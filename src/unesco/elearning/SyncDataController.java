/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import unesco.elearning.alerts.DialogBoxes;
import unesco.elearning.controllers.ContentSettings;
import unesco.elearning.controllers.LogCtrl;
import unesco.elearning.controllers.LoginPage;
import unesco.elearning.model.SyncData;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import javafx.scene.layout.Background;
import javafx.stage.DirectoryChooser;
import unesco.elearning.controllers.AlphanumFileComparator;
import unesco.elearning.controllers.SessionIdentifierGenerator;
import unesco.elearning.controllers.UploadJSONData;
import unesco.elearning.language.Language;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class SyncDataController implements Initializable {
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
    private TableView<SyncData> syncTable;
    private TableColumn<SyncData, String> date;
    @FXML
    private JFXTextArea text;
    
    LogCtrl lgctrl = new LogCtrl();
    DialogBoxes dbox = new DialogBoxes();
    @FXML
    private JFXButton serverButton;
    @FXML
    private JFXTextArea serverText;
    @FXML
    private JFXButton uploadButton;
    
    String startStop="STOPPED";
    
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static InputStream inputStream;
    private static FileOutputStream fileOutputStream;
    private static BufferedOutputStream bufferedOutputStream;
    private static int filesize = 10000000;
    private static int bytesRead;
    private static int current = 0;
    
    Thread t1;
    
    Language lang = new Language();
    
    UploadJSONData uploadJSON = new UploadJSONData();
    @FXML
    private JFXButton start_button;
    @FXML
    private Label results;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
            student_name.setText(LoginPage.ufirstname +" "+LoginPage.ulastname);
            student_status.setText(LoginPage.ustype);
            student_image.setImage(LoginPage.scaledImages);
            
if(MainPageController.STATUS.equals("Non Formal") && LoginPage.atype.equals("LEARNER"))
            {
                String FL = "lang/"+FXMLDocumentController.langValue.getValue()+".txt";
                
        
        String bgn = lang.translate(FL, "BEGIN");
        start_button.setText(bgn);
        
        String RES = lang.translate(FL, "RSLTS");
        results.setText(RES);
        
        String LN = lang.translate(FL, "syncData");
        lesson_name.setText(LN);
        
            }else{
            
        }
    }    

    @FXML
    private void back(MouseEvent event) {
        
        if(startStop.equals("STARTED")){
            
            dbox.showMessageDialog(Alert.AlertType.ERROR, "ERROR", null, "YOU HAVE TO STOP THE SERVER TO EXIT THIS PAGE");
            
        }else{
                        
            try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FormalDashBoard.fxml"));
            UnescoELearning.mainWindow.setScene(new Scene(root1));
            UnescoELearning.mainWindow.centerOnScreen();
            
        }catch(Exception nn)
        {
            System.out.println("Error displaying sync class "+nn);
        }
        }
        
        
    }

    @FXML
    private void startSync(ActionEvent event) {
        
        File f;
        
        if(ContentSettings.C_LOC.trim().length()<2 || ContentSettings.SERVER.trim().length()<2){
            dbox.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "Please check if you have setup your content directory and your server location");
        }else{
            
            //String myLoc = ContentSettings.C_LOC+"/SYNC/JSONDATA.txt";
            String myLoc = ContentSettings.C_LOC+"/SYNC/"+LoginPage.uID+".txt";
            f = new File(myLoc);
            if(f.exists()){
                f.delete();
                System.out.println("I DELETED");
            }else{
                System.out.println(" FILE NOT THERE ");
            }
            
            text.appendText("Starting Data Synchronization.........."+"\n");
            text.appendText("---------------------------------------------------------"+"\n");
            
            //Start courseView..
            
            text.appendText("Scanning Course View Data.........."+"\n");
            text.appendText("---------------------------------------------------------"+"\n");
            
            String str = lgctrl.getCourseViewJSON(myLoc);
             text.appendText("Result.........."+"\n");
             text.appendText(str+"\n");
             
             //Quiz result..
             
            text.appendText("Scanning Quiz Data.........."+"\n");
            text.appendText("---------------------------------------------------------"+"\n");
            
            String str2 = lgctrl.getQuizJSON(myLoc);
                text.appendText("Result.........."+"\n");
                text.appendText(str2+"\n");
          
           //Log Data..
                
           text.appendText("Scanning Logs Data.........."+"\n");
           text.appendText("---------------------------------------------------------"+"\n");
           
           String str3 = lgctrl.getLogJSON(myLoc);
                text.appendText("Result.........."+"\n");
                text.appendText(str3+"\n");
                
           //Profiles Data..
                
           text.appendText("Scanning Profiles Data.........."+"\n");
           text.appendText("---------------------------------------------------------"+"\n");
           
           String str4 = lgctrl.getProfileJSON(myLoc);
                text.appendText("Result.........."+"\n");
                text.appendText(str4+"\n");

                //Testing out connection...
                
           text.appendText("\n"+"Connecting to Remote Server"+"\n");     
           text.appendText("---------------------------------------------------------"+"\n");
           
          
           
           
           int xox = lgctrl.syncComplete();
        
        if(xox==0){
            //sync data done..
            
           text.appendText("\n"+"Transferring Data to Remote Server"+"\n");     
           text.appendText("---------------------------------------------------------"+"\n");
                        
           
           if(f.renameTo(new File(ContentSettings.SERVER+"/" + f.getName()))){
    		System.out.println("File is moved successful!");
                
           text.appendText("\n"+"Synchronization to Remote Server Complete!!!"+"\n");     
           text.appendText("---------------------------------------------------------"+"\n");
                
    	   }else{
    		System.out.println("File is failed to move!");
    	   }
           
           
        }
        
        else{
            //Sync incomplete..
            try{
                f.delete();
            }
            catch(Exception jj){
                System.out.println("Unable to delete file "+jj);
            }            
        }
        
        }
        
        
        
    }

    @FXML
    private void startServer(ActionEvent event) {
        
        if(LoginPage.atype.equals("STUDENT")||LoginPage.atype.equals("LEARNER")){
            //You are not allowed to use this module
            dbox.showMessageDialog(Alert.AlertType.ERROR, FormalDashBoardController.dbError, null, FormalDashBoardController.access_denied);
        }
        else{
            
            //Start Server..
            
            if(startStop.equals("STOPPED")){
            //Start Server..
            
            Thread thread = new Thread(new Runnable() {
         public void run() {
              
             startServer();
         }
    });  
    thread.start();
            //serverButton.setBackground(Background.BLUE);
    serverButton.setText("STOP SERVER");
        }
        else{
            //Stop Server..
            stopServer();
        }
        }
        
        
                
    }

    @FXML
    private void uploadData(ActionEvent event) {
        
        String folder="";
        
        if(LoginPage.atype.equals("STUDENT") || LoginPage.atype.equals("LEARNER")){
            //You are not allowed to use this module
            dbox.showMessageDialog(Alert.AlertType.ERROR, FormalDashBoardController.dbError, null, FormalDashBoardController.access_denied);
        }
        else{
            //Access the feature here..
            
            DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Content Directory");
        String userDirectoryString = System.getProperty("user.home");
        File defaultDirectory = new File(userDirectoryString);
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(UnescoELearning.mainWindow);
        if(selectedDirectory.getPath()==null){
            
        }else{
            folder = selectedDirectory.getPath();
            
            serverText.setText("");
            serverText.appendText("\n"+"Selected Folder For Operation is- "+folder+"\n");
            
            beginProcess(folder);
            
        }
        }
    }
    
    public void beginProcess(String folder){
        
        //First load all files inside folder 
        try{
            
            File[] files = new File(folder).listFiles();
            Arrays.sort(files, new AlphanumFileComparator() );
            
            for (File file : files) {
                if (file.isDirectory()) {
                    //DO NOTHING..
                }else{
                    
                    String FNAME = file.getAbsolutePath();
                    
                    if(FNAME.endsWith(".txt")){
                        //Process JSON DATA..
                        
                        String str = uploadJSON.retrieveData(FNAME);
                        
                        serverText.appendText("\n"+"PROCESSING FILE.. "+file.getName()+"\n");
                        serverText.appendText("-------------------------------------------------"+"\n");
                        serverText.appendText(str);
                        
                    }else{
                        //NOT A JSON FILE SO PROCESS WILL SKIP THIS FILE...
                    }
                    
                }
                serverText.appendText("\n"+"-------------------------------------------------"+"\n");
            }
            
        }
        
        catch(Exception nn){
            System.out.println("ERROR OCCURED HERE--"+nn);
        }
        
    }
    
    
    
    public void startServer(){
        
        int sn=0;
        
        try{
            serverSocket = new ServerSocket(10898);

    System.out.println("Server started. Listening to the port 10898");
    
    serverText.appendText("\n"+"Server started. Listening to the port 10898"+"\n");
            startStop="STARTED";

    while(true){
        
    
    clientSocket = serverSocket.accept();

    byte[] mybytearray = new byte[filesize];   
    
    String stID = SessionIdentifierGenerator.nextSessionId().toUpperCase();

    inputStream = clientSocket.getInputStream();
    fileOutputStream = new FileOutputStream("C:\\SMLA_SHARE_DATA\\"+stID+".txt");
    bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

    System.out.println("Receiving...");
    
    serverText.appendText("Receiving File..."+"\n");

    bytesRead = inputStream.read(mybytearray, 0, mybytearray.length);
    current = bytesRead;
    
    sn++;

    do {
        bytesRead = inputStream.read(mybytearray, current, (mybytearray.length - current));
        if (bytesRead >= 0) {
            current += bytesRead;
        } 
    } while (bytesRead > -1);

    System.out.println("Sever recieved the file.."+sn);
    serverText.appendText("Sever recieved the file.."+sn+"\n");
    
    bufferedOutputStream.write(mybytearray, 0, current);
    bufferedOutputStream.flush();
    bufferedOutputStream.close();
    inputStream.close();
    
    
    }
    
}
        catch(Exception nn){
            //Exception here..
        }
        
    }
    
    public void stopServer(){
        
        try{
            
            if(serverSocket!=null){
                //clientSocket.close();
                startStop="STOPPED";
                serverButton.setText("START SERVER");
                serverSocket.close();
                               
                serverText.appendText("\n"+"SERVER STOPPED...");
            }
            
        }
        catch(Exception nn){
            System.out.println("ERROR "+nn);
            serverText.appendText("\n"+"ERROR SHUTTING DOWN SERVER "+nn.getMessage());
            nn.printStackTrace();
        }
    }
    
}
