package com.company.game;

import com.company.Main;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

import static com.company.game.Direction.directions;
import static java.lang.Thread.sleep;

public class Player {
    Pane myPane;

    Color color;

    protected Game game;

    protected String name;

    protected Player next;

    protected boolean active = false;

    protected int id;

    protected static int totalId = 0;

    private Set<Piece> pieces = new HashSet<>();

    private Piece current;

    private Piece last;

    boolean human;

    public Player(String name, boolean human, int turn) {
        this.name = name;
        this.human = human;
        this.color = turn==1?Color.WHITE:Color.BLACK;
    }


    public Pane getPane() {
        return myPane;
    }

    public void setPane(Pane myPane) {
        this.myPane = myPane;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getName() {
        return name + (human?"(Me)":"");
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getNext() {
        return next;
    }

    public void setNext(Player next) {
        this.next = next;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(){
        active = true;
        //System.out.println("Trying to green the pane...");

        if(game.getGamePage() != null & myPane != null){
            game.getGamePage().setActive(myPane);
            //game.getController().showTime();
            //System.out.println("I'm green b1tch");
        }else{
            if(myPane == null) System.out.println("pane is null");
            else System.out.println("gamePage is null");
        }
        game.setCurrentPlayer(this);

    }
    public void setInActive(){
        active = false;
        //System.out.println("Trying to red the pane...");

        if(game.getGamePage() != null & myPane != null)
        {
            game.getGamePage().setInactive(myPane);
            //System.out.println("I'm red b1tch");
        }else{
            if(myPane == null) System.out.println("pane is null");
            else System.out.println("gamePage is null");
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getTotalId() {
        return totalId;
    }

    public static void setTotalId(int totalId) {
        Player.totalId = totalId;
    }

    public Set<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(Set<Piece> pieces) {
        this.pieces = pieces;
    }

    public Pane getMyPane() {
        return myPane;
    }

    public boolean isHuman() {
        return human;
    }

    public Piece makeMove(){

        while(current == null){
            try {
                sleep(Main.time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (current.getX() > 0) game.draw(current);
        Piece copy = current.copy();
        current = null;
        if (copy.getX() > 0) pieces.add(copy);
        last = copy;
        return copy;

    }


    public Piece getCurrent() {
        return current;
    }

    public void setPiece(Piece current) {
        this.current = current;
    }

    public Color getColor() {
        return color;
    }

    public void addPiece(Piece piece){
        pieces.add(piece);
    }

    public boolean win(){

        Piece piece = last;

        pieces.add(piece);

        for(var dir: directions){
            int score = 1;
            for(int j = 1;score<5;j++){
                if(pieces.contains(piece.getNext(dir,j))){
                    score++;
                }
                else{
                    break;
                }
            }
            for(int j = -1;score<5;j--){
                if(pieces.contains(piece.getNext(dir,j))){
                    score++;
                }
                else{
                    break;
                }
            }
            System.out.println(name + " -- " + score);

            if(score >= 5){
                return true;
            }
        }

        return false;
    }
}
