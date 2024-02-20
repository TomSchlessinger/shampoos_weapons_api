package myshampooisdrunk.weapons_api.mixin;

import myshampooisdrunk.weapons_api.WeaponAPI;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin {
    @Inject(at=@At("TAIL"),method = "grantCriterion")
    private void THISGAMEISSOFUCKINGPOORLYPROGRAMMEDFUCKYOUMOJANK(AdvancementEntry advancement, String criterionName, CallbackInfoReturnable<Boolean> cir){
        for(Identifier i : WeaponAPI.RECIPE_IDS.keySet()){
            if(advancement.id().equals(WeaponAPI.RECIPE_IDS.get(i).getRight())){
                ((PlayerAdvancementTracker)(Object)this).revokeCriterion(advancement,criterionName);
            }
        }
    }
}
