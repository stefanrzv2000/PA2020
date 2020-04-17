package com.app.shell;

import com.daos.FactoryTypes;

public class Shell {
    private FactoryTypes method;

    boolean active;

    public Shell(){
        active = true;
    }

    public void start(){
        System.out.println("Welcome to MusicAlbums database shell!\nPlease select your preferred method of operating with the database with use <method>.");

        while(active){
            printNewLine();
        }
    }

    private void printNewLine(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("MusicAlbums/" + method + ">>");
        System.out.flush();
    }

}
