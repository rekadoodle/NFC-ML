package nfc;

import java.util.ArrayList;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.forge.ForgeHooksClient;
import net.minecraft.src.forge.MinecraftForge;
import nfc.BlockSlab.BlockProperties;

public class BlockMultiCustomRender extends BlockMultiTexture {

	private PropsBlockCustomRenderMulti currentRender;
	
	private static PropsBlock[] convertProps(PropsBlock[] blocks) {
		for(int i = 0; i < blocks.length; i++) {
			if(blocks[i].getClass() == PropsBlockTexture.class) {
				blocks[i] = new PropsBlockDummyCustom((PropsBlockTexture) blocks[i]);
			}
		}
		return blocks;
	}

	public BlockMultiCustomRender(int id, Material material, PropsBlock ...blocks) {
		super(id, material, convertProps(blocks));
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
		for(int i = 0; i < blockprops.RENDERS; i++) {
			blockprops.setBlockBounds(this, metadata - blockprops.block_metadata, i);
			renderblocks.renderStandardBlock(this, x, y, z);
		}
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
		this.getBlockProps(metadata).setInvBlockBounds(this, metadata);
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
}
