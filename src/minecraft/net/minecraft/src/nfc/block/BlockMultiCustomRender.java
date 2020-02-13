package net.minecraft.src.nfc.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.*;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.nfc.props.*;
import net.minecraft.src.nfc.props.PropsItem.Wrench.IWrenchable;

public class BlockMultiCustomRender extends BlockMultiTexture implements IWrenchable {

	private PropsBlockCustomRenderMulti currentRender;
	
	private static PropsBlock[] convertProps(PropsBlock[] blocks) {
		for(int i = 0; i < blocks.length; i++) {
			if(blocks[i].getClass() == PropsBlockTexture.class) {
				blocks[i] = new PropsBlockDummyCustom((PropsBlockTexture) blocks[i]);
			}
		}
		return blocks;
	}

	public BlockMultiCustomRender(int id, int renderID, Material material, PropsBlock ...blocks) {
		super(id, renderID, material, convertProps(blocks));
		int correctId = 0;
		for(PropsBlock obj : super.getAllBlockProps()) {
			PropsBlockCustomRenderMulti blockprops = (PropsBlockCustomRenderMulti) obj;
			boolean flag = blockprops.ID_DROPPED == blockprops.block_id && blockprops.DAMAGE_DROPPED == blockprops.block_metadata;
			blockprops.block_metadata = correctId;
			correctId += blockprops.IDS_USED;
			if(flag) {
				blockprops.DAMAGE_DROPPED = blockprops.block_metadata;
			}
		}
		//Shows remaining metadatas for this block on launch
		//System.out.println("block with id " + id + " has " + (16 - correctId) + " metadatas remaining");
		if(material == Material.rock) {
			MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 0);
		}
		else if(material == Material.wood) {
			MinecraftForge.setBlockHarvestLevel(this, "axe", 0);
		}
	}
	
	@Override
	public void onBlockPlaced(World world, int x, int y, int z, int side)
    {
		PropsBlockCustomRenderMulti blockprops = this.getBlockProps(world.getBlockMetadata(x, y, z));
		blockprops.onBlockPlaced(world, x, y, z, side, this);
    }
	
	@Override
	public void renderBlockWorld(RenderBlocks renderblocks, int metadata, int x, int y, int z) {
		PropsBlockCustomRenderMulti blockprops = this.getBlockProps(metadata);
		this.preRender(blockprops, metadata, renderblocks);
		if(blockprops instanceof PropsBlockSlabRotatedTexture) {
			int subMetadata = metadata - blockprops.block_metadata;
			if(subMetadata > 5) {
				subMetadata -= 6;
				subMetadata *= 2;
			}
			switch(subMetadata) {
			case 3:
			case 2:
				renderblocks.field_31085_i = 1;
				renderblocks.field_31084_j = 2;
				renderblocks.field_31083_k = 0;
				renderblocks.field_31082_l = 0;
				break;
			/* These 2 commented out cases are used for flipping the rotation based on the side that the block is placed on
			 * NFC only uses 3 possible texture rotations so I'm sticking with that for now (normal, z-rotated 90 and x-rotated 90)
			 * but commenting out these allows for z-rotated 270 and x-rotated 270. More metadatas would be required for the double slabs
			 * to use these extra rotations.
			case 2:
				renderblocks.field_31085_i = 2;
				renderblocks.field_31084_j = 1;
				renderblocks.field_31083_k = 3;
				renderblocks.field_31082_l = 3;
				break;
			*/
			case 5:
			case 4:
				renderblocks.field_31087_g = 1;
				renderblocks.field_31086_h = 2;
				renderblocks.field_31083_k = 2;
				renderblocks.field_31082_l = 1;
				break;
			/*
			case 4:
				renderblocks.field_31087_g = 2;
				renderblocks.field_31086_h = 1;
				renderblocks.field_31083_k = 1;
				renderblocks.field_31082_l = 2;
			*/
			}
		}
		for(int i = 0; i < blockprops.RENDERS; i++) {
			blockprops.setBlockBounds(this, metadata - blockprops.block_metadata, i);
			renderblocks.renderStandardBlock(this, x, y, z);
		}
		renderblocks.field_31087_g = 0;
		renderblocks.field_31086_h = 0;
		renderblocks.field_31085_i = 0;
		renderblocks.field_31084_j = 0;
		renderblocks.field_31083_k = 0;
		renderblocks.field_31082_l = 0;
		this.postRender();
	}
	
	@Override
	public void preRender(PropsBlock blockprops, int metadata, RenderBlocks renderblocks) {
		super.preRender(blockprops, metadata, renderblocks);
		this.currentRender = (PropsBlockCustomRenderMulti) blockprops;
	}
	
	@Override
	public void postRender() {
		super.postRender();
		this.currentRender = null;
	}
	
	@Override
	public void renderBlockInv(RenderBlocks renderblocks, int metadata) {
		PropsBlockCustomRenderMulti blockprops = this.getBlockProps(metadata);
		blockprops.setInvBlockBounds(this, metadata - blockprops.block_metadata);
		super.renderBlockInv(renderblocks, metadata);
	}
	
	@Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public boolean isOpaqueCube()
    {
        return false;
    }
	
	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, int side) {
		int metadata = world.getBlockMetadata(x, y, z);
		PropsBlockCustomRenderMulti blockprops = this.getBlockProps(metadata);
	    return blockprops.isBlockSolidOnSide(side, metadata - blockprops.block_metadata);
    }
	
	@Override
	public int quantityDropped(int metadata, Random random)
    {
		PropsBlock blockprops = this.getBlockProps(metadata);
		if(blockprops instanceof PropsBlockSlabRotatedTexture) {
			return metadata - blockprops.block_metadata > 5 ? 2 : 1;
		}
        return 1;
    }
	
	@Override
	public PropsBlockCustomRenderMulti getBlockProps(int metadata) {
		int correctId = 0;
		for(PropsBlock obj : super.getAllBlockProps()) {
			PropsBlockCustomRenderMulti blockprops = (PropsBlockCustomRenderMulti) obj;
			correctId += blockprops.IDS_USED;
			if(metadata < correctId) {
				return blockprops;
			}
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
    public void getCollidingBoundingBoxes(World world, int x, int y, int z, AxisAlignedBB axisalignedbb, ArrayList arraylist)
    {
		int metadata = world.getBlockMetadata(x, y, z);
		PropsBlockCustomRenderMulti blockprops = this.getBlockProps(metadata);
		for(int i = 0; i < blockprops.RENDERS; i++) {
			int subMetadata = metadata - blockprops.block_metadata;
			blockprops.setBlockBounds(this, subMetadata, i);
			super.getCollidingBoundingBoxes(world, x, y, z, axisalignedbb, arraylist);
		}
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int x, int y, int z)
    {
		int metadata = iblockaccess.getBlockMetadata(x, y, z);
		PropsBlockCustomRenderMulti blockprops = this.getBlockProps(metadata);
		int subMetadata = metadata - blockprops.block_metadata;
		blockprops.setBoundingBox(this, subMetadata);
    }
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata)
    {
		PropsBlockCustomRenderMulti blockprops = this.getBlockProps(metadata);
		int subMetadata = metadata - blockprops.block_metadata;
		return blockprops.getTextureIndex(side, subMetadata);
    }
	
	/**
	 * Used for inventory item rendering
	 */
	@Override
	public int getBlockTextureFromSide(int side)
    {
		if(currentRender == null) return super.getBlockTextureFromSide(side);
		return currentRender.getTextureIndex(side);
    }

	@Override
	public boolean onWrenched(World world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		PropsBlockCustomRenderMulti blockprops = this.getBlockProps(metadata);
		int newMetadata = blockprops.getMetadataAfterWrench(metadata);
		if(metadata == newMetadata) {
			return false;
		}
		world.markBlockAsNeedsUpdate(x, y, z);
		return world.setBlockMetadata(x, y, z, newMetadata);
	}
	
	@Override
	public String getItemNameIS(ItemStack itemstack)
    {
		int metadata = itemstack.getItemDamage();
		PropsBlockCustomRenderMulti blockprops = this.getBlockProps(metadata);
		int subMetadata = metadata - blockprops.block_metadata;
    	return blockprops.getName(subMetadata);
    }
}
