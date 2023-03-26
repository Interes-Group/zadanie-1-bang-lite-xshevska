package sk.stuba.fei.uim.oop.cards.blue;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.Random;

public class BlueCard extends Card {
    protected Random random;

    public BlueCard(String name, Board board) {
        super(name, board);
        this.random = new Random();
    }

    public boolean checkEffect(Player player) {
        return this.random.nextInt(4) == 3;
    }

    public boolean checkDuplicate(Player player, Card card) {
        for (Card c : player.getBlueCards()) {
            if (c.equals(card)) {
                return true;
            }
        }
        return false;
    }

    protected void playBlueCard(Player player) {
        if (player.checkDuplicate(this)) {
            System.out.println(player.getName() + " already have duplicate of card " + this.getName());
            System.out.println("Play with another card.");
            player.addCard(this);
        } else {
            System.out.println(this.getName() + " going into " + player.getName() + "' blue field");
            player.addBlueCard(this);
        }
    }

    @Override
    public String toString() {
        return "BlueCard{" +
                ", name='" + name + '\'' +
                '}';
    }
}
