package com.vitaxses.lifesteal;

import com.vitaxses.lifesteal.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class LifeWars extends JavaPlugin {

    private static LifeWars Instance;

    private static File bannedPlayers;
    public List<String> getBannedPlayers(boolean toLower) {
        List<String> s = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(bannedPlayers))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (toLower) s.add(line.toLowerCase());
                else s.add(line);
            }
        } catch (IOException e) {
            getLogger().info("Error Reading BannedPlayers.txt");
        }
        return s;
    }
    public void WriteToBannedPlayers(String s) {
        try {
            FileWriter writer = new FileWriter(bannedPlayers, true);
            writer.write(s);
        } catch (IOException e) {
            getLogger().info("Error Writing To BannedPlayers.txt");
        }
    }
    public void RemoveBannedPlayer(String s, boolean toLower) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(bannedPlayers.toURI()));

            if (toLower) {
                if (lines.removeIf(line -> line.toLowerCase().equals(s.toLowerCase()))) {
                    Files.write(Paths.get(bannedPlayers.toURI()), lines);
                }
            } else if (lines.removeIf(line -> line.equals(s))) {
                Files.write(Paths.get(bannedPlayers.toURI()), lines);
            }
        } catch (IOException e) {
            getLogger().info("Error While Removing Banned Player From BannedPlayers.txt");
        }
    }

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Instance = this;

        bannedPlayers = new File(getDataFolder(), "BannedPlayers.txt");
        if (!bannedPlayers.exists()) {
            try {
                bannedPlayers.createNewFile();
            } catch (IOException e) {
                getLogger().info("Error While Creating BannedPlayers.txt");
                throw new RuntimeException(e);
            }
        }


        getCommand("reloadconfig").setExecutor(new RLConfig(this));
        if (getConfig().getBoolean("Enabled")) {
            CraftingRecipes customRecipeHandler = new CraftingRecipes(this);
            customRecipeHandler.registerRecipe();
            getServer().getPluginManager().registerEvents(new CoreLifesteal(this), this);
            getServer().getPluginManager().registerEvents(new BanItems(this), this);
            getServer().getPluginManager().registerEvents(new RevivePlayers(this), this);
            getCommand("withdraw").setExecutor(new Withdraw(this));
            getCommand("eliminate").setExecutor(new Eliminate());
            getCommand("adminrevive").setExecutor(new AdminRevive());
            getCommand("sethealth").setExecutor(new SetHealth() );
            getCommand("editconfig").setExecutor(new EditConfigCm(this));

            getCommand("editconfig").setTabCompleter(new editconfigTab());

        }

    }

    public static LifeWars getInstance() {
        return Instance;
    }

}
