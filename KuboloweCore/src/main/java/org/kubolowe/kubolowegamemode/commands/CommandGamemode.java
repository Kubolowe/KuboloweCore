package org.kubolowe.kubolowegamemode.commands;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.kubolowe.kubolowegamemode.Kubolowe_Gamemode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.kubolowe.kubolowegamemode.utils.ChatFormat.colorize;

public class CommandGamemode implements CommandExecutor, TabCompleter {
    private final Kubolowe_Gamemode plugin;

    public CommandGamemode(Kubolowe_Gamemode plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;
        Player target = player;

        if (args.length == 2) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(colorize(plugin.getConfig().getString("no_found_player")));
                return false;
            }
        }

        if (args.length >= 1) {
            String gamemode;
            switch (args[0].toLowerCase()) {
                case "0":
                case "survival":
                    gamemode = plugin.getConfig().getString("variables.survival");
                    target.setGameMode(GameMode.SURVIVAL);
                    break;
                case "1":
                case "creative":
                    gamemode = plugin.getConfig().getString("variables.creative");
                    target.setGameMode(GameMode.CREATIVE);
                    break;
                case "2":
                case "adventure":
                    gamemode = plugin.getConfig().getString("variables.adventure");
                    target.setGameMode(GameMode.ADVENTURE);
                    break;
                case "3":
                case "spectator":
                    gamemode = plugin.getConfig().getString("variables.spectator");
                    target.setGameMode(GameMode.SPECTATOR);
                    break;
                default:
                    player.sendMessage(colorize("&cPoprawna komenda /gamemode [0/1/2/3/survival/creative/adventure/spectator] [gracz]"));
                    return false;
            }

            if (gamemode == null) {
                player.sendMessage(colorize("&cNie można ustawić trybu gry, ponieważ wartość jest nieprawidłowa w konfiguracji."));
                return false;
            }

            if (plugin.getConfig().getBoolean("chat")) {
                if (target.equals(player)) {
                    player.sendMessage(colorize(plugin.getConfig().getString("chat_message").replaceAll("\\{GAMEMODE\\}", gamemode)));
                } else {
                    player.sendMessage(colorize(plugin.getConfig().getString("chat_message_other_sender").replaceAll("\\{GAMEMODE\\}", gamemode).replaceAll("\\{PLAYER\\}", target.getName())));
                    target.sendMessage(colorize(plugin.getConfig().getString("chat_message_other").replaceAll("\\{GAMEMODE\\}", gamemode).replaceAll("\\{PLAYER\\}", player.getName())));
                }
            }
            if (plugin.getConfig().getBoolean("actionbar")) {
                if (target.equals(player)) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(colorize(plugin.getConfig().getString("actionbar_message").replaceAll("\\{GAMEMODE\\}", gamemode))));
                } else {
                    target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(colorize(plugin.getConfig().getString("actionbar_message_other").replaceAll("\\{GAMEMODE\\}", gamemode).replaceAll("\\{PLAYER\\}", player.getName()))));
                }
            }
            if (plugin.getConfig().getBoolean("title")) {
                if (target.equals(player)) {
                    player.sendTitle(colorize(plugin.getConfig().getString("title_message").replaceAll("\\{GAMEMODE\\}", gamemode)), colorize(plugin.getConfig().getString("title_desc_message").replaceAll("\\{GAMEMODE\\}", gamemode)), plugin.getConfig().getInt("fadein"), plugin.getConfig().getInt("stay"), plugin.getConfig().getInt("fadeout"));
                } else {
                    target.sendTitle(colorize(plugin.getConfig().getString("title_message").replaceAll("\\{GAMEMODE\\}", gamemode)), colorize(plugin.getConfig().getString("title_desc_message_other").replaceAll("\\{GAMEMODE\\}", gamemode).replaceAll("\\{PLAYER\\}", player.getName())), plugin.getConfig().getInt("fadein"), plugin.getConfig().getInt("stay"), plugin.getConfig().getInt("fadeout"));
                }
            }
            return true;
        } else {
            player.sendMessage(colorize("&cPoprawna komenda /gamemode [0/1/2/3/survival/creative/adventure/spectator] [player]"));
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("0");
            completions.add("1");
            completions.add("2");
            completions.add("3");
            completions.add("survival");
            completions.add("creative");
            completions.add("adventure");
            completions.add("spectator");
        } else if (args.length == 2) {
            List<String> playerNames = Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
            for (String playerName : playerNames) {
                if (playerName.toLowerCase().startsWith(args[1].toLowerCase())) {
                    completions.add(playerName);
                }
            }
        }

        return completions;
    }
}
