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
    public static final String ANSI_GREEN = "\u001B[32m";
    private ArrayList<Card> gameCards;
    private Player[] players;

    public Board(Player[] players) {
        this.players = players;
        ArrayList<Card> cards = creatingCards();
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
        IntStream.range(0, 2).forEach(i -> cards.add(new Barrel(this)));
        cards.add(new Dynamite(this));
        IntStream.range(0, 3).forEach(i -> cards.add(new Prison(this)));
        IntStream.range(0, 30).forEach(i -> cards.add(new Bang(this)));
        IntStream.range(0, 15).forEach(i -> cards.add(new Missed(this)));
        IntStream.range(0, 8).forEach(i -> cards.add(new Beer(this)));
        IntStream.range(0, 6).forEach(i -> cards.add(new CatBalou(this)));
        IntStream.range(0, 4).forEach(i -> cards.add(new Stagecoach(this)));
        IntStream.range(0, 2).forEach(i -> cards.add(new Indians(this)));

        Collections.shuffle(cards);
        return cards;
    }

    public void printPlayers() {
        System.out.println("\n--- Game Board --- DEAD PLAYERS: 💀x" + this.countDeadPlayers());
        for (int i = 0; i < players.length; i++) {
            if (players[i].isActive()) {
                System.out.println("Id: " + (i + 1) + " - " + players[i].getName() + " * " + "❤x" + players[i].getLives() + " * " + "[" + players[i].getBlueCards().stream().map((e) -> {
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

    public int getGameIndexOfCurrentPlayer(Player player) {
        for (int i = 0; i < this.players.length; i++) {
            if (players[i].equals(player)) {
                return i;
            }
        }
        return 0;
    }

    public boolean checkPrisoner(Player player) {
        Card prison = player.getBlueCards().stream().filter(card -> card instanceof Prison).findAny().orElse(null);
        System.out.println("checkPrisoner " + player.getName() +": " + prison + " | " + (prison != null));
        return prison != null;
    }

    public int findPrison(Player player) {
        int indexCart = -1;
        for (int i = 0; i < player.getBlueCards().size(); i++) {
            if (player.getBlueCards().get(i) instanceof Prison) {
                indexCart = i;
                break;
            }
        }
        return indexCart;
    }

    public ArrayList<Card> getGameCards() {
        return gameCards;
    }
}
