package nfc.props;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFurnace;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import nfc.block.BlockBrickOven;

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
	
	public static class Wrench extends PropsItem {
		
		public interface IWrenchable {
			public abstract boolean onWrenched(World world, int x, int y, int z);
		}

		public Wrench(String name, int textureIndex) {
			super(name, textureIndex);
		}
		
		public boolean onItemUseFirst(ItemStack ist, EntityPlayer player, World world, int x, int y, int z, int side) {
			Block block = Block.blocksList[world.getBlockId(x, y, z)];
			if(block instanceof IWrenchable) {
				return ((IWrenchable)block).onWrenched(world, x, y, z);
			}
			else if(block instanceof BlockBrickOven) {
				world.markBlockAsNeedsUpdate(x, y, z);
				return world.setBlockMetadata(x, y, z, (world.getBlockMetadata(x, y, z) + 1) % 6);
			}
			else if(block instanceof BlockFurnace) {
				world.markBlockAsNeedsUpdate(x, y, z);
				return world.setBlockMetadata(x, y, z, (((world.getBlockMetadata(x, y, z) - 1)) % 4) + 2);
			}
			return false;
		}
	}
}
