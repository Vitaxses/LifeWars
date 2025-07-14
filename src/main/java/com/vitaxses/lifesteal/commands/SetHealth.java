package com.vitaxses.lifesteal.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetHealth implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (strings.length == 2) {
                String targetPlayerName = strings[0];
                double healthValue;

                try {
                    healthValue = Double.parseDouble(strings[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage("Invalid health value. Please provide a valid number.");
                    return true;
                }

                Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

                if (targetPlayer != null) targetPlayer.setMaxHealth(healthValue);
                else player.sendMessage("Player not found.");

            } else {
                commandSender.sendMessage("Usage: /SetHealth <Player> <Value>");
            }
            return true;
        }
        return false;
    }
}