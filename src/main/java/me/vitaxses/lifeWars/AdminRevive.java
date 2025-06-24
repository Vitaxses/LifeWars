package me.vitaxses.lifeWars;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminRevive implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            // IS PLAYER!
            Player player = (Player) sender;

            if (args.length == 1) {
                String playerName = args[0];
                BanList banList = Bukkit.getBanList(BanList.Type.NAME);

                if (banList.isBanned(playerName) && LifeWars.getInstance().getBannedPlayers(true).contains(playerName.toLowerCase())) {
                    unbanPlayer(playerName);
                    player.sendMessage(playerName + " has been unbanned.");
                } else {
                    player.sendMessage(playerName + " is not banned.");
                }
                return true;
            }
        } else {
            if (args.length == 1) {
            String playerName = args[0];
            BanList banList = Bukkit.getBanList(BanList.Type.NAME);

            if (banList.isBanned(playerName) && LifeWars.getInstance().getBannedPlayers(true).contains(playerName.toLowerCase())) {
                unbanPlayer(playerName);
                sender.sendMessage(playerName + " has been unbanned.");
            } else {
                sender.sendMessage(playerName + " is not banned.");
            }
            return true;
        }}
        return false;
    }


    public void unbanPlayer(String playerName) {
        LifeWars.getInstance().RemoveBannedPlayer(playerName, true);
        String command = "pardon " + playerName;
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
    }

}


