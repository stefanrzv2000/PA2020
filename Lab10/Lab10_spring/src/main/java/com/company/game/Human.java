package com.company.game;

import com.company.MainApplication;
import com.company.serv.ClientThread;

import static java.lang.Thread.sleep;

public class Human extends Player {

    ClientThread client;

    private Piece current;

    public Human(String name, ClientThread clientThread) {
        super(name);
        client = clientThread;
    }

    @Override
    public Piece makeMove() {
        while(current == null){
            try {
                sleep(MainApplication.time);
                //System.out.print(".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        last = current;
        current = null;
        return last;
    }

    public void setPiece(Piece piece){
        current = piece;
        System.out.println(name + ": piece set");
    }
}
