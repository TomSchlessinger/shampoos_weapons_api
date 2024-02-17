package myshampooisdrunk.weapons_api;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.loot.FabricBlockLootTableGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeCraftedCriterion;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.advancement.criterion.TameAnimalCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.CrackParticle;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.data.server.loottable.LootTableProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WeaponAPIDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator gen) {
		FabricDataGenerator.Pack pack = gen.createPack();
		pack.addProvider(CustomItemLootTable::new);
		pack.addProvider(AdvancementsProvider::new);
		pack.addProvider(CustomRecipeProvider::new);
		StringBuilder loadFile = new StringBuilder();
//		for(Identifier i : WeaponAPI.CUSTOM_RECIPES.keySet()){
//			loadFile.append("scoreboard objectives add ").append(i.getPath()).append(" dummy\n");
//		}
//		File file = new File("./src/main/data/api","load.mcfunction");
//		if(file.exists()){
//			file.delete();
//			try {
//				file.createNewFile();
//				System.out.println("BAAAAAAAAAAAA");
//				FileWriter writer = new FileWriter(file);
//				writer.write(loadFile.toString());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		else{
//			try {
//				file.createNewFile();
//				System.out.println("BAAAAAAAAAAAA");
//				FileWriter writer = new FileWriter(file);
//				writer.write(loadFile.toString());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}
	static class AdvancementsProvider extends FabricAdvancementProvider {
		Set<AdvancementEntry> advancements = new HashSet<>();
		protected AdvancementsProvider(FabricDataOutput dataGenerator) {
			super(dataGenerator);
		}

		@Override
		public void generateAdvancement(Consumer<AdvancementEntry> consumer) {
			for(Identifier id : WeaponAPI.CUSTOM_RECIPES.keySet()){
				advancements.add(
						Advancement.Builder.create()
								.criterion("after_crafted", RecipeCraftedCriterion.Conditions.create(WeaponAPI.RECIPE_IDS.get(id).getLeft()))
								.rewards(AdvancementRewards.Builder.loot(WeaponAPI.RECIPE_IDS.get(id).getMiddle()))
								.build(consumer,WeaponAPI.RECIPE_IDS.get(id).getRight().toString())
				);
			}
		}
	}
	static class CustomRecipeProvider extends FabricRecipeProvider {

		public CustomRecipeProvider(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generate(RecipeExporter exporter) {
			for(Identifier id : WeaponAPI.CUSTOM_RECIPES.keySet()){
				if(WeaponAPI.CUSTOM_RECIPES.get(id).getLeft() instanceof ShapelessRecipe r){
					ShapelessRecipeJsonBuilder j = ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.KNOWLEDGE_BOOK);
					for(Ingredient in : r.getIngredients()){
						j=j.input(in);
					}
					j.criterion(RecipeProvider.hasItem(Items.AIR), RecipeProvider.conditionsFromItem(Items.AIR))
							.offerTo(exporter,WeaponAPI.RECIPE_IDS.get(id).getLeft());
				}else if(WeaponAPI.CUSTOM_RECIPES.get(id).getLeft() instanceof ShapedRecipe s){
					ShapedRecipeJsonBuilder j = ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.KNOWLEDGE_BOOK)
							.pattern("abc").pattern("def").pattern("ghi");
					char[] a = new char[]{'a','b','c','d','e','f','g','h','i'};

					for(int b = 0; b < s.getIngredients().size(); b++){
						j=j.input(a[b],s.getIngredients().get(b));
					}
					j.criterion(RecipeProvider.hasItem(Items.AIR), RecipeProvider.conditionsFromItem(Items.AIR))
							.offerTo(exporter,WeaponAPI.RECIPE_IDS.get(id).getLeft());
				}
			}
		}
	}
	public static <T> T echo(T init){
		System.out.println(init);
		return init;
	}
	static class CustomItemLootTable extends SimpleFabricLootTableProvider{
		public CustomItemLootTable(FabricDataOutput output) {
			super(output, LootContextTypes.ADVANCEMENT_REWARD);
		}

		@Override
		public void accept(BiConsumer<Identifier, LootTable.Builder> exporter) {
			for(Identifier i : WeaponAPI.CUSTOM_RECIPES.keySet()){
				ItemStack item = WeaponAPI.CUSTOM_RECIPES.get(i).getRight().create();
				exporter.accept(
						WeaponAPI.RECIPE_IDS.get(i).getMiddle(),
						new LootTable.Builder().pool(
								LootPool.builder()
										.rolls(ConstantLootNumberProvider.create(1))
										.with(
												ItemEntry.builder(item.getItem()).apply(SetNbtLootFunction.builder(item.getNbt()))
										)
						)
				);
			}
		}
	}
}
/*
* .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).build())
								.apply(
										ReferenceLootFunction.builder(new Identifier(i.getPath() + "_recipe_function",i.getNamespace()))
												.conditionally(RandomChanceLootCondition.builder(1f))
												.build().apply(
														WeaponAPI.CUSTOM_RECIPES.get(i).getMiddle().create(),
												LootContext.
														)

								)*/
