package net.noobsters.core.paper.board;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import net.citizensnpcs.api.npc.NPC;
import net.noobsters.core.paper.ChessManager.Color;
import net.noobsters.core.paper.ChessManager.PieceType;

public @Data class Piece {
    private PieceType pieceType;
    private Color color;
    private Box box = null;
    private List<Move> possibleMoves = new ArrayList<>();
    private Boolean hasBeenMoved = false;
    private NPC npc;

    public Piece(PieceType pieceType, Color color, Box box){
        this.pieceType = pieceType;
        this.color = color;
        this.box = box;

    }

}
