package myshampooisdrunk.weapons_api.weapon;

import myshampooisdrunk.weapons_api.WeaponAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.security.auth.callback.Callback;

public abstract class AbstractCustomItem{
    private final String key;
    private final Item item;
    private final int id;
    private final Identifier identifier;
    public AbstractCustomItem(Item item, Identifier identifier) {
        this(item,identifier,null);
    }
    public AbstractCustomItem(Item item, Identifier identifier, @Nullable String itemName){
        this(item, identifier,WeaponAPI.ITEM_COUNT.getOrDefault(item,0)+1,itemName);
        if(WeaponAPI.ITEM_COUNT.containsKey(item)){
            WeaponAPI.ITEM_COUNT.put(item,WeaponAPI.ITEM_COUNT.get(item)+1);
        }else{
            WeaponAPI.ITEM_COUNT.put(item,1);
        }

    }
    private AbstractCustomItem(Item item, Identifier identifier, int id, String itemName) {
        this.identifier=identifier;
        this.key=itemName;
        this.item = item;
        this.id = id;
    }
    public void onUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable cir){

    }
    public void onAttack(Entity target, CallbackInfo ci){

    }
    public void onSneak(boolean sneaking, PlayerEntity player, CallbackInfo ci){

    }
    public void whileSneak(PlayerEntity p, CallbackInfo ci){

    }
    public void onClick(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player, CallbackInfoReturnable<Boolean> cir){

    }
    public void onDrop(PlayerEntity p, ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfo ci){

    }
    public Item getItem(){return item;}
    public int getId(){return id;}
    public Identifier getIdentifier(){return identifier;}
    public ItemStack create(){
        MutableText t = (key != null ? Text.translatable(key) : (MutableText) item.getName()).setStyle(Style.EMPTY.withItalic(false));
        ItemStack ret = new ItemStack(item).setCustomName(t);//new Formatting("ITALIC",'o',false)
        NbtCompound name = ret.getOrCreateSubNbt("display");
        name.putString("Name",Text.Serialization.toJsonString(t));//.replace("{\"text\":","{\"italic\":false,\"text\":")
        ret.getOrCreateNbt().putInt("CustomModelData",id);
        return ret;
    }
}
