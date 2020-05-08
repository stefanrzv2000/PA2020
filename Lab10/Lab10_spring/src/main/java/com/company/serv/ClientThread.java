package com.company.serv;

import static com.company.MainApplication.*;
import com.company.game.*;
import com.company.rest.Services;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread{
    private int id;
    private Socket socket;
    private GameServer server;
    private final Services services = new Services();

    private Human player;
    private Game game;

    Scanner in;
    PrintWriter out;

    boolean active;

    public ClientThread(int id, Socket socket, GameServer server) {
        this.id = id;
        this.socket = socket;
        this.server = server;

        active = true;

        try {
            in  = new Scanner(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        log("Starting work");
        while(active){
            Command command;
            command = readCommand();
            log("command: " + command);

            if(command.getType() == CommandType.EXIT){
                exitThread(1);
            }

            Response response = solve(command);
            sendResponse(response);

            if(response.get(1).equals(EXITS)){
                exitThread(1);
            }

        }

        log("Finished working");
    }

    private Command readCommand() {
        String text = in.nextLine();
        return new Command(text);
    }

    private void sendResponse(Response response){
        log("response: " + response);
        out.println(response);
        out.flush();
    }

    private void log(String text){
        server.saveLog("Thread " + id + ": " + text);
    }

    private void debug(String text){
        String name = player.getName();
        System.out.println("Thread " + id + "(" + name + "): " + text);
    }

    private Response solve(Command command){
        String response = "";
        String name;

        switch (command.getType()){
            case NAME:
                break;
            case JOIN:{

                String gid = command.get(ARGS.GAME_ID.index());
                name = command.get(ARGS.PLAYER_NAME_JOIN.index());

                Integer pid2 = services.postPlayer(name);

                if(Game.exists(gid)){
                    player = new Human(name,this);
                    game = Game.get(gid);
                    game.addPlayer(player);
                    game.setPid2(pid2);
                    game.start();
                    response = "join " + gid + " " + game.getPlayer(0).getName() + " " + game.getSize();
                }
                else{
                    response = "error invalid_gid";
                }

                break;
            }
            case CREATE:
            {
                name = command.get(ARGS.PLAYER_NAME_CREATE.index());
                player = new Human(name,this);
                Integer pid1 = services.postPlayer(name);

                debug("name = " + name);

                int size = Integer.parseInt(command.get(ARGS.SIZE.index()));
                boolean bot = command.get(ARGS.BOT.index()).contains("y");

                game = new Game(size);
                game.addPlayer(player);
                game.setPid1(pid1);
                if(bot) {
                    game.addPlayer(new Bot());
                    game.setPid1(0);
                    game.start();
                }

                debug("game created: " + " " + game.getGid());

                response = "create " + game.getGid();

                break;
            }
            case OK: {
                debug("raspuns ok");
                name = waitForPlayer();
                response = "player " + name;
                break;
            }
            case EXIT: {
                response = "exit " + EXITS;
                break;
            }
            case PIECE:{

                int x = Integer.parseInt(command.get(ARGS.X.index()));
                int y = Integer.parseInt(command.get(ARGS.Y.index()));


                debug("got coordinates " + x + " " + y);

                if(x != -2) {
                    Piece piece = new Piece(player,x,y);
                    player.setPiece(piece);
                    boolean win = player.winsWith(piece);
                    debug("win = " + win);
                    if(win){
                        response = "piece " + WIN + " " + WIN;
                        active = false;
                        break;
                    }
                }
/*

                if(x == EXIT){
                    response = "exit " + EXITS;
                    break;
                }

*/
                debug("Trying to access game.currentPlayer");

                while(game.getCurrentPlayer() != player){
                    try {
                        sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if(x == EXIT){
                    response = "exit " + EXITS;
                    break;
                }


                debug("Trying to access game.current");
                Piece next;
                do {
                    try {
                        sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    next = game.getCurrent(player);
                }while(next == null);



                response = "piece " + next.getX() + " " + next.getY();



                if(game.getWinner() != null){
                    response = response + " win";
                    active = false;
                }

                break;
            }

            default:
        }

        return new Response(response);
    }

    private String waitForPlayer(){

        while(game.getPlayers().size() < 2){
            try {
                sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //debug("waiting for another player...");

            try {
                if(socket.getInputStream().available()>0){
                    debug("why am I here?");
                    Command command = readCommand();
                    if(command.getType() == CommandType.EXIT){
                        exitThread(1);
                    }
                    sendResponse(solve(command));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //debug("Still waiting for another player...");

        }

        return game.getPlayer(1).getName();
    }

    private void exitThread(int status){
        if(game != null) game.setOver(true);
        sendResponse(new Response("exit"));
        active = false;
        //System.exit(status);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
