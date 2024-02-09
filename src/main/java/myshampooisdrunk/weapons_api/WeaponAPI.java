package myshampooisdrunk.weapons_api;

import myshampooisdrunk.weapons_api.example.BallerStick;
import myshampooisdrunk.weapons_api.example.SillyDiamondSword;
import myshampooisdrunk.weapons_api.register.CustomItemRegistry;
import myshampooisdrunk.weapons_api.weapon.AbstractCustomItem;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RawShapedRecipe;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class WeaponAPI implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("shampoos_weapons_api");
	public static Map<Item,Set<AbstractCustomItem>> ITEMS = new HashMap<>();
	public static Map<Item,Integer> ITEM_COUNT = new HashMap<>();
	@Override
	public void onInitialize() {
		AbstractCustomItem bozoSword = new SillyDiamondSword();
		AbstractCustomItem ballerStick = new BallerStick("silly_weapon.silly_weapon.silly_weapon");
		CustomItemRegistry.registerItem(bozoSword);
		CustomItemRegistry.registerItem(ballerStick);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
			content.add(bozoSword.create());
			content.add(ballerStick.create());
		});
		//Map.of(
		// Character.valueOf('#'), Ingredient.ofItems(Items.PAPER),
		// Character.valueOf('x'), Ingredient.ofItems(Items.FILLED_MAP)),
		// "###", "#x#", "###")
		ShapedRecipe bozoRecipe = new ShapedRecipe("", CraftingRecipeCategory.EQUIPMENT,
				RawShapedRecipe.create(Map.of(
								'd', Ingredient.ofItems(Items.DIAMOND_BLOCK),
								'n', Ingredient.ofItems(Items.NETHERITE_BLOCK)),
						"dnd","ndn","dnd"
				),bozoSword.create());
		ShapedRecipe bitchRecipe = new ShapedRecipe("", CraftingRecipeCategory.MISC,
				RawShapedRecipe.create(Map.of(
								'd', Ingredient.ofItems(Items.DIAMOND),
								'n', Ingredient.ofItems(Items.NETHERITE_INGOT)),
						"dnd","ndn","dnd"
				),ballerStick.create());
		ShapelessRecipe FUCKYOUrecipe = new ShapelessRecipe("",CraftingRecipeCategory.REDSTONE,ballerStick.create(),
				DefaultedList.copyOf(Ingredient.ofItems(Items.ACACIA_BOAT),Ingredient.ofItems(Items.ANDESITE_WALL)));
		CustomItemRegistry.registerRecipe(bozoRecipe,new Identifier("example_mod","bozo_recipe"));
		CustomItemRegistry.registerRecipe(bitchRecipe,new Identifier("example_mod","bitch_recipe"));
		CustomItemRegistry.registerRecipe(FUCKYOUrecipe,new Identifier("example_mod","FUCK_YOU_FUCK_YOU"));
	}
}