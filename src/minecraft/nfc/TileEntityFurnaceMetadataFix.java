package nfc;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFurnace;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntityFurnace;

public class TileEntityFurnaceMetadataFix extends TileEntityFurnace {
	
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
	public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        checkRecipeOnTick = true;
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
            		if(furnaceBurnTime == 0)
                    {
                        currentItemBurnTime = furnaceBurnTime = getItemBurnTime(furnaceItemStacks[1]);
                        if(furnaceBurnTime > 0)
                        {
                        	inventoryChanged = true;
                            if(furnaceItemStacks[1] != null)
                            {
                                if(furnaceItemStacks[1].getItem().hasContainerItem())
                                {
                                    furnaceItemStacks[1] = new ItemStack(furnaceItemStacks[1].getItem().getContainerItem());
                                } else
                                {
                                    furnaceItemStacks[1].stackSize--;
                                }
                                if(furnaceItemStacks[1].stackSize == 0)
                                {
                                    furnaceItemStacks[1] = null;
                                }
                            }
                        }
                    }
            	}
            }
            if(isBurning() && cooking) {
            	furnaceCookTime++;
                if(furnaceCookTime == 200)
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
                BlockFurnace.updateFurnaceBlockState(isBurning(), worldObj, xCoord, yCoord, zCoord);
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
        if(furnaceItemStacks[0] == null)
        {
            return false;
        }
        ItemStack itemstack = FurnaceManager.instance.getSmeltingResult(furnaceItemStacks[0]);
        if(itemstack == null)
        {
            return false;
        }
        if(furnaceItemStacks[2] == null)
        {
            return true;
        }
        if(!furnaceItemStacks[2].isItemEqual(itemstack))
        {
            return false;
        }
        if(furnaceItemStacks[2].stackSize < getInventoryStackLimit() && furnaceItemStacks[2].stackSize < furnaceItemStacks[2].getMaxStackSize())
        {
            return true;
        }
        return furnaceItemStacks[2].stackSize < itemstack.getMaxStackSize();
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
        ItemStack itemstack = FurnaceManager.instance.getSmeltingResult(furnaceItemStacks[0]);
        if(furnaceItemStacks[2] == null)
        {
            furnaceItemStacks[2] = itemstack.copy();
        } else
        if(furnaceItemStacks[2].itemID == itemstack.itemID)
        {
            furnaceItemStacks[2].stackSize += itemstack.stackSize;
        }
        if(furnaceItemStacks[0].getItem().hasContainerItem())
        {
            furnaceItemStacks[0] = new ItemStack(furnaceItemStacks[0].getItem().getContainerItem());
        } else
        {
            furnaceItemStacks[0].stackSize--;
        }
        if(furnaceItemStacks[0].stackSize <= 0)
        {
            furnaceItemStacks[0] = null;
        }
    }
	
	protected ItemStack furnaceItemStacks[] = new ItemStack[3];
	protected boolean checkRecipeOnTick = false;
	protected boolean cooking = false;
}
