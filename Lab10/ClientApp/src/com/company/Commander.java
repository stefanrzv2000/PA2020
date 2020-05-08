package com.company;

import com.company.cli.Command;
import com.company.cli.GameClient;
import com.company.cli.Instruction;

import static java.lang.Thread.sleep;

public interface Commander {
    void execute(Instruction instruction);

    default void sendCommand(String text){
        GameClient client = GameClient.getInstance();

        client.setCommand(new Command(text,this));

        System.out.print("loading...");
        while(client.isWaiting()) {
            try {
                sleep(1000);
                System.out.print(".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        execute(client.getInstruction());

    }
}
