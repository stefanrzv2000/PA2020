package com.company.serv;

import java.util.Arrays;
import java.util.List;

public abstract class TextUtil {

    protected final String text;
    protected final List<String> args;

    public TextUtil(String text) {
        this.text = text;
        args = Arrays.asList(text.split(" "));
    }

    @Override
    public String toString() {
        return text;
    }

    public String get(int i){
        return args.get(i);
    }

}

