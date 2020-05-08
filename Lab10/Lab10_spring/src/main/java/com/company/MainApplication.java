package com.company;

import com.company.rest.Services;
import com.company.rest.RestPlayer;
import com.company.serv.GameServer;

public class MainApplication {

    public static final long time = 10;
    public static final int EXIT = -3;
    public static final int WIN = 100;
    public static final String EXITS = "-3";

    public static void main(String[] args) {
/*

        Loader loader = new Loader();
        String path = "./src/main/resources/";
        String file = "gidd" + ".txt";
        System.out.println(file);

        try(PrintWriter print = new PrintWriter(new FileWriter(path + file))) {
            print.println("The winner is " + "no one" + ".");
        } catch (IOException e) {
            e.printStackTrace();
        }

        loader.upload(file);
        loader.close();

*/
/*
        Services caller = new Services();
        RestPlayer player = caller.getById(2);
        System.out.println(player);
*/
        GameServer server = new GameServer();
    }
}
