package com.company;

import com.company.cli.ARGS;
import com.company.cli.Command;
import com.company.cli.GameClient;
import com.company.cli.Instruction;
import com.company.game.Game;
import com.company.game.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class StartPage implements Commander {

    GameClient client = GameClient.getInstance();

    @FXML
    TextField nameText, gameIdText,sizeText;
    @FXML
    Button createButton, joinButton;
    @FXML
    CheckBox botBox;


    private String getName(){
        return nameText.getText();
    }

    private String getId(){
        return gameIdText.getText();
    }

    private int getSize(){
        int size = -1;
        try{
            size = Integer.parseInt(sizeText.getText());
        }catch (IllegalArgumentException ignored){ }

        return size;
    }

    public void showAlert(AlertType type, String message, String title){
        //System.out.println("clicked");
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
        //System.out.println("passed");
    }

    public void onJoinClicked(){
        String name = getName();
        String gid = getId();

        if(name.isBlank()){
            showAlert(AlertType.ERROR,"Name must not be blank!","ERROR");
            return;
        }

        if(gid.isBlank()){
            showAlert(AlertType.ERROR,"Game ID must not be blank!","ERROR");
            return;
        }

        showAlert(AlertType.CONFIRMATION,"Your name is " + name + " and the game ID is " + gid,"Join");

        if(!client.isActive()){
            return;
        }

        sendCommand("join " + gid + " " + name);
    }

    public void onCreateClicked(){
        String name = getName();
        int size = getSize();
        String bot = botBox.isSelected()?"y":"n";

        if(name.isBlank()){
            showAlert(AlertType.ERROR,"Name must not be blank!","ERROR");
            return;
        }

        if(size == -1){
            showAlert(AlertType.ERROR,"Size must be a number!","ERROR");
            return;
        }
        if(size > 19 | size < 5){
            showAlert(AlertType.ERROR,"Size has to be between 5 and 19!","Incorrect size");
            return;
        }

        showAlert(AlertType.CONFIRMATION,"Your name is " + name + " and the size is " + size,"Create");

        if(!client.isActive()){
            return;
        }

        sendCommand("create " + name + " " + size + " " + bot);

    }

    public void onExitClicked(){
        System.out.println("trying to exit");
        showAlert(AlertType.INFORMATION,"Waiting for the server's response","Waiting");
        sendCommand("exit");
    }

    @Override
    public void execute(Instruction instruction) {
        String name;

        switch (instruction.getType()){
            case CREATE:
            case JOIN: {
                createGamePage();
                return;
            }

            case WAIT:{
                String gid = instruction.get(1);
                showAlert(AlertType.INFORMATION,"Please wait until another player joins your game." +
                        "\nYour game ID is " + gid,"Wait");
                sendCommand("ok");
                return;
            }

            case EXIT:
                System.exit(1);

            default:
        }
    }

    private void createGamePage(){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("GamePage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root));
            stage.show();
            nameText.getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
