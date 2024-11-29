package com.vitaxses.lifesteal;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class BanItems implements Listener {

    private LifeWars main;

    public BanItems(LifeWars main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if (!main.getConfig().getBoolean("Over32GoldenApples")) {
            Player player = event.getPlayer();
            if (event.getItem().getItemStack().getType() == Material.GOLDEN_APPLE) {
                int goldenAppleCount = 0;
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item != null && item.getType() == Material.GOLDEN_APPLE) {
                        goldenAppleCount += item.getAmount();
                    }
                }

                if (goldenAppleCount >= 32) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
            Action action = event.getAction();

            if (action == Action.RIGHT_CLICK_BLOCK) {
                if (event.hasItem()) {
                    Material itemMaterial = event.getItem().getType();

                    if (itemMaterial == Material.END_CRYSTAL || itemMaterial == Material.RESPAWN_ANCHOR || itemMaterial == Material.TNT_MINECART) {
                        if (itemMaterial == Material.END_CRYSTAL && !main.getConfig().getBoolean("EndCrystal")) {
                        event.setCancelled(true);
                        } else if (itemMaterial == Material.RESPAWN_ANCHOR && !main.getConfig().getBoolean("RespawnAnchors")) {
                            event.setCancelled(true);
                        } else if (itemMaterial == Material.TNT_MINECART && !main.getConfig().getBoolean("TNTminecart")) {
                            event.setCancelled(true);
                        }
                    } else if (itemMaterial == Material.GOLDEN_APPLE && !main.getConfig().getBoolean("Over32GoldenApples")) {
                        ItemStack item = event.getItem();
                        int amount = item.getAmount();

                        if (amount > 32) {
                            item.setAmount(32);

                            Item droppedApples = event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), item);

                            droppedApples.getItemStack().setAmount(amount - 32);
                        }
                    }
                }
            }
        }

    @EventHandler
    public void tridentNoGo(PlayerPickupItemEvent event) {
        if (!main.getConfig().getBoolean("Trident")) {
            ItemStack item = event.getItem().getItemStack();
            if (item.getType() == Material.TRIDENT) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            if (!main.getConfig().getBoolean("Totems")) {
                Player player = (Player) event.getWhoClicked();
                ItemStack item = event.getCurrentItem();

                if (item != null) {

                    if (item.getType() == Material.TOTEM_OF_UNDYING) {
                        ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);
                            player.closeInventory();
                            player.getInventory().removeItem(totem);
                    }

                    if (item.getEnchantments().containsKey(Enchantment.THORNS) && main.getConfig().getBoolean("NoThornsEnchantMent")) {
                        event.setCancelled(true);
                        item.removeEnchantment(Enchantment.THORNS);
                    }
                }
            }
        }
    }


    @EventHandler
    public void onSmithingTableUse(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        ItemStack nothing = new ItemStack(Material.AIR);
        if (currentItem != null && !main.getConfig().getBoolean("Netherite")) {
            if (currentItem.equals(Material.NETHERITE_AXE) ||
                    currentItem.getType().equals(Material.NETHERITE_BOOTS) ||
                    currentItem.getType().equals(Material.NETHERITE_HOE) ||
                    currentItem.getType().equals(Material.NETHERITE_CHESTPLATE) ||
                    currentItem.getType().equals(Material.NETHERITE_HELMET) ||
                    currentItem.getType().equals(Material.NETHERITE_LEGGINGS) ||
                    currentItem.getType().equals(Material.NETHERITE_PICKAXE) ||
                    currentItem.getType().equals(Material.NETHERITE_SHOVEL) ||
                    currentItem.getType().equals(Material.NETHERITE_SWORD)) {
                event.setCancelled(true);
                event.setCurrentItem(nothing);
            }
        }
    }

}