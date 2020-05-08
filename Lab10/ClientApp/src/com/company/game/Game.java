package com.company.game;

import com.company.Commander;
import com.company.GamePage;
import com.company.cli.Instruction;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.company.Main.EXIT;
import static com.company.Main.WINS;


public class Game extends Thread implements Commander {

    private static Game game;

    private GamePage gamePage;

    private String gid;

    private int size;

    private Set<Piece> pieces;

    private Piece last;

    private List<Player> players;

    private boolean over = true;
    private boolean started = false;

    private Player currentPlayer;

    private Player winner;

    public Game(int i, String gid) {
        size = i;
        this.gid = gid;
        pieces = new HashSet<>();
        players = new ArrayList<>();

    }

    public static Game getGame(){
        return game;
    }

    public static Game newGame(int i, String id){
        if(game!=null){
            game.end();
        }
        game = new Game(i, id);
        return game;
    }

    @Override
    public void run(){

        if(getHostPlayer() == getPlayer(1)){
            confirm();
        }


        System.out.println("The game has started");
        over = false;
        started = true;
        currentPlayer = players.get(0);
        currentPlayer.setActive();
        while(!over){
            debug("It's " + currentPlayer.getName() + "'s turn!");
            last = currentPlayer.makeMove();
            debug("move: " + last);
            //if(currentPlayer.win()){setWinner(currentPlayer);}

            switchPlayer();

            debug(inactivePlayer().getName() + " made his move");
            process(last);
            debug("processed: " + inactivePlayer().getName() + " made his move");
        }
        if(winner != null){
            System.out.println(winner.getName() + " is the Winner!");
        }

        debug("I'm done");
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player){
        players.add(player);
        player.setGame(this);
    }

    public void addPiece(Piece piece){
        pieces.add(piece);
    }

    public Player getPlayer(int i){
        return players.get(i);
    }

    public int getSize() {
        return size;
    }

    public String getGid() {
        return gid;
    }

    public GamePage getGamePage() {
        return gamePage;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getHostPlayer(){
        Player player = null;
        for(Player p: players){
            if(p.isHuman()) player = p;
        }
        return player;
    }

    public Player getOtherPlayer(){
        Player player = null;
        for(Player p: players){
            if(!p.isHuman()) player = p;
        }
        return player;
    }

    public void setGamePage(GamePage gamePage) {
        this.gamePage = gamePage;
    }

    public void end(){
        over = true;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;

        if(winner.isHuman()){
            Platform.runLater(()->gamePage.showWin());
            //gamePage.showWin();
        }
        else{
            Platform.runLater(()->gamePage.showLose());
            //gamePage.showLose();
        }

        end();
    }

    public Piece getLast() {
        return last;
    }

    public void setLast(Piece last) {
        this.last = last;
    }

    public void switchPlayer(){
        currentPlayer.setInActive();
        debug("Inactive player: " + currentPlayer.getName());
        for(var p: players){
            if(currentPlayer!=p){
                currentPlayer = p;
                break;
            }
        }
        currentPlayer.setActive();
        debug("Active player: " + currentPlayer.getName());

    }

    public boolean containsPiece(Piece piece){
        return pieces.contains(piece);
    }

    public void process(Piece piece){
        if(piece.getX() > 0) {
            pieces.add(piece);
            draw(piece);

            if (piece.getOwner().isHuman()) {
                String cmd = "piece " + piece.getX() + " " + piece.getY();
                if (winner != null) {
                    cmd = cmd + " win";
                }
                sendCommand(cmd);
            } else {
                getOtherPlayer().addPiece(piece);
            }
        }
        else {
            if(piece.getX() == EXIT){
                if (piece.getOwner().isHuman()) {
                    String cmd = "piece " + piece.getX() + " " + piece.getY();
                    if (winner != null) {
                        cmd = cmd + " win";
                    }
                    sendCommand(cmd);

                } else {
                    Platform.runLater(()->gamePage.otherExited());
                    over = true;
                }
            }
        }
    }

    @Override
    public void execute(Instruction instruction) {
        debug("Instruction: " + instruction);
        switch (instruction.getType()){

            case PIECE:{
                if(instruction.length() >= 4 && instruction.get(3).equals("win")){
                        over = true;
                        Platform.runLater(()->gamePage.showLose());
                }
                if(instruction.get(2).equals(WINS)){
                    over = true;
                    Platform.runLater(()->gamePage.showWin());
                }
                break;
            }
            case EXIT:
                over = true;
                Platform.runLater(()->gamePage.end());
            default:
        }
    }

    public void draw(Piece piece){
        gamePage.drawPiece(piece);
    }

    private void confirm(){
        sendCommand("piece -2" + " -2");
    }

    public boolean isOver() {
        return over;
    }

    public void debug(String text){
        System.out.println("game: " + text);
        System.out.flush();
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public boolean hasStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public Player inactivePlayer(){
        Player player = null;
        for(var p: players){
            if(p!=currentPlayer){
                player = p;
                break;
            }
        }
        return player;
    }
}
