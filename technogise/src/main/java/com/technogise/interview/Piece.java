package com.technogise.interview;

import java.util.List;

import com.technogise.interview.chess.ChessBoard;

public abstract class Piece {

    protected Position pos;
    protected Color color;

    public Piece(Position position, Color color) {
        this.pos = position;
        this.color = color;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((pos == null) ? 0 : pos.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Piece other = (Piece)obj;
        if (color != other.color)
            return false;
        if (pos == null) {
            if (other.pos != null)
                return false;
        } else if (!pos.equals(other.pos))
            return false;
        return true;
    }

    public void setPosition(Position position) {
        this.pos = position;
    }

    public abstract List<Position> getNextPositions(ChessBoard chessBoard);
}
