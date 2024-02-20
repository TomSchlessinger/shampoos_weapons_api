package myshampooisdrunk.weapons_api.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.GiveCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.Collection;

public class CustomGiveCommand extends GiveCommand {
    //im fucking done what the fuck... fuck you mojang and your brigadier ass bullshit fuck you mojank
    //dogshit company
    //hope you go bankrupt
    //better before you were sold to microshit
    //fuck you mojank
    //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    private static int execute(ServerCommandSource source, ItemStackArgument item, Collection<ServerPlayerEntity> targets, int count) throws CommandSyntaxException {
        int i = item.getItem().getMaxCount();
        int j = i * 100;
        ItemStack itemStack = item.createStack(count, false);
        if (count > j) {
            source.sendError(Text.translatable("commands.give.failed.toomanyitems", j, itemStack.toHoverableText()));
            return 0;
        }
        for (ServerPlayerEntity serverPlayerEntity : targets) {
            int k = count;
            while (k > 0) {
                ItemEntity itemEntity;
                int l = Math.min(i, k);
                k -= l;
                ItemStack itemStack2 = item.createStack(l, false);
                boolean bl = serverPlayerEntity.getInventory().insertStack(itemStack2);
                if (!bl || !itemStack2.isEmpty()) {
                    itemEntity = serverPlayerEntity.dropItem(itemStack2, false);
                    if (itemEntity == null) continue;
                    itemEntity.resetPickupDelay();
                    itemEntity.setOwner(serverPlayerEntity.getUuid());
                    continue;
                }
                itemStack2.setCount(1);
                itemEntity = serverPlayerEntity.dropItem(itemStack2, false);
                if (itemEntity != null) {
                    itemEntity.setDespawnImmediately();
                }
                serverPlayerEntity.getWorld().playSound(null, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((serverPlayerEntity.getRandom().nextFloat() - serverPlayerEntity.getRandom().nextFloat()) * 0.7f + 1.0f) * 2.0f);
                serverPlayerEntity.currentScreenHandler.sendContentUpdates();
            }
        }
        if (targets.size() == 1) {
            source.sendFeedback(() -> Text.translatable("commands.give.success.single", count, itemStack.toHoverableText(), ((ServerPlayerEntity)targets.iterator().next()).getDisplayName()), true);
        } else {
            source.sendFeedback(() -> Text.translatable("commands.give.success.single", count, itemStack.toHoverableText(), targets.size()), true);
        }
        return targets.size();
    }
}
