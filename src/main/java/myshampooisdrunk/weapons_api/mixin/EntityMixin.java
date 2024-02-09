package myshampooisdrunk.weapons_api.mixin;

import myshampooisdrunk.weapons_api.WeaponAPI;
import myshampooisdrunk.weapons_api.weapon.AbstractCustomItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Entity.class)
public class EntityMixin {
    @Inject(at=@At("HEAD"),method = "setSneaking")
    public void onSneak(boolean sneaking, CallbackInfo ci){
        if((Entity)(Object)this instanceof PlayerEntity p){
            ItemStack item = p.getStackInHand(Hand.MAIN_HAND);
            if(WeaponAPI.ITEMS.containsKey(item.getItem())) {
                for (AbstractCustomItem custom : WeaponAPI.ITEMS.get(item.getItem())) {
                    if (custom.getItem().equals(item.getItem()) && item.getOrCreateNbt().getInt("CustomModelData") == custom.getId()) {
                        custom.onSneak(sneaking, p, ci);
                        break;
                    }
                }
            }
        }
    }

}
