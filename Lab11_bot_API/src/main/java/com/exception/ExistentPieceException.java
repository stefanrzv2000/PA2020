package com.exception;

import com.game.Piece;

public class ExistentPieceException extends RuntimeException{
    public ExistentPieceException(Piece piece){
        super("The piece with x = " + piece.getX() + " and y = " + piece.getY() + " is already on board.");
    }
}
