package org.kubolowe.kubolowegamemode.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kubolowe.kubolowegamemode.Kubolowe_Gamemode;
import org.kubolowe.kubolowegamemode.utils.ChatFormat;

public class CommandReload implements CommandExecutor {
    private final Kubolowe_Gamemode plugin;

    public CommandReload(Kubolowe_Gamemode plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if(args.length == 0) {
            player.sendMessage(ChatFormat.colorize("&cUsage: /kgamemode reload"));
        }
        if (args[0].equals("reload")) {
            plugin.reloadConfig();
            player.sendMessage(ChatFormat.colorize("&aKonfiguracja została przeładowana!"));

        }
        return false;
    }
}
