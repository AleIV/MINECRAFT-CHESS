package net.noobsters.core.paper;

import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;


public class Chess extends JavaPlugin{

    private @Getter PaperCommandManager commandManager;
    private @Getter ChessManager chessManager;

    private static @Getter Chess instance;

    @Override
    public void onEnable() {
        instance = this;

        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new ChessCMD(this));
        chessManager = new ChessManager(this);

    }

    @Override
    public void onDisable() {

    }
    
}