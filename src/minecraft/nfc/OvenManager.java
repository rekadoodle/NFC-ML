package nfc;

import java.util.*;

import net.minecraft.src.*;

public class OvenManager {

	public class OvenRecipe {
		
		public OvenRecipe(ItemStack recipeOutput, List<ItemStack> recipeItems, int time) {
			this.recipeOutput = recipeOutput;
			this.recipeItems = recipeItems;
			this.time = time;
		}

		public ItemStack getRecipeOutput() {
			return recipeOutput;
		}
		
		public int getTime(){
			return time;
		}

		public boolean matches(ItemStack[] itemstacks) {
			ArrayList<ItemStack> arraylist = new ArrayList<ItemStack>(recipeItems);
			int i = 0;
			do {
				if (i >= 3) {
					break;
				}
				for (int j = 0; j < 3; j++) {
					ItemStack itemstack = itemstacks[j + (i*3)];
					if (itemstack == null) {
						continue;
					}
					boolean flag = false;
					Iterator<ItemStack> iterator = arraylist.iterator();
					do {
						if (!iterator.hasNext()) {
							break;
						}
						ItemStack itemstack1 = (ItemStack) iterator.next();
						if (itemstack.itemID != itemstack1.itemID || itemstack1.getItemDamage() != -1 && itemstack.getItemDamage() != itemstack1.getItemDamage()) {
							continue;
						}
						flag = true;
						if(itemstack1.stackSize > 1) {
							itemstack1.stackSize--;
						}
						else {
							arraylist.remove(itemstack1);
						}
						break;
					} while (true);
					if (!flag) {
						return false;
					}
				}

				i++;
			} while (true);
			return arraylist.isEmpty();
		}

		public ItemStack getCraftingResult() {
			return recipeOutput.copy();
		}

		public int getRecipeSize() {
			return recipeItems.size();
		}

		private final ItemStack recipeOutput;
		private final List<ItemStack> recipeItems;
		private final int time;
	}
	
	public OvenManager() {
		recipes = new ArrayList<OvenRecipe>();
		
		addShapelessRecipe(mod_NFC.BRASS.getItemStack(6), 1600, mod_NFC.COPPER.getItemStack(3), mod_NFC.ZINC.getItemStack(3));
		addShapelessRecipe(mod_NFC.BRONZE.getItemStack(6), 1600, mod_NFC.COPPER.getItemStack(5), mod_NFC.TIN.getItemStack());
		addShapelessRecipe(mod_NFC.STEEL.getItemStack(8), 6400, mod_NFC.CHROME.getItemStack(), new ItemStack(Item.ingotIron, 7));
		addShapelessRecipe(mod_NFC.TUNGSTEN.getItemStack(), 200, mod_NFC.ORE_TUNGSTEN.getItemStack());
		addShapelessRecipe(mod_NFC.TITANIUM.getItemStack(), 200, mod_NFC.ORE_TITANIUM.getItemStack());
	}

	public void addShapelessRecipe(ItemStack result, int timeToSmelt, ItemStack... recipeItems) {
		ArrayList<ItemStack> arraylist = new ArrayList<ItemStack>();
		for(ItemStack itemstack : recipeItems) {
			for(int i = 0; i < itemstack.stackSize; i++) {
				arraylist.add(new ItemStack(itemstack.itemID, 1, itemstack.getItemDamage()));
			}
		}
		recipes.add(new OvenRecipe(result, arraylist, timeToSmelt));
	}

	public ItemStack findMatchingRecipe(ItemStack[] itemstacks, TileEntityBrickOven joe) {
		for (int var6 = 0; var6 < this.recipes.size(); ++var6) {
			OvenRecipe var12 = (OvenRecipe) this.recipes.get(var6);
			if (var12.matches(itemstacks)) {
				joe.setTime(var12.getTime());
				return var12.getCraftingResult();
			}
		}
		return null;
	}

	public List<OvenRecipe> getRecipeList() {
		return recipes;
	}
	
	public static final OvenManager smelting() {
		return instance;
	}

	private static final OvenManager instance = new OvenManager();
	private List<OvenRecipe> recipes;

}
