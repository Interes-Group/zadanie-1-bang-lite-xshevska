package sk.stuba.fei.uim.oop.cards.blue;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class Dynamite extends BlueCard {
    private static final String CARD_NAME = "Dynamite";

    public Dynamite(Board board) {
        super(CARD_NAME, board);
    }

    public void takeLifeFromPlayer(Player player, int index) {
        // if dynamite make BOOM -> delete it and add to deck of card
        System.out.println(this.getName() + " will do BOOM! to " + player.getName());
        player.removeLife();
        player.removeLife();
        player.removeLife();
        System.out.println("Player " + player.getName() + " lost his/her live.. -💔x3" + "  ❤x" + player.getLives());
        if (player.getLives() <= 0) {
            player.printDead();
        }
        player.removeBlueCard(index);
        this.board.addGameCard(this);
    }

    public void moveDynamiteToPreviousPlayer(Player player, int index) {
        // else delete Dynamite from player, and at it to previous player
        System.out.println(this.getName() + " will not explode.\nDynamite will move to previous Player.");

        player.removeBlueCard(index);

        // check previous active player
        // returnPreviousActivePlayer()  - getGameIndexOfCurrentPlayer()
        Player previousPlayer = this.getPreviousActivePlayer(player);
        System.out.println("Dynamite move to Player: " + previousPlayer.getName() +
                " from Player: " + player.getName());
        previousPlayer.addBlueCard(this); // this
    }

    public Player getPreviousActivePlayer(Player player) {
        this.board.getGameIndexOfCurrentPlayer(player);

        ArrayList<Player> activePlayers = new ArrayList<>();
        for (Player p : this.board.getPlayers()) {
            if(p.isActive()) {
                activePlayers.add(p);
            }
        }
        int currentIndex = activePlayers.indexOf(player);
        if (currentIndex > 0) {
            return activePlayers.get(currentIndex - 1);
        } else {
            return activePlayers.get(activePlayers.size()-1);
        }
    }

    @Override
    public boolean checkEffect(Player player) {
        return this.random.nextInt(8) == 7;
    }

    @Override
    public void playCard(Player player) {
        super.playCard(player);
        this.playBlueCard(player);
    }
}
