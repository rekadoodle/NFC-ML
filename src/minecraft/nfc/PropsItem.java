package nfc;

import net.minecraft.src.ItemStack;

public class PropsItem extends Props {
	public int item_metadata;
	public int item_id;

	public PropsItem(String name, int textureIndex) {
		super(name, textureIndex);
	}
	
	@Override
	protected String getNamePrefix() {
		return "item.nfc.";
	}
	
	@Override
	public ItemStack getItemStack(int amount) {
		return new ItemStack(this.item_id, amount, this.item_metadata);
	}
}
