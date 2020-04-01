package sample.compulsory;

import javafx.scene.layout.Pane;
import sample.Controller;

import java.util.ArrayList;
import java.util.List;

public abstract class Player implements Runnable {

    Pane myPane;

    protected Game game;

    protected PlayerType type;

    protected String name;

    protected Player next;

    protected List<Token> tokens = new ArrayList<>();

    protected boolean active = false;

    protected int totalTokens;


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getNext() {
        return next;
    }

    public void setNext(Player next) {
        this.next = next;
    }

    public Token getTokenFromBoard(int index){
        Token token = game.getBoard().giveToken(index,this);
        if(token != null) {
            tokens.add(token);
            return token;
        }
        return null;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public String getName() {
        return name;
    }

    public synchronized void setActive(){
        /*
        while (active) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         */
        active = true;
        if(myPane != null){
            game.getController().setActive(myPane);
            //game.getController().showTime();
        }
        game.setCurrentPlayer(this);
        notifyAll();

    }
    public synchronized void setInActive(){
        active = false;
        if(myPane != null)
        {
            game.getController().setInactive(myPane);
        }

    }

    public abstract boolean takeTurn();

    public Pane getMyPane() {
        return myPane;
    }

    public void setMyPane(Pane myPane) {
        this.myPane = myPane;
    }

    public int calculateScore(){
        return calculateScore(tokens);
    }

    public int calculateScore(List <Token> givenTokens){
        int score = 0;

        int []frequence = new int[totalTokens];

        for (Token token : givenTokens){
            frequence[token.getValue()] ++;
        }

        for (int ratio = 1; ratio < totalTokens; ratio++){
            for (int start = 0; start < totalTokens; start++){
                int ratioScore = 0;
                for (int tokenVal = start; tokenVal < totalTokens; tokenVal += ratio){
                    if (frequence[tokenVal] == 0){
                        break;
                    }
                    ratioScore++;
                }

                if (score < ratioScore){
                    score = ratioScore;
                }
            }
        }

        return score;
    }

    public List<Integer> calculateScoreList(){
        int score = calculateScore();

        int []frequence = new int[totalTokens];

        for (Token token : tokens){
            frequence[token.getValue()] ++;
        }

        for (int ratio = 1; ratio < totalTokens; ratio++){
            for (int start = 0; start < totalTokens; start++){
                int ratioScore = 0;

                List <Integer> currentList = new ArrayList<>();
                for (int tokenVal = start; tokenVal < totalTokens; tokenVal += ratio){
                    if (frequence[tokenVal] == 0){
                        break;
                    }
                    ratioScore++;
                    currentList.add(tokenVal);
                }

                if (score == ratioScore){
                    return currentList;
                }
            }
        }

        return null;
    }

    @Override
    public final void run() {

        totalTokens = game.getBoard().getSize();

        //System.out.println("Player " + name + " is in the game! The next player is " + next.getName() + ".");


        while(game.getBoard().getTokens().size() != 0 & !game.isOver()){

            if(!takeTurn()) break;

            int score = calculateScore();

            if(score >= game.getMaxScore()){
                System.out.println("\nCongratulations, " + name + ", you reached " + score + " points and you have WON the game!\n");

                game.setOver(true);
            }

        }

        next.setActive();
    }

    public PlayerType getType() {
        return type;
    }
}
