package sk.stuba.fei.uim.oop.player;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.blue.BlueCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import static sk.stuba.fei.uim.oop.bang.BangGame.ANSI_BLUE;

public class Player {
    private final String name;
    private int lives;

    private ArrayList<BlueCard> blueCards;
    private ArrayList<Card> cards;

//    private List<Card> greenCards;
//    private List<Card> blueCards;


    public Player(String name) {
        this.cards = new ArrayList<Card>();
        this.blueCards = new ArrayList<>();
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

    public void removeCard(int card) {
        this.cards.remove(card);
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }

    public ArrayList<Card> removeCardsFromHand() {
        ArrayList<Card> removedCards = new ArrayList<>(this.cards);
        removedCards.addAll(this.blueCards);
        this.cards.clear();
        this.blueCards.clear();
        Collections.shuffle(removedCards);
        return removedCards;
    }

    public void removeLife() {
        this.lives--;
    }

    public ArrayList<BlueCard> getBlueCards() {
        return blueCards;
    }

    //добавление толоько одной карты в наш арей лист синих карт
    public void addBlueCard(BlueCard blueCard) {
        this.blueCards.add(blueCard);
    }

    private String printBlueCards() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < blueCards.size(); i++) {
            if (i == blueCards.size() - 1) {
                sb.append("|" + blueCards.get(i).getName() + "|");
            }
            sb.append("|" + blueCards.get(i).getName());
        }
        return sb.toString();
    }

    private String printPlayerCards() {
        int counter = 1;
        return cards.stream().map((e) -> {
            StringBuffer st = new StringBuffer();
            if(cards.get(cards.size()-1).equals(e)){
                st.append((cards.indexOf(e)+1) +" - " + e.getName());
            }else {
                st.append((cards.indexOf(e)+1) +" - " + e.getName() + "| ");
            }
            return st;
        }).collect(Collectors.joining());
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Name: " + name + " " + " ❤x" + lives + ANSI_BLUE);
        sb.append("\nCARDS ON HAND: [" + printPlayerCards() + "]");
        return sb.toString();
    }
}
