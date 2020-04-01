package sample.compulsory;

import sample.Controller;
import sample.optional.Time;
import sample.optional.TimeKeeper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Thread.sleep;

public class Game {

    private Controller controller;

    private List<Player> players;

    private Board board;

    private boolean over = false;

    public Game(Controller controller)
    {
        players = new ArrayList<>();
        this.controller = controller;
    }

    public void start(){

        TimeKeeper timeKeeper = new TimeKeeper(this,10);

        for (int i = 0; i < players.size()-1; i++){
            players.get(i).setNext(players.get(i+1));
        }
        players.get(players.size()-1).setNext(players.get(0));

        Thread[] t = new Thread[players.size()];

        for (int i = 0; i < players.size(); i ++){
            players.get(i).setInActive();
            t[i] = new Thread(players.get(i));
            t[i].start();
        }

        new Thread(timeKeeper).start();

        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        players.get(0).setActive();

        for(var thread: t){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(var player: players){
            player.getTokens().sort(Comparator.comparingInt(Token::getValue));
            System.out.println("player " + player.getName() + " has " + player.getTokens().size() + " tokens: " + player.getTokens().toString());
        }

    }

    public void addPlayer(Player player){
        players.add(player);
        player.setGame(this);
    }


    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
        board.setGame(this);
    }

    public boolean isOver() {
        return over;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}
