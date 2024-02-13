package myshampooisdrunk.weapons_api;

import myshampooisdrunk.weapons_api.example.GoofySillyGoofyItem;
import myshampooisdrunk.weapons_api.register.CustomItemRegistry;
import myshampooisdrunk.weapons_api.weapon.AbstractCustomItem;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class WeaponAPI implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("shampoos_weapons_api");
	public static Map<Item,Set<AbstractCustomItem>> ITEMS = new HashMap<>();
	public static Map<Item,Integer> ITEM_COUNT = new HashMap<>();
	public static Map<Identifier,CraftingRecipe> CUSTOM_RECIPES = new HashMap<>();
	@Override
	public void onInitialize() {
		AbstractCustomItem bbbbbbbbbbbbbbbbbb = new GoofySillyGoofyItem();
		CustomItemRegistry.registerItem(bbbbbbbbbbbbbbbbbb);
		//no way to implement recipes using mod; you must use datatpakc
	}
}