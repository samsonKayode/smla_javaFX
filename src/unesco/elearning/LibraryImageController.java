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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author SAMSON KAYODE
 */
public class LibraryImageController implements Initializable {
    @FXML
    private FontAwesomeIconView bck;
    @FXML
    private ImageView myImg;
    @FXML
    private Label title;
    @FXML
    private Text note;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        title.setText(LibraryController.iTitle);
        
        File fx = new File(LibraryController.cLoc+"/"+LibraryController.iExt);
        
        Image img = new Image(fx.toURI().toString());
        myImg.setImage(img);
        
        note.setText(LibraryController.iDesc);
        
    }    

    @FXML
    private void close(MouseEvent event) {
        
        LibraryController.lbStage.close();
        
    }
    
}
