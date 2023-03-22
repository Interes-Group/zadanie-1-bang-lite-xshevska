package sk.stuba.fei.uim.oop.cards.blue;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

public class Prison extends BlueCard {
    private static final String CARD_NAME = "Prison";

    public Prison(String name, Board board) {
        super(name, board);
    }

    @Override
    public void playCard(Player player) {
        super.playCard(player);
        // вызываю функцию для выбора игрока на которого будем стрелять
        int playNumber = this.board.getTargetPlayNumber(player);
        Player targetPlayer = this.board.getPlayers()[playNumber];

        targetPlayer.addBlueCard(this);
    }
}
