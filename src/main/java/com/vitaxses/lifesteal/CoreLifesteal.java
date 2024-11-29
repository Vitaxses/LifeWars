package com.vitaxses.lifesteal;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Collections;

public class CoreLifesteal implements Listener {

    private LifeWars main;

    public CoreLifesteal(LifeWars main) {
        this.main = main;
    }

    boolean shouldMinus = true;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        if (main.getConfig().getBoolean("CoreLifestealMechanic")) {
            Player player = event.getEntity();
            Player killer = player.getKiller();

            if (killer != null) {
                double originalHealthKiller = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                double newHealthKiller = originalHealthKiller + 2.0;

                if (newHealthKiller > 40) {
                    newHealthKiller = 40;
                    givePlayerHeart(killer);
                }

                setPlayerMaxHealth(killer, newHealthKiller);
            }

            double originalHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            double newHealth = originalHealth - 2.0;

            if (newHealth <= 1) {
                newHealth = 0;
                handlePlayerDeath(player);
            }

            setPlayerMaxHealth(player, newHealth);
        }

    }
        private void givePlayerHeart (Player player){
            ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
            ItemMeta heartMeta = heart.getItemMeta();
            heartMeta.setDisplayName(ChatColor.BOLD + main.getConfig().getString("HeartName"));
            heartMeta.setLore(Collections.singletonList(ChatColor.WHITE + main.getConfig().getString("HeartLore")));
            heart.setItemMeta(heartMeta);
            player.getInventory().addItem(heart);
        }

        private void setPlayerMaxHealth(Player player,double health){
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        }

        private void handlePlayerDeath(Player player){
            int reviveMaxHealth = main.getConfig().getInt("ReviveHealth");
            player.getInventory().clear();
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(reviveMaxHealth);
            player.banPlayer(ChatColor.RED + main.getConfig().getString("EliminatedBanMsg"), ChatColor.DARK_RED +  main.getConfig().getString("EliminatedBanMsgAfter1"));
        }



    @EventHandler
    public void HeartEquip(PlayerInteractEvent event) {
        shouldMinus = true;
        if (main.getConfig().getBoolean("Use/EquipHearts")) {
            Player player = event.getPlayer();
            ItemStack Nothing = new ItemStack(Material.AIR);

            Action action = event.getAction();
            if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
                if (player.getInventory().getItemInMainHand().getType() == Material.FERMENTED_SPIDER_EYE) {
                    ItemStack item = new ItemStack(player.getInventory().getItemInMainHand());
                    if (item.getItemMeta().getDisplayName().equals(ChatColor.BOLD + main.getConfig().getString("HeartName"))) {
                        double killerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                        double newKillerMaxHealth = killerMaxHealth + 2;

                        int maxHealthCap = main.getConfig().getInt("MaxHealthPoints");


                        if (newKillerMaxHealth > maxHealthCap) {
                            newKillerMaxHealth = maxHealthCap;
                            int maxHealthCapHalf = maxHealthCap / 2;
                            shouldMinus = false;
                            player.sendMessage(ChatColor.RED+ main.getConfig().getString("YouCantGoOverMaxHearts") + ChatColor.GOLD + maxHealthCapHalf + ChatColor.RED + main.getConfig().getString("YouCantGoOverMaxHearts2"));
                        }



                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newKillerMaxHealth);
                        if (shouldMinus) {
                            int minusOne = player.getInventory().getItemInHand().getAmount();

                            double MinusOne = minusOne - 1;
                            player.getInventory().getItemInMainHand().setAmount((int) MinusOne);
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 0.5f, 0.5f);
                            player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.5f, 1.5f);

                        }
                    }
                    }
                }
            }
        }
    }