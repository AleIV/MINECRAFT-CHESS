package net.noobsters.core.paper;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;

/**
 * Core
 */
public class Chess extends JavaPlugin implements Listener{
    // GUI tutorial: https://github.com/MrMicky-FR/FastInv
    // Scoreboard Tutorial: https://github.com/MrMicky-FR/FastBoard
    // Commands Tutorial: https://github.com/aikar/commands/wiki/Using-ACF
    private @Getter PaperCommandManager commandManager;

    private static @Getter Chess instance;

    @Override
    public void onEnable() {
        instance = this;

        commandManager = new PaperCommandManager(this);

    }

    @Override
    public void onDisable() {

    }
    
}