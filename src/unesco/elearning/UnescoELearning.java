/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import java.sql.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import unesco.elearning.database.DatabaseConn;

/**
 *
 * @author SAMSON KAYODE
 */
public class UnescoELearning extends Application {
    
    DatabaseConn db = new DatabaseConn();
    
    public static Boolean splashLoaded = false;
    public static Stage mainWindow = new Stage();
    
    @Override
    public void start(Stage stage) throws Exception {
        
        //System.out.println("I AM HERE");
        
        int createTables = db.initTables();
        if(createTables==0){
            System.out.println("Database tables created ");
            db.confirmSettings();
            
        }else{
            System.out.println("Database tables not created ");
        }  
        
        mainWindow = stage;
        
        Parent root = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
        
        stage.getIcons().add(new Image("/unesco/elearning/image/icon.png"));
        
        
        
        Rectangle2D SB = Screen.getPrimary().getVisualBounds();
        
        
        
        
        Scene scene = new Scene(root);
       
        
        System.out.println("WIDTH:::-"+SB.getWidth());
        System.out.println("HEIGHT:::-"+SB.getHeight());
                
        //Scene scene = new Scene(root);
        
        if(SB.getWidth()>=1280){
            
            
        }else{
            stage.setWidth(SB.getWidth());
        }
        
        if(SB.getHeight()<750){
            stage.setHeight(SB.getHeight());
        }else{
            
        }
        
        /*
            if(SB.getHeight()>=900){
            
        }else{
            stage.setHeight(SB.getHeight());
        
        }
        */
        
        //stage.setHeight(SB.getHeight());
        
        
        stage.setScene(scene);
                
        stage.centerOnScreen();
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
      
        
       
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
