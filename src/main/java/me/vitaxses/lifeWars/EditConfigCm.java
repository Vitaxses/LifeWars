package me.vitaxses.lifeWars;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditConfigCm implements CommandExecutor {
    private final LifeWars plugin;

    public EditConfigCm(LifeWars plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 2) {
                String configKey = args[0];
                boolean value;

                if (args[1].equalsIgnoreCase("true")) {
                    value = true;
                } else if (args[1].equalsIgnoreCase("false")) {
                    value = false;
                } else {
                    sender.sendMessage(ChatColor.RED+"Invalid value. Use '"+ ChatColor.GREEN +"true"+ ChatColor.RED +"' or '"+ ChatColor.DARK_RED +"false"+ ChatColor.RED +"'.");
                    return true;
                }

                if (plugin.getConfig().contains(configKey)) {

                    plugin.getConfig().set(configKey, value);
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.GREEN+"Config value '" + configKey + "' set to " + value);
                }
            } else {
                sender.sendMessage(ChatColor.RED+"Usage: /editconfig <configKey> <true|false>");
            }

            return true;
        } else {
            if (args.length == 2) {
            String configKey = args[0];
            boolean value;

            if (args[1].equalsIgnoreCase("true")) {
                value = true;
            } else if (args[1].equalsIgnoreCase("false")) {
                value = false;
            } else {
                sender.sendMessage("Invalid value. Use 'true' or 'false'.");
                return true;
            }

            if (plugin.getConfig().contains(configKey)) {

                plugin.getConfig().set(configKey, value);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage("Config value '" + configKey + "' set to " + value);
                return true;
            }
        } else {
            sender.sendMessage("Usage: /editconfig <configKey> <true|false>");
            }
        }
        return false;
    }
}
