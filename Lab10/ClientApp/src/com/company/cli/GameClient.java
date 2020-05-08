package com.company.cli;

import com.company.Main;
import com.company.game.Game;
import com.company.game.Piece;
import com.company.game.Player;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static com.company.Main.EXIT;
import static com.company.Main.WIN;

public class GameClient extends Thread {

    private static final String serverAddress = "192.168.100.6";
    private static final int PORT = 3579;
    private static GameClient instance;

    private Game game;

    private Command current;
    private Response response;
    private Instruction instruction;

    private boolean active;
    private boolean waiting = true;
    private boolean running = true;

    Socket socket;

    Scanner in;
    PrintWriter out;
    Scanner sin;

    private GameClient(){
        try {
            socket = new Socket(serverAddress,PORT);
            System.out.println("Connected to " + serverAddress + ":" + PORT);

            in  = new Scanner(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            sin = new Scanner(System.in);
            active = true;
            waiting = false;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static GameClient getInstance() {
        if(instance == null) instance = new GameClient();
        return instance;
    }

    @Override
    public void run() {
        System.out.println("Indeed, I am");
        waiting = true;
        running = true;
        while (running){

            Command command = getCurrent();
            waiting=true;
            current = null;
            System.out.println("server: Am citit comanda " + command);
            response = getResponse(command);
            System.out.println("server: Am primit: " + response);

            if(response.isError()){
                System.out.println("am belit-o");
                break;
            }

            solve(command,response);

            if(response.get(0).equals("exit")) {
                System.out.println("client: exiting");
                break;
            }
            if(game.hasStarted() & game.isOver()){
                System.out.println("client: Game over!");
                break;
            }
        }

        System.out.println("client: I'm done");
    }

    private Command readCommand(){
        String text = sin.nextLine();
        return new Command(text);
    }

    private Command getCurrent(){
        while(current == null){
            try {
                sleep(Main.time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return current;
    }

    private boolean hasCommand(){
        return current != null;
    }

    public void setCommand(Command command){
        current = command;
        //notifyAll();
    }

    private Response getResponse(Command command){
        out.println(command);
        out.flush();
        System.out.println("server: Am trimis comanda " + command);

        while(!available()){
            try {
                sleep(Main.time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        return new Response(in.nextLine());
    }

    public boolean isActive() {
        return active;
    }

    public void setActive() {
        this.active = true;
    }

    public void setInActive() {
        this.active = false;
    }



    public void solve(Command command, Response response){

        String instr = "";
        String name;

        switch (command.getType()){
            case CREATE: {
                System.out.println("we need to create");
                int size = Integer.parseInt(command.get(ARGS.SIZE.index()));
                String gid = response.get(ARGS.GAME_ID.index());
                name = command.get(ARGS.PLAYER_NAME_CREATE.index());

                Player player = new Player(name,true,1);

                game = Game.newGame(size,gid);
                game.addPlayer(player);

                instr = "wait " + gid;

                System.out.println("created");

                break;
            }
            case OK:{
                System.out.println("The other player joined");
                name = response.get(ARGS.PLAYER_NAME_CREATE.index());

                Player other = new Player(name,false,2);
                game.addPlayer(other);

                System.out.println("players: " + game.getPlayers().size());

                instr = "create";
                //game.start();

                break;

            }
            case JOIN:{
                int size = Integer.parseInt(response.get(ARGS.SIZE_JOIN.index()));
                String gid = response.get(ARGS.GAME_ID.index());

                String name1 = response.get(ARGS.PLAYER_NAME_JOIN.index());
                Player player1 = new Player(name1,false,1);

                String name2 = command.get(ARGS.PLAYER_NAME_JOIN.index());
                Player player2 = new Player(name2,true,2);

                game = Game.newGame(size,gid);
                game.addPlayer(player1);
                game.addPlayer(player2);
                //game.start();

                instr = command.toString();

                break;
            }
            case EXIT: {
                game.setOver(true);
                instr = command.toString();
                running = false;
                //exitClient(1);
            }
            case PIECE:{
                if(response.get(0).equals("piece")){
                    int x = Integer.parseInt(response.get(1));
                    int y = Integer.parseInt(response.get(2));

                    if(x == EXIT){
                        running = false;
                    }
                    if(x == WIN){
                        instr = response.toString();
                        running = false;
                        break;
                    }

                    Player player = game.getOtherPlayer();
                    System.out.println("the other player is "  + player.getName());
                    Piece piece = new Piece(player,x,y);
                    player.setPiece(piece);
                }
                instr = response.toString();
                if(response.length() >= 4 && response.get(3).equals("win")){
                    running = false;
                }

                break;
            }

            default:
        }

        instruction = new Instruction(instr);
        debug("client: Instruction: " + instr);
        waiting = false;
        debug("no more waiting ");

    }


    public boolean isWaiting() {
        //System.out.println("waiting? " + waiting);
        return waiting;
    }

    public Instruction getInstruction() {
        waiting = true;
        return instruction;
    }

    public void exitClient(int status){
        instruction = new Instruction("exit");
        System.out.println("Instruction: exit");
        waiting = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(status);
    }

    private boolean available(){
        try {
            return socket.getInputStream().available()>0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void debug(String text){
        System.out.println("client: " + text);;
    }
}
