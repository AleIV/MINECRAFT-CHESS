package net.noobsters.core.paper;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Flags;
import co.aikar.commands.annotation.Subcommand;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.noobsters.core.paper.ChessManager.PieceColor;
import net.noobsters.core.paper.ChessManager.PieceType;
import net.noobsters.core.paper.board.Box;
import net.noobsters.core.paper.board.BoxLoc;
import net.noobsters.core.paper.board.ChessBoard;
import net.noobsters.core.paper.board.Piece;

@RequiredArgsConstructor
@CommandPermission("chess.cmd")
@CommandAlias("chess")
public class ChessCMD extends BaseCommand {
    private @NonNull Chess instance;

    ChessBoard board;

    @Subcommand("create-here")
    public void createBoard(Player player1, @Flags("other") String player2, Integer boardSize) {

        //var chessManager = instance.getChessManager();
        // creation of the board
        List<String> players = new ArrayList<>();
        players.add(player1.getName().toString());
        players.add(player2);

        board = new ChessBoard(instance, player1.getLocation(), boardSize, players);
        
        //chessManager.getBoards().add(board);
    }

    @Subcommand("summon")
    public void summonPiece(Player player, PieceType piece, PieceColor pieceColor) {
        new Piece(piece, pieceColor, new Box(player.getLocation()), player.getName().toString());

    }

    @Subcommand("move")
    public void movePiece(Player player, String column1, int row1, String column2, int row2) {
        
        board.move(new BoxLoc(column1, row1), new BoxLoc(column2, row2));

    }

}