package myshampooisdrunk.weapons_api.register;

import myshampooisdrunk.weapons_api.WeaponAPI;
import myshampooisdrunk.weapons_api.weapon.AbstractCustomItem;

import java.util.HashSet;

public class CustomItemRegistry {
    public static void registerItem(AbstractCustomItem item){
        WeaponAPI.ITEMS.computeIfAbsent(item.getItem(), k -> new HashSet<>());
        WeaponAPI.ITEMS.get(item.getItem()).add(item);
    }
}
