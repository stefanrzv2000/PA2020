package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/App.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        new Thread(()->Controller.game.start()).start();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        //Controller.game.start();
    }
}
