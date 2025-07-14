package com.vitaxses.lifesteal.banItems;

import com.vitaxses.lifesteal.LifeWars;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CombatManager implements Listener {
    public final Set<UUID> inCombat = new HashSet<>();
    private final Set<UUID> playersToPunish = new HashSet<>();

    public void setupListeners(LifeWars instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
        instance.getServer().getPluginManager().registerEvents(new IronPieceModule(), instance);
        instance.getServer().getPluginManager().registerEvents(new Lvl2PotsModule(), instance);
        instance.getServer().getPluginManager().registerEvents(new PearlModule(), instance);
        instance.getServer().getPluginManager().registerEvents(new ElytraModule(), instance);
        instance.getServer().getPluginManager().registerEvents(new TridentModule(), instance);
        instance.getServer().getPluginManager().registerEvents(new NetheriteArmorModule(), instance);
        instance.getServer().getPluginManager().registerEvents(new NetheriteMeleeModule(), instance);
    }

    @EventHandler
    public void leaveEvent(PlayerQuitEvent e) {
        if (!inCombat.contains(e.getPlayer().getUniqueId())) return;

        e.setQuitMessage(ChatColor.DARK_RED + (ChatColor.BOLD + e.getPlayer().getName() +" has just disconnected while in combat and has 5 minutes to join back or they loose a heart (unless at 1)"));
        OfflinePlayer player = e.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isConnected()) {
                    this.cancel();
                    return;
                }
                playersToPunish.add(player.getUniqueId());
            }
        }.runTaskLater(LifeWars.getInstance(), 6000);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player attacker && e.getEntity() instanceof Player victim) {
            inCombat.remove(victim.getUniqueId());
            inCombat.add(victim.getUniqueId());

            inCombat.remove(attacker.getUniqueId());
            inCombat.add(attacker.getUniqueId());

            new BukkitRunnable() {
                @Override
                public void run() {
                    inCombat.remove(attacker.getUniqueId());
                    inCombat.remove(victim.getUniqueId());
                }
            }.runTaskLater(LifeWars.getInstance(), LifeWars.getInstance().getConfig().getLong("CombatTimer"));


            if (!LifeWars.getInstance().getConfig().getBoolean("Lvl2PotsModule")) return;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!inCombat.contains(victim.getUniqueId()) || !inCombat.contains(attacker.getUniqueId())) {
                        this.cancel();
                    }

                    if (victim.hasPotionEffect(PotionEffectType.STRENGTH) && victim.getPotionEffect(PotionEffectType.STRENGTH).getAmplifier() > 0) {
                        victim.removePotionEffect(PotionEffectType.STRENGTH);
                    }
                    if (victim.hasPotionEffect(PotionEffectType.SPEED) && victim.getPotionEffect(PotionEffectType.SPEED).getAmplifier() > 0) {
                        victim.removePotionEffect(PotionEffectType.SPEED);
                    }

                    if (attacker.hasPotionEffect(PotionEffectType.STRENGTH) && attacker.getPotionEffect(PotionEffectType.STRENGTH).getAmplifier() > 0) {
                        attacker.removePotionEffect(PotionEffectType.STRENGTH);
                    }
                    if (attacker.hasPotionEffect(PotionEffectType.SPEED) && attacker.getPotionEffect(PotionEffectType.SPEED).getAmplifier() > 0) {
                        attacker.removePotionEffect(PotionEffectType.SPEED);
                    }
                }
            }.runTaskTimer(LifeWars.getInstance(), 20, 100L);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        inCombat.remove(e.getPlayer().getUniqueId());
        if (e.getPlayer().getKiller() == null) return;
        inCombat.remove(e.getPlayer().getKiller().getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!playersToPunish.contains(e.getPlayer().getUniqueId())) return;
        double health = e.getPlayer().getAttribute(Attribute.MAX_HEALTH).getBaseValue();
        if (health > 3) {
            e.setJoinMessage(ChatColor.DARK_RED + e.getPlayer().getName() + " has just joined after combat logging and have lost 1 heart!");
            e.getPlayer().getAttribute(Attribute.MAX_HEALTH).setBaseValue(health - 2d);
        }
        playersToPunish.remove(e.getPlayer().getUniqueId());
    }

}
