package sk.stuba.fei.uim.oop.player;

import sk.stuba.fei.uim.oop.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private int lives;
    private ArrayList<Card> cards;

//    private List<Card> greenCards;
//    private List<Card> blueCards;


    public Player(String name) {
        this.cards = new ArrayList<Card>();
        this.name = name;
        this.lives = 4;
    }

    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public boolean isActive() {
        return this.lives > 0;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeLife() {
        this.lives--;
    }
}
