package net.minecraft.src.nfc;

import java.util.*;

import net.minecraft.src.*;

public class BrickOvenManager {

	public class Recipe {
		
		public Recipe(ItemStack recipeOutput, int time, ItemStack... recipeItems) {
			this.output = recipeOutput;
			this.inputs = recipeItems;
			this.cookTime = time;
		}
		
		public boolean matches(ItemStack[] itemstacks) {
			List<ItemStack> recipeList = new ArrayList<ItemStack>();
			for(ItemStack recipeItem : inputs) {
				recipeList.add(recipeItem.copy());
			}
			for(int i = 0; i < 9; i++) {
				if(itemstacks[i] == null)
					continue;
				if(recipeList.isEmpty()) {
					return false;
				}
				for(Iterator<ItemStack> iterator = recipeList.iterator(); iterator.hasNext();) {
					ItemStack recipeItem = iterator.next();
					if(recipeItem.isItemEqual(itemstacks[i])) {
						recipeItem.stackSize--;
						if(recipeItem.stackSize == 0)
							iterator.remove();
						break;
					}
				}
			}
			return recipeList.isEmpty();
		}

		public final ItemStack output;
		public final ItemStack[] inputs;
		public final int cookTime;
	}
	
	public class Fuel {
		public Fuel(ItemStack itemstack, int burnTime) {
			this.itemstack = itemstack;
			this.burnTime = burnTime;
		}

		public final ItemStack itemstack;
		public final int burnTime;
	}
	
	public BrickOvenManager() {
		this.addShapelessRecipe(NFC.BRASS.getItemStack(6), 1600, NFC.COPPER.getItemStack(3), NFC.ZINC.getItemStack(3));
		this.addShapelessRecipe(NFC.BRONZE.getItemStack(6), 1600, NFC.COPPER.getItemStack(5), NFC.TIN.getItemStack());
		this.addShapelessRecipe(NFC.STEEL.getItemStack(8), 6400, NFC.CHROME.getItemStack(), new ItemStack(Item.ingotIron, 7));
		this.addShapelessRecipe(NFC.TUNGSTEN.getItemStack(), 200, NFC.ORE_TUNGSTEN.getItemStack());
		this.addShapelessRecipe(NFC.TITANIUM.getItemStack(), 200, NFC.ORE_TITANIUM.getItemStack());
		
		this.addFuel(new ItemStack(Block.planks), 100);
		this.addFuel(new ItemStack(Item.coal), 1600);
		this.addFuel(new ItemStack(Item.coal, 1, 1), 800);
		this.addFuel(NFC.ANTHRACITE.getItemStack(), 6400);
	}

	public void addShapelessRecipe(ItemStack result, int timeToSmelt, ItemStack... recipeItems) {
		recipes.add(new Recipe(result, timeToSmelt, recipeItems));
	}
	
	public void addFuel(ItemStack itemstack, int burnTime) {
		fuels.add(new Fuel(itemstack, burnTime));
	}

	public Recipe findMatchingRecipe(ItemStack[] itemstacks) {
		for(Recipe recipe : recipes) {
			if (recipe.matches(itemstacks)) {
				return recipe;
			}
		}
		return null;
	}
	
	public int getItemBurnTime(ItemStack itemstack) {
		if(itemstack == null) {
			return 0;
		}
		for(Fuel fuel : fuels) {
			if(fuel.itemstack.isItemEqual(itemstack)) {
				return fuel.burnTime;
			}
		}
		return 0;
	}

	public static final BrickOvenManager instance = new BrickOvenManager();
	private List<Recipe> recipes = new ArrayList<Recipe>();
	private List<Fuel> fuels = new ArrayList<Fuel>();
	

}
