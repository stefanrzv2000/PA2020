package com.company.serv;

public enum ARGS {
    PLAYER_NAME_CREATE(1),
    SIZE(2),
    BOT(3),
    GAME_ID(1),
    PLAYER_NAME_JOIN(2),
    X(1),
    Y(2);

    int i;

    ARGS(int i){
        this.i = i;
    }

    public int index(){
        return i;
    }
}
