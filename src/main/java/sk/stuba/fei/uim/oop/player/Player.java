package sk.stuba.fei.uim.oop.player;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.blue.BlueCard;
import sk.stuba.fei.uim.oop.cards.blue.Dynamite;
import sk.stuba.fei.uim.oop.cards.blue.Prison;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class Player {
    public static final String ANSI_BLUE = "\u001B[34m";
    private String name;
    private int lives;
    private ArrayList<BlueCard> blueCards;
    private ArrayList<Card> cards;

    public Player(String name) {
        this.cards = new ArrayList<>();
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

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public boolean isActive() {
        return this.lives > 0;
    }

    public boolean hasDynamite() {
        Card dynamite = this.getBlueCards().stream()
                .filter(card -> card instanceof Dynamite).findAny().orElse(null);
        return dynamite == null;
    }


    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(int card) {
        this.cards.remove(card);
    }

    public void removeBlueCard(int indexCard) {
        this.blueCards.remove(indexCard);
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

    public void addBlueCard(BlueCard blueCard) {
        this.blueCards.add(blueCard);
    }

    public String printCardsOnHand() {
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < this.getCards().size(); i++) {
            if (i == this.getCards().size() - 1) {
                st.append(i + 1).append(" - ").append(this.getCards().get(i).getName());
            } else {
                st.append(i + 1).append(" - ").append(this.getCards().get(i).getName()).append(" | ");
            }
        }
        return st.toString();
    }

    @Override
    public String toString() {
        return "Name: " + name + " " + " ❤x" + lives + ANSI_BLUE + "\nCARDS ON HAND: [" + printCardsOnHand() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return lives == player.lives && name.equals(player.name) && blueCards.equals(player.blueCards) && cards.equals(player.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lives, blueCards, cards);
    }

    public void printDead() {
        System.out.println("\n" + this.getName() + " ARE DEAD... 💀\n");
    }

    public boolean checkDuplicate(Card card) {
        for (Card c : this.getBlueCards()) {
            if (card.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public int getIndexOfDynamite() {
        for (int i = 0; i < this.getBlueCards().size(); i++) {
            if (this.getBlueCards().get(i) instanceof Dynamite) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkPrisoner() {
        Card prison = this.getBlueCards().stream().filter(card -> card instanceof Prison).findAny().orElse(null);
        System.out.println("checkPrisoner " + this.getName() + ": " + prison + " | " + (prison != null));
        return prison != null;
    }
}
