package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.blue.Barrel;
import sk.stuba.fei.uim.oop.cards.blue.Dynamite;
import sk.stuba.fei.uim.oop.cards.blue.Prison;
import sk.stuba.fei.uim.oop.cards.brown.*;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Board {
    private ArrayList<Card> gameCards;
//    private ArrayList<Tile> actionCards;

    public Board(Player[] players) {

        //создай карты
        ArrayList<Card> cards = creatingCards();
        System.out.println("This are cards: ");
        cards.stream().forEach(System.out::println);

        //пройдись по игрокам и дай им карты
        addingCardsForPlayers(players, cards);

        this.gameCards = cards;
    }

    private void addingCardsForPlayers(Player[] players, ArrayList<Card> cards) {
        for (Player p : players) {
            ArrayList<Card> playerCards = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
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
        for (int i = 0; i < 2; i++) {
            cards.add(new Barrel("Barrel", this));
        }

        cards.add(new Dynamite("Dynamite", this));

        for (int i = 0; i < 3; i++) {
            cards.add(new Prison("Prison", this));
        }

        for (int i = 0; i < 30; i++) {
            cards.add(new Bang("Bang", this));
        }

        for (int i = 0; i < 15; i++) {
            cards.add(new Missed("Missed", this));
        }

        for (int i = 0; i < 8; i++) {
            cards.add(new Beer("Beer", this));
        }

        for (int i = 0; i < 6; i++) {
            cards.add(new CatBalou("CatBalou", this));
        }

        for (int i = 0; i < 4; i++) {
            cards.add(new Stagecoach("Stagecoach", this));
        }

        for (int i = 0; i < 2; i++) {
            cards.add(new Indians("Indians", this));
        }

        Collections.shuffle(cards);
        return cards;
    }
}
