package myshampooisdrunk.weapons_api.example;

import myshampooisdrunk.weapons_api.weapon.AbstractCustomItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class GoofySillyGoofyItem extends AbstractCustomItem {
    public GoofySillyGoofyItem() {
        super(Items.ENCHANTED_BOOK,"zonky.zonky.zonky.zonky.zonky.fong");
    }

    @Override
    public void onUse(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable cir) {
        Item item = player.getStackInHand(hand).getItem();
        NbtList list = EnchantedBookItem.getEnchantmentNbt(player.getStackInHand(hand));
        for(int i = 0; i < list.size(); i++ ){
            NbtCompound nbtCompound = list.getCompound(i);
            Identifier identifier2 = EnchantmentHelper.getIdFromNbt(nbtCompound);
            assert identifier2 != null;
            Vec3d playerPos = player.getPos();
            Vec3d pos = player.raycast(50,1,true).getPos();
            Vec3d vel = player.getVelocity();
            Vec3d look = player.getRotationVector();
            if(identifier2.equals(EnchantmentHelper.getEnchantmentId(Enchantments.BLAST_PROTECTION))){
                world.createExplosion(player, world.getDamageSources().explosion(player,player),null,pos, (float) (player.squaredDistanceTo(pos)/25f),true, World.ExplosionSourceType.MOB);
            }
            if(identifier2.equals(EnchantmentHelper.getEnchantmentId(Enchantments.PROJECTILE_PROTECTION))){
                ArrowEntity arrow = new ArrowEntity(world, player, new ItemStack(Items.BEACON));
                arrow.setPos(playerPos.x,playerPos.y,playerPos.z);
                arrow.setVelocity(vel.x+look.x*50,vel.y+look.y*50,vel.z+look.z*50);
                arrow.setOnFire(true);
                world.spawnEntity(arrow);
            }
            if(identifier2.equals(EnchantmentHelper.getEnchantmentId(Enchantments.BINDING_CURSE))){
                player.damage(world.getDamageSources().indirectMagic(player,player),15);
            }
            if(identifier2.equals(EnchantmentHelper.getEnchantmentId(Enchantments.FEATHER_FALLING))){
                player.setVelocity(vel.x,vel.y+100,vel.z);
            }
            //public Explosion createExplosion(@Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior behavior, Vec3d pos, float power, boolean createFire, ExplosionSourceType explosionSourceType) {
            //        return this.createExplosion(entity, damageSource, behavior, pos.getX(), pos.getY(), pos.getZ(), power, createFire, explosionSourceType, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, SoundEvents.ENTITY_GENERIC_EXPLODE);
            //    }
        }
    }
}
