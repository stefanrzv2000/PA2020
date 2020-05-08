package com.company.game;

import java.util.Objects;

public class Piece {
    Player owner;
    int x;
    int y;

    public Piece(Player owner, int x, int y) {
        this.owner = owner;
        this.x = x;
        this.y = y;
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

    public Piece getNext(Direction dir, int i){
        return new Piece(null,x + dir.x*i, y + dir.y*i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return x == piece.x &&
                y == piece.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
