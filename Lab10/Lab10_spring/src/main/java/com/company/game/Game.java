package com.company.game;

import com.company.rest.RestGame;
import com.company.rest.Services;
import com.company.serv.Loader;
import com.github.javafaker.Faker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


public class Game extends Thread{
    private static final Map<String,Game> games = new HashMap<>();

    private String gid;

    private Integer pid1;
    private Integer pid2;

    private final int size;

    private final Set<Piece> pieces;

    private Piece last;

    private Piece current;

    private final List<Player> players;

    private Map<Player,Piece> curr;

    private boolean over = false;

    private Player currentPlayer;

    private Player winner;

    private String file;
    private final String path = "./src/main/resources/";
    private PrintWriter fout;

    private RestGame restGame;

    public Game(int size){
        restGame = new RestGame();
        last = new Piece(null, -1,-1);
        Faker faker = new Faker();
        this.size = size;
        restGame.setDataJoc(String.valueOf(LocalDate.now()));
        restGame.setSize(size);
        pieces = new HashSet<>();
        players = new ArrayList<>();
        do{
            gid = faker.color().name() + (int)(Math.random()*1000);
            gid = gid.replace(" ","_");
        }while (existsGame(gid));
        restGame.setGameId(gid);
        games.put(gid,this);
    }

    public static Game get(String id){
        return games.get(id);
    }


    public void addPlayer(Player player){
        players.add(player);
        player.setGame(this);
    }

    public void addPiece(Piece piece){
        pieces.add(piece);
    }

    public void run(){
        debug("Game started");
        over = false;

        startRecording();

        curr = new HashMap<>();
        for(var p: players){
            curr.put(p,null);
        }

        currentPlayer = players.get(0);
        currentPlayer.setActive();
        while(!over && winner == null){
            //current = null;
            System.out.println("It's " + currentPlayer.getName() + "'s turn!");
            last = currentPlayer.makeMove();
            sendBotMove(last);
            if(currentPlayer.win()){ setWinner(currentPlayer);}

            debug(currentPlayer.getName() + " made his move");
            process();
            debug("processed: " + currentPlayer.getName() + " made his move");
            switchPlayer();
        }
        if(winner != null){
            debug(winner.getName() + " is the Winner!");
        }
        loadResults();
        debug("Game over!");
    }

    public static boolean exists(String gid){
        return games.containsKey(gid);
    }

    public Player getPlayer(int i){
        return players.get(i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(gid, game.gid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gid);
    }

    public String getGid() {
        return gid;
    }

    public int getSize() {
        return size;
    }

    public boolean isOver() {
        return over;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public void debug(String text){
        System.out.println("Game Thread " + getId() + ": " + text);
    }

    public void process(){

        Player p = getOtherThan(currentPlayer);

        String color = p==players.get(0)?"W ":"B ";
        record(color + last.getX() + " " + last.getY());
        if(winner != null){
            record("RE " + winner.getName());
        }

        pieces.add(last);
        current = last;
        curr.put(p,last);
        last = null;

    }

    public void switchPlayer(){
        currentPlayer.setInActive();
        for(var p: players){
            if(currentPlayer!=p){
                currentPlayer = p;
                return;
            }
        }
        currentPlayer.setActive();
    }

    public void setLast(Piece piece){
        last = piece;
    }

    public Piece getCurrent(){
        Piece copy = current;
        current = null;
        return copy;
    }

    public Piece getCurrent(Player p){
        Piece copy = curr.get(p);
        curr.put(p,null);
        return copy;
    }

    public boolean containsPiece(Piece piece){
        return pieces.contains(piece);
    }

    public Player getOtherThan(Player p){
        Player other = null;
        for(var o: players){
            if(p!=o) {
                other = o;
                break;
            }
        }
        return other;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
        restGame.setWin(winner==players.get(0)?1:2);
        //over = true;

    }

    private void loadResults(){
        fout.close();

        try {
            restGame.setSgfResume(Files.readString(Path.of(path+file)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Services services = new Services();
        services.postGame(restGame);

        Loader loader = new Loader();
        loader.upload(file);

    }

    private void startRecording(){
        try {
            file = gid + ".sgf";
            fout = new PrintWriter(new FileOutputStream(path+file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        record("AP Gomoku Java Server");
        record("GM Gomoku");
        record("GN " + gid);
        record("SZ " + size);
        record("DT " + LocalDate.now());
        record("PW " + players.get(0).getName());
        record("PB " + players.get(1).getName());
        record("PL W");

    }

    private void record(String text){
        if(fout != null) {
            fout.println(text);
            fout.flush();
        }
    }

    public Integer getPid1() {
        return pid1;
    }

    public void setPid1(Integer pid1) {
        this.pid1 = pid1;
        restGame.setId1(pid1);
    }

    public Integer getPid2() {
        return pid2;
    }

    public void setPid2(Integer pid2) {
        this.pid2 = pid2;
        restGame.setId2(pid2);
    }

    public Boolean existsGame(String gid){
        Services services = new Services();
        return games.containsKey(gid) || services.existsGameId(gid);
    }

    private void sendBotMove(Piece piece){
        Player bot = getOtherThan(currentPlayer);
        if(bot.isBot()){
            ((Bot)bot).setLastMove(piece);
        }
    }
}
