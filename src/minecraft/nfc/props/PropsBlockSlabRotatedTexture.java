package nfc.props;

import net.minecraft.src.Block;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.World;
import nfc.block.BlockMultiCustomRender;

public class PropsBlockSlabRotatedTexture extends PropsBlockSlab {

	public PropsBlockSlabRotatedTexture(String name, Block block, int metadata) {
		super(name, block, metadata, 9);
	}
	
	@Override
	public void onBlockPlaced(World world, int x, int y, int z, int side, BlockMultiCustomRender block) {
		ChunkCoordinates adjacentCoords = this.adjacentBlockCoords(x, y, z, side);
        if(this.block_id == world.getBlockId(adjacentCoords.x, adjacentCoords.y, adjacentCoords.z)) {
        	int metadata = world.getBlockMetadata(adjacentCoords.x, adjacentCoords.y, adjacentCoords.z);
        	if(metadata >= this.block_metadata && metadata - this.block_metadata < this.IDS_USED) {
        		int subMetadata = metadata - this.block_metadata;
        		if(subMetadata < 6) {
                	if(subMetadata == this.getOppositeSide(side)) {
                		world.setBlockMetadata(adjacentCoords.x, adjacentCoords.y, adjacentCoords.z, this.block_metadata + 6 + (subMetadata / 2));
                		world.setBlock(x, y, z, 0);
                	}
                	else if(subMetadata != side) {
                		world.setBlockMetadata(x, y, z, metadata);
                	}
                	return;
        		}
        	}
        }
		world.setBlockMetadata(x, y, z, block_metadata + this.getOppositeSide(side));
	}
	
	@Override
	public void setBlockBounds(Block block, int metadata, int renders) {
		if(metadata > 5) {
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
		else {
			super.setBlockBounds(block, metadata, renders);
		}
	}

	@Override
	public int getTextureIndex(int side, int metadata) {
		int newSide;
		if(metadata > 5) {
			metadata -= 6;
			metadata *= 2;
		}
		switch(metadata) {
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
				if(side == metadata){
					newSide = 0;
				}
				else if(getOppositeSide(side) == metadata){
					newSide = 1;
				}
				else {
					newSide = 2;
				}
			}
		}
		return super.getTextureIndex(newSide, metadata);
	}
}
