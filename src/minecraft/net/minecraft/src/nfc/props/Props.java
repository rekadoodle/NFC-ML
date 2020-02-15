package net.minecraft.src.nfc.props;

import net.minecraft.src.*;

/**
 * Describes properties for a Block or Item
 * This provides a more flexible approach to having multiple blocks/items per ID
 */
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
	
	/**
	 * @return new ItemStack with the id and metadata of this Props
	 */
	public ItemStack getItemStack() {
		return this.getItemStack(1);
	}
	
	public int getTextureIndex() {
		return this.TEXTURE_INDEX;
	}
	
	/**
	 * @return new ItemStack with the id and metadata of this Props
	 */
	public abstract ItemStack getItemStack(int stackSize);
}
