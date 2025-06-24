package me.vitaxses.lifeWars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Eliminate implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            if (args.length == 1) {
                String playerName = args[0];
                Player player = Bukkit.getPlayer(playerName);

                if (player != null) {
                    player.banPlayer(ChatColor.RED+"Eliminated!");
                    LifeWars.getInstance().WriteToBannedPlayers(playerName);
                    sender.sendMessage(playerName + " is Eliminated.");
                } else {
                    sender.sendMessage("Player " + playerName + " not found.");
                }
            } else {
                sender.sendMessage("Usage: /eliminate <playername>");
            }

            return true;
    }

}
