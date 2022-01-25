/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class GameFormController{   
    private Stage stage;

    public void setStage(Stage stage) {
       this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene gameFormScene = new Scene(root);
        stage.setScene(gameFormScene);
    }
    
    
}
