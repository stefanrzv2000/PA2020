package sample.compulsory;

import java.util.ArrayList;
import java.util.List;

public class Board {
    int size;
    List<Token> tokens;

    Game game;

    public Board(int size){
        this.size = size;
        tokens = new ArrayList<>();
        for(int i = 0; i < size; i ++){
            tokens.add(new Token(i+1));
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
            game.getController().unDrawFigure(t.getFigure());
            return t;
        }
        return null;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
