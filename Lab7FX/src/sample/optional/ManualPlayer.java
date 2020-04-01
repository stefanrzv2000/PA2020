package sample.optional;

import sample.compulsory.Player;
import sample.compulsory.PlayerType;

import static java.lang.Thread.sleep;

public class ManualPlayer extends Player {

    int selectedToken;

    public ManualPlayer(String name){
        this.name = name;
        this.type = PlayerType.MANUAL;
    }

    @Override
    public synchronized boolean takeTurn() {
        while (!active & game.getBoard().getTokens().size() != 0){
            //System.out.println(name + ": I am waiting for my turn.");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int n = game.getBoard().getTokens().size();

        if(n == 0 | game.isOver()) { return false; }

        //System.out.println(name + ": I have received permission to take my turn. " +
        //        "There are " + n + " tokens left.");

        selectedToken = -1;

        while(selectedToken == -1 & !game.isOver()){
            try {
                sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("DEBUG:                 " + name + ": selectedToken = " + selectedToken);
        }
        if(game.isOver()) return false;


        var t = getTokenFromBoard(selectedToken);

        //System.out.println("Player " + name + " has taken the token " + t
        //        + ", " + game.getBoard().getTokens().size() + " tokens left.");


        setInActive();
        //System.out.println("Urmeaza player-ul " + next.getName());
        next.setActive();

        return true;
    }


    public int getSelectedToken() {
        return selectedToken;
    }

    public void setSelectedToken(int selectedToken) {
        this.selectedToken = selectedToken;
    }
}
