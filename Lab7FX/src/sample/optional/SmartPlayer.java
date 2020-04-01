package sample.optional;


import sample.compulsory.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SmartPlayer extends Player {

    public SmartPlayer(String name){
        this.name = name;
        this.type = PlayerType.SMART;
    }


    @Override
    public synchronized boolean takeTurn() {
        while (!active & game.getBoard().getTokens().size() != 0){
            //System.out.println(name + ": I am waiting for my turn.");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int n = game.getBoard().getTokens().size();

        if(n == 0 | game.isOver()) { return false; }

        //System.out.println(name + ": I have received permission to take my turn. " +
        //        "There are " + n + " tokens left.");
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(game.isOver()) { return false; }

        /*
        //var t = getTokenFromBoard((int)(Math.random()*game.getBoard().getTokens().size()));


        List<Token> shuffled = new ArrayList<>(game.getBoard().getTokens());
        List<Token> have = new ArrayList<>(tokens);

        Token t = getBestToken(have, shuffled, 0, 4);

        if (t == null){
            t = getTokenFromBoard((int)(Math.random()*game.getBoard().getTokens().size()));
        }
        else{
            int index = 0;
            for (Token token : game.getBoard().getTokens()){
                if (token.getValue() == t.getValue()){
                    t = getTokenFromBoard(index);
                    break;
                }
                index++;
            }
        }
        */

        Token t;
        int tokenVal = getBestTokenVal(tokens, 4);
        if (tokenVal != -1) {

            int index = 0;
            for (Token token : game.getBoard().getTokens()) {
                if (token.getValue() == tokenVal) {
                    break;
                }
                index++;
            }

            t = getTokenFromBoard(index);
        }
        else{
            t = getTokenFromBoard((int)(Math.random()*game.getBoard().getTokens().size()));
        }

        //System.out.println("Player " + name + " has taken the token " + t
        //        + ", " + game.getBoard().getTokens().size() + " tokens left.");
        setInActive();
        //System.out.println("Urmeaza player-ul " + next.getName());
        next.setActive();
        return true;
    }

    public Token getBestToken(List<Token> chosenTokens, List<Token> remainedTokens, int depth, int stopDepth) {
        if (depth >= stopDepth) {
            return null;
        }

        int score = calculateScore(chosenTokens);

        for (Token token : remainedTokens){
            chosenTokens.add(token);

            int possibleScore = calculateScore(chosenTokens);
            if (possibleScore > score){
                chosenTokens.remove(token);
                return token;
            }

            chosenTokens.remove(token);
        }

        List<Token> remainedTokensClone = new ArrayList<>(remainedTokens);

        for (Token token : remainedTokensClone){
            chosenTokens.add(token);
            remainedTokens.remove(token);
            Token futureOption = getBestToken(chosenTokens, remainedTokens, depth+1, stopDepth);

            if (futureOption != null){
                chosenTokens.remove(token);
                remainedTokens.add(token);
                return futureOption;
            }

            remainedTokens.add(token);
            chosenTokens.remove(token);
        }

        return null;
    }

    public int getBestTokenVal(List<Token> givenTokens, int consideredMoves){
        int maxPossibleScore = calculateScore(givenTokens);

        int[] frequence = new int[totalTokens];

        for (Token token : givenTokens){
            frequence[token.getValue()] ++;
        }

        int []isAvailable = new int[totalTokens];

        for (Token token : game.getBoard().getTokens()){
            isAvailable[token.getValue()] ++;
        }

        List <Integer> bestOptions = new ArrayList<>();

        for (int ratio = 1; ratio < totalTokens; ratio++){
            for (int start = 0; start < totalTokens; start++){
                int ratioScore = 0;
                int futureMoves = 0;
                List <Integer> currentOptions = new ArrayList<>();

                for (int tokenVal = start; tokenVal < totalTokens; tokenVal += ratio){
                    if (frequence[tokenVal] == 0){
                        if (isAvailable[tokenVal] == 1){
                            futureMoves ++;
                            if (futureMoves > consideredMoves)
                                break;
                        }
                        else{
                            break;
                        }

                        currentOptions.add(tokenVal);
                    }
                    ratioScore++;
                }

                if (maxPossibleScore < ratioScore){
                    maxPossibleScore = ratioScore;
                    bestOptions = currentOptions;
                }
            }
        }

        int randomTokenIndex = (int) (Math.random() * bestOptions.size());
        int tokenVal = -1;
        if (bestOptions.size() != 0) {
            tokenVal = bestOptions.get(randomTokenIndex);
        }
        return tokenVal;
    }
}
