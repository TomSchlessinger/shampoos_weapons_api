package myshampooisdrunk.weapons_api.register;

import myshampooisdrunk.weapons_api.WeaponAPI;
import myshampooisdrunk.weapons_api.weapon.AbstractCustomItem;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Map;

public class CustomItemRegistry {

    public static void registerItem(AbstractCustomItem item){
        WeaponAPI.ITEMS.computeIfAbsent(item.getItem(), k -> new HashSet<>());
        WeaponAPI.ITEMS.get(item.getItem()).add(item);
    }
    public static void registerRecipe(CraftingRecipe r,Identifier id){
        WeaponAPI.CUSTOM_RECIPES.put(id, r);
    }
}
