package com.technogise.interview;

import java.util.ArrayList;
import java.util.List;

import com.technogise.interview.chess.ChessBoard;

public class Pawn extends Piece {

    public Pawn(Position position, Color color) {
        super(position, color);
    }

    public Pawn(Color color) {
        this(null, color);
    }

    @Override
    public List<Position> getNextPositions(ChessBoard chessBoard) {
        List<Position> nextPositions = new ArrayList<Position>();

        if (color == Color.BLACK) {
            nextPositions.add(new Position(pos.getX(), pos.getY() - 1));

            if (pos.getY() == 6) {
                nextPositions.add(new Position(pos.getX(), pos.getY() - 2));
            }

        }

        return nextPositions;
    }

}
