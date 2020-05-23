/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import com.jfoenix.controls.JFXRadioButton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import unesco.elearning.alerts.DialogBoxes;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class PinLessonController implements Initializable {
    @FXML
    private ToggleGroup lesson_group;
    
    String selOpt="", rpos="";
    
    DialogBoxes dbox = new DialogBoxes();
    @FXML
    private JFXRadioButton lesson_1;
    @FXML
    private JFXRadioButton lesson_2;
    @FXML
    private JFXRadioButton lesson_3;
    @FXML
    private JFXRadioButton lesson_4;
    @FXML
    private JFXRadioButton lesson_5;
    @FXML
    private JFXRadioButton lesson_6;
    
    JFXRadioButton nlesson_1,nlesson_2,nlesson_3,nlesson_4,nlesson_5,nlesson_6;
    
    public static String xLoc="";
    String myLoc ="";
    String selItem="";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nlesson_1 = lesson_1;
        nlesson_2 = lesson_2;
        nlesson_3 = lesson_3;
        nlesson_4 = lesson_4;
        nlesson_5 = lesson_5;
        nlesson_6 = lesson_6;
        
        if(xLoc.equals("FORMAL")){
            myLoc = CourseListController.myLoc;
            selItem=CourseListController.selItem;
        }else{
            myLoc = NonFormalCoursesController.myLoc;
            selItem = NonFormalCoursesController.selItem;
        }
        
        String less1 = findExist(myLoc, "LESS1");
        String a[] = less1.split("#");
        nlesson_1.setText(a[1]);
        
        String less2 = findExist(myLoc, "LESS2");
        String b[] = less2.split("#");
        nlesson_2.setText(b[1]);
        
        String less3 = findExist(myLoc, "LESS3");
        String c[] = less3.split("#");
        nlesson_3.setText(c[1]);
        
        String less4 = findExist(myLoc, "LESS4");
        String d[] = less4.split("#");
        nlesson_4.setText(d[1]);
        
        String less5 = findExist(myLoc, "LESS5");
        String e[] = less5.split("#");
        nlesson_5.setText(e[1]);
        
        String less6 = findExist(myLoc, "LESS6");
        String f[] = less6.split("#");
        nlesson_6.setText(f[1]);
        
    }    

    @FXML
    private void less1(ActionEvent event) {
        
        rpos = "1";
        selOpt = "LESS1";
        
    }

    @FXML
    private void less2(ActionEvent event) {
        
        rpos = "2";
        selOpt = "LESS2";
    }

    @FXML
    private void less3(ActionEvent event) {
        
        rpos = "3";
        selOpt = "LESS3";
    }

    @FXML
    private void less4(ActionEvent event) {
        
        rpos = "4";
        selOpt = "LESS4";
    }

    @FXML
    private void less5(ActionEvent event) {
        
        rpos = "5";
        selOpt = "LESS5";
    }

    @FXML
    private void less6(ActionEvent event) {
        
        rpos = "6";
        selOpt = "LESS6";
    }

    @FXML
    private void cancel(ActionEvent event) {
        
        if(xLoc.equals("FORMAL")){
            
            CourseListController.courseStage.close();
            
        }else{
            NonFormalCoursesController.courseStage.close();
        }

    }

    @FXML
    private void save(ActionEvent event) {
        
        int a = writeToFile();
        
        if(a==0){
            //data saved..
            
            //dbox.showMessageDialog(Alert.AlertType.INFORMATION, "Information", null, selItem +" Saved to Position "+rpos);
            //CourseListController.courseStage.close();
            
            if(xLoc.equals("FORMAL")){
            
            CourseListController.courseStage.close();
            
        }else{
            NonFormalCoursesController.courseStage.close();
        }

        }
        
        else{
            //data not saved..
            
            dbox.showMessageDialog(Alert.AlertType.ERROR, "Error", null, "Error saving lesson to position");
            CourseListController.courseStage.close();
        }
    }
    
    public int writeToFile()
    {
        int res =1;
        String xyz="";
        
        if(xLoc.equals("FORMAL")){
            xyz = selOpt+" #"+SelectTermController.termSelected+"/"+CourseListController.selItem;
        }else{
            xyz = selOpt+" #"+"/"+NonFormalOptionController.TITLE+"/"+NonFormalCoursesController.selItem;
        }

        String x = findExist(myLoc, selOpt);
        
        int xo = replaceText(myLoc, x, xyz);
        
        if(xo==0)
        {
            res =0;
        }
        else
        {
            res = 1;
        }
        
        return res;
        
        }
    
    public String findExist(String file, String word){
        
    String rt="";
        
    File nfile = new File(file);
    Scanner scanner = null;

    try {
        scanner = new Scanner(nfile);
    } catch(FileNotFoundException e) { 
        
       //handle this
    }

    //now read the file line by line
    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if(line.contains(word)) { 
            
            rt = line;
        }
        
        else{
            //rt= "NIL";
        }
        
    }
    scanner.close();
    
    return rt;
    }
    
    public int replaceText(String nfile, String word, String newText){
        
        int res =1;
        
        try
             {
             File file = new File(nfile);
             BufferedReader reader = new BufferedReader(new FileReader(file));
             String line = "", oldtext = "";
             while((line = reader.readLine()) != null)
                 {
                     
                         oldtext += line + "\r\n";
                     

             }
             reader.close();

             //To replace a line in a file
             String newtext = oldtext.replaceAll(word, newText);
            
             FileWriter writer = new FileWriter(nfile);
             writer.write(newtext);writer.close();
             
             res =0;
         }
         catch (IOException ioe)
             {
             ioe.printStackTrace();
             res = 1;
         }
        
        return res;
        
    }
    
}
