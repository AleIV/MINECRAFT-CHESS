package net.noobsters.core.paper;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Horse.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

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
import net.citizensnpcs.trait.HorseModifiers;
import net.citizensnpcs.trait.SkinTrait;
import net.citizensnpcs.trait.versioned.ShulkerTrait;
import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.ChessManager.PieceColor;
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
        if (boardSize != null) {
            size = boardSize;
        }
        var chessManager = instance.getChessManager();
        // creation of the board
        var board = new ChessBoard(player.getLocation(), size);
        chessManager.getBoards().add(board);
    }

    public ItemStack createCustomShield(DyeColor color) {

        List<Pattern> patterns = new ArrayList<>();
        patterns.add(new Pattern(DyeColor.BLACK, PatternType.BRICKS));
        patterns.add(new Pattern(color, PatternType.GRADIENT));
        patterns.add(new Pattern(color, PatternType.GRADIENT_UP));
        patterns.add(new Pattern(DyeColor.LIGHT_GRAY, PatternType.CROSS));
        patterns.add(new Pattern(DyeColor.LIGHT_GRAY, PatternType.CIRCLE_MIDDLE));
        patterns.add(new Pattern(DyeColor.GRAY, PatternType.FLOWER));

        var shield = new ItemStack(Material.SHIELD);
        var meta = shield.getItemMeta();
        var bmeta = (BlockStateMeta) meta;
        var banner = (Banner) bmeta.getBlockState();

        banner.setPatterns(patterns);
        banner.setBaseColor(color);

        banner.update();
        bmeta.setBlockState(banner);
        shield.setItemMeta(bmeta);

        return shield;
    }

    @Subcommand("summon")
    public void summonPiece(Player player, PieceType piece, PieceColor pieceColor) {

        var chatColor = ChatColor.WHITE;
        switch (pieceColor) {
        case WHITE:
            chatColor = ChatColor.WHITE;
            break;
        case BLACK:
            chatColor = ChatColor.DARK_GRAY;
            break;
        case RED:
            chatColor = ChatColor.DARK_RED;
            break;
        case YELLOW:
            chatColor = ChatColor.YELLOW;
            break;
        case BLUE:
            chatColor = ChatColor.BLUE;
            break;
        case GREEN:
            chatColor = ChatColor.DARK_GREEN;
            break;

        default:
            break;
        }
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, chatColor + "" + piece);

        switch (piece) {
        case PAWN: {

            switch (pieceColor) {
            case WHITE: {

                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET, new ItemStack(Material.CHAINMAIL_HELMET));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE,
                        new ItemStack(Material.IRON_CHESTPLATE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.CHAINMAIL_LEGGINGS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, new ItemStack(Material.IRON_BOOTS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.OFF_HAND, createCustomShield(DyeColor.WHITE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND, new ItemStack(Material.IRON_SWORD));
            }
                break;
            case BLACK: {
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET, new ItemStack(Material.NETHERITE_HELMET));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE,
                        new ItemStack(Material.IRON_CHESTPLATE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.NETHERITE_LEGGINGS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, new ItemStack(Material.IRON_BOOTS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.OFF_HAND, createCustomShield(DyeColor.BLACK));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND, new ItemStack(Material.NETHERITE_SWORD));
            }
                break;

            default:
                break;
            }
        }
            break;
        case KING: {

            switch (pieceColor) {
            case WHITE: {
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET,
                        new ItemBuilder(Material.GOLDEN_HELMET).enchant(Enchantment.DURABILITY).build());
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE,
                        new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.CHAINMAIL_LEGGINGS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, new ItemStack(Material.GOLDEN_BOOTS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND, new ItemStack(Material.GOLDEN_AXE));
            }

                break;
            case BLACK: {
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET,
                        new ItemBuilder(Material.GOLDEN_HELMET).enchant(Enchantment.DURABILITY).build());
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE,
                        new ItemStack(Material.NETHERITE_CHESTPLATE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.NETHERITE_LEGGINGS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, new ItemStack(Material.GOLDEN_BOOTS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND, new ItemStack(Material.GOLDEN_AXE));
            }

                break;

            default:
                break;
            }
        }
            break;
        case QUEEN: {
            switch (pieceColor) {
            case WHITE: {
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET, new ItemStack(Material.DIAMOND_HELMET));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE,
                        new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.CHAINMAIL_LEGGINGS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, new ItemStack(Material.DIAMOND_BOOTS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND,
                        new ItemBuilder(Material.TRIDENT).enchant(Enchantment.LOYALTY, 3).build());
            }

                break;
            case BLACK: {
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET, new ItemStack(Material.DIAMOND_HELMET));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE,
                        new ItemStack(Material.NETHERITE_CHESTPLATE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.NETHERITE_LEGGINGS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, new ItemStack(Material.DIAMOND_BOOTS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND,
                        new ItemBuilder(Material.TRIDENT).enchant(Enchantment.LOYALTY, 3).build());
            }

                break;

            default:
                break;
            }
        }
            break;
        case BISHOP: {
            switch (pieceColor) {
            case WHITE: {
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET, new ItemStack(Material.BLAST_FURNACE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE,
                        new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.CHAINMAIL_LEGGINGS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, new ItemStack(Material.CHAINMAIL_BOOTS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND, new ItemStack(Material.IRON_SWORD));
            }

                break;
            case BLACK: {
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET, new ItemStack(Material.BLAST_FURNACE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE,
                        new ItemStack(Material.NETHERITE_CHESTPLATE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.NETHERITE_LEGGINGS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, new ItemStack(Material.NETHERITE_BOOTS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND, new ItemStack(Material.NETHERITE_SWORD));
            }

                break;

            default:
                break;
            }
        }
            break;
        case KNIGHT: {
            npc.setBukkitEntityType(EntityType.HORSE);

            switch (pieceColor) {
            case WHITE: {
                npc.getOrAddTrait(HorseModifiers.class).setColor(Color.WHITE);

            }

                break;
            case BLACK: {
                npc.getOrAddTrait(HorseModifiers.class).setColor(Color.BLACK);

            }

                break;

            default:
                break;
            }
        }
            break;
        case ROOK: {
            npc.setBukkitEntityType(EntityType.SHULKER);
            npc.getOrAddTrait(ShulkerTrait.class).setPeek(100);
            switch (pieceColor) {
                case WHITE: {
                    npc.getOrAddTrait(ShulkerTrait.class).setColor(DyeColor.WHITE);
    
                }
    
                    break;
                case BLACK: {
                    npc.getOrAddTrait(ShulkerTrait.class).setColor(DyeColor.BLACK);
    
                }
    
                    break;
    
                default:
                    break;
                }
        }
            break;

        default:
            break;
        }

        npc.data().setPersistent(NPC.DEFAULT_PROTECTED_METADATA, false);
        npc.getOrAddTrait(SkinTrait.class).setSkinName(player.getName().toString());

        npc.spawn(player.getLocation());
    }

}