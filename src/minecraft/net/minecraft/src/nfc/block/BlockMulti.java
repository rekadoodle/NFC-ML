package net.minecraft.src.nfc.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;
import net.minecraft.src.nfc.Core;
import net.minecraft.src.nfc.item.ItemBlockNFC;
import net.minecraft.src.nfc.props.PropsBlock;

public class BlockMulti extends Block implements ITextureProvider, ISpecialResistance {
	private final List<PropsBlock> propsList = new ArrayList<PropsBlock>();
	
	public BlockMulti(int i, Material material, PropsBlock ...blocks) {
		super(i, material);
		for(PropsBlock blockprops : blocks) {
			blockprops.block_id = this.blockID;
			blockprops.block_metadata = this.propsList.size();
			if(blockprops.DAMAGE_DROPPED == 0 && blockprops.ID_DROPPED == 0) {
				blockprops.DAMAGE_DROPPED = blockprops.block_metadata;
				blockprops.ID_DROPPED = blockprops.block_id;
			}
			this.propsList.add(blockprops);
			if(blockprops instanceof PropsBlock.Ore) {
				((PropsBlock.Ore) blockprops).setHarvestLevel();
			}
			else if(material == Material.rock) {
				MinecraftForge.setBlockHarvestLevel(this, blockprops.block_metadata, "pickaxe", 0);
			}
			else if(material == Material.wood) {
				MinecraftForge.setBlockHarvestLevel(this, blockprops.block_metadata, "axe", 0);
			}
		}
		ModLoader.RegisterBlock(this, ItemBlockNFC.class);
	}

	public PropsBlock getBlockProps(int metadata) {
		if(metadata > propsList.size())
			return propsList.get(0);
		return propsList.get(metadata);
	}
	
	public List<PropsBlock> getAllBlockProps(){
		return propsList;
	}
	
	@Override
	public String getTextureFile() 
	{
		return new StringBuilder(Core.resources).append("terrain.png").toString();
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata)
    {
		return this.getBlockProps(metadata).getTextureIndex(side);
    }
	
	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chance)
    {
        if(world.multiplayerWorld)
        {
            return;
        }
        PropsBlock blockprops = this.getBlockProps(metadata);
        int quantityDropped = quantityDropped(metadata, world.rand);
        
        for(int i = 0; i < quantityDropped; i++)
        {
            if(world.rand.nextFloat() > chance)
            {
                continue;
            }
            int id = idDropped(blockprops, world.rand);
            if(id > 0)
            {
                dropBlockAsItem_do(world, x, y, z, new ItemStack(id, 1, damageDropped(blockprops)));
            }
        }
    }
	
	public int quantityDropped(int metadata, Random random)
    {
        return this.quantityDropped(this.getBlockProps(metadata), random);
    }
	
	public int quantityDropped(PropsBlock blockprops, Random random)
    {
        return 1;
    }
	
	protected int damageDropped(PropsBlock blockprops)
    {
        return blockprops.DAMAGE_DROPPED;
    }
	
	public int idDropped(PropsBlock blockprops, Random random) {
		return blockprops.ID_DROPPED;
	}
	
	@Override
	public float getSpecialExplosionResistance(World world, int x, int y, int z, double src_x, double src_y, double src_z, Entity exploder) {
		return this.getBlockProps(world.getBlockMetadata(x, y, z)).RESISTANCE * 3;
	}
	
	@Override
	public float getHardness(int metadata)
	{
		return this.getBlockProps(metadata).HARDNESS;
    }
	
	public String getItemNameIS(ItemStack itemstack)
    {
    	return this.getBlockProps(itemstack.getItemDamage()).getName();
    }

}
