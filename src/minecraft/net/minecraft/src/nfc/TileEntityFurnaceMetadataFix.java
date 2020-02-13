package net.minecraft.src.nfc;

import net.minecraft.src.*;

public class TileEntityFurnaceMetadataFix extends TileEntityFurnace {
	
	public TileEntityFurnaceMetadataFix() {
		this(3);
	}
	
	public TileEntityFurnaceMetadataFix(int slotCount) {
		this.furnaceItemStacks = new ItemStack[slotCount];
		parentFurnaceItemStacksField.set(this, furnaceItemStacks);
	}
	
	@Override
	public int getSizeInventory()
    {
        return furnaceItemStacks.length;
    }

	@Override
    public ItemStack getStackInSlot(int i)
    {
        return furnaceItemStacks[i];
    }

	@Override
    public ItemStack decrStackSize(int i, int j)
    {
        checkRecipeOnTick = true;
        if(furnaceItemStacks[i] != null)
        {
            if(furnaceItemStacks[i].stackSize <= j)
            {
                ItemStack itemstack = furnaceItemStacks[i];
                furnaceItemStacks[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = furnaceItemStacks[i].splitStack(j);
            if(furnaceItemStacks[i].stackSize == 0)
            {
                furnaceItemStacks[i] = null;
            }
            return itemstack1;
        } else
        {
            return null;
        }
    }
	
	@Override
	public void onInventoryChanged()
    {
        checkRecipeOnTick = true;
        super.onInventoryChanged();
    }

	@Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        checkRecipeOnTick = true;
        furnaceItemStacks[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		furnaceItemStacks = parentFurnaceItemStacksField.get(this);
		currentItemBurnTime = getItemBurnTime(furnaceItemStacks[this.getFuelSlot()]);
		checkRecipeOnTick = true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		parentFurnaceItemStacksField.set(this, furnaceItemStacks);
		super.writeToNBT(nbttagcompound);
	}
	
	@Override
	public int getCookProgressScaled(int i) {
		return (furnaceCookTime * i) / requiredTime;
	}

	@Override
	public void updateEntity()
    {
        boolean lastTickBurning = isBurning();
        if(lastTickBurning)
        {
            furnaceBurnTime--;
        }
        if(!worldObj.multiplayerWorld)
        {
            boolean inventoryChanged = false;
            if(checkRecipeOnTick) {
            	checkRecipeOnTick = false;
            	if(cooking = canSmelt()) {
            		if(!isBurning())
                    {
            			ItemStack fuel = furnaceItemStacks[this.getFuelSlot()];
                        currentItemBurnTime = furnaceBurnTime = getItemBurnTime(fuel);
                        if(isBurning())
                        {
                        	inventoryChanged = true;
                            if(fuel != null)
                            {
                                if(fuel.getItem().hasContainerItem())
                                {
                                	furnaceItemStacks[this.getFuelSlot()] = new ItemStack(fuel.getItem().getContainerItem());
                                } else
                                {
                                	furnaceItemStacks[this.getFuelSlot()].stackSize--;
                                }
                                if(fuel.stackSize == 0)
                                {
                                	furnaceItemStacks[this.getFuelSlot()] = null;
                                }
                            }
                        }
                    }
            	}
            }
            if(isBurning() && cooking) {
            	furnaceCookTime++;
                if(furnaceCookTime == requiredTime)
                {
                    furnaceCookTime = 0;
                    smeltItem();
                    inventoryChanged = true;
                    cooking = false;
                }
            }
            else
            {
                furnaceCookTime = 0;
            }
            if(lastTickBurning != isBurning())
            {
            	inventoryChanged = true;
            	this.updateBlockState(isBurning());
            }
            if(inventoryChanged)
            {
            	checkRecipeOnTick = true;
                onInventoryChanged();
            }
        }
    }

	protected boolean canSmelt()
    {
        if(this.inputSlotsEmpty())
        {
            return false;
        }
        ItemStack result = this.getResultItemStack();
        ItemStack output = furnaceItemStacks[this.getOutputSlot()];
        if(result == null)
        {
            return false;
        }
        if(output == null)
        {
            return true;
        }
        if(!output.isItemEqual(result))
        {
            return false;
        }
        if(output.stackSize < getInventoryStackLimit() && output.stackSize < output.getMaxStackSize())
        {
            return true;
        }
        return output.stackSize < result.getMaxStackSize();
    }
	
	protected int getItemBurnTime(ItemStack itemstack)
    {
        if(itemstack == null)
        {
            return 0;
        }
        int i = itemstack.getItem().shiftedIndex;
        if(i < 256 && Block.blocksList[i].blockMaterial == Material.wood)
        {
            return 300;
        }
        if(i == Item.stick.shiftedIndex)
        {
            return 100;
        }
        if(i == Item.coal.shiftedIndex)
        {
            return 1600;
        }
        if(i == Item.bucketLava.shiftedIndex)
        {
            return 20000;
        }
        if(i == Block.sapling.blockID)
        {
            return 100;
        } else
        {
            return ModLoader.AddAllFuel(i);
        }
    }

	@Override
    public void smeltItem()
    {
        if(!canSmelt())
        {
            return;
        }
        ItemStack result = this.getResultItemStack();
        ItemStack output = furnaceItemStacks[this.getOutputSlot()];
        if(output == null)
        {
        	furnaceItemStacks[this.getOutputSlot()] = result.copy();
        } 
        else if(output.isItemEqual(result))
        {
        	furnaceItemStacks[this.getOutputSlot()].stackSize += result.stackSize;
        }
        this.reduceInputSlots(1);
    }
	
	protected void updateBlockState(boolean isActive) {
		BlockFurnace.updateFurnaceBlockState(isActive, worldObj, xCoord, yCoord, zCoord);
	}
	
	protected int getFuelSlot() {
		return this.furnaceItemStacks.length - 2;
	}
	
	protected int getOutputSlot() {
		return this.furnaceItemStacks.length - 1;
	}
	
	protected ItemStack getResultItemStack() {
		return FurnaceManager.instance.getSmeltingResult(furnaceItemStacks[0]);
	}
	
	protected boolean inputSlotsEmpty() {
		return furnaceItemStacks[0] == null;
	}
	
	protected void reduceInputSlots(int amount) {
		if(furnaceItemStacks[0].getItem().hasContainerItem())
        {
            furnaceItemStacks[0] = new ItemStack(furnaceItemStacks[0].getItem().getContainerItem());
        } else
        {
            furnaceItemStacks[0].stackSize -= amount;
        }
        if(furnaceItemStacks[0].stackSize <= 0)
        {
            furnaceItemStacks[0] = null;
        }
	}
	
	protected ItemStack furnaceItemStacks[] = new ItemStack[3];
	protected boolean checkRecipeOnTick = false;
	protected boolean cooking = false;
	public int requiredTime = 200;
	private static Utils.EasyField<ItemStack[]> parentFurnaceItemStacksField = new Utils.EasyField<ItemStack[]>(TileEntityFurnace.class, "furnaceItemStacks", "i");
}
