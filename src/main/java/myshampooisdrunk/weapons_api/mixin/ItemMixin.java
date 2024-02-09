package myshampooisdrunk.weapons_api.mixin;

import myshampooisdrunk.weapons_api.WeaponAPI;
import myshampooisdrunk.weapons_api.weapon.AbstractCustomItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Item.class)
public abstract class ItemMixin{

	@Inject(at = @At("HEAD"), method = "use")
	private void useItem(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		ItemStack item = user.getStackInHand(hand);
		if(WeaponAPI.ITEMS.containsKey(item.getItem())){
			for(AbstractCustomItem custom : WeaponAPI.ITEMS.get(item.getItem())){
				if(custom.getItem().equals(item.getItem()) && item.getOrCreateNbt().getInt("CustomModelData") == custom.getId()){
					custom.onUse(world, user, hand,cir);
					break;
				}
			}
		}

	}

}