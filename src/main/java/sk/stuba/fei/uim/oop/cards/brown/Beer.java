package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

public class Beer extends BrownCard {
    private static final String CARD_NAME = "Beer";

    public Beer(String name, Board board) {
        super(name, board);
    }

    @Override
    public void playCard(Player player) {

    }
}
