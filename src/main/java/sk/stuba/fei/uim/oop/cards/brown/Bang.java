package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.blue.Barrel;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.stream.Stream;

public class Bang extends Card {
    private static final String CARD_NAME = "Bang";

    public Bang(Board board) {
        super(CARD_NAME, board);
    }

    @Override
    public void playCard(Player player) {
        super.playCard(player);
        int playNumber = this.getPlayerNumber(player);
        Player targetPlayer = this.board.getPlayers()[playNumber];


        Card barrel = targetPlayer.getCards().stream()
                .filter(card -> card instanceof Barrel)
                .findAny()
                .orElse(null);

        Card missedCard = targetPlayer.getCards().stream().filter(card -> card instanceof Missed).findFirst().orElse(null);

        if (barrel != null) {
            if (!((Barrel) barrel).checkEffect(player)) {
                // если checkEffect(player) вернула false, то продолжаем выполнение в блоке else if
                System.out.println("Barrel effect failed!");

                if (missedCard != null) {
                    missedCard.playCard(targetPlayer);
                    this.board.addGameCard(this);
                    System.out.println("Missed после Barrel!");
                } else {
                    targetPlayer.removeLife();
                    System.out.println("Player " + targetPlayer.getName() + "was not protected by a Barrel card and the player did not have a Missed card!");
                    System.out.println("Player " + targetPlayer.getName() + " lost his/her live.. -💔x1" + "  ❤x" + targetPlayer.getLives());
                }
            } else {
                System.out.println("The Barrel card kept the player alive!");
            }
        } else if (missedCard != null) {
            missedCard.playCard(targetPlayer);
            this.board.addGameCard(this);
        } else {
            targetPlayer.removeLife();
            System.out.println("Player " + targetPlayer.getName() + " lost his/her live.. -💔x1" + "  ❤x" + targetPlayer.getLives());
            if (targetPlayer.getLives() == 0) {
                targetPlayer.printDead();
            }
            this.board.addGameCard(this);
        }
    }
}
