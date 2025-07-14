package com.vitaxses.lifesteal.banItems;

import com.vitaxses.lifesteal.LifeWars;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class Lvl2PotsModule implements Listener {
    public boolean isEnabled() {
        return LifeWars.getInstance().getConfig().getBoolean("Lvl2PotsModule");
    }

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        if (e.getItem() == null || !isEnabled()) return;
        if (e.getItem().getType() != Material.SPLASH_POTION && e.getItem().getType() != Material.POTION) return;
        PotionMeta meta = (PotionMeta) e.getItem().getItemMeta();
        if (meta.getBasePotionType() == PotionType.STRONG_STRENGTH || meta.getBasePotionType() == PotionType.STRONG_SWIFTNESS) e.setCancelled(true);
    }
}
