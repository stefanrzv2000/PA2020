package com.company.game;

import java.util.ArrayList;
import java.util.List;

public class Direction {
    public static List<Direction> directions = new ArrayList<>();

    static{
        directions.add(new Direction(0,1));
        directions.add(new Direction(1,0));
        directions.add(new Direction(1,1));
        directions.add(new Direction(-1,1));
    }

    int x;
    int y;

    public Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

