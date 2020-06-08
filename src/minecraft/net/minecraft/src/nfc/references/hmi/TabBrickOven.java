package net.minecraft.src.nfc.references.hmi;

import java.util.ArrayList;
import java.util.List;

import hmi.tabs.TabSmelting;
import net.minecraft.src.*;
import net.minecraft.src.nfc.NFC;
import net.minecraft.src.nfc.BrickOvenManager;
import net.minecraft.src.nfc.Utils;

public class TabBrickOven extends TabSmelting {
	
	private final List<BrickOvenManager.Recipe> recipeList = new Utils.EasyField<List<BrickOvenManager.Recipe>>(BrickOvenManager.class, "recipes").get(BrickOvenManager.instance);
	private final List<BrickOvenManager.Recipe> filteredRecipes = new ArrayList<BrickOvenManager.Recipe>();
	private final List<BrickOvenManager.Fuel> fuelList = new Utils.EasyField<List<BrickOvenManager.Fuel>>(BrickOvenManager.class, "fuels").get(BrickOvenManager.instance);
	
	public TabBrickOven(BaseMod tabCreator) {
		super(tabCreator, 11, Utils.getResource("oven.png"), 102, 92, 36, 15, NFC.BRICKOVEN_IDLE, 0);
		
		int count = 0;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				slots[count++] = (new Integer[] {2 + x*18, 5 + y*18});
			}
		}
		
		slots[count++] = new Integer[] {20, 77};
		slots[count++] = new Integer[] {80, 59};
	}
	
	public ItemStack[][] getItems(int index, ItemStack filter) {
		ItemStack[][] items = new ItemStack[recipesPerPage][];
		for(int j = 0; j < recipesPerPage; j++)
        {
            items[j] = new ItemStack[slots.length];
            int k = index + j;
            if(k < filteredRecipes.size())
            {
            	BrickOvenManager.Recipe recipe = filteredRecipes.get(k);
            	
            	int slotIndex = 0;
            	for (int i = 0; i < recipe.inputs.length; i++) {
            		int currentSlot = slotIndex;
            		items[j][slotIndex++] = recipe.inputs[i].copy();
            		while(items[j][currentSlot].stackSize > 1) {
            			items[j][currentSlot].stackSize--;
            			items[j][slotIndex++] = new ItemStack(items[j][currentSlot].getItem(), 1, items[j][currentSlot].getItemDamage());
            		}
            		
            	}
            	items[j][10] = recipe.output.copy();
            	BrickOvenManager.Fuel fuel = fuelList.get(rand.nextInt(fuelList.size()));
            	items[j][9] = fuel.itemstack.copy();
            	items[j][9].stackSize = recipe.cookTime / fuel.burnTime;
             }

            if(items[j][10] == null && recipesOnThisPage > j) {
            	recipesOnThisPage = j;
                redrawSlots = true;
                break;
            }
            else if(items[j][10] != null && recipesOnThisPage == j) {
            	recipesOnThisPage = j+1;
                redrawSlots = true;
            }
        }
		return items;
	}
	
	public void updateRecipes(ItemStack filter, Boolean getUses) {
		filteredRecipes.clear();
		updateRecipesWithoutClear(filter, getUses);
	}
	
	public void updateRecipesWithoutClear(ItemStack filter, Boolean getUses) {
		for(BrickOvenManager.Recipe recipe : recipeList) {
			if(filter == null) {
				filteredRecipes.add(recipe);
			}
			else if(!getUses && recipe.output.isItemEqual(filter)) {
				filteredRecipes.add(recipe);
			}
			else if(getUses){
				for(ItemStack item : recipe.inputs) {
					if(item.isItemEqual(filter)) {
						filteredRecipes.add(recipe);
						break;
					}
				}
			}
		}
		size = filteredRecipes.size();
		if (size == 0 && getUses){
			for(ItemStack craftingStation : equivalentCraftingStations) {
				if(filter.itemID == craftingStation.itemID && filter.getItemDamage() == craftingStation.getItemDamage()) {
		    		updateRecipes(null, getUses);
		    		break;
				}
			}
    	}
    	size = filteredRecipes.size();
	}

}
