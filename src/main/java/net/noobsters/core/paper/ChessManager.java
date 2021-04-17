package net.noobsters.core.paper;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import lombok.Data;
import lombok.NonNull;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.noobsters.core.paper.board.ChessBoard;

public @Data class ChessManager implements Listener {

    private @NonNull Chess instance;
    private List<ChessBoard> boards;

    public ChessManager(Chess instance) {
        this.instance = instance;

        Bukkit.getPluginManager().registerEvents(this, instance);

    }

    @EventHandler
    public void onDeathNPC(NPCDeathEvent e) {
        var npc = e.getNPC();
        npc.destroy();

    }

    public enum PieceColor {
        WHITE, BLACK, RED, YELLOW, BLUE, GREEN;
    }

    public enum PieceType {
        PAWN, KING, QUEEN, BISHOP, KNIGHT, ROOK;
    }

}
