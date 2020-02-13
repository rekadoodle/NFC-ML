package net.minecraft.src.nfc;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class Utils {

	public static final Minecraft mc = ModLoader.getMinecraftInstance();
	private static final Field modifiersField = getField(Field.class, "modifiers");
	private static final Field recipeOutput = Utils.getField(ShapedRecipes.class, "recipeOutput", "e");
	private static final Field timerField = Utils.getField(Minecraft.class, "timer", "T");
	private static Timer timer;
	
	private static final Map<String, String> loadedResources = new HashMap<String, String>();
	private static final List<String> missingResources = new ArrayList<String>();
	private static final String resourcesFolder = "/nfc/resources/";

	// Used for easy reflection with obfuscated or regular fields
	public static final Field getField(Class<?> target, String... names) {
		for (Field field : target.getDeclaredFields()) {
			for (String name : names) {
				if (field.getName() == name) {
					field.setAccessible(true);
					return field;
				}
			}
		}
		return null;
	}
	
	public static void replaceBlock(Block newBlock, String ...fields) {
        try {
    		Field blockField = getField(Block.class, fields);
			modifiersField.setInt(blockField, blockField.getModifiers() & ~Modifier.FINAL);
			blockField.set(null, newBlock);
			Block.blocksList[newBlock.blockID] = newBlock;
        } 
        catch (Exception e) { e.printStackTrace(); }
	}
	
	public static int clearBlockID(Block block) {
		return clearBlockID(block.blockID);
	}
	
	public static int clearBlockID(int id) {
		Block.blocksList[id] = null;
		return id;
	}
	
	public static void overrideShapedRecipes(ItemStack vanilla, ItemStack newitems) {
		@SuppressWarnings("unchecked")
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for(IRecipe recipe : recipes) {
			if(recipe instanceof ShapedRecipes) {
				if(recipe.getRecipeOutput().isItemEqual(vanilla)) {
					try {
						recipeOutput.set(recipe, newitems);
					} 
					catch (Exception e) { e.printStackTrace(); }
				}
			}
		}
	}
	
	public static int cursorX() {
		if(mc.currentScreen != null) {
	        return (Mouse.getX() * mc.currentScreen.width) / mc.displayWidth;
		}
		ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        return (Mouse.getX() * scaledresolution.getScaledWidth()) / mc.displayWidth;
	}
	
	public static int cursorY() {
		if(mc.currentScreen != null) {
	        return mc.currentScreen.height - (Mouse.getY() * mc.currentScreen.height) / Utils.mc.displayHeight - 1;
		}
        ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        return scaledresolution.getScaledHeight() - (Mouse.getY() * scaledresolution.getScaledHeight()) / mc.displayHeight - 1;
	}
	
	public static float renderPartialTicks() {
		if(timer == null) {
			try {
				timer = (Timer) timerField.get(Utils.mc);
			}
			catch (Exception e) { e.printStackTrace(); } 
		}
		return timer.renderPartialTicks;
	}
	
	public static URL getResourceURL(String resource) {
		return getResourceURLFromLocation(getResource(resource));
	}
	
	private static URL getResourceURLFromLocation(String location) {
		return Utils.class.getResource(location);
	}
	
	public static String getResource(String resource) {
		return getResource(resource, resource);
	}
	
	public static String getResource(String resource, String backupResource) {
		if(resourceExists(resource)) {
			loadedResources.get(resource);
		}
		return new StringBuilder().append(resourcesFolder).append(backupResource).toString();
	}
	
	public static boolean resourceExists(String resource) {
		if(loadedResources.containsKey(resource)) {
			return true;
		}
		if(missingResources.contains(resource)) {
			return false;
		}
		String location = new StringBuilder().append(resourcesFolder).append(resource).toString();
		if(getResourceURLFromLocation(location) != null) {
			loadedResources.put(resource, location);
			return true;
		}
		else  {
			missingResources.add(resource);
			System.out.println("NFC-ML ERROR: Missing file " + new File("bin\\minecraft.jar").getAbsolutePath().replace("\\", "/") + location);
			return false;
		}
	}
}
