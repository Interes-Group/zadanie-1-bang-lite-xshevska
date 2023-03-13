package sk.stuba.fei.uim.oop.cards.blue;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;

public class BlueCard extends Card {

    protected String color;

    public BlueCard(String name, Board board) {
        super(name, board);
        this.color = "Blue";
    }

    public String getColor() {
        return color;
    }
}
