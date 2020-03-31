package sample.optional;

import sample.compulsory.*;

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

            if(!takeTurn()) break;

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
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(n == 0) { return false; }
        var t = getTokenFromBoard((int)(Math.random()*game.getBoard().getTokens().size()));
        System.out.println("Player " + name + " has taken the token " + t
                + ", " + game.getBoard().getTokens().size() + " tokens left.");
        setInActive();
        System.out.println("Urmeaza player-ul " + next.getName());
        next.setActive();
        return true;
    }
}