package com.company.compulsory;

import java.util.ArrayList;
import java.util.List;

public class Board {
    int size;
    List<Token> tokens;

    public Board(int size){
        this.size = size;
        tokens = new ArrayList<>();
        for(int i = 0; i < size; i ++){
            tokens.add(new Token(i));
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public synchronized List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public synchronized Token giveToken(int index, Player player){
        if(index >= tokens.size()) return null;
        if(tokens.get(index).getOwner() == null) {
            Token t = tokens.get(index);
            t.setOwner(player);
            tokens.remove(index);
            return t;
        }
        return null;
    }
}
