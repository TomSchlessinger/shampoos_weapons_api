package myshampooisdrunk.weapons_api.example;

import myshampooisdrunk.weapons_api.weapon.AbstractCustomItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class BallerStick extends AbstractCustomItem {
    public BallerStick(String name){
        super(Items.STICK,name);
    }
    @Override
    public void onUse(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable cir){

        ItemStack item = player.getStackInHand(hand);
        player.incrementStat(Stats.USED.getOrCreateStat(item.getItem()));
        if (!player.getAbilities().creativeMode) {
            item.decrement(1);
            player.getInventory().offerOrDrop(new ItemStack(Items.BEACON));
        }
        Vec3d finalPos = player.raycast(50d,10,false).getPos();
        player.teleport(finalPos.x,finalPos.y,finalPos.z);
    }
    @Override
    public void onSneak(boolean b, PlayerEntity player, CallbackInfo ci){
        HostileEntity spawn;
        if(b){
            spawn = new ZombieEntity(EntityType.HUSK,player.getWorld());
        }else{
            spawn = new ZombieEntity(EntityType.DROWNED, player.getWorld());
        }
        spawn.setPos(player.getX(), player.getY()+5,player.getZ());
        player.getWorld().spawnEntity(spawn);
    }
}
