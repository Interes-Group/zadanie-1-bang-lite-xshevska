package sk.stuba.fei.uim.oop.bang;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.blue.Dynamite;
import sk.stuba.fei.uim.oop.cards.blue.Prison;
import sk.stuba.fei.uim.oop.cards.brown.Missed;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;

public class BangGame {
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RED = "\u001B[31m";
    private final Player[] players;
    private int currentPlayer;
    private Board board;

    public BangGame() {
        System.out.println("--- Welcome to the game of BANG! ---" + ANSI_PURPLE);
        System.out.println(" Welcome to the world of madness and endless possibilities!" + ANSI_CYAN);
        System.out.println(" Today, you will dive into the exciting console game Bang, where every move can be " + "crucial in your fight for survival." + ANSI_BLUE);
        int numberPlayers = 0;
        while (numberPlayers < 2 || numberPlayers > 4) {
            numberPlayers = ZKlavesnice.readInt(ANSI_BLUE + "*** Enter number of players (2-4): ***");
            if (numberPlayers < 2 || numberPlayers > 4) {
                System.out.println(ANSI_RED + " !!! You enter wrong number of players.Please Try Again! !!!");
            }
        }
        this.players = new Player[numberPlayers];
        for (int i = 0; i < numberPlayers; i++) {
            this.players[i] = new Player(ZKlavesnice.readString(ANSI_BLUE + "*** Enter name for " + "PLAYER " + (i + 1) + " : ***"));
        }
        this.board = new Board(this.players);
        this.startGame();

    }

    private void startGame() {
        System.out.println("--- GAME STARTED ---");

        while (this.getNumberOfActivePlayers() > 1) {
            System.out.println("GAME CARDS: " + this.board.sizeOfGameCards());
            Player activePlayer = this.players[this.currentPlayer];

            if (!activePlayer.isActive()) {
                ArrayList<Card> removedCards = activePlayer.removeCardsFromHand();
                for (Card card : removedCards) {
                    this.board.addGameCard(card);
                }
                this.incrementCounter();
                continue;
            }


            // проверь в тюрме ли он текущий игрок
            // если да, то удали карту Тюрма от него и пускай ходит дальше
            if (this.board.checkPrisoner(activePlayer)) {
                if (activePlayer.getBlueCards().get(0).checkEffect(activePlayer)) {
                    // удали карту тюрма от игрока
                    activePlayer.removeBlueCard(this.board.findPrison(activePlayer));
                    // add to gameCard
                    this.board.addGameCard(new Prison(this.board));
                    this.incrementCounter();
                    continue;
                }
                activePlayer.removeBlueCard(this.board.findPrison(activePlayer));
                this.board.addGameCard(new Prison(this.board));
            }


            System.out.println("Player cards on hand: " + activePlayer.printCardsOnHand());
            this.board.pullTwoCards(activePlayer);
            System.out.println("Player pulled two cards: " + activePlayer.printCardsOnHand());

            System.out.println("--- " + activePlayer.getName() + "'s TURN ---" + ANSI_PURPLE);
            System.out.println(activePlayer);
            this.board.printPlayers();


            while(true){
                int numberCard = pickCard(activePlayer);
                if(numberCard == -1) {
                    System.out.println("Player want to SKIP his move.");
                    break;
                } else {
                    this.makeTurn(activePlayer, numberCard);
                    System.out.println("--- " + activePlayer.getName() + "'s TURN ---" + ANSI_PURPLE);
                    System.out.println(activePlayer);
                    this.board.printPlayers();
                }
            }
            this.incrementCounter();


            // пройтись по игрокy и удалить рандомно столько карт, чтобы осталось у нeuj какрт как и жизней
            // если 4 жизни у него, то оставь у игрока 4 максимум карт.
            System.out.println("This is NewCircle of the GAME");
            System.out.println("--- must check for the harts and cards of players --- ");
            this.board.controllHartsAndCards(activePlayer);
            System.out.println("Game cards total are: " + this.board.getGameCards().size());
        }
        System.out.println("--- GAME FINISHED ---");


    }

    private void makeTurn(Player activePlayer, int numberCard) {
        Card selectedCard = activePlayer.getCards().get(numberCard);

        if (selectedCard instanceof Missed) {
            System.out.println("This card cannot be played!");
        } else if(selectedCard instanceof  Dynamite || selectedCard instanceof Prison){
            // проверь на кого ты можешь поставить и выпиши их
            // для дальнейшего выбора.
            ArrayList<Player> playable = this.checkPlayersToAction(activePlayer, selectedCard);
            System.out.println("list of players: ");
            playable.stream().forEach((e)->{
                System.out.print(e.getName() + " ");
            });
            if (playable.size() != 0) {
                activePlayer.removeCard(numberCard);
                this.playCard(playable, selectedCard);
            } else {
                System.out.println("Right now you don't have an option of a player to go to.");
                System.out.println("Try another card.");
            }
        } else {
            // вызываю функцию для выбора игрока на которого будем стрелять
            activePlayer.removeCard(numberCard);
            selectedCard.playCard(activePlayer);
        }
    }

    private void playCard(ArrayList<Player> playable, Card selectCard) {
        System.out.println("--- The players you can look up to are:");
        // проверь игроков, если их нету, то пропусти ход и выбери другую карту
        // в жругом случаи просто делай дальше
        int i = 1;
        for (Player p : playable) {
            System.out.println(" - " + i + " " + p.getName());
            i++;
        }
        int numberOfTargetPlayer;
        while (true) {
            numberOfTargetPlayer = ZKlavesnice.readInt("*** Chose player ***") - 1;
            if (numberOfTargetPlayer < 0 || numberOfTargetPlayer > playable.size() - 1) {
                System.out.println(" !!! You enter wrong number of card. Try Again! !!! ");
            }else {
                break;
            }
        }
        // игрок выбирает номер игрока
        selectCard.playCard(playable.get(numberOfTargetPlayer));

    }

    private int pickCard(Player activePlayer) {
        int numberCard;
        while (true) {
            System.out.println("Enter number \'0\' to skip turn.");
            numberCard = ZKlavesnice.readInt("*** Enter number of card you want to PLAY: ***") - 1;
            if (numberCard < -1 || numberCard > activePlayer.getCards().size() - 1) {
                System.out.println(" !!! You enter wrong number of card. Try Again! !!! ");
            } else {
                break;
            }
        }
        return numberCard;
    }

    private void incrementCounter() {
        this.currentPlayer++;
        this.currentPlayer %= this.players.length;
    }

    private int getNumberOfActivePlayers() {
        int count = 0;
        for (Player player : this.players) {
            if (player.isActive()) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<Player> checkPlayersToAction(Player activePlayer, Card selectedCard) {
        ArrayList<Player> playablePlayers = new ArrayList<>();
        for (Player p : this.board.getPlayers()) {
            if (p.isActive() && !p.equals(activePlayer)) {
                if(!p.checkDuplicate(selectedCard)) {
                    playablePlayers.add(p);
                }
            }
        }
        return playablePlayers;
    }
}
