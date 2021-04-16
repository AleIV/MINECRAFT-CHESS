package net.noobsters.core.paper.board;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

import lombok.Data;

public @Data class ChessBoard {

    private Box[][] board = new Box[8][8];
    private int boardSize = 1;
    private boolean inGame = false;
    private Location boardLocation;
    private List<String> players; //participants UUID

    public ChessBoard(Location boardLocation, int boardSize){
        this.boardLocation = boardLocation;
        this.boardSize = boardSize;

        var diameter = (boardSize*2)+1;
        var white = true;
        for (int i = 0; i < board.length; i++) {
            white = !white;
            var location = boardLocation.clone().add(i == 0 ? 0 : diameter*i, 0, 0);
            for (int j = 0; j < board.length; j++) {
                white = !white;
                var loc = location.clone().add(0, 0, j == 0 ? 0 : diameter*j);
                createSquare(loc, white ? Material.WHITE_CONCRETE : Material.BLACK_CONCRETE);
                board[i][j] = new Box(loc);
                
            }
        }

    }

    public void createSquare(Location location, Material material){
        var loc = location.clone().add(-boardSize, 0, -boardSize);
        var diameter = (boardSize*2)+1;
        for (int i = 0; i < diameter; i++) {
            for (int j = 0; j < diameter; j++) {
                var block = loc.getWorld().getBlockAt(loc).getRelative(i, 0, j);
                block.setType(material);
            }
        }
    }

    public void move(Piece piece, Move move){
        var box = getBox(move);
        box.setPiece(piece);
        piece.setBox(box);
        piece.setHasBeenMoved(true);
    }

    public Box getBox(int column, int row){
        return board[column-1][row-1];
    }

    public Box getBox(String column, int row){
        var rowPos = row-1;
        switch (column) {
            case "A": return board[0][rowPos];
            case "B": return board[1][rowPos];
            case "C": return board[2][rowPos];
            case "D": return board[3][rowPos];
            case "E": return board[4][rowPos];
            case "F": return board[5][rowPos];
            case "G": return board[6][rowPos];
            case "H": return board[7][rowPos];
                
            default:
                break;
        }

        return null;
    }

    public Box getBox(Move move){
        return getBox(move.getColumn(), move.getRow());
    }

}
