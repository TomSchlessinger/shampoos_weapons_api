package myshampooisdrunk.weapons_api.example;

import myshampooisdrunk.weapons_api.weapon.AbstractCustomItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

public class HopefullyThisItemWorks extends AbstractCustomItem {
    public HopefullyThisItemWorks() {
        super(Items.BONE,new Identifier("shampoos_weapons_api","greatest_programmer_ever"),"if.this.breaks.i.will.cry");
    }

    @Override
    public void onUse(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable cir) {
//        Item item = player.getStackInHand(hand).getItem();
//        NbtList list = EnchantedBookItem.getEnchantmentNbt(player.getStackInHand(hand));
//        for(int i = 0; i < list.size(); i++ ){
//            NbtCompound nbtCompound = list.getCompound(i);
//            Identifier identifier2 = EnchantmentHelper.getIdFromNbt(nbtCompound);
//            assert identifier2 != null;
//            Vec3d playerPos = player.getPos();
//            Vec3d pos = player.raycast(50,1,true).getPos();
//            Vec3d vel = player.getVelocity();
//            Vec3d look = player.getRotationVector();
//            if(identifier2.equals(EnchantmentHelper.getEnchantmentId(Enchantments.BLAST_PROTECTION))){
//                world.createExplosion(player, world.getDamageSources().explosion(player,player),null,pos, (float) (player.squaredDistanceTo(pos)/25f),true, World.ExplosionSourceType.MOB);
//            }
//            if(identifier2.equals(EnchantmentHelper.getEnchantmentId(Enchantments.PROJECTILE_PROTECTION))){
//                ArrowEntity arrow = new ArrowEntity(world, player, new ItemStack(Items.BEACON));
//                arrow.setPos(playerPos.x,playerPos.y,playerPos.z);
//                arrow.setVelocity(vel.x+look.x*50,vel.y+look.y*50,vel.z+look.z*50);
//                arrow.setOnFire(true);
//                world.spawnEntity(arrow);
//            }
//            if(identifier2.equals(EnchantmentHelper.getEnchantmentId(Enchantments.BINDING_CURSE))){
//                player.damage(world.getDamageSources().indirectMagic(player,player),15);
//            }
//            if(identifier2.equals(EnchantmentHelper.getEnchantmentId(Enchantments.FEATHER_FALLING))){
//                player.setVelocity(vel.x,vel.y+100,vel.z);
//            }
//
//
//        }
        player.attack(player);
    }
}
