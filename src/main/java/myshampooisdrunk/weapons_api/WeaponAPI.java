package myshampooisdrunk.weapons_api;

import myshampooisdrunk.weapons_api.example.GoofySillyGoofyItem;
import myshampooisdrunk.weapons_api.example.HopefullyThisItemWorks;
import myshampooisdrunk.weapons_api.register.CustomItemRegistry;
import myshampooisdrunk.weapons_api.weapon.AbstractCustomItem;
import net.fabricmc.api.ModInitializer;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class WeaponAPI implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("shampoos_weapons_api");
	public static Map<Item,Set<AbstractCustomItem>> ITEMS = new HashMap<>();
	public static Map<Item,Integer> ITEM_COUNT = new HashMap<>();
	public static Map<Identifier, Pair<CraftingRecipe,AbstractCustomItem>> CUSTOM_RECIPES = new HashMap<>();
	public static Map<Identifier,Triple<Identifier,Identifier,Identifier>> RECIPE_IDS = new HashMap<>();
	@Override
	public void onInitialize() {
		AbstractCustomItem bbbbbbbbbbbbbbbbbb = new GoofySillyGoofyItem();
		AbstractCustomItem bbbbbbbbbbbbbbbbbb2 = new HopefullyThisItemWorks();
		CustomItemRegistry.registerItem(bbbbbbbbbbbbbbbbbb);
		CustomItemRegistry.registerItem(bbbbbbbbbbbbbbbbbb2);
		CustomItemRegistry.registerRecipe(new ShapelessRecipe("", CraftingRecipeCategory.MISC,
				bbbbbbbbbbbbbbbbbb.create(),
				DefaultedList.copyOf(Ingredient.EMPTY,Ingredient.ofItems(Items.STONE),Ingredient.ofItems(Items.STONE))
		),bbbbbbbbbbbbbbbbbb.getIdentifier(),bbbbbbbbbbbbbbbbbb);
		CustomItemRegistry.registerRecipe(new ShapelessRecipe("", CraftingRecipeCategory.MISC,
				bbbbbbbbbbbbbbbbbb2.create(),
				DefaultedList.copyOf(Ingredient.EMPTY,Ingredient.ofItems(Items.DEEPSLATE),Ingredient.ofItems(Items.DEEPSLATE))
		),bbbbbbbbbbbbbbbbbb2.getIdentifier(),bbbbbbbbbbbbbbbbbb2);
		for(Identifier id : CUSTOM_RECIPES.keySet()){
			RECIPE_IDS.put(id,Triple.of(
					new Identifier(id.getNamespace(),id.getPath() + "_recipe"),
					new Identifier(id.getNamespace(),id.getPath() + "_recipe_output"),
					new Identifier(id.getNamespace(),id.getPath() + "_recipe_advancement")
			));
		}
		CustomItemRegistry.addToGroup(bbbbbbbbbbbbbbbbbb2, ItemGroups.COMBAT);
		CustomItemRegistry.addToGroup(bbbbbbbbbbbbbbbbbb, ItemGroups.COMBAT);
	}
}