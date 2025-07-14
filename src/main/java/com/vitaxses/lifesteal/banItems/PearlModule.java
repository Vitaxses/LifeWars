package com.vitaxses.lifesteal.banItems;

import com.vitaxses.lifesteal.LifeWars;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PearlModule implements Listener {
    public boolean isEnabled() {
        return LifeWars.getInstance().getConfig().getBoolean("PearlUseModule");
    }
    @EventHandler
    public void interactEvent(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().getType() == Material.ENDER_PEARL &&  isEnabled()
                && LifeWars.getInstance().combatManager.inCombat.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
