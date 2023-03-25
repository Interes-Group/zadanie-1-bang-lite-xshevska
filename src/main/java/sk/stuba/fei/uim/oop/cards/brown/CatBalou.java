package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

public class CatBalou extends Card {
    private static final String CARD_NAME = "Cat Balou";

    public CatBalou(Board board) {
        super(CARD_NAME, board);
    }

    private boolean choseTypeOfCardToRemoveFromPlayer(Player player) {
        int typeCard;
        while (true) {
            System.out.println("1. Cards from table");
            System.out.println("2. Cards from hand");
            typeCard = ZKlavesnice.readInt("*** Which type of card you want to chose to remove " + "from Player : ***");
            if (typeCard < 1 || typeCard > 2) {
                System.out.println(" !!! You enter wrong number. Try Again! !!! ");
            } else if ((typeCard == 1 && player.getBlueCards().isEmpty()) || (typeCard == 2 && player.getCards().isEmpty())) { //check if player has that cards.
                System.out.println(" !!! Player don't have any cards to remove from table !!! ");
            } else {
                break;
            }
        }
        return typeCard == 1;
    }

    private void deleteRandomCardFromPlayer(Player player, boolean card) {
        int max, randCard;
        if (card) {
            max = player.getBlueCards().size() - 1;
            randCard = (int) (Math.random() * (max));
            this.board.getGameCards().add(player.getBlueCards().get(randCard));
            System.out.println("Card " + player.getBlueCards().get(randCard) + " will be removed from " +
                    player.getName());
            player.removeBlueCard(randCard);
        } else {
            max = player.getCards().size() - 1;
            randCard = (int) (Math.random() * (max));
            this.board.getGameCards().add(player.getCards().get(randCard));
            System.out.println("Card " + player.getCards().get(randCard) + " will be removed from " +
                    player.getName());
            player.removeCard(randCard);
        }
    }

    @Override
    public void playCard(Player player) {
        super.playCard(player);
        // вызываю функцию для выбора игрока на которого будем стрелять
        int playNumber = this.getPlayerNumber(player);

        if(this.board.getPlayers()[playNumber].getCards().size() == 0 &&
                this.board.getPlayers()[playNumber].getBlueCards().size() == 0){
            System.out.println("Sorry, " + player.getName() + " don't have any card right now.");
            System.out.println("This card cannot be played");
            return;
        }

        Player targetPlayer = this.board.getPlayers()[playNumber];
        // check which type of cards you want to remove from player!
        boolean cardOnTable = this.choseTypeOfCardToRemoveFromPlayer(targetPlayer);
        //remove that card!
        this.deleteRandomCardFromPlayer(targetPlayer, cardOnTable);
    }
}
