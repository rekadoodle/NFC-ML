package net.minecraft.src.nfc;

import net.minecraft.src.*;
import net.minecraft.src.nfc.block.BlockBrickOven;

public class TileEntityBrickOven extends TileEntityFurnaceMetadataFix {

	public TileEntityBrickOven() {
		super(11);
	}
	
	@Override
	protected int getItemBurnTime(ItemStack itemstack) {
		return BrickOvenManager.instance.getItemBurnTime(itemstack);
	}

	@Override
	protected void updateBlockState(boolean isActive) {
		BlockBrickOven.updateFurnaceBlockState(isActive, worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	protected ItemStack getResultItemStack() {
		return BrickOvenManager.instance.findMatchingRecipe(furnaceItemStacks, this);
	}

	public void setTime(int i) {
		requiredTime = i;
	}

	@Override
	protected boolean inputSlotsEmpty() {
		for (int i = 0; i < 9; i++) {
			if (furnaceItemStacks[i] != null)
				return false;
		}
		return true;
	}

	@Override
	protected void reduceInputSlots(int amount) {
		for (int i = 0; i < 9; i++) {
			if(furnaceItemStacks[i] == null) continue;
			if (furnaceItemStacks[i].getItem().hasContainerItem()) {
				furnaceItemStacks[i] = new ItemStack(furnaceItemStacks[i].getItem().getContainerItem());
			} else {
				furnaceItemStacks[i].stackSize -= amount;
			}
			if (furnaceItemStacks[i].stackSize <= 0) {
				furnaceItemStacks[i] = null;
			}
		}
	}
}
