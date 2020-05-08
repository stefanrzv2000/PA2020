package com.company;

import com.company.cli.Instruction;
import com.company.game.Game;
import com.company.game.Piece;
import com.company.game.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import static javafx.scene.control.Alert.AlertType.*;
import static com.company.Main.EXIT;


public class GamePage implements Commander, Initializable {

    private int boardSize = 30;
    private int maxScore = 6;
    private int timeLimit = 10;

    public Game game;

    String ACTIVE_PLAYER = "-fx-background-color: ccffcc; -fx-border-color: aaffaa; -fx-border-width: 3; -fx-border-radius: 10;";
    String INACTIVE_PLAYER = "-fx-background-color: ffcccc; -fx-border-color: ffaaaa; -fx-border-width: 3; -fx-border-radius: 10;";

    @FXML
    Pane Player1Pane, Player2Pane, Player3Pane, Player4Pane, Player5Pane, Player6Pane;
    @FXML
    Label player1Label, player2Label, player3Label, player4Label, player5Label, player6Label;
    @FXML
    Canvas canvas;
    @FXML
    Label timeLabel,gidLabel;

    GraphicsContext draw;

    double[] x_es;
    double[] y_es;

    double w;
    double h;

    int size;

    double len;
    double side;
    double big;
    double xOff;
    double yOff;
    double radius;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        draw = canvas.getGraphicsContext2D();

        game = Game.getGame();

        game.setGamePage(this);

        Pane[] panes = {Player1Pane, Player2Pane, Player3Pane, Player4Pane, Player5Pane, Player6Pane};

        Label[] labels = {player1Label, player2Label, player3Label, player4Label, player5Label, player6Label};

        for (var pane: panes){
            pane.setVisible(false);
        }

        int i = 0;
        for (var player: game.getPlayers()){
            player.setPane(panes[i]);
            panes[i].setVisible(true);
            labels[i].setText(player.getName());
            i++;
        }

        gidLabel.setText(game.getGid());

        drawBoard();

        game.start();
    }

    @Override
    public void execute(Instruction instruction) {
        switch (instruction.getType()){
            case CREATE:

            default:
        }
    }

    public void drawCircle(double cx, double cy, double rad, Color color){
        draw.setFill(color);
        draw.fillOval(cx-rad,cy-rad,2*rad,2*rad);

    }

    public void drawPiece(Piece piece){
        double cx = x_es[piece.getX()];
        double cy = y_es[piece.getY()];

        drawCircle(cx,cy,radius+2,Color.BLACK);
        drawCircle(cx,cy,radius,piece.getOwner().getColor());
    }

    private void drawBoard(){
        draw.setFill(new Color(233.0/255,195.0/255,114.0/255,1));
        w = canvas.getWidth();
        h = canvas.getHeight();

        size = game.getSize();

        len = Math.min(w,h);
        side = len/(size+1);
        big = side*(size-1);
        xOff = (w-big)/2;
        yOff = (h-big)/2;
        radius = side/2.5;

        System.out.println(w + " " + h + " " + big);
        System.out.println(xOff + " " + yOff);

        draw.fillRect(0,0,w,h);


        x_es = IntStream.range(0,size)
                .mapToDouble(i -> xOff + i*side)
                .toArray();

        y_es = IntStream.range(0,size)
                .mapToDouble(i -> yOff + i*side)
                .toArray();

        draw.setStroke(Color.BLACK);
        for(var x:x_es){
            draw.strokeLine(x,y_es[0],x,y_es[size-1]);
        }

        for(var y: y_es){
            draw.strokeLine(x_es[0],y,x_es[size-1],y);
        }

    }

    public void setActive(Pane pane){
        pane.setStyle(ACTIVE_PLAYER);

        /*if(pane == Player1Pane){
            System.out.println("Pane 1 green apparently");
        }

        if(pane == Player2Pane){
            System.out.println("Pane 2 green apparently");
        }*/

    }

    public void setInactive(Pane pane){
        pane.setStyle(INACTIVE_PLAYER);

        /*if(pane == Player1Pane){
            System.out.println("Pane 1 red apparently");
        }

        if(pane == Player2Pane){
            System.out.println("Pane 2 red apparently");
        }*/
    }

    public void onCanvasClicked(MouseEvent event){
        //System.out.println("Am primit un click!");
        if(game.isOver()){return;}
        Player player = game.getCurrentPlayer();
        //System.out.println("It is " + player.getName() + "'s turn");
        //System.out.println("It is " + player.getColor().toString());
        //System.out.flush();
        if(!player.isHuman()){return;}

        double x = event.getX();
        double y = event.getY();

        //System.out.println("x_es: " + Arrays.toString(x_es));
        //System.out.println("y_es: " + Arrays.toString(y_es));
        //System.out.println(x + " " + y);

        int l = 0;
        int c = 0;
        boolean ok = false;
        for(var xx: x_es){
            if((x-xx)*(x-xx) < radius*radius){
                for(var yy: y_es){
                    if((x-xx)*(x-xx) + (y-yy)*(y-yy) < radius*radius){
                        ok = true;
                        break;
                    }
                    l++;
                }
                break;
            }
            c++;
        }

        if(!ok) {return;}

        //System.out.println(c + " " + l);
        //System.out.println(x_es[c] + " " + y_es[l]);

        Piece piece = new Piece(player,c,l);
        if(game.containsPiece(piece)){return;}

        player.setPiece(piece);
    }

    public void showAlert(Alert.AlertType type, String message, String title){
        //System.out.println("clicked");
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
        //System.out.println("passed");
    }


    public void showWin(){
        showAlert(INFORMATION,"Congratulations, You won the game!","Winner");
        end();
    }

    public void showLose(){
        showAlert(INFORMATION,"Sorry, " + game.getOtherPlayer().getName() + " won the game!","Loser");
        end();
    }

    public void onExitClicked(){
        if(game.isOver()){return;}
        Player player = game.getCurrentPlayer();

        if(!player.isHuman()){return;}

        Piece piece = new Piece(player,EXIT, EXIT);
        player.setPiece(piece);
    }

    public void otherExited(){
        showAlert(INFORMATION,"Sorry, " + game.getOtherPlayer().getName() + " has exited the game.", "Game over");
        game.setOver(true);
        end();
    }

    public void end(){
        Stage stage = (Stage)timeLabel.getScene().getWindow();
        stage.close();
    }

}
