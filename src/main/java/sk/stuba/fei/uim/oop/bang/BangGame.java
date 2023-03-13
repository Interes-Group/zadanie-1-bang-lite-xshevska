package sk.stuba.fei.uim.oop.bang;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;
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
        System.out.println("This is players: ");
        this.printAllPlayers();

    }

    private void startGame() {
        System.out.println("--- GAME STARTED ---");

    }


    private void printAllPlayers() {
        Arrays.stream(players).forEach((e) -> {
            System.out.println(e + "\n");
        });
    }

}
