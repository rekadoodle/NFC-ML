package nfc;

import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;

public abstract class Props {

	private final int TEXTURE_INDEX;
	public final String NAME;

	public Props(String name, int textureIndex) {
		this.NAME = name;
		this.TEXTURE_INDEX = textureIndex;
		this.addLocalisation(name);
	}
	
	public void addLocalisation(String name) {
		ModLoader.AddLocalization(new StringBuilder().append(this.getName()).append(".name").toString(), name);
	}
	
	public String getName() {
		String backendName = NAME.trim().toLowerCase().replace(' ', '.');
		return new StringBuilder().append(this.getNamePrefix()).append(backendName).toString();
	}
	
	protected abstract String getNamePrefix();
	
	public ItemStack getItemStack() {
		return this.getItemStack(1);
	}
	
	public int getTextureIndex() {
		return this.TEXTURE_INDEX;
	}
	
	public abstract ItemStack getItemStack(int amount);
}
