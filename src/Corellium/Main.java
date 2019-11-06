package Corellium;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Corellium/Escritorio/escritorio.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/Corellium/Ventana/Web/web.fxml"));
        primaryStage.setTitle("Corellium v0.1");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
