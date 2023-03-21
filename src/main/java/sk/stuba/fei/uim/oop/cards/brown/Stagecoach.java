package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.player.Player;

public class Stagecoach extends Card {
    private static final String CARD_NAME = "Stagecoach";

    public Stagecoach(String name, Board board) {
        super(name, board);
    }

    @Override
    public void playCard(Player player) {
        this.board.pullTwoCards(player);
        this.board.addGameCard(this);
        System.out.println("Two cards are pulled to " + player.getName());
    }
}
