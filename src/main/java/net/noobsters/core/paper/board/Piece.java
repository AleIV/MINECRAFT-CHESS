package net.noobsters.core.paper.board;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import net.noobsters.core.paper.ChessManager.Color;
import net.noobsters.core.paper.ChessManager.PieceType;

public @Data class Piece {
    private PieceType pieceType;
    private Color color;
    private Box box = null;
    private List<Move> possibleMoves = new ArrayList<>();
    private Boolean hasBeenMoved = false;

    public Piece(PieceType pieceType, Color color, Box box){
        this.pieceType = pieceType;
        this.color = color;
        this.box = box;
    }

}
