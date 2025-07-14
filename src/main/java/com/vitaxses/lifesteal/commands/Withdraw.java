package com.vitaxses.lifesteal.commands;

import com.vitaxses.lifesteal.LifeWars;
import org.apache.commons.lang3.StringUtils;
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
import org.bukkit.util.StringUtil;

import java.util.Arrays;

public class Withdraw implements CommandExecutor {

    private final LifeWars main;

    public Withdraw(LifeWars main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!main.getConfig().getBoolean("WithdrawCm")) {
            commandSender.sendMessage(ChatColor.RED + main.getConfig().getString("DisabledInConfigMsg"));
            return true;
        }

        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        int number = 1;
        if (StringUtils.isNumeric(strings[0])) {
            number = Integer.parseInt(strings[0]);
        }

        int amount = number * 2;
        int heartItem = number;

        giveItem(player, amount, heartItem);
        return true;
    }

    private void giveItem(Player player, int amount, int heartItem) {
        double originalHealth = player.getAttribute(Attribute.MAX_HEALTH).getBaseValue();
        double newHealth = originalHealth - amount;

        if (newHealth > 1) {
            player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(newHealth);

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
