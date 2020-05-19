package com.game;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private static List<Game> games = new ArrayList<>();

    private int id;
    private int size;

    private Set<Piece> pieces = new HashSet<>();
    private Set<Piece> myPieces = new HashSet<>();
    private Set<Piece> othersPieces = new HashSet<>();

    public Game(int size){
        id = games.size();
        this.size = size;
        games.add(this);
    }

    public static List<Game> getGames() {
        return games;
    }

    public static void setGames(List<Game> games) {
        Game.games = games;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static Game get(int id){
        if(id < games.size()){
            return games.get(id);
        }
        return null;
    }

    public boolean addPiece(Piece piece){
        if(pieces.contains(piece)){
            return false;
        }
        pieces.add(piece);
        if(piece.isMine()){
            myPieces.add(piece);
        }
        else {
            othersPieces.add(piece);
        }
        return true;
    }

    public Piece move(){
        Piece piece;
        do {
            piece = new Piece(
                    (int) (Math.random() * getSize()),
                    (int) (Math.random() * getSize()),
                    true);
        }while (pieces.contains(piece));
        return piece;
    }

}
