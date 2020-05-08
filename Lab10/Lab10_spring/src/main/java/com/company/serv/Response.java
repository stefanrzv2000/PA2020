package com.company.serv;

public class Response extends TextUtil{

    boolean error;

    public Response(String text) {
        super(text);
        error = args.get(0).contains("error");
    }

    public boolean isError() {
        return error;
    }

}
