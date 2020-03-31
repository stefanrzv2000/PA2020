package com.company;

import com.company.compulsory.Board;
import com.company.compulsory.Game;
import com.company.optional.RandomPlayer;

public class Main {

    public static void main(String[] args) {

        Board board = new Board(30);

        Game game = new Game();

        game.setBoard(board);

        game.addPlayer(new RandomPlayer("Mihai"));
        game.addPlayer(new RandomPlayer("Ioan"));
        game.addPlayer(new RandomPlayer("Andrei"));

        game.start();
    }
}
