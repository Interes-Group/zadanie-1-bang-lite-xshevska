package sk.stuba.fei.uim.oop.bang;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.brown.Bang;
import sk.stuba.fei.uim.oop.cards.brown.Missed;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;
import java.util.Arrays;

public class BangGame {
    private final Player[] players;
    private int currentPlayer;
    private Board board;
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RED = "\u001B[31m";


    public BangGame() {
        System.out.println("--- Welcome to the game of BANG! ---" + ANSI_PURPLE);
        System.out.println(" Welcome to the world of madness and endless possibilities!" + ANSI_CYAN);
        System.out.println(" Today, you will dive into the exciting console game Bang, where every move can be crucial in your fight for survival." + ANSI_BLUE);
        int numberPlayers = 0;
        while (numberPlayers < 2 || numberPlayers > 4) {
            numberPlayers = ZKlavesnice.readInt(ANSI_BLUE + "*** Enter number of players (2-4): ***");
            if (numberPlayers < 2 || numberPlayers > 4) {
                System.out.println(ANSI_RED + " !!! You enter wrong number of players.Please Try Again! !!!");
            }
        }
        this.players = new Player[numberPlayers];
        for (int i = 0; i < numberPlayers; i++) {
            this.players[i] = new Player(ZKlavesnice.readString(ANSI_BLUE + "*** Enter name for PLAYER " + (i + 1) + " : ***"));
        }


        this.board = new Board(this.players);
        this.startGame();

    }

    private void startGame() {
        System.out.println("--- GAME STARTED ---");
        while (this.getNumberOfActivePlayers() > 1) {
            System.out.println("GAME CARDS: " + this.board.sizeOfGameCards());
            Player activePlayer = this.players[this.currentPlayer];
            //проверка, активен ли текущий игрок. Если он неактивен, это означает, что он был убит или покинул игру, и все его карты из руки должны быть перемещены на игровое поле.
            if (!activePlayer.isActive()) {
                // удаляем карты от игрока (с руки и с доски)
                // удаленные карты игрока мы добавляем на доску в колоду карт с которой тянут карты
                ArrayList<Card> removedCards = activePlayer.removeCardsFromHand();
                for (Card card : removedCards) {
                    this.board.addGameCard(card);
                }
                this.incrementCounter();
                continue;
            }
            System.out.println("--- PLAYER " + activePlayer.getName() + " STARTS TURN ---");
            System.out.println(activePlayer);
            this.board.printPlayers();


            this.playCard(activePlayer);
            this.incrementCounter();
        }
        System.out.println("--- GAME FINISHED ---");

    }

    private void playCard(Player activePlayer) {
        int numberCard = pickCard(activePlayer);
        int playNumber = whoKill(activePlayer);

        Card selectedCard = activePlayer.getCards().get(numberCard);
        if (selectedCard instanceof Bang) {
            Bang bangCard = (Bang) selectedCard;
            bangCard.playCard(players[playNumber]);
            activePlayer.removeCard(numberCard);
        }
        if (selectedCard instanceof Missed) {
            System.out.println("This card cannot be played.");
        } else {
            selectedCard.playCard(activePlayer);
            System.out.println("Not Bang");
        }

//        activePlayer.getCards().get(numberCard).playCard(activePlayer);

    }

    private int whoKill(Player activePlayer) {
        int victim;
        while (true) {
            victim = ZKlavesnice.readInt("*** Who do you want to shoot? : ***") - 1;
            if (victim < 0 || victim > activePlayer.getCards().size() - 1) {
                System.out.println(" !!! You enter wrong number of card. Try Again! !!! ");
            } else {
                break;
            }
        }
        System.out.println("This is player victim : " + victim);
        return victim;
    }


    private int pickCard(Player activePlayer) {
        int numberCard = 0;
        while (true) {
            numberCard = ZKlavesnice.readInt("*** Enter number of card you want to PLAY: ***") - 1;
            if (numberCard < 0 || numberCard > activePlayer.getCards().size() - 1) {
                System.out.println(" !!! You enter wrong number of card. Try Again! !!! ");
            } else {
                break;
            }
        }
        System.out.println("This is card : " + numberCard);
        return numberCard;
    }

    private void incrementCounter() {
        this.currentPlayer++;
        this.currentPlayer %= this.players.length;
    }


    private void printAllPlayers() {
        Arrays.stream(players).forEach((e) -> {
            System.out.println(e + "\n");
        });
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

}
