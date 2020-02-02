package nfc;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityBlock extends TileEntity {
	
	private int metadata;

	public TileEntityBlock() {
		
	}
	
	public String interact() {
		setMetadata(getBlockMetadata() + 1);
		return String.valueOf(metadata);
	}
	
	public void setMetadata(int i) {
		metadata = i;
	}
	
	public int getBlockMetadata() {
		return metadata;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        metadata = nbt.getInteger("metadata");
    }

	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("metadata", metadata);
    }
}
