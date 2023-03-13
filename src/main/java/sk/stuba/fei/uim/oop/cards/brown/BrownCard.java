package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;

public class BrownCard extends Card {

    protected String color;

    public BrownCard(String name, Board board) {
        super(name, board);
        this.color = "Brown";
    }

    public String getColor() {
        return color;
    }
}
