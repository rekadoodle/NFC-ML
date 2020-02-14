package net.minecraft.src.nfc.references.hmi;

import java.util.List;

import hmi.tabs.TabSmelting;
import net.minecraft.src.*;
import net.minecraft.src.nfc.FurnaceManager;
import net.minecraft.src.nfc.Utils;

public class TabSmeltingMetadata extends TabSmelting {
	
	private final List<FurnaceManager.Recipe> recipeList = new Utils.EasyField<List<FurnaceManager.Recipe>>(FurnaceManager.class, "recipes").get(FurnaceManager.instance);

	public TabSmeltingMetadata(BaseMod tabCreator) {
		super(tabCreator);
	}

	@Override
	public void updateRecipesWithoutClear(ItemStack filter, Boolean getUses) {
		for(FurnaceManager.Recipe recipe : recipeList) {
			if(filter == null || (!getUses && recipe.output.isItemEqual(filter) || (getUses && recipe.input.isItemEqual(filter)))) {
				this.recipes.add(new ItemStack[] {recipe.output, recipe.input});
			}
		}
		super.updateRecipesWithoutClear(filter, getUses);
	}
}
