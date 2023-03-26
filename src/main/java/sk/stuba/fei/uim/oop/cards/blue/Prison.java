package sk.stuba.fei.uim.oop.cards.blue;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

public class Prison extends BlueCard {
    private static final String CARD_NAME = "Prison";

    public Prison(Board board) {
        super(CARD_NAME, board);
    }

    private int findPrison(Player player) {
        int indexCart = -1;
        for (int i = 0; i < player.getBlueCards().size(); i++) {
            if (player.getBlueCards().get(i) instanceof Prison) {
                indexCart = i;
                break;
            }
        }
        return indexCart;
    }

    @Override
    public void playCard(Player player) {
        super.playCard(player);
        player.addBlueCard(this);
    }

    @Override
    public boolean checkEffect(Player player) {
        player.removeBlueCard(this.findPrison(player));
        this.board.addDiscardingDeckCard(new Prison(this.board));
        return super.checkEffect(player);
    }
}
