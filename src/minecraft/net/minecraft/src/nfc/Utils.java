package net.minecraft.src.nfc;

import java.io.File;
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
	private static final EasyField<ItemStack> recipeOutputField = new EasyField<ItemStack>(ShapedRecipes.class, "recipeOutput", "e");
	private static final EasyField<Timer> timerField = new EasyField<Timer>(Minecraft.class, "timer", "T");
	private static Timer timer;
	
	private static final Map<String, String> loadedResources = new HashMap<String, String>();
	private static final List<String> missingResources = new ArrayList<String>();
	private static final String resourcesFolder = "/nfc/resources/";
	
	public static void replaceBlock(Block newBlock, String ...fields) {
		EasyField<Block> blockField = new EasyField<Block>(Block.class, fields);
		blockField.removeFinalModifier();
		blockField.set(newBlock);
		Block.blocksList[newBlock.blockID] = newBlock;
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
					recipeOutputField.set(recipe, newitems);
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
			timer = timerField.get(Utils.mc);
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
			logError("Missing file " + new File("bin\\minecraft.jar").getAbsolutePath().replace("\\", "/") + location);
			return false;
		}
	}
	
	public static void logError(String... lines) {
		System.out.println(new StringBuilder().append("NFC-ML ERROR: ").append(lines[0]).toString());
		for (String message : lines) {
			if(message == lines[0]) continue;
			System.out.println(new StringBuilder().append('\t').append(message).toString());
		}
	}
	
	public static class EasyField<T> {

		private static final EasyField<Integer> modifiersField = new EasyField<Integer>(Field.class, "modifiers");
		public final Field field;
		
		public EasyField(Class<?> target, String... names) {
			for (Field field : target.getDeclaredFields()) {
				for (String name : names) {
					if (field.getName() == name) {
						field.setAccessible(true);
						this.field = field;
						return;
					}
				}
			}
			this.field = null;
			logError("Failed to located field " + names[0] + " in class " + target.getSimpleName());
		}
		
		public boolean exists() {
			return field != null;
		}
		
		@SuppressWarnings("unchecked")
		public T get(Object instance) {
			try {
				return (T) field.get(instance);
			}
			catch (Exception e) { e.printStackTrace(); }
			return null;
		}
		
		public T get() {
			return this.get(null);
		}
		
		public void set(Object instance, T value) {
			try {
				field.set(instance, value);
			} 
			catch (Exception e) { e.printStackTrace(); }
		}
		
		public void set(T value) {
			this.set(null, value);
		}
		
		public void removeFinalModifier() {
			modifiersField.set(field, field.getModifiers() & ~Modifier.FINAL);
		}
		
	}
}
