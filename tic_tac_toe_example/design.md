# Tic Tac Toe Design
This document explains the various aspects of design for developing a Tic Tac Toe game.

## Table of content

- [Tic Tac Toe Design](#Tic-Tac-Toe-Design)
  - [Table of content](#Table-of-content)
  - [Assumptions](#Assumptions)
  - [Overview](#Overview)
  - [Game flow](#Game-flow)
    - [SocketBasedPlayer flow](#SocketBasedPlayer-flow)
    - [SocketBasedPlayer run method](#SocketBasedPlayer-run-method)
    - [SocketTicTacToeClient program](#SocketTicTacToeClient-program)
  - [OO design for game](#OO-design-for-game)


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
  - This can be via Socket or web-based API communication

## Game flow
There are three components to Tic Tac Toe game.
- Server
- Client 1 representing player 1
- Client 2 representing player 2

Flow of game is as follows:
1) Game Server program starts and prints out the IP:PORT of the game server and then starts listening for connection on that
2) Client program is executed by player 1 on his terminal passing in the IP:PORT of the game server
3) Similary, another instance of client program is executed by player 2 on his terminal passing in the IP:PORT of the game server
4) Game server upon receiving player 1's connection make that player the "host" player of that game and waits for another player to join the same game.
5) When client 2 establishes the connection to game server, game server makes it player 2 (joiner) and then waits for host to make the first move.

```java
public class TicTacToeServer {

    private GameStore gameStore;

    public static void main(String[] args) throws Exception {
        final int PORT = 8888; // read from ENV
        final int THREAD_POOL_SIZE = 50; // read from ENV
        try (var listener = new ServerSocket(PORT)) {
            System.out.println("Tic Tac Toe Game is Running on port " + PORT);
            var pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

            // start server and listen for players to connect
            while (true) {
                // accept a connection from clients
                pool.execute(new SocketBasedPlayer(listener.accept()));
            }
        }
    }
}
```

### SocketBasedPlayer flow
1) SocketBasedPlayer is a class that represents player and wraps the socket communication between server and client program
2) SocketBasedPlayer implements runnable
3) The pseudo code of the run method is shown below

### SocketBasedPlayer run method
```java

public class SocketBasedPlayer {

    private TicTacToeServer server;

    private Socket socket;

    private MoveStratergy moveStratergy;

    // this player object is a POJO that holds state of the player
    private Player player;

    public void run() {
        try {
            player = new Player();
            // ask player's name
            this.player.setName(this.askName());

            while (true) {
                // ask what player wants to do? - host a game or join game
                this.mode = askMode();

                if mode == "exit" {
                    // close socket and return
                    return;
                }

                if mode == "host" {
                    // create a new game and wait for other player to join
                    Game game = server.createNewGame(this);

                    // waitForOtherPlayer should wait on Game object till it is notified
                    Player joiner = this.waitForOtherPlayer();
                    if joiner == null {
                        // send error: nobody joined your game
                        server.removeGame(game);
                        continue;
                    }
                    game.setJoiner(this.player)
                    game.play();
                } else if mode == "join" {
                    gameID = askGameID()
                    Game game = server.findGame(gameID)
                    if game == null {
                        // send error: game not found & continue
                        continue;
                    }
                    game.setJoiner(this);

                    // notifyHostPlayer notifies the Host Player thread
                    game.notifyHostPlayer();
                    game.play();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
```

```java
public class Game {

    private SocketBasedPlayer host;
    private SocketBasedPlayer joiner;

    public void play() {
        String winner = null;
        Player currPlayer = null;
        while(winner == null && board.isMovePossible()) {
            currPlayer = currPlayer == null || currPlayer == joiner ? host : joiner;
            Move move = currPlayer.getMove();
            moves.add(move);
            board.makeMove(move);

            winner = board.getWinner();
        }
        game.setResult(new GameResult(winner));
        game.notfiyAllPlayers();
    }
}
```

### SocketTicTacToeClient program
- This will be the client side program that connects to the server to play Tic Tac Toe
- This will establish a socket connection to the server
- This also has GameDisplay reference to show the current state of the game to the player

```java
public class SocketTicTacToeClient {
    private Socket socket;

    private GameDisplay gameDisplay;

    public static void main(String[] args) {
        socket = new Socket(serverAddress, 8082);
        SocketTicTacToeClient client = new SocketTicTacToeClient(socket);
        client.play();
    }

    public void play() {
        // read from socket input stream
        // server asks for Name - send name and go back to reading from socket input stream
        // server asks for mode => send mode and go back to reading from socker input stream
        // if mode == host then server sends gameID 
        // - wait for notification from server till joiner joins this game ID
        // - after receiving notification that joiner has joined, send move to server and wait for your turn
        // else if mode == join send game ID to server and wait for notification to make move
        // - when server notifies to make move, make your move and send move to server
    }
}
```

## OO design for game
This section describes the high level object-oriented design of the game.

- TicTacToeServer
  - This class will act as the server

- GameStore
  - This class provides the abstract to stores the games
  - This can be a in-memory map or a DB or cache like Redis

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

- MoveStratergy
  - board Board - has copy of the board to calculate next move
  - getMove() Move - this method returns the next move
  - This is an abstraction defined to have different move stratergies
  - For example, RandomMoveStratergy, PlayToWinStratergy, UserInputsMoveStratergy


