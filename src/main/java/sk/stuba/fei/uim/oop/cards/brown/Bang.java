package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.player.Player;

public class Bang extends Card {
    private static final String CARD_NAME = "Bang";

    public Bang(String name, Board board) {
        super(CARD_NAME, board);
    }


    @Override
    public void playCard(Player player) {
        Card missed = player.getCards().stream()
                .filter(card -> card instanceof Missed)
                .findAny()
                .orElse(null);
        if (missed == null) {
            player.removeLife();
            if (player.getLives() == 0) {
                System.out.println("Player " + player.getName() + " lost his live.. -💔x1" + "  ❤x" +player.getLives());
                player.printDead();
            }else {
                System.out.println("Player " + player.getName() + " lost his live.. -💔x1" + "  ❤x" +player.getLives());
            }
            this.board.addGameCard(this);
        } else {
            missed.playCard(player);
            this.board.addGameCard(this);
        }
    }
}
