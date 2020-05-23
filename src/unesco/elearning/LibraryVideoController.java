/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class LibraryVideoController implements Initializable {
    @FXML
    private Label title;
    @FXML
    private FontAwesomeIconView bck;
    @FXML
    private MediaView myVideo;
    @FXML
    private Text note;
    
    MediaPlayer mp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        title.setText(LibraryController.iTitle);
        note.setText(LibraryController.iDesc);
        
        File fx = new File(LibraryController.cLoc+"/"+LibraryController.iExt);
        
        Media media = new Media(fx.toURI().toString());
        mp = new MediaPlayer(media);
        
        myVideo.setMediaPlayer(mp);
        
        mp.play();
        
        // TODO
    }    

    @FXML
    private void close(MouseEvent event) {
        
        mp.stop();
        LibraryController.lbStage.close();
    }
    
}
