package net.minecraft.src.nfc.item;

import net.minecraft.src.*;
import net.minecraft.src.nfc.block.BlockMulti;

public class ItemBlockNFC extends ItemBlock {

	public ItemBlockNFC(int id) {
		super(id);
		this.setHasSubtypes(true);
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
