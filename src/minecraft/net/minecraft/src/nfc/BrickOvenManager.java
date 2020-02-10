package net.minecraft.src.nfc;

import java.util.*;

import net.minecraft.src.*;

public class BrickOvenManager {

	public class Recipe {
		
		public Recipe(ItemStack recipeOutput, int time, ItemStack... recipeItems) {
			this.recipeOutput = recipeOutput;
			this.recipeItems = recipeItems;
			this.time = time;
		}
		
		public boolean matches(ItemStack[] itemstacks) {
			List<ItemStack> recipeList = new ArrayList<ItemStack>();
			for(ItemStack recipeItem : recipeItems) {
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

		public final ItemStack recipeOutput;
		public final ItemStack[] recipeItems;
		public final int time;
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
		this.addShapelessRecipe(Core.BRASS.getItemStack(6), 1600, Core.COPPER.getItemStack(3), Core.ZINC.getItemStack(3));
		this.addShapelessRecipe(Core.BRONZE.getItemStack(6), 1600, Core.COPPER.getItemStack(5), Core.TIN.getItemStack());
		this.addShapelessRecipe(Core.STEEL.getItemStack(8), 6400, Core.CHROME.getItemStack(), new ItemStack(Item.ingotIron, 7));
		this.addShapelessRecipe(Core.TUNGSTEN.getItemStack(), 200, Core.ORE_TUNGSTEN.getItemStack());
		this.addShapelessRecipe(Core.TITANIUM.getItemStack(), 200, Core.ORE_TITANIUM.getItemStack());
		
		this.addFuel(new ItemStack(Block.planks), 100);
		this.addFuel(new ItemStack(Item.coal), 1600);
		this.addFuel(new ItemStack(Item.coal, 1, 1), 800);
		this.addFuel(Core.ANTHRICITE.getItemStack(), 6400);
	}

	public void addShapelessRecipe(ItemStack result, int timeToSmelt, ItemStack... recipeItems) {
		recipes.add(new Recipe(result, timeToSmelt, recipeItems));
	}
	
	public void addFuel(ItemStack itemstack, int burnTime) {
		fuels.add(new Fuel(itemstack, burnTime));
	}

	public ItemStack findMatchingRecipe(ItemStack[] itemstacks, TileEntityBrickOven tileentity) {
		for(Recipe recipe : recipes) {
			if (recipe.matches(itemstacks)) {
				if(tileentity != null)
					tileentity.setTime(recipe.time);
				return recipe.recipeOutput;
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

	public List<Recipe> getRecipeList() {
		return recipes;
	}

	public static final BrickOvenManager instance = new BrickOvenManager();
	private List<Recipe> recipes = new ArrayList<Recipe>();
	private List<Fuel> fuels = new ArrayList<Fuel>();
	

}
