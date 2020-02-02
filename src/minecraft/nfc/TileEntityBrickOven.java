package nfc;

import java.lang.reflect.Field;

import net.minecraft.src.*;

public class TileEntityBrickOven extends TileEntityFurnace implements IInventory {

	public TileEntityBrickOven() {
		this.setParentFurnaceItemStacks(new ItemStack[11]);
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return furnaceItemStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (furnaceItemStacks[i] != null) {
			if (furnaceItemStacks[i].stackSize <= j) {
				ItemStack itemstack = furnaceItemStacks[i];
				furnaceItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = furnaceItemStacks[i].splitStack(j);
			if (furnaceItemStacks[i].stackSize == 0) {
				furnaceItemStacks[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		furnaceItemStacks[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		furnaceItemStacks = this.getParentFurnaceItemStacks();
		currentItemBurnTime = getItemBurnTime(furnaceItemStacks[9]);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		this.setParentFurnaceItemStacks(furnaceItemStacks);
		super.writeToNBT(nbttagcompound);
	}

	@Override
	public int getCookProgressScaled(int i) {
		return (furnaceCookTime * i) / requiredTime;
	}

	@Override
	public int getBurnTimeRemainingScaled(int i) {
		if (currentItemBurnTime == 0) {
			currentItemBurnTime = 200;
		}
		return (furnaceBurnTime * i) / currentItemBurnTime;
	}

	@Override
	public boolean isBurning() {
		return furnaceBurnTime > 0;
	}

	@Override
	public void updateEntity() {
		boolean flag = furnaceBurnTime > 0;
		boolean flag1 = false;
		if (furnaceBurnTime > 0) {
			furnaceBurnTime--;
		}
		if (!worldObj.multiplayerWorld) {
			if (furnaceBurnTime == 0 && canSmelt()) {
				currentItemBurnTime = furnaceBurnTime = getItemBurnTime(furnaceItemStacks[9]);
				if (furnaceBurnTime > 0) {
					flag1 = true;
					if (furnaceItemStacks[9] != null) {
						if (furnaceItemStacks[9].getItem().hasContainerItem()) {
							furnaceItemStacks[9] = new ItemStack(
									furnaceItemStacks[9].getItem()
											.getContainerItem());
						} else {
							furnaceItemStacks[9].stackSize--;
						}
						if (furnaceItemStacks[9].stackSize == 0) {
							furnaceItemStacks[9] = null;
						}
					}
				}
			}
			if (isBurning() && canSmelt()) {
				furnaceCookTime++;
				if (furnaceCookTime == requiredTime) {
					furnaceCookTime = 0;
					smeltItem();
					flag1 = true;
				}
			} else {
				furnaceCookTime = 0;
			}
			if (flag != (furnaceBurnTime > 0)) {
				flag1 = true;
				BlockBrickOven.updateFurnaceBlockState(furnaceBurnTime > 0, worldObj, xCoord, yCoord, zCoord);
			}
		}
		if (flag1) {
			onInventoryChanged();
		}
	}

	private boolean canSmelt() {
		ItemStack itemstack = OvenManager.smelting().findMatchingRecipe(furnaceItemStacks, this);
		if (itemstack == null) {
			return false;
		}
		if (furnaceItemStacks[10] == null) {
			return true;
		}
		if (!furnaceItemStacks[10].isItemEqual(itemstack)) {
			return false;
		}
		if (furnaceItemStacks[10].stackSize < getInventoryStackLimit()
				&& furnaceItemStacks[10].stackSize + itemstack.stackSize < furnaceItemStacks[10]
						.getMaxStackSize()) {
			return true;
		}
		return furnaceItemStacks[10].stackSize + itemstack.stackSize < itemstack.getMaxStackSize();
	}

	@Override
	public void smeltItem() {
		if (!canSmelt()) {
			return;
		}
		ItemStack itemstack = OvenManager.smelting().findMatchingRecipe(furnaceItemStacks, this);
		if (furnaceItemStacks[10] == null) {
			furnaceItemStacks[10] = itemstack.copy();
		} else if (furnaceItemStacks[10].itemID == itemstack.itemID) {
			furnaceItemStacks[10].stackSize += itemstack.stackSize;
		}
		
		//Removed container item code
		for(int i = 0; i < 9; i++)
		{
			if(furnaceItemStacks[i] != null){
				furnaceItemStacks[i].stackSize--;
				if (furnaceItemStacks[i].stackSize <= 0) {
				furnaceItemStacks[i] = null;
				}
			}
		}
	}
	
	private int getItemBurnTime(ItemStack itemstack) {
		if (itemstack == null) {
			return 0;
		}
		int i = itemstack.getItem().shiftedIndex;
		int j = itemstack.getItemDamage();
		if (i == Block.planks.blockID) {
			return 100;
		}
		if (i == Item.coal.shiftedIndex && j == 0) {
			return 1600;
		}
		if(i == Item.coal.shiftedIndex) {
			return 800;
		}
		//if (i == NFC.anthracite.shiftedIndex) {
		//	return 6400;
		//}

		return 0;
	}
	
	public void setTime(int i){
		requiredTime = i;
	}
	
	public void setParentFurnaceItemStacks(ItemStack[] furnaceItemStacks) {
		this.furnaceItemStacks = furnaceItemStacks;
		try {
			 parentFurnaceItemStacksField.set(this, furnaceItemStacks);
		} 
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public ItemStack[] getParentFurnaceItemStacks() {
		try {
			return (ItemStack[]) parentFurnaceItemStacksField.get(this);
		} 
		catch (Exception e) { e.printStackTrace(); }
		return null;
	}

	private ItemStack furnaceItemStacks[];
	public int requiredTime = 200;
	private static Field parentFurnaceItemStacksField = Utils.getField(TileEntityFurnace.class, "furnaceItemStacks", "i");
}
