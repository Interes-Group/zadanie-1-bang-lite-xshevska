package sk.stuba.fei.uim.oop.player;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.blue.BlueCard;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        return cards.stream().map((e) -> {
            StringBuffer st = new StringBuffer();
            st.append(e.getName() + "|");
            return st;
        }).collect(Collectors.joining());
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Name: " + name + " " + " ❤x" + lives);
        sb.append("\nBLUE CARDS [" + printBlueCards() + "]");
        sb.append("\nCARDS [" + printPlayerCards() + "]");
        return sb.toString();
    }
}
