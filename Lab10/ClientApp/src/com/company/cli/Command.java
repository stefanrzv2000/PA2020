package com.company.cli;

import com.company.Commander;

import java.util.Arrays;
import java.util.List;

public class Command extends TextUtil{

    private final CommandType type;
    Commander from;

    public Command(String text) {
        super(text);
        this.from = null;
        type = CommandType.get(args.get(0));
    }

    public Command(String text, Commander from) {
        super(text);
        this.from = from;
        type = CommandType.get(args.get(0));
    }

    public CommandType getType() {
        return type;
    }

}
