package com.company.game;

import com.company.rest.BotServer;
import com.company.rest.RestPiece;

public class Bot extends Player {

    private Integer gid;
    private BotServer botServer = new BotServer();

    private Piece lastMove;

    public Bot() {
        super("Gomoku_Master");
        bot = true;
    }

    @Override
    public Piece makeMove(){
        RestPiece move = new RestPiece(lastMove.getX(),lastMove.getY(),true);
        RestPiece piece = botServer.sendPiece(gid,move);

        return new Piece(this,piece.getX(),piece.getY());
    }


    public Piece makeMoveInitial() {
        Piece piece;
        do{
            piece = new Piece(this,
                    (int)(Math.random()*game.getSize()),
                    (int)(Math.random()*game.getSize())
                    );
        }while(game.containsPiece(piece));
        last = piece;
        return piece;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public BotServer getBotServer() {
        return botServer;
    }

    public void setBotServer(BotServer botServer) {
        this.botServer = botServer;
    }

    public Piece getLastMove() {
        return lastMove;
    }

    public void setLastMove(Piece lastMove) {
        this.lastMove = lastMove;
    }

    @Override
    public void setGame(Game game) {
        super.setGame(game);
        gid = botServer.createGame(game.getSize());
    }
}
