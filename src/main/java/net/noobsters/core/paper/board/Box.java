package net.noobsters.core.paper.board;

import org.bukkit.Location;

import lombok.Data;

public  @Data class Box {
    
    private Location location;
    private Piece piece = null;

    public Box(Location location){
        this.location = location;

    
    }

    public void setPiece(Piece piece){
        this.piece = piece;
        //create entity
    }

    public boolean containsPiece(){
        return piece != null;
    }

    public boolean containsPiece(Piece piece){
        return this.piece != null && this.piece == piece;
    }
}
