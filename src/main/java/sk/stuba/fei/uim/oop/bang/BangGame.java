package sk.stuba.fei.uim.oop.bang;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.brown.*;
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
        System.out.println(" Today, you will dive into the exciting console game Bang, where every move can be " +
                "crucial in your fight for survival." + ANSI_BLUE);
        int numberPlayers = 0;
        while (numberPlayers < 2 || numberPlayers > 4) {
            numberPlayers = ZKlavesnice.readInt(ANSI_BLUE + "*** Enter number of players (2-4): ***");
            if (numberPlayers < 2 || numberPlayers > 4) {
                System.out.println(ANSI_RED + " !!! You enter wrong number of players.Please Try Again! !!!");
            }
        }
        this.players = new Player[numberPlayers];
        for (int i = 0; i < numberPlayers; i++) {
            this.players[i] = new Player(ZKlavesnice.readString(ANSI_BLUE + "*** Enter name for " +
                    "PLAYER " + (i + 1) + " : ***"));
        }
        this.board = new Board(this.players);
        this.startGame();

    }

    private void startGame() {
        System.out.println("--- GAME STARTED ---");

        while (this.getNumberOfActivePlayers() > 1) {
            System.out.println("GAME CARDS: " + this.board.sizeOfGameCards());  // выписаи для себя
            Player activePlayer = this.players[this.currentPlayer];

            //проверка, активен ли текущий игрок. Если он неактивен, это означает, что он был убит или покинул игру,
            // и все его карты из руки должны быть перемещены на игровое поле.
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


            System.out.println("Player cards on hand: " + activePlayer.printCardsOnHand());
            this.board.pullTwoCards(activePlayer);
            System.out.println("Player pulled two cards: " + activePlayer.printCardsOnHand());

            System.out.println("--- " + activePlayer.getName() + "'s TURN ---" + ANSI_PURPLE);
            System.out.println(activePlayer);
            this.board.printPlayers();

            int playerMove;
            while (true) {
                this.playerTurnMenu();
                playerMove = ZKlavesnice.readInt("*** Choose what you want do from 1-2: ***");
                if (playerMove < 1 || playerMove > 2) {
                    System.out.println(" !!! You enter wrong number of card. Try Again! !!! ");
                } else if(playerMove == 1) {
                    this.playCard(activePlayer);

                    //print game.
                    System.out.println("--- " + activePlayer.getName() + "'s TURN ---" + ANSI_PURPLE);
                    System.out.println(activePlayer);
                    this.board.printPlayers();
                } else {
                    System.out.println("Player want to SKIP his move.");
                    break;
                }
            }
            this.incrementCounter();


            // пройтись по игрокy и удалить рандомно столько карт, чтобы осталось у нeuj какрт как и жизней
            // если 4 жизни у него, то оставь у игрока 4 максимум карт.
            System.out.println("This is NewCircle of the GAME");
            System.out.println("--- must check for the harts and cards of players --- ");
            this.board.controllHartsAndCards(activePlayer);



            // 2.
            // +++ Игрок играет картами которые у него есть. Ходит сколько хочет
            //  -- Перед собою игрок не может иметь одинаковые карты того же вида (гулубую)
            // 3.
            // Убираем карты. Если жизней две, то карт максимум может быть две!
            //  -- Убираются карты рандомно.
            //  -- делается это только на начале кола.

        }
        System.out.println("--- GAME FINISHED ---");
        // Выпиши победителя как-то, чтобы было красиво

    }
    private void playerTurnMenu() {
        System.out.println("1. Play card");
        System.out.println("2. Skip turn");
    }
    private void playCard(Player activePlayer) {
        int numberCard = pickCard(activePlayer);
        Card selectedCard = activePlayer.getCards().get(numberCard);
        int playNumber;

        if (selectedCard instanceof Bang) {
            Bang bangCard = (Bang) selectedCard;
            playNumber = whoKill(activePlayer);
            // если человек выбрал себя || выбранный человек уже мертв, то пускай человек выберет еще раз номер игрока
            // на которого будет произведена атака
            while (playNumber == getGameIndexOfCurrentPlayer(activePlayer) || !this.players[playNumber].isActive()) {
                System.out.println("You can't shoot to that player.");
                System.out.println("Chose another player PLEASE!");
                playNumber = whoKill(activePlayer);
            }
            activePlayer.removeCard(numberCard);
            bangCard.playCard(players[playNumber]);
        }
        if (selectedCard instanceof Missed) {
            System.out.println("This card cannot be played.");
        }
        if (selectedCard instanceof Beer) {
            System.out.println("You chose the Beer - ❤ + 1.");
            selectedCard.playCard(activePlayer);
            activePlayer.removeCard(numberCard);
        }
        if (selectedCard instanceof Stagecoach) {
            System.out.println("You chose " + selectedCard.getName() + "! Lucky plus 2x🎴");
            selectedCard.playCard(activePlayer);
            activePlayer.removeCard(numberCard);
        }
        if (selectedCard instanceof Indians) {
            System.out.println("You chose Indians! Prepare for GREAT BATTLE 😈 + 🔫");
            selectedCard.playCard(activePlayer);
            activePlayer.removeCard(numberCard);
        }
    }
    private int whoKill(Player activePlayer) {
        int victim;
        while (true) {
            victim = ZKlavesnice.readInt("*** Who do you want to shoot? : ***") - 1;
            if (victim < 0 || victim > this.players.length - 1) {
                System.out.println(" !!! You enter wrong number of card. Try Again! !!! ");
            } else {
                break;
            }
        }
        return victim;
    }
    private int pickCard(Player activePlayer) {
        int numberCard;
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
        Arrays.stream(players).forEach((e) -> System.out.println(e + "\n"));
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
    private int getGameIndexOfCurrentPlayer(Player player) {
        for (int i = 0; i < this.players.length; i++) {
            if (players[i].equals(player)) {
                return i;
            }
        }
        return 0;
    }
}
