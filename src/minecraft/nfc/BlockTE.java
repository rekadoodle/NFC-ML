package nfc;

import java.util.Random;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public abstract class BlockTE extends BlockContainer implements ITextureProvider, ISpecialResistance {
	
	private final String modTextureFile = new StringBuilder(mod_NFC.resources).append("terrain.png").toString();
	private String textureFile = modTextureFile;
	private int renderType = mod_NFC.render_ID;
	protected TileEntityBlock blockRemovedTileEntity;

	protected BlockTE(int i, Material material) {
		super(i, material);
		ModLoader.RegisterBlock(this, ItemBlockNFC.class);
	}
	
	public TileEntityBlock getTileEntity(IBlockAccess iblockaccess, int x, int y, int z) {
		TileEntity tileentity = iblockaccess.getBlockTileEntity(x, y, z);
		if(tileentity instanceof TileEntityBlock) {
			return (TileEntityBlock) tileentity;
		}
		return null;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityliving)
    {
		TileEntityBlock tileentity = this.getTileEntity(world, x, y, z);
		if(tileentity != null && entityliving instanceof EntityPlayer) {
			ItemStack itemstack = ((EntityPlayer)entityliving).getCurrentEquippedItem();
			if(itemstack != null) {
				tileentity.setMetadata(itemstack.getItemDamage());
			}
		}
    }
	
	@Override
	public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side)
    {
        TileEntity tileentity = this.getTileEntity(iblockaccess, x, y, z);
        if(tileentity != null) {
            return this.getBlockTextureFromSideAndMetadata(side, tileentity.getBlockMetadata());
        }
        return this.getBlockTextureFromSideAndMetadata(side, iblockaccess.getBlockMetadata(x, y, z));
    }
	
	@Override
	public String getTextureFile() 
	{
		return this.textureFile;
	}
	
	public void setTextureFile(String s) {
		this.textureFile = s;
	}
	
	public void resetTextureFile() {
		this.textureFile = this.modTextureFile;
	}
	
	
	@Override
    public void onBlockRemoval(World world, int x, int y, int z)
    {
		this.blockRemovedTileEntity = this.getTileEntity(world, x, y, z);
        super.onBlockRemoval(world, x, y, z);
    }
	
	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chance)
    {
        if(world.multiplayerWorld)
        {
            return;
        }
        TileEntityBlock tileentity = this.getTileEntity(world, x, y, z);
        if(tileentity == null) {
        	tileentity = blockRemovedTileEntity;
        }
        if(tileentity != null) {
            metadata = blockRemovedTileEntity.getBlockMetadata();
        }
        int quantityDropped = quantityDropped(metadata, world.rand);
        for(int i = 0; i < quantityDropped; i++)
        {
            if(world.rand.nextFloat() > chance)
            {
                continue;
            }
            int id = idDropped(metadata, world.rand);
            if(id > 0)
            {
                dropBlockAsItem_do(world, x, y, z, new ItemStack(id, 1, damageDropped(metadata)));
            }
        }
    }
	
	public int quantityDropped(int metadata, Random random)
    {
        return this.quantityDropped(random);
    }
	
	@Override
	public float blockStrength(World world, EntityPlayer player, int x, int y, int z) {
		TileEntity tileentity = this.getTileEntity(world, x, y, z);
	    return blockStrength(player, tileentity.getBlockMetadata());
    }
	
	@Override
	public float getSpecialExplosionResistance(World world, int x, int y, int z, double src_x, double src_y, double src_z, Entity exploder) {
		TileEntity tileentity = this.getTileEntity(world, x, y, z);
		return this.getResistance(tileentity.getBlockMetadata());
	}
	
	public float getResistance(int metadata) {
		return this.blockResistance;
	}
	
	public PropsBlockTexture getRenderProps(IBlockAccess iblockaccess, int x, int y, int z)
    {
		TileEntity tileentity = this.getTileEntity(iblockaccess, x, y, z);
		return this.getRenderProps(tileentity.getBlockMetadata());
    }
	
	public abstract PropsBlockTexture getRenderProps(int metadata);
	
	@Override
	public int getRenderType()
    {
		return this.renderType;
    }
	
	public void setRenderType(int renderType) {
		this.renderType = renderType;
	}
	
	public void resetRenderType() {
		this.renderType = mod_NFC.render_ID;
	}
	
	public String getItemNameIS(ItemStack itemstack)
    {
    	return this.getItemNameFromMetadata(itemstack.getItemDamage());
    }
	
	protected String getName(int metadata) {
    	return new StringBuilder(this.getItemNameFromMetadata(metadata)).append(".name").toString();
    }
	
	protected abstract String getItemNameFromMetadata(int metadata);

}
