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
            if(c.equals(card)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "BlueCard{" +
                ", name='" + name + '\'' +
                '}';
    }
}
