package sk.stuba.fei.uim.oop.cards.blue;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

public class Prison extends BlueCard {
    private static final String CARD_NAME = "Prison";

    public Prison(Board board) {
        super(CARD_NAME, board);
    }

    @Override
    public void playCard(Player player) {
        super.playCard(player);
        player.addBlueCard(this);
    }





}
