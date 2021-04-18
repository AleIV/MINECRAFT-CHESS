package net.noobsters.core.paper.board;

import lombok.Data;

public @Data class Move {
    private BoxLoc from;
    private BoxLoc to;

    public Move(BoxLoc from, BoxLoc to){
        this.from = from;
        this.to = to;
    }
}
