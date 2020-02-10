package net.minecraft.src.nfc.block;

import net.minecraft.src.*;
import net.minecraft.src.nfc.TileEntityFurnaceMetadataFix;
import net.minecraft.src.nfc.Utils;

public class BlockFurnaceMetadataFix extends BlockFurnace {

	public BlockFurnaceMetadataFix(Block block, boolean isActive) {
		super(Utils.clearBlockID(block), isActive);
		this.setHardness(3.5F);
		this.setStepSound(soundStoneFootstep);
		this.setBlockName("furnace");
		this.disableNeighborNotifyOnMetadataChange();
		if(isActive) {
			this.setLightValue(0.875F);
		}
	}
	
	@Override
	protected TileEntity getBlockEntity()
    {
        return new TileEntityFurnaceMetadataFix();
    }

}
