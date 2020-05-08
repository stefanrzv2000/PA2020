package com.company.cli;

public enum CommandType {
    EXIT,
    NAME,
    CREATE,
    JOIN,
    NONE,
    PIECE,
    OK;

    public static CommandType get(String text){
        switch (text){
            case "exit": return EXIT;
            case "name": return NAME;
            case "create": return CREATE;
            case "join": return JOIN;
            case "ok": return OK;
            case "piece": return PIECE;
            default: return NONE;
        }
    }
}
