package com.technogise.interview.chess;

import java.util.Optional;

import com.technogise.interview.Piece;
import com.technogise.interview.Position;

public class Cell {

    private Position pos;
    private Optional<Piece> piece;

    public Cell(Position position, Piece piece) {
        this.pos = position;
        this.piece = Optional.ofNullable(piece);

    }

    public Optional<Piece> getPiece() {
        return piece;
    }

}
