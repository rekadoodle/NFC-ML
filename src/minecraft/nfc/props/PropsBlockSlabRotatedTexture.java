package nfc.props;

import net.minecraft.src.Block;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import nfc.block.BlockMultiCustomRender;

public class PropsBlockSlabRotatedTexture extends PropsBlockSlab {

	public PropsBlockSlabRotatedTexture(String name, Block block, int metadata) {
		super(name, block, metadata, 9);
		ModLoader.AddLocalization(new StringBuilder().append(this.getName(6)).append(".name").toString(), "Double Slab");
	}
	
	@Override
	public void onBlockPlaced(World world, int x, int y, int z, int side, BlockMultiCustomRender block) {
		int metadata = world.getBlockMetadata(x, y, z);
		if(metadata - this.block_metadata < 6) {
			ChunkCoordinates adjacentCoords = this.adjacentBlockCoords(x, y, z, side);
	        if(this.block_id == world.getBlockId(adjacentCoords.x, adjacentCoords.y, adjacentCoords.z)) {
	        	int adjacentMetadata = world.getBlockMetadata(adjacentCoords.x, adjacentCoords.y, adjacentCoords.z);
	        	if(adjacentMetadata >= this.block_metadata && adjacentMetadata - this.block_metadata < this.IDS_USED) {
	        		int subMetadata = adjacentMetadata - this.block_metadata;
	        		if(subMetadata < 6) {
	                	if(subMetadata == this.getOppositeSide(side)) {
	                		world.setBlockMetadata(adjacentCoords.x, adjacentCoords.y, adjacentCoords.z, this.block_metadata + 6 + (subMetadata / 2));
	                		world.setBlock(x, y, z, 0);
	                	}
	                	else if(subMetadata != side) {
	                		world.setBlockMetadata(x, y, z, adjacentMetadata);
	                	}
	                	return;
	        		}
	        	}
	        }
			world.setBlockMetadata(x, y, z, block_metadata + this.getOppositeSide(side));
		}
		else {
			world.setBlockMetadata(x, y, z, block_metadata + 6 + (side / 2));
		}
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
	
	@Override
	public int getMetadataAfterWrench(int metadata) {
		if(metadata < block_metadata + 6) {
			if(++metadata >= block_metadata + 6) {
				return block_metadata;
			}
		}
		else if(++metadata >= block_metadata + IDS_USED) {
			return block_metadata + 6;
		}
		return metadata;
	}
	
	@Override
	public void setInvBlockBounds(Block block, int metadata) {
		if(metadata < 6) {
			super.setInvBlockBounds(block, metadata);
		}
		else {
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	@Override
	public String getName(int metadata) {
		return new StringBuilder().append(super.getName()).append(metadata < 6 ? "" : ".double").toString();
	}
}
