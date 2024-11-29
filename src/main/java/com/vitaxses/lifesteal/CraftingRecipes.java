package com.vitaxses.lifesteal;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CraftingRecipes {

    private LifeWars main;

    public CraftingRecipes(LifeWars main) {
        this.main = main;
    }

    public void registerRecipe() {

        if (main.getConfig().getBoolean("CustomRecipes")) {

            if (main.getConfig().getBoolean("ReviveRecipe")) {

                NamespacedKey recipeKey2 = new NamespacedKey(main, "revive_recipe");
                ItemStack TotemItem = new ItemStack(Material.ENCHANTED_BOOK);
                ItemMeta TotemMeta = TotemItem.getItemMeta();
                TotemMeta.setDisplayName(ChatColor.AQUA + main.getConfig().getString("ReviveItemName") );
                TotemMeta.setLore(Arrays.asList(ChatColor.DARK_AQUA + main.getConfig().getString("ReviveItemLore")) );
                TotemMeta.setUnbreakable(true);
                TotemItem.setItemMeta(TotemMeta);

                ShapedRecipe recipe2 = new ShapedRecipe(recipeKey2, TotemItem);
                recipe2.shape("#n#", "DND", "#n#");
                ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE, 1);
                ItemMeta heartMeta = heart.getItemMeta();
                heartMeta.setDisplayName(ChatColor.BOLD + main.getConfig().getString("HeartName"));
                heartMeta.setLore(Arrays.asList(ChatColor.WHITE + main.getConfig().getString("HeartLore")));
                heart.setItemMeta(heartMeta);

                recipe2.setIngredient('#', heart);
                recipe2.setIngredient('n', Material.NETHERITE_INGOT);
                recipe2.setIngredient('D', Material.DIAMOND_BLOCK);
                recipe2.setIngredient('N', Material.NETHER_STAR);

                main.getServer().addRecipe(recipe2);
            }
            if (main.getConfig().getBoolean("HeartRecipe")) {
                //heart recipe
                NamespacedKey recipeKey4 = new NamespacedKey(main, "Heart_recipe");

                ItemStack heartresult = new ItemStack(Material.FERMENTED_SPIDER_EYE, 1);
                ItemMeta heartMetaresult = heartresult.getItemMeta();
                heartMetaresult.setDisplayName(ChatColor.BOLD + main.getConfig().getString("HeartName"));
                heartMetaresult.setLore(Arrays.asList(ChatColor.WHITE + main.getConfig().getString("HeartLore")));
                heartresult.setItemMeta(heartMetaresult);

                ShapedRecipe recipe4 = new ShapedRecipe(recipeKey4, heartresult);
                recipe4.shape("#G#", "GTG", "#G#");
                recipe4.setIngredient('#', Material.DIAMOND_BLOCK);
                recipe4.setIngredient('G', Material.GOLD_BLOCK);
                recipe4.setIngredient('T', Material.TOTEM_OF_UNDYING);

                main.getServer().addRecipe(recipe4);
            }

        }

    }
}