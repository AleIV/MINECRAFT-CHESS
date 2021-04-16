package net.noobsters.core.paper;

import java.util.List;

import lombok.Data;
import lombok.NonNull;
import net.noobsters.core.paper.board.ChessBoard;

public @Data class ChessManager {
    
    private @NonNull Chess instance;
    private List<ChessBoard> boards;

    public ChessManager(Chess instance){
        this.instance = instance;

    }

    public enum Civilization {
        ZOMB, BLACK, RED, YELLOW, BLUE, GREEN;
    }

    public enum Color {
        WHITE, BLACK, RED, YELLOW, BLUE, GREEN;
    }

    public enum PieceType {
        PAWN, KING, QUEEN, BISHOP, KNIGHT, ROOK;
    }

}
