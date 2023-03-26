package sk.stuba.fei.uim.oop.cards.blue;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Dynamite extends BlueCard {
    private static final String CARD_NAME = "Dynamite";

    public Dynamite(Board board) {
        super(CARD_NAME, board);
    }

    public void takeLifeFromPlayer(Player player, int index) {
        // if dynamite make BOOM -> delete it and add to deck of card
        System.out.println(this.getName() + " will do BOOM! to " + player.getName());
        IntStream.range(0, 3).forEach(i -> player.removeLife());
        System.out.println("Player " + player.getName() + " lost his/her live.. -💔x3" + "  ❤x" + player.getLives());
        if (player.getLives() <= 0) {
            player.printDead();
        }
        player.removeBlueCard(index);
        this.board.addDiscardingDeckCard(this);
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

    //функция активного игрока у которого нет в голубом поле Динамит!
    public Player getPreviousActivePlayer(Player player) {
        ArrayList<Player> activePlayers = new ArrayList<>();
        for (Player p : this.board.getPlayers()) {
            if (p.isActive() && p.hasDynamite()) {
                activePlayers.add(p);
            }
        }
        int currentIndex = activePlayers.indexOf(player);
        if (currentIndex > 0) {
            return activePlayers.get(currentIndex - 1);
        } else {
            return activePlayers.get(activePlayers.size() - 1);
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
