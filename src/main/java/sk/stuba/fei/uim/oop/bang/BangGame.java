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
    public static final String ANSI_YELLOW = "\u001B[33m";


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
        System.out.println(ANSI_YELLOW + this.players[this.currentPlayer]);
        // +++ потяни две карты з баличка игрового
        this.board.pullTwoCards(this.players[this.currentPlayer]);
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
            System.out.println("--- PLAYER " + activePlayer.getName() + " STARTS TURN ---" + ANSI_PURPLE);

//            System.out.println(ANSI_YELLOW + activePlayer);
//            // +++ потяни две карты з баличка игрового
//            this.board.pullTwoCards(activePlayer);

            System.out.println(activePlayer);

            this.board.printPlayers();

            this.makeTurn(activePlayer);
//            this.playCard(activePlayer);
        }
        System.out.println("--- GAME FINISHED ---");

    }

    private void makeTurn(Player player) {

        // 1.
        // проверь голубые карты игрока, если какие-то есть, сделай их ефект
        // -


        // 2.
        // +++ Игрок играет картами которые у него есть. Ходит сколько хочет
        int playerChoice = playerChoice();

        switch (playerChoice) {
            case 1:
                this.playCard(player);
                break;
            case 2:
                System.out.println(player.getName() + " passed the turn to another player.");
                this.incrementCounter();
                break;
        }

//        this.incrementCounter();

        //  -- Перед собою игрок не может иметь одинаковые карты того же вида (гулубую)

        // 3.
        // Убираем карты. Если жизней две, то карт максимум может быть две!
        //  -- Убираются карты рандомно.
        //  -- делается это только на начале кола.
    }

    private int playerChoice() {
        int playerMove = 0;
        while (true) {
            this.playerTurnMenu();
            playerMove = ZKlavesnice.readInt("*** Choose what you want do from 1-2: ***");
            if (playerMove < 1 || playerMove > 2) {
                System.out.println(" !!! You enter wrong number of card. Try Again! !!! ");
            } else {
                break;
            }
        }
        return playerMove;
    }

    private void playerTurnMenu() {
        System.out.println("1. Play card");
        System.out.println("2. Skip turn");
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
        }
//        else {
////            selectedCard.playCard(activePlayer);
//            System.out.println("Else");
//        }

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
//        System.out.println("This is card : " + numberCard);
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
