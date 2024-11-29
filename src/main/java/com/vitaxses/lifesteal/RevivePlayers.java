package com.vitaxses.lifesteal;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import java.util.Arrays;

public class RevivePlayers implements Listener {

    private LifeWars main;

    public RevivePlayers(LifeWars main) {
        this.main = main;
    }

    @EventHandler
    public void InteractRevive(PlayerInteractEvent event) {

        if (main.getConfig().getBoolean("Revive")) {

            Player player = event.getPlayer();
            ItemStack reviveItem = event.getItem();
            Action action = event.getAction();

            if (reviveItem != null) {
                if (reviveItem.getType() == Material.ENCHANTED_BOOK) {
                    if (reviveItem.getItemMeta().getLore() != null && reviveItem.getItemMeta().getLore().equals(Arrays.asList(ChatColor.DARK_AQUA + main.getConfig().getString("ReviveItemLore") ) ) ) {
                        if (!reviveItem.getItemMeta().getDisplayName().equals(ChatColor.AQUA + main.getConfig().getString("ReviveItemName") ) ) {
                            String NameOfBookPlayer = reviveItem.getItemMeta().getDisplayName();
                            if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
                                BanList banlist = Bukkit.getBanList(BanList.Type.NAME);
                                if (banlist.isBanned(NameOfBookPlayer) && main.getBannedPlayers(true).contains(NameOfBookPlayer.toLowerCase())) {
                                    main.getLogger().info("checking if banned!");

                                    unbanPlayer(player, NameOfBookPlayer);
                                    main.getLogger().info("Success with unbanning player!");
                                    player.getItemInHand().setType(Material.AIR);
                                    reviveItem.setType(Material.AIR);
                                    player.updateInventory();

                                    int reviveMaxHealth = main.getConfig().getInt("ReviveHealth");

                                    if (reviveMaxHealth < 0) {
                                        reviveMaxHealth = 6;
                                        main.getLogger().warning("ReviveMaxHealth in config is 0 or under, please select higher value!");
                                        main.getLogger().warning("ReviveMaxHealth in config is 0 or under, please select higher value!");
                                        main.getLogger().warning("ReviveMaxHealth in config is 0 or under, please select higher value!");
                                        main.getLogger().warning("ReviveMaxHealth in config is 0 or under, please select higher value!");

                                        main.getLogger().warning("ReviveMaxHealth in config is 0 or under, please select higher value!");
                                        main.getLogger().warning("Set the ReviveMaxHealth to 6 (3 hearts)!");
                                    }

                                } else {
                                    player.sendMessage(ChatColor.RED + NameOfBookPlayer + " is not banned!");
                                }
                            }
                        }
                    } else {
                        player.sendActionBar("no bugs, please!");
                    }
                }
            }
        }
    }

    public void unbanPlayer(Player player, String playerName) {
        ItemStack nothing = new ItemStack(Material.AIR);

        String command = "pardon " + playerName;
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);

        Bukkit.broadcastMessage(ChatColor.GREEN + player.getName()+ main.getConfig().getString("ReviveAnnouncement") + ChatColor.YELLOW + playerName);
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
        player.setItemInHand(nothing);
    }

}