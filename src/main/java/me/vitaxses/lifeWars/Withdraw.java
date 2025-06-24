package me.vitaxses.lifeWars;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Withdraw implements CommandExecutor {

    private LifeWars main;

    public Withdraw(LifeWars main) {
        this.main = main;
    }


    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (main.getConfig().getBoolean("WithdrawCm")) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                double originalHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                double newHealth = originalHealth - 2.0;

                if (newHealth > 1) {
                    int number = 1;
                    int amount;
                    int heartItem;

                    if (strings.length == 1) {
                        try {
                            number = Integer.parseInt(strings[0]);
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.RED + "Invalid number.");
                        }
                    }

                    switch (number) {
                        case 1:
                            amount = 2;
                            heartItem = 1;
                            giveItem(player, amount, heartItem);
                            break;
                        case 2:
                            amount = 4;
                            heartItem = 2;
                            giveItem(player, amount, heartItem);
                            break;
                        case 3:
                            amount = 6;
                            heartItem = 3;
                            giveItem(player, amount, heartItem);
                            break;
                        case 4:
                            amount = 8;
                            heartItem = 4;
                            giveItem(player, amount, heartItem);
                            break;
                        case 5:
                            amount = 10;
                            heartItem = 5;
                            giveItem(player, amount, heartItem);
                            break;
                        case 6:
                            amount = 12;
                            heartItem = 6;
                            giveItem(player, amount, heartItem);
                            break;
                        case 7:
                            amount = 14;
                            heartItem = 7;
                            giveItem(player, amount, heartItem);
                            break;
                        case 8:
                            amount = 16;
                            heartItem = 8;
                            giveItem(player, amount, heartItem);
                            break;
                        case 9:
                            amount = 18;
                            heartItem = 9;
                            giveItem(player, amount, heartItem);
                            break;
                        case 10:
                            amount = 20;
                            heartItem = 10;
                            giveItem(player, amount, heartItem);
                            break;
                        default:
                            amount = 2;
                            heartItem = 1;
                            giveItem(player, amount, heartItem);
                            break;
                    }
                } else {
                    player.sendMessage(ChatColor.RED + main.getConfig().getString("YouDontHaveEnoughHearts"));
                }
            }

            return true;
        } else {commandSender.sendMessage(main.getConfig().getString("DisabledInConfigMsg"));}
        return false;
    }

    private void giveItem(Player player, int amount, int heartItem) {
        double originalHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double newHealth = originalHealth - amount;

        if (newHealth > 1) {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newHealth);
            ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE, heartItem);
            ItemMeta heartMeta = heart.getItemMeta();
            heartMeta.setDisplayName(ChatColor.BOLD + main.getConfig().getString("HeartName"));
            heartMeta.setLore(Arrays.asList(ChatColor.WHITE + main.getConfig().getString("HeartLore")));
            heart.setItemMeta(heartMeta);

            player.getInventory().addItem(heart);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1.5f, 1.5f);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1.5f, 1.5f);
            player.sendMessage(ChatColor.GREEN + main.getConfig().getString("SuccessWithDraw") + heartItem + " " + ChatColor.BOLD + "Heart(s)");
        } else {
            player.sendMessage(ChatColor.RED + main.getConfig().getString("YouDontHaveEnoughHearts"));
        }
    }

}

