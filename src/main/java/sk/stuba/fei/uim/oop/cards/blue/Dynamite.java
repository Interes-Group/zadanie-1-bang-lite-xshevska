package sk.stuba.fei.uim.oop.cards.blue;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

public class Dynamite extends BlueCard {
    private static final String CARD_NAME = "Dynamite";

    public Dynamite(Board board) {
        super(CARD_NAME, board);
    }

    @Override
    public boolean checkEffect(Player player) {
        return super.checkEffect(player);
    }
}
