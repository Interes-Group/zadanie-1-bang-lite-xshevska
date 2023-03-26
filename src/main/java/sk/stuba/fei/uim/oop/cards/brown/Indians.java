package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.player.Player;

public class Indians extends Card {
    private static final String CARD_NAME = "Indians";

    public Indians(Board board) {
        super(CARD_NAME, board);
    }

    @Override
    public void playCard(Player player) {
        super.playCard(player);
        System.out.println("You chose Indians! Prepare for GREAT BATTLE 😈 + 🔫");
        for (Player p : this.board.getPlayers()) {
            boolean bang = false;
            if (!p.equals(player) && p.isActive()) {
                for (Card c : p.getCards()) {
                    if (c instanceof Bang) {
                        p.removeCard(c);
                        this.board.addDiscardingDeckCard(c);
                        System.out.println(p.getName() + " had a card Bang! His life is saved.. but card is lost -1x🎴");
                        bang = true;
                        break;
                    }
                }
                if (!bang) {
                    p.removeLife();
                    if (p.getLives() == 0) {
                        System.out.println(p.getName() + " has not any Bang card.. He lose his LIFE CELL 😈");
                        p.printDead();
                    } else {
                        System.out.println(p.getName() + " has not any Bang card.. He lose his LIFE CELL 😈");
                    }
                }
            }
        }
        this.board.addDiscardingDeckCard(this);
    }
}
