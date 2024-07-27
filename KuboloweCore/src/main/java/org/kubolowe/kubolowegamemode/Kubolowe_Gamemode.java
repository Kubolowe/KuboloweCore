package org.kubolowe.kubolowegamemode;

import org.bukkit.plugin.java.JavaPlugin;
import org.kubolowe.kubolowegamemode.commands.CommandReload;
import org.kubolowe.kubolowegamemode.commands.CommandGamemode;

public final class Kubolowe_Gamemode extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("gamemode").setExecutor(new CommandGamemode(this));
        this.getCommand("gamemode").setTabCompleter(new CommandGamemode(this));
        this.getCommand("agamemode").setExecutor(new CommandReload(this));
        saveDefaultConfig();
        getLogger().info("Kubolowe Gameode został włączony");
    }

    @Override
    public void onDisable() {
        getLogger().info("Kubolowe Gameode został wyłączony");
    }
}
