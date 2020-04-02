package sample.compulsory;

import java.util.ArrayList;
import java.util.List;

public class Board {
    int size;

    List<Token> allTokens;
    List<Token> tokens;

    Game game;

    public Board(int size){
        this.size = size;
        tokens = new ArrayList<>();
        allTokens = new ArrayList<>();
        for(int i = 0; i < size; i ++){
            Token t = new Token(i);
            tokens.add(t);
            allTokens.add(t);
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
            Token token = tokens.get(index);
            token.setOwner(player);
            tokens.remove(index);
            game.getController().unDrawFigure(token.getFigure());
            return token;
        }
        return null;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Token> getAllTokens() {
        return allTokens;
    }
}
