package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.blue.Barrel;
import sk.stuba.fei.uim.oop.player.Player;

public class Bang extends Card {
    private static final String CARD_NAME = "Bang";

    public Bang(String name, Board board) {
        super(CARD_NAME, board);
    }


    @Override
    public void playCard(Player player) {
        super.playCard(player);

        // вызываю функцию для выбора игрока на которого будем стрелять
        int playNumber = this.board.getTargetPlayNumber(player);
        Player targetPlayer = this.board.getPlayers()[playNumber];

        // check player for shoot
//        this.board.checkTargetPlayer(player, );
        // can we shoot him?
        // if he has barrel, check 25%
        // if he has not barrel, chceck for missed card,
        // and then make action


        // сделать функцию, которая будет проверять на наличие чиних карт, которые
        // могут защитить игрока [ Barrel,  ]
        // и будет проверять на наличие карты Missed


        Card missed = targetPlayer.getCards().stream()
                .filter(card -> card instanceof Missed || card instanceof Barrel)
                .findAny()
                .orElse(null);
        if (missed == null) {
            targetPlayer.removeLife();
            System.out.println("Player " + targetPlayer.getName() + " lost his/her live.. -💔x1" + "  ❤x" + targetPlayer.getLives());
            if (targetPlayer.getLives() == 0) {
                targetPlayer.printDead();
            }
            this.board.addGameCard(this);
        }else if(missed instanceof Barrel) {
            System.out.println("This player has Barrel on himself.");
            System.out.println("NOT IMPLEMENTED YET!");
        }else {
            missed.playCard(targetPlayer);
            this.board.addGameCard(this);
        }
    }
}
