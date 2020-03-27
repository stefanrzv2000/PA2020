package com.company.optional;

public class HelpCommand extends Command {
    HelpCommand(){
        super(null, "irelevant");
    }

    @Override
    public void execute() {
        System.out.println("We don't know either");
    }
}
