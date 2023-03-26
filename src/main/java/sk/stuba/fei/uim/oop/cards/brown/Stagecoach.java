package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.player.Player;

public class Stagecoach extends Card {
    private static final String CARD_NAME = "Stagecoach";

    public Stagecoach(Board board) {
        super(CARD_NAME, board);
    }

    @Override
    public void playCard(Player player) {
        super.playCard(player);
        if(this.board.getGameCards().size() < 3){
            System.out.println("You can't play " + this.getName() + " right now.\nPlay with another card.");
            player.addCard(this);
        }else {
            this.board.pullTwoCards(player);
            this.board.addDiscardingDeckCard(this);
            System.out.println("Two cards are pulled to " + player.getName());
        }
    }
}
