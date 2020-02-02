package nfc;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

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
	
	public static class Food extends PropsItem {
		
		public final int HEAL_AMOUNT;

		public Food(String name, int healAmount, int textureIndex) {
			super(name, textureIndex);
			this.HEAL_AMOUNT = healAmount;
		}
		
		public void onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	    {
	        itemstack.stackSize--;
	        entityplayer.heal(HEAL_AMOUNT);
	    }
	}
}
