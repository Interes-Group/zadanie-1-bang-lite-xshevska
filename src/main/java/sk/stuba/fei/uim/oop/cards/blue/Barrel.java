package sk.stuba.fei.uim.oop.cards.blue;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

public class Barrel extends BlueCard {
    private static final String CARD_NAME = "Barrel";

    public Barrel(String name, Board board) {
        super(name, board);
    }

    @Override
    public void playCard(Player player) {
        super.playCard(player);
        System.out.println(this.getName() + " going into " + player.getName() + "' blue field");
        player.addBlueCard(this);
    }
}