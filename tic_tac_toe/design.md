# Tic Tac Toe Design
This document explains the various aspects of design for developing a Tic Tac Toe game.

## Table of content

- [Tic Tac Toe Design](#Tic-Tac-Toe-Design)
  - [Table of content](#Table-of-content)
  - [Assumptions](#Assumptions)
  - [Overview](#Overview)
  - [OO design for game](#OO-design-for-game)
    - [Game loop](#Game-loop)


## Assumptions
- This game is a two player game.
- There won't be any spectators for this game.
- There is no time limit for any player to make a move. But, it is assumed that player will make a move within few seconds making this a near real-time game. But the game design does not enforce any time limit.

## Overview
In this document, we will consider following aspects of the game design.

- Core logic of Game
  - The rules of the game
  - Stratergy for making move
  - Decision regarding who has won & lost or draw

- Game Display functionality

- Communication between players - This needs to be near-real time

## OO design for game
This section describes the high level object-oriented design of the game.

- GameDisplay
  - game Game - Reference to Game
  - render() - method to render the game

- Game
  - gameID uuid - unique ID for the game
  - board Board - reference to the game board
  - host Player - first player who has created this game - Has symbol `X`
  - joiner Player - player who has joined this game as second player - Has symbol `O`
  - winner string - "host" / "joiner"
  - moves List<Move> - list of moves

- Board
  - int[3][3] board - initialize each element in array to 0
  - makeMove(Move move) - this method updates the move on the board
  
- Player
  - name string - Name of the player
  - symbol int - `X` / `O` or `1` / `-1`
    - Using 1 and -1 can help in calculating the winner easily but just summing up the values in a row/column/diagonal
  - moveStratergy MoveStratergy - Each player can have different stratergy for making a move
  - board Board - player needs a reference to the board to decide next move

- Move
  - row, column int
  - player Player - Player who has made this move

### Game loop

```java
// start method shows the pseudo code for game loop
public String start() {
    Player currPlayer = host;
    while(winner == null && board.isMovePossible()) {
        gameDisplay.render();
        
        Move move = currPlayer.makeMove();
        moves.add(move);

        winner = board.getWinner();

        // switch turn
        currPlayer = currPlayer == host ? joiner : host;
    }
    return winner;
}
```


