package com.company.serv;


import com.jcraft.jsch.*;

public class Loader {


    private static final String user = "stefan.balauca";
    private static final String host = "students.info.uaic.ro";
    private static final int PORT = 22;
    private static final String pwd = "12759a54c98dafb96c77";
    private static final String path = "games/";

/*
    private static final String user = "demo";
    private static final String host = "test.rebex.net";
    private static final String pwd = "password";
    private static final String path = "~/games/";
*/
    JSch jsch;
    Session session;
    ChannelSftp channel;

    public Loader(){
        jsch = new JSch();
        try {
            session = jsch.getSession(user, host, PORT);
            session.setPassword(pwd);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            channel.cd(path);

            System.out.println("connected");

        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }

    }

    public void upload(String file){
        try {
            channel.put("./src/main/resources/" + file, file);
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        channel.exit();
        System.out.println("sftp Channel exited.");
        channel.disconnect();
        System.out.println("Channel disconnected.");
        session.disconnect();
        System.out.println("Host Session disconnected.");
    }

}
