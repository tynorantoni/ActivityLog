
package com.mycompany.projectone;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MainApp extends Application {

    private Stage myStage;
    private AnchorPane rootPane;

    @Override
    public void start(Stage myStage) throws Exception {
        this.myStage = myStage;
        this.myStage.setTitle("Activity Log");
       
        initRootPane();
    }

    public void initRootPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/Scene.fxml"));

            rootPane = (AnchorPane) loader.load();

            Scene scene = new Scene(rootPane);
            myStage.setScene(scene);
            myStage.show();

            FXMLController kontroler = loader.getController();
            kontroler.setMainApp(this);
            kontroler.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
