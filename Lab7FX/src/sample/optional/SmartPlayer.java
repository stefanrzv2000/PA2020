package sample.optional;


import sample.compulsory.*;

import java.util.*;

import static java.lang.Thread.sleep;

public class SmartPlayer extends Player {

    public SmartPlayer(String name){
        this.name = name;
        this.type = PlayerType.SMART;
        this.id = totalId;
        totalId++;
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
        int nrTokensLeft = game.getBoard().getTokens().size();

        if(nrTokensLeft == 0 | game.isOver()) { return false; }

        //System.out.println(name + ": I have received permission to take my turn. " +
        //        "There are " + n + " tokens left.");
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(game.isOver()) { return false; }

        /*
        //FIRST METHOD
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

        Token chosenToken;

        double chance = Math.random();

        boolean attack = false;
        if (chance < 0.66){
            attack = true;
        }

        int tokenVal;
        if (attack) {
            tokenVal = getBestTokenVal(tokens, 2);
        }
        else {
            tokenVal = getBestBlockTokenVal();
        }

        if (tokenVal != -1) {

            int index = 0;
            for (Token token : game.getBoard().getTokens()) {
                if (token.getValue() == tokenVal) {
                    break;
                }
                index++;
            }

            chosenToken = getTokenFromBoard(index);
        }
        else{
            if (attack){
                tokenVal = getBestBlockTokenVal();
            }
            else {
                tokenVal = getBestTokenVal(tokens, 2);
            }

            if (tokenVal != -1){
                int index = 0;
                for (Token token : game.getBoard().getTokens()) {
                    if (token.getValue() == tokenVal) {
                        break;
                    }
                    index++;
                }

                chosenToken = getTokenFromBoard(index);
            }
            else {
                chosenToken = getTokenFromBoard((int)(Math.random()*game.getBoard().getTokens().size()));
            }
        }

        //System.out.println("Player " + name + " has taken the token " + t
        //        + ", " + game.getBoard().getTokens().size() + " tokens left.");
        setInActive();
        //System.out.println("Urmeaza player-ul " + next.getName());
        next.setActive();
        return true;
    }


    //bad method
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

    private int getBestTokenVal(List<Token> givenTokens, int consideredMoves){
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

    private int getBestBlockTokenVal(){
        List<Player> others = new ArrayList<>();

        for (Player player : game.getPlayers()){
            if (this == player){
                continue;
            }
            others.add(player);
        }

        others.sort(Comparator.comparingInt(Player::calculateScore));

        int tokenValue = -1;
        for (Player player : others){
            tokenValue = getBestTokenVal(player.getTokens(), 1);
            if (tokenValue != -1){
                break;
            }
        }
        return tokenValue;
    }
}
