package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.blue.Barrel;
import sk.stuba.fei.uim.oop.player.Player;

public class Bang extends Card {
    private static final String CARD_NAME = "Bang";

    public Bang(Board board) {
        super(CARD_NAME, board);
    }

    @Override
    public void playCard(Player player) {
        boolean check = false;
        super.playCard(player);
        int playNumber = this.getPlayerNumber(player);
        Player targetPlayer = this.board.getPlayers()[playNumber];


        Card barrel = targetPlayer.getBlueCards().stream()
                .filter(card -> card instanceof Barrel).findAny().orElse(null);

        Card missedCard = targetPlayer.getCards().stream()
                .filter(card -> card instanceof Missed).findFirst().orElse(null);

        if (barrel != null) {
            check = ((Barrel) barrel).checkEffect(player);
            if (check) {
                System.out.println("The player was protected from the Bang card by the Barrel card! 💗🎴");
            } else {
                System.out.println("Unfortunately, the player Barrel did not defend! 😿");
            }
        }

        if ((!check) && (missedCard != null)) {
            missedCard.playCard(targetPlayer);
            this.board.addDiscardingDeckCard(this);
        }
        if (!check) {
            targetPlayer.removeLife();
            System.out.println("Player " + targetPlayer.getName() + " lost his/her live.. -💔x1" + "  ❤x" + targetPlayer.getLives());
            if (targetPlayer.getLives() <= 0) {
                targetPlayer.printDead();
            }
            this.board.addDiscardingDeckCard(this);
        }
    }
}
