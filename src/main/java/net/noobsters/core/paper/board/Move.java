package net.noobsters.core.paper.board;

import lombok.Data;

public @Data class Move {
    private String column;
    private int row;

    public Move(String column, int row){
        this.column = column;
        this.row = row;
    }
}
