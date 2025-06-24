package me.vitaxses.lifeWars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RLConfig implements CommandExecutor {
    private LifeWars main;

    public RLConfig(LifeWars main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        main.saveDefaultConfig();
        main.reloadConfig();
        commandSender.sendMessage("Reloaded Config!");

        return true;
    }
}
