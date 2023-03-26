package sk.stuba.fei.uim.oop.cards.blue;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

public class Barrel extends BlueCard {
    private static final String CARD_NAME = "Barrel";

    public Barrel(Board board) {
        super(CARD_NAME, board);
    }

    @Override
    public void playCard(Player player) {
        super.playCard(player);
        this.playBlueCard(player);
    }
}