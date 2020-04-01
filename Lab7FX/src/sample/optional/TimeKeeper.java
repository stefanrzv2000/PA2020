package sample.optional;

import javafx.application.Platform;
import sample.Controller;
import sample.compulsory.Game;

import static java.lang.Thread.sleep;

public class TimeKeeper implements Runnable {

    Game game;

    int totalTime;

    int remainingTime;

    public TimeKeeper(Game game, int totalTime) {
        this.game = game;
        this.totalTime = totalTime;
        remainingTime = totalTime;
    }


    @Override
    public void run() {
        long startTime = System.nanoTime();

        while(remainingTime > 0 & game.getBoard().getTokens().size()>0 & !game.isOver()){
            remainingTime = totalTime - (int)((System.nanoTime() - startTime)/1_000_000_000);
            //System.out.println("remaining time: " + remainingTime);
            Platform.runLater(()->{game.getController().setTime(remainingTime);});

            //game.getController().showTime();
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        game.setOver(true);
    }
}
