package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

public abstract class Card {
    protected String name;
    protected Board board;

    public Card(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    public void playCard(Player player) {
        System.out.println("--- " + player.getName() + " choose " + this.name + " card to play. ---");
    }

    public String getName() {
        return name;
    }
}
