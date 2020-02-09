package nfc.props;

import net.minecraft.src.*;
import nfc.Utils;
import nfc.block.BlockMultiCustomRender;

public class PropsBlockStairs extends PropsBlockCustomRenderMulti {
	
	public Block block;
	public int blockmetadata;

	
	public PropsBlockStairs(String name, Block block, int metadata) {
		super(name, 8, 2, PropsBlockTexture.getTextureFile(block), PropsBlockTexture.STAIRS, block.getHardness(metadata), block.getExplosionResistance(null), 0, 0, block.getBlockTextureFromSideAndMetadata(0, metadata));
		this.block = block;
		this.blockmetadata = metadata;
		//this.item_metadata = 6;
		this.addLocalisation(new StringBuilder().append(this.NAME).append(' ').append("Stairs").toString());
	}

	public PropsBlockStairs(String name, Block block) {
		this(name, block, 0);
	}

	@Override
	public void setBlockBounds(Block block, int metadata, int renders) {
		if(renders == 0) {
			if(metadata < 4) {
	    		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	    	}
	    	else {
	    		block.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
	    	}
		}
		else {
			int i = metadata;
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
	    	if(metadata < 4) {
	    		minY = HALF;
	    		maxY = MAX;
	    	}
	    	else {
	    		minY = MIN;
	    		maxY = HALF;
	    	}
			block.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
	}

	@Override
	public int getTextureIndex(int side, int metadata) {
		return block.getBlockTextureFromSideAndMetadata(side, blockmetadata);
	}

	@Override
	public void onBlockPlaced(World world, int x, int y, int z, int side, BlockMultiCustomRender block) {
		ChunkCoordinates adjacentCoords = this.adjacentBlockCoords(x, y, z, side);
        if(block == Block.blocksList[world.getBlockId(adjacentCoords.x, adjacentCoords.y, adjacentCoords.z)]) {
        	int metadata = world.getBlockMetadata(adjacentCoords.x, adjacentCoords.y, adjacentCoords.z);
        	if(metadata >= this.block_metadata && metadata - this.block_metadata < this.IDS_USED) {
        		int subMetadata = metadata - this.block_metadata;
        		System.out.println((subMetadata % 4) + " " + this.getOppositeSide(side) + " " + side);
            	if(subMetadata != side && (subMetadata % 4) != this.getOppositeSide(side)) {
            		world.setBlockMetadata(x, y, z, metadata);
                	return;
            	}
        	}
        }
		int metadata = MathHelper.floor_double((double)((Utils.mc.thePlayer.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		MovingObjectPosition mop = Utils.mc.objectMouseOver;
		if(mop != null && mop.typeOfHit == EnumMovingObjectType.TILE) {
			double yPlacementCoordOnBlock = mop.hitVec.yCoord - (double)y;
			if(yPlacementCoordOnBlock > 0.5)
				metadata += 4;
		}
		world.setBlockMetadata(x, y, z, block_metadata + metadata);
	}
	
	public int getOppositeSide(int side) {
    	switch(side % 4) {
    	case 0:
    		return 1;
    	case 3:
    		return 0;
    	case 2:
    		return 2;
    	case 1:
    		return 3;
    	}
    	return side;
    }

	@Override
	public void setBoundingBox(Block block, int metadata) {
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public void setInvBlockBounds(Block block, int metadata) {
		//unused
	}

}
