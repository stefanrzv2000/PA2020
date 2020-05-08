package com.company.serv;

public enum CommandType {
    EXIT,
    NAME,
    JOIN,
    CREATE,
    NONE,
    OK,
    PIECE;

    public static CommandType get(String text){
        switch (text){
            case "exit": return EXIT;
            case "name": return NAME;
            case "join": return JOIN;
            case "create": return CREATE;
            case "ok": return OK;
            case "piece": return PIECE;
            default: return NONE;
        }
    }
}
