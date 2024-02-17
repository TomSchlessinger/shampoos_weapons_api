package myshampooisdrunk.weapons_api.register;

import myshampooisdrunk.weapons_api.WeaponAPI;
import myshampooisdrunk.weapons_api.weapon.AbstractCustomItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.HashSet;

public class CustomItemRegistry {

    public static void registerItem(AbstractCustomItem item){
        WeaponAPI.ITEMS.computeIfAbsent(item.getItem(), k -> new HashSet<>());
        WeaponAPI.ITEMS.get(item.getItem()).add(item);
    }
    public static void addToGroup(AbstractCustomItem item, RegistryKey<ItemGroup> g){
        ItemGroupEvents.modifyEntriesEvent(g).register(content -> {
            content.add(item.create());
        });
    }public static void addToGroup(AbstractCustomItem item, RegistryKey<ItemGroup> g, ItemStack after){
        ItemGroupEvents.modifyEntriesEvent(g).register(content -> {
            content.addAfter(after, item.create());
        });
    }
    public static void registerRecipe(CraftingRecipe r, Identifier id, AbstractCustomItem item){
        WeaponAPI.CUSTOM_RECIPES.put(id, new Pair<>(r,item));
    }
}
