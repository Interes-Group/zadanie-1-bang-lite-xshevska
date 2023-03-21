package sk.stuba.fei.uim.oop.player;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.blue.BlueCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import static sk.stuba.fei.uim.oop.bang.BangGame.ANSI_BLUE;

public class Player {
    private final String name;
    private int lives;

    private ArrayList<BlueCard> blueCards;
    private ArrayList<Card> cards;

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

    public void addLife() {
        this.lives++;
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
            sb.append("|").append(blueCards.get(i).getName());
        }
        return sb.toString();
    }

    public String printCardsOnHand() {
        return cards.stream().map((e) -> {
            StringBuffer st = new StringBuffer();
            if(cards.get(cards.size()-1).equals(e)){
                st.append(cards.indexOf(e) + 1).append(" - ").append(e.getName());
            }else {
                st.append(cards.indexOf(e) + 1).append(" - ").append(e.getName()).append("| ");
            }
            return st;
        }).collect(Collectors.joining());
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Name: " + name + " " + " ❤x" + lives + ANSI_BLUE);
        sb.append("\nCARDS ON HAND: [" + printCardsOnHand() + "]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return lives == player.lives && name.equals(player.name) && blueCards.equals(player.blueCards)
                && cards.equals(player.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lives, blueCards, cards);
    }

    public void printDead() {
        System.out.println("\n" + this.getName() + " ARE DEAD... 💀\n");
    }
}
