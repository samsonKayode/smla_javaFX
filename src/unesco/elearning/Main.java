/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

/**
 *
 * @author SAMSON KAYODE
 */
import java.io.File;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
//  w  w  w.j av a2s  . c  o m
public class Main extends Application {
  @Override
  public void start(final Stage stage) {
    stage.setWidth(1000);
    stage.setHeight(700);
    Scene scene = new Scene(new Group());


    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(browser);

    webEngine.getLoadWorker().stateProperty()
        .addListener(new ChangeListener<State>() {
          @Override
          public void changed(ObservableValue ov, State oldState, State newState) {

            if (newState == Worker.State.SUCCEEDED) {
              stage.setTitle(webEngine.getLocation());
            }

          }
        });
    //webEngine.load("http://google.com");
    
    File f = new File("C:\\LMS\\Basic Technology\\Lessons\\iSpring\\Lesson_Two (Web)\\index.html");
    
    try {
        browser.getEngine().load(f.toURI().toURL().toString());
    } catch (MalformedURLException ex) {
        
        System.out.println("ERROR "+ex);
    }

    scene.setRoot(browser);

    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
