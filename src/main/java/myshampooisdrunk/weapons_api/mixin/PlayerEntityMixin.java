package myshampooisdrunk.weapons_api.mixin;

import myshampooisdrunk.weapons_api.WeaponAPI;
import myshampooisdrunk.weapons_api.weapon.AbstractCustomItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At("HEAD"),method="attack")
    private void attack(Entity target, CallbackInfo ci) {
        PlayerEntity user = (PlayerEntity)(Object)this;
        ItemStack item = user.getStackInHand(Hand.MAIN_HAND);
        if(WeaponAPI.ITEMS.containsKey(item.getItem())) {
            for (AbstractCustomItem custom : WeaponAPI.ITEMS.get(item.getItem())) {
                if (custom.getItem().equals(item.getItem()) && item.getOrCreateNbt().getInt("CustomModelData") == custom.getId()) {
                    custom.onAttack(target, ci);
                    break;
                }
            }
        }
    }
    @Inject(at=@At("HEAD"),method="dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;")
    private void dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir){
        if(WeaponAPI.ITEMS.containsKey(stack.getItem())) {
            PlayerEntity user = (PlayerEntity) (Object) this;
            for (AbstractCustomItem custom : WeaponAPI.ITEMS.get(stack.getItem())) {
                if (custom.getItem().equals(stack.getItem()) && stack.getOrCreateNbt().getInt("CustomModelData") == custom.getId()) {
                    custom.onDrop(user, stack, throwRandomly, retainOwnership, cir);
                    break;
                }
            }
        }
    }
    @Inject(at=@At("HEAD"),method="tick")
    public void whileSneaking(CallbackInfo ci){
        if(this.isSneaking()){
            PlayerEntity user = (PlayerEntity)(Object)this;
            ItemStack item = user.getStackInHand(Hand.MAIN_HAND);
            if(WeaponAPI.ITEMS.containsKey(item.getItem())) {
                for (AbstractCustomItem custom : WeaponAPI.ITEMS.get(item.getItem())) {
                    if (custom.getItem().equals(item.getItem()) && item.getOrCreateNbt().getInt("CustomModelData") == custom.getId()) {
                        custom.whileSneak((PlayerEntity) (Object) this, ci);
                        break;
                    }
                }
            }
        }
    }

}
