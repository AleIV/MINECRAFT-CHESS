package net.noobsters.core.paper;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import fr.mrmicky.fastinv.ItemBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;
import net.citizensnpcs.api.trait.trait.Owner;
import net.noobsters.core.paper.ChessManager.PieceType;
import net.noobsters.core.paper.board.ChessBoard;

@RequiredArgsConstructor
@CommandPermission("chess.cmd")
@CommandAlias("chess")
public class ChessCMD extends BaseCommand {
    private @NonNull Chess instance;

    @Subcommand("create here")
    public void createBoard(Player player, @Optional Integer boardSize) {
        var size = 1;
        if(boardSize != null){
            size = boardSize;
        }
        var chessManager = instance.getChessManager();
        //creation of the board
        var board = new ChessBoard(player.getLocation(), size);
        chessManager.getBoards().add(board);
    }

    @Subcommand("summon")
    public void summonPiece(Player player, PieceType piece) {

            var type = EntityType.PLAYER;

            switch (piece) {
                case PAWN:{
                    type = EntityType.HORSE;
                }break;
                case KING:{
                    type = EntityType.HORSE;
                }break;
                case QUEEN:{
                    type = EntityType.HORSE;
                }break;
                case BISHOP:{
                    type = EntityType.HORSE;
                }break;
                case KNIGHT:{
                    type = EntityType.HORSE;
                }break;
                case ROOK:{
                    type = EntityType.HORSE;
                }break; 
            
                default:
                    break;
            }

            NPC npc = CitizensAPI.getNPCRegistry().createNPC(type, "" + piece);

            var horseArmor = new ItemBuilder(Material.LEATHER_HORSE_ARMOR).meta(LeatherArmorMeta.class, meta -> meta.setColor(Color.fromBGR(255, 255, 255))).build();
            npc.getOrAddTrait(Equipment.class).set(1, horseArmor);

            npc.data().setPersistent(NPC.DEFAULT_PROTECTED_METADATA, false);

            npc.spawn(player.getLocation());
    }

}