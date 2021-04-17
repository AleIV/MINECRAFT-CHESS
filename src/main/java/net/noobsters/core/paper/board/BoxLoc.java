package net.noobsters.core.paper.board;

import lombok.Data;

public @Data class BoxLoc {
    private String column;
    private int row;

    public BoxLoc(String column, int row){
        this.column = column;
        this.row = row;
    }
}
