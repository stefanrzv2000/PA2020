package com.company.serv;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerLog {

    private static final String filename = "logs.txt";

    private PrintWriter out;

    ServerLog(){
        try {
            out = new PrintWriter(new FileWriter(filename,true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String text){
        SimpleDateFormat formatter= new SimpleDateFormat("'['yyyy-MM-dd HH:mm:ss'] '");
        Date date = new Date(System.currentTimeMillis());
        out.println(formatter.format(date) + text);
        out.flush();
    }



}
