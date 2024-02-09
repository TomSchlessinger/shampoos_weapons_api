package myshampooisdrunk.weapons_api;

import myshampooisdrunk.weapons_api.example.BallerStick;
import myshampooisdrunk.weapons_api.example.SillyDiamondSword;
import myshampooisdrunk.weapons_api.register.CustomItemRegistry;
import myshampooisdrunk.weapons_api.weapon.AbstractCustomItem;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
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
	}
}