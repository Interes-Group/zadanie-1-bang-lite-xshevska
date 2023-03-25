package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.player.Player;

public class Missed extends Card {
    private static final String CARD_NAME = "Missed";

    public Missed(Board board) {
        super(CARD_NAME, board);
    }

    @Override
    public void playCard(Player player) {
        System.out.println("BEFORE playing Missed card. GameCards = " + this.board.sizeOfGameCards());
        player.removeCard(this);
        this.board.addGameCard(this);
        System.out.println("AFTER playing Missed card. GameCards = " + this.board.sizeOfGameCards());
        System.out.println("Player " + player.getName() + " had luck and it saved him/her! [❤]");
    }
}
