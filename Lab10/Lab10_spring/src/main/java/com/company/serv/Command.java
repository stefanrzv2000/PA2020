package com.company.serv;

import java.util.Arrays;
import java.util.List;

public class Command extends TextUtil{

    private final CommandType type;

    public Command(String text){
        super(text);
        type = CommandType.get(args.get(0));
    }

    public String getText() {
        return text;
    }

    public CommandType getType() {
        return type;
    }

    public List<String> getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return text;
    }
}
