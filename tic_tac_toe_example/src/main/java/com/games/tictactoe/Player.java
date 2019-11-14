package com.games.tictactoe;

public class Player {

    private int playerType;
    private String name;
    private int symbol;

    public Player(String name, int playerType, int symbol) {
        this.playerType = playerType;
        this.name = name;
        this.symbol = symbol;
    }
}
