package com.vitaxses.lifesteal.banItems;

import com.vitaxses.lifesteal.LifeWars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class NetheriteMeleeModule implements Listener {

    public boolean isEnabled() {
        return LifeWars.getInstance().getConfig().getBoolean("NetheriteMeleeModule");
    }

    public NetheriteMeleeModule() {
        Bukkit.getPluginManager().registerEvents(this, LifeWars.getInstance());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!isEnabled()) return;
        handleWeapons(event.getPlayer());
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!isEnabled()) return;
        if (event.getPlayer() instanceof Player player) {
            handleWeapons(player);
        }
    }

    private void handleWeapons(Player player) {
        PlayerInventory inv = player.getInventory();

        ItemStack[] contents = inv.getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item == null) continue;

            Material type = item.getType();
            if (type == Material.NETHERITE_SWORD || type == Material.NETHERITE_AXE) {
                int emptySlot = inv.firstEmpty();
                if (emptySlot != -1 && emptySlot != i) {
                    inv.setItem(emptySlot, item);
                    inv.setItem(i, null);
                } else {
                    Item item1 = player.getWorld().dropItemNaturally(player.getLocation(), item);
                    item1.setCanMobPickup(false);
                    item1.setPickupDelay(0);
                    item1.setCanPlayerPickup(true);
                    item1.setGlowing(true);
                    item1.setInvulnerable(true);
                    inv.setItem(i, null);
                    player.sendMessage("§cNetherite melee weapons are disabled and have been dropped!");
                }
            }
        }
    }
}
