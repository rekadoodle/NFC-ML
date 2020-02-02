package nfc;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.mod_NFC;
import net.minecraft.src.forge.ForgeHooksClient;

	

public class TileEntitySlab extends TileEntityBlock {
	
    public float minX = 0.0F;
    public float minY = 0.0F;
    public float minZ = 0.0F;
    public float maxX = 1.0F;
    public float maxY = 1.0F;
    public float maxZ = 1.0F;
    
    
	private boolean isFullBlock;
	
	/**
	 * stairs: 6-13
	 * 6 bottom east
	 * 7 bottom west
	 * 8 bottom north
	 * 9 bottom south
	 * 10 top east
	 * 11 top west
	 * 12 top north
	 * 13 top south
	 */
	public int side;
	private boolean rotateTexture;
	

    public TileEntitySlab() {
    	//update();
    }
    
    @Override
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        side = nbt.getByte("rotation");
        isFullBlock = nbt.getBoolean("doubleSlab");
        update();
    }

	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setByte("rotation", (byte)side);
        nbt.setBoolean("doubleSlab", isFullBlock);
    }
    
    public boolean acceptsNewBlock(World world, int metadata, int side) {
    	if(!isFullBlock && metadata == getBlockMetadata() && isSideOppositeOrEqual(side)) {
    		isFullBlock = true;
    		update();
    		world.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
    		return true;
    	}
    	return false;
    }
    
    public boolean isSideOppositeOrEqual(int side) {
    	if(this.side == side) return true;
    	return this.side == getOppositeSide(side);
    }
    
    public int getOppositeSide(int side) {
    	switch(side) {
    	case 0:
    		return 1;
    	case 1:
    		return 0;
    	case 2:
    		return 3;
    	case 3:
    		return 2;
    	case 4:
    		return 5;
    	case 5:
    		return 4;
    	default:
    		return 0;
    	}
    }
    
    @Override
    public String interact() {
    	/*
    	if(isFullBlock && (!rotateTexture || side == 0) && side < 4) {
        	setOrientation(side + 2);
		}
		else {
			setOrientation(side + 1);
		}
    	if(side > 5 || (isFullBlock && !rotateTexture && side > 4)){
    		setOrientation(0);
    	}
    	*/
    	setOrientation(side + 1);
    	if(side > 13)
    		setOrientation(0);
		return String.valueOf(side);
    }
    
    public void update() {
    	if(!isFullBlock) {
			final float MIN = 0.0F;
			final float MAX = 1.0F;
			final float HALF = 0.5F;
			float minX = MIN;
			float minY = MIN;
			float minZ = MIN;
			float maxX = MAX;
			float maxY = MAX;
			float maxZ = MAX;
			
			switch(side) {
			case 1:
				minY = HALF; break;
			case 0:
				maxY = HALF; break;
			case 3:
				minZ = HALF; break;
			case 2:
				maxZ = HALF; break;
			case 5:
				minX = HALF; break;
			default:
				maxX = HALF; break;
			}
			setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
		else {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
    	rotateTexture = mod_NFC.slab.shouldTextureRotate(this.getBlockMetadata());
    }
    
    public void firstBounds(Block block) {
    	if(side < 10) {
    		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    	}
    	else {
    		block.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
    	}
    }
    
    public void secondBounds(Block block) {
    	int i = (side - 6);
    	float minY;
    	float maxY;
    	float minX;
    	float maxX;
    	float minZ;
    	float maxZ;
    	final float MIN = 0.0F;
    	final float HALF = 0.5F;
    	final float MAX = 1.0F;
    	switch(i % 4) {
    	case 0:
    		minZ = HALF;
    		maxZ = MAX;
    		minX = MIN;
    		maxX = MAX;
    		break;
    	case 1:
    		minX = MIN;
    		maxX = HALF;
    		minZ = MIN;
    		maxZ = MAX;
    		break;
    	case 2:
    		minZ = MIN;
    		maxZ = HALF;
    		minX = MIN;
    		maxX = MAX;
    		break;
    	default:
    		minX = HALF;
    		maxX = MAX;
    		minZ = MIN;
    		maxZ = MAX;
    		break;
    	}
    	
    	
    	if(side < 10) {
    		minY = HALF;
    		maxY = MAX;
    	}
    	else {
    		minY = MIN;
    		maxY = HALF;
    	}
		block.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public void resetBlockBounds(Block block) {
    	block.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public void setBlockBounds(float f, float f1, float f2, float f3, float f4, float f5)
    {
        minX = f;
        minY = f1;
        minZ = f2;
        maxX = f3;
        maxY = f4;
        maxZ = f5;
    }

	public boolean shouldSideBeRendered(int side)
    {
		if(isFullBlock)
			return false;
		if(side == 0 && minY > 0.0D)
        {
            return true;
        }
        if(side == 1 && maxY < 1.0D)
        {
            return true;
        }
        if(side == 2 && minZ > 0.0D)
        {
            return true;
        }
        if(side == 3 && maxZ < 1.0D)
        {
            return true;
        }
        if(side == 4 && minX > 0.0D)
        {
            return true;
        }
        if(side == 5 && maxX < 1.0D)
        {
            return true;
        }
        return !isFullBlock;
    }
	
	public int getTextureFromSideAndMetadata(Block block, int side, int metadata) {
		int newSide = side;
		if(rotateTexture) {
			switch(this.side) {
			case 0:
				newSide = side;
				break;
			case 1:
				switch(side) {
				case 0:
					newSide = 1; break;
				case 1:
					newSide = 0; break;
				default:
					newSide = side;
				}
				break;
			default:
				switch(side) {
				case 0:
					newSide = 2; break;
				case 1:
					newSide = 3; break;
				default:
					if(side == this.side){
						newSide = 0;
					}
					else if(isSideOppositeOrEqual(side)){
						newSide = 1;
					}
					else {
						newSide = 2;
					}
				}
			}
		}
		return block.getBlockTextureFromSideAndMetadata(newSide, metadata);
	}
	
	public void setOrientation(int side) {
		this.side = side;
		update();
	}

	public int getQuantity() {
		return isFullBlock ? 2 : 1;
	}
}
