package com.company.game;

import java.util.Objects;

public class Piece {
    private Player owner;
    private int x;
    private int y;

    public Piece(Player owner, int x, int y) {
        this.owner = owner;
        this.x = x;
        this.y = y;
    }

    public Piece copy(){
        return new Piece(owner,x,y);
    }

    public Piece getNext(Direction dir, int i){
        return new Piece(owner,x + dir.x*i, y + dir.y*i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;
        Piece piece = (Piece) o;
        return x == piece.x &&
                y == piece.y &&
                Objects.equals(owner, piece.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, x, y);
    }

    public Player getOwner() {
        return owner;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "owner=" + owner +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
