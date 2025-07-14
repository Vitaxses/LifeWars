package com.vitaxses.lifesteal.banItems;

import com.vitaxses.lifesteal.LifeWars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class NetheriteArmorModule implements Listener {

    public boolean isEnabled() {
        return LifeWars.getInstance().getConfig().getBoolean("NetheriteArmorModule");
    }

    public NetheriteArmorModule() {
        Bukkit.getPluginManager().registerEvents(this, LifeWars.getInstance());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!isEnabled()) return;
        handleArmor(event.getPlayer());
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!isEnabled()) return;
        if (event.getPlayer() instanceof Player player) {
            handleArmor(player);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!isEnabled()) return;
        if (event.getPlayer() instanceof Player player) {
            handleArmor(player);
        }
    }

    private void handleArmor(Player player) {
        PlayerInventory inv = player.getInventory();

        ItemStack[] armor = {inv.getHelmet(), inv.getChestplate(), inv.getLeggings(), inv.getBoots()};
        for (int i = 0; i < armor.length; i++) {
            ItemStack item = armor[i];
            if (item != null && item.getType().name().startsWith("NETHERITE_")) {
                int emptySlot = inv.firstEmpty();
                if (emptySlot != -1) {
                    inv.setItem(emptySlot, item);
                    removeArmorPiece(inv, i);
                } else {
                    Item item1 = player.getWorld().dropItemNaturally(player.getLocation(), item);
                    item1.setCanMobPickup(false);
                    item1.setPickupDelay(0);
                    item1.setCanPlayerPickup(true);
                    item1.setGlowing(true);
                    item1.setInvulnerable(true);
                    removeArmorPiece(inv, i);
                    player.sendMessage("§cNetherite armor is disabled and you have no room in your inventory!");
                }
            }
        }
    }

    private void removeArmorPiece(PlayerInventory inv, int index) {
        switch (index) {
            case 0 -> inv.setHelmet(null);
            case 1 -> inv.setChestplate(null);
            case 2 -> inv.setLeggings(null);
            case 3 -> inv.setBoots(null);
        }
    }

}
