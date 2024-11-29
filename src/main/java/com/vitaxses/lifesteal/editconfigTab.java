package com.vitaxses.lifesteal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class editconfigTab implements TabCompleter {

    @Override
        public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

            if (args.length == 1) {
                return StringUtil.copyPartialMatches(args[0], Arrays.asList("Totems", "Trident", "TNTminecart",
                        "EndCrystal", "RespawnAnchors",
                        "Over32GoldenApples",
                        "Netherite", "NoThornsEnchantMent", "CoreLifestealMechanic",
                        "CustomRecipes", "HeartRecipe","ReviveRecipe",
                        "Use/EquipHearts","Revive",
                        "WithdrawCm","Enabled"), new ArrayList<>());
            } else if (args.length == 2) {
                return StringUtil.copyPartialMatches(args[1], Arrays.asList("true","false"), new ArrayList<>());
            }

        return null;
        }
}
