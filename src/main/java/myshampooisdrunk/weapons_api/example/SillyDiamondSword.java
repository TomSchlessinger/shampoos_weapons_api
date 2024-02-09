package myshampooisdrunk.weapons_api.example;

import myshampooisdrunk.weapons_api.weapon.AbstractCustomItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class SillyDiamondSword extends AbstractCustomItem {
    public SillyDiamondSword() {
        super(Items.DIAMOND_SWORD);
    }
    @Override
    public void onUse(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable cir) {
        Vec3d look = player.getRotationVector();
        float power = 3f;
        player.setVelocity(look.x*power,look.y*power,look.z*power);
    }
    @Override
    public void onAttack(Entity target, CallbackInfo ci){
        target.teleport(target.getX(),target.getY()-1,target.getZ());
    }
    @Override
    public void whileSneak(PlayerEntity p, CallbackInfo ci){
        p.getInventory().damageArmor(p.getDamageSources().playerAttack(p),1f,new int[]{0, 2});
    }
    @Override
    public void onClick(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player, CallbackInfoReturnable<Boolean> cir){
        player.setVelocity(0,10,0);
    }
    @Override
    public void onDrop(PlayerEntity p, ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfo ci){
        p.damage(p.getDamageSources().badRespawnPoint(Vec3d.ZERO),10);
    }
}
