package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sample.compulsory.Board;
import sample.compulsory.Game;
import sample.compulsory.Player;
import sample.compulsory.PlayerType;
import sample.optional.*;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class Controller implements Initializable {

    private int boardSize = 100;
    private int maxScore = 5;
    private int timeLimit = 30;

    public static Game game;
    public static Time time;

    String ACTIVEPLAYER = "-fx-background-color: ccffcc; -fx-border-color: aaffaa; -fx-border-width: 3; -fx-border-radius: 10;";
    String INACTIVEPLAYER = "-fx-background-color: ffcccc; -fx-border-color: ffaaaa; -fx-border-width: 3; -fx-border-radius: 10;";

    @FXML
    Pane Player1Pane, Player2Pane, Player3Pane, Player4Pane, Player5Pane, Player6Pane;
    @FXML
    Label player1Label, player2Label, player3Label, player4Label, player5Label, player6Label;
    @FXML
    Canvas canvas;
    @FXML
    Label timeLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Pane[] panes = {Player1Pane, Player2Pane, Player3Pane, Player4Pane, Player5Pane, Player6Pane};

        Label[] labels = {player1Label, player2Label, player3Label, player4Label, player5Label, player6Label};

        for (var pane: panes){
            pane.setVisible(false);
        }

        Board board = new Board(boardSize);

        game = new Game(this,maxScore,timeLimit);

        game.setBoard(board);

        game.addPlayer(new RandomPlayer("Mihai"));
        game.addPlayer(new SmartPlayer("Ioan"));
        game.addPlayer(new ManualPlayer("Sorin"));
        game.addPlayer(new SmartPlayer("Florin"));
        game.addPlayer(new SmartPlayer("Alex"));
        game.addPlayer(new RandomPlayer("Andrei"));
        game.addPlayer(new RandomPlayer("Andreiii"));
        game.addPlayer(new RandomPlayer("Andreiii"));
        game.addPlayer(new RandomPlayer("Andreiii"));

        for(int i = 0; i < game.getPlayers().size(); i++){
            game.getPlayers().get(i).setMyPane(panes[i]);
            panes[i].setVisible(true);
            labels[i].setText(game.getPlayers().get(i).getName());
        }

        initBoard(board);

        //Player1Pane.setStyle(ACTIVEPLAYER);

        //game.start();
    }

    private void initBoard(Board board){

        double h = canvas.getHeight();
        double w = canvas.getWidth();

        int nr = board.getSize();
        int rows = (int)Math.sqrt(nr-1) + 1;

        double h0 = h/(rows);
        double w0 = w/(rows);

        double size = Math.min(h0,w0)*2.0/5.0;

        var tokens = board.getTokens();

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < rows & i*rows+j < tokens.size(); j++){
                var token = tokens.get(i*rows+j);
                token.setFigure(new Figure(w0/2 + w0*j,h0/2 + h0*i, size, 6,
                        new Color(Math.random()*0.5 + 0.3,Math.random()*0.5 + 0.3,Math.random()*0.5 + 0.3,1),
                        String.valueOf(token.getValue())));
                drawFigure(token.getFigure());
            }
        }
    }

    public void setActive(Pane pane){ pane.setStyle(ACTIVEPLAYER); }

    public void setInactive(Pane pane){
        pane.setStyle(INACTIVEPLAYER);
    }

    public void drawFigure(Figure figure){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(figure.getColor());
        double centerX = figure.getX();
        double centerY = figure.getY();
        double size = figure.getSize();

        int sides = figure.getSides();

        String text = figure.getText();

        double[] x_es = IntStream.range(0,sides)
                .mapToDouble(i -> centerX + size*Math.sin(2*Math.PI*(double)i/(double)sides))
                .toArray();


        double[] y_es = IntStream.range(0,sides)
                .mapToDouble(i -> centerY + size*Math.cos(2*Math.PI*(double)i/(double)sides))
                .toArray();

        graphicsContext.fillPolygon(x_es, y_es, sides);

        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setLineWidth(1);
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);
        graphicsContext.strokeText(text,centerX,centerY);
    }

    public void unDrawFigure(Figure figure){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(new Color(1,1,1,1));
        double centerX = figure.getX();
        double centerY = figure.getY();
        double size = figure.getSize();

        graphicsContext.fillOval(centerX-size,centerY-size,2*size,2*size);
    }

    public synchronized void setTime(int totalTime){
        time.setTime(totalTime);
        //System.out.println("Controller: time = " + time.timeToString());
        showTime();
    }


    public synchronized void showTime(){
        timeLabel.setText(time.timeToString());
    }

    public void onCanvasClicked(MouseEvent e){

        Player player = game.getCurrentPlayer();

        //System.out.println("Current player is " + player.getName());

        if(player.getType()!= PlayerType.MANUAL | game.isOver()) return;

        double x = e.getX();
        double y = e.getY();

        //System.out.println("Clicked: x = " + x + " y = " + y);

        for(int i = 0; i < game.getBoard().getTokens().size(); i++){
            var token = game.getBoard().getTokens().get(i);

            double cx = token.getFigure().getX();
            double cy = token.getFigure().getY();
            double size = token.getFigure().getSize();

            if((cx-x)*(cx-x) + (cy-y)*(cy-y) < size*size){
                //System.out.println("cx = " + cx + " cy = " + cy );
                ((ManualPlayer)player).setSelectedToken(i);
                //System.out.println("Am gasit i = " + i);
                break;
            }

        }

    }

    public void exitButton(){
        game.setOver(true);
        Stage stage = (Stage)timeLabel.getScene().getWindow();
        stage.close();
    }

    public static void shutDown(){
        game.setOver(true);
    }

}
