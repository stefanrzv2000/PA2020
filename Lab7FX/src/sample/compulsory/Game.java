package sample.compulsory;

import sample.Controller;
import sample.optional.Time;
import sample.optional.TimeKeeper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Thread.sleep;

public class Game {

    private int maxScore;

    private int timeLimit;

    private Controller controller;

    private List<Player> players;

    private Board board;

    private boolean over = false;

    Player currentPlayer;

    public Game(Controller controller, int maxScore, int timeLimit)
    {
        players = new ArrayList<>();
        this.controller = controller;
        this.maxScore = maxScore;
        this.timeLimit = timeLimit;
    }

    public void start(){

        TimeKeeper timeKeeper = new TimeKeeper(this,timeLimit);

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

        currentPlayer = players.get(0);
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
            System.out.println(player.getName() + " score: " + player.calculateScore());
            System.out.println(player.calculateScoreList());
        }

    }

    public void addPlayer(Player player){
        if(players.size() >= 6){
            System.err.println("Player limit exceeded: Only 6 players can play at a time!");
            return;
        }
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getMaxScore() {
        return maxScore;
    }
}
