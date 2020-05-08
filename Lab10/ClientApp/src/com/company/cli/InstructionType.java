package com.company.cli;

public enum InstructionType {
    CREATE,EXIT,NONE,JOIN,WAIT,PIECE;

    public static InstructionType get(String text){
        switch (text){
            case "exit": return EXIT;
            case "create": return CREATE;
            case "join": return JOIN;
            case "wait": return WAIT;
            case "piece": return PIECE;
            default: return NONE;
        }
    }
}
