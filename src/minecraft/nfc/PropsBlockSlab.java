package nfc;

import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class PropsBlockSlab extends PropsBlockCustomRenderMulti {


	public Block block;
	public int blockmetadata;
	
	public PropsBlockSlab(String name, Block block) {
		this(name, block, 0);
	}
	
	public PropsBlockSlab(String name, Block block, int metadata) {
		this(name, block, metadata, 6);
	}
	
	public PropsBlockSlab(String name, Block block, int metadata, int idsUsed) {
		super(name, idsUsed, 1, PropsBlockTexture.getTextureFile(block), PropsBlockTexture.STANDARD_BLOCK, block.getHardness(metadata), block.getExplosionResistance(null), 0, 0, block.getBlockTextureFromSideAndMetadata(0, metadata));
		this.block = block;
		this.blockmetadata = metadata;
		this.addLocalisation(new StringBuilder().append(this.NAME).append(' ').append("Slab").toString());
	}
	
	@Override
	public void onBlockPlaced(World world, int x, int y, int z, int side, BlockMultiCustomRender block) {
		ChunkCoordinates adjacentCoords = this.adjacentBlockCoords(x, y, z, side);
        if(this.block_id == world.getBlockId(adjacentCoords.x, adjacentCoords.y, adjacentCoords.z)) {
        	System.out.println("AYO");
        	int metadata = world.getBlockMetadata(adjacentCoords.x, adjacentCoords.y, adjacentCoords.z);
        	if(metadata >= this.block_metadata && metadata - this.block_metadata < this.IDS_USED) {
        		int subMetadata = metadata - this.block_metadata;
            	if(subMetadata == this.getOppositeSide(side)) {
            		world.setBlockAndMetadata(adjacentCoords.x, adjacentCoords.y, adjacentCoords.z, this.block.blockID, this.blockmetadata);
            		world.setBlock(x, y, z, 0);
            	}
            	else if(subMetadata != side) {
            		world.setBlockMetadata(x, y, z, metadata);
            	}
            	return;
        	}
        }
		world.setBlockMetadata(x, y, z, block_metadata + this.getOppositeSide(side));
	}
	
	@Override
	protected String getNamePrefix() {
		return "item.nfc.slab.";
	}

	@Override
	public void setBlockBounds(Block block, int metadata, int renders) {
		float minX = 0.0F;
		float minY = 0.0F;
		float minZ = 0.0F;
		float maxX = 1.0F;
		float maxY = 1.0F;
		float maxZ = 1.0F;
		
		switch(metadata) {
		case 1:
			minY = 0.5F; break;
		case 0:
			maxY = 0.5F; break;
		case 3:
			minZ = 0.5F; break;
		case 2:
			maxZ = 0.5F; break;
		case 5:
			minX = 0.5F; break;
		default:
			maxX = 0.5F; break;
		}
		block.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	@Override
	public void setBoundingBox(Block block, int metadata) {
		this.setBlockBounds(block, metadata, 0);
	}
	
	@Override
	public int getTextureIndex(int side, int metadata) {
		return block.getBlockTextureFromSideAndMetadata(side, blockmetadata);
	}

	@Override
	public void setInvBlockBounds(Block block, int metadata) {
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
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
}
