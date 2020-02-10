package nfc.item;

import net.minecraft.src.*;
import nfc.Core;
import nfc.block.BlockMulti;

public class ItemBlockNFC extends ItemBlock {

	public ItemBlockNFC(int id) {
		super(id);
		this.setHasSubtypes(true);
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
		if(itemstack.itemID == Core.blockID + 1) {
			boolean flag = false;
			int x2 = i;
			int y2 = j;
			int z2 = k;
			if(world.getBlockId(i, j, k) == Core.blockID + 1) {
				flag = true;
			}
			else {
				
				if(l == 0)
	            {
					y2--;
	            }
				else if(l == 1)
	            {
	            	y2++;
	            }
				else if(l == 2)
	            {
	            	z2--;
	            }
				else if(l == 3)
	            {
	            	z2++;
	            }
				else if(l == 4)
	            {
	            	x2--;
	            }
				else if(l == 5)
	            {
	            	x2++;
	            }
				flag = world.getBlockId(x2, y2, z2) == Core.blockID + 1;
			}
			if(flag) {
				/*
				TileEntitySlab e = (TileEntitySlab) world.getBlockTileEntity(x2, y2, z2);
				if(e.acceptsNewBlock(world, itemstack.getItemDamage(), l)) {
					Block block = Block.blocksList[Core.blockID];
					world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, block.stepSound.func_1145_d(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
	                itemstack.stackSize--;
					return true;
				}
				*/
			}
		}
		return super.onItemUse(itemstack, entityplayer, world, i, j, k, l);
    }
	
	@Override
    public String getItemNameIS(ItemStack itemstack)
    {
		Block block = Block.blocksList[shiftedIndex];
		if(block instanceof BlockMulti) return ((BlockMulti)block).getItemNameIS(itemstack);
    	return block.getBlockName();
    }
	
	@Override
	public int getPlacedBlockMetadata(int i)
    {
        return i;
    }
}
