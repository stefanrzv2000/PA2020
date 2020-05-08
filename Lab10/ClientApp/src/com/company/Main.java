package com.company;

import com.company.cli.GameClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static final long time = 10;
    public static final int EXIT = -3;
    public static final int WIN = 100;
    public static final String WINS = "100";

    @Override
    public void start(Stage primaryStage) throws Exception{

        /*
        primaryStage.setOnCloseRequest(evt->{
            StartPage.shutDown();
        });
        */

        //StartPage.time = new Time(100);

        //FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/App.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));

        GameClient.getInstance().start();

        System.out.println("The client is up");

        primaryStage.show();
    }


    public static void main(String[] args) { launch(args); }

}