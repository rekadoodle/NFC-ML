package net.minecraft.src.nfc;

import net.minecraft.src.*;

public class ContainerBrickOven extends Container {

	public ContainerBrickOven(InventoryPlayer inventoryplayer, TileEntityBrickOven tileentity) {
		this.tileentity = tileentity;
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 3; k++) {
		addSlot(new Slot(tileentity,  k + i*3, 38 + k*18, 17 + i*18));
			}
		}
		addSlot(new Slot(tileentity, 9, 56, 89));
		addSlot(new SlotFurnace(inventoryplayer.player, tileentity, 10, 116, 71));
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlot(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18,
						120 + i * 18));
			}
		}
		for (int j = 0; j < 9; j++) {
			addSlot(new Slot(inventoryplayer, j, 8 + j * 18, 178));
		}

	}

	@Override
	public void func_20112_a(int i, int j) {
		if (i == 0) {
			tileentity.furnaceCookTime = j;
		}
		if (i == 1) {
			tileentity.furnaceBurnTime = j;
		}
		if (i == 2) {
			tileentity.currentItemBurnTime = j;
		}
		if (i == 3) {
			tileentity.requiredTime = j;
		}
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityplayer) {
		return tileentity.canInteractWith(entityplayer);
	}

	/**
	 * This is some shift clicking shit I don't really understand, think I fixed it doe
	 */
	@Override
	public ItemStack getStackInSlot(int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) slots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i == 10) {
				func_28125_a(itemstack1, 11, 47, true);
			} 
			else if (i >= 11 && i < 38) {
				func_28125_a(itemstack1, 38, 47, false);
			} 
			else if (i >= 38 && i < 47) {
				func_28125_a(itemstack1, 11, 38, false);
			} 
			else {
				func_28125_a(itemstack1, 11, 47, false);
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize != itemstack.stackSize) {
				slot.onPickupFromSlot(itemstack1);
			} else {
				return null;
			}
		}
		return itemstack;
	}

	private TileEntityBrickOven tileentity;
}
