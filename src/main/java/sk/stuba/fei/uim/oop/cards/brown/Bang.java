package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.player.Player;

public class Bang extends BrownCard {
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
            player.setLives(player.getLives() - 1);
            System.out.println("Player " + player.getName() + " lost his live.. -💔x1" + "  ❤x" +player.getLives());
        } else {
            missed.playCard(player);
        }
    }

}
