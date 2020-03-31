package com.company.compulsory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Thread.sleep;

public class Game {
    private List<Player> players;

    private Board board;

    public Game(){
        players = new ArrayList<>();
    }

    public void start(){
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
    }
}
