package com.company.compulsory;

import java.util.ArrayList;
import java.util.List;

public abstract class Player implements Runnable {

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
        notifyAll();

    }
    public synchronized void setInActive(){
        active = false;
    }

    public abstract boolean takeTurn();


}
