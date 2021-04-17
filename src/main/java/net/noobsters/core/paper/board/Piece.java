package net.noobsters.core.paper.board;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;

import fr.mrmicky.fastinv.ItemBuilder;
import lombok.Data;
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

public @Data class Piece {
    private PieceType pieceType;
    private PieceColor color;
    private Box box = null;
    private List<BoxLoc> possibleMoves = new ArrayList<>();
    private Boolean hasBeenMoved = false;
    private NPC npc;

    public Piece(PieceType pieceType, PieceColor color, Box box, String player) {
        this.pieceType = pieceType;
        this.color = color;
        this.box = box;

        var loc = box.getLocation();
        switch (color) {
        case WHITE: {
            loc.setYaw(0);
            loc.setPitch(0);
        }

            break;
        case BLACK: {
            loc.setYaw(180);
            loc.setPitch(0);
        }
            break;

        default:
            break;
        }
        this.npc = createPiece(loc, player, pieceType, color);

    }

    @Override
    public void finalize() {
        this.box = null;
        this.npc = null;
        this.possibleMoves.clear();
    }

    public NPC createPiece(Location location, String player, PieceType piece, PieceColor pieceColor) {

        var chatColor = ChatColor.WHITE;
        switch (pieceColor) {
        case WHITE:
            chatColor = ChatColor.WHITE;
            break;
        case BLACK:
            chatColor = ChatColor.GRAY;
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

        switch (piece) {
        case PAWN: {
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, chatColor + "" + piece);
            npc.data().setPersistent(NPC.DEFAULT_PROTECTED_METADATA, false);
            npc.getOrAddTrait(SkinTrait.class).setSkinName(player);
            npc.spawn(location);

            npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE, new ItemStack(Material.IRON_CHESTPLATE));
            npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, new ItemStack(Material.IRON_BOOTS));

            switch (pieceColor) {
            case WHITE: {

                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET, new ItemStack(Material.CHAINMAIL_HELMET));

                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.CHAINMAIL_LEGGINGS));

                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.OFF_HAND, getCustomShield(DyeColor.WHITE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND, new ItemStack(Material.IRON_SWORD));

                return npc;
            }

            case BLACK: {
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET, new ItemStack(Material.NETHERITE_HELMET));

                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.NETHERITE_LEGGINGS));

                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.OFF_HAND, getCustomShield(DyeColor.BLACK));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND, new ItemStack(Material.NETHERITE_SWORD));

                // SentinelTrait sentinel = npc.getOrAddTrait(SentinelTrait.class);

                // sentinel.addTarget("uuid:" + player.getUniqueId());
                return npc;
            }
            default:
                break;

            }
        }
            break;
        case KING: {

            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, chatColor + "" + piece);
            npc.data().setPersistent(NPC.DEFAULT_PROTECTED_METADATA, false);
            npc.getOrAddTrait(SkinTrait.class).setSkinName(player);
            npc.spawn(location);

            npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET,
                    new ItemBuilder(Material.GOLDEN_HELMET).enchant(Enchantment.DURABILITY).build());

            npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, new ItemStack(Material.GOLDEN_BOOTS));
            npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND, new ItemStack(Material.GOLDEN_AXE));

            switch (pieceColor) {
            case WHITE: {

                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE,
                        new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.CHAINMAIL_LEGGINGS));

                return npc;
            }

            case BLACK: {

                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE,
                        new ItemStack(Material.NETHERITE_CHESTPLATE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.NETHERITE_LEGGINGS));
                return npc;
            }
            default:
                break;

            }
        }
            break;
        case QUEEN: {
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, chatColor + "" + piece);
            npc.data().setPersistent(NPC.DEFAULT_PROTECTED_METADATA, false);
            npc.getOrAddTrait(SkinTrait.class).setSkinName(player);
            npc.spawn(location);

            npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET, new ItemStack(Material.DIAMOND_HELMET));
            npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND,
                    new ItemBuilder(Material.TRIDENT).enchant(Enchantment.LOYALTY, 3).build());
            npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, new ItemStack(Material.DIAMOND_BOOTS));

            switch (pieceColor) {
            case WHITE: {

                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE,
                        new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.CHAINMAIL_LEGGINGS));
                return npc;
            }

            case BLACK: {

                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE,
                        new ItemStack(Material.NETHERITE_CHESTPLATE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.NETHERITE_LEGGINGS));
                return npc;
            }
            default:
                break;

            }
        }
            break;
        case BISHOP: {
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, chatColor + "" + piece);
            npc.data().setPersistent(NPC.DEFAULT_PROTECTED_METADATA, false);
            npc.getOrAddTrait(SkinTrait.class).setSkinName(player);
            npc.spawn(location);

            switch (pieceColor) {
            case WHITE: {
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET, getCustomBanner(DyeColor.WHITE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE,
                        new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.CHAINMAIL_LEGGINGS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, new ItemStack(Material.CHAINMAIL_BOOTS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND, new ItemStack(Material.IRON_SWORD));
                return npc;
            }

            case BLACK: {
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET, getCustomBanner(DyeColor.BLACK));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE,
                        new ItemStack(Material.NETHERITE_CHESTPLATE));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS,
                        new ItemStack(Material.NETHERITE_LEGGINGS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, new ItemStack(Material.NETHERITE_BOOTS));
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND, new ItemStack(Material.NETHERITE_SWORD));
                return npc;
            }
            default:
                break;

            }
        }
            break;
        case KNIGHT: {
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.HORSE, chatColor + "" + piece);
            npc.data().setPersistent(NPC.DEFAULT_PROTECTED_METADATA, false);
            npc.getOrAddTrait(HorseModifiers.class).setArmor(new ItemStack(Material.IRON_HORSE_ARMOR));
            npc.spawn(location);

            switch (pieceColor) {
            case WHITE: {
                npc.getOrAddTrait(HorseModifiers.class).setColor(Color.WHITE);
                return npc;
            }

            case BLACK: {
                npc.getOrAddTrait(HorseModifiers.class).setColor(Color.BLACK);
                return npc;
            }
            default:
                break;

            }
        }
            break;
        case ROOK: {

            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.SHULKER, chatColor + "" + piece);
            npc.data().setPersistent(NPC.DEFAULT_PROTECTED_METADATA, false);
            npc.spawn(location);

            npc.getOrAddTrait(ShulkerTrait.class).setPeek(100);
            switch (pieceColor) {
            case WHITE: {
                npc.getOrAddTrait(ShulkerTrait.class).setColor(DyeColor.WHITE);
                return npc;
            }

            case BLACK: {
                npc.getOrAddTrait(ShulkerTrait.class).setColor(DyeColor.BLACK);
                return npc;
            }
            default:
                break;

            }
        }
            break;

        default:
            return null;

        }
        return null;

    }

    public ItemStack getCustomShield(DyeColor color) {

        var shield = new ItemStack(Material.SHIELD);
        var meta = shield.getItemMeta();
        var bmeta = (BlockStateMeta) meta;
        var banner = (Banner) bmeta.getBlockState();

        banner.setPatterns(getGlobalPattern(color));
        banner.setBaseColor(color);

        banner.update();
        bmeta.setBlockState(banner);
        shield.setItemMeta(bmeta);

        return shield;
    }

    public ItemStack getCustomBanner(DyeColor color) {

        Material bannerColor = Material.WHITE_BANNER;
        switch (color) {
        case RED:
            bannerColor = Material.RED_BANNER;
            break;
        case YELLOW:
            bannerColor = Material.YELLOW_BANNER;
            break;
        case BLUE:
            bannerColor = Material.BLUE_BANNER;
            break;
        case GREEN:
            bannerColor = Material.GREEN_BANNER;
            break;
        case WHITE:
            bannerColor = Material.WHITE_BANNER;
            break;
        case BLACK:
            bannerColor = Material.BLACK_BANNER;
            break;
        default:
            break;
        }

        return new ItemBuilder(bannerColor).meta(BannerMeta.class, meta -> meta.setPatterns(getGlobalPattern(color)))
                .build();
    }

    public List<Pattern> getGlobalPattern(DyeColor color) {
        List<Pattern> patterns = new ArrayList<>();
        patterns.add(new Pattern(DyeColor.BLACK, PatternType.BRICKS));
        patterns.add(new Pattern(color, PatternType.GRADIENT));
        patterns.add(new Pattern(color, PatternType.GRADIENT_UP));
        patterns.add(new Pattern(DyeColor.LIGHT_GRAY, PatternType.CROSS));
        patterns.add(new Pattern(DyeColor.LIGHT_GRAY, PatternType.CIRCLE_MIDDLE));
        patterns.add(new Pattern(DyeColor.GRAY, PatternType.FLOWER));

        return patterns;
    }

}
