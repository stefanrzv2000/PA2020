package sample.compulsory;

import javafx.scene.layout.Pane;
import sample.Controller;

import java.util.ArrayList;
import java.util.List;

public abstract class Player implements Runnable {

    Pane myPane;

    protected Game game;

    protected PlayerType type;

    protected String name;

    protected Player next;

    protected List<Token> tokens = new ArrayList<>();

    protected boolean active = false;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getNext() {
        return next;
    }

    public void setNext(Player next) {
        this.next = next;
    }

    public Token getTokenFromBoard(int index){
        Token token = game.getBoard().giveToken(index,this);
        if(token != null) {
            tokens.add(token);
            return token;
        }
        return null;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public String getName() {
        return name;
    }

    public synchronized void setActive(){
        /*
        while (active) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         */
        active = true;
        if(myPane != null){
            game.getController().setActive(myPane);
        }
        notifyAll();

    }
    public synchronized void setInActive(){
        active = false;
        if(myPane != null)
        {
            game.getController().setInactive(myPane);
        }

    }

    public abstract boolean takeTurn();

    public Pane getMyPane() {
        return myPane;
    }

    public void setMyPane(Pane myPane) {
        this.myPane = myPane;
    }

}
