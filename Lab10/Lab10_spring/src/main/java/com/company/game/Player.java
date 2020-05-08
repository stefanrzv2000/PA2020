package com.company.game;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.company.game.Direction.directions;

public abstract class Player {

    protected Game game;

    protected String name;

    protected Player next;

    protected boolean active = false;

    protected int id;

    protected static int totalId = 0;

    Set<Piece> pieces;

    protected Piece last;

    protected boolean bot = false;

    public Player(String name) {
        this.name = name;
        id = totalId++;
        pieces = new HashSet<>();
    }


    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getName() {
        return name;
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

    public void setActive() {
        this.active = true;
    }

    public void setInActive() {
        this.active = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract Piece makeMove();

    public boolean winsWith(Piece piece) {

        Set<Piece> pieces = new HashSet<>(this.pieces);

        pieces.add(piece);

        return win(pieces, piece);
    }

    public boolean win() {

        Piece piece = last;

        return win(pieces, piece);
/*
        pieces.add(last);

        System.out.println(name + " -- " + pieces.size());

        if(pieces.size() < 5) return false;

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

 */
    }

    public boolean win(Set<Piece> pieces, Piece piece) {

        pieces.add(piece);

        System.out.println(name + " -- " + pieces.size());

        if (pieces.size() < 5) return false;

        for (var dir : directions) {
            int score = 1;
            for (int j = 1; score < 5; j++) {
                if (pieces.contains(piece.getNext(dir, j))) {
                    score++;
                } else {
                    break;
                }
            }
            for (int j = -1; score < 5; j--) {
                if (pieces.contains(piece.getNext(dir, j))) {
                    score++;
                } else {
                    break;
                }
            }
            System.out.println(name + " -- " + score);
            if (score >= 5) {
                return true;
            }
        }

        return false;
    }

    public boolean isBot() {
        return bot;
    }
}
