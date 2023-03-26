package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.player.Player;

public class Beer extends Card {
    private static final String CARD_NAME = "Beer";

    public Beer(Board board) {
        super(CARD_NAME, board);
    }

    @Override
    public void playCard(Player player) {
        super.playCard(player);
        System.out.println("Life up [❤ + 1] to " + player.getName());
        player.addLife();
        this.board.addDiscardingDeckCard(this);
    }
}