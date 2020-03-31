package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import sample.compulsory.Board;
import sample.compulsory.Game;
import sample.optional.*;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class Controller implements Initializable {

    public static Game game;

    String ACTIVEPLAYER = "-fx-background-color: ccffcc; -fx-border-color: aaffaa; -fx-border-width: 3; -fx-border-radius: 10;";
    String INACTIVEPLAYER = "-fx-background-color: ffcccc; -fx-border-color: ffaaaa; -fx-border-width: 3; -fx-border-radius: 10;";

    @FXML
    Pane Player1Pane, Player2Pane, Player3Pane, Player4Pane, Player5Pane, Player6Pane;
    @FXML
    Label player1Label, player2Label, player3Label, player4Label, player5Label, player6Label;
    @FXML
    Canvas canvas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Pane[] panes = {Player1Pane, Player2Pane, Player3Pane, Player4Pane, Player5Pane, Player6Pane};

        Label[] labels = {player1Label, player2Label, player3Label, player4Label, player5Label, player6Label};

        for (var pane: panes){
            pane.setVisible(false);
        }

        Board board = new Board(30);

        game = new Game(this);

        game.setBoard(board);

        game.addPlayer(new RandomPlayer("Mihai"));
        game.addPlayer(new SmartPlayer("Ioan"));
        game.addPlayer(new RandomPlayer("Andrei"));

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
        int rows = (int)Math.sqrt(nr) + 1;

        double h0 = h/(rows-1);
        double w0 = h/(rows-1);

        double size = Math.min(h0,w0)*2.0/5.0;

        var tokens = board.getTokens();

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < rows & i*rows+j < tokens.size(); j++){
                var token = tokens.get(i*rows+j);
                token.setFigure(new Figure(w0/2 + w0*j,h0/2 + h0*i, size, 6,
                        new Color(Math.random()*0.5,Math.random()*0.5,Math.random()*0.5,1),
                        String.valueOf(token.getValue())));
                drawFigure(token.getFigure());
            }
        }
    }

    public void setActive(Pane pane){
        pane.setStyle(ACTIVEPLAYER);
    }

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
}
