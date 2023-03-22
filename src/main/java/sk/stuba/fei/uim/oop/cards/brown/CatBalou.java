package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.Calendar;

public class CatBalou extends Card {
    private static final String CARD_NAME = "Cat Balou";

    public CatBalou(String name, Board board) {
        super(name, board);
    }

    @Override
    public void playCard(Player player) {
        super.playCard(player);
        // вызываю функцию для выбора игрока на которого будем стрелять
        int playNumber = this.board.getTargetPlayNumber(player);

        if(this.board.getPlayers()[playNumber].getCards().size() == 0 &&
                this.board.getPlayers()[playNumber].getBlueCards().size() == 0){
            System.out.println("Sorry, " + player.getName() + " don't have any card right now.");
            System.out.println("This card cannot be played");
            return;
        }

        Player targetPlayer = this.board.getPlayers()[playNumber];
        // check which type of cards you want to remove from player!
        boolean cardOnTable = this.board.choseTypeOfCardToRemoveFromPlayer(targetPlayer);
        //remove that card!
        this.board.deleteRandomCardOfPlayer(targetPlayer, cardOnTable);
    }
}
