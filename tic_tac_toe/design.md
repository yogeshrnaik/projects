# Tic Tac Toe Design
This document explains the various aspects of design for developing a Tic Tac Toe game.

## Table of content

- [Tic Tac Toe Design](#Tic-Tac-Toe-Design)
  - [Table of content](#Table-of-content)
  - [Assumptions](#Assumptions)
  - [Overview](#Overview)
  - [Game flow](#Game-flow)
    - [Game start flow](#Game-start-flow)
    - [Game loop](#Game-loop)
  - [OO design for game](#OO-design-for-game)
    - [Tic Tac Toe main program](#Tic-Tac-Toe-main-program)


## Assumptions
- This game is a two player game.
- There won't be any spectators for this game.
- There is no time limit for any player to make a move. But, it is assumed that player will make a move without blocking forever to make this a near real-time game. But the game design does not enforce any time limit as of now.

## Overview
In this document, we will consider following aspects of the game design.

- Core logic of Game
  - The rules of the game
  - Stratergy for making move
  - Decision regarding who has won & lost or draw

- Game Display functionality
  - This should be developed as a plugin so that we can use console display or GUI based display

- Communication between players - This needs to be near-real time

## Game flow
There are two components to Tic Tac Toe game.
- Server
- Client 1 representing player 1
- Client 2 representing player 2

Flow of game is as follows:
1) Game Server program starts and prints out the IP:PORT of the game server and then starts listening for connection on that
2) Client program is executed by player 1 on his terminal passing in the IP:PORT of the game server
3) Similary, another instance of client program is executed by player 2 on his terminal passing in the IP:PORT of the game server
4) Game server upon receiving player 1's connection make that player the "host" player of that game and waits for another player to join the same game.
5) When client 2 establishes the connection to game server, game server makes it player 2 and starts the game.

### Game start flow
1) When both player joins the game, Game server starts the game by calling game.start().
2) The pseudo code of start() method is shown below.

### Game loop
```java
// start method shows the pseudo code for game loop
// this method is called when both player have joined the game
public void start() {
    String winner = null;
    Player currPlayer = null;
    while(winner == null && board.isMovePossible()) {
        currPlayer = currPlayer == null || currPlayer == joiner ? host : joiner;
        gameDisplay.render();
        
        Move move = currPlayer.makeMove();
        moves.add(move);

        winner = board.getWinner();
    }
    game.setResult(new GameResult(winner));
}
```


## OO design for game
This section describes the high level object-oriented design of the game.

- TicTacToeServer
  - This class will act as the server

- TicTacToeClient
  - This class acts as the client for the TicTacToeServer

- GameDisplay
  - render(game Game) - method to render the game

We can have multiple implementations of the GameDisplay.
- ConsoleGameDisplay - prints the game on the console
- GUIGameDisplay - renders the game on some GUI

- Game
  - gameID uuid - unique ID for the game
  - board Board - reference to the game board
  - host Player - first player who has created this game - Has symbol `X`
  - joiner Player - player who has joined this game as second player - Has symbol `O`
  - moves List<Move> - list of moves
  - gameState String - end state of the game - not started / in progress / decided / draw

- Board
  - int[3][3] board - initialize each element in array to 0
  - currPlayer - flag for player indicating whose turn is it right now
  - void makeMove(Move move) 
    - this method updates the move on the board if the move is valid
    - if move is invalid throws InvalidMoveException with error why the move is invalid
  - boolean isMovePossible(Move move)
    - this method returns true is provided move is valid
  
- Player
  - name string - Name of the player
  - symbol int - `X` / `O` or `1` / `-1`
    - Using 1 and -1 can help in calculating the winner easily but just summing up the values in a row/column/diagonal
  - moveStratergy MoveStratergy - Each player can have different stratergy for making a move
  - board Board - player needs a reference to the board to decide next move

- Move
  - row, column int
  - player Player - Player who has made this move

- Move


### Tic Tac Toe main program

When the program starts, it asks for the mode in which the player wants to continue.
- host mode 
  -  in this mode the player will be the host of a new game

- join mode
  - in this mode the player will be asked to enter ID of the game he/she wants to join

```java
class TicTacToeProgram {
    private GameDisplay gameDisplay;

    public TicTacToeProgram(GameDisplay display) {
        this.display = display;
    }

    public static void main(String[] args) {
        TicTacToeProgram program = new TicTacToeProgram(new ConsoleDisplay());
        Player player = new Player(askName());
        do {
            String mode = gameDisplay.askMode();
            if "host".equals(mode) {
                Game game = new Game(gameDisplay, player);
                Player joiner = game.waitForOtherPlayer();
                game.setJoiner(joiner);
                game.start();
                game.showResult();
            } else if "join".equals(mode) {
                String gameID = askGameID();
                Game game = joinGame(gameID);
                game.start();
                game.showResult();
            } else if "single-player".equals(mode) {
                Game game = new Game(gameDisplay, player);
                game.setJoiner(new ComputerPlayer("computer"));
                game.start();
                game.showResult();
            }
        } while (!mode.equals("exit"))
}
```

