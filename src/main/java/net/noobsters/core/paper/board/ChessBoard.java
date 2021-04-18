package net.noobsters.core.paper.board;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

import lombok.Data;
import lombok.NonNull;
import net.noobsters.core.paper.Chess;
import net.noobsters.core.paper.ChessManager.PieceColor;
import net.noobsters.core.paper.ChessManager.PieceType;

public @Data class ChessBoard {
    private @NonNull Chess instance;
    
    private Box[][] board = new Box[8][8];
    private int boardSize = 1;
    private boolean inGame = false;
    private Location boardLocation;
    private List<String> players; //participants name
    private List<Move> history = new ArrayList<>();

    public ChessBoard(Chess instance, Location boardLocation, int boardSize, List<String> players){
        this.boardLocation = boardLocation;
        this.boardSize = boardSize;
        this.players = players;

        var diameter = (boardSize*2)+1;
        var white = true;
        for (int i = 0; i < board.length; i++) {
            white = !white;
            var location = boardLocation.clone().add(i == 0 ? 0 : diameter*i, 0, 0);
            for (int j = 0; j < board.length; j++) {
                white = !white;
                var loc = location.clone().add(0, 0, j == 0 ? 0 : diameter*j);
                createSquare(loc, white ? Material.WHITE_CONCRETE : Material.BLACK_CONCRETE);
                var finalLoc = loc.add(0, 1, 0);
                board[i][j] = new Box(finalLoc);
                
            }
        }
        resetPieces();

    }

    public void resetPieces(){
        var player1 = players.get(0);
        var player2 = players.get(1);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                var box = board[i][j];
                if(box.getPiece() != null){
                    var npc = box.getPiece().getNpc();
                    if(npc != null){
                        npc.despawn();
                        npc.destroy();
                    }
                    box.getPiece().finalize();
                }

                
                if(j == 1){
                    //white pawns
                    box.setPiece(new Piece(PieceType.PAWN, PieceColor.WHITE, box, player1));                    

                }else if(j == 6){
                    //black pawns
                    box.setPiece(new Piece(PieceType.PAWN, PieceColor.BLACK, box, player2));

                }else if(box == getBox("D", 1)){
                    //white queen

                    box.setPiece(new Piece(PieceType.QUEEN, PieceColor.WHITE, box, player1));
                }else if(box == getBox("D", 8)){
                    //black queen

                    box.setPiece(new Piece(PieceType.QUEEN, PieceColor.BLACK, box, player2));

                }else if(box == getBox("E", 1)){
                    //white king
                    box.setPiece(new Piece(PieceType.KING, PieceColor.WHITE, box, player1));

                }else if(box == getBox("E", 8)){
                    //black king
                    box.setPiece(new Piece(PieceType.KING, PieceColor.BLACK, box, player2));

                }else if(box == getBox("A", 1) || box == getBox("H", 1)){
                    //white towers
                    box.setPiece(new Piece(PieceType.ROOK, PieceColor.WHITE, box, player1));
                    
                }else if(box == getBox("A", 8) || box == getBox("H", 8)){
                    //black rooks
                    box.setPiece(new Piece(PieceType.ROOK, PieceColor.BLACK, box, player2));

                }else if(box == getBox("B", 1) || box == getBox("G", 1)){
                    //white knights
                    box.setPiece(new Piece(PieceType.KNIGHT, PieceColor.WHITE, box, player1));

                }else if(box == getBox("B", 8) || box == getBox("G", 8)){
                    //black knights
                    box.setPiece(new Piece(PieceType.KNIGHT, PieceColor.BLACK, box, player2));

                }else if(box == getBox("C", 1) || box == getBox("F", 1)){
                    //white bishop
                    box.setPiece(new Piece(PieceType.BISHOP, PieceColor.WHITE, box, player1));

                }else if(box == getBox("C", 8) || box == getBox("F", 8)){
                    //black bishop
                    box.setPiece(new Piece(PieceType.BISHOP, PieceColor.BLACK, box, player2));
                }

                
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

    public boolean move(BoxLoc from, BoxLoc to){
        var box1 = getBox(from);
        var box2 = getBox(to);

        if(box1.isEmpty() || !box2.isEmpty()){
            return false;
            
        }else{
            var piece = box1.getPiece();
            box1.getLocation().clone().add(0, -1, 0).getBlock().setType(Material.LIME_CONCRETE);
            box2.setPiece(piece);
            box1.setPiece(null);

            piece.setBox(box2);
            piece.setHasBeenMoved(true);
            
            var npc = piece.getNpc();
            npc.getNavigator().setTarget(box2.getLocation());
            box2.getLocation().clone().add(0, -1, 0).getBlock().setType(Material.YELLOW_CONCRETE);

            history.add(new Move(from, to));
            return true;
        }
    }


    public Box getBox(int column, int row){
        return board[column-1][row-1];
    }

    public Box getBox(String column, int row){
        var rowPos = row-1;
        switch (column) {
            case "H": return board[0][rowPos];
            case "G": return board[1][rowPos];
            case "F": return board[2][rowPos];
            case "E": return board[3][rowPos];
            case "D": return board[4][rowPos];
            case "C": return board[5][rowPos];
            case "B": return board[6][rowPos];
            case "A": return board[7][rowPos];
                
            default:
                break;
        }

        return null;
    }

    public Box getBox(BoxLoc move){
        return getBox(move.getColumn(), move.getRow());
    }

}
