package com.company.serv;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

    private static final String serverAddress = "192.168.100.6";
    private static final int PORT = 3579;

    private ServerSocket serverSocket;
    private ServerLog log;

    public GameServer(){
        log = new ServerLog();
        serverSocket = null;
        try {
            InetAddress addr = InetAddress.getByName(serverAddress);
            saveLog("Server up - listening to " + serverAddress + ":" + PORT);
            int id = 0;
            serverSocket = new ServerSocket(PORT,0,addr);
            while (true){
                Socket socket = serverSocket.accept();
                new ClientThread(id,socket,this).start();
                saveLog("Accepted client " + id);
                id++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void saveLog(String text){
        log.write(text);
        System.out.println(text);
    }


}
