package com.technogise.interview.chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.technogise.interview.Piece;
import com.technogise.interview.Position;

public class ChessBoard {

    private Cell[][] board = new Cell[8][8];

    public Optional<Piece> get(Position position) {
        return board[position.getX()][position.getY()].getPiece();
    }

    public void place(Position position, Piece piece) {
        piece.setPosition(position);

        board[position.getX()][position.getY()] = new Cell(position, piece);
    }

    public List<Position> getNextPosition(Position position) {
        Optional<Piece> piece = get(position);
        if (piece.isPresent()) {
            piece.get().getNextPositions(this);
        }
        return new ArrayList<Position>();
    }

}
