package com.company.cli;

public class Response extends TextUtil{

    private boolean error;

    public Response(String text) {
        super(text);
        error = get(0).equals("error");
    }

    public boolean isError() {
        return error;
    }
}
