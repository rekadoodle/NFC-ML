package nfc;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.*;

public class FurnaceManager {
	
	public class Recipe {
		
		public Recipe(ItemStack input, ItemStack output) {
			this.input = input;
			this.output = output;
		}

		public final ItemStack input;
		public final ItemStack output;
	}
	
	public void addSmelting(ItemStack input, ItemStack output)
    {
        recipes.add(new Recipe(input, output));
    }

	public ItemStack getSmeltingResult(ItemStack input)
    {
		for(Recipe recipe : recipes) {
			if(recipe.input.isItemEqual(input)) {
				return recipe.output.copy();
			}
		}
        return (ItemStack)FurnaceRecipes.smelting().getSmeltingResult(input.itemID);
    }
	
	private final List<Recipe> recipes = new ArrayList<Recipe>();
	public static final FurnaceManager instance = new FurnaceManager();
}
