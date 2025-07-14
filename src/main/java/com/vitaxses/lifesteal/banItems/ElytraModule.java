package com.vitaxses.lifesteal.banItems;

import com.vitaxses.lifesteal.LifeWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class ElytraModule implements Listener {
    public boolean isEnabled() {
        return LifeWars.getInstance().getConfig().getBoolean("ElytraUseModule");
    }

    @EventHandler
    public void event(EntityToggleGlideEvent e) {
        if (e.isGliding() && isEnabled() && LifeWars.getInstance().combatManager.inCombat.contains(e.getEntity().getUniqueId())) e.setCancelled(true);
    }
}
