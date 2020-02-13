package net.minecraft.src.nfc.props;

import net.minecraft.src.*;
import net.minecraft.src.nfc.block.BlockMultiCustomRender;


public abstract class PropsBlockCustomRenderMulti extends PropsBlockTexture {
	public final int IDS_USED;
	public final int RENDERS;
	
	public PropsBlockCustomRenderMulti(String name, int idsUsed, int renders, String textureFile, int renderType, float hardness, float resistance, int idDropped, int damageDropped, int textureIndex) {
		super(name, textureFile, renderType, hardness, resistance, idDropped, damageDropped, textureIndex);
		this.IDS_USED = idsUsed;
		this.RENDERS = renders;
	}
	
	public ChunkCoordinates adjacentBlockCoords(int x, int y, int z, int side) {
		if(side == 0)
        {
            y++;
        }
		else if(side == 1)
        {
            y--;
        }
		else if(side == 2)
        {
            z++;
        }
		else if(side == 3)
        {
            z--;
        }
		else if(side == 4)
        {
            x++;
        }
		else if(side == 5)
        {
            x--;
        }
        return new ChunkCoordinates(x, y, z);
	}
	
	public int getMetadataAfterWrench(int metadata) {
		if(++metadata >= block_metadata + IDS_USED) {
			return block_metadata;
		}
		return metadata;
	}
	
	public String getName(int metadata) {
		return this.getName();
	}
	
	public abstract void setBlockBounds(Block block, int metadata, int renders);
	public abstract int getTextureIndex(int side, int metadata);
	public abstract void onBlockPlaced(World world, int x, int y, int z, int side, BlockMultiCustomRender block);
	public abstract void setBoundingBox(Block block, int metadata);
	public abstract void setInvBlockBounds(Block block, int metadata);
	public abstract boolean isBlockSolidOnSide(int side, int metadata);
	
}