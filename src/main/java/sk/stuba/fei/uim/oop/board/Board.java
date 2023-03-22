package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.blue.Barrel;
import sk.stuba.fei.uim.oop.cards.blue.Dynamite;
import sk.stuba.fei.uim.oop.cards.blue.Prison;
import sk.stuba.fei.uim.oop.cards.brown.*;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private ArrayList<Card> gameCards;
    private Player[] players;
    public static final String ANSI_GREEN = "\u001B[32m";

    public Board(Player[] players) {
        this.players = players;
        //создай карты
        ArrayList<Card> cards = creatingCards();
//        System.out.println("This are cards: ");
//        cards.stream().forEach(System.out::println);

        //пройдись по игрокам и дай им карты
        addingCardsForPlayers(players, cards);
        this.gameCards = cards;

    }

    private void addingCardsForPlayers(Player[] players, ArrayList<Card> cards) {
        for (Player p : players) {
            ArrayList<Card> playerCards = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Card card = cards.remove(0);
                playerCards.add(card);
            }
            p.setCards(playerCards);
        }
    }

    /*
    вернет новый АрейЛист картов, которые уже наполненые картами и перемешаные.
     */
    private ArrayList<Card> creatingCards() {
        ArrayList<Card> cards = new ArrayList<>();
        IntStream.range(0, 2).forEach(i -> cards.add(new Barrel("Barrel", this)));
        cards.add(new Dynamite("Dynamite", this));
        IntStream.range(0, 3).forEach(i -> cards.add(new Prison("Prison", this)));
        IntStream.range(0, 30).forEach(i -> cards.add(new Bang("Bang", this)));
        IntStream.range(0, 15).forEach(i -> cards.add(new Missed("Missed", this)));
        IntStream.range(0, 8).forEach(i -> cards.add(new Beer("Beer", this)));
        IntStream.range(0, 6).forEach(i -> cards.add(new CatBalou("CatBalou", this)));
        IntStream.range(0, 4).forEach(i -> cards.add(new Stagecoach("Stagecoach", this)));
        IntStream.range(0, 2).forEach(i -> cards.add(new Indians("Indians", this)));

        Collections.shuffle(cards);
        return cards;
    }

    public void printPlayers() {
        System.out.println("\n--- Game Board --- DEAD PLAYERS: 💀x" + this.countDeadPlayers());
        for (int i = 0; i < players.length; i++) {
            if (players[i].isActive()) {
                System.out.println("Id: " + (i + 1) + " - " + players[i].getName() + " * " +
                        "❤x" + players[i].getLives() + " * " +
                        "[" + players[i].getBlueCards().stream().map((e) -> {
                    StringBuffer st = new StringBuffer();
                    st.append(e.getName() + ", ");
                    return st;
                }).collect(Collectors.joining()) + "]" +
                        //тут будут все карты игрока
                        "\n ALL CARDS: [" + players[i].getCards().stream().map((e) -> {
                    StringBuffer st = new StringBuffer();
                    st.append(e.getName() + " ");
                    return st;
                }).collect(Collectors.joining()) + "]");
            }
        }
        System.out.println("------------------\n");

    }

    public int countDeadPlayers() {
        int count = 0;
        for (Player p : this.players) {
            if (!p.isActive()) {
                count++;
            }
        }
        return count;
    }

    public void addGameCard(Card card) {
        this.gameCards.add(card);
    }

    public int sizeOfGameCards() {
        return this.gameCards.size();
    }

    public void pullTwoCards(Player player) {
        for (int i = 0; i < 2; i++) {
            player.addCard(this.gameCards.remove(0));
        }
        System.out.println(ANSI_GREEN + player.getName() + " pull two cards.");
//        return  player.getName() + " pull two cards.";
    }

    public Player[] getPlayers() {
        return players;
    }

    public void controllHartsAndCards(Player player) {
        int cardForDeleting = 0;

        if (player.getLives() < player.getCards().size()) {
            cardForDeleting = player.getCards().size() - player.getLives();
            System.out.println(player.getName() + " will delete " + cardForDeleting + " cards!");
            this.deletingCardsFromPerson(player, cardForDeleting);
            System.out.println();
        }

    }

    private void deletingCardsFromPerson(Player player, int cardsNumber) {
        Random rand = null;
        int card;
        // max card size = a --- min card size = 0 ---
        for (int i = 0; i < cardsNumber; i++) {
            // rand.nextInt((max - min) + 1) + min
            card = (int) (Math.random() * (player.getCards().size() - 0));
            System.out.println(player.getName() + " (" + i + ") - rand card is: " + player.getCards().get(card));
            player.removeCard(card);
        }
    }

    public int setTarget(Player activePlayer) {
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

    public int getGameIndexOfCurrentPlayer(Player player) {
        for (int i = 0; i < this.players.length; i++) {
            if (players[i].equals(player)) {
                return i;
            }
        }
        return 0;
    }

    public int getTargetPlayNumber(Player player) {
        int playNumber = this.setTarget(player);

        // если человек выбрал себя || выбранный человек уже мертв, то пускай человек выберет еще раз номер игрока
        // на которого будет произведена атака
        while (playNumber == this.getGameIndexOfCurrentPlayer(player) || !this.getPlayers()[playNumber].isActive()) {
            System.out.println("You can't play to that player.");
            System.out.println("Chose another player PLEASE!");
            playNumber = this.setTarget(player);
        }
        return playNumber;
    }

    public boolean choseTypeOfCardToRemoveFromPlayer(Player player) {
        int typeCard;
        while (true) {
            System.out.println("1. Cards on table");
            System.out.println("2. Cards on hand");
            typeCard = ZKlavesnice.readInt("*** Which type of card you want to chose to remove " +
                    "from Player : ***");
            if (typeCard < 1 || typeCard > 2) {
                System.out.println(" !!! You enter wrong number. Try Again! !!! ");
            } else if ((typeCard == 1 && player.getBlueCards().isEmpty()) ||
                    (typeCard == 2 && player.getCards().isEmpty())) { //check if player has that cards.
                System.out.println(" !!! Player don't have any cards to remove from table !!! ");
            } else {
                break;
            }
        }
        return typeCard == 1;
    }

    public void deleteRandomCardOfPlayer(Player player, boolean card) {
        int max, randCard;
        if (card) {
            max = player.getBlueCards().size() - 1;
            randCard = (int) (Math.random() * (max));
            this.gameCards.add(player.getBlueCards().get(randCard));
            player.removeBlueCard(randCard);
        } else {
            max = player.getCards().size() - 1;
            randCard = (int) (Math.random() * (max));
            this.gameCards.add(player.getCards().get(randCard));
            player.removeCard(randCard);
        }
    }

    public boolean checkPrisoner(Player player) {
        Card prison = player.getCards().stream()
                .filter(card -> card instanceof Prison)
                .findAny()
                .orElse(null);
        // сделай проверку на побег
        // есди да, то побеги Тру
        // если нет, то Фалсе
        return prison != null;
    }

    public boolean chanceToEscape() {
        int rand = (int)(Math.random() * 3);
        System.out.println("random namber to escape is - " + rand);
        return rand == 3;
    }

    public int findPrison(Player player) {
        for (int i = 0; i < player.getBlueCards().size(); i++) {
            if(player.getBlueCards().get(i) instanceof Prison) {
                return i;
            }
        }
        return 0;
    }
}
