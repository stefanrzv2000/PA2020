package com.company.rest;

import java.util.Objects;

public class RestPiece {
    private int x;
    private int y;
    private boolean mine;

    public RestPiece(){}

    public RestPiece(int x, int y, boolean mine) {
        this.x = x;
        this.y = y;
        this.mine = mine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestPiece piece = (RestPiece) o;
        return x == piece.x &&
                y == piece.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
}
