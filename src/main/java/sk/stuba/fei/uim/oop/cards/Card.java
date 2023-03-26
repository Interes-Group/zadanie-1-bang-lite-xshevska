package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.Objects;

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


    private int setTarget() {
        int victim;
        while (true) {
            victim = ZKlavesnice.readInt("*** Who do you want to shoot? : ***") - 1;
            if (victim < 0 || victim > this.board.getPlayers().length - 1) {
                System.out.println(" !!! You enter wrong number of card. Try Again! !!! ");
            } else {
                break;
            }
        }
        return victim;
    }

    protected int getPlayerNumber(Player player) {
        int playNumber = this.setTarget();

        while (playNumber == this.board.getGameIndexOfCurrentPlayer(player) || !this.board.getPlayers()[playNumber].isActive()) {
            System.out.println("You can't play to that player.");
            System.out.println("Chose another player PLEASE!");
            playNumber = this.setTarget();
        }
        return playNumber;
    }

    @Override
    public String toString() {
        return "Card name is " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(name, card.name) && Objects.equals(board, card.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, board);
    }

}
