package com.company.optional;

import com.company.compulsory.Player;
import com.company.compulsory.PlayerType;

import static java.lang.Thread.sleep;

public class RandomPlayer extends Player {

    public RandomPlayer(String name){
        this.name = name;
        this.type = PlayerType.RANDOM;
    }

    @Override
    public void run() {

        System.out.println("Player " + name + " is in the game! The next player is " + next.getName() + ".");


        while(game.getBoard().getTokens().size() != 0){

            if(game.getBoard().getTokens().size() == 0) break;

            if(!takeTurn()) break;
/*
            try {
                sleep((int)(Math.random()*10)+1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
 */
        }

        next.setActive();
    }

    @Override
    public synchronized boolean takeTurn() {
        while (!active & game.getBoard().getTokens().size() != 0){
            System.out.println(name + ": I am waiting for my turn.");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int n = game.getBoard().getTokens().size();
        System.out.println(name + ": I have received permission to take my turn. " +
                "There are " + n + " tokens left.");
        if(n == 0) {
            //System.out.println(name + ": wtf !!!!!!!!!!!!!!!!!!!!");
            return false;
        }
        var t = getTokenFromBoard((int)(Math.random()*game.getBoard().getTokens().size()));
        System.out.println("Player " + name + " has taken the token " + t
                + ", " + game.getBoard().getTokens().size() + " tokens left.");
        setInActive();
        //next.setActive();
        System.out.println("Urmeaza player-ul " + next.getName());
        next.setActive();
        return true;
    }
}
