package com.technogise.interview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.technogise.interview.chess.ChessBoard;

public class ChessBoardTest {

    private static final Position POSITION_0_0 = new Position(0, 0);
    private ChessBoard board;

    @Before
    public void setup() {
        board = new ChessBoard();
    }

    @Test
    public void givenPostionShouldReturnPiecePlacedAtThatPosition() {
        board.place(POSITION_0_0, new Pawn(Color.BLACK));

        Optional<Piece> piece = board.get(POSITION_0_0);

        assertEquals(piece.get(), new Pawn(POSITION_0_0, Color.BLACK));
    }

    @Test
    public void givenPostionHavingPawnShouldReturnPossibleNextPositions() {
        board.place(new Position(1, 6), new Pawn(Color.BLACK));

        List<Position> nextPositions = board.getNextPosition(new Position(1, 6));

        assertTrue(nextPositions.contains(new Position(1, 4)));
        assertTrue(nextPositions.contains(new Position(1, 5)));
    }
}
