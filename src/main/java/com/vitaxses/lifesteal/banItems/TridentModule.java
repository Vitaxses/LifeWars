package com.vitaxses.lifesteal.banItems;

import com.vitaxses.lifesteal.LifeWars;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class TridentModule implements Listener {
    public boolean isEnabled() {
        return LifeWars.getInstance().getConfig().getBoolean("TridentModule");
    }

    @EventHandler
    public void interactEvent(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().getType() == Material.TRIDENT && isEnabled() && LifeWars.getInstance().combatManager.inCombat.contains(e.getPlayer().getUniqueId())) e.setCancelled(true);
    }
}
