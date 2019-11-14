package com.games.tictactoe;

import java.util.*;

public class Game {

    private String gameID;
    private Board board;
    private Player host;
    private Player joiner;
    private List<Move> moves;
    private GameDisplay gameDisplay;

    public Game(Player host, GameDisplay gameDisplay) {
        this.host = host;
        this.gameDisplay = gameDisplay;
    }

    public static void main(String[] args) {
        Game game = new Game(new Player("host", 1, 1), new GameDisplay());
    }
}
